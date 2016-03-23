package com.yaowang.lansha.action.web;

import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.*;
import com.yaowang.lansha.service.*;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhy
 * @ClassName: AnchorRankAction
 * @Description: 主播排行
 * @date 2016-01-27 上午17:41:28
 */
public class AnchorRankAction extends LanshaBaseAction {
    private static final long serialVersionUID = 1L;
    @Resource
    private LanshaRoomRankingService lanshaRoomRankingService;
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private YwUserRoomDayDataService ywUserRoomDayDataService;
    @Resource
    private YwUserRoomContractService ywUserRoomContractService;

    @Resource
    private LanshaLiveHistoryService lanshaLiveHistoryService;
    private List<YwUserRoom> rooms;
    private Integer myRankScore = null;

    private String playTime;

    private String totalPlayTime;

    YwUserRoomDayData dayData;

    private Integer sign = 0;

    private Integer time = 1;

    private String salary = "0";
    //累计收益
    private Float totalSalary = 0.0f;
    //奖金
    private Float totalBonus = 0.0f;
    //罚金
    private Float totalForfeit = 0.0f;
    //最终收益
    private Float lastSalary = 0.0f;

    private String sortInfo = "暂无排名";

    private Map<Integer, String> timeData;

    public static final Integer VALID = 3600 * 4;

    public static Map<Integer, String> getData() throws ParseException {
        Map<Integer, String> SORT_MAP = new LinkedHashMap<Integer, String>();
        SORT_MAP.put(1, DateUtils.format(DateUtils.getPlusDate(new Date(), -1)));
        SORT_MAP.put(2, DateUtils.format(DateUtils.getPlusDate(new Date(), -2)));
        SORT_MAP.put(3, DateUtils.format(DateUtils.getPlusDate(new Date(), -3)));
        SORT_MAP.put(4, DateUtils.format(DateUtils.getPlusDate(new Date(), -4)));
        SORT_MAP.put(5, DateUtils.format(DateUtils.getPlusDate(new Date(), -5)));
        SORT_MAP.put(6, DateUtils.format(DateUtils.getPlusDate(new Date(), -6)));
        SORT_MAP.put(7, DateUtils.format(DateUtils.getPlusDate(new Date(), -7)));
        return SORT_MAP;


    }


    public String anchorRank() throws IOException {
        YwUser ywUser = getUserLogin();
        if (ywUser == null) {
            addActionError(getFailedLogin());
            return "msg";
        }
        rooms = lanshaRoomRankingService.getUserRooms(20);
        if (CollectionUtils.isNotEmpty(rooms)) {
            ywUserRoomService.setUserName((List<YwUserRoom>) rooms);
        }
        myRankScore = lanshaRoomRankingService.getUserRanking(ywUser.getId());
        return SUCCESS;
    }

