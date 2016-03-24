package com.yaowang.action.admin;

import java.io.IOException;

import javax.annotation.Resource;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.entity.LogEmail;
import com.yaowang.entity.LogMt;
import com.yaowang.entity.LogSystem;
import com.yaowang.service.LogAdminLoginService;
import com.yaowang.service.LogEmailService;
import com.yaowang.service.LogMtService;
import com.yaowang.service.LogSystemService;
import com.yaowang.util.email.EmailUtil;
/**
 * 系统日志
 * @author shenl
 *
 */
public class LogAction extends BasePageAction{
	private static final long serialVersionUID = 1L;
	
	private LogSystem entity;
	private LogSystem log;
	private String userName;
	private String ip;

	@Resource
	private LogSystemService logSystemService;
	@Resource
	private LogAdminLoginService logAdminLoginService;
	@Resource
	private LogMtService logMtService;
	@Resource
	private LogEmailService logEmailService;
	
	/**
	 * 系统日志
	 * @return
	 */
	public String log(){
		if (startTime == null) {
			setStartTime(getNow());
		}
		if (endTime == null) {
			setEndTime(getNow());
		}
		list = logSystemService.getLogSystemPage(entity, startTime, endTime, getPageDto());
		return SUCCESS;
	}
	/**
	 * 查看
	 * @return
	 */
	public String view(){
		log = logSystemService.getLogSystemById(id);
		return SUCCESS;
	}
	
	/**
	 * 删除日志
	 * @return
	 */
	public String delete(){
		logSystemService.delete(ids);
		list = logSystemService.getLogSystemPage(entity, getStartTime(), getEndTime(), getPageDto());
		addActionMessage("删除成功");
		return SUCCESS;
	}
	
	/**
	 * 后台登录日志
	 * @return
	 */
	public String adminLog(){
		list = logAdminLoginService.getLogAdminLoginPage(userName, getStartTime(), getEndTime(), getPageDto());
		return SUCCESS;
	}
	
	/***
	 * 删除
	 * @return
	 */
	public String adminDelete(){
		logAdminLoginService.delete(ids);
		list = logAdminLoginService.getLogAdminLoginPage(userName, getStartTime(), getEndTime(), getPageDto());
		addActionMessage("删除成功");
		return SUCCESS;
	}
	/**
	 * 短信日志
	 * @return
	 */
	public String mtLog(){
		LogMt mt = new LogMt();
		mt.setTelphone(userName);
		mt.setIp(ip);
		
		list = logMtService.getLogMtPage(mt, getStartTime(), getEndTime(), getPageDto());
		return SUCCESS;
	}
	/**
	 * 邮件日志
	 * @return
	 */
	public String emailLog(){
		list = logEmailService.getLogEmailPage(getStartTime(), getEndTime(), getPageDto());
		return SUCCESS;
	}
	
	/**
	 * 发送邮箱
	 * @throws IOException 
	 */
	public void sendEmail() throws IOException{
		LogEmail log = logEmailService.getLogEmailById(Integer.valueOf(id));
		try {
			EmailUtil.sendEmail(log.getTitle(), log.getContent(), log.getEmail().split(","));
			write("success");
		} catch (Exception e) {
			write(e.getMessage());
		}
	}
	
	public LogSystem getEntity() {
		return entity;
	}

	public void setEntity(LogSystem entity) {
		this.entity = entity;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public LogSystem getLog() {
		return log;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
