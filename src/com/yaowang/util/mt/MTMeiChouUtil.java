package com.yaowang.util.mt;

import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.http.HttpUtil;
import com.yaowang.util.json.JsonUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-2
 * Time: 下午12:39
 * To change this template use File | Settings | File Templates.
 */
public class MTMeiChouUtil {
    /**
     * 短信发送服务器
     */
    private static String MT_SERVER = "http://sms.maychoo.com";
    /**
     * 短信服务账户
     */
    private static String MT_OPEN_ID = "smstest";
    /**
     * 短信服务密码
     */
    private static String MT_PK = "9F7B5B06C87D9E71F9D9126E8D9FC923C0B77CE8EB43BBF05FAD908F65C2A302CC9B013A3C0B1CB0947EE7718CA6EB09";
    /**
     * 错误码
     */
    private static Map<String, String> errorMap = new HashMap<String, String>();

    static {
//        refresh();
//		errorMap.put("0", "发送成功");
        errorMap.put("-4", "系统错误");
        errorMap.put("-1", "用户名或密码错误");
        errorMap.put("-7", "验证码错误");
        errorMap.put("-2", "余额不足");
        errorMap.put("-3", "超日流量或欠费");
        errorMap.put("-6", "超出单次最大提交数（5000）");
        errorMap.put("-10", "消息内容不能为空字");
        errorMap.put("-11", "消息内容不能超过300");
        errorMap.put("-12", "短信提交批次不能为空");

    }

    /**
     * 刷新配置
     */
    public static void refresh() {
//        MT_SERVER = SysOptionServiceImpl.getValue("SYS.MC.SERVER");
//        MT_OPEN_ID = SysOptionServiceImpl.getValue("SYS.MC.OPEN_ID");
//        MT_OPEN_PASS = SysOptionServiceImpl.getValue("SYS.MC.OPEN_PASS");
    }

    /**
     * 发送消息
     *
     * @param content
     * @param mobile
     * @return
     * @throws Exception
     */
    public static String sendMt(String content, String mobile) throws Exception {
        String str = String.format(MT_SERVER + "/home/sms/send?username=%s&pk=%s&content=%s&mobile=%s", MT_OPEN_ID, MT_PK, content, mobile);
        JSONObject json = HttpUtil.getJsonByHttp(str, null);
        String code =((String)json.get("code")).replace(" ","");
        if (code.equals("0")) {
            //发送成功
            return null;
        } else if (errorMap.containsKey(code)) {
            return errorMap.get(code);
        } else {
            return "发送失败";
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(sendMt("test", "13675824165"));

    }
}
