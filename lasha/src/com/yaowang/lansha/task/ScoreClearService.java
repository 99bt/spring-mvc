package com.yaowang.lansha.task;

import org.springframework.stereotype.Service;

import com.yaowang.lansha.service.LanshaRoomRankingService;

import javax.annotation.Resource;

/**
 * 
 * 定时清理积分排行缓存
 * @author zhanghq
 * @date 2016年3月5日 下午6:38:42
 */
@Service("scoreClearService")
public class ScoreClearService {
	
	/**
	 * 积分排名服务 
	 */
    @Resource
	private LanshaRoomRankingService lanshaRoomRankingService;

	/**
	 * 清除缓存
	 */
	public void clearScore(){
		lanshaRoomRankingService.clear();
	}
}
