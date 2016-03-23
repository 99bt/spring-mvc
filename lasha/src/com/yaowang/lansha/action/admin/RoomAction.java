package com.yaowang.lansha.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.action.BasePageAction;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwUser;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.entity.YwUserRoomHot;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwUserRoomHotService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.lansha.service.YwUserService;
import com.yaowang.util.UUIDUtils;

/**
 * @ClassName: RoomAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tandingbo
 * @date 2015年12月4日 下午2:14:33
 * 
 */
public class RoomAction extends BasePageAction {
	private static final long serialVersionUID = -4231419910668657730L;

	@Resource
	private YwUserRoomService ywUserRoomService;
	@Resource
	private YwGameService ywGameService;
	@Resource
	private YwUserService ywUserService;
	@Resource
	private YwUserRoomHotService ywUserRoomHotService;

	private YwUserRoom entity;
	private YwUserRoomHot roomHot;
	private List<Map<String, Object>> listMapRootHot;

	private String roomName;
	private String username;
	private String gameName;
	private String idInt;
	private Integer hot;
	private String sign;//是否签约
    
	private Integer num;

	private String reason;//禁播原因

    private Integer type;//类型
	/**
	 * @Description: 列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		YwUserRoom room = new YwUserRoom();
		if (StringUtils.isNotBlank(idInt)) {
			if (idInt.length() > 7) {
				//不存在7位以上的房间id
				return SUCCESS;
			}
			if(idInt.matches("\\d+")){
				room.setIdInt(Integer.valueOf(idInt));
			}else{
				room.setIdInt(0);
			}
		}
		
		room.setName(roomName);
		room.setGameName(gameName);
		room.setUsername(username);
		room.setSign(sign);
		room.setOrderSql("id_int desc");
		list = ywUserRoomService.getYwUserRoomList(room, null, getPageDto(), null, true);
		if (CollectionUtils.isNotEmpty(list)) {
			ywUserRoomService.setGameName((List<YwUserRoom>) list);
			ywUserRoomService.setUserName((List<YwUserRoom>) list);
			ywUserRoomService.setIsHotRoom((List<YwUserRoom>) list);
            ywUserRoomService.setNvShenRoom((List<YwUserRoom>) list);
            ywUserRoomService.setDaShenRoom((List<YwUserRoom>) list);
		}
		return SUCCESS;
	}

	/**
	 * @Description: 详情
	 * @return
	 */
	public String detail() {
		if (StringUtils.isBlank(id)) {
			entity = new YwUserRoom();
			entity.setBaseNumber(0);// 基数
			entity.setMultipleNumber(1);// 倍数
		} else {
			entity = ywUserRoomService.getYwUserRoomById(id);
			YwGame ywGame = ywGameService.getYwGameById(entity.getGameId());
			if (ywGame != null) {
				entity.setGameName(ywGame.getName());
			}
			YwUser user = ywUserService.getYwUserById(entity.getUid());
			if (user != null) {
				entity.setNickname(user.getNickname());
			}
		}
		return SUCCESS;
	}

	/**
	 * @Description: 保存
	 * @return
	 */
	public String save() {
		try {
			validateInfo(entity);
		} catch (Exception e) {
			addActionError(e.getMessage());
			return ERROR;
		}

		if (StringUtils.isBlank(entity.getId())) {
			// 新增
			entity.setId(UUIDUtils.newId());
			entity.setCreateTime(getNow());
			entity.setNumber(0);
			if (entity.getOrderId() == null) {
				entity.setOrderId(0);
			}
			entity.setFans(0);
			entity.setReportNum(0);
			entity.setTimeLength(0);
			ywUserRoomService.save(entity);
		} else {
			// 修改
			entity.setUpdateTime(getNow());
			ywUserRoomService.update(entity);
		}

		list();
		return SUCCESS;
	}

	/**
	 * @Description: 获取游戏
	 * @return
	 */
	public String game() {
		PageDto dto = getPageDto();
		dto.setCount(false);
		dto.setRowNum(5);

		YwGame game = new YwGame();
		game.setName(name);
		game.setStatus(LanshaConstant.STATUS_ONLINE);

		list = ywGameService.getYwGamePage(game, null, dto, null, null, null);
		if (CollectionUtils.isEmpty(list)) {
			dto.setCurrentPage(dto.getCurrentPage() - 1);
			list = ywGameService.getYwGamePage(game, null, dto, null, null, null);
		}
		return SUCCESS;
	}

