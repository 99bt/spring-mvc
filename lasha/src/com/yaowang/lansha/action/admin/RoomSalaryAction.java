package com.yaowang.lansha.action.admin;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.util.CSVUtils;
import com.yaowang.lansha.entity.YwRoomMonthSettlement;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoomDayData;
import com.yaowang.lansha.service.YwRoomMonthSettlementService;
import com.yaowang.lansha.service.YwUserRoomDayDataService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.upload.DownloadUtil;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-4   主播薪资报表
 * Time: 下午12:57
 * To change this template use File | Settings | File Templates.
 */
public class RoomSalaryAction extends BasePageAction {
    @Resource
    YwUserRoomDayDataService ywUserRoomDayDataService;
    @Resource
    YwRoomMonthSettlementService ywRoomMonthSettlementService;
    @Resource
    private YwUserService ywUserService;

    private String roomId;

    private String mobile;

    private Integer month;

    private String timeFrame;

    private String rankingId;

    private  YwUserRoomDayData ywUserRoomDayData;


    public String list() {
        int year = DateUtils.getYear(getNow());
        int nowMonth = DateUtils.getMonth(getNow());
        YwUserRoomDayData entity = new YwUserRoomDayData();
        if (month == null) {
            month = nowMonth;
        }
        if (StringUtils.isNotEmpty(mobile)) {
            YwUser ywUser = ywUserService.getYwusersByTelphone(mobile);
            if (ywUser == null) {
                return SUCCESS;
            }
            entity.setUserId(ywUser.getId());
        }
        entity.setRoomId(roomId);

        startTime = DateUtils.getFirstDay(year, month);
        endTime = DateUtils.getLastDay(year, month);
        timeFrame = DateUtils.format(startTime) + "~" + DateUtils.format(endTime);
        if (month < nowMonth) {
            YwRoomMonthSettlement ywRoomMonthSettlement = new YwRoomMonthSettlement();
            ywRoomMonthSettlement.setMonth(year + "" + month);
            ywRoomMonthSettlement.setRoomId(roomId);
            ywRoomMonthSettlement.setUserId(entity.getUserId());
            list=getListByMonth(ywRoomMonthSettlement);

        } else {

            list = getListByDay(entity,startTime,endTime,getPageDto());
        }
        return SUCCESS;
    }

    /**
     * 查询yw_room_month_settlement
     * @param entity
     * @return
     */
    private List<YwUserRoomDayData> getListByMonth(YwRoomMonthSettlement entity) {
        List<YwUserRoomDayData> dayDataList = new ArrayList<YwUserRoomDayData>();
        List<YwRoomMonthSettlement> list = ywRoomMonthSettlementService.getYwRoomMonthSettlementPage(entity, getPageDto());
        YwUserRoomDayData dayData = null;
        for (YwRoomMonthSettlement monthSettlement : list) {
            dayData = new YwUserRoomDayData();
            dayData.setLiveTime(monthSettlement.getLiveTime());
            dayData.setRoomId(monthSettlement.getRoomId());
            dayData.setSalary(monthSettlement.getDue());
            dayData.setForfeit(monthSettlement.getForfeit());
            dayData.setBonus(monthSettlement.getBonus());
            dayDataList.add(dayData);
        }
        ywUserRoomDayDataService.setMobile(dayDataList);
        return dayDataList;
    }

    private List<YwUserRoomDayData> getListByDay(YwUserRoomDayData entity,Date startTime, Date endTime, PageDto page) {
        List<YwUserRoomDayData> dayDataList = ywUserRoomDayDataService.getYwUserRoomDayDataTotalList(entity, startTime, endTime,page);
        return dayDataList;
    }

    /**
     * 工资详情
     *
     * @return
     */
    public String detail() {
        YwUserRoomDayData entity = new YwUserRoomDayData();
        if (StringUtils.isNotEmpty(id)) {
            entity.setRoomId(id);
        }
        list = ywUserRoomDayDataService.getYwUserRoomDayDataList(entity, startTime, endTime);
        return SUCCESS;
    }

    /**
     * 编辑
     * @return
     */
    public String edit() {
        ywUserRoomDayData=ywUserRoomDayDataService.getYwUserRoomDayDataById(rankingId);

        return SUCCESS;
    }

    /**
     * 更新
     * @return
     */
    public String update() {

        ywUserRoomDayDataService.updateInfo(ywUserRoomDayData);
        detail();
        return SUCCESS;
    }


