package com.yaowang.lansha.action.admin;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.common.util.CSVUtils;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.dao.LanshaGiftUserDao;
import com.yaowang.lansha.entity.LanshaLiveHistory;
import com.yaowang.lansha.entity.LanshaRoomRanking;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.LanshaLiveHistoryService;
import com.yaowang.lansha.service.LanshaRoomRankingService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;
import com.yaowang.util.upload.DownloadUtil;

/**
 * @author tandingbo
 * @ClassName: LiveReportAction
 * @Description: TODO(直播报表)
 * @date 2015年12月18日 上午11:08:28
 */
public class LiveReportAction extends LanshaBaseAction {
    private static final long serialVersionUID = -665682706617491830L;

    @Resource
    private LanshaLiveHistoryService lanshaLiveHistoryService;
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private LanshaRoomRankingService lanshaRoomRankingService;
    @Resource
    private LanshaGiftUserDao lanshaGiftUserDao;

    private String roomId;// 房间号
    private String startHour;// 开始时间
    private String endHour;// 开始时间
    private String timeFrame;
    private Integer examine = 3;
    private Integer effective = 30;

    private String sort = "one";

    /**
     * @return
     * @throws ParseException
     * @Description: 列表直播报表
     */
    public String liveReport() throws ParseException {
        list = getListUserRoom();
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    private List<LanshaRoomRanking> getDataList() throws ParseException {
        LanshaRoomRanking entity = new LanshaRoomRanking();
        List<LanshaRoomRanking> list;
        Date now = DateUtils.getDate(getNow());
        // 开始日期
        if (startTime == null) {
            startTime = now;
        }
        if (endTime == null) {
            endTime = now;
        }
        // 开始时间
        if (StringUtils.isBlank(startHour)) {
            startHour = "08";
        }
        // 结束时间
        if (StringUtils.isBlank(endHour)) {
            endHour = "24";
        }
        if (examine < 3) {
            entity.setSign(examine);
        }
        if (StringUtils.isNotBlank(roomId)) {
            entity.setRoomIdint(Integer.valueOf(roomId));
        }

        // 有效时长标准
        String value = SysOptionServiceImpl.getValue("LANSHA.ROOM.RANKING.EFFECTIVE");
        if (StringUtils.isNotEmpty(value)) {
            effective = Integer.valueOf(value);
        }
        timeFrame = DateUtils.format(startTime) + "~" + DateUtils.format(endTime);
        list = lanshaRoomRankingService.getAllLanshaRoomRanking(entity, startTime, endTime, getPageDto());
        lanshaRoomRankingService.setVaildDays(list, effective);
        lanshaRoomRankingService.setGameName(list);

        Collections.sort(list, new Comparator<LanshaRoomRanking>() {
            @Override
            public int compare(LanshaRoomRanking b1, LanshaRoomRanking b2) {
                if (sort.equals("one")) {
                    return b2.getTicketNumber().compareTo(b1.getTicketNumber());
                } else if (sort.equals("two")) {
                    return b2.getXiamiNumber().compareTo(b1.getXiamiNumber());
                } else if (sort.equals("three")) {
                    return b2.getFlowerNubmer().compareTo(b1.getFlowerNubmer());
                } else if (sort.equals("four")) {
                    return b2.getRelationNumber().compareTo(b1.getRelationNumber());
                } else if (sort.equals("five")) {
                    return b2.getLiveTime().compareTo(b1.getLiveTime());
                } else {
                    return b2.getVaildDays().compareTo(b1.getVaildDays());
                }

            }

        });
        return list;
    }

    /**
     * @return
     * @throws ParseException
     * @Description: 直播报表数据辅助类
     */
    private List<YwUserRoom> getListUserRoom() throws ParseException {
        // 查询条件
        LanshaLiveHistory liveHistory = new LanshaLiveHistory();
        Date now = DateUtils.getDate(getNow());
        // 开始日期
        if (startTime == null) {
            startTime = now;
        }
        if (endTime == null) {
            endTime = now;
        }
        // 开始时间
        if (StringUtils.isBlank(startHour)) {
            startHour = "08";
        }
        // 结束时间
        if (StringUtils.isBlank(endHour)) {
            endHour = "24";
        }
        if (examine < 3) {
            liveHistory.setSign(examine);
        }
        // 查询日期范围
        timeFrame = DateUtils.format(startTime) + "~" + DateUtils.format(endTime);

        if (StringUtils.isNotBlank(roomId)) {
            liveHistory.setIdInt(Integer.valueOf(roomId));
        }
        // 播放记录
        List<LanshaLiveHistory> listLiveHistory = lanshaLiveHistoryService.getLanshaLiveHistoryForPage(liveHistory, startTime, endTime, null);
        if (CollectionUtils.isNotEmpty(listLiveHistory)) {
            // 房间对应有效播放时长
            Map<String, Long> map = new HashMap<String, Long>();
            Map<String, YwUserRoom> result = new HashMap<String, YwUserRoom>();
            Set<String> userIds = new HashSet<String>();
            
            for (LanshaLiveHistory lanshaLiveHistory : listLiveHistory) {
                // 房间ID
                String roomId = lanshaLiveHistory.getRoomId();
                // 计算有效播放时长
                Long playTime = 0l;
                if (lanshaLiveHistory.getStartTime() != null && lanshaLiveHistory.getEndTime() != null) {
                    playTime = setEffectiveTime(lanshaLiveHistory.getStartTime(), lanshaLiveHistory.getEndTime());
                }
                if (map.containsKey(roomId)) {
                    Long totalTime = playTime + map.get(roomId);
                    map.put(roomId, totalTime);
                } else {
                    YwUserRoom userRoom=new YwUserRoom();
                    userRoom.setRoomId(roomId);
                    userRoom.setIdInt(lanshaLiveHistory.getIdInt());
                    userRoom.setUid(lanshaLiveHistory.getUserId());
                    userRoom.setTimeFrame(timeFrame);
                    userRoom.setSign(lanshaLiveHistory.getSign()+"");
                    userRoom.setGameId(lanshaLiveHistory.getGameId());
                    
                    userIds.add(lanshaLiveHistory.getUserId());
                    result.put(roomId, userRoom);
                    map.put(roomId, playTime);
                }
            }
            // 计算主播收到的礼物
            Map<String, Integer> giftUserMap = lanshaGiftUserDao.getSumGiftNumber(LanshaConstant.GIFT_ID_FIVE, userIds.toArray(new String[]{}), startTime, endTime);
            
            List<YwUserRoom> list = new ArrayList<YwUserRoom>();
            for (Map.Entry<String, YwUserRoom> entry : result.entrySet()) {
                if (map.containsKey(entry.getKey())) {
                    YwUserRoom userRoom=entry.getValue();
                    userRoom.setTimeLength(Integer.valueOf(map.get(entry.getKey()).toString()));
                    if (giftUserMap.containsKey(userRoom.getUid())) {
                    	userRoom.setNumber(giftUserMap.get(userRoom.getUid()));
					}else {
						userRoom.setNumber(0);
					}
                    list.add(userRoom);
                }
            }
            ywUserRoomService.setGameName(list);
            // 设置主播昵称
            ywUserRoomService.setUserName(list);
            return list;
        }
        return new ArrayList<YwUserRoom>();
    }

    /**
     * @return
     * @throws ParseException
     * @Description: 直播报表数据详情
     */
    public String liveDetailReport() throws ParseException {
        if (StringUtils.isBlank(id)) {
            list = new ArrayList<Map<String, Object>>();
            return SUCCESS;
        }
        list = getLiveDetailReport();
        return SUCCESS;
    }

    /**
     * @return
     * @throws ParseException
     * @Description: 直播报表数据详情辅助类
     */
    private List<Map<String, Object>> getLiveDetailReport() throws ParseException {
        Date now = DateUtils.getDate(getNow());
        // 日期
        if (startTime == null) {
            startTime = now;
        }
        if (endTime == null) {
            endTime = now;
        }
        // 开始时间
        if (StringUtils.isBlank(startHour)) {
            startHour = "10";
        }
        // 结束时间
        if (StringUtils.isBlank(endHour)) {
            endHour = "22";
        }

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 查询条件
        LanshaLiveHistory liveHistory = new LanshaLiveHistory();
        liveHistory.setRoomId(id);


        // 播放记录
        List<Map<String, Object>> listMapLiveHistoryReport = lanshaLiveHistoryService.listMapLiveHistoryReport(liveHistory, startTime, endTime);
        if (CollectionUtils.isNotEmpty(listMapLiveHistoryReport)) {
            // 设置名称
            ywUserRoomService.setName(listMapLiveHistoryReport);

            for (Map<String, Object> map : listMapLiveHistoryReport) {
                Integer timeLength = 0;
                // 开始时间、结束时间不为空
                if (map.get("startTime") != null && map.get("endTime") != null) {
                    // 计算有效播放时长
                    Long playTime = setEffectiveTime(DateUtils.pasetime(map.get("startTime").toString()), DateUtils.pasetime(map.get("endTime").toString()));
                    timeLength = Integer.valueOf(playTime.toString());
                }
                if (timeLength > 0) {
                    map.put("playTime", DateUtils.secondToTime(timeLength));
                    result.add(map);
                }
            }
        }
        return result;
    }

    /**
     * @return
     * @throws ParseException
     * @Description: 直播报表数据详情辅助类
     */
    private List<Map<String, Object>> getLiveDetailReportBack() throws ParseException {
        Date now = DateUtils.getDate(getNow());
        // 日期
        if (startTime == null) {
            startTime = now;
        }
        if (endTime == null) {
            endTime = now;
        }
        // 开始时间
        if (StringUtils.isBlank(startHour)) {
            startHour = "10";
        }
        // 结束时间
        if (StringUtils.isBlank(endHour)) {
            endHour = "22";
        }

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 查询条件
        LanshaLiveHistory liveHistory = new LanshaLiveHistory();
        liveHistory.setRoomId(id);
        // 播放记录
        List<Map<String, Object>> listMapLiveHistoryReport = lanshaLiveHistoryService.listMapLiveHistoryReport(liveHistory, startTime, endTime);
        if (CollectionUtils.isNotEmpty(listMapLiveHistoryReport)) {
            // 设置名称
            ywUserRoomService.setName(listMapLiveHistoryReport);

            for (Map<String, Object> map : listMapLiveHistoryReport) {
                Integer timeLength = 0;
                // 开始时间、结束时间不为空
                if (map.get("startTime") != null && map.get("endTime") != null) {
                    // 计算有效播放时长
                    Long playTime = setEffectiveTime(DateUtils.pasetime(map.get("startTime").toString()), DateUtils.pasetime(map.get("endTime").toString()));
                    timeLength = Integer.valueOf(playTime.toString());
                }
                if (timeLength > 0) {
                    map.put("playTime", DateUtils.secondToTime(timeLength));
                    result.add(map);
                }
            }
        }
        return result;
    }

    /**
     * @throws ParseException 直播报表数据导出
     * @Description:
     */
    public void liveReportExport() throws ParseException {
        List<Object> fieldNames = new ArrayList<Object>();
        List<String> propertyNames = new ArrayList<String>();
        fieldNames.add("日期");
        propertyNames.add("timeFrame");
        fieldNames.add("房间ID");
        propertyNames.add("idInt");
        fieldNames.add("主播名称");
        propertyNames.add("nickname");
        fieldNames.add("游戏名称");
        propertyNames.add("gameName");
        fieldNames.add("有效时长");
        propertyNames.add("playTime");
        fieldNames.add("日票");
        propertyNames.add("number");
        fieldNames.add("签约 ");
        propertyNames.add("remark");

        // 获取导出数据
        List<YwUserRoom> list =getListUserRoom();
        for (YwUserRoom item : list) {
            item.setTimeFrame(timeFrame);
            if (item.getSign().equals("1")) {
                item.setRemark("签约");
            } else {
                item.setRemark("未签约");
            }

        }
        String filename = DateUtils.format(getNow(), "yyyyMMddHHmmss");
        File file = CSVUtils.createCSVFile(fieldNames, propertyNames, list, getLocalTempPath(), filename);
        DownloadUtil.downloadCSV("直播报表统计记录-" + DateUtils.formatHms(getNow()) + ".csv", file);
    }

    /**
     * @throws ParseException 直播报表详情数据导出
     * @Description:
     */
    public void liveDetailReportExport() throws ParseException {
        List<Object> fieldNames = new ArrayList<Object>();
        List<String> propertyNames = new ArrayList<String>();
        fieldNames.add("房间名称");
        propertyNames.add("userRoom.name");
        fieldNames.add("房间ID");
        propertyNames.add("userRoom.roomId");
        fieldNames.add("主播名称");
        propertyNames.add("userRoom.nickname");
        fieldNames.add("游戏名称");
        propertyNames.add("userRoom.gameName");
        fieldNames.add("有效时长");
        propertyNames.add("userRoom.playTime");
        fieldNames.add("开始时间");
        propertyNames.add("startTime");
        fieldNames.add("结束时间");
        propertyNames.add("endTime");

        // 获取导出数据
        // List<LanshaLiveHistory> list = getLiveDetailReport();
        // if (CollectionUtils.isNotEmpty(list)) {
        // String filename = DateUtils.format(getNow(), "yyyyMMddHHmmss");
        // File file = CSVUtils.createCSVFile(fieldNames, propertyNames, list,
        // getLocalTempPath(), filename);
        // DownloadUtil.downloadCSV("直播报表统计详情记录-" +
        // DateUtils.formatHms(getNow()) + ".csv", file);
        // }
    }

    /**
     * @param startTime 播放开始时间
     * @param endTime   播放结束时间
     * @return
     * @Description: 计算有效时长
     */
    private Long setEffectiveTime(Date startTime, Date endTime) {
        // 设定有效开始时间
        Date effectiveStartTime = setMinute(startTime, Integer.valueOf(startHour));
        // 设定有效结束时间
        Date effectiveEndTime = null;
        if ("00".equals(endHour)) {
            effectiveEndTime = DateUtils.getEndDate(startTime);
        } else if (24 > Integer.parseInt(endHour)) {
            effectiveEndTime = setMinute(startTime, Integer.valueOf(endHour));
        } else {
            effectiveEndTime = setMinute(endTime, Integer.valueOf(endHour));
        }

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

    /**
     * @return
     * @Description: 一天中小时数
     */
    public List<String> getListHour() {
        return Arrays.asList(DateUtils.DAYS_P_HOUR_CY);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
