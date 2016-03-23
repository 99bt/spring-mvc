package com.yaowang.lansha.dao;

import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUserRoomHot;

/**
 * 热门推荐房间 
 * @author 
 * 
 */
public interface YwUserRoomHotDao{
	/**
	 * 新增热门推荐房间
	 * @param ywUserRoomHot
	 * @return
	 */
	public YwUserRoomHot save(YwUserRoomHot entity);
	
	/**
	 * 根据ids数组删除热门推荐房间
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新热门推荐房间
	 * @param ywUserRoomHot
	 * @return
	 */
	public Integer update(YwUserRoomHot entity);
	
	/**
	 * 根据id获取热门推荐房间
	 * @param id
	 * @return
	 */
	public YwUserRoomHot getYwUserRoomHotById(String id);
		
	/**
	 * 根据ids数组查询热门推荐房间map
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoomHot> getYwUserRoomHotMapByIds(String[] ids);
	
	/**
	 * 获取热门推荐房间列表
	 * @return
	 */
	public List<YwUserRoomHot> getYwUserRoomHotList(YwUserRoomHot entity);
		
	/**
	 * 分页获取热门推荐房间列表
	 * @param page
	 * @return
	 */
	public List<YwUserRoomHot> getYwUserRoomHotPage(YwUserRoomHot entity, PageDto page);

	/**
	 * @Description: 根据房间ids删除信息
	 * @param ids
	 */
	public void deleteByRoomId(String[] ids);

	/**
	 * @Description: 根据房间id获取热门房间信息
	 * @param roomId
	 * @return
	 */
	public YwUserRoomHot getYwUserRoomHotByRoomId(String roomId,Integer type);

	/**
	 * @Description: 获取热门房间信息
	 * @param page
	 * @return
	 */
    public List<Map<String, Object>> listMapRootHot(Integer type,PageDto page);

	/**
	 * @Description: 
	 * @param ids
	 * @return
	 */
	public Map<String, YwUserRoomHot> getMapUserRoomHotByRoomIds(String[] ids);

    /**
     * @Description:
     * @param ids
     * @return
     */
    public Map<String, YwUserRoomHot> getMapUserRoomDaShenByRoomIds(String[] ids);


    /**
     * @Description:
     * @param ids
     * @return
     */
    public Map<String, YwUserRoomHot> getMapUserRoomNvShenByRoomIds(String[] ids);

    public List<YwUserRoomHot> listRoomByType(Integer online,Integer type);

    /**
     * 随机一个在线的热门房间号
     * @return
     */
	public Integer randRoomInHotWithOnline();

	
}
