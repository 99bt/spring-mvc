/*
package com.lansha.test;

import com.hzhl.connector.MessageTool;
import com.hzhl.exception.LotteryException;
import com.hzhl.util.*;
import com.hzhl.util.alipayUtil.services.AlipayService;
import com.hzhl.util.alipayUtil.util.AlipayNotify;
import com.hzhl.base.BaseAction;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

*/
/**
 * 支付宝充值 (余额、扫码支付)
 *
 * @author  wm
 *
 * @version 1.0
 *
 * @date 2014-08-28
 *
 *//*

public class AlipayAction extends BaseAction {

    private final static Logger logger = Logger.getLogger(AlipayAction.class);
    */
/**
     * 支付宝充值页面
     * @return
     * @throws Exception
     *//*

    public String alipayView() throws Exception {
            return SUCCESS;
    }

    */
/**
     * 支付宝充值(余额、扫码)
     *
     * @return
     * @throws Exception
     *//*

    public String aliPay() throws Exception {
        String[] bankInfo = null;
        double money_order=0D;
        String promptStr ="";
        try {
            String czMoney = getRequest().getParameter("amount");
            try {
                BigDecimal bg = new BigDecimal(czMoney);
                money_order = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (money_order < 10) {
                    promptStr = "充值金额至少为10元！";
                    getRequest().setAttribute("msg", promptStr);
                    return "fail";
                }
            } catch (Exception e) {
                logger.error("充值金额的格式不正确!");
                promptStr = "充值金额的格式不正确！";
                getRequest().setAttribute("msg", promptStr);
                return "fail";
            }
            String paytype=getRequest().getParameter("bank_code");
            if(StringUtil.isEmpty(paytype)){
                promptStr ="请选择充值方式";
                getRequest().setAttribute("msg", promptStr);
                return "fail";
            }else{
                bankInfo = getChargeInfo(Integer.parseInt(paytype));
            }
            String detail = bankInfo[1]+""+bankInfo[0];  //充值详情
            String myInType = String.valueOf(CapitalInOutConstants.ZFB_IN_TYPE); //充值类型id
            String userIp = HLHelper.getUserIP(getRequest()); //用户ip
            String total_fee = String.valueOf(money_order); //充值金额
            String userId = this.getUserId();
            //添加充值流水记录
            String params[] = new String[]{getUserName(),getPassword(), total_fee, myInType, detail, userIp};
            String[] resultArr = new MessageTool().split(helpMessage(params, "C0003"));
            String out_trade_no = resultArr[1];          // 充值订单号
            logger.info("<aliPay>userId="+userId + ", <充值存入记录> type="+bankInfo[0]+" , detail="+detail+" , payId="+out_trade_no+" , money="+money_order);
            String subject = "浙彩网充值";  // 商品名称
            String body = subject;          // 商品描述，推荐格式：商品名称（订单编号：订单编号）
            String payment_type = "1";      // 支付宝类型.1代表商品购买
            String show_url = HLConfig.get("Letousky.LianLian.notify_url");
            String notify_url = "";// 接收异步通知URL
            String return_url = "";// 接收同步通知URL
            Map<String, String> requestParamMap = new HashMap<String, String>();
            String returnStr = "success";
            if(paytype.equals("6666")){ //余额支付
                notify_url = show_url+ getRequest().getContextPath()+ "/aliPayNotify.htm";
                return_url = show_url+ getRequest().getContextPath()+ "/user/aliPayReturn.htm";
                requestParamMap.put("body", body);
            }else{ //扫码支付
                notify_url = show_url+ getRequest().getContextPath()+  "/aliPaySmNotify.htm";
                return_url = show_url+ getRequest().getContextPath()+  "/user/aliPaySmReturn.htm";
                requestParamMap.put("qr_pay_mode","2"); //扫码跳转模式
                returnStr = "sm";
            }
            requestParamMap.put("subject", subject);
            requestParamMap.put("out_trade_no", out_trade_no);
            requestParamMap.put("total_fee", total_fee);
            requestParamMap.put("show_url", show_url);
            requestParamMap.put("payment_type",payment_type);
            requestParamMap.put("notify_url", notify_url);
            requestParamMap.put("return_url",return_url);
            String paymethod="";
            String defaultbank = "";
            if(!StringUtil.isEmpty(paymethod))  {
                requestParamMap.put("paymethod", "bankPay");
            }
            if(!StringUtil.isEmpty(defaultbank)){
                requestParamMap.put("defaultbank", defaultbank);
            }
            //防钓鱼时间戳
            String anti_phishing_key  = AlipayService.query_timestamp();	//获取防钓鱼时间戳函数;
            if(!StringUtil.isBlankOrEmpty(anti_phishing_key)){
                requestParamMap.put("anti_phishing_key", anti_phishing_key);
            }
            //获取客户端的IP地址
            String exter_invoke_ip= HLHelper.getUserIP(getRequest());
            requestParamMap.put("exter_invoke_ip", exter_invoke_ip);
            requestParamMap = AlipayService.create_direct_pay_by_user(requestParamMap);
            if(logger.isInfoEnabled()){
                logger.info("<alipay>参数=>" + requestParamMap);
            }
            getRequest().setAttribute("requestParamMap", requestParamMap);
            return returnStr;
        } catch (LotteryException le) {
            logger.error("<alipay>"+le.getErrNo() + " " + le.getErrMsg());
            getRequest().setAttribute("msg", le.getErrMsg());
            return "fail";
        } catch (Exception e) {
            e.printStackTrace();
            getRequest().setAttribute("msg", e.getMessage());
            return "fail";
        }
    }

    */
