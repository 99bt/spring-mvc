package com.yaowang.lansha.action.admin;

import javax.annotation.Resource;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.LogUserLogin;
import com.yaowang.lansha.service.LogUserLoginService;

public class LogUserLoginAction extends BasePageAction {
	
	private static final long serialVersionUID = 1L;
	@Resource
	private LogUserLoginService logUserLoginService;
	private String userName;

	/**
	 * 前台登录日志
	 * @return
	 */
	public String userLog(){
		if (startTime == null) {
			setStartTime(getNow());
		}
		if (endTime == null) {
			setEndTime(getNow());
		}
		LogUserLogin user = new LogUserLogin();
		user.setUserName(userName);
		list = logUserLoginService.getLogUserLoginPage(user, startTime, endTime, getPageDto());
		return SUCCESS;
	}

	/***
	 * 前台登录日志删除
	 * @return
	 */
	public String userDelete(){
		logUserLoginService.delete(ids);
		userLog();
		addActionMessage("删除成功");
		return SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