	/**
	 * @Description: 获取主播信息
	 */
	public void search() {
		JSONObject jsonData = new JSONObject();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (!StringUtils.isBlank(name)) {
			data = ywUserService.getListMapAnchor(name, LanshaConstant.USER_TYPE_ANCHOR, BaseConstant.YES);
		}
		jsonData.put("data", data);
		try {
			write(jsonData.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void validateInfo(YwUserRoom entity) throws Exception {
		if (StringUtils.isBlank(entity.getUid())) {
			throw new RuntimeException("主播不存在");
		}
		if (StringUtils.isBlank(entity.getName())) {
			throw new RuntimeException("房间名称不能为空");
		}
		if (entity.getGameId() == null) {
			throw new RuntimeException("游戏不能为空");
		}
		if (entity.getIntro() != null && entity.getIntro().length() > 200) {
			throw new RuntimeException("房间介绍内容最多只能输入200字");
		}
		if (entity.getNotice() != null && entity.getNotice().length() > 200) {
			throw new RuntimeException("房间公告内容最多只能输入200字");
		}
	}

	/**
	 * @Description: 更新观众基数
	 * @return
	 */
	public String updateBasenum() {
		if (ids == null || ids.length < 1) {
			addActionError("请选择一条记录");
			return SUCCESS;
		}
		ywUserRoomService.updateBasenum(ids,num);
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 更新观众倍数
	 * @return
	 */
	public String updateMultiplenum() {
		if (ids == null || ids.length < 1) {
			addActionError("请选择一条记录");
			return SUCCESS;
		}
		ywUserRoomService.updateMultiplenum(ids, num);
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 删除
	 * @return
	 */
	public String delete() {
		if (ids == null || ids.length < 1) {
			addActionError("请选择一条记录");
			return SUCCESS;
		}
		ywUserRoomService.updateBySql(ids, LanshaConstant.ROOM_STATUS_DELETE,null);
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 禁播
	 * @return
	 */
	public String banned() {
		try {
			bannedOrLaunchHelper(LanshaConstant.ROOM_STATUS_BANNED,reason);
			//禁播新增禁播原因
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 开播
	 * @return
	 */
	public String launch() {
		try {
			bannedOrLaunchHelper(LanshaConstant.ROOM_STATUS_OFFLINE,"");
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		list();
		return SUCCESS;
	}

	/**
	 * @Description: 禁播、开播辅助类
	 * @param status
	 * @throws Exception
	 */
	private void bannedOrLaunchHelper(Integer status,String remarks) throws Exception {
		if (ids == null && ids.length < 1) {
			throw new RuntimeException("请选择一条记录");
		}
		ywUserRoomService.updateBySql(ids, status,remarks);
	}

	/**
	 * @Description: 设置为热门房间
	 * @return
	 */
	public void setupHot() {
		if (StringUtils.isBlank(id)) {
			return;
		}
        if (type==null) {
            return;
        }
		// 获取房间信息
		YwUserRoomHot ywUserRoomHot = ywUserRoomHotService.getYwUserRoomHotByRoomId(id,type);
		if (BaseConstant.NO == hot) {
			if (ywUserRoomHot != null) {
				// 删除热门房间
				ywUserRoomHotService.delete(new String[] { ywUserRoomHot.getId() });
			}
		} else if (BaseConstant.YES == hot) {
			if (ywUserRoomHot == null) {
				// 保存热门房间
				YwUserRoomHot userRoomHot = new YwUserRoomHot();
				userRoomHot.setId(UUIDUtils.newId());
				userRoomHot.setOrderId(0);
				userRoomHot.setRoomId(id);
                userRoomHot.setType(type);
				userRoomHot.setCreateTime(getNow());
				ywUserRoomHotService.save(userRoomHot);
			}
		}
	}

	/**
	 * @Description: 列表热门房间
	 * @return
	 */
	public String listHot() {
		listMapRootHot = ywUserRoomHotService.listMapRootHot(1,getPageDto());
		if (CollectionUtils.isNotEmpty(listMapRootHot)) {
			ywUserRoomHotService.putName(listMapRootHot);
		} else {
			listMapRootHot = new ArrayList<Map<String, Object>>();
		}
		return SUCCESS;
	}

	/**
	 * @Description: 热门房间详情
	 * @return
	 */
	public String detailHot() {
		if (StringUtils.isBlank(id)) {
			roomHot = new YwUserRoomHot();
		} else {
			roomHot = ywUserRoomHotService.getYwUserRoomHotById(id);
			YwUserRoom ywUserRoom = ywUserRoomService.getYwUserRoomById(roomHot.getRoomId());
			if (ywUserRoom != null) {
				roomHot.setRoomName(ywUserRoom.getName());
			}
		}
		return SUCCESS;
	}

	/**
	 * @Description: 保存热门房间
	 * @return
	 */
	public String saveHot() {
		if (StringUtils.isBlank(roomHot.getRoomId())) {
			addActionError("请选择房间");
			return ERROR;
		}
		YwUserRoomHot ywUserRoomHot = ywUserRoomHotService.getYwUserRoomHotByRoomId(roomHot.getRoomId(),1);
		if (StringUtils.isBlank(roomHot.getId())) {
			// 新增
			if (ywUserRoomHot != null) {
				addActionError("房间已经是热门房间，请换一个房间");
				return ERROR;
			}
			roomHot.setId(UUIDUtils.newId());
			roomHot.setCreateTime(getNow());
			ywUserRoomHotService.save(roomHot);
		} else {
			// 修改
			if (!roomHot.getId().equals(ywUserRoomHot.getId())) {
				addActionError("房间已经是热门房间，请换一个房间");
				return ERROR;
			}
			ywUserRoomHotService.update(roomHot);
		}
		listHot();
		addActionMessage("保存成功");
		return SUCCESS;
	}

	/**
	 * @Description: 删除热门房间
	 * @return
	 */
	public String deleteHot() {
		if (ids != null && ids.length > 0) {
			ywUserRoomHotService.delete(ids);
		}
		listHot();
		addActionMessage("删除成功");
		return SUCCESS;
	}

	/**
	 * @Description: 获取房间信息
	 * @return
	 */
	public void searchHot() {
		JSONObject jsonData = new JSONObject();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (!StringUtils.isBlank(name)) {
			List<Integer> listInStatus = new ArrayList<Integer>();
			listInStatus.add(LanshaConstant.ROOM_STATUS_OFFLINE);
			listInStatus.add(LanshaConstant.ROOM_STATUS_ONLINE);
			data = ywUserRoomService.getListMapRoomInfo(name, listInStatus);
		}
		jsonData.put("data", data);
		try {
			write(jsonData.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
     * @Description: 列表大神房间
     * @return
     */
    public String listDaShen() {
        listMapRootHot = ywUserRoomHotService.listMapRootHot(2,getPageDto());
        if (CollectionUtils.isNotEmpty(listMapRootHot)) {
            ywUserRoomHotService.putName(listMapRootHot);
        } else {
            listMapRootHot = new ArrayList<Map<String, Object>>();
        }
        return SUCCESS;
    }

    /**
     * @Description: 保存大神房间
     * @return
     */
    public String saveDaShen() {
        if (StringUtils.isBlank(roomHot.getRoomId())) {
            addActionError("请选择房间");
            return ERROR;
        }
        YwUserRoomHot ywUserRoomHot = ywUserRoomHotService.getYwUserRoomHotByRoomId(roomHot.getRoomId(),2);
        if (StringUtils.isBlank(roomHot.getId())) {
            // 新增
            if (ywUserRoomHot != null) {
                addActionError("房间已经是大神房间，请换一个房间");
                return ERROR;
            }
            roomHot.setId(UUIDUtils.newId());
            roomHot.setCreateTime(getNow());
            ywUserRoomHotService.save(roomHot);
        } else {
            // 修改
            if (!roomHot.getId().equals(ywUserRoomHot.getId())) {
                addActionError("房间已经是大神房间，请换一个房间");
                return ERROR;
            }
            ywUserRoomHotService.update(roomHot);
        }
        listDaShen();
        addActionMessage("保存成功");
        return SUCCESS;
    }
    /**
     * @Description: 删除大神房间
     * @return
     */
    public String deleteDaShen() {
        if (ids != null && ids.length > 0) {
            ywUserRoomHotService.delete(ids);
        }
        listDaShen();
        addActionMessage("删除成功");
        return SUCCESS;
    }

    /**
     * @Description: 列表女神房间
     * @return
     */
    public String listNvShen() {
        listMapRootHot = ywUserRoomHotService.listMapRootHot(3,getPageDto());
        if (CollectionUtils.isNotEmpty(listMapRootHot)) {
            ywUserRoomHotService.putName(listMapRootHot);
        } else {
            listMapRootHot = new ArrayList<Map<String, Object>>();
        }
        return SUCCESS;
    }



    /**
     * @Description: 保存女神房间
     * @return
     */
    public String saveNvShen() {
        if (StringUtils.isBlank(roomHot.getRoomId())) {
            addActionError("请选择房间");
            return ERROR;
        }
        YwUserRoomHot ywUserRoomHot = ywUserRoomHotService.getYwUserRoomHotByRoomId(roomHot.getRoomId(),3);
        if (StringUtils.isBlank(roomHot.getId())) {
            // 新增
            if (ywUserRoomHot != null) {
                addActionError("房间已经是女神房间，请换一个房间");
                return ERROR;
            }
            roomHot.setId(UUIDUtils.newId());
            roomHot.setCreateTime(getNow());
            ywUserRoomHotService.save(roomHot);
        } else {
            // 修改
            if (!roomHot.getId().equals(ywUserRoomHot.getId())) {
                addActionError("房间已经是女神房间，请换一个房间");
                return ERROR;
            }
            ywUserRoomHotService.update(roomHot);
        }
        listNvShen();
        addActionMessage("保存成功");
        return SUCCESS;
    }

    /**
     * @Description: 删除女神房间
     * @return
     */
    public String deleteNvShen() {
        if (ids != null && ids.length > 0) {
            ywUserRoomHotService.delete(ids);
        }
        listNvShen();
        addActionMessage("删除成功");
        return SUCCESS;
    }



    public YwUserRoom getEntity() {
		return entity;
	}

	public void setEntity(YwUserRoom entity) {
		this.entity = entity;
	}

	public YwUserRoomHot getRoomHot() {
		return roomHot;
	}

	public void setRoomHot(YwUserRoomHot roomHot) {
		this.roomHot = roomHot;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public List<Map<String, Object>> getListMapRootHot() {
		return listMapRootHot;
	}

	public void setListMapRootHot(List<Map<String, Object>> listMapRootHot) {
		this.listMapRootHot = listMapRootHot;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdInt() {
		return idInt;
	}

	public void setIdInt(String idInt) {
		this.idInt = idInt;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
