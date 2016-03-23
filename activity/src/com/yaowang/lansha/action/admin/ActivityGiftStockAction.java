package com.yaowang.lansha.action.admin;

import com.yaowang.common.dao.PageDto;
import com.yaowang.common.util.CSVUtils;
import com.yaowang.entity.AdminUser;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.ActivityGift;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.ActivityUser;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.ActivityGiftService;
import com.yaowang.lansha.service.ActivityGiftStockService;
import com.yaowang.lansha.service.ActivityUserService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.service.AdminUserService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.upload.DownloadUtil;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author zhouhongyang
 * @ClassName: ActivityGiftStockAction
 * @Description: TODO(礼物发放)
 * @date 2015年12月24日 下午16:34:47
 */
public class ActivityGiftStockAction extends LanshaBaseAction {

    private static final long serialVersionUID = -1346658640088706630L;

    @Resource
    private ActivityGiftStockService activityGiftStockService;

    @Resource
    private YwUserService ywUserService;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private ActivityUserService activityUserService;

    @Resource
    private ActivityGiftService activityGiftService;

    private ActivityGiftStock entity;

    private ActivityGiftStock giftStock;

    private String status;

    private int currentIndex;//当前分页页数

    private String itemId;
    /**
     * @return
     * @Description: 礼品列表
     */
    public String list() {
        PageDto pageDto = getPageDto();
        pageDto.setRowNum(100);//2016-2-2 需求定义，显示每页100条数据
        list = getList(pageDto);
        return SUCCESS;
    }

    //数据辅助类
    public List<ActivityGiftStock> getList(PageDto page) {
        if (StringUtils.isNotEmpty(entity.getUserName())) {//判断用户名
            YwUser ywUser = ywUserService.getYwusersByUsername(entity.getUserName(), true);
            if (ywUser == null) {
                return null;
            }
            entity.setUserId(ywUser.getId());
        }
        List<ActivityGiftStock> stockList = activityGiftStockService.getActivityGiftStockPage(entity,startTime,endTime,page);
		activityGiftStockService.setUserName(stockList);//用户名
		activityGiftStockService.setGiftName(stockList);//设置礼物名称
		activityGiftStockService.setItemName(stockList);//设置活动名称
		activityGiftStockService.setAdminName(stockList);//设置管理员名称
		activityGiftStockService.setActivityUser(stockList);//设置真实姓名
		return stockList;
    }

    public String listview() {
        ActivityGiftStock activityGiftStock = new ActivityGiftStock();

        if (entity != null) {
            if (StringUtils.isNotEmpty(entity.getUserName())) {//判断用户名
                YwUser ywUser = ywUserService.getYwusersByUsername(entity.getUserName(), true);
                if (ywUser == null) {
                    return SUCCESS;
                }
                activityGiftStock.setUserId(ywUser.getId());
            }
//			if(StringUtils.isNotEmpty(entity.getItemName())){//判断活动
//				ActivityItem activityItem = activityItemService.getActivityItemByName(entity.getItemName());
//				if(activityItem ==null){
//					return SUCCESS;
//				}
//				activityGiftStock.setItemId(activityItem.getId());
//			}
            if (StringUtils.isNotEmpty(entity.getItemId())) {//判断活动ID
                activityGiftStock.setItemId(entity.getItemId());
            }

            if (StringUtils.isNotEmpty(entity.getGiftName())) {//判断礼品
                ActivityGift activityGift = activityGiftService.getActivityGiftByName(entity.getGiftName());
                if (activityGift == null) {
                    return SUCCESS;
                }
                activityGiftStock.setGiftId(activityGift.getId());
            }
        }


        List<ActivityGiftStock> list = activityGiftStockService.getActivityGiftStockPages(activityGiftStock, startTime, endTime, getPageDto());
        activityGiftStockService.setGiftName(list);//礼物名
//		activityGiftStockService.setItemName(list);//活动名
        activityGiftStockService.setUserName(list);//用户名
        activityGiftStockService.setAdminName(list);//操作人
        activityGiftStockService.setActivityUser(list);//活动信息
        this.list = list;
        return SUCCESS;
    }

