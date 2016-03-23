package com.yaowang.lansha.action.mobile;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.*;
import com.yaowang.lansha.service.*;
import com.yaowang.lansha.service.impl.LanshaTicketService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.NumberUtil;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.openfire.http.MessageTool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author wanglp
 * @ClassName: LiveAction
 * @Description: 直播
 * @date 2015-12-21 下午4:25:44
 */
public class LiveAction extends LanshaMobileAction {
    private static final long serialVersionUID = 1L;
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private YwUserService ywUserService;
    @Resource
    private YwUserRoomRelationService ywUserRoomRelationService;
    @Resource
    private YwUserRoomHistoryService ywUserRoomHistoryService;
    @Resource
    private ActivityUserService activityUserService;
    @Resource
    private YwUserRoomAdminService ywUserRoomAdminService;
    @Resource
    private LanshaUserGiftStockService lanshaUserGiftStockService;
    @Resource
    private LanshaGiftUserService lanshaGiftUserService;
    @Resource
    private LanshaRoomBlacklistService lanshaRoomBlacklistService;
    @Resource
    private ActivityGiftStockService activityGiftStockService;
    @Resource
    private ActivityGiftService activityGiftService;
    @Resource
    private YwGameService ywGameService;
    @Resource
    private LanshaTicketService lanshaTicketService;
    private YwUserRoom room;
    //礼物id
    private String giftId;

    /**
     * @throws IOException
     * @Title: list
     * @Description: 直播列表
     */
    @SuppressWarnings("unchecked")
    public void list() throws IOException {
        //查询数据
        PageDto page = getPageDto();
        page.setRowNum(10);
        YwUserRoom searchRoom = new YwUserRoom();
        searchRoom.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
        searchRoom.setOrderSql("number*multiple_number+base_number desc, create_time");
        list = ywUserRoomService.getAllLiveListByRoome(searchRoom, page, null);
        ywUserRoomService.setData((List<YwUserRoom>) list, true);
        //返回数据
        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
        for (YwUserRoom entity : (List<YwUserRoom>) list) {
            Map<String, Object> datamap = new HashMap<String, Object>();
            datamap.put("id", entity.getId());
            datamap.put("img", getUploadFilePath(entity.getLiveImg()));// (http地址)
            datamap.put("name", entity.getName());
            datamap.put("number", entity.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(entity.getOnLineNumber(), 1));
            datamap.put("nickName", entity.getNickname());
            datamap.put("gameName", entity.getGameName());
            datamap.put("online", entity.getOnline() == null ? "0" : entity.getOnline().toString());// (0：离线，1：在线，2：停播)
            datamap.put("headImg", getUploadFilePath(entity.getUserIcon()));
            datalist.add(datamap);
        }

        writeSuccessWithData(datalist);
    }

