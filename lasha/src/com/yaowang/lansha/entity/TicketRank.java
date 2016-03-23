package com.yaowang.lansha.entity;

import java.io.Serializable;

/**
 * 日票榜
 * @author zhanghq
 * @date 2016年3月2日 下午8:17:17
 */
public class TicketRank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 房间id
	 */
	private String roomid;
	
	/**
	 * 票数
	 */
	private Integer num = 0;
	
	/**
	 * 排名
	 */
	private Integer order = 0;
	
	/**
	 * 上一名
	 */
	private TicketRank previous = null;
	
	/**
	 * 增加日票
	 * @param num
	 */
	public void addTicket(int num){
		this.num += num;
	}	
	
	public TicketRank(String roomid) {
		setRoomid(roomid);
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public Integer getNum() {
		return num;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public TicketRank getPrevious() {
		return previous;
	}

	public void setPrevious(TicketRank previous) {
		this.previous = previous;
	}
}
