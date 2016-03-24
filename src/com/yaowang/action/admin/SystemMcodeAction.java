package com.yaowang.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.entity.SysMcodeDetail;
import com.yaowang.entity.SysMcodeList;
import com.yaowang.service.SysMcodeDetailService;
import com.yaowang.service.SysMcodeListService;
/**
 * 微代码
 * @author shenl
 *
 */
public class SystemMcodeAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private SysMcodeListService sysMcodeListService;
	@Resource
	private SysMcodeDetailService sysMcodeDetailService;
	
	private SysMcodeDetail entity;
	private List<SysMcodeDetail> details;

	/**
	 * 列表
	 * @return
	 */
	public String list(){
		//类型
		list = sysMcodeListService.getSysMcodeListList();
		if (StringUtils.isBlank(id) && list.size()>0) {
			id = ((SysMcodeList)list.get(0)).getThisId();
		}
		if (StringUtils.isNotBlank(id)) {
			details = sysMcodeDetailService.getSysMcodeDetailList(id, null);
		}
		return SUCCESS;
	}
	/**
	 * 详细信息
	 * @return
	 */
	public String detail(){
		if (entity != null) {
			entity = sysMcodeDetailService.getSysMcodeDetailById(entity.getId());
		}else {
			entity = new SysMcodeDetail();
		}
		return SUCCESS;
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		if (StringUtils.isEmpty(entity.getId())) {
			entity.setListId(id);
			sysMcodeDetailService.save(entity);
		}else {
			sysMcodeDetailService.update(entity);
		}
		addActionMessage("保存成功！");
		return SUCCESS;
	}

	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		//删除微代码
		sysMcodeDetailService.delete(ids);
		//类型
		list = sysMcodeListService.getSysMcodeListList();
		if (StringUtils.isNotBlank(id)) {
			details = sysMcodeDetailService.getSysMcodeDetailList(id, null);
		}
		addActionMessage("删除成功！");
		return SUCCESS;
	}
	
	public List<SysMcodeDetail> getDetails() {
		return details;
	}
	public void setDetails(List<SysMcodeDetail> details) {
		this.details = details;
	}
	public SysMcodeDetail getEntity() {
		return entity;
	}
	public void setEntity(SysMcodeDetail entity) {
		this.entity = entity;
	}
}
