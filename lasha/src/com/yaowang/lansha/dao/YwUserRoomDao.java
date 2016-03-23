package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwBanner;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUserRoom;

/**
 * yw_user_room 
 * @author 
 * 
 */
public interface YwUserRoomDao{
	/**
	 * 新增yw_user_room
	 * @param ywUserRoom
	 * @return
	 */
	public YwUserRoom save(YwUserRoom entity);
	
	/**
	 * 根据ids数组删除yw_user_room
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新房间状态
	 * @param room
	 * @return
	 */
	public int updateState(YwUserRoom room);
	/**
	 * 更新房间关注数
	 * @param id
	 * @return
	 */
	public Integer updateFans(String id, Integer numb);
	
	/**
	 * 根据id获取yw_user_room
	 * @param id
	 * @return
	 */
	public YwUserRoom getYwUserRoomById(String id);
	
	/**
	 * 根据idInt获取yw_user_room
	 * @param idInt
	 * @return
	 */
	public YwUserRoom getYwUserRoomById(int idInt);
	
	/**
	 * 根据用户id
	 * @param id
	 * @return
	 */
	public YwUserRoom getYwUserRoomByUid(String uid);
	/**
	 * 根据房间号获取房间信息
	 * @param id
	 * @return
	 */
	public YwUserRoom getYwUserRoomByRoomId(String roomId);
	
		
	/**
	 * 根据ids数组查询yw_user_roommap
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoom> getYwUserRoomMapByIds(String[] ids);
	
	/**
	 * 根据idInts数组查询yw_user_roommap
	 * @param idInts
	 * @param status TODO
	 * @return
	 */
	public Map<String, YwUserRoom> getYwUserRoomMapByIdInts(String[] idInts, Integer status);
	
	/**
	 * 获取yw_user_room列表
	 * @return
	 */
	public List<YwUserRoom> getYwUserRoomList(YwUserRoom room);
		
	/**
	 * 分页获取yw_user_room列表
	 * @param room 
	 * @param uids TODO
	 * @param page
	 * @param isBan TODO
	 * @param isShow TODO
	 * @return
	 */
	public List<YwUserRoom> getYwUserRoomList(YwUserRoom room, String[] uids, PageDto page, Boolean isBan, Boolean isShow);

	/**
	 * 修改直播状态
	 * @param ids
	 * @param status
	 * @param remarks
	 */
	public void updateBySql(String[] ids, Integer status,String remarks);
	
	/**
	 * 根据uids数组查询yw_user_roommap
	 * @param uids
	 * @return
	 */
	public Map<Integer, YwUserRoom> getYwUserRoomMapByUIds(String[] uids);

	/**
	 * 根据房间id更新
	 * @param room
	 * @return
	 */
	public Integer updateByRoomId(YwUserRoom room);
	/**
	 * 更新房间图片
	 * @param temp
	 * @return
	 */
	public Integer updateLiveImgByRoomId(YwUserRoom temp);
	/**
	 * 重置状态
	 * @return
	 */
	public Integer updateReset(Date date);

	/**
	 * @Description: 根据房间名称获取房间信息
	 * @param name
	 * @param listInStatus
	 * @return
	 */
	public List<Map<String, Object>> getListMapRoomInfo(String name, List<Integer> listInStatus);

	/**
	 * @Description: 获取房间信息
	 * @param entity
	 * @param ids
	 * @return
	 */
	public List<YwUserRoom> listYwUserRoomList(YwUserRoom entity, String[] ids);

	/**
	 * @Description: 修改
	 * @param entity
	 */
	public Integer update(YwUserRoom entity);
	/**
	 * 根据条件获取全部直播房间
	 * @param ids TODO
	 * @return
	 */
	public List<YwUserRoom> getAllLiveListByRoome(YwUserRoom entity, PageDto page, String[] ids);

	/**
	 * @Description: 
	 * @param ids
	 * @return
	 */
	public List<YwUserRoom> getYwUserRoomByIds(String[] ids, PageDto page);
	/**
	 * 获得房间总数
	 * @return
	 * @creationDate. 2015-12-14 下午12:24:56
	 */
	public Integer getRoomCount();
	
