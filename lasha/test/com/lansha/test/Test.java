package com.lansha.test;

import com.yaowang.lansha.dao.YwGameDao;
import com.yaowang.lansha.dao.YwUserRoomDao;
import com.yaowang.lansha.entity.LanshaRoomRanking;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUserRoomDayData;
import com.yaowang.lansha.entity.YwUserRoomHot;
import com.yaowang.lansha.service.*;
import com.yaowang.lansha.service.impl.LanshaTicketService;
import com.yaowang.lansha.task.RoomRankingService;
import com.yaowang.util.DateUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-2
 * Time: 下午7:26
 * To change this template use File | Settings | File Templates.
 */
public class Test extends BaseJunitTest {

    @Resource
    private YwUserRoomDayDataService ywUserRoomDayDataService;
    @Resource
    LanshaTicketService lanshaTicketService;
    @Resource
    RoomRankingService roomRankingService;
    @Resource
    LanshaRoomRankingService lanshaRoomRankingService;
    @Resource
    private YwUserRoomHotService ywUserRoomHotService ;
    @Resource
    YwUserRoomDao ywUserRoomDao;
    @Resource
    private YwUserRoomService ywUserRoomService ;
    @Resource
    YwGameDao   ywGameDao ;
    @Resource
    YwGameHotService ywGameHotService;


    @org.junit.Test
    public void find() throws ParseException {
        YwUserRoomDayData dayData = new YwUserRoomDayData();
        dayData.setDay(DateUtils.getDate(new Date()));
        dayData.setUserId("09C19BE234CE41C092920FA4CF0CF546");
        List<YwUserRoomDayData> dayDatas = ywUserRoomDayDataService.getYwUserRoomDayDataSort(dayData);
        YwUserRoomDayData dayData1 = ywUserRoomDayDataService.getYwUserRoomDayData(dayData);
    }

    @org.junit.Test
    public void test() throws Exception {
        for(int i=10000;i<30000;i++)
        {
//            lanshaTicketService.put("test"+i,i+1+"");
        }
//        long aa = System.currentTimeMillis();
//        int arr[] = lanshaTicketService.sort("test7957");
//        System.out.println(arr);
//        System.out.println(System.currentTimeMillis() - aa);
        // /usr/local/ffmpeg/bin/ffmpeg -i rtmp://live.lansha.tv/live/test6311 -f image2 -ss 1 -vframes 1 -s 516*288 C:\temp\2016\03\1457157668875
    }

    @org.junit.Test
    public void sortedSet() throws Exception {
        lanshaTicketService.resort();
    }

    @org.junit.Test
    public void sorthash() throws Exception {
        long aa = System.currentTimeMillis();
        String arr[] = lanshaTicketService.sort("test28888");
        System.out.println(System.currentTimeMillis() - aa);
    }
    @org.junit.Test
    public void find1() throws Exception {
//        roomRankingService.doReport(DateUtils.getDate(new Date()));
     List<LanshaRoomRanking> list=  lanshaRoomRankingService.getLanshaRoomRankingListSort(DateUtils.toDate("2016-03-09"));

    }

    @org.junit.Test
    public void hot() throws Exception {

        List<YwUserRoomHot> list=  ywUserRoomHotService.getByType(1, 2);


    }


    @org.junit.Test
    public void hot1() throws Exception {

        Map<String,Integer> map= ywUserRoomDao.getRoomNumber();
        System.out.println(111);


    }

    @org.junit.Test
    public void NUM() throws Exception {

        Map<String,Integer> map= ywUserRoomService.getRoomNumber();
        System.out.println(111);


    }

    @org.junit.Test
    public void insert() throws Exception {
        List<Map<String, Object>> list=  ywGameHotService.getHotGame(60);
        System.out.println(111);


    }


}
