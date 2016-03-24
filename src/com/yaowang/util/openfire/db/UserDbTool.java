package com.yaowang.util.openfire.db;

import com.yaowang.util.openfire.DBUtil;
import com.yaowang.util.spring.ContainerManager;

public class UserDbTool {
	/**
	 * 直接db修改密码
	 * @param username
	 * @param pwd
	 * @param name
	 * @return
	 */
	public static Boolean modifyPwd(String username, String pwd, String npwd, String name){
//		try {
//			Boolean b = modifyPwd(username, pwd, npwd);
//			if (b) {
//				return b;
//			}else {
				DBUtil dbUtil =  (DBUtil)ContainerManager.getComponent("dbUtil");
				int count = dbUtil.updateSql("update ofUser set plainPassword = ?, encryptedPassword = ?, name = ? where username = ?", new Object[]{npwd, null, name, username});
				return count > 0;
//			}
//		} catch (Exception e) {
//			return false;
//		}
	}
}
