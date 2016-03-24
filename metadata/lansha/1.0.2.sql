-- 主播实名认证 2016-01-04
alter table yw_user_room_apply add(room_name varchar(100) null comment '房间名称');
alter table yw_user_room_apply add(game_id char(32) null comment '所属游戏');
alter table yw_user_room_apply add(notice text null comment '直播公告');

-- 礼品
delete from activity_gift where item_id = '00000000000000000000000000000001';
insert into activity_gift(id, item_id, name, status, order_id, create_time, money, type, object_id, bi, number, stock) values
('00000000000000000000000000000001', '00000000000000000000000000000001', '5个虾米', '1', 2, now(), 0.05, '2', '00000000000000000000000000000001', 5, 85000, 85000),
('00000000000000000000000000000002', '00000000000000000000000000000001', '15个虾米', '1', 5, now(), 0.15, '2', '00000000000000000000000000000001', 15, 14133, 14133),
('00000000000000000000000000000003', '00000000000000000000000000000001', '10元移动充值卡', '1', 8, now(), 10, '3', '', 1, 700, 700),
('00000000000000000000000000000004', '00000000000000000000000000000001', '30元移动充值卡', '1', 3, now(), 30, '3', '', 1, 30, 30),
('00000000000000000000000000000005', '00000000000000000000000000000001', '100元移动充值卡', '1', 6, now(), 100, '3', '', 1, 15, 15),
('00000000000000000000000000000006', '00000000000000000000000000000001', '公仔', '1', 4, now(), 30, '4', '', 1, 120, 120),
('00000000000000000000000000000007', '00000000000000000000000000000001', '拍立得', '1', 7, now(), 500, '4', '', 1, 2, 2),
('00000000000000000000000000000008', '00000000000000000000000000000001', 'iPhone6s', '1', 1, now(), 6000, '4', '', 1, 0, 0);

-- app下载
-- iOS下载地址
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000215', 'LANSHA.APP.IOS', 1, 'APP(iOS)下载地址', '', '', 15, '00000000000000000000000000000002');
-- 安卓下载地址
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000216', 'LANSHA.APP.ANDROID', 1, 'APP(安卓)下载地址', 'http://www.lansha.tv/store/app/blueshark-official-release.apk', 'http://www.lansha.tv/store/app/blueshark-official-release.apk', 16, '00000000000000000000000000000002');
-- 直播插件iOS下载地址
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000217', 'LANSHA.APP.LIVE_IOS', 1, '直播插件(iOS)下载地址', '', '', 17, '00000000000000000000000000000002');
-- 直播插件安卓下载地址
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000218', 'LANSHA.APP.LIVE_ANDROID', 1, '直播插件(安卓)下载地址', '', '', 18, '00000000000000000000000000000002');

-- 首页banner
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000219', 'LANSHA.INDEX.BANNER', 1, '首页banner图片地址', '/activity/upload/banner_bg.jpg', '/activity/upload/banner_bg.jpg', 19, '00000000000000000000000000000002');

-- 意见反馈
alter table lansha_suggestion add(type varchar(2) null default '3' comment '类型(1:使用帮助,2:直播问题,3:账户安全,4:手机app,5:录屏插件,6:其他)');

insert into sys_mcode_list(id, name, length, type, remark, is_using, this_id) 
value('00000000000000000000000000000002', 'LANSHA_SUGGESTION', '1', '1', 'lansha-意见反馈类型', '1', 'LANSHA_SUGGESTION');

insert into sys_mcode_detail(id, list_id, this_id, content, is_using, mcode_type, display_order) values 
('00000000000000000000000000002001', 'LANSHA_SUGGESTION', '1', '使用帮助', '1', '1', '1'),
('00000000000000000000000000002002', 'LANSHA_SUGGESTION', '2', '直播问题', '1', '1', '2'),
('00000000000000000000000000002003', 'LANSHA_SUGGESTION', '3', '账户安全', '1', '1', '3'),
('00000000000000000000000000002004', 'LANSHA_SUGGESTION', '4', '手机app', '1', '1', '4'),
('00000000000000000000000000002005', 'LANSHA_SUGGESTION', '5', '录屏插件', '1', '1', '5'),
('00000000000000000000000000002006', 'LANSHA_SUGGESTION', '6', '其他', '1', '1', '6');

