package com.yaowang.lansha.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.entity.YwUser;

/**
 * 用户基本信息表 
 * @author 
 * 
 */
public interface YwUserDao{
	/**
	 * 新增用户基本信息表
	 * @param ywUsers
	 * @return
	 */
	public YwUser save(YwUser entity);
	
	/**
	 * 根据ids数组删除用户基本信息表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据id获取用户基本信息表
	 * @param id
	 * @return
	 */
	public YwUser getYwUserById(String id);
	
	/**
	 * 根据令牌（token）查询用户
	 * @param token
	 * @return
	 */
	public YwUser getYwusersByToken(String token);
		
	/**
	 * 根据ids数组查询用户基本信息表map
	 * @param ids
	 * @return
	 */
	public Map<String, YwUser> getYwUserMapByIds(String[] ids);
	
	/**
	 * 获取用户基本信息表列表
	 * @param searchNote TODO
	 * @param status TODO
	 * @param page TODO
	 * @return
	 */
	public List<YwUser> getYwUserList(String searchNote, Integer status, PageDto page);
	
	/**
	 * 获取推荐的用户数
	 * @param ywUsers
	 * @return
	 */
	public Integer getRecommendUserNumber(YwUser entity);
		
	/**
	 * 分页获取用户基本信息表列表
	 * @param users TODO
	 * @param ids TODO
	 * @param status TODO
	 * @param page
	 * @param startTime TODO
	 * @param endTime TODO
	 * @return
	 */
	public List<YwUser> getYwUserList(YwUser users, String[] ids, Integer[] status, PageDto page, Date startTime, Date endTime);
	
	/**
	 * 修改密码
	 * @param ywUsers
	 * @return
	 */
	public Integer updatePassword(YwUser entity);

	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @param containDel 是否包含删除的用户
	 * @return
	 */
	public YwUser getYwusersByUsername(String username, boolean containDel);

	/**
	 * 根据手机号码获取用户
	 * @param telphone
	 * @return
	 */
	public YwUser getYwusersByTelphone(String telphone);

	/**
	 * 获取系统所有用户Id
	 * @param entity
	 * @return
	 */
	public List<String> getYwUserAllId(YwUser entity);
	/**
	 * 更新用户基本信息表
	 * @param ywUsers
	 * @return
	 */
	public Integer update(YwUser entity);
	/**
	 * 根据昵称查询用户
	 * @param nickname
	 * @return
	 */
	public YwUser getYwusersByNickname(String nickname);

	/**
	 * @Description: 根据手机号获取用户信息
	 * @param entity TODO
	 * @param telphones
	 * @return
	 */
	public List<YwUser> getYwusersByMobile(YwUser entity, String[] telphones);

	/**
	 * @Description: 绑定手机号
	 * @param id
	 * @param telphone
	 */
	public void updateMobile(String id, String telphone);
	/**
	 * @Title: updateBatchBi
	 * @Description: 批量更新用户豆币
	 * @param list
	 * @return
	 */
	public Integer updateBatchBiAndJingyan(List<YwUser> list);

	/**
	 * @Description: 获取主播信息
	 * @param name
	 * @param type
	 * @param status TODO
	 * @return
	 */
	public List<Map<String, Object>> getListMapAnchor(String name, Integer type, Integer status);
	/**
	 * 更新用户基本信息表基本属性
	 * @param ywUsers
	 * @return
	 */
	public Integer updateForBase(YwUser entity);
	/**
	 * @Title: updateStatus
	 * @Description: 批量修改用户状态
	 * @param ids
	 * @param status
	 * @param remark TODO
	 * @return
	 */
	public Integer updateStatus(String[] ids, Integer status, String remark);
	/**
	 * 添加蓝鲨币
	 * @param id
	 * @param bi
	 * @return
	 */
	public Integer updateAddBi(String id, int bi, int time);
	/**
	 * 减少蓝鲨币
	 * @param id
	 * @param bi
	 * @return
	 */
	public Integer updateReduceBi(String id, int bi);
	/**
	 * 修改消息推送开关
	 * @param id
	 * @param push
	 * @return
	 * @creationDate. 2015-12-21 下午3:45:31
	 */
	public Integer updatePush(String id, String push);

	/**
	 * @Description: 根据ID修改token信息
	 * @param id
	 * @param token
	 */
	public void updateToken(String id, String token);
	/**
	 * 根据时间查询注册用户数
	 * @param startTime
	 * @param endTime
	 * @return
	 * @creationDate. 2015-12-28 下午12:07:38
	 */
	public List<YwUser> getRegiestCount(Date startTime, Date endTime);
	/**
	 * 根据idInt获取用户基本信息表
	 * @param idInt
	 * @return
	 */
	public YwUser getYwUserByIdInt(Integer idInt);
	/**
	 * @Title: updateIsRead
	 * @Description: 更新用户已经阅读过播放页规则
	 * @param id
	 * @return
	 */
	public Integer updateIsRead(String id);
	/**
	 * @Title: updateBlankList
	 * @Description: 用户加入或移除黑名单（禁言or解除禁言）
	 * @param id 用户id
	 * @param isBlack 0：解除，1：加入
	 * @return
	 */
	public Integer updateBlackList(String id, String isBlack);

    public List<String> getYwUserAllIds(String[] ids);

    /**
     * 根据设备号查找用户
     * @param deviceId
     * @return
     */
	public List<String> getUserIdByDeveice(String deviceId);
}
