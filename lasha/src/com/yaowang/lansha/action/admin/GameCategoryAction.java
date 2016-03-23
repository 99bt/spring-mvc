package com.yaowang.lansha.action.admin;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.YwGameType;
import com.yaowang.lansha.service.YwGameTypeService;

public class GameCategoryAction extends BasePageAction {

	/**
	 * [2015-12-4上午11:22:33]zlb
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwGameTypeService ywGameTypeService;
	
	private YwGameType entity;
	
	private YwGameType ywGameType;
	
	/**
	 * 列表
	 * @author zlb
	 * @creationDate. 2015-12-4 下午2:12:21 
	 * return String
	 */
	public String list(){
		list = ywGameTypeService.getYwGameTypeList(entity, getPageDto());
		return SUCCESS;
	}
	
	/**
	 * 新增和修改
	 * @author zlb
	 * @creationDate. 2015-12-4 下午3:02:49 
	 * return String
	 */
	public String detail(){
		if(StringUtils.isBlank(id)){
			return SUCCESS;
		}else{
			ywGameType = ywGameTypeService.getYwGameTypeById(id);
			return SUCCESS;
		}
	}
	
	/**
	 * 新增和修改
	 * @return
	 * @creationDate. 2015-12-4 下午3:31:19
	 */
	public String save(){
		if(ywGameType != null){
			if(StringUtils.isNotBlank(ywGameType.getId())){
				ywGameTypeService.update(ywGameType);
				addActionMessage("修改成功");
			}else{
				ywGameType.setCreateTime(getNow());
				ywGameTypeService.save(ywGameType);
				addActionMessage("保存成功");
			}
		}else{
			addActionError("操作失败");
		}
		list();
		return SUCCESS;
	}
	
	/**
	 * 类别删除
	 * @return
	 * @creationDate. 2015-12-4 下午3:31:41
	 */
	public String delete(){
		ywGameTypeService.updateStatus(ids, 0);
		addActionMessage("删除成功");
		list();
		return SUCCESS;
	}
	
	public YwGameType getEntity() {
		return entity;
	}

	public void setEntity(YwGameType entity) {
		this.entity = entity;
	}

	public YwGameType getYwGameType() {
		return ywGameType;
	}

	public void setYwGameType(YwGameType ywGameType) {
		this.ywGameType = ywGameType;
	}
}
