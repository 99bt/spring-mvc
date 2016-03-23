package com.yaowang.lansha.action.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.lansha.service.YwUserRoomRelationService;
import com.yaowang.lansha.service.YwUserRoomService;
/**
 * @ClassName: MyAttentionAction
 * @Description: 我的关注
 * @author wanglp
 * @date 2015-12-10 上午11:13:04
 *
 */
public class MyAttentionAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwUserRoomRelationService ywUserRoomRelationService;
	@Resource
	private YwUserRoomService ywUserRoomService;

	@SuppressWarnings("unchecked")
	public String myAttention(){
		// 当前登录用户ID
		String uid = getUserLogin().getId();
		YwUserRoomRelation roomRelation = new YwUserRoomRelation();
		roomRelation.setUid(uid);
		roomRelation.setStatus(BaseConstant.YES);
		List<YwUserRoomRelation> listRoomRelation = ywUserRoomRelationService.getYwUserRoomRelationPage(roomRelation, null);
		if(CollectionUtils.isNotEmpty(listRoomRelation)){
			Set<String> roomIds = new HashSet<String>();
			for (YwUserRoomRelation ywUserRoomRelation : listRoomRelation) {
				roomIds.add(ywUserRoomRelation.getRoomId());
			}
			
			PageDto page = getPageDto();
			page.setCount(false);
			list = ywUserRoomService.getYwUserRoomByIds(roomIds.toArray(new String[]{}), page);
			if(CollectionUtils.isNotEmpty(list)){
				ywUserRoomService.setUserName((List<YwUserRoom>)list);
				ywUserRoomService.setGameName((List<YwUserRoom>)list);
			}
		}
		return SUCCESS;
	}
}