-- 用户更新
alter table yw_user_band modify uid char(32) null comment '用户id'; 

-- 用户邀请报表
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000050005', '00000000000000000000000000000005', '推广注册统计', '/admin/report/user.html', '1', '5', now());

-- 添加3次抽奖机会
update activity_user set stock = 0 where stock is null;
update activity_user set stock = stock + 3;
insert into activity_user(id, telphone, last_activity, stock, create_time) select id, mobile, now(), 3, create_time from yw_user where id not in(select id from activity_user);

-- 20160115
-- 是否阅读过播放页规则
alter table yw_user add(is_read char(1) null comment '是否阅读过播放页规则');
-- 客户端类型
alter table lansha_version add(app_type char(1) not null default '0' comment '客户端类型(0:普通，1:蓝鲨录)');
-- 意见反馈 2016-01-15
alter table lansha_suggestion add(title varchar(100) null comment '意见反馈标题');
-- 20160116
-- 广告类型
alter table yw_banner add(client_type char(1) not null default '0' comment '端类型(0:pc，1:app)');

-- 20160119
update sys_option set nowvalue = 'rtmp://live.lansha.tv/live/' where iniid = 'FMS.RTMP';
update yw_user_room set rtmp = (select nowvalue from sys_option where iniid = 'FMS.RTMP');

update sys_option set nowvalue = 'rtmp://play.lansha.tv/live/' where iniid = 'FMS.RTMP.LIVE';
update yw_user_room set live_host = (select nowvalue from sys_option where iniid = 'FMS.RTMP.LIVE');

-- 用户抽奖机会
create table activity_user_stock (
	id 			char(32) 		not null,
	user_id 	char(32) 		not null comment '用户id',
	item_id		char(32) 		not null comment '活动id',
	last_activity	datetime		null comment '上次抽奖时间',
	stock		integer			not null default 0 comment '抽奖次数',
	create_time	datetime		null,

	primary key (id)
)engine=innodb default charset=utf8 comment '用户抽奖机会';
-- 将activity_user表的数据移动到activity_user_stock表
insert into activity_user_stock (id,user_id,item_id,last_activity,stock,create_time)  select id,id,'00000000000000000000000000000001',last_activity,stock,create_time from activity_user;
-- 增加qq号字段
alter table activity_user add column qq varchar(15) null comment 'QQ号' after telphone;
-- 删除last_activity
alter table activity_user drop column last_activity;
-- 删除stock字段
alter table activity_user drop column stock;
-- 更新activity_user表注释
alter table activity_user comment '用户领奖信息';
-- 新增第二次活动
insert into activity_item(id, name, start_time, status, create_time) value('00000000000000000000000000000002', '推广活动(王者荣耀)', now(), '1', now());
-- 新增第二次活动礼品
insert into activity_gift(id, item_id, name, status, order_id, create_time, money, type, object_id, bi, number, stock) values
('00000000000000000000000000002001', '00000000000000000000000000000002', '10游戏点券', '1', 1, now(), 1, '3', '', 1, 6000, 6000),
('00000000000000000000000000002002', '00000000000000000000000000000002', '游戏手柄', '1', 2, now(), 160, '4', '', 1, 8, 8),
('00000000000000000000000000002003', '00000000000000000000000000000002', '60游戏点券', '1', 3, now(), 6, '3', '', 1, 2000, 2000),
('00000000000000000000000000002004', '00000000000000000000000000000002', '2个Q币', '1', 4, now(), 2, '3', '', 1, 5000, 5000),
('00000000000000000000000000002005', '00000000000000000000000000000002', '300游戏点券', '1', 5, now(), 30, '3', '', 1, 200, 200),
('00000000000000000000000000002006', '00000000000000000000000000000002', '5个虾米', '1', 6, now(), 0.05, '2', '00000000000000000000000000000001', 5, 380000, 380000),
('00000000000000000000000000002007', '00000000000000000000000000000002', 'iPhone 6s', '1', 7, now(), 6000, '4', '', 1, 0, 0),
('00000000000000000000000000002008', '00000000000000000000000000000002', '5个Q币', '1', 8, now(), 5, '3', '', 1, 4000, 4000),
('00000000000000000000000000002009', '00000000000000000000000000000002', '3480游戏点券', '1', 9, now(), 348, '3', '', 1, 0, 0),
('00000000000000000000000000002010', '00000000000000000000000000000002', 'iPAD Air2', '1', 10, now(), 3500, '4', '', 1, 0, 0);
-- 新增用户登录方式字段
alter table log_user_login add column login_method char(1) null comment '登录方式(0:App登录,1:PC端登录)';

