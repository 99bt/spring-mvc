package com.yaowang.lansha.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.ActivityGift;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.ActivityUser;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.ActivityGiftService;
import com.yaowang.lansha.service.ActivityGiftStockService;
import com.yaowang.lansha.service.ActivityUserService;

/**
 * @ClassName: ActivityGiftStockAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tandingbo
 * @date 2016年1月8日 下午2:59:50
 * 
 */
public class ActivityGiftStockAction extends LanshaBaseAction {
	private static final long serialVersionUID = 9116323465198567310L;

	@Resource
	private ActivityUserService activityUserService;
	@Resource
	private ActivityGiftStockService activityGiftStockService;
	@Resource
	private ActivityGiftService activityGiftService;	
	
	private ActivityUser entity;
    private String itemId;//活动id
    private String qq;//用户qq
	/**
	 * @Description: 我的奖品记录信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		YwUser ywUser = getUserLogin();
		if (ywUser == null) {
			list = new ArrayList<ActivityGiftStock>();
			entity = new ActivityUser();
			return SUCCESS;
		}
		ActivityGiftStock activityGiftStock = new ActivityGiftStock();
		activityGiftStock.setUserId(ywUser.getId());
		list = activityGiftStockService.getActivityGiftStockList(activityGiftStock);
		if (CollectionUtils.isNotEmpty(list)) {
			activityGiftStockService.setGiftName((List<ActivityGiftStock>) list);
			activityGiftStockService.setUserInfo((List<ActivityGiftStock>) list);
		}
		entity = activityUserService.getActivityUserById(ywUser.getId());
		return SUCCESS;
	}

	/**
	 * @throws IOException 
	 * PC端推广落地页新增活动
	 * @Description: TODO
	 * @return void
	 * @throws
	 */
	public void saveActivity() throws IOException{
		YwUser ywUser=getUserLogin();
		if(ywUser!=null){			
			//新增收获人
			ActivityUser activityUser = new ActivityUser();
			activityUser.setId(ywUser.getId());
			activityUser.setQq(qq);
			activityUser.setCreateTime(getNow());


			String giftId=null;
			//判断itemId是否存在
			if(LanshaConstant.ITEM_ID_TWO.equals(itemId)){
				giftId=LanshaConstant.Q_GIFT_ID;
			}else if(LanshaConstant.ITEM_ID_THIRD.equals(itemId)){
				giftId=LanshaConstant.C_GIFT_ID;
			}else{
				write(getErrorMsg("不存在该活动"));
				return;
			}
			
			//判断是否重复新增
			ActivityGiftStock activityGiftStock=new ActivityGiftStock();
			activityGiftStock.setUserId(ywUser.getId());
			activityGiftStock.setGiftId(LanshaConstant.Q_GIFT_ID);
			int c=activityGiftStockService.getActivityGiftStockNumber(activityGiftStock, null, null);
		    if(c>0){//已领取过此活动
		    	write(getErrorMsg("已领过此活动"));
				return;
		    }else{
		    	activityGiftStock.setUserId(ywUser.getId());
				activityGiftStock.setGiftId(LanshaConstant.C_GIFT_ID);
				c=activityGiftStockService.getActivityGiftStockNumber(activityGiftStock, null, null);
		        if(c>0){//已领取过此活动
			    	write(getErrorMsg("已领过此活动"));
					return;
		        }
		    }
		    //判断库存是否还有
		    ActivityGift oldactivityGift= activityGiftService.getActivityGiftById(giftId);
		    if(oldactivityGift.getStock()<1){//库存无
		    	write(getErrorMsg("活动商品已被领完"));
				return;
		    }
		    
			ActivityGiftStock itemLottery = new ActivityGiftStock();
			itemLottery.setItemId(itemId);
			itemLottery.setUserId(ywUser.getId());
			itemLottery.setIp(getClientIP());
			itemLottery.setDefaultGiftId(null);		
			ActivityGift finalPrize=activityGiftService.getActivityGiftById(giftId);
			//奖品数-1
			itemLottery.setGiftId(finalPrize.getId());
			itemLottery.setGiftName(finalPrize.getName());
			itemLottery.setType(finalPrize.getType());
			itemLottery.setPrizeIndex(finalPrize.getOrderId());
			if("3".equalsIgnoreCase(finalPrize.getType())
					||"4".equalsIgnoreCase(finalPrize.getType())){
				itemLottery.setStatus("0");
			}else{
				itemLottery.setStatus("1");
			}
			itemLottery.setGiftProperty("0");
			itemLottery.setCreateTime(new Date());		
			activityGiftStockService.updateGiftNumberAndStockAndUser(itemLottery,activityUser);
			write("{\"status\": 1}");
			return;
		}
	}
	


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public ActivityUser getEntity() {
		return entity;
	}

	public void setEntity(ActivityUser entity) {
		this.entity = entity;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
}
