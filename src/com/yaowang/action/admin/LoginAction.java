package com.yaowang.action.admin;


import java.lang.reflect.Method;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BaseDataAction;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.entity.AdminUser;
import com.yaowang.entity.LogAdminLogin;
import com.yaowang.service.AdminUserService;
import com.yaowang.service.LogAdminLoginService;
import com.yaowang.util.ResourcesLoad;
import com.yaowang.util.spring.ContainerManager;
/**
 * 后台登录
 * @author shenl
 *
 */
public class LoginAction extends BaseDataAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String uid;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 新密码
	 */
	private String newPwd;
	/**
	 * 验证码
	 */
	private String code;
	
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private LogAdminLoginService logAdminLoginService;
	
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		if (getAdminLogin() != null) {
			// 已经登录
			return SUCCESS;
		}else if (StringUtils.isNotEmpty(uid)) {
			// 登录操作
			AdminUser user = adminUserService.getAdminUserByUserName(uid);
			if (user != null) {
//				if (!("ywwl".equals(code) && ResourcesLoad.getDevMode())) {
//					Object[] args = (Object[])getSession().get("mt_code");
//					if (args == null || !code.equals(args[0])) {
//						addActionError("验证码错误");
//						return "login";
//					}
//					Date date = (Date)args[1];
//					if (new Date().getTime() - date.getTime() > 5 * 60 * 1000) {
//						addActionError("验证码超时");
//						return "login";
//					}
//				}
				
				if (StringUtils.isEmpty(user.getPassword()) && StringUtils.isEmpty(pwd)) {
					// 账户密码正确
					login(user);
					return SUCCESS;
				}
				
				if (user.equalsPwd(pwd)) {
					// 账户密码正确
					if ("1".equals(user.getState())) {
						login(user);
						return SUCCESS;
					}else {
						addActionError("您的账户当前状态异常");
						return "login";
					}
				}
			}
			
			addActionError("账户或密码不正确");
		}
		return "login";
	}
	
	/**
	 * 发送验证码
	 * @throws Exception 
	 */
//	public void code() throws Exception{
//		AdminUser user = adminUserService.getAdminUserByUserName(uid);
//		if (user != null) {
//			if (StringUtils.isEmpty(user.getPassword()) && StringUtils.isEmpty(pwd)) {
//				if (StringUtils.isEmpty(user.getTelphone())) {
//					write("当前手机号码未设置");
//					return;
//				}
//				// 账户密码正确
//				sendMt(user);
//				return;
//			}
//			
//			if (user.equalsPwd(pwd)) {
//				// 账户密码正确
//				if ("1".equals(user.getState())) {
//					if (StringUtils.isEmpty(user.getTelphone())) {
//						write("当前手机号码未设置");
//						return;
//					}
//					
//					sendMt(user);
//					return;
//				}else {
//					write("您的账户当前状态异常");
//					return;
//				}
//			}
//		}
//		write("账户或密码错误");
//	}
	
//	private void sendMt(AdminUser user) throws Exception{
//		String code = RandCodeUtil.getRandCode(6).toString();
//		MTUtil.sendMt("【" + getPlatFormName() + "】您的登录验证码是:" + code + "，有效期5分钟", user.getTelphone());
//		getSession().put("mt_code", new Object[]{code, new Date()});
//		write("1");
//	}
	
	/**
	 * 登录操作
	 * @param user
	 */
	private void login(AdminUser user){
		//反射调用
		Map<String, String> dataMap = ResourcesLoad.load("classpath*:/conf/login.properties");
		for (String key : dataMap.keySet()) {
			String data = dataMap.get(key);
			String[] strs = data.split("\\.");
			Object object = ContainerManager.getComponent(strs[0]);
			Class<?> cls = object.getClass();
			try {
				Method method = cls.getMethod(strs[1], AdminUser.class);
				method.invoke(object, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		setAdminLogin(user);
		// 保存最后登录记录
		user.setLoginTime(getNow());
		user.setLoginIp(getClientIP());
		adminUserService.update(user);
		// 登录日志
		LogAdminLogin log = new LogAdminLogin();
		log.setLoginIp(getClientIP());
		log.setLoginTime(getNow());
		log.setUserId(user.getId());
		logAdminLoginService.save(log);
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logout(){
		getSession().remove(BaseConstant.ADMIN_LOGIN);
		getSession().remove(BaseConstant.ADMIN_MODEL);
		return "login";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	public String changePwd(){
		if (StringUtils.isNotBlank(pwd)) {
			AdminUser user = getAdminLogin();
			if (user.equalsPwd(pwd)) {
				user.setEncryptPassword(newPwd);
				adminUserService.updatePwd(user);
				addActionMessage("密码修改成功");
			}else {
				addActionError("原密码错误");
			}
		}
		return SUCCESS;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

