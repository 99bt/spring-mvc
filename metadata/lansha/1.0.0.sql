-- 直播日志
alter table yw_user_room_history add(type_id char(32) null comment '类型id(蓝鲨币为空)');
-- 广告增加大图
alter table yw_banner add(big_img varchar(100)	null comment '大图地址');
-- 用户推送字段
alter table yw_user add(push char(1) null comment '全局推送开关(0:否拒绝,1:是接受)');

-- 主播开播记录
create table lansha_live_history(
	id 			char(32) 		not null,
	room_id		char(32) 		not null comment '房间id',
	start_time	datetime		null comment '开播时间',
	end_time	datetime		null comment '结束时间',
	length		integer			null comment '在线时间',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '主播开播记录';

-- 礼物
create table lansha_gift (
	id 			char(32) 		not null,
	name		varchar(50)		not null comment '礼物名称',
	status		char(1)			not null comment '0:停用，1:启用',
	bi			integer			not null comment '1个礼物对应蓝鲨币',
	img			varchar(100)	null comment '图标地址',
	orderid		integer			null comment '排序号',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '礼物';

-- 送礼物记录
create table lansha_gift_user (
	id 			char(32) 		not null,
	gift_id		char(32) 		not null comment '礼物id',
	user_id		char(32) 		not null comment '赠送人id',
	anchor_id	char(32) 		not null comment '主播id',
	number		integer			not null comment '礼物数',
	bi			integer			not null comment '消耗蓝鲨币',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '送礼物记录';

-- 用户礼物库存表
create table lansha_user_gift_stock (
	id 			char(32) 		not null,
	user_id		char(32) 		not null comment '用户id',
	gift_id		char(32) 		not null comment '礼物id',
	stock		integer			not null comment '库存',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '用户礼物库存表';

-- 用户收支记录
create table lansha_user_record (
	id 			char(32) 		not null,
	user_id		char(32) 		not null comment '用户id',
	type		char(1)			not null comment '1:收入,2:支出',
	object_type	char(1)			not null comment '类型(1:蓝鲨币,2:礼物)',
	object_id	char(32)		null comment '类型id',
	stock		integer			null comment '量',
	remark		varchar(200)	null comment '备注',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '用户收支记录';

-- 意见反馈
create table lansha_suggestion(
	id 				char(32)	 	not null,
	user_id			char(32)		null comment '用户id',
	ip				varchar(20)		not null comment '用户ip',
	content			text			null comment '提交内容',
	create_time		datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment='意见反馈';

-- app版本表
create table lansha_version (
    id          char(32) 	not null,
    os_type		char(1)		not null comment '操作系统',
    version		varchar(10)	not null comment '版本号',
    name		varchar(30)	not null comment '包名',
    size		varchar(10) not null comment '大小',
    update_log	text 		not null comment '更新日志',
    is_force	char(1) 	default '0' comment '是否强制更新 0否1是',
    online_time datetime	not null comment '上线时间',
    status 		char(1) 	default '1' comment '状态 0删除1正常',
    address 	varchar(200) null comment '地址',
    
    primary key  (id)
) engine=innodb default charset=utf8 comment='app版本表';

-- 前台用户登录日志
create table log_user_login (
	id              char(32)		not null,
	user_id			char(32)		not null,			-- 用户id
	login_time		datetime		null,				-- 最后登录时间
	login_ip		varchar(100)	null,				-- 最后登录ip

	primary key (id)
)engine=innodb default charset=utf8;

-- 第三方登录绑定
create table yw_user_band(
	id          char(32)	not null,
	type		char(1)		null comment '类型（1:qq，2:sina weibo）',
	open_id		varchar(32)	null comment 'open id',
	uid			int(11) 	null comment '用户id',
	create_time	datetime	null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8;

-- 礼物虾米
insert into lansha_gift(id, name, status, bi, img, orderid, create_time)
value('00000000000000000000000000000001', '虾米', '1', 1, '', 1, now());

-- 礼物模块
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000020004', '00000000000000000000000000000002', '礼物管理', '/admin/gift/gift.html', '1', '4', now());

-- 直播统计报表
delete from sys_model where id = '00000000000000000000000000000005' or parent_id = '00000000000000000000000000000005';
insert into sys_model(id, parent_id, name, img, is_use, order_id, create_time)
value('00000000000000000000000000000005', '00000000000000000000000000000000', '统计报表', '/static/images/leftico01.png', '1', '5', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000050001', '00000000000000000000000000000005', '直播报表', '/admin/report/live.html', '1', '1', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000050002', '00000000000000000000000000000005', '意见反馈报表', '/admin/report/suggestion.html', '1', '2', now());


-- 热词搜索
insert into sys_mcode_list(id, name, length, type, remark, is_using, this_id) 
value('00000000000000000000000000000001', 'APP_SEARCH', '1', '1', 'app-热词搜索', '1', 'APP_SEARCH');
-- app版本
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000010005', '00000000000000000000000000000001', 'app版本管理', '/admin/lansha/version.html', '1', '5', now());

insert into sys_mcode_detail(id, list_id, this_id, content, is_using, mcode_type, display_order) values 
('00000000000000000000000000001001', 'APP_SEARCH', '1', '刀剑如梦', '1', '1', '1'),
('00000000000000000000000000001002', 'APP_SEARCH', '2', '天涯明月刀', '1', '1', '2'),
('00000000000000000000000000001003', 'APP_SEARCH', '3', '天下', '1', '1', '3'),
('00000000000000000000000000001004', 'APP_SEARCH', '4', '琅琊榜', '1', '1', '4'),
('00000000000000000000000000001005', 'APP_SEARCH', '5', '怪物猎人开拓记', '1', '1', '5');

-- 短信类型
insert into sys_option_type(id, name, orderid)
value('00000000000000000000000000000099', '短信参数', 99);

update sys_option set id = '00000000000000000000000000099001', type_id = '00000000000000000000000000000099', orderid = 1 where iniid = 'SYS.MT.USE';
update sys_option set id = '00000000000000000000000000099003', type_id = '00000000000000000000000000000099', orderid = 3 where iniid = 'SYS.MT.SERVER';
update sys_option set id = '00000000000000000000000000099004', type_id = '00000000000000000000000000000099', orderid = 4 where iniid = 'SYS.MT.OPEN_ID';
update sys_option set id = '00000000000000000000000000099005', type_id = '00000000000000000000000000000099', orderid = 5 where iniid = 'SYS.MT.OPEN_PASS';

insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099002', 'SYS.MT.TYPE', 1, '短信发送网关(1:企信通平台,2:创蓝短信)', '1', '1', 2, '00000000000000000000000000000099');

-- 创蓝短信发送服务器
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099006', 'SYS.MT.SERVER.LC', 1, '短信服务-发送服务器(创蓝)', 'http://222.73.117.158', 'http://222.73.117.158', 6, '00000000000000000000000000000099');
-- 短信服务账户
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099007', 'SYS.MT.OPEN_ID.LC', 1, '短信服务-账户(创蓝)', 'jiekou-clcs-11', 'jiekou-clcs-11', 7, '00000000000000000000000000000099');
-- 短信服务密码
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099008', 'SYS.MT.OPEN_PASS.LC', 1, '短信服务-密码(创蓝)', 'Tch123123', 'Tch123123', 8, '00000000000000000000000000000099');

-- 通用验证码
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000212', 'SYS.COMMON.CODE', 1, '通用验证码', 'ywwl', 'java', 14, '00000000000000000000000000000002');
