-- 会员上级
alter table yw_user add(parent_id char(32) null comment '推广上级会员');

-- 活动
create table activity_item (
	id 			char(32) 		not null,
	name		varchar(100)	null comment '活动名称',
	start_time	datetime		null comment '开始时间',
	status		char(1)			null comment '状态(0:下线,1:在线)',
	index_url	varchar(100)	null comment '活动主页',
	middle_gift	float			null comment '每人奖品价值降低概率值',
	max_gift	float			null comment '每人奖品价值上限',
	remark		varchar(200)	null comment '备注',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '活动';

-- 活动礼品
create table activity_gift (
	id 			char(32) 		not null,
	item_id		char(32) 		not null comment '活动id',
	name		varchar(100)	null comment '礼品名称',
	status		char(1)			null comment '状态(0:下线,1:在线)',
	img			varchar(100)	null comment '礼品图标',
	order_id	integer			not null default 0 comment '排序号',
	remark		varchar(200)	null comment '备注',
	create_time	datetime		null comment '创建时间',
	money		float			null comment '价值',
	type		char(1)			not null comment '类型(0:谢谢惠顾,1:蓝鲨币,2:礼包,3:充值卡,4:实物)',
	bi			integer			not null comment '奖品数量',
	object_id	char(32)		null comment '礼包id',
	
	number		integer			not null default 0 comment '总数量',
	stock		integer			not null default 0 comment '库存',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '活动礼品';

-- 活动礼品库存
create table activity_gift_stock(
	id 			char(32) 		not null,
	item_id		char(32) 		not null comment '活动id',
	gift_id		char(32) 		not null comment '礼品id',
	user_id		char(32) 		not null comment '领取人id',
	status		char(1)			not null comment '状态(0:等待发货,1:已发货，2：审核不通过)',
	type		char(1)			not null comment '类型(0:谢谢惠顾,1:蓝鲨币,2:礼包,3:充值卡,4:实物)',
	admin_id	char(32)		null comment '操作人',
	create_time	datetime		null comment '中奖时间',
	remark		varchar(200)	null comment '备注',
	ip			varchar(100)	null comment 'ip',

	primary key (id)
)engine=innodb default charset=utf8 comment '活动礼品库存';

-- 用户抽奖机会
create table activity_user (
	id 			char(32) 		not null comment '用户id',
	realname	varchar(50)		null comment '收件人',
	address		varchar(200)	null comment '收货地址',
	telphone	varchar(20)		null comment '联系电话',
	last_activity	datetime		null comment '上次抽奖时间',
	stock		integer			not null default 0 comment '抽奖次数',
	create_time	datetime		null,

	primary key (id)
)engine=innodb default charset=utf8 comment '用户抽奖机会';

-- 抽奖机会抽取日志
create table activity_user_log (
	id 			char(32) 		not null,
	user_id		char(32) 		not null comment '领取人id',
	stock		integer			not null default 1 comment '抽奖次数',
	type		char(1)			not null comment '类型(1:推荐注册,2:连续登陆)',
	remark		varchar(200)	null comment '备注',
	create_time	datetime		null comment '领取时间',

	primary key (id)
)engine=innodb default charset=utf8 comment '抽奖机会抽取日志';

-- 活动
insert into activity_item(id, name, start_time, status, create_time) value('00000000000000000000000000000001', '2015第一次活动', now(), '1', now());
-- 礼品
insert into activity_gift(id, item_id, name, status, order_id, create_time, money, type, object_id, bi, number, stock) values
('00000000000000000000000000000001', '00000000000000000000000000000001', '5个虾米', '1', 2, now(), 0.03, '2', '00000000000000000000000000000001', 5, 100000, 100000),
('00000000000000000000000000000002', '00000000000000000000000000000001', '15个虾米', '1', 5, now(), 0.2, '2', '00000000000000000000000000000001', 15, 80000, 80000),
('00000000000000000000000000000003', '00000000000000000000000000000001', '10元移动充值卡', '1', 8, now(), 10, '3', '', 1, 1000, 1000),
('00000000000000000000000000000004', '00000000000000000000000000000001', '30元移动充值卡', '1', 3, now(), 30, '3', '', 1, 300, 300),
('00000000000000000000000000000005', '00000000000000000000000000000001', '100元移动充值卡', '1', 6, now(), 100, '3', '', 1, 200, 200),
('00000000000000000000000000000006', '00000000000000000000000000000001', '玩偶', '1', 4, now(), 30, '4', '', 1, 50, 50),
('00000000000000000000000000000007', '00000000000000000000000000000001', '拍立得', '1', 7, now(), 500, '4', '', 1, 10, 10),
('00000000000000000000000000000008', '00000000000000000000000000000001', 'iPhone6s', '1', 1, now(), 6000, '4', '', 1, 3, 3);


-- 活动管理
delete from sys_model where id = '00000000000000000000000000000006' or parent_id = '00000000000000000000000000000006';
insert into sys_model(id, parent_id, name, img, is_use, order_id, create_time)
value('00000000000000000000000000000006', '00000000000000000000000000000000', '活动管理', '/static/images/leftico01.png', '1', '6', now());
-- 活动管理
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000060001', '00000000000000000000000000000006', '活动管理', '/admin/activity/activity.html', '1', '1', now());
-- 礼物发放
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000060002', '00000000000000000000000000000006', '礼物发放', '/admin/activity/gift.html', '1', '2', now());
-- 用户抽奖
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000060003', '00000000000000000000000000000006', '用户抽奖', '/admin/activity/user.html', '1', '3', now());
-- 礼物报表
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000060004', '00000000000000000000000000000005', '礼物报表', '/admin/report/giftReport.html', '1', '3', now());
-- 注册人数报表
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000050003', '00000000000000000000000000000005', '注册人数', '/admin/report/regiest.html', '1', '3', now());

-- 抽奖时间间隔限制
insert into sys_option_type(id, name, orderid)
value('00000000000000000000000000000003', '活动参数', 3);
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000301', 'ACTIVITY.LIMIT.TIME', 1, '抽奖最少时间间隔(秒)', '15', '15', 11, '00000000000000000000000000000003');

-- 蓝鲨广告
create table lansha_advertisement (
	id 			char(32) 		null,
	name		varchar(50)		null comment '名称',
	link		varchar(100)	null comment '链接地址',
	img			varchar(100)	null comment '图片地址',
	type		char(1)			not null comment '类型(1:房间广告)',
	rate		float			null comment '概率(0-10,越接近10权重越高)',
	status		char(1)			null comment '状态(0:下线,1:在线)',
	remark		varchar(200)	null comment '备注',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment '蓝鲨广告';

-- 直播间广告
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000030004', '00000000000000000000000000000003', '直播间广告', '/admin/room/advertisement.html', '1', '4', now());

-- 视频播放缓冲时间
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000213', 'LANSHA.PLAY.CACHE', 1, '视频播放缓冲时间(秒)', '3', '3', 13, '00000000000000000000000000000002');

-- 页脚
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000214', 'LANSHA.FOOT.ABOUT', 1, '前台页脚', '©2015 中国蓝鲨网络科技有限公司 版权所有 备案号', '©2015 中国蓝鲨网络科技有限公司 版权所有 备案号', 14, '00000000000000000000000000000002');

-- 切换流地址
update sys_option set nowvalue = 'rtmp://live.9d6d.com/live/' where iniid = 'FMS.RTMP';
update yw_user_room set rtmp = (select nowvalue from sys_option where iniid = 'FMS.RTMP');

update sys_option set nowvalue = 'rtmp://play.9d6d.com/live/' where iniid = 'FMS.RTMP.LIVE';
update yw_user_room set live_host = (select nowvalue from sys_option where iniid = 'FMS.RTMP.LIVE');
