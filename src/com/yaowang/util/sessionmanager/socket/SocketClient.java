package com.yaowang.util.sessionmanager.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {
	private static Socket server = null;
	private static PrintWriter writer = null;
	private static BufferedReader in = null;
	
	private static SocketClientCheckThread thread;
	
	static{
//		//实时监控
		thread = new SocketClientCheckThread();
		thread.setDaemon(true);
		//启动实时与服务器端连接
//		thread.connection();
//		thread.start();
	}
	
	/**
	 * 连接服务器端
	 * @throws IOException
	 */
	public static Boolean connection() {
		try {
			server = new Socket(InetAddress.getLocalHost(), Integer.valueOf("5678"));
			writer = new PrintWriter(new OutputStreamWriter(server.getOutputStream(), "utf8"), true);
			in = new BufferedReader(new InputStreamReader(server.getInputStream(), "utf8"));
			return true;
		}catch (ConnectException e) {
			//服务器控制台监听未打开
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 输出
	 * 
	 * @param line
	 * @throws IOException
	 */
	public static void print(final String line) {
		thread.connection();
		
		if (writer == null || !thread.getIsConn()) {
			System.out.println(line);
			return;
		}
//		String returnLine = null; 
		try {
//			while (!"success".equals(returnLine)) {
				writer.println(line);
				writer.flush();
//				returnLine = in.readLine();
//			}
		} finally {
			thread.closeConn();
		}
	}
	
	public static void close(){
		//如果服务器端关闭，清理连接
		try {
			if (writer != null) {
				writer.close();
				writer = null;
			}
			if (in != null) {
				in.close();
				in = null;
			}
			if (server != null && !server.isClosed()) {
				server.close();
				server = null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
