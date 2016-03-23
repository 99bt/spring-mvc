package com.yaowang.lansha.service;

import com.yaowang.lansha.entity.YwUser;

public interface LanshaAddActivityStockService {
	/**
	 * 注册时增加抽奖机会
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午2:18:42
	 */
	public void addRegActivityStock(YwUser ywUser);
	
	/**
	 * 登录时增加抽奖机会
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午2:19:04
	 */
	public void addLoginActivityStock(YwUser ywUser);
	
	/**
	 * 领取蓝鲨币获得抽奖机会
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午3:17:13
	 */
	public void addActivityStock(YwUser ywUser);
	
	/**
	 * 当主播成为主播增加主播和推广人5次抽奖机会
	 * @param ywUser
	 * @creationDate. 2016-1-11 下午3:48:21
	 */
	public void addHostStock(YwUser ywUser);
	/**
	 * 登录时增加抽奖机会
	 * @param ywUser
	 * @param numb(标示是登录还是注册调用[regiest:注册；login:登录])
	 * @creationDate. 2016-1-21 上午11:50:05
	 */
	public void addFirstMobileLogin(YwUser ywUser, String type);
	
	/**
	 * 增加抽奖机会接口
	 * @param ywUser
	 * @param add
	 * @param itemId
	 * @return
	 * @creationDate. 2016-1-20 下午5:25:01
	 */
	public boolean addActivityStockInterface(YwUser ywUser, int add, String itemId);
//	/**
//	 * wap首页注册增加抽奖机会
//	 * @param ywUser
//	 * @param ip
//	 */
//	public void addWapActivityStocks(YwUser ywUser, String ip, String itemId, String defaultGiftId);
//	/**
//	 * wap首页登录增加抽奖机会
//	 * @param ywUser
//	 * @param ip
//	 */
//	public void addWapLoginActivityStocks(YwUser ywUser, String ip, String itemId, String defaultGiftId);
}