    /**
     * @throws ParseException 礼物发放数据导出
     * @Description:
     */
    public void export() throws ParseException {
        List<Object> fieldNames = new ArrayList<Object>();
        List<String> propertyNames = new ArrayList<String>();
        fieldNames.add("礼品名称");
        propertyNames.add("giftName");
        fieldNames.add("领取人账号");
        propertyNames.add("userName");
        fieldNames.add("QQ号码");
        propertyNames.add("qq");
        fieldNames.add("真实姓名");
        propertyNames.add("realName");
        fieldNames.add("发货状态");
        propertyNames.add("status");
        fieldNames.add("礼品类型");
        propertyNames.add("type");
        fieldNames.add("中奖时间");
        propertyNames.add("createTime");
        fieldNames.add("IP地址");
        propertyNames.add("ip");
        fieldNames.add("操作人");
        propertyNames.add("adminName");
        fieldNames.add("备注");
        propertyNames.add("remark");

        // 获取导出数据
        List<ActivityGiftStock> exportList = getList(null);
        Pattern p = Pattern.compile("<([^>]*)>"); //过滤html标签
        for (ActivityGiftStock item : exportList) {
            if (item.getRemark() != null) {
                item.setRemark(p.matcher(item.getRemark()).replaceAll(""));
            }
        }
        String filename = DateUtils.format(getNow(), "yyyyMMddHHmmss");
        File file = CSVUtils.createCSVFile(fieldNames, propertyNames, exportList, getLocalTempPath(), filename);
        DownloadUtil.downloadCSV("礼物发放数据记录-" + DateUtils.formatHms(getNow()) + ".csv", file);
    }

    //
    public String importExcel() throws Exception {
        UploadFile[] files = UploadUtils.handleFileUpload();
        ImportParams params = new ImportParams();
        for (UploadFile file : files) {
            //String ext = FileUtil.getExtensionName(file.getFileName());
            //FileSystemUtil.saveFile(file.getFile(), filePath);
            List<CourseEntity> list = ExcelImportUtil.importExcel(file.getFile(), CourseEntity.class, params);
            final int arrsize =  list.size();
            String[] qqs=new String[arrsize];
           for(int i=0;i<arrsize;i++)
           {
               qqs[i]=list.get(i).getQq();
           }
            List<String> ids=ywUserService.getYwUserAllIds(qqs);
            ActivityGiftStock updateItem = new ActivityGiftStock();
            updateItem.setAdminId((getAdminLogin().getId()));
            updateItem.setRemark("");
            updateItem.setStatus("2");
            final int size =  ids.size();
            String[] userIds = (String[])ids.toArray(new String[size]);
            activityGiftStockService.updateForUserIds(userIds,updateItem);


        }

        return SUCCESS;
    }

    /**
     * 审核
     *
     * @return
     * @throws Exception
     * @creationDate. 2015-12-7 上午11:26:35
     */
    public String doGift() throws Exception {
        ActivityGiftStock activityGiftStock = activityGiftStockService.getActivityGiftStockById(id);
        List<ActivityGiftStock> stockList = new ArrayList<ActivityGiftStock>();
        stockList.add(activityGiftStock);
        activityGiftStockService.setItemName(stockList);//设置活动名称
        if (activityGiftStock == null) {
            list();
            addActionError("找不到数据");
            return SUCCESS;
        }
        if (entity == null) {
            entity = new ActivityGiftStock();
        }
        entity.setItemName(activityGiftStock.getItemName());
        if ("1".equalsIgnoreCase(activityGiftStock.getStatus())) {
            list();
            addActionMessage("该中奖礼物已经发货.");
            return SUCCESS;
        } else if ("2".equalsIgnoreCase(activityGiftStock.getStatus())) {
            list();
            addActionMessage("该中奖礼物未通过审核.");
            return SUCCESS;
        }

        activityGiftStock.setAdminId(getAdminLogin().getId());
        activityGiftStock.setStatus(status);
        if (giftStock != null) {
            activityGiftStock.setRemark(giftStock.getRemark());
        }
        try {
            ActivityUser activityUser = activityUserService.getActivityUserById(activityGiftStock.getUserId());
            if (activityUser == null) {
                if ("1".equalsIgnoreCase(status)) {
                    list();
                    addActionError("用户找不到");
                    return SUCCESS;
                }
            } else {
                activityGiftStock.setMobile(activityUser.getTelphone());
            }
            activityGiftStockService.updateForDoGift(activityGiftStock);

        } catch (RuntimeException e) {
            addActionError(e.getMessage());
            list();
            e.printStackTrace();
            return SUCCESS;
        }
        list();
        addActionMessage("已审核");
        return SUCCESS;
    }

