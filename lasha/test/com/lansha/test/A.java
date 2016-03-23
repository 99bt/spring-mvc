package com.lansha.test;

import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.dao.LanshaGiftUserDao;
import com.yaowang.lansha.entity.LanshaGiftUser;
import com.yaowang.lansha.beans.SortBean;
import com.yaowang.util.DateUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.cache.DefaultCacheManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-3
 * Time: 下午8:15
 * To change this template use File | Settings | File Templates.
 */
@Service("lanshaTicketService")
public class A extends DefaultCacheManager {

    @Resource
    private LanshaGiftUserDao lanshaGiftUserDao;
    @Resource
    RedisCacheService redisCacheService;



    /***
     * 先判断日票的key是否存在，不存在重置日票数据，存在则对应日票key 中对应列的日票数加1
     * @param userId
     */
    public void put(String userId) {
        if (redisCacheService.existsKey(LanshaConstant.LANSHA_TICKET_KEY)) {
            redisCacheService.hincrBy(LanshaConstant.LANSHA_TICKET_KEY, userId);
        } else {
            resort();
            redisCacheService.hincrBy(LanshaConstant.LANSHA_TICKET_KEY, userId);
        }

    }

    public int[] sort(String userId) throws Exception {
        Map<String, String> map = redisCacheService.get(LanshaConstant.LANSHA_TICKET_KEY); //缓存中获取
        SortBean sortBean = null;
        List<SortBean> list = new ArrayList<SortBean>();
        //遍历map
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sortBean = new SortBean();
            sortBean.setUserId(entry.getKey());
            sortBean.setNumber(Integer.valueOf(entry.getValue()));
            list.add(sortBean);
        }
        //排序
        Collections.sort(list, new Comparator<SortBean>() {
            @Override
            public int compare(SortBean b1, SortBean b2) {
                return b2.getNumber().compareTo(b1.getNumber());
            }
        });

        int size = list.size();
        int sort = 0;//排名
        int number = 0; //日票
        int balance = 0;//上一名日票
        for (int i = 0; i < size; i++) {
            SortBean bean = list.get(i);
            if (bean.getUserId().equals(userId)) {
                sort = i + 1;
                number = bean.getNumber();
                if (sort > 1) {
                    bean = list.get(i - 1);
                    balance = bean.getNumber();
                    break;
                }
            }
        }
        int[] arr = new int[]{sort, number, balance};
        return arr;
    }

    /**
     * 刷新日票排名
     *
     * @throws ParseException
     */
    public void resort() {
        LanshaGiftUser lanshaGiftUser = new LanshaGiftUser();
        lanshaGiftUser.setCreateTime(new Date());
        lanshaGiftUser.setGiftId(LanshaConstant.GIFT_ID_FIVE);
        final List<LanshaGiftUser> list = lanshaGiftUserDao.getSumGiftNum(lanshaGiftUser);
        for (LanshaGiftUser lanshaGift : list) {
            if (StringUtils.isNotEmpty(lanshaGift.getAnchorId())) {
                put(lanshaGift.getAnchorId(), lanshaGift.getNumber() + "");
            }
        }
    }
    private void put(String userId, String value) {
        redisCacheService.put(LanshaConstant.LANSHA_TICKET_KEY, userId, value);
    }
    public void zadd(String userId, Integer value) {
        redisCacheService.zadd(LanshaConstant.LANSHA_TICKET_KEY_ZADD, value, userId);
    }

    public void sortedSet() throws ParseException {
        LanshaGiftUser lanshaGiftUser = new LanshaGiftUser();
        lanshaGiftUser.setCreateTime(new Date());
        lanshaGiftUser.setGiftId(LanshaConstant.GIFT_ID_FIVE);
        final List<LanshaGiftUser> list = lanshaGiftUserDao.getSumGiftNum(lanshaGiftUser);
        for (LanshaGiftUser lanshaGift : list) {
            if (StringUtils.isNotEmpty(lanshaGift.getAnchorId())) {
                zadd(lanshaGift.getAnchorId(), lanshaGift.getNumber());
            }
        }
    }

    public int[] sortzadd(String userId) throws Exception {
        Long sort = redisCacheService.zrevrank(LanshaConstant.LANSHA_TICKET_KEY_ZADD, userId);
        Double number = redisCacheService.zscore(LanshaConstant.LANSHA_TICKET_KEY_ZADD, userId); //获取某一个
        int[] arr = new int[]{sort.intValue(), number.intValue(), 0};
        return arr;
    }

}