	/**
	 * 更新直播码
	 * @param room
	 * @return
	 */
	public int updateRoomId(String id,String roomId);

	/**
	 * @Description: 获取热门房间信息
	 * @param entity
	 * @param page
	 * @return
	 */
	public List<YwUserRoom> getYwUserRoomIsHot(YwUserRoom entity, PageDto page);

	/**
	 * @Description: app首页调用
	 * @param ywBanner
	 * @param online
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getBannerUserRoom(YwBanner ywBanner, Integer online, PageDto page);
	/**
	 * 根据ids数组查询yw_user_roommap
	 * @param userIds
	 * @return
	 */
	public Map<String, YwUserRoom> getYwUserRoomMapByUserIds(String[] userIds);
	/**
	 * 获取房间
	 * @param array
	 * @return
	 */
	public Map<Integer, YwUserRoom> getYwUserRoomMapByIds(Integer[] array);

	/**
	 * 通过主播昵称查找房间
	 * @param name
	 * @return
	 */
	public List<YwUserRoom> getYwUserRoomByNickName(String name,PageDto page);
    /**
     * 通过房间id_int 获取信息
     * @param id
     * @return
     */
    public List<Map<String, Object>> getByRoomIdint(Integer id );

    public Integer updateSignByIDInts(Integer status,String[] ids);

    /**
     * 更新房间观众数
     * @param type 1-倍数; 0-基数
     * @param ids
     * @param num
     * @return
     */
	public Integer updateRoomNumber(int type, String[] ids, Integer num);

    /**
     * 统计游戏房间数
     * @param ids
     * @return
     */
    public Map<String, Integer> getRoomNumber() ;
    

	/**
	 * 通过搜索关键词查找房间
	 * @param keywork  关键字
	 * @param listUser 主播列表
	 * @param listGame 游戏列表
	 * @param page 分页信息
	 * @return
	 */
	public List<YwUserRoom> findByKeyword(String keywork, List<YwGame> listGame, List<Map<String, Object>> listUser, PageDto page);

	/**
	 * 获取条件查询房间数
	 * @param name
	 * @param listGame
	 * @param listUser
	 * @return
	 */
	public Integer countFindByKeyword(String name, List<YwGame> listGame, List<Map<String, Object>> listUser);

	/**
	 * 按热门类型获取房间列表
	 * 在线的在前面，其次按热门order_id排序
	 * @param type
	 * @param page
	 * @return
	 */
	public List<YwUserRoom> getBestUserRooms(Integer type, PageDto page);

	/**
	 * 获得指定游戏的直播间数量
	 * @param gameId
	 * @return
	 */
	public Integer getRoomCount(String gameId);

	/**
	 * 获取某一游戏的某一类型的热门推荐
	 * @param type
	 * @param page
	 * @param gameId
	 * @return
	 */
	public List<YwUserRoom> getBestUserRooms(Integer type, PageDto page, String gameId);

	/**
	 * 获取
	 * @param limit
	 * @param ids
	 * @return
	 */
	public List<YwUserRoom> getRoomOnlineFHot(int limit, String[] ids);

	/**
	 * 
	 * @param type
	 * @param page
	 * @return
	 */
	public List<YwUserRoom> getBestUserRoomsOnline(Integer type, PageDto page);
	
	/**
	 * @Description: 获取大神女神房间
	 * @param entity
	 * @param page
	 * @return
	 */
	public List<YwUserRoom> getYwUserRoomIsDN(YwUserRoom entity, PageDto page);

	/**
	 * 随机获取limit个推荐在线直播
	 * @param limit
	 * @param ids 排除列表
	 * @return
	 */
	public List<String> getRandLiveFHot(int limit, String[] ids);



    public List<YwUserRoom> geUserRoomsByEntity(Integer type, PageDto page);


	/**
	 * 获取所有在线直播间，并按推荐排序
	 * @param lanshaHotAnchor
	 * @param page
	 * @return
	 */
	public List<YwUserRoom> getAllOnlineLiveSortByHot(Integer lanshaHotAnchor, PageDto page);
}
