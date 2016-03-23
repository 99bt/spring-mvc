package com.yaowang.lansha.action.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.*;
import com.yaowang.lansha.service.*;
import com.yaowang.lansha.service.impl.LanshaTicketService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.freemark.AppSetting;
import com.yaowang.util.openfire.http.MessageTool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 直播
 *
 * @author shenl
 */
public class LiveAction extends LanshaBaseAction {
    private static final long serialVersionUID = 1L;

    @Resource
    private YwUserService ywUserService;
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private YwUserRoomHotService ywUserRoomHotService;
    @Resource
    private YwUserRoomRelationService ywUserRoomRelationService;
    @Resource
    private YwUserRoomHistoryService ywUserRoomHistoryService;
    @Resource
    private LanshaUserGiftStockService lanshaUserGiftStockService;
    @Resource
    private LanshaGiftUserService lanshaGiftUserService;
    @Resource
    private LanshaAdvertisementService lanshaAdvertisementService;
    @Resource
    private YwUserRoomAdminService ywUserRoomAdminService;

    @Resource
    private LanshaRoomBlacklistService lanshaRoomBlacklistService;

    private static final double SWITCH_NUM = 10000.0 ;
    private LanshaAdvertisement lanshaAdvertisement;

    private YwUserRoom room;
    private YwUser user;
    private YwUserRoomRelation relation;
    private YwUser loginUser;
    private String pn;
    private List<YwUserRoom> roomList;
    private LanshaUserGiftStock flowerStock;
    private LanshaUserGiftStock ticketStock;
    //主播收到的鲜花数
    private int flowerSum;
    //主播收到的鲜花数
    private int ticketSum;
    //领取鲜花间隔时间
    private int giveFlowerTime;
    //领取鲜花最大库存
    private int maxSum;
    //用户在房间内的身份：master-直播间主人 admin-管理员 other-普通用户
    private String role;
    //被处理用户id,如被任命的管理员
    private String uid;
    //日票数
    private String currentTicketNum;
    //日票排名
    private String ticketOrder;
    //日票差上一名票数
    private String ticketWithPrevious;
	//管理员列表
	private List<YwUserRoomAdmin> adminList;
    @Resource
    private LanshaTicketService lanshaTicketService;
    
    private String itemId;//活动id
    /**
     * 直播页面
     *
     * @throws Exception
     */
    public String live() throws Exception {
        //伪静态
        String objectId = getObjectId();
        if (StringUtils.isNotBlank(objectId)) {
            id = objectId;
        }
        if (getIsWap()) {
            //wap
            getRequest().getRequestDispatcher("/wap/live/" + id + ".html").forward(getRequest(), getResponse());
            return null;
        }

        if (StringUtils.isEmpty(id) || !id.matches("\\d+")) {
            addActionError("房间不存在");
            return "msg";
        }

        // 查询房间
        room = ywUserRoomService.getYwUserRoomById(Integer.valueOf(id));
        if (room == null) {
            addActionError("房间不存在");
            return "msg";
        }
        if (room.getOnline() == 2) {
            addActionError("房间已被禁播");
            return "msg";
        }
      
        
        // 主播日票
        String arr[] = lanshaTicketService.sort(room.getUid());
        
		if (arr.length > 0) {
        	ticketOrder = arr[0];
            currentTicketNum = arr[1];
            ticketWithPrevious = arr[2];
		}
        
        //领取鲜花参数
        giveFlowerTime = getFlowerTime();
        maxSum = 10;

        // 热门推荐
        PageDto page = getPageDto();
        page.setRowNum(8);
        page.setCount(false);
        List<YwUserRoomHot> hots = ywUserRoomHotService.getYwUserRoomHotPage(null, null);
        Set<String> roomIds = new HashSet<String>();
        for (YwUserRoomHot host : hots) {
            roomIds.add(host.getRoomId());
        }
        YwUserRoom userRoom = new YwUserRoom();
        userRoom.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
        userRoom.setOrderSql(" rand()");
        List<YwUserRoom> rooms = ywUserRoomService.getAllLiveListByRoome(userRoom, page, roomIds.toArray(new String[]{}));
        ywUserRoomService.setData((List<YwUserRoom>) rooms,true);
        //设置头像
        for (YwUserRoom room : rooms) {
            room.setUserIcon(getUploadFilePath(room.getUserIcon()));
        }
        list = rooms;

        if (rooms.size() >= 2) {
            //取前2条
            if (rooms.size() > 2) {
                roomList = rooms.subList(0, 2);
            } else {
                roomList = rooms;
            }
        } else {
            roomList = rooms.subList(0, rooms.size());
        }
        
        role = "other";
        //主播信息
        List<YwUserRoom> roomlist= new ArrayList<YwUserRoom>();
        roomlist.add(room);
        ywUserRoomService.setData(roomlist,true);
        user = ywUserService.getYwUserById(room.getUid());
        if(null == user){
        	getResponse().sendError(404);
        	return null;
        }
        user.setHeadpic(getUploadFilePath(user.getHeadpic()));

        //随机广告
        lanshaAdvertisement = lanshaAdvertisementService.getLanshaAdvertisementByRate();

        loginUser = getUserLogin();
        //主播收到的鲜花数量
        LanshaUserGiftStock lanshaUserGiftStock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID_THREE, user.getId());
        flowerSum = lanshaUserGiftStock == null ? 0 : lanshaUserGiftStock.getStock();

