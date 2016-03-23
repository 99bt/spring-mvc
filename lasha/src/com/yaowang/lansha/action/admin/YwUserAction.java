package com.yaowang.lansha.action.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.util.encryption.MD5;
import com.yaowang.lansha.action.api.RoomStateAction;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.openfire.OpenFireConstant;
import com.yaowang.util.openfire.http.OfficiaUserTool;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;
/**
 * @ClassName: YwUserAction
 * @Description: 会员管理-后台
 * @author wanglp
 * @date 2015-12-7 上午11:47:42
 *
 */
public class YwUserAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private YwUserService ywUserService;
	private YwUser entity;
	private String idInt;
	private String status;
	private String remark;//封号或冻结原因
	private String username;
	private String isBlack;
	
	/**
	 * @Title: list
	 * @Description: 列表
	 * @return
	 */
	public String list(){
		if (startTime == null) {
			setStartTime(getNow());
		}
		if (endTime == null) {
			setEndTime(getNow());
		}
		
		YwUser searchUser = new YwUser();
		if(StringUtils.isNotBlank(idInt)){
			try {
				searchUser.setIdInt(Integer.valueOf(idInt));
			} catch (Exception e) {
				searchUser.setIdInt(-1);
			}
		}
		searchUser.setNickname(name);
		searchUser.setUsername(username);
		list = ywUserService.getYwUserList(searchUser, null, new Integer[]{LanshaConstant.USER_STATUS_NORMAL, LanshaConstant.USER_STATUS_FREEZE, LanshaConstant.USER_STATUS_CLOSE}, getPageDto(), startTime, endTime);
		ywUserService.setGiftStock((List<YwUser>)list);
		//设置头像
		for (YwUser user : (List<YwUser>)list) {
			user.setHeadpic(getUploadFilePath(user.getHeadpic()));
		}
		
		return SUCCESS;
	}
	/**
	 * @Title: info
	 * @Description: 详情
	 * @return
	 */
	public String info(){
		if(entity != null && StringUtils.isNotBlank(entity.getId())){
			entity = ywUserService.getYwUserById(entity.getId());
			ywUserService.setGiftStock(entity);
			//设置头像
			entity.setHeadpic(getUploadFilePath(entity.getHeadpic()));
		}
		return SUCCESS;
	}
	/**
	 * @Title: save
	 * @Description: 保存
	 * @return
	 * @throws Exception 
	 */
	public String save() throws Exception{
		//上传图片处理
		UploadFile[] files = UploadUtils.handleFileUpload();
		for (UploadFile file : files) {
			if (!file.getContentType().matches("image/.+")) {
				addActionError("请上传正确的图片文件");
				//info();
				return SUCCESS;
			}
		}
		
		YwUser existsUser = ywUserService.getYwusersByUsername(entity.getUsername(), false);
		if(existsUser != null && !existsUser.getId().equals(entity.getId())){
			addActionError("用户名已存在");
			//info();
			return SUCCESS;
		}
		
		if(StringUtils.isBlank(entity.getId())){
			entity.setId(UUIDUtils.newId());
			entity.setRegChannel("后台");
			entity.setUserType(LanshaConstant.USER_TYPE_ORDINARY);
			entity.setUserStatus(LanshaConstant.USER_STATUS_NORMAL);
			entity.setPassword(MD5.encrypt(MD5.encrypt(entity.getPassword())));//密码加密
			
			upload(files, entity);
			ywUserService.save(entity);
		}else{
			//密码加密
			if(StringUtils.isNotBlank(entity.getPassword())){
				entity.setPassword(MD5.encrypt(MD5.encrypt(entity.getPassword())));
			}
			
			upload(files, entity);
			ywUserService.updateForBase(entity);
		}
		//info();
		addActionMessage("保存成功");
		
		AsynchronousService.submit(new ObjectCallable() {
			@Override
			public Object run() throws Exception {
				//调用im刷新官方用户缓存
				OfficiaUserTool.clearOfficialUser(OpenFireConstant.IP);
				return null;
			}
		});
		
		return SUCCESS;
	}
	/**
	 * @Title: remove
	 * @Description: 删除
	 * @return
	 */
	public String remove(){
		ywUserService.delete(ids);
		addActionMessage("删除成功");
		list();
		return SUCCESS;
	}
	/**
	 * @throws IOException 
	 * @Title: updateStatus
	 * @Description: 封号或冻结
	 */
	public void updateStatus() throws IOException{
		try {
			Integer count = ywUserService.updateStatus(new String[]{ id }, Integer.valueOf(status), remark);
			if(count!= null && count > 0){
				write("1");
			}else{
				write("0");
			}
		} catch (Exception e) {
			write("0");
		}
	}
	
	/**
	 * 保存图片
	 * 
	 * @param banner
	 * @throws Exception
	 */
	private void upload(UploadFile[] files, YwUser user) throws Exception {
		//头像
		UploadFile file1 = null;
		
		for (UploadFile file : files) {
			if ("headpicImg".equals(file.getFieldName())) {
				file1 = file;
			}
		}
		
		//头像
		String ext;
		String filePath;
		if(file1 != null){
			ext = FileUtil.getExtensionName(file1.getFileName());
			//文件地址 年/月/日/user/user_id/headpic.
			filePath = StorePathUtil.buildPath(user.getId(), "user", "headpic_"+ System.currentTimeMillis() +"." + ext).toString();
			FileSystemUtil.saveFile(file1.getFile(), filePath);
			
//			File file = file1.getFile();
//			//文件地址 年/月/日/user/user_id/headpic_80*80.(80px等比例压缩)
//			ImageUtils.changeSize(file.getAbsolutePath(), 80, 80);
//			filePath = StorePathUtil.buildPath("user", user.getId(), "headpic_80_80." + ext).toString();
//			FileSystemUtil.saveFile(file, filePath.toString());
			
			user.setHeadpic(filePath);
		}
	}
	
	/**
	 * @throws IOException 
	 * @Description: 一键登录
	 */
	public void weblogin() throws IOException{
		if(StringUtils.isBlank(id)){
			getResponse().sendRedirect(getContextPath()+"/login.html");
			return;
		}
		YwUser user = ywUserService.getYwUserById(id);
		if(user == null){
			getResponse().sendRedirect(getContextPath()+"/login.html");
			return;
		}
		setUserLogin(user);
		getResponse().sendRedirect(getContextPath()+"/user/center.html");
	}
	
	/**
	 * @throws IOException 
	 * @Title: updateBlank
	 * @Description: 禁言or解除禁言
	 */
	public void updateBlack() throws IOException{
		int count = ywUserService.updateBlackList(id, isBlack);
		if(count <= 0){
			writeNoLog("0");
			return ;
		}else{
			writeNoLog("1");
			return ;
		}
	}
	
	public void app() throws Exception{
		YwUser user = ywUserService.getYwUserById(id);
		Boolean b = RoomStateAction.appsend(user, "test", "test2", "123");
		if (b) {
			write("推送成功");
		}else {
			write("不能推送");
		}
	}
	
	public YwUser getEntity() {
		return entity;
	}
	public void setEntity(YwUser entity) {
		this.entity = entity;
	}
	public String getIdInt() {
		return idInt;
	}
	public void setIdInt(String idInt) {
		this.idInt = idInt;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setIsBlack(String isBlack) {
		this.isBlack = isBlack;
	}

}
