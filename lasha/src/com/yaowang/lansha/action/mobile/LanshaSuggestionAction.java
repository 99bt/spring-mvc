package com.yaowang.lansha.action.mobile;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.entity.LanshaSuggestion;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.LanshaSuggestionService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.UUIDUtils;

/**
 * 意见反馈
 * @author Administrator
 */
public class LanshaSuggestionAction extends LanshaMobileAction{
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwUserService ywUserService;
	
	@Resource
	private LanshaSuggestionService lanshaSuggestionService;
	
	private String token;//令牌
	private String note;//意见内容
	
	//增加见意
	public void save() throws IOException{
		if(StringUtils.isEmpty(note)){//意见内容不能为空
			write(getFailed("意见内容不能为空"));
			return ;
		}else if (note.length() > 200) {
			write(getFailed("意见内容字数不能超过200个字"));
			return;
		}
		
		YwUser ywUser = ywUserService.getYwusersByToken(token);
		
		LanshaSuggestion lanshaSuggestion = new LanshaSuggestion();
		lanshaSuggestion.setId(UUIDUtils.newId());
		lanshaSuggestion.setContent(note);
		lanshaSuggestion.setIp(getClientIP());
		lanshaSuggestion.setType("4");// 类型:手机APP
		if(ywUser != null){
			lanshaSuggestion.setUserId(ywUser.getId());
		}
		lanshaSuggestion.setCreateTime(getNow());
		
		if(lanshaSuggestionService.save(lanshaSuggestion)==null){
			write(getFailed("新增失败"));
		}else{
			write(EMPTY_ENTITY);
		}
	}

	public void setToken(String token) {
		this.token = token;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