-- 20160122
-- 新增第三次活动
insert into activity_item(id, name, start_time, status, create_time) value('00000000000000000000000000000003', '推广活动(穿越火线)', now(), '1', now());
-- 新增第三次活动礼品
insert into activity_gift(id, item_id, name, status, order_id, create_time, money, type, object_id, bi, number, stock) values
('00000000000000000000000000003001', '00000000000000000000000000000003', '10个钻石', '1', 1, now(), 1, '3', '', 1, 6000, 6000),
('00000000000000000000000000003002', '00000000000000000000000000000003', '游戏手柄', '1', 2, now(), 160, '4', '', 1, 8, 8),
('00000000000000000000000000003003', '00000000000000000000000000000003', '60个钻石', '1', 3, now(), 6, '3', '', 1, 2000, 2000),
('00000000000000000000000000003004', '00000000000000000000000000000003', '2个Q币', '1', 4, now(), 2, '3', '', 1, 5000, 5000),
('00000000000000000000000000003005', '00000000000000000000000000000003', '30个Q币', '1', 5, now(), 30, '3', '', 1, 200, 200),
('00000000000000000000000000003006', '00000000000000000000000000000003', '5个虾米', '1', 6, now(), 0.05, '2', '00000000000000000000000000000001', 5, 380000, 380000),
('00000000000000000000000000003007', '00000000000000000000000000000003', 'iPhone 6s', '1', 7, now(), 6000, '4', '', 1, 0, 0),
('00000000000000000000000000003008', '00000000000000000000000000000003', '5个Q币', '1', 8, now(), 5, '3', '', 1, 4000, 4000),
('00000000000000000000000000003009', '00000000000000000000000000000003', '6480个钻石', '1', 9, now(), 648, '3', '', 1, 0, 0),
('00000000000000000000000000003010', '00000000000000000000000000000003', 'iPAD Air2', '1', 10, now(), 3500, '4', '', 1, 0, 0);

alter table activity_item add column chance varchar(32) NOT NULL default '0,0,0,0,0,0' comment '抽奖机会增加次数(注册、推广注册、连续登陆、连续登陆并领取虾米、成为主播、首次登录app)';
update activity_item set chance = '1,1,1,1,5,0' where id = '00000000000000000000000000000001';
update activity_item set chance = '2,1,0,0,0,5' where id = '00000000000000000000000000000002';
update activity_item set chance = '2,1,0,0,0,5' where id = '00000000000000000000000000000003';


-- 20160124
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000015', 'SYS.UPLOAD.HOST.PATH', 1, '文件上传域名地址', 'http://cdn.lansha.tv', 'http://cdn.lansha.tv', 15, '00000000000000000000000000000001');

-- 20160125
-- 是否签约
alter table yw_user_room add(sign char(1) NOT NULL default '0' comment '是否签约(0：未签约；1已签约)');