/**
     * 充值申请提交支付宝完成以后,支付宝异步通知回复
     * @return
     * @throws Exception
     *//*

    public String aliPayNotify()throws Exception{
        getRequest().setCharacterEncoding("UTF-8");
        getResponse().setContentType("text/html;charset=UTF-8");
        logger.info("<alipayNotify> 异步通知回调开始");
        PrintWriter out = getResponse().getWriter();
        String sign = getRequest().getParameter("sign");
        String notify_type = getRequest().getParameter("notify_type");
        String trade_no = getRequest().getParameter("trade_no");
        String payment_type = getRequest().getParameter("payment_type");
        String trade_status = getRequest().getParameter("trade_status");
        String subject = getRequest().getParameter("subject");
        String body = getRequest().getParameter("body");
        String out_trade_no = getRequest().getParameter("out_trade_no");
        String total_fee = getRequest().getParameter("total_fee");
        String notifyId = getRequest().getParameter("notify_id");
        logger.info("<alipayNotify> sign =>"+sign+" , notify_id=>"+notifyId+" , trade_status=>"+trade_status+" , notify_type=>"+notify_type+" , trade_no=>"+trade_no+
                      " out_trade_no=>"+out_trade_no+" , total_fee=>"+total_fee+" , subject=>"+subject+" , body=>"+body+" , payment_type=>"+payment_type);
        Map params = getPamateMap(getRequest());
        boolean isFlag = false;
        if (AlipayNotify.verify(params)) {//验证成功
            if ("TRADE_SUCCESS".equalsIgnoreCase(trade_status) || "TRADE_FINISHED".equalsIgnoreCase(trade_status)) {
                try {
                    String ip = InsiderCenterFormat.getIpAddr(getRequest());
                    new MessageTool().split(helpMessage(new String[]{out_trade_no, total_fee,ip}, "C0084"));
                    logger.info("<alipayNotify> 异步回调校验支付都处理成功，更新订单 out_trade_no=>"+out_trade_no);
                    isFlag = true;
                } catch (Exception e) {
                    logger.error("<alipayNotify> updae deposit fail, payId=" + out_trade_no+" , errorInfo="+e.getMessage(), e);
                }
            } else {
                isFlag = true;
                logger.info("<alipayNotify> trade_status error， trade_status=" + trade_status + ", payId=" + out_trade_no);
            }
        }else{
            logger.info("<alipayNotify> verify fail, params=>" + params.toString());
        }
        String returnMsg = "";
        if(isFlag){
            returnMsg = "success";
        }else{
            returnMsg = "fail";
        }
        out.println(returnMsg);//回应信息
        return null;
    }

    */
