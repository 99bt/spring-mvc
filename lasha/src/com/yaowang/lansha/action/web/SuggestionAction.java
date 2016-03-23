package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.entity.SysMcodeDetail;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.LanshaSuggestion;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.LanshaSuggestionService;
import com.yaowang.util.UUIDUtils;

/**
 * @ClassName: SuggestionAction
 * @Description: 意见反馈
 * @author tandingbo
 * @date 2016年1月15日 上午11:31:48
 * 
 */
public class SuggestionAction extends LanshaBaseAction {
	private static final long serialVersionUID = 5226033311889833239L;

	@Resource
	private LanshaSuggestionService lanshaSuggestionService;

	public LanshaSuggestion suggestion;

	/**
	 * @Description: 意见反馈页面
	 * @return
	 */
	public String suggestion() {
		return SUCCESS;
	}

	/**
	 * @Description: 保存意见反馈信息
	 * @return
	 * @throws IOException
	 */
	public void doSuggestion() throws IOException {
		if (suggestion == null || StringUtils.isBlank(suggestion.getType())) {
			write(getFailed("意见反馈类型不能为空"));
			return;
		}
		
		if(StringUtils.isNotBlank(suggestion.getTitle())){
			if(suggestion.getTitle().length() > 100){
				write(getFailed("意见标题字数不能超过100个字"));
				return;
			}
		}
		
		if (StringUtils.isEmpty(suggestion.getContent())) {
			write(getFailed("意见内容不能为空"));
			return;
		} else if (suggestion.getContent().length() > 200) {
			write(getFailed("意见内容字数不能超过200个字"));
			return;
		}

		suggestion.setId(UUIDUtils.newId());
		suggestion.setIp(getClientIP());
		suggestion.setCreateTime(getNow());

		YwUser ywUser = getUserLogin();
		if (ywUser != null) {
			suggestion.setUserId(ywUser.getId());
		}

		try {
			lanshaSuggestionService.save(suggestion);
			write(EMPTY_ENTITY);
		} catch (Exception e) {
			write(getFailed("操作失败"));
		}
	}
	
	/**
	 * @Description: 意见反馈类型
	 * @return
	 */
	public List<SysMcodeDetail> getListSuggestionType(){
		return sysMcodeDetailService.getSysMcodeDetailList("LANSHA_SUGGESTION");
	}

	public LanshaSuggestion getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(LanshaSuggestion suggestion) {
		this.suggestion = suggestion;
	}
}
