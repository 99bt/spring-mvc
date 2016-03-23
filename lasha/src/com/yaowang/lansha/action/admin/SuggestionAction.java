package com.yaowang.lansha.action.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yaowang.entity.SysMcodeDetail;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.LanshaSuggestion;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.LanshaSuggestionService;
import com.yaowang.lansha.service.YwUserService;

/**
 * @ClassName: SuggestionAction
 * @Description: TODO(意见反馈)
 * @author tandingbo
 * @date 2015年12月21日 上午9:43:24
 * 
 */
public class SuggestionAction extends LanshaBaseAction {
	private static final long serialVersionUID = -2901274191144200049L;

	@Resource
	private LanshaSuggestionService lanshaSuggestionService;
	@Resource
	private YwUserService ywUserService;

	public LanshaSuggestion entity;
	public String title;// 意见标题
	public String type;// 反馈意见类型

	/**
	 * @Description: 列表意见反馈
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		LanshaSuggestion suggestion = new LanshaSuggestion();
		suggestion.setType(type);
		suggestion.setTitle(title);
		list = lanshaSuggestionService.getMgameSuggestionPage(suggestion, name, getPageDto(), startTime, endTime);
		lanshaSuggestionService.setSuggestionName((List<LanshaSuggestion>) list);
		return SUCCESS;
	}

	/**
	 * @Description: 详情
	 * @return
	 */
	public String detail() {
		if (StringUtils.isBlank(id)) {
			addActionError("请选择一条记录");
			return SUCCESS;
		}
		entity = lanshaSuggestionService.getMgameSuggestionById(id);
		if (entity != null) {
			// 用户名称
			if (StringUtils.isBlank(entity.getUserId())) {
				entity.setUserName("匿名用户");
			} else {
				YwUser ywUser = ywUserService.getYwUserById(entity.getUserId());
				if (ywUser != null) {
					entity.setUserName(ywUser.getUsername());
				} else {
					entity.setUserName("匿名用户");
				}
			}
			// 意见类型
			Map<String, String> mapMcode = sysMcodeDetailService.getMcodeThisIdToContent("LANSHA_SUGGESTION");
			if (mapMcode.containsKey(entity.getType())) {
				entity.setType(mapMcode.get(entity.getType()));
			}
		}
		return SUCCESS;
	}

	/**
	 * @Description: 意见反馈类型
	 * @return
	 */
	public List<SysMcodeDetail> getListSuggestionType() {
		return sysMcodeDetailService.getSysMcodeDetailList("LANSHA_SUGGESTION");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LanshaSuggestion getEntity() {
		return entity;
	}

	public void setEntity(LanshaSuggestion entity) {
		this.entity = entity;
	}

}
