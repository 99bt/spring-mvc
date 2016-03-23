package com.yaowang.lansha.entity;

public class ActivityStockAdd {
	/**
	 * 活动id
	 */
	private String itemId;
	/**
	 * 注册时增加机会数
	 */
	private int regAdd;
	/**
	 * 通过推广注册增加机会数
	 */
	private int regSpreadAdd;
	/**
	 * 推广他人注册推广人获得机会数
	 */
	private int spreadAdd;
	/**
	 * 登录获得机会数
	 */
	private int loginAdd;
	/**
	 * 移动端登录增加机会
	 */
	private int mobileLoginAdd;
	/**
	 * 领取虾米获得机会数
	 */
	private int xiamiAdd;
	/**
	 * 成为主播获得机会数
	 */
	private int hostAdd;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public int getRegAdd() {
		return regAdd;
	}
	public void setRegAdd(int regAdd) {
		this.regAdd = regAdd;
	}
	public int getRegSpreadAdd() {
		return regSpreadAdd;
	}
	public void setRegSpreadAdd(int regSpreadAdd) {
		this.regSpreadAdd = regSpreadAdd;
	}
	public int getSpreadAdd() {
		return spreadAdd;
	}
	public void setSpreadAdd(int spreadAdd) {
		this.spreadAdd = spreadAdd;
	}
	public int getLoginAdd() {
		return loginAdd;
	}
	public void setLoginAdd(int loginAdd) {
		this.loginAdd = loginAdd;
	}
	public int getXiamiAdd() {
		return xiamiAdd;
	}
	public void setXiamiAdd(int xiamiAdd) {
		this.xiamiAdd = xiamiAdd;
	}
	public int getHostAdd() {
		return hostAdd;
	}
	public void setHostAdd(int hostAdd) {
		this.hostAdd = hostAdd;
	}
	public int getMobileLoginAdd() {
		return mobileLoginAdd;
	}
	public void setMobileLoginAdd(int mobileLoginAdd) {
		this.mobileLoginAdd = mobileLoginAdd;
	}
}
