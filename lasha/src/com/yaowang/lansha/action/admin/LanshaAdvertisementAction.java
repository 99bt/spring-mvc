package com.yaowang.lansha.action.admin;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.LanshaAdvertisement;
import com.yaowang.lansha.service.LanshaAdvertisementService;
import com.yaowang.util.UUIDUtils;

/**
 *  蓝鲨广告
 * @author Administrator
 * zengxi
 */
public class LanshaAdvertisementAction extends BasePageAction{
	private static final long serialVersionUID = 1L;
	
	private LanshaAdvertisement entity;
	
	@Resource
	private LanshaAdvertisementService lanshaAdvertisementService;
	
	//查询分页
	public String list(){
		LanshaAdvertisement lanshaAdvertisement = new LanshaAdvertisement();
		
		if(name !=null){
			lanshaAdvertisement.setName(name);
		}
		
		list = lanshaAdvertisementService.getLanshaAdvertisementPage(lanshaAdvertisement, getPageDto());
		return SUCCESS;
	}
	
	
	//跳转止新增和修改
	public String detail(){
		if(StringUtils.isBlank(id)){
			return SUCCESS;
		}else{
			entity = lanshaAdvertisementService.getLanshaAdvertisementById(id);
			return SUCCESS;
		}
	}
	
	//新增和修改
	public String save(){
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getId())){
				try{
					lanshaAdvertisementService.saveImgFile(entity, getUploadPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(lanshaAdvertisementService.update(entity)<0){
					addActionMessage("修改失败");
				}else{
					addActionMessage("修改成功");
				}
			}else{
				entity.setCreateTime(getNow());
				entity.setId(UUIDUtils.newId());
				try{
					lanshaAdvertisementService.saveImgFile(entity, getUploadPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(lanshaAdvertisementService.save(entity)==null){
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
		if(lanshaAdvertisementService.delete(ids)<0){
			addActionMessage("删除失败");
		}else{
			addActionMessage("删除成功");
		}
		
		//刷新页面
		list();
		return SUCCESS;
	}

	
	

	public LanshaAdvertisement getEntity() {
		return entity;
	}

	public void setEntity(LanshaAdvertisement entity) {
		this.entity = entity;
	}
	
	

}
