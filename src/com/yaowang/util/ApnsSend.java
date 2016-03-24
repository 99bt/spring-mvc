package com.yaowang.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

/**
 * iOS app推送
 * @author Administrator
 *
 */
public class ApnsSend {
	private static String certificatePath = null;
	//此处注意导出的证书密码不能为空因为空密码会报错
	private static String certificatePassword = "lanshatv";
	private static String type = "1";
	static{
		// 证书地址
		type = LanshaResource.dataMap.get("apns-path");
		String path = "";
		if ("1".equals(type)) {
			//正式
			path = "lansha_aps_production.p12";
		}else {
			//测试
			path = "lansha_aps_development.p12";
		}
		
		URL url = new ApnsSend().getClass().getClassLoader().getResource(path);
		File file = FileUtils.toFile(url);
		if (!file.exists()) {
			System.out.println("证书不存在:" + path);
		}else {
			System.out.println("证书已找到:" + path);
		}
		certificatePath = file.getAbsolutePath();
	}
    
	/**
	 * 三次重试推送
	 * @param alert
	 * @param deviceToken
	 */
	public static void push(String alert, String id, String sound, String deviceToken,String pushType){
//		System.out.println("----iOS推送消息=" + alert + "&deviceToken=" + deviceToken);
//        System.out.println("--------------------------");
//        System.out.println("1".equals(type));
//        System.out.println(deviceToken);
//        System.out.println(alert);
//        System.out.println("--------------------------");
        
		if (StringUtils.isEmpty(alert)) {
			return;
		}
		if (alert.length() > 200) {
			alert = alert.substring(0, 199) + "...";
		}
		for (int i = 0; i < 3; i++) {
			try {
				push2(alert, id, getDefaultSound(), deviceToken, pushType);
				break;
			} catch (Exception e) {
				if (e instanceof CommunicationException || e instanceof IOException) {
					//暂停3秒后重推
					try {
						Thread.sleep(3 * 1000);
					} catch (InterruptedException e1) {
//						e1.printStackTrace();
					}
				}else {
					e.printStackTrace();
					break;
				}
			}
		}
	}
	/**
	 * 获取默认提示信息
	 * @return
	 */
	public static String getDefaultSound(){
		String sound = null;
		Integer currentTime = Integer.parseInt(DateUtils.format(new Date(), "HH"));
		if (currentTime >= 23 || currentTime <= 7) {
			sound = null;
		}else {
			sound = "default";
		}
		return sound;
	}
	
	/**
	 * 推送
	 * @param alert
	 * @param deviceToken
	 * @throws Exception
	 */
    private static void push2(String alert, String id, String sound, String deviceToken,String pushType) throws JSONException, CommunicationException, KeystoreException, InvalidDeviceTokenFormatException, IOException {
    	int badge = 0;//图标小红圈的数值

        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);
        boolean sendCount = true;
        
        PushNotificationPayload payLoad = new PushNotificationPayload();
        payLoad.addAlert(alert); // 消息内容
        payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
        
        payLoad.addCustomDictionary("pushType", pushType);//推送消息类型 1：主播上线
        payLoad.addCustomDictionary("roomId", id);//订单ID
        
        if (StringUtils.isNotBlank(sound)) {
            payLoad.addSound(sound);//铃音
        }
        PushNotificationManager pushManager = new PushNotificationManager();
        try {
	        //true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
	        pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, "1".equals(type)));
	        
	        List<PushedNotification> notifications = new ArrayList<PushedNotification>();
	        // 发送push消息
	        if (sendCount) {
	            Device device = new BasicDevice();
	            device.setToken(tokens.get(0));
	            PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
	            notifications.add(notification);
	        } else {
	            List<Device> device = new ArrayList<Device>();
	            for (String token : tokens) {
	                device.add(new BasicDevice(token));
	            }
	            notifications = pushManager.sendNotifications(payLoad, device);
	        }
	        List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
	        List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
	        int failed = failedNotifications.size();
	        int successful = successfulNotifications.size();
		} finally{
			if (pushManager != null) {
				pushManager.stopConnection();
			}
		}
    }
    
    public static void main(String[] args) throws Exception {
    	String device = "dab81095e6b1893b4f3febb1e5cf29e2f9d508d9aa7bbd120b8d31239cffbaa0";
//    	String device = "8abd6f161c8472c94de851352a7c0b5084a3789ddfd02d1b48dc540d358c7515";
//    	for (int i = 0; i < 10; i++) {
    		push("testaaa111\n123", "123", getDefaultSound(), device, "1");
//		}
	}
}