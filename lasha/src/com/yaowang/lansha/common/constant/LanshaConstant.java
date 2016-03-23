package com.yaowang.lansha.common.constant;

/**
 * @ClassName: LanshaConstant
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tandingbo
 * @date 2015年12月4日 下午5:19:24
 * 
 */
public class LanshaConstant {
	/**
	 * 用户类型-前台普通会员
	 */
	public static final Integer USER_TYPE_ORDINARY = 0;
	/**
	 * 用户类型-主播
	 */
	public static final Integer USER_TYPE_ANCHOR = 1;
	/**
	 * 状态-删除
	 */
	public static final Integer STATUS_DELETE = 0;
    /**
     * 状态-有效
     */
    public static final Integer STATUS_VAILD = 1;
	/**
	 * 状态-上线
	 */
	public static final Integer STATUS_ONLINE = 1;
	/**
	 * 状态-下线
	 */
	public static final Integer STATUS_OFFLINE = 2;
	/**
	 * 用户状态-删除
	 */
	public static final Integer USER_STATUS_DELETE = 0;
	/**
	 * 用户状态-正常
	 */
	public static final Integer USER_STATUS_NORMAL = 1;
	/**
	 * 用户状态-冻结
	 */
	public static final Integer USER_STATUS_FREEZE = 2;
	/**
	 * 用户状态-封号
	 */
	public static final Integer USER_STATUS_CLOSE = 3;

	// *****************************房间状态*****************************************//
	/**
	 * 状态-下线
	 */
	public static final Integer ROOM_STATUS_OFFLINE = 0;
	/**
	 * 状态-上线
	 */
	public static final Integer ROOM_STATUS_ONLINE = 1;
	/**
	 * 状态-禁播
	 */
	public static final Integer ROOM_STATUS_BANNED = 2;
	/**
	 * 状态-隐藏删除)
	 */
	public static final Integer ROOM_STATUS_DELETE = 4;

	// ******************* 主播审核状态 *******************/
	/**
	 * 设置审核状态-未审核
	 */
	public final static String MASTER_STATUS_AUDITING = "0";
	/**
	 * 设置审核状态-审核通过
	 */
	public final static String MASTER_STATUS_VETTED = "1";
	/**
	 * 设置审核状态-审核不通过
	 */
	public final static String MASTER_STATUS_NOTBY = "2";

	/**
	 * 类型(蓝鲨币)
	 */
	public final static String RECORD_OBJECT_TYPE_1 = "1";
	/**
	 * 类型(礼物)
	 */
	public final static String RECORD_OBJECT_TYPE_2 = "2";
	/**
	 * 类型(收入)
	 */
	public final static String INCOME = "1";
	/**
	 * 类型(支出)
	 */
	public final static String EXPEND = "2";
	/**
	 * 默认礼物id(虾米)
	 */
	public final static String GIFT_ID = "00000000000000000000000000000001";
	public final static String GIFT_NAME = "个虾米";

    public  final static  String GIFT_TICKET_ID="0001";
    public  final static  String GIFT_TICKET_NAME="张日票";
	/**
	 * 领到的鲜花
	 */
	public final static String GIFT_ID_TWO = "00000000000000000000000000000002";
	public final static String GIFT_NAME_TWO = "朵鲜花";
	public final static String GIFT_PARENT_CLASS_TWO = "talk_gift";
	public final static String GIFT_CLASS_TWO = "icon_flower";
	
	/**
	 * 收到的鲜花
	 */
	public final static String GIFT_ID_THREE = "00000000000000000000000000000003";
	
	/**
	 * 日票
	 */
	public final static String GIFT_ID_FOUR = "00000000000000000000000000000004";
	
	/**
	 * 收到的日票
	 */
	public final static String GIFT_ID_FIVE = "00000000000000000000000000000005";
	/**
	 * （王者荣耀）默认礼物1个Q币id
	 */
	public final static String Q_GIFT_ID = "00000000000000000000000000002011";

