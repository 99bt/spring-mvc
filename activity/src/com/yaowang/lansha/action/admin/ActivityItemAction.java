package com.yaowang.lansha.action.admin;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.ActivityItem;
import com.yaowang.lansha.service.ActivityItemService;
import com.yaowang.util.UUIDUtils;

/**
 * @ClassName: ActivityItemAction
 * @Description: TODO(活动管理)
 * @author tandingbo
 * @date 2015年12月24日 上午10:55:47
 * 
 */
public class ActivityItemAction extends LanshaBaseAction {
	private static final long serialVersionUID = 6547227584448648715L;

	@Resource
	private ActivityItemService activityItemService;

	private ActivityItem entity;

	/**
	 * @Description: 活动列表
	 * @return
	 */
	public String list() {
		if (entity == null) {
			entity = new ActivityItem();
		}
		entity.setName(name);
		list = activityItemService.getActivityItemPage(entity, getPageDto());
		return SUCCESS;
	}

	/**
	 * @Description: 详情
	 * @return
	 */
	public String detail() {
		if (StringUtils.isBlank(id)) {
			entity = new ActivityItem();
		} else {
			entity = activityItemService.getActivityItemById(id);
		}
		return SUCCESS;
	}

	/**
	 * @Description: 保存
	 * @return
	 */
	public String save() {
		if (StringUtils.isBlank(entity.getName())) {
			addActionError("活动名称不能为空");
			return ERROR;
		}
		if (entity.getMiddleGift() == null) {
			addActionError("每人奖品价值降低概率值不能为空");
			return ERROR;
		}
		if (entity.getMaxGift() == null) {
			addActionError("每人奖品价值上限不能为空");
			return ERROR;
		}
		Date now = getNow();
		if (entity.getStartTime() == null) {
			entity.setStartTime(now);
		}
		if (StringUtils.isBlank(entity.getId())) {
			// 新增
			entity.setId(UUIDUtils.newId());
			entity.setCreateTime(now);
			try {
				activityItemService.save(entity);
			} catch (Exception e) {
				addActionError("保存失败");
				return ERROR;
			}
		} else {
			// 修改
			try {
				activityItemService.update(entity);
			} catch (Exception e) {
				addActionError("保存失败");
				return ERROR;
			}
		}
		list();
		addActionMessage("保存成功");
		return SUCCESS;
	}

	/**
	 * @Description: 删除
	 * @return
	 */
	public String delete() {
		if (ids.length < 1) {
			addActionMessage("请选择一条删除记录");
			return SUCCESS;
		}
		try {
			activityItemService.delete(ids);
			addActionMessage("删除成功");
		} catch (Exception e) {
			addActionError("删除失败");
		}
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 上线
	 * @return
	 */
	public String online() {
		try {
			updateStatus("1");
			addActionMessage("操作成功");
		} catch (Exception e) {
			addActionMessage("操作失败");
		}
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 下线
	 * @return
	 */
	public String offline() {
		try {
			updateStatus("0");
			addActionMessage("操作成功");
		} catch (Exception e) {
			addActionMessage("操作失败");
		}
		list();
		return SUCCESS;
	}

	private void updateStatus(String status) throws Exception {
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("请选择操作记录");
		}
		activityItemService.updateStatusById(id, status);
	}

	public ActivityItem getEntity() {
		return entity;
	}

	public void setEntity(ActivityItem entity) {
		this.entity = entity;
	}
}