-- 20160125
-- 微信接口访问的Token
create table wechat_access_token (
	id 			char(32) 		not null,
	app_id 		char(32) 		not null unique comment '应用id',
	token_name 	varchar(200) 	not null comment 'token名',
	pub_date	datetime 		not null comment '发布时间',
	expires_in	integer			not null comment '失效时间(秒)',
	create_time	datetime		null,

	primary key (id)
)engine=innodb default charset=utf8 comment '微信接口访问的Token';
-- 微信JS接口访问的票据
create table wechat_jsapiticket (
	id 			char(32) 		not null,
	app_id 		char(32) 		not null unique comment '应用id',
	ticket_name varchar(200) 	not null comment 'ticket名',
	pub_date	datetime 		not null comment '发布时间',
	expires_in	integer			not null comment '失效时间(秒)',
	create_time	datetime		null,

	primary key (id)
)engine=innodb default charset=utf8 comment '微信JS接口访问的票据';
-- 初始化wap王者荣耀微信分享数据
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue,description, orderid, type_id)
value('00000000000000000000000000000302', 'ACTIVITY.WZRY.SHARE', 1, '王者荣耀微信分享内容', 'http://cdn.lansha.tv/store/app/sharelogo.jpg|蓝鲨TV手游直播平台100%送大奖了|我点一次就中了2个Q币，还有游戏点券、平板、手机等好礼，你也来试试吧|ON','http://cdn.lansha.tv/store/app/sharelogo.jpg|蓝鲨TV手游直播平台100%送大奖了|我点一次就中了2个Q币，还有游戏点券、平板、手机等好礼，你也来试试吧|ON', '分享的图标|标题|描述|微信分享功能开启(ON)或关闭(OFF),竖线分割', 12, '00000000000000000000000000000003');
-- 初始化wap穿越火线微信分享数据
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue,description, orderid, type_id)
value('00000000000000000000000000000303', 'ACTIVITY.CYHX.SHARE', 1, '穿越火线微信分享内容', 'http://cdn.lansha.tv/store/app/sharelogo.jpg|蓝鲨TV手游直播平台100%送大奖了|我点一次就中了2个Q币，还有钻石、平板、手机等好礼，你也来试试吧|ON','http://cdn.lansha.tv/store/app/sharelogo.jpg|蓝鲨TV手游直播平台100%送大奖了|我点一次就中了2个Q币，还有钻石、平板、手机等好礼，你也来试试吧|ON', '分享的图标|标题|描述|微信分享功能开启(ON)或关闭(OFF),竖线分割', 13, '00000000000000000000000000000003');

-- 2016-01-26
-- 领取鲜花时间
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000220', 'LANSHA.FLOWER.TIME', 1, '领取鲜花时间(秒)', '180', '180', 20, '00000000000000000000000000000002');

-- 20160127
-- 修改头像地址知道长度，对接第三方登录
alter table yw_user modify column headpic varchar(200);

-- 更新奖品名称
update activity_gift set name='iPhone 6' where id in ('00000000000000000000000000000008','00000000000000000000000000002007','00000000000000000000000000003007');

-- 房间排名
create table lansha_room_ranking (
	id 				char(32) 		not null,
	user_id 		char(32) 		not null comment '用户id',
	room_idInt		integer			not null comment '房间id',
	live_time		integer			not null comment '直播时长(分钟)',
	xiami_number	integer			not null comment '每日虾米数量',
	relation_number	integer			not null comment '每日关注数量',
	flower_nubmer	integer			not null comment '每日鲜花数',
	audience		integer			not null comment '观众人数',
	create_time		datetime		null,

	primary key (id)
)engine=innodb default charset=utf8 comment '房间排名';