/**
     * 充值申请提交支付宝完成以后,支付宝同步通知
     *
     * @return
     * @throws Exception
     *//*

    public String aliPayReturn()throws Exception{
        getRequest().setCharacterEncoding("UTF-8");
        getResponse().setContentType("text/html;charset=UTF-8");
        logger.info("<alipayReturn> 同步通知回调开始");
        String sign = getRequest().getParameter("sign");
        String notify_type = getRequest().getParameter("notify_type");
        String trade_no = getRequest().getParameter("trade_no");
        String payment_type = getRequest().getParameter("payment_type");
        String trade_status = getRequest().getParameter("trade_status");
        String subject = getRequest().getParameter("subject");
        String body = getRequest().getParameter("body");
        String out_trade_no = getRequest().getParameter("out_trade_no");
        String total_fee = getRequest().getParameter("total_fee");
        String notifyId = getRequest().getParameter("notify_id");
        logger.info("<alipayReturn> sign =>"+sign+" , notify_id=>"+notifyId+" , trade_status=>"+trade_status+" , notify_type=>"+notify_type+" , trade_no=>"+trade_no+
                " out_trade_no=>"+out_trade_no+" , total_fee=>"+total_fee+" , subject=>"+subject+" , body=>"+body+" , payment_type=>"+payment_type);
        Map params = getPamateMap(getRequest());
        boolean flag = false;
        if (AlipayNotify.verify(params)) {//验证成功
            if ("TRADE_SUCCESS".equalsIgnoreCase(trade_status) || "TRADE_FINISHED".equalsIgnoreCase(trade_status)) {
                flag = true;
            }else{
                logger.info("<alipayReturn> trade_status error, trade_status=" + trade_status + ", payId=" + out_trade_no);
            }
        }else{
            logger.info("<alipayReturn> verify fail, params=>" + params.toString());
        }
        getRequest().setAttribute("flag",flag);
        getRequest().setAttribute("money",total_fee);
        getRequest().setAttribute("rechargeTime",DateUtil.parseToDefaultDateTimeString(new Date()));
        return SUCCESS;
    }

    */
/**
     * 支付宝扫码支付异步回调
     * @return
     * @throws Exception
     *//*

    public String aliPaySmNotify()throws Exception{
        getRequest().setCharacterEncoding("UTF-8");
        getResponse().setContentType("text/html;charset=UTF-8");
        PrintWriter out = getResponse().getWriter();
        String payId = getRequest().getParameter("out_trade_no");       //订单编号
        String trade_no= getRequest().getParameter("trade_no");         //支付宝交易号
        String trade_status =getRequest().getParameter("trade_status"); //支付结果
        String total_fee = getRequest().getParameter("total_fee");      //充值金额
        logger.info("<alipaySmNotify> 异步请求回调 payId="+payId+" , trade_no="+trade_no+" , trade_status="+trade_status+" , total_fee="+total_fee);
        Map params = getPamateMap(getRequest());
        boolean isFlag = false;
        if (AlipayNotify.verify(params)) {//验证成功
            if(("TRADE_SUCCESS".equalsIgnoreCase(trade_status) || "TRADE_FINISHED".equalsIgnoreCase(trade_status)) && !StringUtil.isEmpty(payId)){
                try {
                    new MessageTool().split(helpMessage(new String[] { payId, total_fee,InsiderCenterFormat.getIpAddr(getRequest())},"C0084"));
                    logger.info("<alipaySmNotify> 异步回调校验支付都处理成功，更新订单 payId=>"+payId);
                    isFlag = true;
                } catch (Exception e) {
                    logger.error("<alipaySmNotify> updae deposit fail, payId=>" + payId+" , errorInfo="+e.getMessage(), e);
                }
            }else{
                isFlag = true;
                logger.info("<alipaySmNotify> trade_status error，trade_status=>" + trade_status + ", payId=>" + payId);
            }
        }else{ // 回传参数校验失败
            logger.info("<alipaySmNotify> 参数校验失败, params=>"+params.toString());
        }
        String returnMsg = "";
        if(isFlag){
            returnMsg = "success";
        }else{
            returnMsg = "fail";
        }
        out.println(returnMsg);//回应信息
        return null;
    }

    */
