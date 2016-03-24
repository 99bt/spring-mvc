/**
 * 
 */
package com.yaowang.util.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * @author shenl
 *
 */
public class HttpUtil {
	
	/** 发送GET请求并获取服务器端返回值 
	 * @throws  
	 * @throws Exception
	 */
	public static String handleGet(String strUrl, Integer timeOut) throws ConnectTimeoutException, Exception {
		String result = null;
		//实例化get请求
		HttpGet request = new HttpGet(strUrl);
		//实例化客户端
		DefaultHttpClient client = new DefaultHttpClient();
		if (timeOut != null) {
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
		}
		//执行该请求,得到服务器端的响应内容
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			//把响应结果转成String
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		return result;
	}
	
	public static String handleGet(String strUrl) throws Exception {
		return handleGet(strUrl, null);
	}
	
	/** 携带一个params数据发送Post请求到指Url */
	public static String handlePost(String strUrl, List<NameValuePair> params) throws ConnectTimeoutException, Exception {
		String result = null;
		HttpPost request = new HttpPost(strUrl);
		if (params != null && params.size() != 0){
			request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		}
		//实例化客户端
		DefaultHttpClient client = new DefaultHttpClient();
		//执行该请求,得到服务器端的响应内容
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			//把响应结果转成String
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		return result;
	}
	/**
	 * 判断字符串非空
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s){
		if (s == null || "".equals(s)){
			return false;
		}
		return true;
	}
	/**
	 * 判断字符串为空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		if (s == null || "".equals(s)){
			return true;
		}
		return false;
	}
	/**
	 * 按字节截取
	 * @param str
	 * @param len
	 * @return
	 */
	public static String cutOff(String str , int len){
		if (str.getBytes().length < len){
			return str;
		}
		char[] charArr = str.toCharArray();
		int charLength = charArr.length;  //字符数组
		StringBuilder reStr = new StringBuilder("");  //截取之后的字符串
		int k = 0;         //计算字节数
        for(int i=0; i < charLength && k < len ; i++){
            String s = String.valueOf(charArr[i]);  
            byte[] getBytes = s.getBytes();    
            k += getBytes.length;  
            if(k<=len){      //处理如："a我"，2的情况，只输出"a",而不是"a我"  
                reStr.append(charArr[i]);  
            }                     
        }    
		return reStr.toString()+"...";
	}
	/**
	 * url添加参数
	 * @param params
	 * @param name
	 * @param value
	 * @return
	 */
	public static List<NameValuePair> addParams(List<NameValuePair> params, final String name, final String value){
		params.add(new NameValuePair() {
			
			@Override
			public String getValue() {
				return value;
			}
			
			@Override
			public String getName() {
				return name;
			}
		});
		return params;
	}
	
	/**
	 * @Title: getJsonByHttp
	 * @Description: 获取json对象
	 * @param url http请求
	 * @param params 参数
	 * @param requestType 请求类型：post、get
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getJsonByHttp(String url, Map<String, String> params) throws Exception {
		return getJsonByHttp(url, params, "POST");
	}
	
	public static JSONObject getJsonByHttp(String url, Map<String, String> params, String requestType) throws Exception {
		String response = null;
		if("post".equalsIgnoreCase(requestType)){
			List<NameValuePair> pairParams = new ArrayList<NameValuePair>();
			if(MapUtils.isNotEmpty(params)){
				for(Map.Entry<String, String> entry : params.entrySet()){
					addParams(pairParams, entry.getKey(), entry.getValue());
				}
			}
			
			response = handlePost(url, pairParams);
		}else if("get".equalsIgnoreCase(requestType)){
			StringBuffer sParams = new StringBuffer();
			if(MapUtils.isNotEmpty(params)){
				for(Map.Entry<String, String> entry : params.entrySet()){
					sParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}
				sParams.deleteCharAt(sParams.length()-1);
			}
			if(sParams.length() > 0){
				url += "?"+sParams.toString();
			}
			response = handleGet(url);
		}
		JSONObject object = JSONObject.fromObject(response);
		return object;
	}
	
	public static void main(String[] args) throws Exception {
		JSONObject object = HttpUtil.getJsonByHttp("http://www.penlun.com/index.php/api/dw/os", null, "get");
		System.out.println(object);
	}
	
}