    /**
     * @throws Exception
     * @Title: list
     * @Description: 直播间
     */
    public void live() throws Exception {
        // 查询房间
        final YwUserRoom room = ywUserRoomService.getYwUserRoomById(id);
        if (room == null) {
            write(getFailed("房间不存在"));
            return;
        }
        //主播
        YwUser user = ywUserService.getYwUserById(room.getUid());
        if (user == null) {
            write(getFailed("房间已没有主播"));
            return;
        }
        String role = "0";
        //当前登录用户
        Map<String, Object> datamap = new HashMap<String, Object>();
        //是否主播本人
        final YwUser loginuser = getUserLogin();
        if (loginuser != null) {
            boolean permission = ywUserRoomAdminService.containsAdmin(id, loginuser.getId());
            if (permission) {
                role = "2";
            } else if (StringUtils.isNotBlank(loginuser.getOfficialType())) {
                if ("1".equals(user.getOfficialType())) {
                    role = "3";
                } else if ("2".equals(loginuser.getOfficialType())) {
                    role = "4";
                }
            }
        }
        if (loginuser != null && loginuser.getId().equals(room.getUid())) {
            datamap.put("isAnchor", BaseConstant.YES_S);
            role = "1";
        } else {
            datamap.put("isAnchor", BaseConstant.NO_S);
        }

        //订阅人数
        YwUserRoomRelation entity = new YwUserRoomRelation();
        entity.setRoomId(id);
        entity.setStatus(1);
//        int relation = ywUserRoomRelationService.getSumRelationNumber(entity);
        //是否订阅

        datamap.put("relation", room.getFans() + "");
        datamap.put("isQBi", "0");
        datamap.put("isGag", "0");
        if (loginuser == null) {
            datamap.put("isRelation", BaseConstant.NO_S);
            datamap.put("bi", "0");
            datamap.put("ticketNum", "0");
        } else {
            entity.setUid(loginuser.getId());
            List<YwUserRoomRelation> relations = ywUserRoomRelationService.getYwUserRoomRelationList(entity);
            if (relations.isEmpty()) {
                datamap.put("isRelation", BaseConstant.NO_S);
            } else {
                datamap.put("isRelation", BaseConstant.YES_S);
            }
            //虾米库存
            LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, loginuser.getId());
            datamap.put("bi", stock == null ? "0" : stock.getStock().toString());

            ActivityUser activityUser = activityUserService.getActivityUserById(loginuser.getId());
            ActivityGiftStock query = new ActivityGiftStock();
            query.setItemId(LanshaConstant.ITEM_ID_TWO);
            query.setGiftId(LanshaConstant.Q_GIFT_ID); //礼物ID
            query.setUserId(loginuser.getId());
            List<ActivityGiftStock> activityStock = activityGiftStockService.getActivityGiftStockList(query);
            if (CollectionUtils.isNotEmpty(activityStock) && null != activityUser && StringUtils.isBlank(activityUser.getQq())) {
                datamap.put("isQBi", "1");
            }


            boolean blackRecord = lanshaRoomBlacklistService.getIsBlackByRoomAndUser(room.getId(), loginuser.getId());
            datamap.put("isGag", blackRecord ? "1" : "0");
        }

        datamap.put("uIdentity", role);
        //其他信息
        datamap.put("id", room.getId());
        datamap.put("img", getUploadFilePath(room.getLiveImg()));// (http地址)
        datamap.put("name", room.getName());
        datamap.put("number", room.getNumber() == null ? "0" : NumberUtil.changeNumberToWan(room.getNumber(), 1));
        datamap.put("baseNumber", room.getBaseNumber() == null ? "0" : room.getBaseNumber().toString());//基数
        datamap.put("multipleNumber", (room.getMultipleNumber() == null || room.getMultipleNumber() == 0) ? "1" : room.getMultipleNumber().toString());//倍数
        datamap.put("nickName", user.getNickname());
        datamap.put("headImg", getUploadFilePath(user.getHeadpic()));
        //游戏
        YwGame game = ywGameService.getYwGameById(room.getGameId());
        if (game == null) {
            datamap.put("gameName", "");
        } else {
            datamap.put("gameName", game.getName());
        }
        String online = room.getOnline() == null ? "0" : room.getOnline().toString();
        datamap.put("online", online);// (0：离线，1：在线，2：停播)
        datamap.put("imUrl", room.getOpenfirePath());
        datamap.put("imPort", room.getOpenfirePort());
        datamap.put("imRoom", room.getOpenfireRoom());
        datamap.put("imConference", room.getOpenfireConference());
        datamap.put("intro", room.getNotice());
        datamap.put("icon", getUploadFilePath(user.getHeadpic()));
        //流地址   标清、高清、超清暂时都一样
        datamap.put("rtmp", room.getLiveHost() + room.getRoomId());
        datamap.put("rtmpHd", room.getLiveHost() + room.getRoomId());
        datamap.put("rtmpSd", room.getLiveHost() + room.getRoomId());
        //领取虾米时间
        String time = SysOptionServiceImpl.getValue("LANSHA.BI.TIME");
        //领取鲜花
        int flowerTime = getFlowerTime();
        if (StringUtils.isBlank(time)) {
            datamap.put("time", "0");
        } else {
            datamap.put("time", time);
        }
        if (loginuser != null&&online.equals("1")) {
            //用户可赠送的鲜花数
            datamap.put("flowerStock", firstGiveFlower(loginuser).getStock() + "");
            datamap.put("ticketNum", firstGiveTicket(loginuser).getStock() + "");
        } else {
            datamap.put("flowerStock", "N");
            datamap.put("ticketNum", "0");
        }
        if (online.equals("1")) {
            datamap.put("flowerTime", String.valueOf(flowerTime));
        }
        else{
            datamap.put("flowerTime","0");
        }
        int maxSum = 10;
        datamap.put("maxNumber", String.valueOf(maxSum));
        String isGet = null;

