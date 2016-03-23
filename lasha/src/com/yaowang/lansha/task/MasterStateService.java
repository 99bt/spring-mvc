package com.yaowang.lansha.task;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yaowang.common.util.LogUtil;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomLog;
import com.yaowang.lansha.service.YwUserRoomLogService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.cmd.CommandUtil;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.openfire.http.RoomTool;
import com.yaowang.util.spring.ContainerManager;

/**
 * 定时统计主播状态
 * @author Administrator
 *
 */
@Service("masterStateService")
public class MasterStateService {
	/**
	 * 是否正在统计
	 */
	private static Boolean IS_REPORT = false;
	
	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwUserRoomLogService ywUserRoomLogService;
	
	/**
	 * 请求数据
	 */
	public void report(){
		if (IS_REPORT) {
			LogUtil.log("FMS.INTERFACE", "定时统计主播状态正在统计，本次统计失败！");
			return;
		}
		try {
			IS_REPORT = true;
			try {
				openfire();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally{
			IS_REPORT = false;
		}
	}
	/**
	 * 获取openfire人数
	 */
	private void openfire(){
		try {
			//如果前面任务截图没停止，则直接kill掉
			String[] cmds = new String[]{"/bin/sh", "-c", "killall -9 ffmpeg" };
			CommandUtil.execCommand(cmds);
		} catch (Exception e1) {
			//
		}
		
		YwUserRoom entity = new YwUserRoom();
		entity.setOnline(LanshaConstant.ROOM_STATUS_ONLINE);

		List<YwUserRoom> rooms = ywUserRoomService.getYwUserRoomList(entity, null, null, true, null);
		Date now = new Date();
		for (int i=0; i<rooms.size(); i++) {
			final YwUserRoom room = rooms.get(i);
			try {
				Integer count = RoomTool.getUserCount(room.getOpenfirePath(), room.getOpenfireRoom(), room.getOpenfireConference());
				if (count == null) {
					//自动重新生成im房间
//					RoomXmppUtil.createRoom(OpenFireConstant.ADMIN_NAME, room.getOpenfireRoom(), room.getOpenfireConference());
					RoomTool.createRoom(entity.getOpenfirePath(), room.getOpenfireRoom(), room.getOpenfireConference());
					count = 0;
				}
//				System.out.println("room.getRoomId()=" + room.getRoomId() + "," + count);
				//记录日志
				YwUserRoomLog log = new YwUserRoomLog();
				log.setCreateTime(now);
				log.setNumber(count);
				log.setRoomId(room.getId());
				log.setUserId(room.getUid());
				ywUserRoomLogService.save(log);
				
				if (room.getNumber() != count) {
					YwUserRoom temp = new YwUserRoom();
					temp.setRoomId(room.getRoomId());
					temp.setNumber(count);
					temp.setUpdateTime(now);
					
					ywUserRoomService.updateByRoomId(temp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				//网宿直播快照
//				WsLiveUtil.liveToPic(room.getRtmp(), room.getRoomId());
				//本地ffmpeg截图(分布截图，避免ffmpeg压力过大)
				liveToPic(room, i);
			} catch (Exception e) {
				//截图报错，不用处理
				e.printStackTrace();
			}
		}
		// 记录日志
		LogUtil.log("FMS.INTERFACE", "定时统计主播状态");
	}
	/**
	 * 本地ffmpeg截图
	 * @param room
	 * @throws Exception
	 */
	public static void liveToPic(final YwUserRoom room, final int index) throws Exception{
		if (StringUtils.isEmpty(room.getLiveHost()) || StringUtils.isEmpty(room.getRoomId())) {
			return;
		}
		AsynchronousService.submit(new ObjectCallable() {
			@Override
			public Object run() throws Exception {
				Thread.sleep(index * 100);
				//获取临时路径
				File picfile = new File(StorePathUtil.getStoreTempPath(new Date()).append(System.currentTimeMillis()).toString());
				
				try {
					if (!picfile.getParentFile().exists()) {
						picfile.getParentFile().mkdirs();
					}
					String cmd = String.format("/usr/local/ffmpeg/bin/ffmpeg -i %s -f image2 -ss 1 -vframes 1 -s 516*288 %s", room.getLiveHost() + room.getRoomId(), picfile.getAbsoluteFile());
					String[] cmds = new String[]{"/bin/sh", "-c", cmd };
					CommandUtil.execCommand(cmds);
					
					//拷贝至文件系统服务器
					String liveImg = "/room/pic/" + room.getIdInt() + "/" + System.currentTimeMillis() + ".jpg";
					FileSystemUtil.saveFile(picfile, liveImg);
					
					//保存图片地址
					YwUserRoom temp = new YwUserRoom();
					temp.setRoomId(room.getRoomId());
					temp.setLiveImg(liveImg);
					YwUserRoomService ywUserRoomService = (YwUserRoomService)ContainerManager.getComponent("ywUserRoomService");
					ywUserRoomService.updateLiveImgByRoomId(temp);
					
					//删除原因截图
					if (StringUtils.isNotBlank(room.getLiveImg())) {
						if (FileSystemUtil.fileExists(room.getLiveImg())) {
							FileSystemUtil.deleteFile(room.getLiveImg());
						}
					}
				} finally {
					if (picfile.exists()) {
						picfile.delete();
					}
				}
				return null;
			}
		});
		
	}
}
