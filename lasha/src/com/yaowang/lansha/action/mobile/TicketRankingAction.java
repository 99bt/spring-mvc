package com.yaowang.lansha.action.mobile;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaMobileAction;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.impl.LanshaTicketService;

import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-1
 * Time: 上午9:53
 * To change this template use File | Settings | File Templates.
 */
public class TicketRankingAction extends LanshaMobileAction {
    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private LanshaTicketService lanshaTicketService;

    private String roomId;

    public void ranking() throws Exception {
        if (StringUtils.isEmpty(roomId)) {
            write(getFailed("roomId不能为空"));
            return;
        }
        YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(roomId);
        if (ywUserRoom == null) {
            write(getFailed("房间不存在"));
            return;
        }
        //返回数据
        Map<String, String> datamap = new HashMap<String, String>();
        String arr[] = lanshaTicketService.sort(ywUserRoom.getUid());
        int length = arr.length;
        datamap.put("roomId", roomId);
        
        if(length>0) {
            datamap.put("rank",arr[0]);
            datamap.put("ticketNum",arr[1]);
            datamap.put("balance",arr[2]);
        }

        writeSuccessWithData(datamap);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
