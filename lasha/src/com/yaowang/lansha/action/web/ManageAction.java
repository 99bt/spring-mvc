package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomAdmin;
import com.yaowang.lansha.service.YwUserRoomAdminService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.UUIDUtils;

/**
 * @ClassName: ManageAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tandingbo
 * @date 2015年12月10日 下午8:14:33
 * 
 */
public class ManageAction extends LanshaBaseAction {
	private static final long serialVersionUID = -8036030833045041470L;
	
	@Resource
	private YwUserRoomAdminService ywUserRoomAdminService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwUserService ywUserService;
	
	public String manageName; // 管理员名称
	
	private List<YwUserRoomAdmin> adminList;
	
	/**
	 * @Description: 我的管理员页面
	 * @return
	 */
	public String manage(){
		YwUser me = getUserLogin();
		YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(me.getId());
		if(null != room){
			YwUserRoomAdmin query = new YwUserRoomAdmin();
			query.setRoomId(room.getId());
			adminList = ywUserRoomAdminService.getYwUserRoomAdminList(query);
		}
		if(null == adminList){
			adminList = new ArrayList<YwUserRoomAdmin>();
		}else{
			for(int i =0;i<adminList.size();i++){
				YwUser ywUser =ywUserService.getYwUserById(adminList.get(i).getUserId());
				if(null != ywUser){
					adminList.get(i).setNickname(ywUser.getNickname());
					adminList.get(i).setHeadpic(getUploadFilePath(ywUser.getHeadpic()));
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * @throws IOException 
	 * @Description: 我的管理员
	 */
	public void listManage() throws IOException{
		// 当前登录用户ID
		String uid = getUserLogin().getId();
		YwUserRoom YwUserRoom = ywUserRoomService.getYwUserRoomByUid(uid);
		if(YwUserRoom == null){
			write(getFailed("你还不是主播"));
			return;
		}
		YwUserRoomAdmin roomAdmin = new YwUserRoomAdmin();
		roomAdmin.setRoomId(YwUserRoom.getId());
		List<YwUserRoomAdmin> managerList = ywUserRoomAdminService.getYwUserRoomAdminList(roomAdmin);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("managerList", managerList);
		result.put("status", 1);
		write(JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat));
	}
	
	/**
	 * @throws IOException 
	 * @Description: 添加我的管理员
	 */
	public void saveManage() throws IOException{
		// 当前登录用户ID
		String uid = getUserLogin().getId();
		YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomByUid(uid);
		if(ywUserRoom == null){
			write(getFailed("你还不是主播"));
			return;
		}
		if(StringUtils.isBlank(manageName)){
			write(getFailed("请输入管理员名称"));
			return;
		}
		YwUser manageUser = ywUserService.getYwusersByNickname(manageName);
		if(manageUser == null){
			write(getFailed("找不到管理员信息"));
			return;
		}
		if(manageUser.getId().equals(uid)){
			write(getFailed("不能设置自己为管理员"));
			return;
		}
		
		YwUserRoomAdmin ywUserRoomAdmin = new YwUserRoomAdmin();
		ywUserRoomAdmin.setUserId(manageUser.getId());
		ywUserRoomAdmin.setRoomId(ywUserRoom.getId());
		List<YwUserRoomAdmin> listRoomAdmin = ywUserRoomAdminService.getYwUserRoomAdminList(ywUserRoomAdmin);

        YwUserRoomAdmin query = new YwUserRoomAdmin();
        query.setRoomId(ywUserRoom.getId());
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
		if(CollectionUtils.isNotEmpty(listRoomAdmin)){
			write(getFailed("管理员已经添加"));
			return;
		}
		YwUserRoomAdmin roomAdmin = new YwUserRoomAdmin();
		roomAdmin.setId(UUIDUtils.newId());
		roomAdmin.setBi(0);
		roomAdmin.setTimeLength(0);
		roomAdmin.setRoomId(ywUserRoom.getId());
		roomAdmin.setUserId(manageUser.getId());
		roomAdmin.setHeadpic(manageUser.getHeadpic());
		roomAdmin.setNickname(manageUser.getNickname());
		roomAdmin.setCreateTime(getNow());
		
		try {
			ywUserRoomAdminService.save(roomAdmin, ywUserRoom);
			write(EMPTY_ENTITY);
		} catch (Exception e) {
			write(getFailed("添加失败"));
		}
	}
	
	/**
	 * @throws IOException 
	 * @Description: 删除我的管理员
	 */
	public void deleteManage() throws IOException{
		// 当前登录用户ID
		String uid = getUserLogin().getId();
		YwUserRoom userRoom = ywUserRoomService.getYwUserRoomByUid(uid);
		if(userRoom == null){
			write(getFailed("你还不是主播"));
			return;
		}
		if(StringUtils.isBlank(id)){
			write(getFailed("请选择管理员记录"));
			return;
		}
		try {
			ywUserRoomAdminService.deleteRoomAdminById(userRoom, id);
			write(EMPTY_ARRAY);
		} catch (Exception e) {
			write(getFailed("操作失败"));
		}
	}

	public String getManageName() {
		return manageName;
	}

	public void setManageName(String manageName) {
		this.manageName = manageName;
	}

	public List<YwUserRoomAdmin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<YwUserRoomAdmin> adminList) {
		this.adminList = adminList;
	}
	
}
