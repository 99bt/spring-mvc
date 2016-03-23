package com.yaowang.lansha.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.ActivityGift;
import com.yaowang.lansha.entity.ActivityGiftStock;
import com.yaowang.lansha.entity.ActivityItem;
import com.yaowang.lansha.entity.LanshaGift;
import com.yaowang.lansha.service.ActivityGiftService;
import com.yaowang.lansha.service.ActivityItemService;
import com.yaowang.lansha.service.LanshaGiftService;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;

/**
 * @ClassName: ActivityItemGiftAction
 * @Description: TODO(活动礼包)
 * @author tandingbo
 * @date 2015年12月24日 下午4:36:54
 * 
 */
public class ActivityItemGiftAction extends LanshaBaseAction {
	private static final long serialVersionUID = 4558106182931265942L;

	@Resource
	private ActivityGiftService activityGiftService;
	@Resource
	private ActivityItemService activityItemService;
	@Resource
	private LanshaGiftService lanshaGiftService;

	public ActivityGift entity;// 礼物
	public ActivityItem activityItem;// 活动
	public String activityId;// 活动ID
	public Integer stock;// 库存

	/**
	 * @Description: 列表活动礼包
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		if (entity == null) {
			entity = new ActivityGift();
		}
		entity.setItemId(activityId);
		ActivityItem activityItem = activityItemService.getActivityItemById(activityId);
		if (activityItem != null) {
			list = activityGiftService.getActivityGiftPage(entity, null);
			if (CollectionUtils.isNotEmpty(list)) {
				activityGiftService.setName((List<ActivityGift>) list, activityItem);
			}
		} else {
			list = new ArrayList<ActivityGift>();
		}
		return SUCCESS;
	}

	/**
	 * @Description: 详情
	 * @return
	 */
	public String detail() {
		activityItem = activityItemService.getActivityItemById(activityId);
		if (StringUtils.isBlank(id)) {
			entity = new ActivityGift();
			entity.setItemId(activityId);
		} else {
			entity = activityGiftService.getActivityGiftById(id);
		}
		return SUCCESS;
	}

	/**
	 * @Description: 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		// 上传图片处理
		UploadFile[] files = UploadUtils.handleFileUpload();
		for (UploadFile file : files) {
			if (!file.getContentType().matches("image/.+")) {
				addActionError("请上传正确的图片文件");
				// info();
				return SUCCESS;
			}
		}

		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(UUIDUtils.newId());
			entity.setCreateTime(getNow());
			entity.setNumber(0);
			entity.setStock(0);
			if(entity.getOrderId()==null){
				entity.setOrderId(0);
			}
			upload(files, entity);
			activityGiftService.save(entity);
		} else {
			if(entity.getOrderId()==null){
				entity.setOrderId(0);
			}
			upload(files, entity);
			activityGiftService.update(entity);
		}
		list();
		addActionMessage("保存成功");
		return SUCCESS;
	}

	// 上传文件
	private void upload(UploadFile[] files, ActivityGift entity) throws Exception {
		UploadFile file1 = null;

		for (UploadFile file : files) {
			if ("iconImg".equals(file.getFieldName())) {
				file1 = file;
			}
		}

		String ext;
		String filePath;
		if (file1 != null) {
			Calendar calendar = Calendar.getInstance();
			ext = FileUtil.getExtensionName(file1.getFileName());
			// 文件地址 /年/月/日/gift/gift_id/headpic+随机数.
			filePath = StorePathUtil.buildPath( entity.getId(), "gift","headpic" + calendar.get(Calendar.MILLISECOND) + "." + ext).toString();
			FileSystemUtil.saveFile(file1.getFile(), filePath);
			entity.setImg(filePath);
		}
	}

	/**
	 * @Description: 删除
	 * @return
	 */
	public String delete() {
		if (ids.length > 0) {
			try {
				activityGiftService.delete(ids);
				addActionMessage("删除成功");
			} catch (Exception e) {
				addActionMessage("删除失败");
			}
		}
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 上线
	 * @return
	 */
	public String online() {
		try {
			updateStatus("1");
			addActionMessage("操作成功");
		} catch (Exception e) {
			addActionMessage("操作失败");
		}
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 下线
	 * @return
	 */
	public String offline() {
		try {
			updateStatus("0");
			addActionMessage("操作成功");
		} catch (Exception e) {
			addActionMessage("操作失败");
		}
		list();
		return SUCCESS;
	}
	
	/**
	 * @Description: 库存
	 * @return
	 */
	public String giftStock(){
		ActivityGift gift = activityGiftService.getActivityGiftById(id);
		if(gift == null){
			addActionError("礼物不存在");
			list();
			return ERROR;
		}
		if((gift.getStock() + stock) < 0){
			addActionError("当前库存不足，请确认后重试");
			list();
			return ERROR;
		}
		ActivityGiftStock giftStock = new ActivityGiftStock();
		giftStock.setNumber(stock);
		giftStock.setStock(stock);
		giftStock.setGiftId(id);
		try {
			activityGiftService.updateGiftNumberAndStock(giftStock);
			addActionError("库存更新成功");
			list();
			return SUCCESS;
		} catch (Exception e) {
			addActionError("库存更新失败");
			list();
			return ERROR;
		}
	}

	private void updateStatus(String status) throws Exception {
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("请选择操作记录");
		}
		activityGiftService.updateStatusById(id, status);
	}

	public List<LanshaGift> getGiftList() {
		LanshaGift search = new LanshaGift();
		search.setStatus("1");
		return lanshaGiftService.getLanshaGiftPage(search, null);
	}

	public ActivityGift getEntity() {
		return entity;
	}

	public void setEntity(ActivityGift entity) {
		this.entity = entity;
	}

	public ActivityItem getActivityItem() {
		return activityItem;
	}

	public void setActivityItem(ActivityItem activityItem) {
		this.activityItem = activityItem;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
