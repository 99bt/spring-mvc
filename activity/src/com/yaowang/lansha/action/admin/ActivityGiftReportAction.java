package com.yaowang.lansha.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.common.highcharts.Highcharts;
import com.yaowang.lansha.entity.ActivityGift;
import com.yaowang.lansha.entity.ActivityItem;
import com.yaowang.lansha.service.ActivityGiftService;
import com.yaowang.lansha.service.ActivityItemService;
import com.yaowang.util.DateUtils;

public class ActivityGiftReportAction extends LanshaBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4996917421201693138L;

	@Resource
	private ActivityGiftService activityGiftService;
	@Resource
	private ActivityItemService activityItemService;
	
	private ActivityGift entity;

	/**
	 * 列表
	 * @return
	 * @creationDate. 2015-12-25 下午4:54:15
	 */
	public String list() throws ParseException{
		if (startTime == null || endTime == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			String now = sdf.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(now));
			cal.add(Calendar.DAY_OF_MONTH, -1);
			setEndTime(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, -6);
			setStartTime(cal.getTime());
		}
		list = activityGiftService.getActivityGiftList(entity);
		return SUCCESS;
	}
	
	public List<ActivityGift> getData() throws ParseException{
		List<ActivityGift> tempList = new ArrayList<ActivityGift>();
		ActivityGift activityGift= activityGiftService.getActivityGiftById(entity.getId());		
		ActivityItem activityItem = activityItemService.getActivityItemById(activityGift.getItemId());
		List<ActivityGift> gifts = activityGiftService.getActivityGiftList(entity,activityItem.getStartTime(),endTime);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if(endTime.getTime() > startTime.getTime()){			
			List<Date> days = DateUtils.getEveryday(sd.format(startTime), sd.format(endTime));
			if(days.size()<1){
				return tempList;
			}
			String now = sd.format(new Date());			
			for(int i=0;i<days.size();i++){
				ActivityGift gift = new ActivityGift();
				gift.setSentGift(0);
				gift.setOrderId(i);
				if(days.get(i).getTime()<sd.parse(now).getTime()){
					gift.setRemainGift(activityGift.getNumber());
				}else{
					gift.setRemainGift(activityGift.getStock());
				}
				gift.setCreateTime(days.get(i));
				tempList.add(gift);	
			}
			boolean isFirst = true;
			Integer startNum = 0;
			for(ActivityGift gift : gifts){
				if(isFirst){
					startNum = activityGift.getNumber();
					isFirst = false;
				}
				startNum = startNum - gift.getSentGift();
				gift.setRemainGift(startNum);
				for(int i=0;i<days.size();i++){
					ActivityGift tmpGift  = tempList.get(i);
					if(sd.format(days.get(i)).equalsIgnoreCase(sd.format(gift.getCreateTime()))){						
						tmpGift.setSentGift(gift.getSentGift());
						tmpGift.setRemainGift(gift.getRemainGift());
						break;
					}else if(days.get(i).getTime()<gift.getCreateTime().getTime()
							&&tmpGift.getSentGift()==0){
						if(i-1>=0){
							tmpGift.setRemainGift(tempList.get(i-1).getRemainGift());
						}
					}
				}
			}
		}
		return tempList;
	}
	
	/**
	 * 礼物报表统计
	 * @return
	 * @throws ParseException 
	 */
	public void statistics() throws ParseException{
		List<ActivityGift> data = getData();
		ActivityGift activityGift= activityGiftService.getActivityGiftById(entity.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		JSONObject jsonData = new JSONObject();
		List<String> listXdata = new ArrayList<String>();
		List<Integer> sentGiftDataList = new ArrayList<Integer>();
		List<Integer> remainGiftDataList = new ArrayList<Integer>();
		List<Highcharts> dataList = new ArrayList<Highcharts>();
		Highcharts sentGiftChart = new Highcharts();
		if (CollectionUtils.isNotEmpty(data)) {
			for (ActivityGift obj : data) {
				listXdata.add(sdf.format(obj.getCreateTime()).toString());//日期
				sentGiftDataList.add(obj.getSentGift());//发放数量
				remainGiftDataList.add(obj.getRemainGift());//剩余数量
			}
		}		
		sentGiftChart.setData(sentGiftDataList);
		sentGiftChart.setName("发放数量");
		dataList.add(sentGiftChart);
		Highcharts remainGiftChart = new Highcharts();		
		remainGiftChart.setData(remainGiftDataList);
		remainGiftChart.setName("剩余数量");
		dataList.add(remainGiftChart);
		jsonData.put("giftName",activityGift.getName()+"报表柱形图");
		jsonData.put("listYdata", dataList);
		jsonData.put("listXdata", listXdata);
		try {
			super.writeNoLog(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 礼物库存报表统计
	 * @return
	 */
	public void stockStatistics(){
		List<ActivityGift> data = activityGiftService.getActivityGiftList(entity);
		if(data==null)
		{
			data = new ArrayList<ActivityGift>();
		}
		ActivityGift giftTotal = new ActivityGift();
		giftTotal.setName("所有礼物");
		data.add(0,giftTotal);
		JSONObject jsonData = new JSONObject();
		List<String> listXdata = new ArrayList<String>();
		List<Integer> sentGiftDataList = new ArrayList<Integer>();
		List<Integer> remainGiftDataList = new ArrayList<Integer>();
		List<Integer> allGiftDataList = new ArrayList<Integer>();
		List<Highcharts> dataList = new ArrayList<Highcharts>();
		Highcharts sentGiftChart = new Highcharts();
		if (CollectionUtils.isNotEmpty(data)) {
			boolean isFirst = true;
			Integer totalNumber = 0;
			Integer totalStock = 0;
			Integer totalSentGifts = 0;
			for (ActivityGift obj : data) {
				listXdata.add(obj.getName());//礼物
				if(isFirst)
				{
					isFirst = false;
					continue;
				}				
				Integer stock= (obj.getNumber() - obj.getStock());				
				sentGiftDataList.add(stock<=0?0:stock);//库存
				remainGiftDataList.add(obj.getStock());
				allGiftDataList.add(obj.getNumber());

				totalSentGifts=totalSentGifts+(stock<=0?0:stock);
				totalNumber=totalNumber+obj.getNumber();
				totalStock=totalStock+obj.getStock();
			}
			sentGiftDataList.add(0, totalSentGifts);
			remainGiftDataList.add(0, totalStock);
			allGiftDataList.add(0, totalNumber);
		}		
		sentGiftChart.setName("已发出");
		sentGiftChart.setData(sentGiftDataList);		
		dataList.add(sentGiftChart);
		
		Highcharts remainGiftChart = new Highcharts();		
		remainGiftChart.setData(remainGiftDataList);
		remainGiftChart.setName("库存");
		dataList.add(remainGiftChart);
		
		Highcharts allGiftChart = new Highcharts();		
		allGiftChart.setData(allGiftDataList);
		allGiftChart.setName("总量");
		dataList.add(allGiftChart);
		jsonData.put("listYdata", dataList);
		jsonData.put("listXdata", listXdata);
		try {
			super.writeNoLog(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ActivityGift getEntity() {
		return entity;
	}

	public void setEntity(ActivityGift entity) {
		this.entity = entity;
	}
}
