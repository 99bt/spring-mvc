package com.yaowang.util.sessionmanager;


import java.io.IOException;
import java.io.NotSerializableException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.Session;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.session.StandardSession;

import com.yaowang.util.MD5;

public class SessionManager extends StandardManager {
	//是否已经加载完session到本地tomcat
	private Boolean isLoadSession = false;
	private final Map<String, Session> sessionMap = new HashMap<String, Session>();
	//sessionMap对象的md5码
	private String sessionDataMd5Code = "";
	private String sessionDataPath = null;
	public Integer sleepTime = 3;
	
	public SessionManager(String webAppPath){
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		sessionDataPath = path + "/session.data";
		
		//加载本地session
		Object data = SessionSerializable.loadData(sessionDataPath);
		if (data != null) {
			sessionMap.putAll((Map<String, Session>)data);
		}
		sessionDataMd5Code = MD5.encrypt(sessionMap);
		//启动一个线程，定时保存
		Thread saveThread = new Thread(){
		    
			@Override
			public void run() {
				while (true) {
					try {
						if (sleepTime == null) {
							break;
						}
						Thread.sleep(sleepTime * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					saveData();
				}
			}
		};
		saveThread.setDaemon(true);
		saveThread.start();
		System.out.println("本地session加载完成，总加载了:" + sessionMap.size() + "个session。");
	}

	@Override
	public Session createSession(String sessionId) {
		Session session = super.createSession(sessionId);
		setSessionMaxInactiveInterval(session);
		sessionMap.put(session.getId(), session);
		return session;
	}
	
	@Override
	public void expireSession(String sessionId) {
		super.expireSession(sessionId);
		sessionMap.remove(sessionId);
	}
	
	@Override
	public Session findSession(String id) throws IOException {
		synchronized (isLoadSession) {
			if (!isLoadSession) {
				isLoadSession = true;
				//将所有的session添加到tomcat中
				Set<String> keys = sessionMap.keySet();
				List<String> sessionIds = new ArrayList<String>();
				sessionIds.addAll(keys);

				for (String sessionid : sessionIds) {
					try {
						addSession(sessionid, sessionMap.get(sessionid));
					} catch (IllegalStateException e) {
						sessionMap.remove(sessionid);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
		return super.findSession(id);
	}
	/**
	 * 序列化保存session至本地
	 */
	private void saveData(){
		//利用md5获取对象的code，如果与前一个不一样。说明有新改动，需要重新保存
		try {
			String md5 = MD5.encrypt(sessionMap);
			if (!md5.equals(sessionDataMd5Code)) {
				sessionDataMd5Code = md5;
				SessionSerializable.saveData(sessionMap, sessionDataPath);
			}
		} catch (Exception e) {
			if (e instanceof NotSerializableException) {
				sleepTime = null;
			}
		}
	}
	/**
	 * 添加此session到本地tomcat
	 * @param session
	 */
	private void addSession(String sessionid, Session session) {
		if (session == null) {
			sessionMap.remove(sessionid);
			return;
		}
		if (session.getMaxInactiveInterval() >= 0) { 
            long timeNow = System.currentTimeMillis();
            int timeIdle = (int) ((timeNow - session.getLastAccessedTime()) / 1000L);
            if (timeIdle >= maxInactiveInterval) {
            	sessionMap.remove(sessionid);
    			return;
            }
        }
		//本地tomcat创建一个session
		Session createSession = super.createSession(sessionid);
		setSessionMaxInactiveInterval(createSession);
		sessionMap.put(sessionid, createSession);
		
		//将反射回来的session中的信息反写到创建出来的session中
		if (session instanceof StandardSession && createSession instanceof StandardSession) {
			StandardSession standardSession = (StandardSession)session;
			StandardSession createStandardSession = (StandardSession)createSession;
			
			Enumeration<?> names = standardSession.getAttributeNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				Object o = standardSession.getAttribute(name);
				createStandardSession.setAttribute(name, o);
			}
		}
	}
	/**
	 * 设置session有效期
	 * @param session
	 */
	private static void setSessionMaxInactiveInterval(Session session){
		session.setMaxInactiveInterval(180 * 60);
	}
}