        LanshaUserGiftStock ticketGiftStock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID_FIVE, user.getId());
        ticketSum = null == ticketGiftStock ? 0 : ticketGiftStock.getStock();

		YwUserRoomAdmin query = new YwUserRoomAdmin();
		query.setRoomId(room.getId());
		adminList = ywUserRoomAdminService.getYwUserRoomAdminList(query);
		if(null == adminList){
			adminList = new ArrayList<YwUserRoomAdmin>();
		}else{
			for(int i =0;i<adminList.size();i++){
				YwUser ywUser =ywUserService.getYwUserById(adminList.get(i).getUserId());
				if(null != ywUser){
					adminList.get(i).setNickname(ywUser.getNickname());
//					adminList.get(i).setHeadpic(getUploadFilePath(ywUser.getHeadpic()));
				}
			}
		}

		isShowActivity(room.getGameName());//判断是否显示活动图标 
        //未登录
        if (loginUser == null) {
            return SUCCESS;
        }
        //用户可赠送的鲜花数
        flowerStock = firstGiveFlower(loginUser);
        //flowerStock =  lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID_TWO, loginUser.getId());
        //用户可赠送的日票数
        ticketStock = firstGiveTicket(loginUser);

        boolean permission = ywUserRoomAdminService.containsAdmin(room.getId(), loginUser.getId());
        if (permission) {
            role = "admin";
        }
        

        boolean blackRecord = lanshaRoomBlacklistService.getIsBlackByRoomAndUser(room.getId(), loginUser.getId());
        role = blackRecord ? "black" : role;
        
        //判断是否主播
        if (loginUser.getId().equals(room.getUid())) {
            role = "master";
            //添加观看记录(主播不需要领取虾米)
//			ywUserRoomHistoryService.saveHistory(room.getId(), loginUser.getId());
            return "anchor";
        } else {
        	if(StringUtils.isNotBlank(loginUser.getOfficialType())){
        		if("1".equals(loginUser.getOfficialType())){
        			role = "official";
        		}else if("2".equals(loginUser.getOfficialType())){
        			role = "superManager";
        		}
        	}
            //是否关注
            YwUserRoomRelation entity = new YwUserRoomRelation();
            entity.setRoomId(room.getId());
            entity.setUid(loginUser.getId());
            entity.setStatus(1);
            List<YwUserRoomRelation> relations = ywUserRoomRelationService.getYwUserRoomRelationList(entity);
            if (!relations.isEmpty()) {
                relation = relations.get(0);
            }
            if (room.getOnline() != 1) {
                return SUCCESS;
            }
            //添加观看记录
            ywUserRoomHistoryService.saveHistory(room.getId(), loginUser.getId());
            return SUCCESS;
        }
        
    }
    
    /**
     * 判断是否显示活动图标
     * @return void
     * @throws
     */
    public void isShowActivity(String gameName){
		String hdlive=getCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LIVE);
		itemId="";
		if(!StringUtils.isBlank(hdlive) && !LanshaConstant.LANSHA_ACTIVITY_TO_LIVE_END.equals(hdlive)){
			//根据游戏名称获取 活动id
			if("王者荣耀".equals(gameName)){
				itemId=LanshaConstant.ITEM_ID_TWO;
			}else if("穿越火线".equals(gameName)){
				itemId=LanshaConstant.ITEM_ID_THIRD;
			}
		}
		addCookie(LanshaConstant.LANSHA_ACTIVITY_TO_LIVE, LanshaConstant.LANSHA_ACTIVITY_TO_LIVE_END);//第二次则不能显示
    }
    /**
     * 任命管理员
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void addManager() throws IOException, InterruptedException {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(uid)) {
            write(getFailed("请求失败，房间id或用户id不能为空!"));
            return;
        }
        YwUser me = getUserLogin();
        if (null == me) {
            write(getFailed("对不起，请先登陆!"));
            return;
        }
        YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(id);
        if (null == ywUserRoom) {
            write(getFailed("房间id不存在!"));
            return;
        }
        if (!me.getId().equals(ywUserRoom.getUid())) {
            write(getFailed("抱歉, 没有设置管理员的权限!"));
            return;
        }
        boolean admin = ywUserRoomAdminService.containsAdmin(id, uid);
        if (admin) {
            write(getFailed("该用户已经是本房间的管理员!"));
            return;
        }
        YwUserRoomAdmin query = new YwUserRoomAdmin();
        query.setRoomId(id);
        List<YwUserRoomAdmin> admins = ywUserRoomAdminService.getYwUserRoomAdminList(query);
        String max = SysOptionServiceImpl.getValue(LanshaConstant.ROOM_ADMIN_NUM_MAX);
        int maxNum = 8;
        if(StringUtils.isNumeric(max)){
        	maxNum = Integer.parseInt(max);
        }
        if (null != admins && admins.size() >= maxNum) {
            write(getFailed("本房间管理员数量已达到上限!"));
            return;
        }
        YwUser adminUser = ywUserService.getYwUserById(uid);
        if (null == adminUser) {
            write(getFailed("系统错误，添加失败!"));
            return;
        }
        if(adminUser.getId().equals(ywUserRoom.getUid())){
        	write(getFailed("不能设置房间主播为管理员!"));
            return;
        }
        YwUserRoomAdmin entity = new YwUserRoomAdmin();
        entity.setBi(0);
        entity.setCreateTime(getNow());
        entity.setHeadpic(adminUser.getHeadpic());
        entity.setNickname(adminUser.getNickname());
        entity.setRoomId(id);
        entity.setTimeLength(0);
        entity.setUserId(adminUser.getId());
        ywUserRoomAdminService.save(entity, ywUserRoom);
        writeSuccess(null);
    }

    /**
     * 新注册用户首次进入房间赠送鲜花
     *
     * @param loginuser
     * @creationDate. 2016-2-2 下午6:07:45
     */
    public LanshaUserGiftStock firstGiveFlower(YwUser loginUser) {
        //新用户进入房间先赠送鲜花
        int defaultNum = 3;
        return findGiftStock(loginUser.getId(), LanshaConstant.GIFT_ID_TWO, defaultNum);
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
        return findGiftStock(loginUser.getId(), LanshaConstant.GIFT_ID_FOUR, defaultNum);
    }

    /**
     * 查找用户礼物库存数量,找不到初始化库存数
     *
     * @param userId
     * @param giftId
     * @param defaultNum
     * @return
     */
    private LanshaUserGiftStock findGiftStock(String userId, String giftId, int defaultNum) {
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
     * 关注房间
     *
     * @throws IOException
     */
    public void relation() throws IOException {
        YwUser user = getUserLogin();
        if (StringUtils.isEmpty(id)) {
            write(getFailed("房间id不能为空"));
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
            YwUserRoomRelation relation1=relations.get(0);
            if(relation1.getStatus()==0)
            {
                ywUserRoomRelationService.updateSave(relation1);
            }
            else{
            //删除
            ywUserRoomRelationService.delete(new String[]{relation1.getId()},id);
            }
        }

        writeSuccess(null);
    }

    /**
     * 在线人数
     *
     * @throws IOException
     */
    public void online() throws IOException {
        YwUserRoom room = ywUserRoomService.getYwUserRoomById(id);
        JSONObject object = new JSONObject();
        if (room == null) {
            object.put("status", 0);
            object.put("num", 0);
        } else if (room.getOnline() == LanshaConstant.ROOM_STATUS_ONLINE) {
            object.put("status", 1);
            object.put("num", room.getOnLineNumber());
        } else {
            //不在线
            object.put("status", 0);
            object.put("num", room.getOnLineNumber());
        }

        writeSuccess(object);
    }

    /**
     * 日票排名
     *
     * @throws IOException
     */
    public void ticketSort() throws Exception {
        JSONObject object = new JSONObject();
        if (StringUtils.isNotEmpty(id)) {
            String arr[] = lanshaTicketService.sort(id);
            LanshaUserGiftStock ticketGiftStock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID_FIVE, id);
            if(null != ticketGiftStock && null !=  ticketGiftStock.getStock()){
            	ticketSum = ticketGiftStock.getStock();
            }else{
            	ticketSum = 0;
            }
            if (arr.length > 0) {
            	object.put("ticketOrder", arr[0]);
                object.put("ticketNum", arr[1]);
            	object.put("ticketWithPrevious", arr[2]);
            }else {
                object.put("ticketOrder", "-");
                object.put("ticketNum", "-");
                object.put("ticketWithPrevious", "-");
			}
        	object.put("ticketSum", getTicketSum());
        }
        writeSuccess(object);
    }

    /**
     * 领取蓝鲨币
     *
     * @throws IOException
     */
    public void stock() throws IOException {
        getGift(LanshaConstant.GIFT_ID);
    }

    //返回礼物库存
    public static void getErrorMsgAndSum(int biSum, String msg) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("msg", msg);
        obj.put("status", 0);
        obj.put("biSum", biSum);
        write(JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat));
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

        if (histories.isEmpty()) {
            write(getErrorMsg("操作无效"));
            return;
        } else {
            YwUserRoomHistory entity = histories.get(0);
            int diff = (int) ((getNow().getTime() - entity.getCreateTime().getTime()) / 1000);
            if (entity.getTimeLength() == null) {
                write(getErrorMsg("已领取"));
                return;
            } else {
                //获取礼物库
                LanshaUserGiftStock giftStock = lanshaUserGiftStockService.getByGiftIdAndUserId(giftId, user.getId());
                //领取虾米
                if (LanshaConstant.GIFT_ID.equals(giftId)) {
                    int time = getTime();
                    //如果当天未领取过虾米，第一次默认没有倒计时，则不判断时长
                    if (getFirstGetGift()) {
                        if (diff < time) {
                            write(getErrorMsg("观看时间小于" + (time / 60) + "分钟，还不能领取"));
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
                        giftStock.setStock(giftStock.getStock() + bi);
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
                        obj.put("biSum", giftStock.getStock());
                        obj.put("bi", defaultNum);
                        writeSuccess(obj);
                        return;
                    }
                    if (diff < time) {
                        getErrorMsgAndSum(giftStock.getStock(), "观看时间小于" + (time / 60) + "分钟，还不能领取");
                        return;
                    } else {
                        //领取1朵鲜花
                        int bi = 1;
                        entity.setBi(bi);


                        int maxSum = 10;
                        if (giftStock.getStock() >= maxSum) {
                            getErrorMsgAndSum(maxSum, "鲜花库存已达到上限" + maxSum + "，领取失败");
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
            write(getErrorMsg("领取失败"));
        }
//		write("{'status': '1', 'msg': '恭喜您已领取" + bi + "朵鲜花', 'biSum': '"gift.getStock(), "', 'bi': '" + bi + "' }");
        JSONObject obj = new JSONObject();
        obj.put("msg", "恭喜您已领取" + bi + giftName);
        obj.put("biSum", gift.getStock());
        obj.put("bi", bi);
        writeSuccess(obj);
        return;
    }

    /**
     * 昵称
     *
     * @return
     */
    public String getMenberName() {
        return getMenberNameStatic();
    }

    public static String getMenberNameStatic() {
        YwUser users = getUserLoginStatic();
        if (users == null) {
            return Math.random() + "|0|" + Math.random();
        } else {
            return users.getNickname() + "|" + users.getId() + "|" + Math.random();
        }
    }

    /**
     * @throws IOException
     * @throws InterruptedException
     * @Title: giveGift
     * @Description: 赠送虾米
     */
    public void giveGift() throws IOException, InterruptedException {
        gift(LanshaConstant.GIFT_ID, "虾米");
    }

    /**
     * 赠送礼物公共方法
     *
     * @param giftId   礼物id
     * @param giftName 礼物名
     * @throws IOException
     * @creationDate. 2016-1-28 上午11:48:47
     */
    public void gift(final String giftId, String giftName) throws IOException {
        //判断登录
        final YwUser user = getUserLogin();

        //礼物库存
        LanshaUserGiftStock gift = lanshaUserGiftStockService.getByGiftIdAndUserId(giftId, user.getId());
        if (gift == null || gift.getStock() <= 0) {
            getErrorMsgAndSum(0, "抱歉，您没有足够的" + giftName);
            return;
        }
        // 查询房间
        final YwUserRoom room = ywUserRoomService.getYwUserRoomById(id);
        if (room == null) {
            getErrorMsgAndSum(0, "房间不存在");
            return;
        } else if (room.getUid().equals(user.getId())) {
            getErrorMsgAndSum(0, "主播不能送自己" + giftName);
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
            obj.put("biSum", gift.getStock() - 1);
            writeSuccess(obj);

        } catch (RuntimeException e) {
            write(getFailed(e.getMessage()));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            getErrorMsgAndSum(0, "赠送失败");
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

    public void updateIsRead() throws IOException {
        YwUser user = getUserLogin();
        if (user != null) {
            ywUserService.updateIsRead(user.getId());
            //更新session
            user.setIsRead(BaseConstant.YES_S);
            setUserLogin(user);
        }
        writeSuccess(null);
    }
    
    /**
     * 随机跳转到yw_user_room_hot表中的一个在线房间
     * @throws IOException 
     */
    public void randToHotLive() throws IOException{
    	Integer roomId = ywUserRoomHotService.getRandRoomIdWithOnline();
    	if(null == roomId){
        	getResponse().sendRedirect(getContextPath() + "/liveList.html");
    	}else {
    		getResponse().sendRedirect(getContextPath() + AppSetting.getLivePathStatic(roomId));
		}
    }
    
    /**
     * 逛逛随机获取4个房间
     * @throws IOException 
     */
    public void switchRandRooms() throws IOException{
    	List<YwUserRoom> rooms = getRandRooms();
    	if(CollectionUtils.isNotEmpty(rooms)){
    		Map<String,Object> json = new HashMap<String,Object>();
    		json.put("randRoomIds", getRandIds());
    		List<Map<String,String>> roomsInfo = new ArrayList<Map<String,String>>();
    		for(YwUserRoom room: rooms){
    			Map<String,String> roomMap = new HashMap<String,String>();
    			roomMap.put("url", getContextPath() + AppSetting.getLivePathStatic(room.getIdInt()));
    			roomMap.put("img", getUploadPath() + room.getLiveImg());
    			roomMap.put("title", room.getName());
    			roomsInfo.add(roomMap);
    		}
    		json.put("roomList", roomsInfo);
    		writeSuccessWithData(json);
    	}else{
            write(getErrorMsg("获取不到房间数据!"));
    	}
    }

    /**
     * 领取鲜花
     *
     * @throws IOException
     */
    public void flower() throws IOException {
        getGift(LanshaConstant.GIFT_ID_TWO);
    }

    /**
     * @throws IOException
     * @throws InterruptedException
     * @Title: giveFlower
     * @Description: 赠送鲜花
     */
    public void giveFlower() throws IOException, InterruptedException {
        gift(LanshaConstant.GIFT_ID_TWO, "鲜花");
    }

    /**
     * @throws IOException
     * @throws InterruptedException
     * @Title: giveFlower
     * @Description: 赠送日票
     */
    public void giveTicket() throws IOException, InterruptedException {

        getClientIP();
        gift(LanshaConstant.GIFT_ID_FOUR, "日票");
    }

    /**
     * 是否阅读过播放页规则
     */
    public int getFirstCom() {
        YwUser user = getUserLogin();
        if (user == null || (StringUtils.isNotBlank(user.getIsRead()) && BaseConstant.YES_S.equals(user.getIsRead()))) {
            return 0;
        }
        return 1;
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




    public YwUserRoom getRoom() {
        return room;
    }

    public YwUserRoomRelation getRelation() {
        return relation;
    }

    public YwUser getUser() {
        return user;
    }

    public LanshaAdvertisement getLanshaAdvertisement() {
        return lanshaAdvertisement;
    }

    public void setLanshaAdvertisement(LanshaAdvertisement lanshaAdvertisement) {
        this.lanshaAdvertisement = lanshaAdvertisement;
    }

    public YwUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(YwUser loginUser) {
        this.loginUser = loginUser;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public List<YwUserRoom> getRoomList() {
        return roomList;
    }

    public LanshaUserGiftStock getFlowerStock() {
        return flowerStock;
    }

    public void setFlowerStock(LanshaUserGiftStock flowerStock) {
        this.flowerStock = flowerStock;
    }

    public int getMaxSum() {
        return maxSum;
    }

    public void setMaxSum(int maxSum) {
        this.maxSum = maxSum;
    }

    public int getGiveFlowerTime() {
        return giveFlowerTime;
    }

    public void setGiveFlowerTime(int giveFlowerTime) {
        this.giveFlowerTime = giveFlowerTime;
    }

    public String getFlowerSum() {
		if(flowerSum >= SWITCH_NUM){
	        DecimalFormat df1 = new DecimalFormat("#########.0");
			return  df1.format(flowerSum/SWITCH_NUM) + "万";
		}
		return flowerSum + "";
    }
    
    public Integer getFlowerSumNum() {
		return flowerSum;
    }

    public void setFlowerSum(int flowerSum) {
        this.flowerSum = flowerSum;
    }

    public LanshaUserGiftStock getTicketStock() {
        return ticketStock;
    }

    public void setTicketStock(LanshaUserGiftStock ticketStock) {
        this.ticketStock = ticketStock;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTicketOrder() {
        return ticketOrder;
    }

    public void setTicketOrder(String ticketOrder) {
        this.ticketOrder = ticketOrder;
    }

    public String getTicketWithPrevious() {
        return ticketWithPrevious;
    }

    public void setTicketWithPrevious(String ticketWithPrevious) {
        this.ticketWithPrevious = ticketWithPrevious;
    }

    public String getCurrentTicketNum() {
        return currentTicketNum;
    }

    public void setCurrentTicketNum(String currentTicketNum) {
        this.currentTicketNum = currentTicketNum;
    }

	public String getTicketSum() {
		if(ticketSum >= SWITCH_NUM){
	        DecimalFormat df1 = new DecimalFormat("#########.0");
			return  df1.format(ticketSum/SWITCH_NUM) + "万";
		}
		return ticketSum + "";
	}

	public List<YwUserRoomAdmin> getAdminList() {
		return adminList;
	}
    
	public String getFavNum(){
		Integer fans = getRoom().getFans();
		if(null == fans){
			return "0";
		}
		if(fans >= SWITCH_NUM){
	        DecimalFormat df1 = new DecimalFormat("#########.0");
			return  df1.format(fans/SWITCH_NUM) + "万";
		}
		return fans + "";
	}
    
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getUserId(){
		if(null == loginUser){
			return "";
		}else{
			return loginUser.getId();
		}
	}
}