    /**
     * 批量发货
     * 需求描述：礼物发放页面每页显示100条数据，不需要分页勾选,新增批量发货和批量拒绝按钮，当用户选择多条数据去发货时如果其中存在错误的纪录则给出以下提示
     * 1.用户选择了已经发货的数据，那么备注中加红提示“重复发货”
     * 2.用户选择了已经拒绝的数据，那么备注中加红提示“已被拒绝”
     * 3.如果出现以上情况，那么UI弹出框中需要列表提示有问题的数据，正常的数据就更新到数据库中不做回滚
     *
     * @return
     * @throws Exception
     */
    public String doBatchGifts() throws Exception {
        if (ids == null || ids.length < 1) {
            addActionMessage("请选择纪录");
        }
        Map<String, ActivityGiftStock> giftsMap = activityGiftStockService.getActivityGiftStockMapByIds(ids);
        List<ActivityGiftStock> errorList = new ArrayList<ActivityGiftStock>();
        List<ActivityGiftStock> normalList = new ArrayList<ActivityGiftStock>();
        List<ActivityGiftStock> allErrorList = new ArrayList<ActivityGiftStock>();
        String str = "<font color='red'>%s</font>";
        for (String id : ids) {
            ActivityGiftStock item = giftsMap.get(id);
            if (item == null) {
                item = new ActivityGiftStock();
                item.setId(id);
                item.setRemark(String.format(str, "找不到数据;"));
                allErrorList.add(item);
                continue;
            } else if ("0".equalsIgnoreCase(item.getStatus())) {
                item.setStatus("1");
                item.setAdminId(getAdminLogin().getId());
                item.setRemark(giftStock.getRemark());
                normalList.add(item);
                continue;
            } else if ("1".equalsIgnoreCase(item.getStatus())) {
                item.setRemark(String.format(str, "重复发货;") + item.getRemark());
                item.setAdminId(getAdminLogin().getId());
                errorList.add(item);
            } else if ("2".equalsIgnoreCase(item.getStatus())) {
                item.setRemark(String.format(str, "已被拒绝;") + item.getRemark());
                item.setAdminId(getAdminLogin().getId());
                errorList.add(item);
            }

        }
        activityGiftStockService.setUserName(errorList);
        //批量发货
        for (ActivityGiftStock item : normalList) {
            activityGiftStockService.updateForDoGift(item);
        }
        //批量更新错误的纪录信息
        for (ActivityGiftStock item : errorList) {
            activityGiftStockService.update(item);
        }
        //UI展现错误信息
        allErrorList.addAll(errorList);
        if (CollectionUtils.isNotEmpty(allErrorList)) {
            StringBuilder errorMsg = new StringBuilder("领取人                                        发货失败原因\n");
            Pattern p = Pattern.compile("<([^>]*)>");
            for (ActivityGiftStock item : allErrorList) {
                errorMsg.append(item.getUserName());
                errorMsg.append("                            ");
                errorMsg.append(p.matcher(item.getRemark() == null ? "" : item.getRemark()).replaceAll(""));//过滤html标签
                errorMsg.append("\n");
            }
            addActionMessage(errorMsg.toString());
        }
        list();
        return SUCCESS;
    }

