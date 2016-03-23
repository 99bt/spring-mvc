package com.yaowang.lansha.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天信息
 * @Description: TODO
 * @author wangjs
 * @date 2016-3-15
 * @version V1.0
 */
public class LanshaChatInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userType;//用户类型
	private String userName;//昵称
	private String content;//内容
	private Date createtime;//创建时间
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	
	
	
	
	
}
