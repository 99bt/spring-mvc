package com.yaowang.lansha.action.admin;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.action.BasePageAction;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.util.CSVUtils;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoomContract;
import com.yaowang.lansha.service.YwUserRoomContractService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.upload.DownloadUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-2-29
 * Time: 上午9:51
 * To change this template use File | Settings | File Templates.
 */
public class YwUserRoomContractAction extends BasePageAction {
    @Resource
    YwUserRoomContractService ywUserRoomContractService;
    @Resource
    YwUserService ywUserService;
    @Resource
    private YwUserRoomService ywUserRoomService;

    private String roomId;
    private String userName;
    private String mobile;

    private String userId;

    private YwUserRoomContract entity;

    private Integer examine = 3;


    public String list() {
        entity = new YwUserRoomContract();
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setStatus(LanshaConstant.STATUS_VAILD);
        if (StringUtils.isNotEmpty(roomId)) {
            entity.setRoomId(roomId);
        }
        if (StringUtils.isNotEmpty(userName)) {
            YwUser ywUser = ywUserService.getYwusersByNickname(userName);
            if (ywUser != null) {
                entity.setUserId(ywUser.getId());
            } else {
                return SUCCESS;
            }
        }
        if (StringUtils.isNotEmpty(mobile)) {
            YwUser ywUser = ywUserService.getYwusersByTelphone(mobile);
            if (ywUser != null) {
                entity.setUserId(ywUser.getId());
            } else {
                return SUCCESS;
            }
        }
        if (examine < 3) {
            entity.setExamine(examine);
        }
        list = getList(getPageDto());

        return SUCCESS;
    }

    public void export() throws ParseException {
        entity = new YwUserRoomContract();
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setStatus(LanshaConstant.STATUS_VAILD);
        if (StringUtils.isNotEmpty(roomId)) {
            entity.setRoomId(roomId);
        }
        if (StringUtils.isNotEmpty(userName)) {
            YwUser ywUser = ywUserService.getYwusersByNickname(userName);
            if (ywUser != null) {
                entity.setUserId(ywUser.getId());
            }

        }
        if (StringUtils.isNotEmpty(mobile)) {
            YwUser ywUser = ywUserService.getYwusersByTelphone(mobile);
            if (ywUser != null) {
                entity.setUserId(ywUser.getId());
            }

        }
        if (examine < 3) {
            entity.setExamine(examine);
        }
        List<Object> fieldNames = new ArrayList<Object>();
        List<String> propertyNames = new ArrayList<String>();
        fieldNames.add("房间Id");
        propertyNames.add("roomId");
        fieldNames.add("手机号");
        propertyNames.add("mobile");
        fieldNames.add("真实姓名");
        propertyNames.add("userName");
        fieldNames.add("游戏名称");
        propertyNames.add("gameName");
        fieldNames.add("签约时间");
        propertyNames.add("timeFrame");
        fieldNames.add("直播时长");
        propertyNames.add("timeTarget");
        fieldNames.add("有效天数");
        propertyNames.add("vaildDays");
        fieldNames.add("初始薪资");
        propertyNames.add("salary");
        fieldNames.add("初始指标");
        propertyNames.add("ticketTarget");
        fieldNames.add("负责人");
        propertyNames.add("manager");
        fieldNames.add("考核");
        propertyNames.add("examineName");
        fieldNames.add("备注");
        propertyNames.add("remark");

        // 获取导出数据
        List<YwUserRoomContract> exportList = getList(null);
        Pattern p = Pattern.compile("<([^>]*)>"); //过滤html标签
        for (YwUserRoomContract item : exportList) {
            if (item.getExamine() == 1) {
                item.setExamineName("参加考核");

            } else {
                item.setExamineName("不参加考核");
            }

        }
        String filename = DateUtils.format(getNow(), "yyyyMMddHHmmss");
        File file = CSVUtils.createCSVFile(fieldNames, propertyNames, exportList, getLocalTempPath(), filename);
        DownloadUtil.downloadCSV("签约主播信息-" + DateUtils.formatHms(getNow()) + ".csv", file);
    }

    public List<YwUserRoomContract> getList(PageDto page) {
        List<YwUserRoomContract> list = ywUserRoomContractService.getYwUserRoomContractPage(entity, page);
        if (CollectionUtils.isNotEmpty(list)) {
            ywUserRoomContractService.setGameName(list);
            ywUserRoomContractService.setUserName(list);
        }
        return list;
    }

    /**
     * @return
     * @Description: 详情
     */
    public String detail() {
        if (StringUtils.isNotEmpty(userId)) {
            entity = ywUserRoomContractService.getYwUserRoomContractById(userId);
            examine = entity.getExamine();
            setInfo(entity);
        } else {
            entity = new YwUserRoomContract();
            examine = 1;
        }
        return SUCCESS;
    }

    /**
     * @Description: 获取房间信息
     */
    public String search() {

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(roomId)) {
            data = ywUserRoomService.getByRoomIdint(Integer.valueOf(roomId));
            List<YwUserRoomContract> rList = new ArrayList<YwUserRoomContract>();
            for (Map<String, Object> map : data) {
                entity = new YwUserRoomContract();
                entity.setRoomId(map.get("id") + "");
                entity.setUserId(map.get("userId") + "");
                entity.setMobile(map.get("mobile") + "");
                entity.setUserName(map.get("username") + "");
                entity.setGameName(map.get("name") + "");
                rList.add(entity);
            }
            list = rList;
        }


        return SUCCESS;
    }

    /**
     * @return
     * @Description: 保存
     */
    public String save() {
        try {
            validateInfo(entity);
        } catch (Exception e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        entity.setVaildDays(20);
        ywUserRoomContractService.saveOrUpdate(entity);
        list();
        return SUCCESS;
    }


    /**
     * @return
     * @Description: 删除
     */
    public String delete() {
        if (ids == null || ids.length < 1) {
            addActionError("请选择一条记录");
            return SUCCESS;
        }
        ywUserRoomContractService.del(ids);
        list();
        return SUCCESS;
    }

    /**
     * 设置用户信息
     *
     * @param ywUserRoomContract
     */
    private void setInfo(YwUserRoomContract ywUserRoomContract) {
        List<YwUserRoomContract> rList = new ArrayList<YwUserRoomContract>();
        rList.add(ywUserRoomContract);
        ywUserRoomContractService.setUserName(rList);
        ywUserRoomContractService.setGameName(rList);
    }

    private void validateInfo(YwUserRoomContract entity) throws Exception {
        if (StringUtils.isBlank(entity.getRoomId())) {
            throw new RuntimeException("房间不存在");
        }

    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public YwUserRoomContract getEntity() {
        return entity;
    }

    public void setEntity(YwUserRoomContract entity) {
        this.entity = entity;
    }

    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
    }
}
