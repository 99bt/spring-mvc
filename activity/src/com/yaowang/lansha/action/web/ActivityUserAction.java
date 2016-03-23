package com.yaowang.lansha.action.web;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.ActivityUser;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.ActivityUserService;
import com.yaowang.util.RegularUtil;

/**
 * @ClassName: ActivityUserAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tandingbo
 * @date 2016年1月8日 下午2:36:13
 * 
 */
public class ActivityUserAction extends LanshaBaseAction {
	private static final long serialVersionUID = -6717365975687266729L;

	@Resource
	private ActivityUserService activityUserService;

	private ActivityUser entity;

	/**
	 * @Description: 用户收货信息
	 * @return
	 */
	public String info() {
		YwUser ywUser = getUserLogin();
		if (ywUser == null) {
			entity = new ActivityUser();
			return SUCCESS;
		}
		entity = activityUserService.getActivityUserById(ywUser.getId());
		return SUCCESS;
	}

	/**
	 * @throws IOException
	 * @Description: 保存
	 */
	public void save() throws IOException {
		if(null == entity){
			write(getFailed("保存失败"));
			return;
		}
		if (StringUtils.isBlank(entity.getRealname())) {
			write(getFailed("收件人姓名不能为空"));
			return;
		}
		if (StringUtils.isBlank(entity.getAddress())) {
			write(getFailed("收件人地址不能为空"));
			return;
		}
		if (StringUtils.isBlank(entity.getTelphone())) {
			write(getFailed("收件人联系方式不能为空"));
			return;
		}
		if(!RegularUtil.telphoneReg(entity.getTelphone())){
			write(getFailed("请输入正确的手机号"));
			return;
		}
//		if (StringUtils.isBlank(entity.getQq())) {
//			write(getFailed("QQ号码不能为空"));
//			return;
//		}
//		if(entity.getQq().length()>11){
//			write(getFailed("QQ号必须在11位内"));
//			return ;
//		}

		YwUser ywUser = getUserLogin();
		if(ywUser == null){
			write(getFailed("请重新登录"));
			return;
		}
		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(ywUser.getId());
			entity.setLimitTime(0);
			entity.setCreateTime(getNow());
			try {
				activityUserService.save(entity, null);
			} catch (Exception e) {
				write(getFailed("保存失败"));
				return;
			}
		} else {
			try {
				activityUserService.updateUserInfo(entity);
			} catch (Exception e) {
				write(getFailed("保存失败"));
				return;
			}
		}

		JSONObject object = new JSONObject();
		object.put("url", getContextPath() + "/user/record.html");
		writeSuccess(object);
	}

	public ActivityUser getEntity() {
		return entity;
	}

	public void setEntity(ActivityUser entity) {
		this.entity = entity;
	}

}
