package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomApply;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomApplyService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
/**
 * @ClassName: BecomeAnchorAction
 * @Description: 主播认证
 * @author wanglp
 * @date 2015-12-10 上午11:13:28
 *
 */
public class BecomeAnchorAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomApplyService ywUserRoomApplyService;
	@Resource
	private YwGameService ywGameService;
	@Resource
	private YwUserRoomService ywUserRoomService;
	
	private YwUser user;
	private YwUserRoomApply userApply;

	public String becomeAnchor() throws IOException{
		user = ywUserService.getYwUserById(getUserLogin().getId());
		//设置用户头像
		user.setHeadpic(getUploadFilePath(user.getHeadpic()));
		PageDto page = getPageDto();
		page.setCount(false);
		page.setCurrentPage(1);
		page.setRowNum(1);
		YwUserRoomApply searchApply = new YwUserRoomApply();
		searchApply.setUserId(user.getId());
		List<YwUserRoomApply> list = ywUserRoomApplyService.getYwUserRoomApplyPage(searchApply, null, null, page);
		if(CollectionUtils.isNotEmpty(list)){
			userApply = list.get(0);
			// 审核通过后处理真实姓名和身份证号
			if(LanshaConstant.MASTER_STATUS_VETTED.equals(userApply.getStatus())){				
				//重新设置session用户值 如果审核时间大于登录时间，重新设置用户，并且跳到我的房间
				if(user.getLastLoginTime() != null && DateUtils.compare(user.getLastLoginTime(), userApply.getAduitTime())<0){
					setUserLogin(user);
					getResponse().sendRedirect(getContextPath()+"/user/myroom.html");
				}
				YwUserRoom room = ywUserRoomService.getYwUserRoomByUid(user.getId());
				if (room != null) {
					userApply.setRoomIdInt(room.getIdInt());
				}
				StringBuffer str = new StringBuffer(userApply.getRealname());
				userApply.setRealname(str.replace(0, 1, "*").toString());
				StringBuffer sb = new StringBuffer(userApply.getIdentitycard());
				int s = userApply.getIdentitycard().length();
				sb.replace(2, s-2, "**********");
				userApply.setIdentitycard(sb.toString());
			}
		}
		return SUCCESS;
	}
	public void becomeAnchorSave() throws IOException{
		if(userApply == null){
			write(getFailed("信息错误"));
			return ;
		}
		if(StringUtils.isBlank(userApply.getRealname())){
			write(getFailed("请输入真实的姓名"));
			return ;
		}
		if(StringUtils.isBlank(userApply.getIdentitycard())){
			write(getFailed("请输入18位身份证号码"));
			return ;
		} 
		if(StringUtils.isBlank(userApply.getPic1()) || StringUtils.isBlank(userApply.getPic2()) || StringUtils.isBlank(userApply.getPic3())){
			write(getFailed("请上传身份证正反面及手持身份证照片"));
			return ;
		}
		if(StringUtils.isBlank(userApply.getRoomName())){
			write(getFailed("请填写房间名称"));
			return ;
		}
		// 房间名称重复检查
		YwUserRoom userRoom = new YwUserRoom();
		userRoom.setName(userApply.getRoomName());
		List<YwUserRoom> lsitUserRoom = ywUserRoomService.listYwUserRoomList(userRoom, null);
		if(CollectionUtils.isNotEmpty(lsitUserRoom)){
			write(getFailed("房间名称【"+userApply.getRoomName()+"】被占用，请换重新填写房间名称"));
			return ;
		}
		if(StringUtils.isBlank(userApply.getGameId())){
			write(getFailed("请选择游戏"));
			return ;
		}
		// 设置默认直播间公告信息
		if(StringUtils.isBlank(userApply.getNotice())){
			userApply.setNotice("欢迎来到我的直播间，喜欢记得点关注哟~");
		}
		
		if(StringUtils.isBlank(userApply.getId())){
			//新增
			//判断是否已经申请过
			PageDto page = getPageDto();
			page.setCount(false);
			page.setCurrentPage(1);
			page.setRowNum(1);
			YwUserRoomApply searchApply = new YwUserRoomApply();
			searchApply.setUserId(getUserLogin().getId());
			//searchApply.setStatus(LanshaConstant.MASTER_STATUS_VETTED);
			List<YwUserRoomApply> list = ywUserRoomApplyService.getYwUserRoomApplyPage(searchApply, null, null, page);
			if(CollectionUtils.isNotEmpty(list)){
				write(getFailed("已经有申请信息,不能重复申请"));
				return ;
			}
			userApply.setUserId(getUserLogin().getId());
			userApply.setCreateTime(getNow());
			userApply.setStatus(LanshaConstant.MASTER_STATUS_AUDITING);
			userApply.setUsername(getUserLogin().getUsername());
			userApply.setId(UUIDUtils.newId());
			//上传文件
			try {
				upload(userApply);
			} catch (Exception e) {
				e.printStackTrace();
				write(getFailed("上传照片失败"));
				return ;
			}
			
			ywUserRoomApplyService.save(userApply);
		}else{
			//上传文件
			try {
				upload(userApply);
			} catch (Exception e) {
				e.printStackTrace();
				write(getFailed("上传照片失败"));
				return ;
			}
			//修改
			userApply.setStatus(LanshaConstant.MASTER_STATUS_AUDITING);
			ywUserRoomApplyService.updateForApply(userApply);
		}
		
		//返回
		JSONObject object = new JSONObject();
		object.put("url", getContextPath()+"/user/becomeAnchor.html");
		writeSuccess(object);
	}
	
	/**
	 * @Description: 获取游戏列表
	 * @return
	 */
	public List<YwGame> getListGame() {
		YwGame ywGame = new YwGame();
		ywGame.setStatus(LanshaConstant.STATUS_ONLINE);
		return ywGameService.getYwGamePage(ywGame, null, null, null, null, null);
	}
	
	private void upload(YwUserRoomApply userApply) throws Exception{
		String pic1 = userApply.getPic1();//手持
		String pic2 = userApply.getPic2();//正面
		String pic3 = userApply.getPic3();//反面
		if(StringUtils.isNotBlank(pic1) && isTempPath(pic1)){
			//去掉uploadPath
			pic1 = removeUploadPath(pic1);
			//原图
			String ext = FileUtil.getExtensionName(pic1);
			String destPath = StorePathUtil.buildPath(userApply.getId(), "user_room_apply", UUIDUtils.newId() + "." + ext).toString();
			FileSystemUtil.copyTempFile(pic1, destPath);
			userApply.setPic1(destPath);
			//删除原有文件
			FileSystemUtil.deleteFile(pic1);
		}
		if(StringUtils.isNotBlank(pic2) && isTempPath(pic2)){
			//去掉uploadPath
			pic2 = removeUploadPath(pic2);
			//原图
			String ext = FileUtil.getExtensionName(pic2);
			String destPath = StorePathUtil.buildPath(userApply.getId(), "user_room_apply", UUIDUtils.newId() + "." + ext).toString();
			FileSystemUtil.copyTempFile(pic2, destPath);
			userApply.setPic2(destPath);
			//删除原有文件
			FileSystemUtil.deleteFile(pic2);
		}
		if(StringUtils.isNotBlank(pic3) && isTempPath(pic3)){
			//去掉uploadPath
			pic3 = removeUploadPath(pic3);
			//原图
			String ext = FileUtil.getExtensionName(pic3);
			String destPath = StorePathUtil.buildPath(userApply.getId(), "user_room_apply", UUIDUtils.newId() + "." + ext).toString();
			FileSystemUtil.copyTempFile(pic3, destPath);
			userApply.setPic3(destPath);
			//删除原有文件
			FileSystemUtil.deleteFile(pic3);
		}
	}
	
	 /**
     * 是否临时目录
     * @param path
     * @return
     */
    private Boolean isTempPath(String path) {
		return path != null && path.matches(".*temp/.*");
	}
    
	/**
	 * 图片
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void pic() throws IOException, Exception{
		YwUserRoomApply apply = ywUserRoomApplyService.getYwUserRoomApplyById(id);
		if (apply == null || !getUserLogin().getId().equals(apply.getUserId())) {
			return;
		}
		//输出文件
		apply.writerPic(getResponse().getOutputStream(), name);
	}
	
	private String removeUploadPath(String url){
		String uploadPath = getUploadPath();
		if(url.contains(uploadPath) || url.contains(uploadPath.replace("/", "\\"))){
			int index = url.indexOf(uploadPath);
			if(index < 0){
				index = url.indexOf(uploadPath.replace("/", "\\"));
			}
			url = url.substring(index+uploadPath.length());
		}
		return url;
	}
	
	public YwUserRoomApply getUserApply() {
		return userApply;
	}
	public void setUserApply(YwUserRoomApply userApply) {
		this.userApply = userApply;
	}
	public YwUser getUser() {
		return user;
	}
}