	/**
	 * （穿越火线）默认礼物1个Q币id
	 */
	public final static String C_GIFT_ID = "00000000000000000000000000003011";
	
    /**
     * 历史注册送礼物活动的礼物ID
     */
	public static final String[] QB_ACTIVITY_GIFT_ID = new String[]{
			"00000000000000000000000000002004",
    		LanshaConstant.Q_GIFT_ID
    };
	
	/**
	 * 默认活动id
	 */
	public final static String ITEM_ID = "00000000000000000000000000000001";
	/**
	 * 第二次活动id
	 */
	public final static String ITEM_ID_TWO = "00000000000000000000000000000002";
	/**
	 * cf默认礼物(2个Q币)
	 */
	public final static String DEFAULT_GIFT_ID_THIRD = "00000000000000000000000000003004";
	/**
	 * 第三次活动id
	 */
	public final static String ITEM_ID_THIRD = "00000000000000000000000000000003";

	/**
	 * 礼物类型名称-谢谢惠顾
	 */
	public final static String GIFT_TYPE_NEXT = "0";
	/**
	 * 礼物类型-蓝鲨币
	 */
	public final static String GIFT_TYPE_SB = "1";
	/**
	 * 礼物类型-礼包
	 */
	public final static String GIFT_TYPE_GIFTS = "2";
	/**
	 * 礼物类型-充值卡
	 */
	public final static String GIFT_TYPE_CARD = "3";
	/**
	 * 礼物类型-实物
	 */
	public final static String GIFT_TYPE_KIND = "4";

	// ******************* 分享状态 *******************/
	/**
	 * 分享状态-APP
	 */
	public final static String SHARE_TYPE_APP = "1";
	/**
	 * 分享状态-房间
	 */
	public final static String SHARE_TYPE_ROOM = "2";
	/**
	 * 微信分享功能开启/关闭控制
	 */
	public final static String WECHAT_SHARE_FUNCTION_ON = "ON";
	/**
	 * 操作系统-android
	 */
	public final static String OSTYPE_ANDROID = "1";
	/**
	 * 操作系统-ios
	 */
	public final static String OSTYPE_IOS = "2";
	/**
	 * 房间关注状态
	 */
	public final static int ROOM_RELATION_STATUS_NORMAL = 1;
	/**
	 * 房间最大管理员上限KEY
	 */
	public final static String ROOM_ADMIN_NUM_MAX = "ROOM.ADMIN.NUM.MAX";

    public final static String LANSHA_TICKET_KEY ="ticket_key";
    public final static String LANSHA_TICKET_KEY_ZADD ="ticket_key_zadd";
    
    //热门推荐
    public final static Integer LANSHA_HOT_ANCHOR = 1;
    
    //大神主播
    public final static Integer LANSHA_BEST_ANCHOR = 2;
    
    //美女主播
    public final static Integer LANSHA_GIRL_ANCHOR = 3;
    
    //推广页Cookie标志
    public final static String LANSHA_ACTIVITY_SHOW = "ACTIVITY_TO_SHOW";
    public final static String LANSHA_ACTIVITY_TO_LIVE = "ACTIVITY_TO_LIVE";
    public final static String LANSHA_ACTIVITY_TO_DETAIL = "ACTIVITY_TO_DETAIL";
    public final static String LANSHA_ACTIVITY_TO_LD = "LANSHA_ACTIVITY_TO_LD";

    public final static String LANSHA_ACTIVITY_TO_LIVE_END = "GAMEOVER";  
    
    /**
	 * 蓝鲨CNZZ统计代码数据字典
	 */
	public static final String SYSTEM_MCODE_LANSHA_CNZZ_CODE = "LANSHA.CNZZ.CODE";
	/**
	 * 默认埋码
	 */
	public static final String SYSTEM_MCODE_LANSHA_CNZZ_CODE_ALL_PAGES = "ALL_PAGES";
}
