package com.yaowang.lansha.action.mobile;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwBanner;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwBannerService;
import com.yaowang.lansha.service.YwGameHotService;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-14
 * Time: 下午8:59
 * To change this template use File | Settings | File Templates.
 */
public class RoomAction extends LanshaMobileAction {
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private YwGameService ywGameService;
    @Resource
    private YwBannerService ywBannerService;

    private String gameId;

    private Integer banner = 0;
    @Resource
    private YwGameHotService ywGameHotService;

    private String type;

    public List<Map<String, Object>> hotGame(List<String> gameIds) {

        List<Map<String, Object>> games = new ArrayList<Map<String, Object>>();//游戏列表
        Map<String, Object> game = new HashMap<String, Object>();
        game.put("id", "");
        game.put("name", "推荐");
        games.add(game);
        game = new HashMap<String, Object>();
        game.put("id", "");
        game.put("name", "全部直播");
        games.add(game);

        Map<String, YwGame> map = ywGameService.getYwGameMapByIds(gameIds.toArray(new String[]{}));
        for (Map.Entry<String, YwGame> entry : map.entrySet()) {
            YwGame ywGame = entry.getValue();
            game = new HashMap<String, Object>();
            game.put("id", ywGame.getId());
            game.put("name", ywGame.getName());
            games.add(game);
        }


        return games;
    }

    /**
     * 首页数据v2
     *
     * @throws IOException
     */
    public void indexV2() throws IOException {

        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> listMapBanner = listMapBanner(); //banner
        List<Map<String, Object>> rooms1 = recommend();//大神女神
        List<Map<String, Object>> hotGames = ywGameHotService.getHotGame(6);

        YwGame entity = new YwGame();
        entity.setStatus(LanshaConstant.STATUS_ONLINE);
        Set<String> gameIds = new HashSet<String>();
        List<Map<String, Object>> games = new ArrayList<Map<String, Object>>();//游戏列表
        Map<String, Object> game = new HashMap<String, Object>();
        game.put("id", "");
        game.put("name", "推荐");
        games.add(game);
        game = new HashMap<String, Object>();
        game.put("id", "");
        game.put("name", "全部直播");
        games.add(game);
        for (Map<String, Object> map : hotGames) {
            game = new HashMap<String, Object>();
            game.put("id", map.get("gameId"));
            game.put("name", map.get("name"));
            games.add(game);
            gameIds.add(map.get("gameId").toString());
        }
        List<Map<String, Object>> rooms2 = new ArrayList<Map<String, Object>>();
        //热门推荐 取四个
        PageDto page = getPageDto();
        page.setRowNum(4);
        YwUserRoom ywUserRoom = new YwUserRoom();
        ywUserRoom.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
        ywUserRoom.setRoomHotType("1");
        List<Map<String, Object>> hotrooms = hot(ywUserRoom, page);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", "");
        map.put("name", "热门推荐");
        map.put("rooms", hotrooms);
        rooms2.add(map);
        list(rooms2, gameIds, games);

        data.put("games", games);
        data.put("rooms1", rooms1);
        data.put("banner", listMapBanner);
        data.put("rooms2", rooms2);
        writeSuccessWithData(data);
    }