/**
     * 支付宝扫码支付页面通知回调
     * @return
     * @throws Exception
     *//*

    public String aliPaySmReturn()throws Exception{
        getRequest().setCharacterEncoding("UTF-8");
        getResponse().setContentType("text/html;charset=UTF-8");
        String payId = getRequest().getParameter("out_trade_no");       //订单编号
        String trade_status =getRequest().getParameter("trade_status"); //支付结果
        String total_fee = getRequest().getParameter("total_fee");      //充值金额
        logger.info("<alipaySmReturn> 同步请求回调 payId="+payId+" , trade_status="+trade_status);
        Map params = getPamateMap(getRequest());
        boolean flag = false;
        if (AlipayNotify.verify(params)) {//验证成功
            if(("TRADE_SUCCESS".equalsIgnoreCase(trade_status) || "TRADE_FINISHED".equalsIgnoreCase(trade_status)) && !StringUtil.isEmpty(payId)){
                flag = true;
            }else{
                logger.info("<alipaySmReturn> trade_status error，trade_status=>" + trade_status + ", payId=>" + payId);
            }
        }else{ // 回传参数校验失败
            logger.info("<alipaySmReturn> 参数校验失败, params=>"+params.toString());
        }
        getRequest().setAttribute("flag",flag);
        getRequest().setAttribute("money",total_fee);
        getRequest().setAttribute("rechargeTime",DateUtil.parseToDefaultDateTimeString(new Date()));
        return  SUCCESS;
    }

    public Map getPamateMap(HttpServletRequest request){
        Map params = new HashMap();
        //获得POST 过来参数设置到新的params中
        Map requestParams = request.getParameterMap();
        Iterator iterator = requestParams.keySet().iterator();
        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            if(name.equals("action")){
                continue;
            }
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++)
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            if (name.equals("body") || name.equals("subject"))
                valueStr = "浙彩网充值";
            params.put(name, valueStr);
        }
        return params;
    }

    public String buildPamaters(HttpServletRequest request) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            value = value == null ? "" : value;
            sb.append(name).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    */
/**
     * 充值银行信息
     * @param bankCode 银行编号
     * @return string[] 支付方式 支付银行 支付code
     *
     *//*

    public String[] getChargeInfo(int bankCode){
        String bankType = "";
        String bankName = "";
        String inType = CapitalInOutConstants.ZFB_IN;
        //支付宝银行代号
        switch (bankCode) {
            case 6711:
                bankType = "BOC" + "B2C";
                bankName = "中国银行";
                break;
            case 6001:
                bankType = "ICBC" + "B2C";
                bankName = "工商银行";
                break;
            case 6002:
                bankType = "ICBC" + "B2C";
                bankName = "工商银行(信用卡)";
                break;
            case 6005:
                bankType = "CMB";
                bankName = "招商银行";
                break;
            case 6006:
                bankType = "CMB";
                bankName = "招商银行(信用卡)";
                break;
            case 6003:
                bankType = "CCB";
                bankName = "建设银行";
                break;
            case 6004:
                bankType = "CCB";
                bankName = "建设银行(信用卡)";
                break;
            case 6007:
                bankType = "ABC";
                bankName = "农业银行";
                break;
            case 6008:
                bankType = "ABC";
                bankName = "农业银行(信用卡)";
                break;
            case 6716:
                bankType = "SPDB";
                bankName = "浦发银行";
                break;
            case 6719:
                bankType = "CIB";
                bankName = "兴业银行";
                break;
            case 6717:
                bankType = "GDB";
                bankName = "广发银行";
                break;
            case 6718:
                bankType = "SDB";
                bankName = "深发银行";
                break;
            case 6713:
                bankType = "CMBC";
                bankName = "民生银行";
                break;
            case 6701:
                bankType = "COMM";
                bankName = "交通银行";
                break;
            case 6702:
                bankType = "COMM";
                bankName = "交通银行(信用卡)";
                break;
            case 6725:
                bankType="CITIC";
                bankName="中信银行";
                break;
            case 6714:
                bankType = "CEBBANK";            //CEB
                bankName = "光大银行";
                break;
            case 6724:
                bankType="SPABANK";
                bankName="平安银行";
                break;
            case 6712:
                bankType = "POSTGC";             //PSBC
                bankName = "邮政储蓄";
                break;
            case 6720:
                bankType = "NBBANK";             //NBCB
                bankName = "宁波银行";
                break;
            case 6721:
                bankType = "HZCBB2C";            //HCCB
                bankName = "杭州银行";
                break;
            case 6722:
                bankType = "BJBANK";             //BCCB
                bankName = "北京银行";
                break;
            case 6723:
                bankType="SHBANK";
                bankName="上海银行";
                break;
            case 6666:
                bankType="ZFB";
                bankName = "余额";
                break;
            case 6669:
                bankType="ZFBSM";
                bankName = "扫码支付";
                break;
        }
        String[] binkInfo = new String[3];
        binkInfo[0] = inType;
        binkInfo[1] = bankName;
        binkInfo[2] = bankType;
        return binkInfo;
    }
}
*/
