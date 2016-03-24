-- 有效直播时长区间(主播工资计算)
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000225', 'LANSHA.ROOM.VALID.TIME', 1, '有效直播时长区间(主播工资计算)','8,24', '8,24', 25, '00000000000000000000000000000002');

alter table yw_user_room_day_data add live_time int(11) default 0  comment '有效直播时长（分）';
-- 游戏seo内容字段
alter table yw_game add seo varchar(500) null comment '游戏seo内容';

insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000226', 'GAME.DOWNLOAD.SWITCH', 1, '游戏是否可以下载（1:是 0:否）', '1', '1', 26, '00000000000000000000000000000002');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000237', 'ROOM.ADMIN.NUM.MAX', 1, '房间管理员数量上限', '5', '8', 37, '00000000000000000000000000000002');

-- 2016-03-14
alter table lansha_room_ranking add up_time bigint(20) default 0  comment '插入时间（毫秒）';
-- 用户类型(官方)
alter table yw_user add official_type varchar(5) null comment '官方类型(1:官方,2:超管)';
-- 房间备注
alter table yw_user_room add remarks varchar(500) null comment '备注(禁播原因)';
-- 主播专题PC
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000227', 'LANSHA.ANCHOR.PC', 1, '主播专题PC', '', '', 27, '00000000000000000000000000000002');
-- 主播专题H5
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000228', 'LANSHA.ANCHOR.H5', 1, '主播专题H5', '', '', 28, '00000000000000000000000000000002');
-- 房间发言等待时间(秒)
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000229', 'LANSHA.ROOM.CD', 1, '房间发言等待时间(秒)', '3', '3', 29, '00000000000000000000000000000002');

alter table yw_game add resource varchar(1000) comment '游戏视频(video,img 标签)';
alter table yw_game add mobile_banner varchar(200) comment '游戏移动端banner';

-- 推荐类型
alter table yw_user_room_hot add type varchar(5) comment '推荐类型(1:热门推荐,2:大神推荐,3:女神推荐)';
--原有数据初始化
update yw_user_room_hot set type=1;

alter table yw_user_room_day_data add remark varchar(500) comment '备注';

-- 首页显示几个推荐游戏(app)
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000230', 'LANSHA.INDEX.SHOWNUM.APP', 1, 'APP端首页显示游戏数量', '6', '6', 30, '00000000000000000000000000000002');


insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000235', 'LANSHA.INDEX.SHOWNUM.APP.RECOMMEND', 1, 'APP端首页显示大神女神数量', '6', '6', 30, '00000000000000000000000000000002');


insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000236', 'LANSHA.INDEX.SHOWNUM.APP.GAME.TYPE', 1, 'APP端游戏页显示游戏类型数量', '6', '6', 30, '00000000000000000000000000000002');


insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('0000000000000000000000000000023', 'LANSHA.INDEX.SHOWNUM.APP.LIST.NUM', 1, 'APP端直播页直播显示单页数量', '6', '6', 30, '00000000000000000000000000000002');


-- 首页显示几个推荐游戏(pc)
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000231', 'LANSHA.INDEX.SHOWNUM.PC', 1, 'PC端首页显示游戏数量', '2', '2', 31, '00000000000000000000000000000002');

insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000232', 'LANSHA.ACTIVITY.GAME', 1, 'PC游戏活动具体名称', '王者荣耀,穿越火线', '王者荣耀,穿越火线', 32, '00000000000000000000000000000002');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000233', 'LANSHA.INDEX.SHOWNUM.APP.GAME', 1, 'APP端游戏页显示单页房间数量', '6', '6', 33, '00000000000000000000000000000002');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000234', 'LANSHA.ACTIVITY.NEWUSER', 1, 'PC活动以此时间为准,该时间后注册的用户都是新用户（格式为yyyy-MM-dd HH:mm:ss）', '2016-03-18 00:00:00', '2016-03-18 00:00:00', 33, '00000000000000000000000000000002');

insert into `activity_gift` (`id`, `item_id`, `name`, `status`, `img`, `order_id`, `remark`, `create_time`, `money`, `type`, `bi`, `object_id`, `number`, `stock`) 
values('00000000000000000000000000003011',  '00000000000000000000000000000003',  '1个q币',  '1',  null,  '5',  null,  now(),  '1',  '3',  '1',  '',  '0',  '1');
-- 精彩活动
insert into `sys_model` (`id`, `parent_id`, `name`, `img`, `url`, `is_use`, `order_id`, `create_time`) values ('00000000000000000000000000060005', '00000000000000000000000000000006', '精彩活动', null, '/admin/activity/activity-push.html', '1', 4, now());

