package com.yaowang.lansha.action.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaImGiveLog;
import com.yaowang.lansha.entity.LanshaUserGiftStock;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.LanshaImGiveLogService;
import com.yaowang.lansha.service.LanshaUserGiftStockService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.openfire.http.MessageTool;
import com.yaowang.util.spring.ContainerManager;

/**
 * openfire回调
 * @author shenl
 *
 */
public class ImCallbackAction extends LanshaBaseAction{
	private static final long serialVersionUID = 1L;

	/**
	 * 发言送虾米
	 * @throws IOException 
	 */
	public void callback() throws IOException{
		if (StringUtils.isNotBlank(name)) {
			ObjectCallable call = new ObjectCallable("发言送虾米") {
				@Override
				public Object run() throws Exception {
					YwUserRoomService roomService = (YwUserRoomService)ContainerManager.getComponent("ywUserRoomService");
					YwUserService ywUserService = (YwUserService)ContainerManager.getComponent("ywUserService");
					LanshaUserGiftStockService lanshaUserGiftStockService = (LanshaUserGiftStockService)ContainerManager.getComponent("lanshaUserGiftStockService");
					LanshaImGiveLogService lanshaImGiveLogService = (LanshaImGiveLogService)ContainerManager.getComponent("lanshaImGiveLogService");
					
					String data = getData().toString();
					data = URLDecoder.decode(data, "utf-8");
					
					Map<String, Integer> map = JSON.parseObject(data, Map.class);
					//发言得虾米概率
					Integer probability = Integer.valueOf(SysOptionServiceImpl.getValue("LANSHA.IM.XIAOMI.PROBABILITY"));
						
					for (String key : map.keySet()) {
						try {
							String[] strs = getUserId(key);
							if (strs != null) {
								String userId = strs[1];
								Integer count = map.get(key);
								
								// 概率计算
								boolean b = false;
								for (int i = 0; i < count; i++) {
									double d = Math.random() * 100;
									if (d <= probability) {
										b = true;
										break;
									}
								}
								
								if (!b) {
									continue;
								}
								// 中奖
								// 查询是否已赠送20次
								LanshaImGiveLog giveLog = new LanshaImGiveLog();
								giveLog.setUserId(userId);
								giveLog.setCreateTime(getNow());
								int number = lanshaImGiveLogService.getGiveNumber(giveLog);
								if (number >= 20) {
									continue;
								}
	
								// 获取房间信息
								YwUserRoom room = new YwUserRoom();
								room.setOpenfireRoom(strs[3]);
								List<YwUserRoom> rooms = roomService.getYwUserRoomList(room);
								if (rooms.isEmpty()) {
									continue;
								}
								room = rooms.get(0);
								
								// 获取用户
								YwUser user = ywUserService.getYwUserById(userId);
								if (user == null) {
									continue;
								}
								
								// 随机1-5个虾米
								int stock = (int)(Math.random() * 5) + 1;
								// 更新库存
								LanshaUserGiftStock entity = new LanshaUserGiftStock();
								entity.setUserId(userId);
								entity.setGiftId(LanshaConstant.GIFT_ID);
								entity.setStock(stock);
								lanshaUserGiftStockService.updateLottery(entity);
								
								// 添加赠送记录
								LanshaImGiveLog log = new LanshaImGiveLog();
								log.setUserId(userId);
								log.setCreateTime(getNow());
								log.setXiami(stock);
								log.setRoomId(room.getId());
								lanshaImGiveLogService.save(log);
								
								// 获取库存
								LanshaUserGiftStock giftStock = lanshaUserGiftStockService.getByGiftIdAndUserId(entity.getGiftId(), entity.getUserId());
								// 发送im消息
								JSONObject object = new JSONObject();
								object.put("uId", userId);
								object.put("uName", user.getNickname());
								object.put("uInventory", giftStock.getStock());
								object.put("giftId", entity.getGiftId());
								object.put("reason", "积极发言");
								object.put("giftDes", stock + LanshaConstant.GIFT_NAME);
								object.put("type", 7);
								MessageTool.sendMessage(room.getOpenfirePath(), room.getOpenfireRoom(), room.getOpenfireConference(), object.toJSONString());
							}
						} catch (Exception e) {
							//不需要提示
						}
					}
					return null;
				}
			};
			call.setData(name);
			AsynchronousService.submit(call);
		}
		write("1");
	}
	/**
	 * 解析用户id
	 * @param nickName
	 * @return
	 */
	public static String[] getUserId(String nickName){
		String[] strs = nickName.split("\\|");
		if (strs.length == 4 && strs[1].length() == 32) {
			return strs;
		}
		return null;
	}
}
