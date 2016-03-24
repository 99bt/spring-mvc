package com.yaowang.util;

import org.apache.commons.dbcp.BasicDataSource;

public class YwBasicDataSource extends BasicDataSource{
	
	@Override
	public synchronized void setUrl(String url) {
		try {
			url = EncryptionKeyUtil.decryption(url);
		} catch (Exception e) {
			//解析失败直接使用
		}
		super.setUrl(url);
	}
	
	@Override
	public void setUsername(String username) {
		try {
			username = EncryptionKeyUtil.decryption(username);
		} catch (Exception e) {
			//解析失败直接使用
		}
		super.setUsername(username);
	}
	
	@Override
	public void setPassword(String password) {
		try {
			password = EncryptionKeyUtil.decryption(password);
		} catch (Exception e) {
			//解析失败直接使用
		}
		super.setPassword(password);
	}
}
