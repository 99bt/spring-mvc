package com.yaowang.lansha.action.mobile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yaowang.lansha.entity.*;
import com.yaowang.lansha.service.*;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.util.MD5;
import com.yaowang.util.UUIDUtils;

public class LivePluginAction extends LanshaMobileAction {
    private static final long serialVersionUID = 1L;
    @Resource
    private YwUserService ywUserService;
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private YwGameService ywGameService;
    @Resource
    private YwGameTypeService ywGameTypeService;
    @Resource
    private LanshaUserGiftStockService lanshaUserGiftStockService;
    @Resource
    private YwUserRoomRelationService ywUserRoomRelationService;

    @Resource
    private YwUserRoomDayDataService ywUserRoomDayDataService;

    @Resource
    private LanshaLiveHistoryService lanshaLiveHistoryService;

    @Resource
    private YwUserRoomContractService ywUserRoomContractService;
    @Resource
    private LanshaRoomRankingService lanshaRoomRankingService;

    private String username;
    private String pwd;
    public static final Integer VALID = 3600 * 4;
//	private String ostype;//1:android,2:ios

    /**
     * @throws IOException
     * @Title: liveLogin
     * @Description: 主播推流登录
     */
    public void liveLogin() throws IOException {
        if (StringUtils.isBlank(username)) {
            write(getFailed("用户名不能空"));
            return;
        }
        if (StringUtils.isBlank(pwd)) {
            write(getFailed("密码不能空"));
            return;
        }
        //判断用户名
        YwUser user = ywUserService.getYwusersByUsername(username, false);
        if (user == null) {
            write(getFailed("用户名或密码不正确"));
            return;
        }
        //判断密码
        if (!user.getPassword().equals(MD5.encrypt(pwd))) {
            write(getFailed("用户名或密码不正确"));
            return;
        }
        if (!user.getUserStatus().equals(LanshaConstant.USER_STATUS_NORMAL)) {
            write(getFailed("账号异常"));
            return;
        }
        //判断是否主播
        YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(user.getId());
        if (room == null) {
            write(getFailed("还不是主播"));
            return;
        }
        if (LanshaConstant.ROOM_STATUS_BANNED.equals(room.getOnline()) || LanshaConstant.ROOM_STATUS_DELETE.equals(room.getOnline())) {
            write(getFailed("房间异常"));
            return;
        }

        //登录
        user.setToken(UUIDUtils.newId());
        int count = ywUserService.update(user);
        if (count <= 0) {
            write(getFailed("登录失败请重试"));
            return;
        }
        //如果已经在直播,断流
        if (LanshaConstant.ROOM_STATUS_ONLINE.equals(room.getOnline())) {
            ywUserRoomService.updateBySql(new String[]{room.getId()}, LanshaConstant.ROOM_STATUS_OFFLINE, null);
        }
        Map<String, Object> datamap = setUserInfo(user, room);
        writeSuccessWithData(datamap);
    }

    /**
     * @throws IOException
     * @Title: liveLogin
     * @Description: 主播推流登录
     */
    public void info() throws IOException {
        //主播
        YwUser user = getUserLogin();
        //判断是否主播
        YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(user.getId());
        if (room == null) {
            write(getFailed("还不是主播"));
            return;
        }
        Map<String, Object> datamap = setUserInfo(user, room);
        writeSuccessWithData(datamap);
    }

    //设置用户信息
    private Map<String, Object> setUserInfo(YwUser user, YwUserRoom room) {
        //设置返回数据
        Map<String, Object> datamap = new HashMap<String, Object>();
        datamap.put("id", user.getId());
        datamap.put("username", user.getUsername());
        datamap.put("nickName", user.getNickname());
        datamap.put("icon", getUploadFilePath(user.getHeadpic()));
        //分享地址(用户推广id)
        datamap.put("url", getHostContextPath("/live.html?id=" + room.getIdInt() + "&pn=" + user.getId()));
        datamap.put("room", room.getIdInt().toString());
        datamap.put("token", user.getToken());
        datamap.put("roomId", room.getIdInt().toString());
        datamap.put("roomGuid", room.getId());
        //虾米库存
        LanshaUserGiftStock stock = lanshaUserGiftStockService.getByGiftIdAndUserId(LanshaConstant.GIFT_ID, user.getId());
        datamap.put("bi", stock == null ? "0" : stock.getStock().toString());
        //订阅人数
        YwUserRoomRelation entity = new YwUserRoomRelation();
        entity.setRoomId(room.getId());
        Integer relations = ywUserRoomRelationService.getSumRelationNumber(entity);
        datamap.put("relation", String.valueOf(relations));

        return datamap;
    }