    /**
     * 积分排行
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public String rank() throws IOException, ParseException {
        YwUser ywUser = getUserLogin();
        timeData = getData();
        String date = timeData.get(time);
        Date yestday = DateUtils.getDate(getNow());
        YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomByUid(ywUser.getId());
        if (ywUserRoom != null) {
            sign = Integer.valueOf(ywUserRoom.getSign());
            YwUserRoomDayData entity = new YwUserRoomDayData();
            entity.setDay(DateUtils.toDate(date));
            entity.setUserId(ywUser.getId());
            list = ywUserRoomDayDataService.getYwUserRoomDayDataSort(entity);
            YwUserRoomDayData sortDayData = ywUserRoomDayDataService.getYwUserRoomDayData(entity);
            if (sortDayData != null) {
                sortInfo = "第" + sortDayData.getRanking() + "名";
            }
            if (sign == 0) {
                entity.setDay(DateUtils.getDateStart(yestday));

                dayData = ywUserRoomDayDataService.getYwUserRoomDayData(entity);
                if (dayData != null) {
                    salary = dayData.getSalary() + "";
                }
                //计算本月奖金 罚金
                int year = DateUtils.getYear(getNow());
                int nowMonth = DateUtils.getMonth(getNow());
                startTime = DateUtils.getFirstDay(year, nowMonth);
                endTime = DateUtils.getLastDay(year, nowMonth);
                YwUserRoomDayData ywUserRoomDayData = ywUserRoomDayDataService.getYwUserRoomDataSum(entity, startTime, endTime);
                if (ywUserRoomDayData != null) {
                    totalSalary = ywUserRoomDayData.getSalary();
                    totalBonus = ywUserRoomDayData.getBonus();
                    totalForfeit=ywUserRoomDayData.getForfeit();
                    lastSalary=ywUserRoomDayData.getLastSalary();

                }
            } else if (sign == 1) {
                // list=getByDate(ywUser.getId(),date);
                YwUserRoomContract ywUserRoomContract = ywUserRoomContractService.getYwUserRoomContractById(ywUserRoom.getUid());
                if (ywUserRoomContract != null) {
                    salary = ywUserRoomContract.getSalary() + "";
                }
            }

            int arr[] = getLiveDetailReport(ywUserRoom.getId());
            int totalTimeLength = arr[0];
            int timeLength = arr[1];
            //非签约主播有效时长标准上限4小时
            if (sign == 0 && timeLength > VALID) {
                playTime = DateUtils.secondToTime(VALID);

            } else {
                playTime = DateUtils.secondToTime(timeLength);
            }
            totalPlayTime = DateUtils.secondToTime(totalTimeLength);

        }


        return SUCCESS;
    }

    private List<LanshaRoomRanking> getByDate(String userId, String date) throws ParseException {
        List<LanshaRoomRanking> listSort = null;
        Date yestday = DateUtils.getDate(getNow());
        List<LanshaRoomRanking> list = lanshaRoomRankingService.getLanshaRoomRankingListSort(DateUtils.getStartDate(yestday));
        lanshaRoomRankingService.getLanshaRoomRankingListSort(DateUtils.getStartDate(getNow()));
        //如果查询时间等于昨天
        if (date.equals(DateUtils.format(yestday))) {
            listSort = list;
        } else {
            listSort = lanshaRoomRankingService.getLanshaRoomRankingListSort(DateUtils.toDate(date));
        }

        int sort = 0;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            LanshaRoomRanking lanshaRoomRanking = list.get(i);
            if (lanshaRoomRanking.getUserId().equals(userId)) {
                sort = i + 1;
            }
        }
        if (sort > 0) {
            sortInfo = "第" + sort + "名";
        }
        return listSort;
    }

    /**
     * @return
     * @throws ParseException
     * @Description:
     */
    private int[] getLiveDetailReport(String roomId) throws ParseException {

        // 查询条件
        LanshaLiveHistory liveHistory = new LanshaLiveHistory();
        liveHistory.setRoomId(roomId);
        Integer timeLength = 0;//有效播放时长
        Integer totalTimeLength = 0;//总播放时长
        int startHour = 8;
        int endHour = 24;
        String value = SysOptionServiceImpl.getValue("LANSHA.ROOM.VALID.TIME");
        if (StringUtils.isNotEmpty(value)) {
            String[] times = value.split(",");
            if (times.length > 2) {
                startHour = Integer.valueOf(times[0]);
                endHour = Integer.valueOf(times[1]);
            }
        }
        // 播放记录
        List<Map<String, Object>> listMapLiveHistoryReport = lanshaLiveHistoryService.listMapLiveHistoryReport(liveHistory, DateUtils.getDateStart(getNow()), DateUtils.getEndDate(getNow()));
        if (CollectionUtils.isNotEmpty(listMapLiveHistoryReport)) {

            for (Map<String, Object> map : listMapLiveHistoryReport) {

                // 开始时间、结束时间不为空
                if (map.get("startTime") != null && map.get("endTime") != null) {
                    // 计算有效播放时长
                    Long playTime = setEffectiveTime(DateUtils.pasetime(map.get("startTime").toString()), DateUtils.pasetime(map.get("endTime").toString()), startHour, endHour);
                    // 计算播放总时长
                    Long totalPlayTime = setEffectiveTime(DateUtils.pasetime(map.get("startTime").toString()), DateUtils.pasetime(map.get("endTime").toString()), 0, 24);
                    timeLength += Integer.valueOf(playTime.toString());
                    totalTimeLength += Integer.valueOf(totalPlayTime.toString());
                }
            }
        }
        return new int[]{totalTimeLength, timeLength};
    }

    /**
     * @param startTime 播放开始时间
     * @param endTime   播放结束时间
     * @return
     * @Description: 计算有效时长
     */
    private Long setEffectiveTime(Date startTime, Date endTime, int startHour, int endHour) throws ParseException {
        // 设定有效开始时间
        Date effectiveStartTime = DateUtils.setMinute(getNow(), startHour);
        // 设定有效结束时间
        Date effectiveEndTime = DateUtils.setMinute(getNow(), endHour);

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


    public List<YwUserRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<YwUserRoom> rooms) {
        this.rooms = rooms;
    }

    public Integer getMyRankScore() {
        return myRankScore;
    }

    public void setMyRankScore(Integer myRankScore) {
        this.myRankScore = myRankScore;
    }

    public YwUserRoomDayData getDayData() {
        return dayData;
    }

    public void setDayData(YwUserRoomDayData dayData) {
        this.dayData = dayData;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Map<Integer, String> getTimeData() {
        return timeData;
    }

    public void setTimeData(Map<Integer, String> timeData) {
        this.timeData = timeData;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setTotalPlayTime(String totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSortInfo() {
        return sortInfo;
    }

    public void setSortInfo(String sortInfo) {
        this.sortInfo = sortInfo;
    }

    public Float getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Float totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Float getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(Float totalBonus) {
        this.totalBonus = totalBonus;
    }

    public Float getTotalForfeit() {
        return totalForfeit;
    }

    public void setTotalForfeit(Float totalForfeit) {
        this.totalForfeit = totalForfeit;
    }

    public Float getLastSalary() {
        return lastSalary;
    }

    public void setLastSalary(Float lastSalary) {
        this.lastSalary = lastSalary;
    }
}