-- 精彩活动表
create table lansha_activity_push (
	id          char(32)		not null,
	title		varchar(50)		not null comment '标题',
	index_img   varchar(100)	null comment '图片',
	activity_url varchar(100)	not null comment '活动链接',
	remark		varchar(500)	null comment '备注',
	order_id	int(11)	 default 0 comment '排序号',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '精彩活动表';

-- 短信发送服务器
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099010', 'SYS.MAYCHOO.SERVER', 1, '短信服务-发送服务器(MAYCHOO)', 'http://sms.maychoo.com', 'http://sms.maychoo.com', 10, '00000000000000000000000000000099');
-- 短信查询库存服务器
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099011', 'SYS.MAYCHOO.STOCK_SERVER', 1, '短信服务-查询库存服务器(MAYCHOO)', 'http://onethink.tao3w.com', 'http://onethink.tao3w.com', 11, '00000000000000000000000000000099');
-- 短信服务账户
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099012', 'SYS.MAYCHOO.OPEN_ID', 1, '短信服务-账户(MAYCHOO)', 'lansha', 'lansha', 12, '00000000000000000000000000000099');
-- 短信服务密码
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000099013', 'SYS.MAYCHOO.OPEN_PASS', 1, '短信服务-密码(MAYCHOO)', 'lanshatv123456', 'lanshatv123456', 13, '00000000000000000000000000000099');

-- 埋码代码
insert into sys_mcode_list(id, name, length, type, remark, is_using, this_id) 
value('00000000000000000000000000000003', '蓝鲨CNZZ统计代码', '1', '1', '蓝鲨CNZZ统计代码', '1', 'LANSHA.CNZZ.CODE');

alter table sys_mcode_detail modify column content varchar(500);
insert into sys_mcode_detail(id, list_id, this_id, content, is_using, mcode_type, display_order) values 
('00000000000000000000000000003001', 'LANSHA.CNZZ.CODE', 'ALL_PAGES', 'dplus.define(\"page\",function(page){page.setTitle(window.title);page.view()});', '1', '1', 1),
('00000000000000000000000000003002', 'LANSHA.CNZZ.CODE', '/wap/index.html', 'function getQueryString(name){var reg=new RegExp(\"(^|&)\"+name+\"=([^&]*)(&|$)\",\"i\");var r=window.location.search.substr(1).match(reg);if(r!=null){return unescape(r[2])}return\"\"}dplus.register({广告来源:getQueryString(\"utm_source\")});', '1', '1', 2),
('00000000000000000000000000003003', 'LANSHA.CNZZ.CODE', '/login.html', 'function loginSuccess(){dplus.identify($.trim($(\"#mTel\").val()));dplus.track(\"登录成功\",{用户名:$.trim($(\"#mTel\").val())})}function loginFailed(){dplus.identify($.trim($(\"#mTel\").val()));dplus.track(\"登录失败\",{用户名:$.trim($(\"#mTel\").val())})};', '1', '1', 3),
('00000000000000000000000000003004', 'LANSHA.CNZZ.CODE', '/appdownload.html', 'dplus.track_links(\".app-one.app-android.app-color01.clearfix\",\"安卓版APP下载\",{current_url:window.location.href},500);dplus.track_links(\".app-one.app-iOS.app-color02.clearfix\",\"iPhone版APP下载\",{current_url:window.location.href},500);dplus.track_links(\".app-one.app-android.app-color03.clearfix\",\"安卓版蓝鲨录下载\",{current_url:window.location.href},500);dplus.track_links(\".app-one.app-iOS.app-color04.clearfix\",\"iPhone版蓝鲨录下载\",{current_url:window.location.href},500);', '1', '1', 4),
('00000000000000000000000000003005', 'LANSHA.CNZZ.CODE', '/index.html', 'function getQueryString(name){var reg=new RegExp(\"(^|&)\"+name+\"=([^&]*)(&|$)\",\"i\");var r=window.location.search.substr(1).match(reg);if(r!=null){return unescape(r[2])}return\"\"}dplus.register({广告来源:getQueryString(\"utm_source\")});', '1', '1', 5),
('00000000000000000000000000003006', 'LANSHA.CNZZ.CODE', '/live/*.html', 'function loginSuccess(){dplus.identify($.trim($(\"#loginMinTel\").val()));dplus.track(\"登录成功\",{用户名:$.trim($(\"#loginMinTel\").val())})}function loginFailed(){dplus.identify($.trim($(\"#loginMinTel\").val()));dplus.track(\"登录失败\",{用户名:$.trim($(\"#loginMinTel\").val())})}function follow(){dplus.track(\"关注\",{页面标题:window.title})}function cancelFollow(){dplus.track(\"取消关注\",{页面标题:window.title})};', '1', '1', 6);
('00000000000000000000000000003007', 'LANSHA.CNZZ.CODE', '/register.html', 'function regSuccess(){dplus.identify($.trim($(\"#tTel\").val()));dplus.track(\"完成注册\",{用户名:$.trim($(\"#tTel\").val()),昵称:$.trim($(\"#nickname\").val())})}function regFailed(){dplus.track(\"注册失败\",{用户名:$.trim($(\"#tTel\").val()),昵称:$.trim($(\"#nickname\").val())})};', '1', '1', 7);


-- ===================== im机器人start ======================================
-- 机器人房间
create table yw_im_robot_room(
	id     char(32)		not null,
	room_id			char(32)		not null comment '房间id',
	openfire_room	varchar(100)	null comment '房间名称',
	status			varchar(5)		null comment '状态(0:停用,1:启用)',
	start_time		datetime		null comment '开始时间',
	end_time		datetime		null comment '结束时间',
	create_time		datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='机器人房间';

-- 机器人用户
create table yw_im_robot_user(
	id          char(32)		not null,
	username	varchar(200)	null comment '用户名',
	weight		integer			null default 5 comment '权重越大说明概率越高(0-10)',
	status		varchar(5)		null comment '状态(0:停用,1:启用)',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='机器人用户';

-- 机器人聊天组
create table yw_im_robot_group(
	id          char(32)		not null,
	title		varchar(200)	null comment '标题',
	status		varchar(5)		null comment '状态(0:停用,1:启用)',
	weight		integer			null default 5 comment '权重越大说明概率越高(0-10)',
	users		integer			null default 1 comment '需要人数',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='机器人聊天组';

-- 机器人聊天内容
create table yw_im_robot_message(
	id          char(32)		not null,
	group_id    char(32)		not null comment '组id',
	status		varchar(5)		null comment '状态(0:停用,1:启用)',
	username	varchar(10)		null comment '用户标示',
	note		varchar(100)	null comment '聊天内容',
	time		integer			null comment '需要延迟时间(毫秒)',
	order_no	integer			default 1 null comment '排序',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='机器人聊天内容';
-- ===================== im机器人end ======================================

-- ===================== 充值start ======================================
-- 充值列表
1,5,10,50,100,500,1000


alter table sys_mcode_detail add(ext1 varchar(200) null comment '扩展字段1');
alter table sys_mcode_detail add(ext2 varchar(200) null comment '扩展字段2');
-- sys_mcode_list,sys_mcode_detail表中插入数据
insert into sys_mcode_list(id, name, length, type, remark, is_using, this_id) value('00000000000000000000000000202000', '易宝支付-银行列表', '2', '1', '易宝支付-银行列表', '1', 'PDFRPID');
insert into sys_mcode_detail(id, list_id, this_id, content, is_using, mcode_type, display_order,ext1, ext2) values 
('00000000000000000000000000202001', 'PDFRPID', 'BOC', 'BOC-NET-B2C', '1', '1', '1', '中国银行', '/images/bank/zg.png'),
('00000000000000000000000000202002', 'PDFRPID', 'ICBC', 'ICBC-NET-B2C', '1', '1', '2', '工商银行', '/images/bank/gs.png'),
('00000000000000000000000000202003', 'PDFRPID', 'ABC', 'ABC-NET-B2C', '1', '1', '3', '农业银行', '/images/bank/ny.png'),
('00000000000000000000000000202004', 'PDFRPID', 'CCB', 'CCB-NET-B2C', '1', '1', '4', '建设银行', '/images/bank/js.png'),
('00000000000000000000000000202005', 'PDFRPID', 'BOCO', 'BOCO-NET-B2C', '1', '1', '5', '交通银行', '/images/bank/jt.png'),
('00000000000000000000000000202006', 'PDFRPID', 'POST', 'POST-NET-B2C', '1', '1', '6', '邮政储蓄银行', '/images/bank/zgyz.png'),
('00000000000000000000000000202007', 'PDFRPID', 'BCCB', 'BCCB-NET-B2C', '1', '1', '7', '北京银行', '/images/bank/bj.png'),
('00000000000000000000000000202008', 'PDFRPID', 'SHB', 'SHB-NET-B2C', '1', '1', '8', '上海银行', '/images/bank/sh.png'),
('00000000000000000000000000202009', 'PDFRPID', 'CMBCHINA', 'CMBCHINA-NET-B2C', '1', '1', '9', '招商银行', '/images/bank/zsyh.png'),
('00000000000000000000000000202010', 'PDFRPID', 'ECITIC', 'ECITIC-NET-B2C', '1', '1', '10', '中信银行', '/images/bank/zx.png'),
('00000000000000000000000000202011', 'PDFRPID', 'CEB', 'CEB-NET-B2C', '1', '1', '11', '光大银行', '/images/bank/zggd.png'),
('00000000000000000000000000202012', 'PDFRPID', 'HXB', 'HXB-NET-B2C', '1', '1', '12', '华夏银行', '/images/bank/hx.png'),
('00000000000000000000000000202013', 'PDFRPID', 'CIB', 'CIB-NET-B2C', '1', '1', '13', '兴业银行', '/images/bank/xy.png'),
('00000000000000000000000000202014', 'PDFRPID', 'PINGANBANK', 'PINGANBANK-NET-B2C', '1', '1', '14', '平安银行', '/images/bank/pa.png'),
('00000000000000000000000000202015', 'PDFRPID', 'CMBC', 'CMBC-NET-B2C', '1', '1', '15', '中国民生银行', '/images/bank/ms.png'),
('00000000000000000000000000202016', 'PDFRPID', 'GDB', 'GDB-NET-B2C', '1', '1', '16', '广发银行', '/images/bank/gf.png'),
('00000000000000000000000000202018', 'PDFRPID', 'SPDB', 'SPDB-NET-B2C', '1', '1', '18', '上海浦东发展银行', '/images/bank/pf.png'),
-- 易宝没提供此银行接口（深圳发展银行和平安银行已合并）
('00000000000000000000000000202017', 'PDFRPID', 'SDB', 'SDB-NET-B2C', '0', '1', '17', '深圳发展银行', '/images/bank/szgf.png'),
-- 易宝没提供此银行接口
('00000000000000000000000000202019', 'PDFRPID', 'BJRCB', 'BJRCB-NET-B2C', '0', '1', '19', '北京农村商业银行', '/images/bank/bjns.png');

-- 鲨币字段
alter table yw_user add stock decimal(11, 2) null default 0 comment '鲨币';

-- 银行卡
alter table yw_user add blank_no decimal(11, 2) null comment '银行卡';
-- 开户行
alter table yw_user add blank_name decimal(11, 2) null comment '开户行';
-- 开户名

-- 账户余额变动表
create table lansha_user_accounts (
	id          char(32)		not null,
	user_id		char(32)		not null comment '用户id',
	type		varchar(10)		not null comment '类型(1:支出,2:充值)',
	title		varchar(200)	null comment '标题',
	money		decimal(11, 2)	not null default 0 comment '金额(如果是支出则为负)',
	remark		varchar(500)	null comment '备注',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '广告商流水';

-- 充值订单
create table lansha_user_order(
	id          	char(32)		not null,
	user_id			char(32)		null comment '用户id',
	username		varchar(100)	null comment '用户名称',
	order_no		varchar(20)		null comment '订单号',
	pay_type 		varchar(20) 	null comment '支付方式(alipay:支付宝,yee:易宝)'
	trade_no		varchar(40)		null comment '三方平台订单号',
	create_time		datetime		null comment '创建时间',
	pay_time		datetime		null comment '支付时间',
	valid_time		datetime		null comment '订单有效时间',
	title			varchar(100)	null comment '订单名称',
	status			char(1)			null comment '状态（0：未支付，1：已支付，2：支付失败）',
	price			decimal(11, 2)	null comment '总价',
	pay_price		decimal(11, 2)	null comment '实际支付金额',
	error_msg		varchar(100)	null comment '充值失败原因',
	remark			varchar(200)	null comment '订单备注',
	stock			decimal(11, 2)	null comment '充值点数',
	bank			varchar(20)		null comment '银行代码',
	
	is_valid 		varchar(5) 		null comment '是否对账',
	valid_price		decimal(11, 2)	null comment '对账金额',
	valid_date		datetime		null comment '对账时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '充值订单';

-- 提现申请表

-- ===================== 充值end ======================================


-- ===================== 录屏start ======================================
-- 录屏文件


-- ===================== 录屏end ======================================
