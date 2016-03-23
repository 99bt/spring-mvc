package com.yaowang.lansha.action.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.common.highcharts.Highcharts;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.DateUtils;

public class RegiestStatisticsAction extends BasePageAction {
	private static final long serialVersionUID = 811436713764614549L;
	
	@Resource
	private YwUserService ywUserService;
	
	/**
	 * 总注册用户数
	 */
	private Integer regCounts = 0;
	
	/**
	 * 列表
	 * @return
	 * @creationDate. 2015-12-25 下午4:54:15
	 * @throws ParseException 
	 */
	public String list() throws ParseException{
		getData();
		return SUCCESS;
	}
	
	public List<YwUser> getData() throws ParseException{
		List<YwUser> users = new ArrayList<YwUser>();
		if (startTime == null && endTime == null) {
			setStartTime(new Date(getNow().getTime() - 7 * 24 * 60 * 60 * 1000));
			setEndTime(new Date(getNow().getTime() - 1 * 24 * 60 * 60 * 1000));
		}
		//不给搜索日期则返回空数据
		if(startTime == null){
			return null;
		}
		if(endTime == null){
			return null;
		}
		List<YwUser> data = ywUserService.getRegiestCount(startTime, endTime);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if(endTime.getTime() > startTime.getTime()){
			List<Date> day = DateUtils.getEveryday(sd.format(startTime), sd.format(endTime));
			for(Date d : day){
				if(CollectionUtils.isEmpty(data)){
					data = new ArrayList<YwUser>();
				}
				//无数据增加每天0注册用户
				if(data.size() == 0){
					YwUser user = new YwUser();
					user.setRegiestCount(0);
					user.setCreateTime(d);
					users.add(user);
				}else{
					boolean isFound = false;
					for(YwUser obj : data){
						if(sd.format(obj.getCreateTime()).equals(sd.format(d))){
							isFound = true;
							users.add(obj);
							break;
						}
					}
					if(!isFound){
						YwUser user = new YwUser();
						user.setRegiestCount(0);
						user.setCreateTime(d);
						users.add(user);
					}
				}
			}
		}
		return users;
	}
	
	/**
	 * 统计注册人数
	 * @creationDate. 2015-12-25 下午4:22:25
	 * @throws ParseException 
	 */
	public void statistics() throws ParseException {
		List<YwUser> data = getData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		JSONObject jsonData = new JSONObject();
		List<String> listXdata = new ArrayList<String>();
		List<Integer> incomeDataList = new ArrayList<Integer>();
		List<Highcharts> dataList = new ArrayList<Highcharts>();
		Highcharts incomeChart = new Highcharts();
		if (CollectionUtils.isNotEmpty(data)) {
			for (YwUser obj : data) {
				listXdata.add(sdf.format(obj.getCreateTime()).toString());//日期
				incomeDataList.add(obj.getRegiestCount());//当前日期注册数
				regCounts += obj.getRegiestCount();//总注册数
			}
			
		}
		incomeChart.setName("注册用户数");
		incomeChart.setData(incomeDataList);
		dataList.add(incomeChart);
		jsonData.put("regCounts", regCounts);
		jsonData.put("listYdata", dataList);
		jsonData.put("listXdata", listXdata);
		try {
			super.writeNoLog(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getRegCounts() {
		return regCounts;
	}

	public void setRegCounts(Integer regCounts) {
		this.regCounts = regCounts;
	}
}
