package com.yaowang.lansha.action.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.yaowang.entity.SysOption;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.constant.MtConstant;
import com.yaowang.common.dao.PageDto;
import com.yaowang.entity.LogMt;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.ActivityUser;
import com.yaowang.lansha.entity.LanshaUserGiftStock;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomApply;
import com.yaowang.lansha.entity.YwUserRoomHistory;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.lansha.service.ActivityGiftStockService;
import com.yaowang.lansha.service.ActivityUserService;
import com.yaowang.lansha.service.LanshaUserGiftStockService;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomApplyService;
import com.yaowang.lansha.service.YwUserRoomHistoryService;
import com.yaowang.lansha.service.YwUserRoomRelationService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.lansha.util.LanshaCommonFunctions;
import com.yaowang.service.LogMtService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.img.ImageUtils;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;

public class UserCenterAction extends LanshaMobileAction {
    private static final long serialVersionUID = -2883164005402314780L;

    @Resource
    private YwUserService ywUserService;
    @Resource
    private YwUserRoomRelationService ywUserRoomRelationService;
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private YwUserRoomHistoryService ywUserRoomHistoryService;
    @Resource
    private LogMtService logMtService;
    @Resource
    private LanshaUserGiftStockService lanshaUserGiftStockService;
    @Resource
    private YwGameService ywGameService;
    @Resource
    private YwUserRoomApplyService ywUserRoomApplyService;
    @Resource
    private ActivityUserService activityUserService;
    @Resource
    private ActivityGiftStockService activityGiftStockService;

    private String nickName;
    private String telphone;
    private Integer sex;
    public String code;

    // ***********APP接口字段
    public String realname;// 真实姓名
    public String identitycard;// 身份证号
    public String expirationTime;// 身份证到期时间
    public String roomName;// 房间名称
    public String gameId;// 所属游戏
    public String notice;// 房间公告

    private String address;//收货地址
    private String qq;//手机号码

    /**
     * 中奖记录
     *
     * @throws IOException
     */
    public void record() throws IOException {
        // 查询条件page
        PageDto page = getPageDto();
        page.setRowNum(20);
        page.setCount(false);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if (page.getCurrentPage() >= 2) {
            //服务端暂时不分页，客户端请求第一页返回所有数据，第二页数据返回空
            writeSuccessWithData(data);
            return;
        }

        YwUser ywUser = getUserLogin();
        if (ywUser == null) {
            write(getFailedLogin());
            return;
        }

        ActivityGiftStock activityGiftStock = new ActivityGiftStock();
        activityGiftStock.setUserId(ywUser.getId());
        //暂不分页，后面改分页加入分页参数查询
        list = activityGiftStockService.getActivityGiftStockPage(activityGiftStock, null);
        if (CollectionUtils.isNotEmpty(list)) {
            activityGiftStockService.setGiftName((List<ActivityGiftStock>) list);
        }

        if (!list.isEmpty()) {
        	// 返回数据
        	ActivityUser activityUser = activityUserService.getActivityUserById(ywUser.getId());
        	for (ActivityGiftStock entity : (List<ActivityGiftStock>) list) {
        		if (StringUtils.isEmpty(activityUser.getQq()) && LanshaConstant.Q_GIFT_ID.equals(entity.getGiftId())) {
        			// qq号码不输入则不显示Q币中奖纪录
        			continue;
        		}
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("date", DateUtils.format(entity.getCreateTime()));
        		//标题
        		String title = entity.getGiftName();
//			if (StringUtils.isNotEmpty(entity.getRemark())) {
//				title += " " + entity.getRemark();
//			}
        		map.put("title", title);
        		
        		if ("0".equals(entity.getStatus())) {
        			map.put("status", "等待发货");
        		} else if ("1".equals(entity.getStatus())) {
        			map.put("status", "已发货");
        		} else if ("2".equals(entity.getStatus())) {
        			//审核不通过，不用返回状态
        			map.put("status", "审核不通过");
        		}
        		map.put("id", entity.getId());
        		map.put("type", entity.getType());
        		map.put("statusM", entity.getStatus());
        		data.add(map);
        	}
		}
        writeSuccessWithData(data);
    }