    /**
     * 批量拒绝发货
     * 需求描述：
     *
     * @return
     * @throws Exception
     */
    public String doBatchRejectGifts() {
        ActivityGiftStock updateItem = new ActivityGiftStock();
        updateItem.setAdminId((getAdminLogin().getId()));
        updateItem.setRemark(giftStock.getRemark());
        updateItem.setStatus("2");
        activityGiftStockService.updateForDoBatchGifts(ids, updateItem);
        list();
        addActionMessage("已审核");
        return SUCCESS;
    }

    /**
     * 查看详情
     *
     * @return
     * @creationDate. 2015-12-7 下午2:00:08
     */
    public String view() {
        giftStock = activityGiftStockService.getActivityGiftStockById(id);
        List<ActivityGiftStock> stockList = new ArrayList<ActivityGiftStock>();
        stockList.add(giftStock);
        activityGiftStockService.setActivityUser(stockList);//设置活动用户信息
        activityGiftStockService.setGiftName(stockList);//设置礼物名称
        YwUser ywUser = ywUserService.getYwUserById(giftStock.getUserId());
        if (ywUser != null) {
            giftStock.setUserName(ywUser.getUsername());
            giftStock.setNickName(ywUser.getNickname());
        }
        AdminUser adminUser = adminUserService.getAdminUserById(giftStock.getAdminId());
        if (adminUser != null) {
            giftStock.setAdminName(adminUser.getUsername());
        }
        ActivityUser activityUser = activityUserService.getActivityUserById(giftStock.getUserId());
        if (activityUser == null) {
            addActionError("用户信息找不到");
            return SUCCESS;
        }
        if ("0".equalsIgnoreCase(giftStock.getStatus()) && "3".equalsIgnoreCase(giftStock.getType())) {
            giftStock.setRemark(getTmpRemark(giftStock.getGiftName()));
            checkMobileType(activityUser.getTelphone());
        }
        if ("4".equalsIgnoreCase(giftStock.getType())) {
            giftStock.setAddress(activityUser.getAddress());
            giftStock.setRealName(activityUser.getRealname());
            giftStock.setMobile(activityUser.getTelphone());
            giftStock.setQq(activityUser.getQq());
            checkMobileType(activityUser.getTelphone());
            if ("0".equalsIgnoreCase(giftStock.getStatus())) {
                giftStock.setRemark("");
            }
        }
        return SUCCESS;
    }

    private void checkMobileType(String telphone) {
        if (StringUtils.isEmpty(telphone)) {
            return;
        }
        if (isChinaMobileNumber(telphone)) {
            giftStock.setMobileDesc(telphone + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国移动");
        } else if (isChinaUnionNumber(telphone)) {
            giftStock.setMobileDesc(telphone + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国联通");
        } else if (isChinaTelecomNumber(telphone)) {
            giftStock.setMobileDesc(telphone + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国电信");
        } else {
            giftStock.setMobileDesc(telphone + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它号码");
        }
    }

    private boolean isChinaMobileNumber(String mobile) {
        return Pattern.matches("^(134|135|136|137|138|139|147|150|151|152|157|159|181|182|183|187|188)\\d{8}$", mobile);
    }

    private boolean isChinaUnionNumber(String mobile) {
        return Pattern.matches("^(130|131|132|155|156|185|186|145|176)\\d{8}$", mobile);
    }

    private boolean isChinaTelecomNumber(String mobile) {
        return Pattern.matches("^(133|153|180|181|189|177)\\d{8}$", mobile);
    }

    private String getTmpRemark(String giftName) {
        StringBuilder sbRemark = new StringBuilder();
        sbRemark.append("恭喜您，参加蓝鲨TV抽奖获得" + giftName + "\n");
        sbRemark.append("序列号：\n");
        sbRemark.append("密码：\n");
        sbRemark.append("希望你在平台上玩的开心\n");
        return sbRemark.toString();
    }

    public ActivityGiftStock getEntity() {
        return entity;
    }

    public void setEntity(ActivityGiftStock entity) {
        this.entity = entity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ActivityGiftStock getGiftStock() {
        return giftStock;
    }

    public void setGiftStock(ActivityGiftStock giftStock) {
        this.giftStock = giftStock;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
