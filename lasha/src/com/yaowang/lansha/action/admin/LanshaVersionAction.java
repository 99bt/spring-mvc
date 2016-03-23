package com.yaowang.lansha.action.admin;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.LanshaVersion;
import com.yaowang.lansha.service.LanshaVersionService;
import com.yaowang.util.UUIDUtils;

/**
 * app版本
 * @author Administrator
 * zengxi
 */
public class LanshaVersionAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	
	private LanshaVersion entity;
	
	@Resource
	private LanshaVersionService lanshaVersionService;
	
	private String osType;//操作系统
	private String version;//版本号
	
	//查询
	public String list(){
		LanshaVersion lanshaVersion = new LanshaVersion();
		
		if(StringUtils.isNotEmpty(name)){//包名
			lanshaVersion.setName(name);
		}
		
		if(StringUtils.isNotEmpty(osType)){//操作系统
			lanshaVersion.setOsType(osType);
		}
		
		if(StringUtils.isNotEmpty(version)){//操作系统
			lanshaVersion.setVersion(version);
		}
		
		list = lanshaVersionService.getVersionPage(lanshaVersion, null, getPageDto(), startTime, endTime);;
		return SUCCESS;
	}
	
	//跳转止新增和修改
	public String detail(){
		if(StringUtils.isBlank(id)){
			return SUCCESS;
		}else{
			entity = lanshaVersionService.getVersionById(id);
			return SUCCESS;
		}
	}
	
	//新增及修改
	public String  save(){
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getId())){
				if(lanshaVersionService.update(entity)<0){
					addActionMessage("修改失败");
				}else{
					addActionMessage("修改成功");
				}
			}else{
				//entity.setOnlineTime(getNow());
				entity.setId(UUIDUtils.newId());
				if(lanshaVersionService.save(entity)==null){
					addActionMessage("保存失败");
				}else{
					addActionMessage("保存成功");
				}
			}
		}else{
			addActionError("操作失败");
		}
		list();
		return SUCCESS;
	}
	
	//删除
	public String delete(){
		if(lanshaVersionService.delete(ids)<0){
			addActionMessage("删除失败");
		}else{
			addActionMessage("删除成功");
		}
		
		//刷新页面
		list();
		return SUCCESS;
	}

	public LanshaVersion getEntity() {
		return entity;
	}

	public void setEntity(LanshaVersion entity) {
		this.entity = entity;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