    /**
     * 收货地址
     *
     * @throws IOException
     */
    public void address() throws IOException {
        YwUser ywUser = getUserLogin();
        if (ywUser == null) {
            write(getFailedLogin());
            return;
        }

        ActivityUser entity = activityUserService.getActivityUserById(ywUser.getId());
        if (entity == null) {
            write(getFailed("您还没有增加收货地址	！"));
            return;
        }

        // 返回结果信息
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("realname", entity.getRealname());
        result.put("address", entity.getAddress());
        result.put("telphone", entity.getTelphone());
        result.put("qq", entity.getQq());
        writerEntity(result);
    }

    /**
     * 保存收货地址
     */
    public void saveAddress() throws IOException {
        ActivityUser entity = new ActivityUser();
        if (StringUtils.isNotEmpty(realname)) {//收件人
            entity.setRealname(realname);
        }

        if (StringUtils.isNotEmpty(address)) {//收货地址
            entity.setAddress(address);
        }

        if (StringUtils.isNotEmpty(telphone)) {//手机号码
            entity.setTelphone(telphone);
        }

        if (StringUtils.isNotEmpty(qq)) {
            entity.setQq(qq);
        }


        YwUser ywUser = getUserLogin();
        if (ywUser == null) {//判断是否登录超时
            write(getFailedLogin());
            return;
        }

        ActivityUser activityUser = activityUserService.getActivityUserById(ywUser.getId());
        entity.setId(ywUser.getId());
        if (activityUser == null) {
            entity.setLimitTime(0);
            entity.setCreateTime(getNow());
            try {
                activityUserService.save(entity, null);
            } catch (Exception e) {
                write(getFailed("保存失败"));
                return;
            }
        } else {
            try {
                activityUserService.updateUserInfo(entity);
            } catch (Exception e) {
                write(getFailed("保存失败"));
                return;
            }
        }

        write(EMPTY_ENTITY);
    }


    /**
     * 我的页面
     *
     * @throws IOException
     * @creationDate. 2015-12-21 上午11:42:07
     */
    public void my() throws IOException {
        YwUser user = getUserLogin();
        YwUserRoomRelation roomRelation = new YwUserRoomRelation();
        roomRelation.setUid(user.getId());
        roomRelation.setStatus(BaseConstant.YES);
        List<YwUserRoomRelation> listRoomRelation = ywUserRoomRelationService.getYwUserRoomRelationPage(roomRelation, null);
        List<YwUserRoom> data = null;
        if (CollectionUtils.isNotEmpty(listRoomRelation)) {
            Set<String> roomIds = new HashSet<String>();
            for (YwUserRoomRelation ywUserRoomRelation : listRoomRelation) {
                roomIds.add(ywUserRoomRelation.getRoomId());
            }
            PageDto page = new PageDto();
            page.setCurrentPage(1);
            page.setRowNum(2);
            page.setCount(false);
            data = ywUserRoomService.getYwUserRoomByIds(roomIds.toArray(new String[]{}), page);
            if (CollectionUtils.isNotEmpty(data)) {
                ywUserRoomService.setUserName(data);
                ywUserRoomService.setGameName(data);
            }
        }
        JSONObject list = new JSONObject();
        JSONObject m = new JSONObject();
        List<Object> roomlist = new ArrayList<Object>();
        if (!CollectionUtils.isEmpty(data)) {
            for (YwUserRoom room : data) {
                JSONObject mm = new JSONObject();
                String liveImg = room.getLiveImg();
                mm.put("id", room.getId());
                mm.put("img", getUploadFilePath(liveImg));
                mm.put("name", room.getName());
                mm.put("number", room.getOnLineNumber() == null ? "0" : room.getOnLineNumber().toString());
                mm.put("nickName", room.getNickname());
                mm.put("gameName", room.getGameName());
                mm.put("online", room.getOnline() == null ? "0" : room.getOnline().toString());
                mm.put("headImg", getUploadFilePath(room.getUserIcon()));
                roomlist.add(mm);
            }
        }
        String icon = user.getHeadpic();
        m.put("icon", StringUtils.isBlank(icon) ? getStaticFilePath("/static/lansha/upload/default.png") : getUploadFilePath(icon));
        // 虾米库存
        LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, user.getId());
        m.put("bi", stock == null ? "0" : stock.getStock().toString());
        m.put("nickName", user.getNickname());
        m.put("idInt", user.getIdInt() == null ? "" : user.getIdInt().toString());
        m.put("relation", roomlist);
        // 申请主播状态(0：未审核，1：审核通过，2：审核不通过，空：未提交过审核信息)
        // 主播申请信息
        PageDto page = getPageDto();
        page.setCount(false);
        page.setCurrentPage(1);
        page.setRowNum(1);
        YwUserRoomApply searchApply = new YwUserRoomApply();
        searchApply.setUserId(user.getId());
        List<YwUserRoomApply> applylist = ywUserRoomApplyService.getYwUserRoomApplyPage(searchApply, null, null, page);
        if (CollectionUtils.isNotEmpty(applylist)) {
            m.put("applyStatus", applylist.get(0).getStatus());
        } else {
            m.put("applyStatus", "");
        }

