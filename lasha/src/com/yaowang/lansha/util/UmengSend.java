package com.yaowang.lansha.util;

import com.yaowang.service.impl.SysOptionServiceImpl;
import push.AndroidNotification;
import push.PushClient;
import push.android.AndroidUnicast;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-2-22
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class UmengSend {
    private static String appKey = null;
    //此处注意导出的证书密码不能为空因为空密码会报错
    private static String appMasterSecret = null;

    static {
         appKey= SysOptionServiceImpl.getValue("LANSHA.UMENG.APPKEY");
         appMasterSecret=SysOptionServiceImpl.getValue("LANSHA.UMENG.APPMASTERSCRET");
    }

    public static void push(String token, String content, String title, String roomId, String pushType) throws Exception {
        PushClient client = new PushClient();
        AndroidUnicast unicast = new AndroidUnicast(appKey, appMasterSecret);
        unicast.setDeviceToken(token);//设备的推送token
        unicast.setTicker(content);
        unicast.setTitle(title);
        unicast.setText(content);
        unicast.setExtraField("roomId", roomId);
        unicast.setExtraField("pushType", pushType);
        unicast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        unicast.setProductionMode();
        client.send(unicast);
    }

}
