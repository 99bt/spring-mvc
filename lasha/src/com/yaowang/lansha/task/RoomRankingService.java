package com.yaowang.lansha.task;

import com.yaowang.common.util.LogUtil;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.dao.LanshaGiftUserDao;
import com.yaowang.lansha.dao.LanshaLiveHistoryDao;
import com.yaowang.lansha.dao.YwUserRoomHistoryDao;
import com.yaowang.lansha.dao.YwUserRoomRelationDao;
import com.yaowang.lansha.entity.*;
import com.yaowang.lansha.service.LanshaRoomRankingService;
import com.yaowang.lansha.service.YwUserRoomDayDataService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 房间积分排名
 *
 * @author shenl
 */
@Service("roomRankingService")
public class RoomRankingService {
    /**
     * 是否正在统计
     */
    private static Boolean IS_REPORT = false;

    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private LanshaLiveHistoryDao lanshaLiveHistoryDao;
    @Resource
    private LanshaGiftUserDao lanshaGiftUserDao;
    @Resource
    private YwUserRoomRelationDao ywUserRoomRelationDao;
    @Resource
    private YwUserRoomHistoryDao ywUserRoomHistoryDao;
    @Resource
    private LanshaRoomRankingService lanshaRoomRankingService;
    @Resource
    private YwUserRoomDayDataService ywUserRoomDayDataService;

