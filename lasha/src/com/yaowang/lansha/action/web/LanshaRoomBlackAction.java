package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.LanshaRoomBlacklist;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.LanshaRoomBlacklistService;
import com.yaowang.lansha.service.YwUserRoomAdminService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.openfire.http.MessageTool;

/**
 * 禁言管理
 * @author zhanghq
 * @date 2016年3月1日 下午6:05:24
 */
public class LanshaRoomBlackAction extends LanshaBaseAction {

	private static final long serialVersionUID = 1L;
	//被禁言用户id
	private String bid;
	@Resource
	private YwUserRoomAdminService ywUserRoomAdminService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private LanshaRoomBlacklistService lanshaRoomBlacklistService;
	@Resource
	private YwUserService ywUserService;
	
	public String blackName; 
	
	private List<LanshaRoomBlacklist> blackList;
	
	
	private static final String BAN_TYPE = "3";
	
	/**
	 * 直播间禁止发言
	 * @throws IOException
	 * @throws InterruptedException
	 * @Description: 禁止发言
	 */
	public void banMsg()  throws IOException, InterruptedException{
		if(StringUtils.isBlank(id) || StringUtils.isBlank(bid)){
			write(getFailed("错误，请求参数错误，参数不能为空!"));
			return;
		}
		final YwUser me = getUserLogin();
		boolean host = isRoomHost(id,me.getId());
		if(false == host){
			boolean permission = ywUserRoomAdminService.containsAdmin(id,me.getId());
			if(false == permission){
				write(getFailed("抱歉，没有禁言权限!"));
				return;
			}
		}
		boolean bidHost = isRoomHost(id,bid);
		boolean bidPermission = ywUserRoomAdminService.containsAdmin(id,bid);
		if(bidHost || bidPermission){
			write(getFailed("抱歉，不能对本房间主播和管理员禁言!"));
			return;
		}

		if(lanshaRoomBlacklistService.getIsBlackByRoomAndUser(id, bid)){
			write(getFailed("该用户已被禁言!"));
			return;
		}
        final YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(id);
        LanshaRoomBlacklist blacklist = new LanshaRoomBlacklist();
        blacklist.setRoomId(id);
        blacklist.setImRoom(ywUserRoom.getOpenfireRoom());
        blacklist.setUserId(bid);
        blacklist.setAdminId(me.getId());
        blacklist.setType(BAN_TYPE);
        blacklist.setCreateTime(getNow());
        lanshaRoomBlacklistService.save(blacklist);
        final YwUser blackUser = ywUserService.getYwUserById(bid);
        AsynchronousService.submit(new ObjectCallable() {
			@Override
			public Object run() throws Exception {
				//发送消息
				JSONObject object = new JSONObject();
				object.put("uId", bid);
				object.put("uName", blackUser.getNickname() == null ? blackUser.getAccount() : blackUser.getNickname());
				object.put("execName", me.getNickname() == null ? "[管理员]" : me.getNickname());
				object.put("timeDes", "永久");
				object.put("type", 5);
				try {
					MessageTool.sendMessage(ywUserRoom.getOpenfirePath(), ywUserRoom.getOpenfireRoom(), ywUserRoom.getOpenfireConference(), object.toJSONString());
				} catch (Exception e) {
					//im发送失败
				}
				return null;
			}
		});
		writeSuccess(null);
	}
	
	/**
	 * 个人中心-解禁用户
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void deleteBlack() throws IOException, InterruptedException{
		if(StringUtils.isBlank(bid)){
			write(getFailed("错误，请求参数错误，参数不能为空!"));
			return;
		}
		final YwUser me = getUserLogin();
		final YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomByUid(me.getId());
		if(ywUserRoom == null){
			write(getFailed("你还不是主播"));
			return;
		}
		if(!lanshaRoomBlacklistService.getIsBlackByRoomAndUser(ywUserRoom.getId(), bid)){
				write(getFailed("该用户未被禁言"));
				return;
		}
		try {
			lanshaRoomBlacklistService.deleteByRoomIdAndUserId(ywUserRoom.getId() , bid);

			final YwUser blackUser = ywUserService.getYwUserById(bid);
	        AsynchronousService.submit(new ObjectCallable() {
				@Override
				public Object run() throws Exception {
					//发送消息
					JSONObject object = new JSONObject();
					object.put("uId", bid);
					object.put("uName", blackUser.getNickname() == null ? blackUser.getAccount() : blackUser.getNickname());
					object.put("execName", me.getNickname() == null ? "[管理员]" : me.getNickname());
					object.put("type", 6);
					try {
						MessageTool.sendMessage(ywUserRoom.getOpenfirePath(), ywUserRoom.getOpenfireRoom(), ywUserRoom.getOpenfireConference(), object.toJSONString());
					} catch (Exception e) {
						//im发送失败
					}
					return null;
				}
			});
			write(EMPTY_ENTITY);
		} catch (Exception e) {
			write(getFailed("操作失败"));
		}
	}
	

	/**
	 * 个人中心添加禁言
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void addBlack()  throws IOException, InterruptedException{
		String uid = getUserLogin().getId();
		YwUserRoom YwUserRoom = ywUserRoomService.getYwUserRoomByUid(uid);
		if(YwUserRoom == null){
			write(getFailed("你还不是主播"));
			return;
		}
		if(StringUtils.isBlank(blackName)){
			write(getFailed("请输入待禁言用户昵称"));
			return;
		}
		YwUser blackUser = ywUserService.getYwusersByNickname(blackName);
		if(blackUser == null){
			write(getFailed("找不到用户信息"));
			return;
		}
		id = YwUserRoom.getId();
		bid=blackUser.getId();
		banMsg();
	}
	
	/**
	 * 房间管理-禁言管理
	 * @return
	 */
	public String roomBlackList(){
		YwUser me = getUserLogin();
		YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(me.getId());
		if(null != room){
			LanshaRoomBlacklist query = new LanshaRoomBlacklist();
			query.setRoomId(room.getId());
			blackList = lanshaRoomBlacklistService.getLanshaRoomBlacklistList(query);
			if(!CollectionUtils.isEmpty(blackList)){
				for(int i=0;i<blackList.size();i++){
					YwUser ywUser =ywUserService.getYwUserById(blackList.get(i).getUserId());
					if(null != ywUser){
						blackList.get(i).setUserNickname(ywUser.getNickname());
						blackList.get(i).setUserHeadpic(getUploadFilePath(ywUser.getHeadpic()));
					}
				}
			}
		}
		if(null == blackList){
			blackList = new ArrayList<LanshaRoomBlacklist>();
		}
		return SUCCESS;
	
	}

	/**
	 * 是否为某房间主播
	 * @param roomId
	 * @param uid
	 * @return
	 */
	private boolean isRoomHost(String roomId, String uid) {
		if(StringUtils.isBlank(roomId) || StringUtils.isBlank(uid)){
			return false;
		}
		YwUserRoom room  = ywUserRoomService.getYwUserRoomByUid(uid);
		if(null == room){
			return false;
		}
		return room.getId().equals(roomId);
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public List<LanshaRoomBlacklist> getBlackList() {
		return blackList;
	}

	public void setBlackList(List<LanshaRoomBlacklist> blackList) {
		this.blackList = blackList;
	}


	public String getBlackName() {
		return blackName;
	}


	public void setBlackName(String blackName) {
		this.blackName = blackName;
	}
}