    public void listExport() {
        List<YwUserRoomDayData> list=null;
        int year = DateUtils.getYear(getNow());
        int nowMonth = DateUtils.getMonth(getNow());
        YwUserRoomDayData entity = new YwUserRoomDayData();
        if (month == null) {
            month = nowMonth;
        }
        if (StringUtils.isNotEmpty(mobile)) {
            YwUser ywUser = ywUserService.getYwusersByTelphone(mobile);
            if (ywUser != null) {
                entity.setUserId(ywUser.getId());
            }
        }
        entity.setRoomId(roomId);
        startTime = DateUtils.getFirstDay(year, month);
        endTime = DateUtils.getLastDay(year, month);
        timeFrame = DateUtils.format(startTime) + "~" + DateUtils.format(endTime);
        if (month < nowMonth) {
            YwRoomMonthSettlement ywRoomMonthSettlement = new YwRoomMonthSettlement();
            ywRoomMonthSettlement.setMonth(year + "" + month);
            ywRoomMonthSettlement.setRoomId(roomId);
            ywRoomMonthSettlement.setUserId(entity.getUserId());
            list=getListByMonth(ywRoomMonthSettlement);

        } else {

            list = getListByDay(entity,startTime,endTime,null);
        }
        List<Object> fieldNames = new ArrayList<Object>();
        List<String> propertyNames = new ArrayList<String>();
        fieldNames.add("日期");
        propertyNames.add("dateTime");
        fieldNames.add("排名");
        propertyNames.add("sort");
        fieldNames.add("房间Id");
        propertyNames.add("roomId");
        fieldNames.add("手机号");
        propertyNames.add("mobile");
        fieldNames.add("有效直播时长");
        propertyNames.add("playInfo");
        fieldNames.add("应得");
        propertyNames.add("salary");
        fieldNames.add("奖金");
        propertyNames.add("bonus");
        fieldNames.add("罚金 ");
        propertyNames.add("forfeit");
        fieldNames.add("实得 ");
        propertyNames.add("lastSalary");
        int i=1;
        for (YwUserRoomDayData dayData : list) {
            dayData.setSort(i);
            dayData.setPlayInfo(DateUtils.secondToTime(dayData.getLiveTime()*60));
            dayData.setDateTime(timeFrame);
            i++;
        }
        String filename = DateUtils.format(getNow(), "yyyyMMddHHmmss");
        File file = CSVUtils.createCSVFile(fieldNames, propertyNames, list, getLocalTempPath(), filename);
        DownloadUtil.downloadCSV("工资月报表-" + DateUtils.formatHms(getNow()) + ".csv", file);
    }

    public void detailExport() {
        YwUserRoomDayData entity = new YwUserRoomDayData();
        if (StringUtils.isNotEmpty(id)) {
            entity.setRoomId(id);
        }
        List<YwUserRoomDayData> list = ywUserRoomDayDataService.getYwUserRoomDayDataList(entity, startTime, endTime);
        List<Object> fieldNames = new ArrayList<Object>();
        List<String> propertyNames = new ArrayList<String>();
        fieldNames.add("日期");
        propertyNames.add("dateTime");
        fieldNames.add("排名");
        propertyNames.add("ranking");
        fieldNames.add("房间Id");
        propertyNames.add("roomId");
        fieldNames.add("手机号");
        propertyNames.add("mobile");
        fieldNames.add("有效直播时长");
        propertyNames.add("playInfo");
        fieldNames.add("薪资阶段(元/小时)");
        propertyNames.add("payStandard");
        fieldNames.add("应得");
        propertyNames.add("salary");
        fieldNames.add("奖金");
        propertyNames.add("bonus");
        fieldNames.add("罚金 ");
        propertyNames.add("forfeit");
        fieldNames.add("实得 ");
        propertyNames.add("lastSalary");
        for (YwUserRoomDayData dayData : list) {
            dayData.setPlayInfo(DateUtils.secondToTime(dayData.getLiveTime()));
            dayData.setDateTime(DateUtils.format(dayData.getDay()));
        }
        String filename = DateUtils.format(getNow(), "yyyyMMddHHmmss");
        File file = CSVUtils.createCSVFile(fieldNames, propertyNames, list, getLocalTempPath(), filename);
        DownloadUtil.downloadCSV("工资详情报表记录-" + DateUtils.formatHms(getNow()) + ".csv", file);
    }

    public String getRankingId() {
        return rankingId;
    }

    public void setRankingId(String rankingId) {
        this.rankingId = rankingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public YwUserRoomDayData getYwUserRoomDayData() {
        return ywUserRoomDayData;
    }

    public void setYwUserRoomDayData(YwUserRoomDayData ywUserRoomDayData) {
        this.ywUserRoomDayData = ywUserRoomDayData;
    }
}