    /**
     * 请求数据
     */
    public void report() {
        if (IS_REPORT) {
            return;
        }
        try {
            IS_REPORT = true;
            try {
                Calendar calendar = Calendar.getInstance();
                //统计昨日
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
                Date reportTime = calendar.getTime();

                doReport(reportTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            IS_REPORT = false;
        }
    }

    public void doReport(Date reportTime) {
        YwUserRoom ywUserRoom = new YwUserRoom();
        ywUserRoom.setSign("0"); //只统计非签约主播
        List<YwUserRoom> rooms = ywUserRoomService.getYwUserRoomList(ywUserRoom);
        // Map<Float,> map = new TreeMap<String, String>();

        int startHour = 8;
        int endHour = 24;
        String value = SysOptionServiceImpl.getValue("LANSHA.ROOM.VALID.TIME");
        if (org.apache.commons.lang.StringUtils.isNotEmpty(value)) {
            String[] times = value.split(",");
            if (times.length > 2) {
                startHour = Integer.valueOf(times[0]);
                endHour = Integer.valueOf(times[1]);
            }
        }
        //薪资列表
        List<LanshaRoomRanking> salaryList = new ArrayList<LanshaRoomRanking>();
        for (YwUserRoom room : rooms) {
            LanshaRoomRanking ranking = calScore(room, reportTime, startHour, endHour);
            if (ranking == null) {
                continue;
            }
            LanshaRoomRanking temp = new LanshaRoomRanking();

            temp.setCreateTime(ranking.getCreateTime());
            temp.setRoomIdint(ranking.getRoomIdint());
            List<LanshaRoomRanking> rankings = lanshaRoomRankingService.getLanshaRoomRankingList(temp);
            if (!rankings.isEmpty()) {
                //有历史记录，需要删除
                List<String> ids = new ArrayList<String>();
                for (LanshaRoomRanking t : rankings) {
                    ids.add(t.getId());
                }
                lanshaRoomRankingService.delete(ids.toArray(new String[]{}));
            }

            salaryList.add(ranking);

        }
        //工资结算
        try {
            calSalary(salaryList, reportTime);
        } catch (Exception e) {
            //结算工资失败不影响日数据汇聚
            e.printStackTrace();
            LogUtil.log("结算工资错误", e);
        }
        //刷新缓存
        lanshaRoomRankingService.clear();
    }


    private void calSalary(List<LanshaRoomRanking> salaryList, Date reportTime) {
        String day = DateUtils.format(reportTime);
        //先删除reportTime 的历史数据
        ywUserRoomDayDataService.deleteByDay(DateUtils.toDate(day));
        if (!CollectionUtils.isEmpty(salaryList)) {
            Collections.sort(salaryList, new Comparator<LanshaRoomRanking>() {
                @Override
                public int compare(LanshaRoomRanking o1, LanshaRoomRanking o2) {
                    return o2.getScore().compareTo(o1.getScore());
                }

            });


            String limit = SysOptionServiceImpl.getValue("LANSHA.ROOM.RANKING.MAX");
            Integer maxLimit = Integer.MAX_VALUE;
            if (StringUtils.isNotBlank(limit)) {
                maxLimit = Integer.parseInt(limit);
            }

//            Map<Float,Integer> map=new HashMap<Float, Integer>();
//            for(int i = 0; i < size; i++)
//            {
//                LanshaRoomRanking rank = salaryList.get(i);
//                if(!map.containsKey(rank.getScore()))
//                {
//                    map.put(rank.getScore(),i+1);
//                }
//            }
            int size = salaryList.size();
            for (int i = 0; i < size; i++) {
                LanshaRoomRanking rank = salaryList.get(i);
                lanshaRoomRankingService.save(rank);
                YwUserRoomDayData dayData = new YwUserRoomDayData();
                dayData.setRankingId(rank.getId());
                dayData.setUserId(rank.getUserId());
                dayData.setRoomId(rank.getRoomIdint() + "");
                dayData.setScore(rank.getScore());
                dayData.setRanking(i + 1);
                int validHours = (rank.getVaildLiveTime() / 60) < maxLimit ? rank.getVaildLiveTime() / 60 : maxLimit;
                dayData.setLiveTime(rank.getVaildLiveTime());
                dayData.setSalary(dayData.getPayStandard() * validHours);
                dayData.setFocusOn(rank.getRelationNumber());
                dayData.setFocusOff(rank.getCancelNumber());
                dayData.setBonus(0f);
                dayData.setForfeit(0f);
                dayData.setDay(rank.getCreateTime());
                ywUserRoomDayDataService.save(dayData);

            }
        }
    }


    /**
     * 计算房间某一天的积分
     *
     * @param room
     * @param reportTime
     * @return
     */
    public LanshaRoomRanking calScore(YwUserRoom room, Date reportTime, Integer startHour, Integer endHour) {
        //直播时长
        LanshaLiveHistory entity = new LanshaLiveHistory();
        entity.setRoomId(room.getId());
        List<LanshaLiveHistory> histories = lanshaLiveHistoryDao.getLanshaLiveHistoryPage(entity, DateUtils.getStartDate(reportTime), DateUtils.getEndDate(reportTime), null);
        if (histories.isEmpty()) {
            //当天没有直播过，所以不需要统计
            return null;
        }
        //积分统计
        LanshaRoomRanking ranking = new LanshaRoomRanking();

        //统计直播时长
        int liveTime = 0;//有效时长
        int totalLiveTime = 0;//总时长
        for (LanshaLiveHistory history : histories) {
            int t = 0;
            long n = 0;
            if (history.getEndTime() == null || history.getEndTime().getDay() != history.getCreateTime().getDay()) {
                //设置为当天的24点
                history.setEndTime(DateUtils.getEndDate(reportTime));

            }
            n = setEffectiveTime(history.getStartTime(), history.getEndTime(), startHour, endHour);
            if (history.getLength() == null) {
                if (history.getEndTime() == null || history.getEndTime().getDay() != history.getCreateTime().getDay()) {
                    //设置为当天的24点
                    history.setEndTime(DateUtils.getEndDate(reportTime));
                }
                if (history.getEndTime().getDay() != history.getStartTime().getDay()) {
                    history.setStartTime(DateUtils.getDateStart(reportTime));
                }
                //计算直播时长
                t = (int) (history.getEndTime().getTime() - history.getCreateTime().getTime()) / 1000;
                if (t <= 0) {
                    //时长错误
                    continue;
                }
            } else {
                t = history.getLength();
            }
            totalLiveTime += t;
            liveTime += n;
        }
        ranking.setVaildLiveTime(liveTime / 60);//有效直播时长
        ranking.setLiveTime(totalLiveTime / 60); // 总时长
        //每日虾米数量
        LanshaGiftUser gift = new LanshaGiftUser();
        gift.setAnchorId(room.getUid());
        gift.setGiftId(LanshaConstant.GIFT_ID);
        gift.setCreateTime(reportTime);
        int n = lanshaGiftUserDao.getSumGiftNumber(gift);
        ranking.setXiamiNumber(n);
        //每日鲜花数
        LanshaGiftUser giftFlower = new LanshaGiftUser();
        giftFlower.setAnchorId(room.getUid());
        giftFlower.setGiftId(LanshaConstant.GIFT_ID_THREE);
        giftFlower.setCreateTime(reportTime);
        int f = lanshaGiftUserDao.getSumGiftNumber(giftFlower);
        ranking.setFlowerNubmer(f);
        //每日日票数
        giftFlower.setAnchorId(room.getUid());
        giftFlower.setGiftId(LanshaConstant.GIFT_ID_FIVE);
        giftFlower.setCreateTime(reportTime);
        int tn = lanshaGiftUserDao.getSumGiftNumber(giftFlower);
        ranking.setTicketNumber(tn);

        //每日关注数量
        YwUserRoomRelation relation = new YwUserRoomRelation();
        relation.setCreateTime(reportTime);
        relation.setRoomId(room.getId());
        relation.setStatus(LanshaConstant.STATUS_VAILD);
        int r = ywUserRoomRelationDao.getSumRelationNumber(relation);
        ranking.setRelationNumber(r);
        //每日取消关注数量
        relation.setStatus(LanshaConstant.STATUS_DELETE);
        int cr = ywUserRoomRelationDao.getSumRelationNumber(relation);
        ranking.setCancelNumber(cr);

        //观众人数
        YwUserRoomHistory history = new YwUserRoomHistory();
        history.setRoomId(room.getId());
        history.setCreateTime(reportTime);
        history.setTypeId(LanshaConstant.GIFT_ID);
        int h = ywUserRoomHistoryDao.getSumUserNumber(history);
        ranking.setAudience(h);

        //积分统计
        ranking.setCreateTime(reportTime);
        ranking.setRoomIdint(room.getIdInt());
        ranking.setUserId(room.getUid());
        ranking.setScore(ranking.getGrade());
        return ranking;
    }

    /**
     * 有效时计算
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private Long setEffectiveTime(Date startTime, Date endTime, Integer startHour, Integer endHour) {

        if (startTime.getDay() != endTime.getDay()) {
            startTime = DateUtils.getDateStart(endTime);
        }
        // 设定有效开始时间
        Date effectiveStartTime = setMinute(startTime, startHour);
        // 设定有效结束时间
        Date effectiveEndTime = setMinute(endTime, endHour);
        if (effectiveStartTime.getTime() > endTime.getTime() || startTime.getTime() > effectiveEndTime.getTime()) {
            return 0l;
        }

        // startTime在effectiveStartTime之前
        if (startTime.getTime() < effectiveStartTime.getTime()) {
            startTime = effectiveStartTime;
        }
        // endTime在effectiveEndTime之后
        if (endTime.getTime() > effectiveEndTime.getTime()) {
            endTime = effectiveEndTime;
        }

        return DateUtils.dateDiff(startTime, endTime, "yyyy-MM-dd HH:mm:ss", "");
    }

    /**
     * @param date
     * @param time
     * @return
     * @Description: 给指定日期设置指定小时
     */
    private Date setMinute(Date date, Integer time) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, time);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
}
