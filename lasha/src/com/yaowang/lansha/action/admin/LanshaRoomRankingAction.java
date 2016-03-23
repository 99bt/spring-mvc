package com.yaowang.lansha.action.admin;

import java.util.*;

import javax.annotation.Resource;

import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.LanshaRoomRanking;
import com.yaowang.lansha.service.LanshaRoomRankingService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;


public class LanshaRoomRankingAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private LanshaRoomRankingService lanshaRoomRankingService;
	private List<LanshaRoomRanking> rankRooms;
	private List<LanshaRoomRanking> rankDetailRooms;
	
	/**
	 * 开始时间
	 */
	private Date startTimes;
	/**
	 * 结束时间
	 */
	private Date endTimes;

	private LanshaRoomRanking entity;
	/**
	 * 主播积分排名列表
	 */
	public String list() {
		if(entity==null){
			entity = new LanshaRoomRanking();
		}
        String day = SysOptionServiceImpl.getValue("LANSHA.ROOM.RANKING.TYPE");
		entity.setRankCircle(day);
		
		Calendar calendar = Calendar.getInstance();
		if(endTime==null){
			//从昨天开始算起
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			endTime = calendar.getTime();
		}
		if(startTime==null){
			//近一个月
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - Integer.valueOf(day));
			startTime = calendar.getTime();
		}
		rankRooms = lanshaRoomRankingService.getAllLanshaRoomRanking(entity,startTime,endTime,getPageDto());
		return SUCCESS;
	}

	/**
	 * 个人排名积分详情列表
	 */
	public String detail() {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
//		Date endTime = calendar.getTime();
//		String day = SysOptionServiceImpl.getValue("LANSHA.ROOM.RANKING.TYPE");
//		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - Integer.valueOf(day) + 1);
//		Date startTime = calendar.getTime();
//		rankDetailRooms = lanshaRoomRankingService.getLanshaRoomRankingPage(entity,DateUtils.getStartDate(startTime), DateUtils.getEndDate(endTime),getPageDto());
		
		rankDetailRooms = lanshaRoomRankingService.getLanshaRoomRankingPage(entity,startTime,endTime,getPageDto());
		lanshaRoomRankingService.setUserName(rankDetailRooms);
        lanshaRoomRankingService.setSalary(rankDetailRooms);
		return SUCCESS;
	}

	public List<LanshaRoomRanking> getRankRooms() {
		return rankRooms;
	}

	public void setRankRooms(List<LanshaRoomRanking> rankRooms) {
		this.rankRooms = rankRooms;
	}

	public LanshaRoomRanking getEntity() {
		return entity;
	}

	public void setEntity(LanshaRoomRanking entity) {
		this.entity = entity;
	}

	public List<LanshaRoomRanking> getRankDetailRooms() {
		return rankDetailRooms;
	}

	public void setRankDetailRooms(List<LanshaRoomRanking> rankDetailRooms) {
		this.rankDetailRooms = rankDetailRooms;
	}

	public Date getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(Date startTimes) {
		this.startTimes = startTimes;
	}

	public Date getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(Date endTimes) {
		this.endTimes = endTimes;
	}
}
