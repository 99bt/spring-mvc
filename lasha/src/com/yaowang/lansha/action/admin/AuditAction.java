package com.yaowang.lansha.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.entity.AdminUser;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoomApply;
import com.yaowang.lansha.service.LanshaAddActivityStockService;
import com.yaowang.lansha.service.YwUserRoomApplyService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.service.AdminUserService;

public class AuditAction extends BasePageAction {
	private static final long serialVersionUID = -3871909950986112345L;

	@Resource
	private YwUserRoomApplyService ywUserRoomApplyService;
	
	@Resource
	private AdminUserService adminUserService;
	
	@Resource
	private YwUserService ywUserService;
	
	@Resource
	private LanshaAddActivityStockService lanshaAddActivityStockService;
	
	private YwUserRoomApply entity;
	
	private YwUserRoomApply ywUserRoomApply;
	
	private String status; 
	private String userIdInt;
	
	/**
	 * 申请主播的列表数据
	 * @return
	 * @creationDate. 2015-12-7 上午10:18:52
	 */
	public String list(){
		if(StringUtils.isNotBlank(userIdInt)){
			entity.setUserIdInt(Integer.valueOf(userIdInt));
		}
		list = ywUserRoomApplyService.getYwUserRoomApplyPage(entity, startTime, endTime, getPageDto());
		ywUserRoomApplyService.setName((List<YwUserRoomApply>)list);
		return SUCCESS;
	}
	
	/**
	 * 审核
	 * @return
	 * @creationDate. 2015-12-7 上午11:26:35
	 */
	public String doAudit(){
		YwUserRoomApply userRoomApply = ywUserRoomApplyService.getYwUserRoomApplyById(id);
		if(userRoomApply == null){
			list();
			addActionError("找不到数据");
			return SUCCESS;
		}
		if(LanshaConstant.MASTER_STATUS_VETTED.equals(userRoomApply.getStatus())){
			list();
			addActionError("该用户已是主播");
			return SUCCESS;
		}
		userRoomApply.setAduitUid(getAdminLogin().getId());
		userRoomApply.setAduitTime(getNow());
		userRoomApply.setStatus(status);
		if(ywUserRoomApply != null){
			userRoomApply.setRemark(ywUserRoomApply.getRemark());
		}
		try{
			YwUser user = ywUserService.getYwUserById(userRoomApply.getUserId());
			if(user == null){
				list();
				addActionError("用户找不到");
				return SUCCESS;
			}
			Integer i = ywUserRoomApplyService.updateForAudit(userRoomApply, user);
			//更新成功并且审核通过增加抽奖机会
			if(i > 0 && LanshaConstant.MASTER_STATUS_VETTED.equals(status)){
				//成为主播增加本人和推广人5次抽奖机会
				lanshaAddActivityStockService.addHostStock(user);
			}
			
		}catch(RuntimeException e){
			addActionError(e.getMessage());
			list();
			e.printStackTrace();
			return SUCCESS;
		}
		list();
		addActionMessage("已审核");
		return SUCCESS;
	}
	
	
	/**
	 * 查看详情
	 * @return
	 * @creationDate. 2015-12-7 下午2:00:08
	 */
	public String view(){
		ywUserRoomApply = ywUserRoomApplyService.getYwUserRoomApplyById(id);
		YwUser ywUser = ywUserService.getYwUserById(ywUserRoomApply.getUserId());
		if(ywUser != null){
			ywUserRoomApply.setUsername(ywUser.getUsername());
		}
		AdminUser adminUser = adminUserService.getAdminUserById(ywUserRoomApply.getAduitUid());
		if(adminUser != null){
			ywUserRoomApply.setAduitName(adminUser.getUsername());
		}
		return SUCCESS;
	}
	/**
	 * 查看图片
	 * @throws Exception 
	 */
	public void pic() throws Exception{
		YwUserRoomApply apply = ywUserRoomApplyService.getYwUserRoomApplyById(id);
		if (apply == null) {
			return;
		}
		//输出文件
		apply.writerPic(getResponse().getOutputStream(), name);
	}

	public YwUserRoomApply getEntity() {
		return entity;
	}

	public void setEntity(YwUserRoomApply entity) {
		this.entity = entity;
	}

	public YwUserRoomApply getYwUserRoomApply() {
		return ywUserRoomApply;
	}

	public void setYwUserRoomApply(YwUserRoomApply ywUserRoomApply) {
		this.ywUserRoomApply = ywUserRoomApply;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserIdInt() {
		return userIdInt;
	}

	public void setUserIdInt(String userIdInt) {
		this.userIdInt = userIdInt;
	}
}
