package com.yaowang.lansha.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yaowang.lansha.beans.SortBean;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.dao.LanshaGiftUserDao;
import com.yaowang.lansha.entity.LanshaGiftUser;
import com.yaowang.util.cache.DefaultCacheManager;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-3
 * Time: 下午8:15
 * To change this template use File | Settings | File Templates.
 */
@Service("lanshaTicketService")
public class LanshaTicketService extends DefaultCacheManager {
    @Resource
    private LanshaGiftUserDao lanshaGiftUserDao;
    
    public String LANSHA_TICKET_KEY = fetchCacheIndexKey + LanshaConstant.LANSHA_TICKET_KEY;


    /***
     * 先判断日票的key是否存在，不存在重置日票数据，存在则对应日票key 中对应列的日票数加1
     * @param userId
     * @throws Exception 
     */
    public void put(String userId) throws Exception {
    	//主播日票数
    	String key = LANSHA_TICKET_KEY + "_ticket_" + userId;
    	Integer count = (Integer)getCacheService().get(key);
    	Map<String, Integer> map = getTicketMap();
    	if (count == null) {
    		if (map.containsKey(userId)) {
				count = map.get(userId) + 1;
			}else {
				count = 1;
			}
    		//缓存key
    		getCacheService().putList(fetchCacheIndexKey, key);
		}else {
			count = count + 1;
		}
    	//返回缓存
    	getCacheService().put(key, count);
    	
    	map.put(userId, count);
		putMap(map);
    }
    
    /**
     * 从缓存中读取日票map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public Map<String, Integer> getTicketMap() throws Exception {
    	Map<String, Integer> map = (Map<String, Integer>)getCacheService().get(LANSHA_TICKET_KEY);
    	if (map == null) {
    		//没有日票(从数据库中计算)
    		LanshaGiftUser lanshaGiftUser = new LanshaGiftUser();
			lanshaGiftUser.setCreateTime(new Date());
			lanshaGiftUser.setGiftId(LanshaConstant.GIFT_ID_FIVE);
			List<LanshaGiftUser> list = lanshaGiftUserDao.getSumGiftNum(lanshaGiftUser);
			
			map = new HashMap<String, Integer>();
			for (LanshaGiftUser lanshaGift : list) {
				if (StringUtils.isNotEmpty(lanshaGift.getAnchorId())) {
					map.put(lanshaGift.getAnchorId(), lanshaGift.getNumber());
				}
			}
			
			putMap(map);
			//缓存key
			getCacheService().putList(fetchCacheIndexKey, LANSHA_TICKET_KEY);
		}
    	return map;
    }
    /**
     * 日票map缓存起来
     * @param map
     * @throws Exception
     */
    public void putMap(Map<String, Integer> map) throws Exception{
    	getCacheService().put(LANSHA_TICKET_KEY, map);
    }
    
    /**
     * 获取日票(排名，日票数，上一名)
     * @param userId
     * @return
     * @throws Exception
     */
    public String[] sort(String userId) throws Exception {
        Map<String, Integer> map = getTicketMap();
        if (map == null) {
        	return new String[]{"正在统计", "-", "-" };
        }
        
    	List<SortBean> list = new ArrayList<SortBean>();
    	for (String key : map.keySet()) {
    		SortBean sortBean = new SortBean();
    		sortBean.setUserId(key);
    		sortBean.setNumber(map.get(key));
    		list.add(sortBean);
    	}
    	// 排序算出排名
    	Collections.sort(list, new Comparator<SortBean>() {
    		@Override
    		public int compare(SortBean b1, SortBean b2) {
    			return b2.getNumber().compareTo(b1.getNumber());
    		}
    	});
    	// 计算排名
    	int ranking = 1;
    	for (int i = 0; i < list.size(); i++) {
    		SortBean bean = list.get(i);
			if (i <= 0) {
				bean.setRanking(ranking);
			}else if (bean.getNumber().compareTo(list.get(i-1).getNumber()) == 0) {
				//与前一名同名
				bean.setRanking(ranking);
			}else {
				ranking = i + 1;
				bean.setRanking(ranking);
			}
		}
    	
    	for (int i = 0; i < list.size(); i++) {
    		SortBean bean = list.get(i);
    		if (bean.getUserId().equals(userId)) {
				// 上一名日票数(为了防止同日票排名情况)
				SortBean befbean = null;
				int index = i - 1;
    			while (index>=0) {
    				if (list.get(index).getNumber().compareTo(bean.getNumber()) != 0) {
    					befbean = list.get(index);
						break;
					}else {
						index--;
					}
				}
    			
    			if (befbean != null) {
    				// 上一名日票数 
    	        	return new String[]{bean.getRanking()+"", bean.getNumber().toString(), befbean.getNumber().toString() + "票" };
    			}else {
    				// 没有上一名
    	        	return new String[]{bean.getRanking()+"", bean.getNumber().toString(), "-" };
				}
    		}
    	}
    	return new String[]{"-", "0", "-" };
    }
    
    /**
     * 重新计算日票，后台按钮调用
     * @throws ParseException
     */
    public void resort() throws Exception {
        clear();
        getTicketMap();
    }
}
