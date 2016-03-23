package com.yaowang.lansha.action.wap;

import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.service.impl.SysOptionServiceImpl;

/**
 * @ClassName: ActivityWapCfAction
 * @Description: cf活动
 * @author wanglp
 * @date 2016-1-22 下午2:56:07
 *
 */
public class ActivityWapCfAction extends ActivityWapAction {
	private static final long serialVersionUID = 1L;
	
	public ActivityWapCfAction() {
		super.setGameName("穿越火线");
		super.itemId = LanshaConstant.ITEM_ID_THIRD;//活动三id
		super.defaultGiftId = LanshaConstant.DEFAULT_GIFT_ID_THIRD;//设置当前活动默认奖品
		super.setShareLink("/wap/activity/cf/index.html");//设置分享的链接
		super.setShareContent(SysOptionServiceImpl.getValue("ACTIVITY.CYHX.SHARE"));//设置分享的内容
	}

}
