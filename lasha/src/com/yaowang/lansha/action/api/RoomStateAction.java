package com.yaowang.lansha.action.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BaseDataAction;
import com.yaowang.common.util.LogUtil;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.LanshaLiveHistory;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomRelation;
import com.yaowang.lansha.service.LanshaLiveHistoryService;
import com.yaowang.lansha.service.YwUserRoomRelationService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.lansha.task.MasterStateService;
import com.yaowang.lansha.util.UmengSend;
import com.yaowang.util.ApnsSend;
import com.yaowang.util.DateUtils;
import com.yaowang.util.LanshaResource;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.asynchronous.AsynchronousService;
import com.yaowang.util.asynchronous.ObjectCallable;
import com.yaowang.util.http.HttpUtil;

/**
 * 主播状态修改接口
 *
 * @author shenl
 */
public class RoomStateAction extends BaseDataAction {
    private static final long serialVersionUID = 1L;
    private static final String REWRITE_PATH = LanshaResource.dataMap.get("rewrite-path");
    private Long time;

    @Resource
    private YwUserRoomService ywUserRoomService;
    @Resource
    private LanshaLiveHistoryService lanshaLiveHistoryService;
    @Resource
    private YwUserRoomRelationService ywUserRoomRelationService;
    @Resource
    private YwUserService ywUserService;

    /**
     * 开始推流
     *
     * @throws Exception
     */
    public void start() throws Exception {
        try {
            if (StringUtils.isNotBlank(id)) {
                YwUserRoom room = ywUserRoomService.getYwUserRoomByRoomId(id);
                if (room == null) {
                    if (StringUtils.isNotBlank(REWRITE_PATH)) {
                        //转发
                        String str = HttpUtil.handleGet(REWRITE_PATH + "/interface/roomStateStart.html?id=" + id + "&time=" + (time == null ? "" : time));
                        write(str);
                    }
                    return;
                } else if (room.getOnline() == 0) {
                    Date update = null;
                    if (time == null) {
                        update = new Date();
                    } else {
                        update = new Date(time * 1000);
                    }

                    Boolean apnsSend = false;
                    if (room.getUpdateTime() != null && (update.getTime() - room.getUpdateTime().getTime()) > 30 * 60 * 1000) {
                        //下线超过半小时，则进行推送
                        apnsSend = true;
                    }
                    room.setTimeLength(0);
                    room.setUpdateTime(update);
                    room.setOnline(1);

                    //主播开播记录
                    LanshaLiveHistory liveHistory = new LanshaLiveHistory();
                    liveHistory.setId(UUIDUtils.newId());
                    liveHistory.setRoomId(room.getId());
                    liveHistory.setLength(0);
                    liveHistory.setType("1");
                    liveHistory.setCreateTime(update);
                    liveHistory.setStartTime(update);
                    //修改状态
                    int count = ywUserRoomService.updateState(room, liveHistory);
                    if (count > 0) {
                        //消息推送
                        if (apnsSend) {
                            messageSend(room);
                        }
                    }
                    //从流中截屏(1秒以后开始截图, 避免截图灰色)
                    MasterStateService.liveToPic(room, 10);
                    //从流中截屏(截图两次，第二次10秒以后开始截图, 避免截图到蓝鲨录)
                    MasterStateService.liveToPic(room, 100);

                    //记录日志
                    LogUtil.log("FMS.STATE", "success:/interface/roomStateStart.html?id=", id, ",time=", time, ",count=", count);
                } else {
                    //记录日志
                    LogUtil.log("FMS.STATE", "error room is null:/interface/roomStateStart.html?id=", id, ",time=", time);
                }
            }
            write("1");
        } catch (Exception e) {
            write("0");
            throw e;
        }
    }

