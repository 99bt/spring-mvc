package com.yaowang.lansha.action.admin;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.LanshaGift;
import com.yaowang.lansha.service.LanshaGiftService;
import com.yaowang.util.UUIDUtils;

/**
 * 礼物  
 * zengxi
 * @author Administrator
 */
public class LanshaGiftAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private LanshaGiftService lanshaGiftService;
	
	private LanshaGift entity;
	
	
	//查询分页
	public String list(){
		LanshaGift lanshaGift = new LanshaGift();
		
		if(name !=null){
			lanshaGift.setName(name);
		}
		
		list = lanshaGiftService.getLanshaGiftPage(lanshaGift, getPageDto());
		return SUCCESS;
	}
	
	
	//跳转止新增和修改
	public String detail(){
		if(StringUtils.isBlank(id)){
			return SUCCESS;
		}else{
			entity = lanshaGiftService.getLanshaGiftById(id);
			return SUCCESS;
		}
	}
	
	//新增和修改
	public String save(){
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getId())){
				entity.setCreateTime(getNow());
				try{
					lanshaGiftService.saveImgFile(entity, getUploadPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(lanshaGiftService.update(entity)<0){
					addActionMessage("修改失败");
				}else{
					addActionMessage("修改成功");
				}
			}else{
				entity.setCreateTime(getNow());
				entity.setId(UUIDUtils.newId());
				try{
					lanshaGiftService.saveImgFile(entity, getUploadPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(lanshaGiftService.save(entity)==null){
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
		if(lanshaGiftService.delete(ids)<0){
			addActionMessage("删除失败");
		}else{
			addActionMessage("删除成功");
		}
		
		//刷新页面
		list();
		return SUCCESS;
	}


	public LanshaGift getEntity() {
		return entity;
	}

	public void setEntity(LanshaGift entity) {
		this.entity = entity;
	}

}
