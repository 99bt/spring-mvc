package com.yaowang.lansha.task.push;

import com.yaowang.lansha.util.JsonUtils;
import com.yaowang.util.ApnsSend;
import com.yaowang.util.MD5;
import com.yaowang.util.http.HttpUtil;
import javapns.notification.PushNotificationPayload;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import push.AndroidNotification;
import push.PushClient;
import push.android.AndroidUnicast;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-2-19
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class Test {



    public static void main1(String[] args) throws Exception {
//        AndiordUmengPushBean bean = new AndiordUmengPushBean("51a4afd35270151c21000007", "v57y97psvyyssul7fe2n4oxtstfclkhk");
//        bean.setType(UmengPushBean.UmengPushType.unicast);
//        bean.setDeviceTokens("AoCP4CpzzTcLMTbEAe7jB8Zv1yQcXOorZ3EMId6yU1pA");
//        bean.getPayload().setDisplayType(AndiordUmengPushBean.AndiordUmengPushDisplayType.notification);
//        bean.setProductionMode(UmengPushBean.UmengSwitch.FALSE);
//        bean.setDescription("test");
//        AndiordUmengPushBean.Payload.Body body = bean.getPayload().getBody();
//        body.setTicker("通知栏提示文字");
//        body.setTitle("通知标题单个定制推送");
//        body.setText("通知文字描述打开app");
//        body.setAfterOpen(AndiordUmengPushBean.AndiordUmengAfterOpen.go_app);
//        body.setPlayVibrate(UmengPushBean.UmengSwitch.TRUE);
//        body.setPlayLights(UmengPushBean.UmengSwitch.TRUE);
//        body.setPlaySound(UmengPushBean.UmengSwitch.TRUE);
//        bean.getAndiordPayload().getExtra().put("type", "message");
//        bean.getAndiordPayload().getExtra().put("nav", "reply");
//        String beanStr = JsonUtils.toString(bean);
//        System.out.println(beanStr);
//        String result = HttpUtil.post("http://msg.umeng.com/api/send", beanStr);
//        System.out.println(result);
       // sendAndroidUnicast();
        String content = "你关注的主播测试账号已开播121212";
        ApnsSend.push(content, "041AA35A118E4F92B2B1E24314CCD413", "", "a613007cdb724f70fba43eb3e67492cafb9fd613d817ba9b6e325565c8ba1846", "1");
        ConcurrentHashMap concurrentHashMap=new ConcurrentHashMap(100000);


    }

    public static void sendAndroidUnicast() throws Exception {
        PushClient client = new PushClient();
        AndroidUnicast unicast = new AndroidUnicast("5681f04767e58e6b230033cb", "5ens1w96mresygqzqmzcqzle6tcj6ieu");
        unicast.setDeviceToken("AjiSZmNHusULSljz9n47hKVXcHGew6aDLG0J2_fhmc5f");//设备的推送token
        String content = "你关注的主播测试账号已开播";
        unicast.setTicker(content);
        unicast.setTitle("蓝鲨Tv");
        unicast.setText(content);
        unicast.setExtraField("roomId", "3A4B9A2F6ACD43BB8563175AD7FF2813");
        unicast.setExtraField("pushType", "1");
        unicast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        unicast.setProductionMode();
//        unicast.setActivity("com.yaowang.bluesharktv.activity.LiveActivity");
        client.send(unicast);
    }

    public static void main(String[] args) {
        ImportParams params = new ImportParams();
//        params.setTitleRows(2);
//        params.setHeadRows(2);
//        //params.setSheetNum(9);
//        params.setNeedSave(true);
        long start = new Date().getTime();
        List<Map<String,Object>> list = ExcelImportUtil.importExcel(new File(
                "c:/aa.xlsx"), Map.class, params);
        System.out.println(111);
    }

}