        //日票排名
        String arr[] = lanshaTicketService.sort(room.getUid());
        if (arr.length > 0) {
            datamap.put("ticketOrder", arr[0]);
            datamap.put("currentTicketNum", arr[1]);
            datamap.put("ticketWithPrevious", arr[2]);
        }

        if (getFirstGetGift()) {
            isGet = "1";
        } else {
            isGet = "0";
        }
        datamap.put("isGet", isGet);
        writeSuccessWithData(datamap);

        if (loginuser != null) {
            AsynchronousService.submit(new ObjectCallable() {
                @Override
                public Object run() throws Exception {
                    //添加观看记录
                    ywUserRoomHistoryService.saveHistory(room.getId(), loginuser.getId());
                    return null;
                }
            });
        }
    }

    /**
     * 新注册用户首次进入房间赠送鲜花
     *
     * @param loginuser
     * @creationDate. 2016-2-2 下午6:07:45
     */
    public LanshaUserGiftStock firstGiveFlower(YwUser loginuser) {
        //新用户进入房间先赠送鲜花
        int defaultNum = 3;
        return findGiftStock(LanshaConstant.GIFT_ID_TWO, loginuser.getId(), defaultNum);
    }

    /**
     * 新注册用户首次进入房间初始日票数量1
     *
     * @param loginuser
     * @creationDate. 2016-2-2 下午6:07:45
     */
    public LanshaUserGiftStock firstGiveTicket(YwUser loginUser) {
        //新用户进入房间先初始化日票数
        int defaultNum = 1;
        return findGiftStock(LanshaConstant.GIFT_ID_FOUR, loginUser.getId(), defaultNum);
    }

    /**
     * 查找用户礼物库存数量,找不到初始化库存数
     *
     * @param userId
     * @param giftId
     * @param defaultNum
     * @return
     */
    private LanshaUserGiftStock findGiftStock(String giftId, String userId, int defaultNum) {
        LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(giftId, userId);
        if (null == stock) {
            stock = new LanshaUserGiftStock();
            stock.setCreateTime(getNow());
            stock.setGiftId(giftId);
            stock.setStock(defaultNum);
            stock.setUserId(userId);
            lanshaUserGiftStockService.save(stock);
        }
        return stock;
    }

    /**
     * @throws IOException
     * @Title: list
     * @Description: 送房间主播礼物
     */
    public void gift() throws IOException {
        if (StringUtils.isBlank(giftId)) {
            give(LanshaConstant.GIFT_ID, "虾米");
        } else {
            if (LanshaConstant.GIFT_ID_TWO.equals(giftId)) {
                give(LanshaConstant.GIFT_ID_TWO, "鲜花");
            } else if (LanshaConstant.GIFT_ID_FOUR.equals(giftId)) {
                give(LanshaConstant.GIFT_ID_FOUR, "日票");
            } else {
                give(LanshaConstant.GIFT_ID, "虾米");
            }
        }
    }

    /**
     * @throws IOException
     * @Title: list
     * @Description: 领取礼物(现在只有虾米)
     */
    public void getBi() throws IOException {
        if (StringUtils.isBlank(giftId) || LanshaConstant.GIFT_ID.equals(giftId)) {
            getGift(LanshaConstant.GIFT_ID);
        } else if (LanshaConstant.GIFT_ID_TWO.equals(giftId)) {
            getGift(LanshaConstant.GIFT_ID_TWO);
        }
    }


    /**
     * 获取虾米限额时间
     *
     * @return
     */
    public Integer getTime() {
        return Integer.valueOf(SysOptionServiceImpl.getValue("LANSHA.BI.TIME"));
    }

    /**
     * 获取鲜花限额时间
     *
     * @return
     */
    public Integer getFlowerTime() {
        return Integer.valueOf(SysOptionServiceImpl.getValue("LANSHA.FLOWER.TIME"));
    }

    /**
     * 当天是否领取过虾米(如果没有领取过，第一次默认不需要倒计时)
     */
    public boolean getFirstGetGift() {
        YwUser user = getUserLogin();
        if (user == null) {
            return true;
        }
        //查询,因为收入记录还包含别人送的虾米记录，不能区分是送还是自领
//		PageDto page = getPageDto();
//		page.setCount(false);
//		page.setRowNum(1);
//		page.setCurrentPage(1);
//		//当天用户领取虾米的记录
//		LanshaUserRecord record = new LanshaUserRecord();
//		record.setObjectId(LanshaConstant.GIFT_ID);
//		record.setObjectType(LanshaConstant.RECORD_OBJECT_TYPE_2);
//		record.setType(LanshaConstant.INCOME);
//		record.setUserId(user.getId());
//		setStartTime(getNow());
//		setEndTime(getNow());
//		List<LanshaUserRecord> list = lanshaUserRecordService.getLanshaUserRecordPage(record, page, startTime, endTime);
//		if(CollectionUtils.isEmpty(list)){
//			return false;
//		}

        //查询
        YwUserRoomHistory history = new YwUserRoomHistory();
        history.setUid(user.getId());
        history.setStatus(2);
        history.setTypeId(LanshaConstant.GIFT_ID);
        setStartTime(getNow());
        setEndTime(getNow());
        PageDto page = getPageDto();
        page.setCount(false);
        page.setRowNum(1);
        page.setCurrentPage(1);
        List<YwUserRoomHistory> histories = ywUserRoomHistoryService.getYwUserRoomHistoryPage(history, null, page, startTime, endTime);
        if (CollectionUtils.isEmpty(histories)) {
            return false;
        }
        return true;
    }

    //返回礼物库存
    public void getErrorMsgAndSum(int biSum, String msg, String giftId) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("msg", msg);
        obj.put("status", "0");
        obj.put("biSum", String.valueOf(biSum));
        obj.put("bi", String.valueOf(0));
        obj.put("giftId", giftId);
        writeSuccessWithData(obj);
    }

    //领取礼物公共方法
    public void getGift(String giftId) throws IOException {
        YwUser user = getUserLogin();
        PageDto page = getPageDto();
        page.setCount(false);
        page.setRowNum(1);

        YwUserRoomHistory history = new YwUserRoomHistory();
        history.setUid(user.getId());
        history.setStatus(1);
        history.setOrder("create_time");
        history.setTypeId(giftId);

        // 必须2小时内进入的房间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 2);
        Date startTime = calendar.getTime();

//		synchronized (user) {
        //防止重复领取，同步锁
        List<YwUserRoomHistory> histories = ywUserRoomHistoryService.getYwUserRoomHistoryPage(history, null, page, startTime, null);
        LanshaUserGiftStock giftStock = lanshaUserGiftStockService.getByGiftIdAndUserId(giftId, user.getId());

        int stock = giftStock == null ? 0 : giftStock.getStock();
        if (histories.isEmpty()) {
            getErrorMsgAndSum(stock, "操作无效", giftId);
            return;
        } else {
            YwUserRoomHistory entity = histories.get(0);
            int diff = (int) ((getNow().getTime() - entity.getCreateTime().getTime()) / 1000);
            if (entity.getTimeLength() == null) {
                getErrorMsgAndSum(stock, "已领取", giftId);
                return;
            } else {
                //领取虾米
                if (LanshaConstant.GIFT_ID.equals(giftId)) {
                    int time = getTime();
                    //如果当天未领取过虾米，第一次默认没有倒计时，则不判断时长
                    if (getFirstGetGift()) {
                        if (diff < time) {
                            getErrorMsgAndSum(stock, "观看时间小于" + (time / 60) + "分钟，还不能领取", giftId);
                            return;
                        }
                    }

                    //随机领取（1-5）个虾米
                    int bi = (int) (Math.random() * 5) + 1;
                    entity.setBi(bi);

                    //获取礼物库存（当前默认礼物虾米）
                    if (giftStock == null) {
                        //第一次领取添加库存记录
                        giftStock = new LanshaUserGiftStock();
                        giftStock.setCreateTime(getNow());
                        giftStock.setGiftId(LanshaConstant.GIFT_ID);
                        giftStock.setStock(bi);
                        giftStock.setUserId(user.getId());
                    } else {
                        giftStock.setStock(stock + bi);
                    }

                    //user.setBi(user.getBi() + bi);

                    //响应信息
                    responseMsg(entity, giftStock, user, diff, LanshaConstant.GIFT_NAME, bi, giftId);
                    return;
                } else if (LanshaConstant.GIFT_ID_TWO.equals(giftId)) {
                    //领取鲜花
                    int time = getFlowerTime();
                    //首次领三朵
                    int defaultNum = 3;
                    if (giftStock == null) {
                        //未领取过鲜花默认赠送3朵
                        giftStock = new LanshaUserGiftStock();
                        giftStock.setCreateTime(getNow());
                        giftStock.setGiftId(LanshaConstant.GIFT_ID_TWO);
                        giftStock.setStock(defaultNum);
                        giftStock.setUserId(user.getId());
                        entity.setBi(defaultNum);
                        entity.setTimeLength(diff);
                        entity.setStatus(2);
                        entity.setTypeId(LanshaConstant.GIFT_ID_TWO);//礼物当前为鲜花
                        ywUserRoomHistoryService.saveGift(entity, giftStock, user);
                        JSONObject obj = new JSONObject();
                        obj.put("msg", "系统默认赠送您" + defaultNum + LanshaConstant.GIFT_NAME_TWO);
                        obj.put("biSum", String.valueOf(giftStock.getStock()));
                        obj.put("bi", String.valueOf(defaultNum));
                        obj.put("status", "1");
                        obj.put("giftId", giftId);
                        writeSuccessWithData(obj);
                        return;
                    }
                    if (diff < time) {
                        getErrorMsgAndSum(giftStock.getStock(), "观看时间小于" + (time / 60) + "分钟，还不能领取", giftId);
                        return;
                    } else {
                        //领取1朵鲜花
                        int bi = 1;
                        entity.setBi(bi);

                        //获取礼物库存（当前礼物为鲜花）
                        int maxSum = 10;
                        if (giftStock.getStock() >= maxSum) {
                            getErrorMsgAndSum(maxSum, "鲜花库存已达到上限" + maxSum + "，领取失败", giftId);
                            return;
                        }
                        giftStock.setStock(giftStock.getStock() + bi);

                        //user.setBi(user.getBi() + bi);

                        //响应信息
                        responseMsg(entity, giftStock, user, diff, LanshaConstant.GIFT_NAME_TWO, bi, giftId);
                        return;
                    }
                }
            }
        }
//		}
    }

    /**
     * @param entity
     * @param gift
     * @param diff
     * @param giftName
     * @param bi
     * @param giftId
     * @throws IOException
     * @creationDate. 2016-1-27 下午10:05:14
     */
    public void responseMsg(YwUserRoomHistory entity, LanshaUserGiftStock gift, YwUser u, int diff, String giftName, int bi, String giftId) throws IOException {
        entity.setTimeLength(diff);
        entity.setStatus(2);
        //保存
        try {
            if (LanshaConstant.GIFT_ID.equals(giftId)) {
                ywUserRoomHistoryService.saveReceiveGift(entity, gift, u);
            } else if (LanshaConstant.GIFT_ID_TWO.equals(giftId)) {
                ywUserRoomHistoryService.saveReceiveFlower(entity, gift, u);
            }
            //setUserLogin(user);
        } catch (Exception e) {
            e.printStackTrace();
            write(getErrorMsg(e.getMessage()));
        }
//		write("{'status': '1', 'msg': '恭喜您已领取" + bi + "朵鲜花', 'biSum': '"gift.getStock(), "', 'bi': '" + bi + "' }");
        JSONObject obj = new JSONObject();
        obj.put("msg", "恭喜您已领取" + bi + giftName);
        obj.put("biSum", String.valueOf(gift.getStock()));
        obj.put("bi", String.valueOf(bi));
        obj.put("status", "1");
        obj.put("giftId", giftId);
        writeSuccessWithData(obj);
        return;
    }

    /**
     * 赠送礼物公共方法
     *
     * @param giftId   礼物id
     * @param giftName 礼物名
     * @throws IOException
     * @creationDate. 2016-2-01 上午11:48:47
     */
    public void give(final String giftId, String giftName) throws IOException {
        final YwUser user = getUserLogin();
        //礼物库存
        LanshaUserGiftStock gift = lanshaUserGiftStockService.getByGiftIdAndUserId(giftId, user.getId());
        if (gift == null || gift.getStock() <= 0) {
            getErrorMsgAndSum(0, "抱歉，您没有足够的" + giftName, giftId);
            return;
        }
        // 查询房间
        final YwUserRoom room = ywUserRoomService.getYwUserRoomById(id);
        if (room == null) {
            getErrorMsgAndSum(gift.getStock(), "房间不存在", giftId);
            return;
        } else if (room.getUid().equals(user.getId())) {
            getErrorMsgAndSum(gift.getStock(), "主播不能送自己" + giftName, giftId);
            return;
        }

        //赠送礼物
        final LanshaGiftUser giftUser = new LanshaGiftUser();
        giftUser.setBi(0);
        giftUser.setNumber(1);
        giftUser.setUserId(user.getId());
        giftUser.setAnchorId(room.getUid());//主播id
        giftUser.setCreateTime(getNow());
        if (LanshaConstant.GIFT_ID.equals(giftId)) {
            giftUser.setGiftId(giftId);
        } else if (LanshaConstant.GIFT_ID_TWO.equals(giftId)) {
            //赠送鲜花存放收到的鲜花库存里
            giftUser.setGiftId(LanshaConstant.GIFT_ID_THREE);
        } else if (LanshaConstant.GIFT_ID_FOUR.equals(giftId)) {
            //赠送日票存放收到的日票
            giftUser.setGiftId(LanshaConstant.GIFT_ID_FIVE);
        }

        try {
            //更新用户礼物库存
            lanshaUserGiftStockService.updateReduceStock(gift.getId(), giftUser.getNumber());

            JSONObject obj = new JSONObject();
            obj.put("msg", "恭喜赠送成功");
            obj.put("biSum", String.valueOf(gift.getStock() - 1));
            obj.put("bi", "1");
            obj.put("status", "1");
            obj.put("giftId", giftId);
            writeSuccessWithData(obj);
        } catch (RuntimeException e) {
            write(getErrorMsg(e.getMessage()));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            write(getErrorMsg("赠送失败"));
            return;
        }

        AsynchronousService.submit(new ObjectCallable() {
            @Override
            public Object run() throws Exception {
                //添加记录
                lanshaGiftUserService.saveGiveGift(giftUser, giftId);
                if (giftId.equals(LanshaConstant.GIFT_ID_FOUR)) {
                    lanshaTicketService.put(giftUser.getAnchorId());
                }
                //发送消息
                JSONObject object = new JSONObject();
                object.put("sendUid", user.getId());
                object.put("sendName", user.getNickname() == null ? "" : user.getNickname());
                object.put("giftNum", giftUser.getNumber());
                object.put("giftId", giftId);
                object.put("type", 4);
                try {
                    MessageTool.sendMessage(room.getOpenfirePath(), room.getOpenfireRoom(), room.getOpenfireConference(), object.toJSONString());
                } catch (Exception e) {
                    //im发送失败
                }
                return null;
            }
        });
    }

    /**
     * @throws IOException
     * @Title: list
     * @Description: 订阅/取消订阅(关注房间)
     */
    public void relation() throws IOException {
        YwUser user = getUserLogin();
        if (StringUtils.isEmpty(id)) {
            write(getFailed("房间id不能为空"));
            return;
        }
        if (user == null) {
            write(getFailed("请先登录"));
            return;
        }

        YwUserRoomRelation entity = new YwUserRoomRelation();
        entity.setRoomId(id);
        entity.setUid(user.getId());
        List<YwUserRoomRelation> relations = ywUserRoomRelationService.getYwUserRoomRelationList(entity);
        if (relations.isEmpty()) {
            YwUserRoomRelation relation = new YwUserRoomRelation();
            relation.setCreateTime(getNow());
            relation.setRoomId(id);
            relation.setStatus(1);
            relation.setUid(user.getId());
            ywUserRoomRelationService.save(relation);
        } else {
            YwUserRoomRelation relation1 = relations.get(0);
            if (relation1.getStatus() == 0) {
                ywUserRoomRelationService.updateSave(relation1);
            } else {
                //删除
                ywUserRoomRelationService.delete(new String[]{relation1.getId()}, id);
            }
        }

        writeSuccessWithData(EMPTY_DATA_ENTITY);
    }

    public YwUserRoom getRoom() {
        return room;
    }

    public void setRoom(YwUserRoom room) {
        this.room = room;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

}
