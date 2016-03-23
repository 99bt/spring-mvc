package com.yaowang.lansha.task;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.service.LanshaUserGiftStockService;
import com.yaowang.lansha.service.impl.LanshaTicketService;

/**
 * 日票服务
 * @author zhanghq
 */
@Service("ticketResetTaskService")
public class TicketResetTaskService {
	
	/**
	 * 每天日票重置数量
	 */
	private static final int TICKET_RESET_NUM = 1;

	@Resource
	private LanshaUserGiftStockService lanshaUserGiftStockService;
	@Resource
	private LanshaTicketService lanshaTicketService;
	
	/**
	 * 重置数量
	 * @throws Exception 
	 */
	public void reset() throws Exception{
		//所以用户日票数归1
		lanshaUserGiftStockService.updateGiftStock(LanshaConstant.GIFT_ID_FOUR, TICKET_RESET_NUM);
		//刷新日票
		lanshaTicketService.clear();
		//避免重复从数据库中取空的map
		lanshaTicketService.putMap(new HashMap<String, Integer>());
	}
}
