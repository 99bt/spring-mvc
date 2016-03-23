package com.yaowang.lansha.util.wx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class HttpRequestUtil {
	
	/**
	 * 获得请求地址，参数经过URLEncoder处理，避免产生乱码以及转义字符
	 * @author ZHY
	 * @creationDate. 2016-01-25 上午11:19:27 
	 * @param serverURL 服务URL
	 * @param inParams 请求输入参数集合
	 * @param enc 编码，用于参数URL编码
	 * @return 请求地址
	 * @throws Exception
	 */
	public static String getRequestURL(String serverURL, Map<String, String> inParams, String enc) throws Exception {
		if (StringUtils.isEmpty(serverURL)) return null;
        if (inParams != null && inParams.size() > 0) {
        	StringBuilder params = new StringBuilder();
        	Set<Entry<String, String>> entrys = inParams.entrySet();
			for (Entry<String, String> param : entrys) {
				params.append(param.getKey()).append("=")
						.append((param.getValue() != null) ? URLEncoder.encode(
								param.getValue(), enc) : "").append("&");
			}

            if (params.length() > 0) {
                params = params.deleteCharAt(params.length() - 1);
                serverURL += ((serverURL.indexOf("?") == -1) ? "?" : "&") + params.toString();
            }
        }
        return serverURL;
	}
	
	
	public static String doGet(String requestURL, String enc) throws Exception {
		if (StringUtils.isEmpty(requestURL))
			return null;
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		BufferedReader reader = null;

		try {
			URL url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(200000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(200000);// 设置读取数据超时时间，单位毫秒
			inputStream = connection.getInputStream();
			if (inputStream != null) {
				String line = ""; // 行字符串
				StringBuilder result = new StringBuilder();
				reader = new BufferedReader(new InputStreamReader(inputStream,
						enc));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				return result.toString();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 释放资源
			if (reader != null) {
				reader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
}