    /**
     * 修改状态
     *
     * @throws IOException
     */
    public void stop() throws Exception {
        try {
            if (StringUtils.isNotBlank(id)) {
                YwUserRoom room = ywUserRoomService.getYwUserRoomByRoomId(id);
                if (room == null) {
                    if (StringUtils.isNotBlank(REWRITE_PATH)) {
                        //转发
                        String str = HttpUtil.handleGet(REWRITE_PATH + "/interface/roomStateStop.html?id=" + id + "&time=" + (time == null ? "" : time));
                        write(str);
                    }
                    return;
                } else if (room != null && room.getOnline() == 1) {
                    Date update = null;
                    if (time == null) {
                        update = new Date();
                    } else {
                        update = new Date(time * 1000);
                    }
                    // 直播时长
                    int dif = (int) (update.getTime() - room.getUpdateTime().getTime()) / 1000;
                    room.setTimeLength(dif);

                    room.setUpdateTime(update);
                    room.setOnline(0);

                    //主播开播记录
                    LanshaLiveHistory fistHistory = lanshaLiveHistoryService.getLanshaLiveHistoryByRoomId(room.getId());
                    if (fistHistory != null) {
                        fistHistory.setType("2");

                        // 根据开播日期获取最大时间
                        Date t = DateUtils.getEndDate(fistHistory.getStartTime());

                        if (update.getTime() <= t.getTime()) {
                            //不跨天
                            fistHistory.setEndTime(update);
                        } else {
                            // 夸天处理
                            // 设置第一天开播时长与结束时间
                            fistHistory.setEndTime(t);

                            // 获取两日期相隔天数
                            Long differenceDay = DateUtils.dateDiff(fistHistory.getStartTime(), update, "yyyy-MM-dd", "d");
                            // 处理两天以上
                            List<LanshaLiveHistory> liveHistoryList = new ArrayList<LanshaLiveHistory>();
                            if (differenceDay > 1) {
                                for (int i = 1; i < differenceDay; i++) {
                                    // 获取开播记录日期
                                    Date currentDate = DateUtils.getPlusDate(fistHistory.getStartTime(), i);
                                    // 构建开播记录
                                    LanshaLiveHistory history = new LanshaLiveHistory();
                                    history.setId(UUIDUtils.newId());
                                    history.setRoomId(room.getId());
                                    history.setType("1");
                                    history.setCreateTime(update);
                                    history.setStartTime(DateUtils.getStartDate(currentDate));
                                    history.setEndTime(DateUtils.getEndDate(currentDate));
                                    // 一天的秒数
                                    history.setLength(24 * 60 * 60);
                                    liveHistoryList.add(history);
                                }
                            }

                            // 最后一天开播记录
                            LanshaLiveHistory endHistory = new LanshaLiveHistory();
                            endHistory.setId(UUIDUtils.newId());
                            endHistory.setRoomId(room.getId());
                            endHistory.setType("1");
                            endHistory.setCreateTime(update);
                            endHistory.setStartTime(DateUtils.getStartDate(update));
                            endHistory.setEndTime(update);
                            endHistory.setLength((int) (endHistory.getEndTime().getTime() - endHistory.getStartTime().getTime()) / 1000);
                            liveHistoryList.add(endHistory);

                            // 保存数据
                            if (CollectionUtils.isNotEmpty(liveHistoryList)) {
                                for (LanshaLiveHistory temp : liveHistoryList) {
                                    lanshaLiveHistoryService.save(temp);
                                }
                            }
                        }
                        //计算第一天播放时长
                        fistHistory.setLength((int) (fistHistory.getEndTime().getTime() - fistHistory.getStartTime().getTime()) / 1000);
                    }

                    //修改状态
                    int count = ywUserRoomService.updateState(room, fistHistory);
                    if (count > 0) {

                    }
                    //记录日志
                    LogUtil.log("FMS.STATE", "success:/interface/roomStateStop.html?id=", id, ",time=", time, ",count=", count);
                } else {
                    //记录日志
                    LogUtil.log("FMS.STATE", "error room is null:/interface/roomStateStop.html?id=", id, ",time=", time);
                }
            }
            write("1");
        } catch (Exception e) {
            write("0");
            throw e;
        }
    }


    public void messageSend(final YwUserRoom ywUserRoom) throws Exception {
        AsynchronousService.submit(new ObjectCallable() {
            @Override
            public Object run() throws Exception {
                YwUserRoomRelation entity = new YwUserRoomRelation();
                entity.setRoomId(ywUserRoom.getId());
                entity.setStatus(LanshaConstant.ROOM_RELATION_STATUS_NORMAL);
                YwUser yw = ywUserService.getYwUserById(ywUserRoom.getUid());
                List<YwUserRoomRelation> list = ywUserRoomRelationService.getYwUserRoomRelationList(entity);
                for (YwUserRoomRelation roomRelation : list) {
                    YwUser ywUser = ywUserService.getYwUserById(roomRelation.getUid());
                    //对登陆的订阅用户发送app推送
                    if(StringUtils.isNotBlank(ywUser.getToken())){
                        //app推送
                        appsend(ywUser, yw.getNickname(), ywUserRoom.getName(), ywUserRoom.getId());
                    }
                }

                return null;
            }
        });
    }
    
    public static Boolean appsend(YwUser ywUser, String nickname, String roomName, String roomId) throws Exception{
    	if (StringUtils.isNotBlank(ywUser.getToken()) && "1".equals(ywUser.getPush())) {
            if (StringUtils.isNotEmpty(ywUser.getTokenId())&&ywUser.getOsType().equals(LanshaConstant.OSTYPE_ANDROID)) {
                UmengSend.push(ywUser.getTokenId(), roomName, nickname, roomId, "1");
            } else if (StringUtils.isNotEmpty(ywUser.getOsType())&&ywUser.getOsType().equals(LanshaConstant.OSTYPE_IOS)) {
                ApnsSend.push(nickname + " 开播了\n" + roomName, roomId, ApnsSend.getDefaultSound(), ywUser.getTokenId().replaceAll("-", ""), "1");
            }
            return true;
        }else {
			return false;
		}
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
