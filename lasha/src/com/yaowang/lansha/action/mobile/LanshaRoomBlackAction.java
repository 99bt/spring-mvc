package com.yaowang.lansha.action.mobile;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaMobileAction;
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

import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-1
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 */
public class LanshaRoomBlackAction extends LanshaMobileAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
    private LanshaRoomBlacklistService lanshaRoomBlacklistService;
    @Resource
    private YwUserRoomService ywUserRoomService;
	@Resource
	private YwUserRoomAdminService ywUserRoomAdminService;

    private String roomId;
    /**
     * 禁言用户Id
     */
    private String userId;

    private String type = "3";
	@Resource
	private YwUserService ywUserService;


    /**
     * 禁言设置
     *
     * @throws IOException
     * @throws ParseException
     */
    public void muzzled() throws IOException, ParseException {
		if(StringUtils.isBlank(roomId) || StringUtils.isBlank(userId)){
			write(getFailed("错误，请求参数错误，参数不能为空!"));
			return;
		}
		final YwUser me = getUserLogin();
		boolean host = isRoomHost(roomId,me.getId());
		if(false == host){
			boolean permission = ywUserRoomAdminService.containsAdmin(roomId,me.getId());
			if(false == permission){
				write(getFailed("抱歉，没有禁言权限!"));
				return;
			}
		}
		boolean bidHost = isRoomHost(roomId,userId);
		boolean bidPermission = ywUserRoomAdminService.containsAdmin(roomId,userId);
		if(bidHost || bidPermission){
			write(getFailed("抱歉，不能对本房间主播和管理员禁言!"));
			return;
		}
		if(lanshaRoomBlacklistService.getIsBlackByRoomAndUser(roomId, userId)){
			write(getFailed("该用户已被禁言!"));
			return;
		}
        final YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(roomId);
        LanshaRoomBlacklist blacklist = new LanshaRoomBlacklist();
        blacklist.setRoomId(roomId);
        blacklist.setImRoom(ywUserRoom.getOpenfireRoom());
        blacklist.setUserId(userId);
        blacklist.setAdminId(me.getId());
        blacklist.setType(type);
        blacklist.setCreateTime(getNow());
        lanshaRoomBlacklistService.save(blacklist);
        final YwUser blackUser = ywUserService.getYwUserById(userId);
        AsynchronousService.submit(new ObjectCallable() {
			@Override
			public Object run() throws Exception {
				//发送消息
				JSONObject object = new JSONObject();
				object.put("uId", userId);
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
		write(EMPTY_ENTITY);
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