    /**
     * @throws IOException
     * @Description:
     */
    public List<Map<String, Object>> listMapBanner() throws IOException {
        List<Map<String, Object>> listMapBanner = new ArrayList<Map<String, Object>>();
        //获取banner,由原来的只给房间banner转换成给所有移动端类型(clientType=1)的banner
        YwBanner ywBanner = new YwBanner();
        ywBanner.setClientType("1");
        List<YwBanner> listBanner = ywBannerService.getYwBannerPage(ywBanner, null);
        ywBannerService.setRoom(listBanner, true);

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listBanner)) {
            // 房间ids
            Set<String> idInts = new HashSet<String>();
            for (YwBanner banner : listBanner) {
                idInts.add(banner.getRoomId());
            }
            // 根据房间id获取房间信息
            Map<String, YwUserRoom> mapUserRoom = ywUserRoomService.getYwUserRoomMapByIdInts(idInts.toArray(new String[]{}), LanshaConstant.ROOM_STATUS_ONLINE);
            int i = 0;
            for (YwBanner banner : listBanner) {
                //取6个广告并且房间在线
                if (i < 6) {
                    Map<String, Object> mapBanner = new HashMap<String, Object>();
                    String roomId = banner.getRoomId();
                    // 广告图片
                    String img = "";
                    if ("0".equals(banner.getType())) {
                        if (!mapUserRoom.containsKey(roomId)) {
                            // 房间信息,过滤掉被删除的房间
                            continue;
                        } else {
                            YwUserRoom userRoom = mapUserRoom.get(roomId);
                            mapBanner.put("roomId", userRoom.getId());//房间guid
                            img = userRoom.getLiveImg();
                        }
                    }
                    if (StringUtils.isNotBlank(banner.getImg())) {
                        img = banner.getImg();
                    }
                    mapBanner.put("img", getUploadFilePath(img));// 图片
                    mapBanner.put("type", banner.getType());// (0：房间，1：广告)
                    mapBanner.put("name", banner.getName());// 名称
                    mapBanner.put("link", banner.getLinkUrl());// 链接地址
                    listMapBanner.add(mapBanner);
                    i++;
                }
            }
        }


        return listMapBanner;


    }

    /**
     * 游戏对应房间
     *
     * @param data
     * @param gameIds
     * @param listMapGames
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void list(List<Map<String, Object>> data, Set<String> gameIds, List<Map<String, Object>> listMapGames) throws IOException {

        //List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        // 房间信息查询
        YwUserRoom room = new YwUserRoom();
        room.setGameIds(gameIds);
        room.setOnline(LanshaConstant.STATUS_ONLINE);
        List<YwUserRoom> listUserRoom = ywUserRoomService.getYwUserRoomList(room, null, null, null, null);

        // 构建房间信息
        Map<String, Object> mapRoom = new HashMap<String, Object>();
        List<Map<String, Object>> listMapRoom = new ArrayList<Map<String, Object>>();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listUserRoom)) {
            // 设置名称信息
            ywUserRoomService.setData(listUserRoom, false);
            for (YwUserRoom ywUserRoom : listUserRoom) {
                Map<String, Object> mapRooms = new HashMap<String, Object>();
                mapRooms.put("id", ywUserRoom.getId());
                mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
                mapRooms.put("name", ywUserRoom.getName());
                mapRooms.put("nickName", ywUserRoom.getNickname());
                mapRooms.put("gameName", ywUserRoom.getGameName());
                mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0"
                        : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(), 1));
                mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
                mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
                String gameid = ywUserRoom.getGameId();
                if (mapRoom.containsKey(gameid)) {
                    listMapRoom = (List<Map<String, Object>>) mapRoom.get(gameid);
                    listMapRoom.add(mapRooms);
                } else {
                    listMapRoom = new ArrayList<Map<String, Object>>();
                    listMapRoom.add(mapRooms);
                }
                mapRoom.put(gameid, listMapRoom);
            }
        }

        // 返回房间信息
        if (MapUtils.isNotEmpty(mapRoom)) {
            for (Map<String, Object> mapGame : listMapGames) {
                Map<String, Object> map = new HashMap<String, Object>();
                String gameId = mapGame.get("id").toString();
                if (mapRoom.containsKey(gameId)) {
                    List<Map<String, Object>> rooms = (List<Map<String, Object>>) mapRoom.get(gameId);
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(rooms)) {
                        // 按观众数倒序排序
                        Collections.sort(rooms, new Comparator<Map<String, Object>>() {
                            @Override
                            public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                                return Integer.valueOf(m2.get("number").toString())
                                        .compareTo(Integer.valueOf(m1.get("number").toString()));
                            }
                        });
                        int size = rooms.size();
                        if (size > 4) {
                            map.put("id", gameId);
                            map.put("name", mapGame.get("name"));
                            map.put("rooms", rooms.subList(0, 4));
                            data.add(map);
                        } else if (size >= 2 && size <= 4) {
                            map.put("id", gameId);
                            map.put("name", mapGame.get("name"));
                            map.put("rooms", rooms);
                            data.add(map);
                        }


                    }
                }
            }
        }
        //return data;
    }


    /**
     * 所有游戏
     *
     * @return
     */
    private List<Map<String, Object>> getGames() {
        List<Map<String, Object>> games = new ArrayList<Map<String, Object>>();
        YwGame entity = new YwGame();
        entity.setStatus(LanshaConstant.STATUS_ONLINE);
        List<YwGame> ywGames = ywGameService.getYwGameList(entity, 0);
        for (YwGame ywGame : ywGames) {
            Map<String, Object> game = new HashMap<String, Object>();
            game.put("id", ywGame.getId());
            game.put("name", ywGame.getName());
            games.add(game);
        }
        return games;
    }

    /**
     * 大神 女神
     *
     * @throws IOException
     */
    public List<Map<String, Object>> recommend() throws IOException {
        int num = 10;
        // 取个数
        String value = SysOptionServiceImpl.getValue("LANSHA.INDEX.SHOWNUM.APP.RECOMMEND");
        if (StringUtils.isNotEmpty(value)) {
            num = Integer.valueOf(value);
        }
        PageDto page = getPageDto();
        page.setCount(false);
        page.setRowNum(num);
        //大神
        List<YwUserRoom> listD = ywUserRoomService.geUserRoomsByEntity(LanshaConstant.LANSHA_BEST_ANCHOR, page);

        //女神
        page.setRowNum(num);
        List<YwUserRoom> listN = ywUserRoomService.geUserRoomsByEntity(LanshaConstant.LANSHA_GIRL_ANCHOR, page);

        listD.removeAll(listN);
        listD.addAll(listN);
        if (!CollectionUtils.isEmpty(listD)) {
            Collections.sort(listD, new Comparator<YwUserRoom>() {
                @Override
                public int compare(YwUserRoom o1, YwUserRoom o2) {
                    return o1.getOrderId().compareTo(o2.getOrderId());
                }

            });
        }

        if (!CollectionUtils.isEmpty(listD)) {
            Collections.sort(listD, new Comparator<YwUserRoom>() {
                @Override
                public int compare(YwUserRoom o1, YwUserRoom o2) {
                    return o2.getOnline().compareTo(o1.getOnline());
                }

            });
        }

        ywUserRoomService.setData((List<YwUserRoom>) listD, false);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (YwUserRoom YwUserRoom : listD) {
            Map<String, Object> mapRooms = new HashMap<String, Object>();
            mapRooms.put("id", YwUserRoom.getId());
            mapRooms.put("img", getUploadFilePath(YwUserRoom.getUserIcon()));
            mapRooms.put("name", YwUserRoom.getNickname());
            result.add(mapRooms);
        }
//        List<YwUserRoomHot> listD = ywUserRoomHotService.getByType(1, 2);
//        if (!CollectionUtils.isEmpty(listD)) {
//            Collections.sort(listD, new Comparator<YwUserRoomHot>() {
//                @Override
//                public int compare(YwUserRoomHot o1, YwUserRoomHot o2) {
//                    return o1.getOrderId().compareTo(o2.getOrderId());
//                }
//
//            });
//        }
//        if (listD.size() > 3) {
//            listD = listD.subList(0, 3);
//        }
//        List<YwUserRoomHot> listN = ywUserRoomHotService.getByType(1, 3);
//        if (!CollectionUtils.isEmpty(listN)) {
//            Collections.sort(listN, new Comparator<YwUserRoomHot>() {
//                @Override
//                public int compare(YwUserRoomHot o1, YwUserRoomHot o2) {
//                    return o1.getOrderId().compareTo(o2.getOrderId());
//                }
//
//            });
//        }
//
//        if (listN.size() > 3) {
//            listN = listN.subList(0, 3);
//        }
//    }
        return result;
    }

    /**
     * 热门推荐房间
     *
     * @return
     */
    public List<Map<String, Object>> hot(YwUserRoom entity, PageDto page) {

        List<Map<String, Object>> listMapRooms = new ArrayList<Map<String, Object>>();
        // 热门推荐
//      List<YwUserRoom> listUserRoom = ywUserRoomService.getYwUserRoomIsHot(entity, page);
        List<YwUserRoom> listUserRoom = ywUserRoomService.getBestOnlineUserRooms(LanshaConstant.LANSHA_HOT_ANCHOR, page);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listUserRoom)) {
            ywUserRoomService.setData(listUserRoom, false);
            // 根据返回数据结构
            for (YwUserRoom ywUserRoom : listUserRoom) {
                Map<String, Object> mapRooms = new HashMap<String, Object>();
                mapRooms.put("id", ywUserRoom.getId());
                mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
                mapRooms.put("name", ywUserRoom.getName());
                mapRooms.put("nickName", ywUserRoom.getNickname());
                mapRooms.put("gameName", ywUserRoom.getGameName());
                mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(), 1));
                mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
                mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
                listMapRooms.add(mapRooms);
            }
        }
        return listMapRooms;
    }

    /**
     * 游戏数据
     *
     * @throws IOException
     */
    public void data() throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        int num = 6;
        // 取个数
        String value = SysOptionServiceImpl.getValue("LANSHA.INDEX.SHOWNUM.APP.GAME");
        if (StringUtils.isNotEmpty(value)) {
            num = Integer.valueOf(value);
        }
        PageDto page = getPageDto();
        page.setRowNum(num);
        YwUserRoom entity = new YwUserRoom();
        entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);
        entity.setOrderSql(" number + base_number desc");
        List<Map<String, Object>> rooms = null;
        List<Map<String, Object>> banners = new ArrayList<Map<String, Object>>();

        if (banner == 1) {
            if (StringUtils.isNotEmpty(gameId)) {
                YwGame ywGame = ywGameService.getYwGameById(gameId);
                if (ywGame != null) {
                    Map<String, Object> banner = new HashMap<String, Object>();
                    if (StringUtils.isNotEmpty(ywGame.getMobileBanner())) {
                        banner.put("img", getUploadFilePath(ywGame.getMobileBanner()));
                        banners.add(banner);
                    }
                    data.put("banners", banners);
                }
            }
        }
        entity.setGameId(gameId);
        rooms = getRoomByGameId(entity, page);
        data.put("rooms", rooms);
        writeSuccessWithData(data);

    }

    /**
     * 根据游戏id 获取房间
     *
     * @param entity
     * @param page
     * @return
     */
    public List<Map<String, Object>> getRoomByGameId(YwUserRoom entity, PageDto page) {
        List<Map<String, Object>> listMapRooms = new ArrayList<Map<String, Object>>();
        List<YwUserRoom> listUserRoom = ywUserRoomService.getYwUserRoomList(entity, page);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listUserRoom)) {
            ywUserRoomService.setData(listUserRoom, false);
//            Collections.sort(listUserRoom, new Comparator<YwUserRoom>() {
//                @Override
//                public int compare(YwUserRoom m1, YwUserRoom m2) {
//                    return Integer.valueOf(m2.getOnLineNumber()).compareTo(m1.getOnLineNumber());
//                }
//            });

            // 根据返回数据结构
            for (YwUserRoom ywUserRoom : listUserRoom) {
                Map<String, Object> mapRooms = new HashMap<String, Object>();
                mapRooms.put("id", ywUserRoom.getId());
                mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
                mapRooms.put("name", ywUserRoom.getName());
                mapRooms.put("nickName", ywUserRoom.getNickname());
                mapRooms.put("gameName", ywUserRoom.getGameName());
                mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(), 1));
                mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
                mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
                listMapRooms.add(mapRooms);
            }
        }
        return listMapRooms;
    }

    /**
     * 直播列表v2
     *
     * @throws IOException
     */
    public void listv2() throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();

        List<Map<String, Object>> types = new ArrayList<Map<String, Object>>();

        Map<String, Object> mapRooms = new HashMap<String, Object>();
        mapRooms.put("id", "");
        mapRooms.put("name", "推荐");
        types.add(mapRooms);
        mapRooms = new HashMap<String, Object>();
        mapRooms.put("id", "hot");
        mapRooms.put("name", "最热");
        types.add(mapRooms);
        mapRooms = new HashMap<String, Object>();
        mapRooms.put("id", "new");
        mapRooms.put("name", "最新");
        types.add(mapRooms);
        data.put("types", types);
        List<Map<String, Object>> listMapRooms = null;

        int num = 10;
        String value = SysOptionServiceImpl.getValue("LANSHA.INDEX.SHOWNUM.APP.LIST.NUM");
        if (StringUtils.isNotEmpty(value)) {
            num = Integer.valueOf(value);
        }
        PageDto page = getPageDto();
        page.setRowNum(num);

        if (StringUtils.isEmpty(type)) {
            listMapRooms = getPushRooms(page);
        } else if (type.equals("hot")) {
            listMapRooms = getBestHotRooms(page);
        } else if (type.equals("new")) {
            listMapRooms = getNewLiveRooms(page);
        }
        data.put("rooms", listMapRooms);
        writeSuccessWithData(data);
    }

    /**
     * 获取推荐房间
     *
     * @throws IOException
     */
    public List<Map<String, Object>> getPushRooms(PageDto page) throws IOException {
        List<Map<String, Object>> listMapRooms = new ArrayList<Map<String, Object>>();
        List<YwUserRoom> rooms = ywUserRoomService.getBestOnlineUserRooms(LanshaConstant.LANSHA_HOT_ANCHOR, page);
        ywUserRoomService.setData((List<YwUserRoom>) rooms, false);
        // 根据返回数据结构
        for (YwUserRoom ywUserRoom : rooms) {
            Map<String, Object> mapRooms = new HashMap<String, Object>();
            mapRooms.put("id", ywUserRoom.getId());
            mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
            mapRooms.put("name", ywUserRoom.getName());
            mapRooms.put("nickName", ywUserRoom.getNickname());
            mapRooms.put("gameName", ywUserRoom.getGameName());
            mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(), 1));
            mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
            mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
            listMapRooms.add(mapRooms);
        }


        return listMapRooms;
    }

    /**
     * 最新房间
     *
     * @throws IOException
     */
    public List<Map<String, Object>> getNewLiveRooms(PageDto page) throws IOException {
        List<Map<String, Object>> listMapRooms = new ArrayList<Map<String, Object>>();
        YwUserRoom entity = new YwUserRoom();
        entity.setOrderSql("create_time desc");
        entity.setOnline(LanshaConstant.STATUS_ONLINE);
        List<YwUserRoom> newRooms = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
        ywUserRoomService.setData((List<YwUserRoom>) newRooms, false);
        // 根据返回数据结构
        for (YwUserRoom ywUserRoom : newRooms) {
            Map<String, Object> mapRooms = new HashMap<String, Object>();
            mapRooms.put("id", ywUserRoom.getId());
            mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
            mapRooms.put("name", ywUserRoom.getName());
            mapRooms.put("nickName", ywUserRoom.getNickname());
            mapRooms.put("gameName", ywUserRoom.getGameName());
            mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(), 1));
            mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
            mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
            listMapRooms.add(mapRooms);
        }
        return listMapRooms;
    }

    /**
     * 最热房间
     *
     * @throws IOException
     */
    public List<Map<String, Object>> getBestHotRooms(PageDto page) throws IOException {
        List<Map<String, Object>> listMapRooms = new ArrayList<Map<String, Object>>();
        YwUserRoom entity = new YwUserRoom();
        entity.setOrderSql(" number*(CASE WHEN multiple_number = '0' THEN 1 ELSE multiple_number END)+base_number desc");
        entity.setOnline(LanshaConstant.STATUS_ONLINE);
        List<YwUserRoom> hots = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
        ywUserRoomService.setData((List<YwUserRoom>) hots, false);
        // 根据返回数据结构
        for (YwUserRoom ywUserRoom : hots) {
            Map<String, Object> mapRooms = new HashMap<String, Object>();
            mapRooms.put("id", ywUserRoom.getId());
            mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
            mapRooms.put("name", ywUserRoom.getName());
            mapRooms.put("nickName", ywUserRoom.getNickname());
            mapRooms.put("gameName", ywUserRoom.getGameName());
            mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0" : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(), 1));
            mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
            mapRooms.put("headImg", getUploadFilePath(ywUserRoom.getUserIcon()));
            listMapRooms.add(mapRooms);
        }
        return listMapRooms;


    }

    /**
     * 最热房间
     *
     * @throws IOException
     */
    public List<YwUserRoom> getAllRooms() throws IOException {
        PageDto page = new PageDto();
        page.setRowNum(8);
        YwUserRoom entity = new YwUserRoom();
        entity.setOrderSql("(CASE WHEN online = '1' THEN -1 ELSE online END)");
        List<YwUserRoom> hots = ywUserRoomService.getAllLiveListByRoome(entity, page, null);
        return hots;

    }


    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getBanner() {
        return banner;
    }

    public void setBanner(Integer banner) {
        this.banner = banner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}


