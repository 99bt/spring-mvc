package com.yaowang.lansha.action.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.yaowang.lansha.entity.YwGameType;
import com.yaowang.lansha.entity.YwUserRoomHot;
import com.yaowang.lansha.service.YwGameHotService;
import com.yaowang.lansha.service.YwGameTypeService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.service.SysMcodeDetailService;
import com.yaowang.util.NumberUtil;

/**
 * @author tandingbo
 * @ClassName: GameAction
 * @Description: TODO(游戏)
 * @date 2015年12月21日 下午2:48:50 update: ---- 201602170001 zhanghongqing 修改内容:
 * 蓝鲨移动端1.07版需求（02-16）.doc -- 4.搜索功能优化
 */
public class GameAction extends LanshaMobileAction {
    private static final long serialVersionUID = -391154051790851242L;

    @Resource
    private YwGameService ywGameService;
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private SysMcodeDetailService sysMcodeDetailService;

    @Resource
    private YwGameTypeService ywGameTypeService;
    @Resource
    private YwGameHotService ywGameHotService;


    private Integer show = 0;

    private String type;

    /**
     * @throws IOException
     * @Description: 游戏列表
     */
    @SuppressWarnings("unchecked")
    public void list() throws IOException {
        // 返回数据
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        // 查询条件
        PageDto page = getPageDto();
        page.setRowNum(4);
        YwGame game = new YwGame();
        game.setStatus(LanshaConstant.STATUS_ONLINE);
        List<YwGame> listGame = ywGameService.getYwGamePages(game, null, page, null, null, null);
        if (CollectionUtils.isNotEmpty(listGame)) {
            Set<String> gameIds = new HashSet<String>();
            List<Map<String, Object>> listMapGames = new ArrayList<Map<String, Object>>();

            for (YwGame ywGame : listGame) {
                String gameId = ywGame.getId();
                gameIds.add(gameId);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", gameId);
                map.put("name", ywGame.getName());
                listMapGames.add(map);
            }

            // 房间信息查询
            YwUserRoom room = new YwUserRoom();
            room.setGameIds(gameIds);
            room.setOnline(LanshaConstant.STATUS_ONLINE);
            List<YwUserRoom> listUserRoom = ywUserRoomService.getYwUserRoomList(room, null, null, null, null);

            // 构建房间信息
            Map<String, Object> mapRoom = new HashMap<String, Object>();
            List<Map<String, Object>> listMapRoom = new ArrayList<Map<String, Object>>();
            if (CollectionUtils.isNotEmpty(listUserRoom)) {
                // 设置名称信息
                ywUserRoomService.setUserName(listUserRoom);
                ywUserRoomService.setGameName(listUserRoom);

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
                    String gameId = mapGame.get("id").toString();
                    if (mapRoom.containsKey(gameId)) {
                        List<Map<String, Object>> rooms = (List<Map<String, Object>>) mapRoom.get(gameId);
                        if (CollectionUtils.isNotEmpty(rooms)) {
                            // 按观众数倒序排序
                            Collections.sort(rooms, new Comparator<Map<String, Object>>() {
                                @Override
                                public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                                    return Integer.valueOf(m2.get("number").toString())
                                            .compareTo(Integer.valueOf(m1.get("number").toString()));
                                }
                            });
                            mapGame.put("rooms", rooms);
                            data.add(mapGame);
                        }
                    }
                }
            }
        }

        writeSuccessWithData(data);
    }

    /**
     * @throws IOException
     * @Description: 全文搜索
     */
    public void search() throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> listMapHot = new ArrayList<Map<String, Object>>();
        // 热门搜索词
        Map<String, String> mapMcodeDetail = sysMcodeDetailService.getMcodeContentToThisId("APP_SEARCH");
        if (MapUtils.isNotEmpty(mapMcodeDetail)) {
            for (String content : mapMcodeDetail.keySet()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", content);
                listMapHot.add(map);
            }
        }
        result.put("hot", listMapHot);

        writeSuccessWithData(result);
    }

    /**
     * @throws IOException
     * @Description: 全文搜索结果
     */
    public void dosearch() throws IOException {
        if (StringUtils.isBlank(name)) {
            write(EMPTY_ENTITY);
            return;
        }

        Set<YwUserRoom> setUserRoom = new HashSet<YwUserRoom>();
        Set<String> roomIds = new HashSet<String>();
        // ------- 201602170001 ---- 如果搜索词为数字且不为空，加入对房间ID的搜索
        if (StringUtils.isNumeric(name) && StringUtils.isNotBlank(name)) {
            int idInt = Integer.parseInt(name);
            YwUserRoom oneByRoomId = ywUserRoomService.getYwUserRoomById(idInt);
            if (null != oneByRoomId) {
                setUserRoom.add(oneByRoomId);
                roomIds.add(oneByRoomId.getId());
            }
        }
        // ------- 201602170001 end ----

        // 主播名称相关的房间
        // ------- 201602170001---- 查找主播相关
        List<YwUserRoom> listByNickName = ywUserRoomService.getYwUserRoomByNickName(name);
        if (CollectionUtils.isNotEmpty(listByNickName)) {
            for (YwUserRoom ywUserRoom : listByNickName) {
                if (!roomIds.contains(ywUserRoom.getId())) {
                    // 去重
                    setUserRoom.add(ywUserRoom);
                    roomIds.add(ywUserRoom.getId());
                }
            }
        }

        // 房间名称相关的房间
        YwUserRoom room = new YwUserRoom();
        room.setName(name);
        // ------- 201602170001 ---- 搜索结果包含不在线的
        room.setOnline(LanshaConstant.STATUS_ONLINE);
        // ------- 201602170001 end----
        List<YwUserRoom> listByName = ywUserRoomService.getYwUserRoomList(room);
        if (CollectionUtils.isNotEmpty(listByName)) {
            for (YwUserRoom ywUserRoom : listByName) {
                if (!roomIds.contains(ywUserRoom.getId())) {
                    setUserRoom.add(ywUserRoom);
                    roomIds.add(ywUserRoom.getId());
                }
            }
        }

        // 游戏名称相关的房间
        room = new YwUserRoom();
        room.setGameName(name);
        room.setOnline(LanshaConstant.STATUS_ONLINE);
        List<YwUserRoom> listByGameName = ywUserRoomService.getYwUserRoomList(room, null, null, null, null);
        if (CollectionUtils.isNotEmpty(listByGameName)) {
            for (YwUserRoom ywUserRoom : listByGameName) {
                if (!roomIds.contains(ywUserRoom.getId())) {
                    // 去重
                    setUserRoom.add(ywUserRoom);
                    roomIds.add(ywUserRoom.getId());
                }
            }
        }

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (setUserRoom.size() > 0) {
            List<YwUserRoom> listUserRoom = new ArrayList<YwUserRoom>(setUserRoom);

            // 按权重排序，同权重 按观众数倒序排序
            // ------- 201602170001 ----
            List<YwUserRoom> roomTemp = new ArrayList<YwUserRoom>();
            //精确匹配
            for (YwUserRoom ywUserRoom : listUserRoom) {
                if (StringUtils.isNumeric(name) && StringUtils.isNotBlank(name)) {
                    int pIdInt = Integer.parseInt(name);
                    if (pIdInt == ywUserRoom.getIdInt()) {
                        //通过房间id精确查找到的
                        roomTemp.add(ywUserRoom);
                    } else if (name.equals(ywUserRoom.getNickname())) {
                        //主播名精确匹配
                        roomTemp.add(ywUserRoom);
                    } else if (name.equals(ywUserRoom.getName())) {
                        //主播房间名精确匹配
                        roomTemp.add(ywUserRoom);
                    }
                }
            }
            //按人气排序
            YwUserRoom.sort(roomTemp);

            //在线房间
            LinkedList<YwUserRoom> roomTemp2 = new LinkedList<YwUserRoom>();
            for (YwUserRoom ywUserRoom : listUserRoom) {
                if (roomTemp.indexOf(ywUserRoom) > -1) {
                    continue;
                }

                if (ywUserRoom.getOnline() == 1) {
                    roomTemp2.add(ywUserRoom);
                }
            }
            //按人气排序
            YwUserRoom.sort(roomTemp2);
            roomTemp.addAll(roomTemp2);

            //离线房间
            for (YwUserRoom ywUserRoom : listUserRoom) {
                if (roomTemp.indexOf(ywUserRoom) > -1) {
                    continue;
                }

                roomTemp.add(ywUserRoom);
            }
            listUserRoom = roomTemp;

            // ------- 201602170001 end ----
            if (listUserRoom.size() > 20) {
                listUserRoom = listUserRoom.subList(0, 19);
            }
            // 设置名称信息
            ywUserRoomService.setUserName(listUserRoom);
            ywUserRoomService.setGameName(listUserRoom);

            for (int i = 0; i < listUserRoom.size(); i++) {
                YwUserRoom ywUserRoom = listUserRoom.get(i);
                Map<String, Object> mapRooms = new HashMap<String, Object>();
                mapRooms.put("id", ywUserRoom.getId());
                mapRooms.put("img", getUploadFilePath(ywUserRoom.getLiveImg()));// (http地址)
                mapRooms.put("name", ywUserRoom.getName());
                mapRooms.put("nickName", ywUserRoom.getNickname());
                mapRooms.put("gameName", ywUserRoom.getGameName());
                mapRooms.put("number", ywUserRoom.getOnLineNumber() == null ? "0"
                        : NumberUtil.changeNumberToWan(ywUserRoom.getOnLineNumber(), 1));
                mapRooms.put("online", ywUserRoom.getOnline() == null ? "0" : ywUserRoom.getOnline().toString());// (0：离线，1：在线，2：停播)
                result.add(mapRooms);
            }
        }

        writeSuccessWithData(result);
    }

    /**
     * 游戏分类数据
     */
    public void type() throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        if (show == 1) {  //show=1 显示分类 0 不显示
            List<Map<String, Object>> types = new ArrayList<Map<String, Object>>();
            int num = 6;
            // 取个数
            String value = SysOptionServiceImpl.getValue("LANSHA.INDEX.SHOWNUM.APP.GAME.TYPE");
            if (StringUtils.isNotEmpty(value)) {
                num = Integer.valueOf(value);
            }
            PageDto page = getPageDto();
            page.setCount(false);
            page.setRowNum(num);
            Map<String, Object> mapRooms = new HashMap<String, Object>();
            mapRooms.put("id", "");
            mapRooms.put("name", "最火");
            types.add(mapRooms);
            YwGameType entity = new YwGameType();
            entity.setStatus("1");
            List<YwGameType> list = ywGameTypeService.getYwGameTypeList(entity, page);
            for (YwGameType ywGameType : list) {
                mapRooms = new HashMap<String, Object>();
                mapRooms.put("id", ywGameType.getId());
                mapRooms.put("name", ywGameType.getName());
                types.add(mapRooms);
            }
            data.put("types", types);
        }

        List<Map<String, Object>> games = new ArrayList<Map<String, Object>>();
        YwGame ywGame = new YwGame();
        //type 为空显示最火
        if (StringUtils.isBlank(type)) {
            games = hotGame();
        } else {

            ywGame.setTypeId(type);
            ywGame.setStatus(LanshaConstant.STATUS_ONLINE);
            List<YwGame> ywGames = ywGameService.getYwGameList(ywGame, 0);
            Map<String, Integer> map = ywUserRoomService.getRoomNumber();
            for (YwGame yw : ywGames) {
                Map<String, Object> game = new HashMap<String, Object>();
                if (map.containsKey(yw.getId())) {
                    game.put("id", yw.getId());
                    game.put("name", yw.getName());
                    game.put("img", getUploadFilePath(yw.getAdvertSmall()));
                    game.put("roomNumber", map.get(yw.getId()) + "");
                } else {
                    game.put("id", yw.getId());
                    game.put("name", yw.getName());
                    game.put("img", getUploadFilePath(yw.getAdvertSmall()));
                    game.put("roomNumber", "0");
                }
                games.add(game);
            }
        }
        if (!CollectionUtils.isEmpty(games)) {
            Collections.sort(games, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return Integer.valueOf(o2.get("roomNumber")+"").compareTo(Integer.valueOf(o1.get("roomNumber")+""));
                }

            });
        }
        data.put("games", games);

        writeSuccessWithData(data);
    }

    public List<Map<String, Object>> hotGame() {
        List<Map<String, Object>> hotGames = ywGameHotService.getHotGame(50);
        List<Map<String, Object>> games = new ArrayList<Map<String, Object>>();

        Map<String, Integer> map = ywUserRoomService.getRoomNumber();

        for (Map<String, Object> result : hotGames) {
            String gameId = result.get("gameId")+"";
            Map<String, Object> game = new HashMap<String, Object>();
            if (map.containsKey(gameId)) {
                game.put("id", gameId);
                game.put("name",result.get("name"));
                game.put("img", getUploadFilePath(result.get("advertSmall")+""));
                game.put("roomNumber", map.get(gameId)+"");
            } else {
                game.put("id", gameId);
                game.put("name",result.get("name"));
                game.put("img", getUploadFilePath(result.get("advertSmall")+""));
                game.put("roomNumber", "0");
            }
            games.add(game);
        }


        return games;
    }


    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
