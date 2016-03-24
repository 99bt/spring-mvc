package com.yaowang.common.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ProxyHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ProxyHandler.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		System.out
//				.println("-------------------------ProxyHandler-----------------------------------");
		noSecurityRequest(req, resp);
	}

	private void noSecurityRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		logger.info("ProxyHandler noSecurityRequest 请求类型: GET");
		String url0 = "";
		OutputStream out = null;
		HttpURLConnection conn = null;
		try {
			url0 = req.getQueryString();
			if (url0.startsWith("url=")) {
				url0 = url0.substring(4);
			}
			if (url0.indexOf("requestTime") != -1) {
				url0 = url0.split("requestTime")[0];
				url0 = url0.substring(0, url0.length() - 1);
			}

			URL url = new URL(url0);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			out = resp.getOutputStream();
			resp.setContentType("text/xml; charset=UTF-8");

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), HTTP.UTF_8));
			BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(
					resp.getOutputStream(), HTTP.UTF_8));
			String tempLine = rd.readLine();
			while (tempLine != null) {
				wd.write(tempLine);
				logger.info(tempLine);
				tempLine = rd.readLine();
			}
			wd.flush();

			logger.info("\n请求地址: " + url0 + "\n请求成功!");
		} catch (Exception e) {
			logger.error("请求地址: " + url0 + "\n请求失败: " + e);
			resp.sendError(500);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("请求类型: POST");
		Document indoc = null;
		try {
			// 解析客户端发送的请求
			SAXReader builder = new SAXReader();
			indoc = builder.read(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			logger.info("请求信息：\n" + indoc.asXML());
		} catch (Exception ex) {
			logger.error("解析客户端发送的请求错误!\n" + ex);
		}

		String url0 = request.getQueryString();
		OutputStream out = response.getOutputStream();
		try {
			if (url0.startsWith("url=")) {
				url0 = url0.substring(4);
				String urlString = new String(url0.getBytes("ISO-8859-1"),
						"GBK");

				response.setContentType("text/xml");
				response.setCharacterEncoding("UTF-8");
				java.net.URL url = new java.net.URL(urlString);

				BufferedInputStream in = null;
				HttpURLConnection connection = null;
				byte[] bs = null;
				if (url != null) {
					try {
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("POST");
						connection.setRequestProperty("Content-Type",
								"application/xml");
						connection.setDoInput(true);
						connection.setDoOutput(true);
						OutputStream toserver = connection.getOutputStream();
						OutputFormat format = OutputFormat
								.createCompactFormat();
						format.setEncoding("UTF-8");
						XMLWriter writer = new XMLWriter(toserver, format);

						writer.write(indoc);
						toserver.flush();
						toserver.close();
						// 读取响应信息，并将响应发送到客户�?
						in = new BufferedInputStream(
								connection.getInputStream());
						bs = new byte[1024];
						int startpos = 0;
						int num = 0;
						num = in.read(bs, startpos, 1024);
						logger.info("返回信息:");
						while (num != -1) {
							out.write(bs, 0, num);
							logger.info(new String(bs));
							num = in.read(bs, 0, 1024);
						}
						logger.info("\n请求地址: " + url0 + "\n请求成功!");
					} catch (IOException e) {
						logger.error("请求地址: " + url0 + "\n请求失败!" + e);
						response.sendError(500);
					} finally {
						if (in != null) {
							try {
								in.close();
							} catch (Exception ex) {
							}
						}
						if (connection != null) {
							try {
								connection.disconnect();
							} catch (Exception ex) {
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