        list.put("data", m);
        list.put("status", 1);
        write(list);
    }

    /**
     * 历史记录
     *
     * @throws IOException
     * @creationDate. 2015-12-21 下午12:02:22
     */
    public void history() throws IOException {
        YwUser user = getUserLogin();
        YwUserRoomHistory roomHistory = new YwUserRoomHistory();
        roomHistory.setUid(user.getId());
        PageDto page = new PageDto();
        page.setCurrentPage(1);
        page.setRowNum(20);
        page.setCount(false);

        List<String> roomIds = ywUserRoomHistoryService.getRoomIdPage(roomHistory, page);
        List<YwUserRoom> data = new ArrayList<YwUserRoom>();
        if (CollectionUtils.isNotEmpty(roomIds)) {
            Map<String, YwUserRoom> mapUserRoom = ywUserRoomService.getYwUserRoomMapByIds(roomIds.toArray(new String[]{}));
            for (String roomId : roomIds) {
                if (mapUserRoom.containsKey(roomId)) {
                    data.add(mapUserRoom.get(roomId));
                }
            }
            if (CollectionUtils.isNotEmpty(data)) {
                ywUserRoomService.setUserName(data);
                ywUserRoomService.setGameName(data);
            }
        }
        JSONObject map = new JSONObject();
        List<Object> roomlist = new ArrayList<Object>();
        if (!CollectionUtils.isEmpty(data)) {
            for (YwUserRoom room : data) {
                Map<String, Object> mm = new HashMap<String, Object>();
                String liveImg = room.getLiveImg();
                mm.put("id", room.getId());
                mm.put("img", getUploadFilePath(liveImg));
                mm.put("name", room.getName());
                mm.put("number", room.getOnLineNumber() == null ? "0" : room.getOnLineNumber().toString());
                mm.put("nickName", room.getNickname());
                mm.put("gameName", room.getGameName());
                mm.put("online", room.getOnline() == null ? "0" : room.getOnline().toString());
                mm.put("headImg", getUploadFilePath(room.getUserIcon()));
                roomlist.add(mm);
            }
        }
        map.put("status", 1);
        map.put("data", roomlist);
        write(map);
    }

    /**
     * 我的订阅
     *
     * @throws IOException
     * @creationDate. 2015-12-21 下午12:05:22
     */
    public void relation() throws IOException {
        YwUser user = getUserLogin();
        YwUserRoomRelation roomRelation = new YwUserRoomRelation();
        roomRelation.setUid(user.getId());
        roomRelation.setStatus(BaseConstant.YES);
        List<YwUserRoomRelation> listRoomRelation = ywUserRoomRelationService.getYwUserRoomRelationPage(roomRelation, null);

        List<YwUserRoom> data = null;
        if (CollectionUtils.isNotEmpty(listRoomRelation)) {
            Set<String> roomIds = new HashSet<String>();
            for (YwUserRoomRelation ywUserRoomRelation : listRoomRelation) {
                roomIds.add(ywUserRoomRelation.getRoomId());
            }

            PageDto page = new PageDto();
            page.setCurrentPage(1);
            page.setRowNum(20);
            page.setCount(false);
            data = ywUserRoomService.getYwUserRoomByIds(roomIds.toArray(new String[]{}), page);
            if (CollectionUtils.isNotEmpty(data)) {
                ywUserRoomService.setUserName(data);
                ywUserRoomService.setGameName(data);
            }
        }
        JSONObject map = new JSONObject();
        List<Object> roomlist = new ArrayList<Object>();
        if (!CollectionUtils.isEmpty(data)) {
            for (YwUserRoom room : data) {
                Map<String, Object> mm = new HashMap<String, Object>();
                String liveImg = room.getLiveImg();
                mm.put("id", room.getId());
                mm.put("img", getUploadFilePath(liveImg));
                mm.put("name", room.getName());
                mm.put("number", room.getOnLineNumber() == null ? "0" : room.getOnLineNumber().toString());
                mm.put("nickName", room.getNickname());
                mm.put("gameName", room.getGameName());
                mm.put("online", room.getOnline() == null ? "0" : room.getOnline().toString());
                mm.put("headImg", getUploadFilePath(room.getUserIcon()));
                roomlist.add(mm);
            }
        }
        map.put("status", 1);
        map.put("data", roomlist);
        write(map);
    }

    /**
     * 资料编辑
     *
     * @throws IOException
     * @creationDate. 2015-12-21 下午2:59:44
     */
    public void info() throws IOException {
        YwUser u = getUserLogin();
        JSONObject map = new JSONObject();
        JSONObject user = new JSONObject();
        String img = u.getHeadpic();
        user.put("icon", StringUtils.isBlank(img) ? getStaticFilePath("/static/lansha/upload/default.png") : getUploadFilePath(img));
        user.put("nickName", StringUtils.isBlank(u.getNickname()) ? "" : u.getNickname());
        user.put("telphone", u.getMobile());
        user.put("sex", u.getSex() == null ? "0" : u.getSex().toString());
        map.put("status", 1);
        map.put("data", user);
        write(map);
    }

    /**
     * 保存资料
     *
     * @throws IOException
     * @creationDate. 2015-12-21 下午3:09:19
     */
    public void infoSave() throws IOException {
        // 当前登录用户ID
        YwUser u = getUserLogin();

        //判断昵称
        YwUser user = new YwUser();
        user.setNickname(nickName);
        /*if(u.getNickname().equals(user.getNickname())){
            write(getFailed("您已使用此昵称"));
			return;
		}*/

        if (LanshaCommonFunctions.matchNickKeywords(user.getNickname())) {
            write(getFailed("昵称请勿包含非法字符"));
            return;
        }

        if ((!user.getNickname().matches("^[\u4E00-\u9FA50-9A-Za-z_]+$")) || LanshaCommonFunctions.judgeCharsLength(user.getNickname()) > 16) {
            write(getFailed("请输入您的昵称(8位汉字或16位字母数字下划线的组合"));
            return;
        }

        List<String> userList = ywUserService.getYwUserAllId(user);
        for (String id : userList) {
            if (!id.equals(u.getId())) {
                write(getFailed("昵称已存在，请勿重复使用"));
                return;
            }
        }

        //user.setNickname(nickName);
        user.setMobile(telphone);
        user.setSex(sex);
        user.setId(u.getId());
        ywUserService.updateForBase(user);
        JSONObject map = new JSONObject();
        map.put("status", 1);
        map.put("data", new Object());
        write(map);
    }

    /**
     * 头像上传
     *
     * @throws IOException
     * @creationDate. 2015-12-21 下午2:16:26
     */
    public void infoIco() throws IOException {
        YwUser u = getUserLogin();
        try {
            // 上传图片处理
            UploadFile[] files = UploadUtils.handleFileUpload();
            UploadFile file = files[0];

            String ext = FileUtil.getExtensionName(file.getFileName());
            long currentTime = System.currentTimeMillis();
            // 文件地址--原图
            String filePath = StorePathUtil.buildPath(u.getId(), "user", "headpic" + currentTime + "_original." + ext).toString();
            FileSystemUtil.saveFile(file.getFile(), filePath);

            //压缩图
            ImageUtils.changeSize(file.getFile().getAbsolutePath(), -1, -1);
            filePath = StorePathUtil.buildPath(u.getId(), "user", "headpic" + currentTime + "." + ext).toString();
            FileSystemUtil.saveFile(file.getFile(), filePath);
            //设置头像地址
            u.setHeadpic(filePath);

            //小图
            ImageUtils.changeSize(file.getFile().getAbsolutePath(), 45, 45);
            filePath = StorePathUtil.buildPath(u.getId(), "user", "headpic" + currentTime + "_small." + ext).toString();
            FileSystemUtil.saveFile(file.getFile(), filePath);

            ywUserService.update(u);
            JSONObject object = new JSONObject();
            JSONObject user = new JSONObject();
            object.put("icon", getUploadFilePath(filePath));
            user.put("data", object);
            user.put("status", 1);
            write(user);
        } catch (Exception e) {
            write(getFailed("上传失败"));
        }
    }

    /**
     * @throws IOException
     * @Description: 手机绑定
     */
    public void mobileBand() throws IOException {
        if (StringUtils.isBlank(token)) {
            write(getError("token不能为空"));
            return;
        }
        // 根据token获取用户信息
        YwUser user = ywUserService.getYwusersByToken(token);
        if (user == null) {
            write(TIMEOUT_STRING);
            return;
        }
        if (StringUtils.isEmpty(telphone)) {
            write(getError("手机号不能为空"));
            return;
        }
        if (StringUtils.isBlank(code)) {
            write(getError("验证码不能为空"));
            return;
        }
        Boolean b = testMt(telphone);
        if (!b) {
            return;
        }

        if (StringUtils.isNotBlank(user.getMobile())) {
            write(getFailed("手机号已绑定！"));
            return;
        }
        YwUser ywUser = ywUserService.getYwusersByUsername(telphone, false);
        if (ywUser != null && !user.getId().equals(ywUser.getId())) {
            write(getFailed("手机号已被使用！"));
            return;
        }

        try {
            user.setUsername(telphone);
            user.setMobile(telphone);
            ywUserService.update(user);
            // ywUserService.updateMobile(user.getId(), telphone);
            write(EMPTY_ENTITY);
        } catch (Exception e) {
            write(getFailed("绑定手机失败！"));
        }
    }

    /**
     * @throws IOException
     * @Description: 主播申请
     */
    public void becomeanchor() throws IOException {
        YwUser user = getUserLogin();
        // 返回结果
        Map<String, Object> result = new HashMap<String, Object>();

        // 获取游戏信息
        YwGame ywGame = new YwGame();
        ywGame.setStatus(LanshaConstant.STATUS_ONLINE);
        List<Map<String, Object>> listMapGame = ywGameService.getYwGameListMap(ywGame);
        result.put("gameList", listMapGame);

        // 主播申请信息
        PageDto page = getPageDto();
        page.setCount(false);
        page.setCurrentPage(1);
        page.setRowNum(1);
        YwUserRoomApply searchApply = new YwUserRoomApply();
        searchApply.setUserId(user.getId());
        List<YwUserRoomApply> list = ywUserRoomApplyService.getYwUserRoomApplyPage(searchApply, null, null, page);
        if (CollectionUtils.isNotEmpty(list)) {
            // 主播信息已存在
            YwUserRoomApply userApply = list.get(0);

            // 获取游戏名称
            String gameId = userApply.getGameId();
            String gameName = "";
            if (CollectionUtils.isNotEmpty(listMapGame)) {
                for (Map<String, Object> map : listMapGame) {
                    String bgameId = map.get("gameId").toString();
                    if (bgameId.equals(gameId)) {
                        gameName = map.get("gameName").toString();
                    }
                }
            }

            // 设置返回信息
            result.put("gameId", gameId);
            result.put("gameName", gameName);
            result.put("notice", userApply.getNotice());
            result.put("status", userApply.getStatus());
            result.put("remark", userApply.getRemark());
            result.put("roomName", userApply.getRoomName());
            //分别判断android和ios版本,ostype区分操作系统
            if (StringUtils.isNotBlank(ostype) && StringUtils.isNotBlank(version)
                    && ((LanshaConstant.OSTYPE_ANDROID.equals(ostype) && "1.03".compareTo(version) <= 0)
                    || (LanshaConstant.OSTYPE_IOS.equals(ostype) && "1.03".compareTo(version) <= 0))) {
                //1.03版本及以后版本
                result.put("pic1", getHostContextPath("/mobile/user/becomeanchor-pic.html?name=1&id=" + userApply.getId()));
                result.put("pic2", getHostContextPath("/mobile/user/becomeanchor-pic.html?name=2&id=" + userApply.getId()));
                result.put("pic3", getHostContextPath("/mobile/user/becomeanchor-pic.html?name=3&id=" + userApply.getId()));
            } else {
                //低于1.03版本
                result.put("pic1", getUploadFilePath(userApply.getPic1()));
                result.put("pic2", getUploadFilePath(userApply.getPic2()));
                result.put("pic3", getUploadFilePath(userApply.getPic3()));
            }
            result.put("telphone", user.getMobile() == null ? "" : user.getMobile());
            result.put("expirationTime", userApply.getExpirationTime() == null ? "" : DateUtils.format(userApply.getExpirationTime()));

            if (LanshaConstant.MASTER_STATUS_VETTED.equals(userApply.getStatus())) {
                // 审核通过
                StringBuffer str = new StringBuffer(userApply.getRealname());
                result.put("realname", str.replace(0, 1, "*").toString());
                StringBuffer sb = new StringBuffer(userApply.getIdentitycard());
                int s = userApply.getIdentitycard().length();
                sb.replace(2, s - 2, "**********");
                result.put("identitycard", sb.toString());
            } else {
                result.put("realname", userApply.getRealname());
                result.put("identitycard", userApply.getIdentitycard());
            }
        } else {
            // 设置未申请主播返回信息
            result.put("pic1", "");
            result.put("pic2", "");
            result.put("pic3", "");
            result.put("gameId", "");
            result.put("status", "");
            result.put("remark", "");
            result.put("gameName", "");
            result.put("realname", "");
            result.put("roomName", "");
            result.put("identitycard", "");
            result.put("expirationTime", "");
            result.put("notice", "欢迎来到我的直播间，喜欢记得点关注哟~");
            result.put("telphone", user.getMobile() == null ? "" : user.getMobile());
        }

        writerEntity(result);
    }


    /**
     * 图片
     *
     * @throws Exception
     * @throws IOException
     */
    public void pic() throws IOException, Exception {
        YwUserRoomApply apply = ywUserRoomApplyService.getYwUserRoomApplyById(id);
        if (apply == null || !getUserLogin().getId().equals(apply.getUserId())) {
            return;
        }
        //输出文件
        apply.writerPic(getResponse().getOutputStream(), name);
    }

    /**
     * @throws Exception
     * @Description: 保存主播申请信息
     */
    public void becomeanchorsave() throws Exception {
        YwUser user = getUserLogin();
        String userId = user.getId();

        if (StringUtils.isBlank(realname)) {
            write(getFailed("请输入真实的姓名"));
            return;
        }
        if (StringUtils.isBlank(identitycard)) {
            write(getFailed("请输入18位身份证号码"));
            return;
        }
        // 上传图片处理
        UploadFile[] files = UploadUtils.handleFileUpload();
        if (files != null && files.length > 0) {
            for (UploadFile file : files) {
                if (!file.getContentType().matches("image/.+")) {
                    write(getFailed("请上传正确的图片文件"));
                    return;
                }
            }
        }

        if (StringUtils.isBlank(roomName)) {
            write(getFailed("请填写房间名称"));
            return;
        }
        // 房间名称重复检查
        YwUserRoom userRoom = new YwUserRoom();
        userRoom.setName(roomName);
        List<YwUserRoom> lsitUserRoom = ywUserRoomService.listYwUserRoomList(userRoom, null);
        if (CollectionUtils.isNotEmpty(lsitUserRoom) && !userId.equals(lsitUserRoom.get(0).getUid())) {
            write(getFailed("房间名称【" + roomName + "】被占用，请换重新填写房间名称"));
            return;
        }
        if (StringUtils.isBlank(gameId)) {
            write(getFailed("请选择游戏"));
            return;
        }
        // 设置默认直播间公告信息
        if (StringUtils.isBlank(notice)) {
            notice = "欢迎来到我的直播间，喜欢记得点关注哟~";
        }

        // 房间对象
        YwUserRoomApply userApply = null;

        // 根据当前登录用户获取主播申请信息
        PageDto page = getPageDto();
        page.setCount(false);
        page.setCurrentPage(1);
        page.setRowNum(1);
        YwUserRoomApply searchApply = new YwUserRoomApply();
        searchApply.setUserId(userId);
        List<YwUserRoomApply> listRoomApply = ywUserRoomApplyService.getYwUserRoomApplyPage(searchApply, null, null, page);

        if (CollectionUtils.isEmpty(listRoomApply)) {
            userApply = new YwUserRoomApply();
        } else {
            // 防止app端未传过来的参数被更新成空，需要从数据库取一次数据
            userApply = listRoomApply.get(0);
        }

        userApply.setRealname(realname);
        userApply.setIdentitycard(identitycard);
        userApply.setRoomName(roomName);
        userApply.setGameId(gameId);
        userApply.setNotice(notice);
        userApply.setExpirationTime(DateUtils.toDate(expirationTime));

        if (StringUtils.isBlank(userApply.getId())) {
            // 新增
            userApply.setUserId(userId);
            userApply.setCreateTime(getNow());
            userApply.setStatus(LanshaConstant.MASTER_STATUS_AUDITING);
            userApply.setUsername(user.getUsername());
            userApply.setId(UUIDUtils.newId());
            // 上传文件
            try {
                upload(userApply, files);
            } catch (Exception e) {
                e.printStackTrace();
                write(getFailed("上传照片失败"));
                return;
            }

            try {
                ywUserRoomApplyService.save(userApply);
            } catch (Exception e) {
                write(getFailed("申请失败，请检查信息是否正确"));
                return;
            }
        } else {
            // 上传文件
            try {
                upload(userApply, files);
            } catch (Exception e) {
                e.printStackTrace();
                write(getFailed("上传照片失败"));
                return;
            }
            // 修改
            // userApply.setId(id);
            userApply.setStatus(LanshaConstant.MASTER_STATUS_AUDITING);
            try {
                ywUserRoomApplyService.updateForApply(userApply);
            } catch (Exception e) {
                write(getFailed("申请失败，请检查信息是否正确"));
                return;
            }
        }
        write(EMPTY_ENTITY);
    }

    /***
     * 申请进度查看借口
     * @throws IOException
     */
    public void applicationStatus() throws IOException {
        YwUser user = getUserLogin();
        String userId = user.getId();
        YwUserRoomApply ywUserRoomApply = new YwUserRoomApply();
        ywUserRoomApply.setUserId(userId);
        List<YwUserRoomApply> listRoomApply = ywUserRoomApplyService.getYwUserRoomApplyList(ywUserRoomApply);
        if (CollectionUtils.isEmpty(listRoomApply)) {

            write(getFailed("没有找到数据"));
        } else {
            ywUserRoomApply = listRoomApply.get(0);
            SysOption sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.LIVE_IOS");
            String lanshaAppIos = sysOption.getNowvalue();

            sysOption = sysOptionService.getSysOptionByIniid("LANSHA.APP.LIVE_ANDROID");
            String lanshaAppAndroid = sysOption.getNowvalue();
            JSONObject object = new JSONObject();
            object.put("status", 1);
            JSONObject data = new JSONObject();
            data.put("status", ywUserRoomApply.getStatus());
            data.put("realname", ywUserRoomApply.getRealname());
            StringBuffer sb = new StringBuffer(ywUserRoomApply.getIdentitycard());
            int s = ywUserRoomApply.getIdentitycard().length();
            sb.replace(2, s-2, "**********");
            data.put("identitycard", sb.toString());
            data.put("info", "系统支持：安卓5.0及以上,ios9.0以下");
            data.put("iosUrl", lanshaAppIos);
            data.put("authDate", DateUtils.format(ywUserRoomApply.getCreateTime()));
            data.put("androidUrl", lanshaAppAndroid);
            object.put("data", data);
            write(object);
        }
    }

    /**
     * @param userApply
     * @param files
     * @throws Exception
     * @Description: 身份证图片上传辅助类
     */
    private void upload(YwUserRoomApply userApply, UploadFile[] files) throws Exception {
        if (files == null || files.length < 1) {
            return;
        }
        UploadFile pic1 = null;// 手持
        UploadFile pic2 = null;// 正面
        UploadFile pic3 = null;// 反面

        for (UploadFile file : files) {
            if ("pic1".equals(file.getFieldName())) {
                pic1 = file;
            } else if ("pic2".equals(file.getFieldName())) {
                pic2 = file;
            } else if ("pic3".equals(file.getFieldName())) {
                pic3 = file;
            }
        }

        String ext, destPath;
        if (pic1 != null) {
            // 身份证正面照
            ext = FileUtil.getExtensionName(pic1.getFileName());
            destPath = StorePathUtil.buildPath(userApply.getId(), "user_room_apply", UUIDUtils.newId() + "." + ext).toString();
            FileSystemUtil.saveFile(pic1.getFile(), destPath);
            userApply.setPic1(destPath);
        }
        if (pic2 != null) {
            // 身份证反面照
            ext = FileUtil.getExtensionName(pic2.getFileName());
            destPath = StorePathUtil.buildPath(userApply.getId(), "user_room_apply", UUIDUtils.newId() + "." + ext).toString();
            FileSystemUtil.saveFile(pic2.getFile(), destPath);
            userApply.setPic2(destPath);
        }
        if (pic3 != null) {
            // 手持身份证照
            ext = FileUtil.getExtensionName(pic3.getFileName());
            destPath = StorePathUtil.buildPath(userApply.getId(), "user_room_apply", UUIDUtils.newId() + "." + ext).toString();
            FileSystemUtil.saveFile(pic3.getFile(), destPath);
            userApply.setPic3(destPath);
        }
    }

    /**
     * 验证手机验证码
     *
     * @param telphone手机号
     * @param rcode短信验证码
     */
    protected Boolean testMt(String telphone) throws IOException {
        if (BaseConstant.PERMANENT_CODE.equals(code)) {
            // 万能验证码
            return true;
        }
        // 根据手机号去发送的验证码
        LogMt logMt = logMtService.getLogMtByTelphone(telphone);
        if (logMt == null) {
            write(getFailed("请先获取验证码"));
            return false;
        }
        if (!(logMt.getCode().equals(code))) {
            write(getFailed("请输入正确的验证码"));
            return false;
        }
        // 验证码有效期10分钟
        if ((getNow().getTime() - logMt.getTime().getTime()) > MtConstant.defaultTime * 120 * 1000) {
            // 有效期超过设定的时间
            write(getFailed("验证码超过了有效期"));
            return false;
        }
        // 更新验证码状态
        logMt.setStatus("1");
        logMtService.updateStatus(logMt);

        return true;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setIdentitycard(String identitycard) {
        this.identitycard = identitycard;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