    /**
     * @throws IOException
     * @Title: gameList
     * @Description: 游戏列表
     */
    public void gameList() throws IOException {
        YwGame searchGame = new YwGame();
        searchGame.setStatus(LanshaConstant.STATUS_ONLINE);
        List<YwGame> list = ywGameService.getYwGamePage(searchGame, null, null, null, null, null);
        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isNotEmpty(list)) {
            //按照类别分类
            Set<String> typeIds = new HashSet<String>();
            Map<String, List<YwGame>> gameMap = new HashMap<String, List<YwGame>>();
            List<YwGame> templist = null;
            for (YwGame entity : list) {
                String typeId = entity.getTypeId();
                if (StringUtils.isBlank(entity.getTypeId())) {
                    typeId = "null";
                }
                typeIds.add(typeId);
                if (gameMap.containsKey(typeId)) {
                    templist = gameMap.get(typeId);
                } else {
                    templist = new ArrayList<YwGame>();
                }
                templist.add(entity);
                gameMap.put(typeId, templist);
            }

            //设置返回数据
            Map<String, YwGameType> typemap = ywGameTypeService.getYwGameTypeMapByIds(typeIds.toArray(new String[]{}));
            for (String typeId : typeIds) {
                Map<String, Object> datamap = new HashMap<String, Object>();
                //分类名称
                if (typemap.containsKey(typeId)) {
                    datamap.put("name", typemap.get(typeId).getName());
                } else {
                    datamap.put("name", "无分类");
                }
                //分类下的游戏信息
                List<YwGame> gamelist = gameMap.get(typeId);
                List<Map<String, Object>> datalist1 = new ArrayList<Map<String, Object>>();
                for (YwGame entity : gamelist) {
                    Map<String, Object> datamap1 = new HashMap<String, Object>();
                    datamap1.put("id", entity.getId());
                    datamap1.put("name", entity.getName());

                    datalist1.add(datamap1);
                }
                datamap.put("datas", datalist1);

                datalist.add(datamap);
            }
        }
        writeSuccessWithData(datalist);
    }

    /**
     * @throws IOException
     * @Title: userInfo
     * @Description: 直播信息
     */
    public void userInfo() throws IOException {
        //主播
        YwUser user = getUserLogin();
        //主播房间信息
        YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(user.getId());
        if (room == null) {
            write(getFailed("还不是主播"));
            return;
        }
        //直播游戏
        YwGame game = ywGameService.getYwGameById(room.getGameId());
        //设置返回数据
        Map<String, Object> datamap = new HashMap<String, Object>();
        datamap.put("notice", room.getNotice());
        datamap.put("roomName", room.getName());
        datamap.put("roomId", room.getIdInt());
        if (game != null) {
            datamap.put("gameName", game.getName());
            datamap.put("gameId", game.getId());
        } else {
            datamap.put("gameName", "");
            datamap.put("gameId", "");
        }
        datamap.put("online", room.getOnline() == null ? "0" : room.getOnline().toString());//直播状态

        writeSuccessWithData(datamap);
    }

    /**
     * @throws IOException
     * @Title: saveInfo
     * @Description: 直播信息保存
     */
    public void saveInfo() throws IOException {
        HttpServletRequest request = getRequest();
        String notice = request.getParameter("notice");
        String roomName = request.getParameter("roomName");
        String gameId = request.getParameter("gameId");

        YwUser user = getUserLogin();
        //主播房间信息
        YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(user.getId());
        if (room == null) {
            write(getFailed("还不是主播"));
            return;
        }
//		//如果房间已经再直播，不能修改信息
//		if(room.getOnline() != null && LanshaConstant.ROOM_STATUS_ONLINE.equals(room.getOnline())){
//			write(getFailed("已经在直播，不能修改信息"));
//			return ;
//		}
        //修改直播游戏
        if (StringUtils.isNotBlank(gameId)) {
            //主播游戏
            YwGame game = ywGameService.getYwGameById(gameId);
            if (game == null) {
                write(getFailed("游戏不存在"));
                return;
            }
            room.setGameId(gameId);
        }
        //修改直播间公告
        if (StringUtils.isNotBlank(notice)) {
            room.setNotice(notice);
        }
        //修改直播间名称
        if (StringUtils.isNotBlank(roomName)) {
            room.setName(roomName);
        }

        //修改直播间信息
        room.setUpdateTime(getNow());
        ywUserRoomService.update(room);
        //设置返回数据
        writeSuccessWithData(EMPTY_DATA_ENTITY);
    }

    /**
     * @throws IOException
     * @Title: live
     * @Description: 进入直播间
     */
    public void live() throws IOException {
        YwUser user = getUserLogin();
        //主播房间信息
        YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(user.getId());
        if (room == null) {
            write(getFailed("还不是主播"));
            return;
        }
        //如果房间已经再直播,修改直播状态,不再限制主播不能进入房间
        if (room.getOnline() != null && LanshaConstant.ROOM_STATUS_ONLINE.equals(room.getOnline())) {
            ywUserRoomService.updateBySql(new String[]{room.getId()}, LanshaConstant.ROOM_STATUS_OFFLINE, null);
//			write(getFailed("已经在直播"));
//			return ;
        }
        //设置返回数据
        Map<String, Object> datamap = new HashMap<String, Object>();
        datamap.put("rtmp", room.getRtmp() + room.getRoomId());
        datamap.put("imUrl", room.getOpenfirePath());
        datamap.put("imPort", room.getOpenfirePort());
        datamap.put("imRoom", room.getOpenfireRoom());
        datamap.put("imConference", room.getOpenfireConference());
        datamap.put("baseNumber", room.getBaseNumber() == null ? "0" : room.getBaseNumber().toString());//基数
        datamap.put("multipleNumber", (room.getMultipleNumber() == null || room.getMultipleNumber() == 0) ? "1" : room.getMultipleNumber().toString());//倍数

        writeSuccessWithData(datamap);
    }

    /**
     * 直播信息
     *
     * @throws IOException
     */
    public void liveInfo() throws IOException, ParseException {
        YwUser user = getUserLogin();
        //主播房间信息
        YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(user.getId());
        if (room == null) {
            write(getFailed("还不是主播"));
            return;
        }

        YwUserRoomDayData entity = new YwUserRoomDayData();
        entity.setDay(DateUtils.getDate(getNow()));
        entity.setUserId(user.getId());
        Map<String, String> datamap = new HashMap<String, String>();

        datamap.put("show", "1");
        datamap.put("type", room.getSign());


        String playTime = "";
        String totalPlayTime = "";
        //累计收益
        String totalSalary = "0";
        //奖金
        String totalBonus = "0";
        //罚金
        String totalForfeit = "0";
        //最终收益
        String lastSalary = "0";
        int arr[] = getLiveDetailReport(room.getId());
        int totalTimeLength = arr[0];
        int timeLength = arr[1];

        if (timeLength < VALID) {
            playTime = DateUtils.secondToTime(timeLength);
        } else {
            playTime = DateUtils.secondToTime(VALID);
        }
        totalPlayTime = DateUtils.secondToTime(totalTimeLength);

        datamap.put("time", playTime);//有效播放时长
        datamap.put("liveSumTime", totalPlayTime); //播放总时长
        datamap.put("today", "");
        if (room.getSign().equals("0")) { //非签约主播
            YwUserRoomDayData dayData = ywUserRoomDayDataService.getYwUserRoomDayData(entity);
            //计算本月奖金 罚金
            int year = DateUtils.getYear(getNow());
            int nowMonth = DateUtils.getMonth(getNow());
            startTime = DateUtils.getFirstDay(year, nowMonth);
            endTime = DateUtils.getLastDay(year, nowMonth);
            YwUserRoomDayData ywUserRoomDayData = ywUserRoomDayDataService.getYwUserRoomDataSum(entity, startTime, endTime);
            if (ywUserRoomDayData != null) {
                totalSalary = ywUserRoomDayData.getSalary() + "元";
                totalBonus = ywUserRoomDayData.getBonus() + "元";
                totalForfeit = ywUserRoomDayData.getForfeit() + "元";
                lastSalary = ywUserRoomDayData.getLastSalary() + "元";

            }
            if (dayData != null) {
                datamap.put("yesterday", dayData.getRanking() + "");
                float money = dayData.getSalary() + dayData.getBonus() - dayData.getForfeit();
                datamap.put("money", money + "元");
            } else {
                datamap.put("yesterday", "0");
                datamap.put("money", "0元");
            }
        } else if (room.getSign().equals("1")) { //签约主播
            YwUserRoomContract ywUserRoomContract = ywUserRoomContractService.getYwUserRoomContractById(room.getUid());

            datamap.put("yesterday", "0");
            if (ywUserRoomContract != null) {
                datamap.put("money", ywUserRoomContract.getSalary() + "元");
            } else {
                datamap.put("money", "0元");
            }

        }

        datamap.put("totalSalary", totalSalary);
        datamap.put("totalBonus", totalBonus);
        datamap.put("totalForfeit", totalForfeit);
        datamap.put("lastSalary", lastSalary);
        writeSuccessWithData(datamap);

    }

    private Integer getByUserId(String userId) throws ParseException {


        Date yestday = DateUtils.getDate(getNow());
        List<LanshaRoomRanking> list = lanshaRoomRankingService.getLanshaRoomRankingListSort(DateUtils.getStartDate(yestday));
        int size = list.size();
        int sort = 0;
        for (int i = 0; i < size; i++) {
            LanshaRoomRanking lanshaRoomRanking = list.get(i);
            if (lanshaRoomRanking.getUserId().equals(userId)) {
                sort = i + 1;
            }
        }

        return sort;
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
        Integer timeLength = 0;
        Integer totalTimeLength = 0;
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
     * 计算有效时长
     *
     * @param startTime
     * @param endTime
     * @param startHour
     * @param endHour
     * @return
     * @throws ParseException
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