-- 积分排名参数
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000221', 'LANSHA.ROOM.RANKING', 1, '积分排名参数(直播时长,虾米数,关注数,鲜花数,观众数)', '1,1,1,1,1', '1,1,1,1,1', 21, '00000000000000000000000000000002');
-- 积分排名周期
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000222', 'LANSHA.ROOM.RANKING.TYPE', 1, '积分排名周期(天)', '10', '10', 22, '00000000000000000000000000000002');
-- 主播积分排行
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000030004', '00000000000000000000000000000003', '主播积分排行', '/admin/room/anchorRank.html', '1', '4', now());
-- wap活动页注册无抽奖机会
update activity_item set chance = '0,1,0,0,0,5' where id = '00000000000000000000000000000002';
update activity_item set chance = '0,1,0,0,0,5' where id = '00000000000000000000000000000003';

-- 2016-01-28
-- 领到的鲜花
insert into lansha_gift(id, name, status, bi, img, orderid, create_time)
value('00000000000000000000000000000002', '领到的鲜花', '2', 2, '', 2, now());
-- 收到的鲜花
insert into lansha_gift(id, name, status, bi, img, orderid, create_time)
value('00000000000000000000000000000003', '收到的鲜花', '3', 3, '', 3, now());

-- 20160130（发布）
alter table yw_user add column device_id varchar(100);
alter table yw_user add column regiest_device_id varchar(100);
alter table yw_user add column os_type char(1) comment '平台(1:android,2:ios)';

-- 20160201
-- 用户禁言
alter table yw_user add column is_black char(1) default '0' comment '是否加入黑名单(0:否,1:是)';

-- 20160203
-- 昵称关键字过滤
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, description,orderid, type_id)
value('00000000000000000000000000000223', 'LANSHA.NICKNAME.KEYWORD', 1, '昵称关键字过滤', '.*管理员.*,.*客服.*,.*官方.*,.*GM.*,.*超管.*,.*版主.*,.*群管.*,.*主播.*,.*斗鱼.*,.*触手.*,.*战旗.*,.*虎牙.*,.*凸凸.*,.*火猫.*,.*熊猫.*,.*龙珠.*,.*我操.*,.*我日.*,.*操你妈.*,.*你妈逼.*,.*卖屁.*,.*傻逼.*,.*Sb.*,.*鸡巴.*,.*奶子.*,.*奶头.*,.*阴户.*,.*阴唇.*,.*阴茎.*,.*阴蒂.*,.*小穴.*,.*淫水.*,.*做爱.*,.*毛泽东.*,.*周恩来.*,.*邓小平.*,.*胡锦涛.*,.*温家宝.*,.*习近平.*,.*李克强.*,.*周永康.*,.*朱镕基.*,.*江泽民.*,.*www.*,.*com.*,.*cn.*,.*org.*,.*cc.*,.*co.*,.*live.*,.*me.*,.*bbs.*,.*qq.*', '.*管理员.*,.*客服.*,.*官方.*,.*GM.*,.*超管.*,.*版主.*,.*群管.*,.*主播.*,.*斗鱼.*,.*触手.*,.*战旗.*,.*虎牙.*,.*凸凸.*,.*火猫.*,.*熊猫.*,.*龙珠.*,.*我操.*,.*我日.*,.*操你妈.*,.*你妈逼.*,.*卖屁.*,.*傻逼.*,.*Sb.*,.*鸡巴.*,.*奶子.*,.*奶头.*,.*阴户.*,.*阴唇.*,.*阴茎.*,.*阴蒂.*,.*小穴.*,.*淫水.*,.*做爱.*,.*毛泽东.*,.*周恩来.*,.*邓小平.*,.*胡锦涛.*,.*温家宝.*,.*习近平.*,.*李克强.*,.*周永康.*,.*朱镕基.*,.*江泽民.*,.*www.*,.*com.*,.*cn.*,.*org.*,.*cc.*,.*co.*,.*live.*,.*me.*,.*bbs.*,.*qq.*','添加关键字以英文逗号分隔,且关键字前后加上.*', 23, '00000000000000000000000000000002');

-- 昵称为空的更新
update yw_user set nickname = CONCAT('shark', id_int) where nickname is null or length(nickname) = 0;
