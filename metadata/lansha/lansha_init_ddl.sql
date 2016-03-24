-- 广告
create table yw_banner(
	id 			char(32) 		not null,
	name		varchar(45)		not null comment '类别名称',
	content		varchar(200)	null comment '广告内容',
	type		char(1)			not null comment '类型(0：房间，1：广告)',
	link_url	varchar(100)	null comment '链接地址',
	img			varchar(100)	null comment '图片地址',
	order_id	integer			null comment '排序',
	create_time	datetime		null comment '创建时间',
	room_id		char(32)		null comment '房间id',

	primary key (id)
)engine=innodb default charset=utf8 comment '广告';

-- 游戏类型
create table yw_game_type(
	id 			char(32) 		not null,
	name		varchar(45)		not null comment '类别名称',
	order_id	integer			null comment '排序',
	status		char			default '0' comment '状态 0禁用1启用',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment '游戏类型';

-- 游戏表
create table yw_game(
	id 				char(32)	 	not null,
	name			varchar(100) 	default null 	comment '游戏名称',
	type_id			char(32)		not null		comment '游戏类型',
	brief_intro		text			null			comment '简介',
	icon			varchar(500)	null			comment '游戏图标',
	screen			text			null			comment '游戏截图',
	advert			varchar(500)	null			comment '游戏图',
	advert_small	varchar(500)	null			comment '游戏图(小)',
	background		varchar(500)	null			comment '背景图',
	qrcode			varchar(500)	null			comment '二维码',
	android_url		varchar(200)	null			comment 'android下载地址',
	ios_url			varchar(200)	null			comment 'iOS下载地址',
	status			integer 		not null		comment '状态0删除，1上线，2下线',
	order_id		integer			null			comment '排序',
	up_time			datetime		null			comment '上线时间',
	create_time		datetime		null			comment '创建时间',
	developer		varchar(80)		null			comment '作者',
	
	primary key (id)
)engine=innodb default charset=utf8 comment='游戏表';

-- 热门推荐游戏
create table yw_game_hot(
	id          char(32)		not null,
	game_id		char(32)		not null comment '房间id',
	order_id	integer			null comment '游戏排序号',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='热门推荐游戏';

-- 用户基本信息表
create table yw_user (
  id 			char(32) 		not null,
  username 		varchar(20) 	binary default '' comment '帐号',
  account 		varchar(50) 	default '' comment '第三方登录的用户名',
  account_type 	enum('weixin','weibo','mail','qq') default null comment '帐号类型',
  nickname 		varchar(20) 	default '' comment '昵称',
  password 		varchar(32) 	default '' comment '密码',
  mobile 		varchar(11) 	default '' comment '手机号码',
  sex 			integer 		default '0' comment '性别 0未知1男2女', 
  headpic 		varchar(100) 	default '' comment '头像地址',
  reg_channel 	varchar(30)		default '' comment '注册渠道',
  birthday 		datetime 		default null comment '生日',
  sign 			varchar(250) 	default '' comment '签名',
  jingyan 		integer 		default 0 comment '经验',
  is_vip 		integer 		default 0 comment '是否是vip',
  last_login_ip varchar(20) 	default '' comment '登录ip',
  last_login_time 	datetime 	default null comment '登录时间',
  user_status 	integer 		default 0 comment '状态 (0:删除、1:正常、2:冻结、3:封号)',
  user_type		integer 		default 0 comment '用户类型 0前台普通会员,1主播...',
  create_time	datetime 		default null comment '创建时间戳',
  update_time	datetime 		default null comment '更新时间戳',
  address		varchar(15) 	default null comment '所在地',
  qq			varchar(15) 	default null comment 'QQ',
  email			varchar(30) 	default null comment '电子邮箱',
  honor			integer			default null comment '头衔',
  token 		char(32) 		null,
  bi 			integer 		default 0 comment '蓝鲨币（冗余用户行为表sum(bi)）',
  
  time_length	integer			default null comment '观看直播时长',
  remark		varchar(500)	null comment '备注',
  id_int		integer			not null comment '自增id', 
  authe			integer			default 0 comment '是否认证（0：否，1：是）',
 
  primary key (id)
) engine=innodb default charset=utf8 comment='用户基本信息表';
create unique index idx_yw_user_id on yw_user(id_int);
alter table yw_user modify id_int integer null auto_increment;
alter table yw_user auto_increment=100000;

-- 房间观看历史
create table yw_user_room_history(
	id          char(32)		not null,
	uid			char(32)		not null comment '用户id',
	room_id		char(32)		not null comment '房间id',
	status		integer		null comment '状态(0删除、1正常)',
	create_time	datetime		null comment '创建时间',
    time_length	integer			default null comment '观看直播时长',
    bi 			integer 		default 0 comment '蓝鲨币',

	primary key (id)
)engine=innodb default charset=utf8 comment='房间收藏表';

-- ================= 暂时不使用 =====================================
-- 房间收藏表
create table yw_user_room_favorite(
	id          char(32)		not null,
	uid			char(32)		not null comment '用户id',
	room_id		char(32)		not null comment '房间id',
	status		integer		null comment '状态(0删除、1正常)',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='房间收藏表';

-- 用户实名认证
create table yw_user_apply(
	id          	char(32)		not null,
	user_id     	char(32)		not null,
	realname		varchar(10)		null comment '真实姓名',
	identitycard	varchar(20)		null comment '身份证号码',
	pic1			varchar(20)		null comment '手持身份证正面',
	pic2			varchar(20)		null comment '身份证正面',
	pic3			varchar(20)		null comment '身份证反面',
	expiration_time	datetime		null comment '身份证到期时间',
	status			char(1)			null comment '状态(0:未审核,1:审核通过,2:审核不通过)',
	create_time		datetime		null comment '创建时间',
	remark 			varchar(200)	null comment '备注',
	aduit_uid		char(32)		null comment '审核人',
	aduit_time		datetime		null comment '审核时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment='热门推荐房间';
-- ================================================================

-- 房间关注
create table yw_user_room_relation(
	id          char(32)		not null,
	uid			char(32)		not null comment '用户id',
	room_id		char(32)		not null comment '房间id',
	status		integer			null comment '状态(0删除、1正常)',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='房间关注';

-- 女神房间
create table yw_user_room (
  id 			char(32) 		not null,
  id_int		integer			not null comment '自增id',
  uid 			char(32) 		not null comment '用户id',
  name 			varchar(100) 	not null comment '房间名称',
  rtmp 			varchar(100) 	default null comment '流媒体地址',
  room_id 		varchar(50) 	default null comment '房间id',
  live_host 	varchar(50) 	default null comment '上行流域名',
  ws_host 		varchar(50) 	default null comment '下行流域名',
  online 		integer 		default null comment '是否在线（0不在线1在线2禁播）',
  number 		integer 		default null comment '房间人数',
  openfire_path varchar(50) 	default null comment 'im服务器',
  openfire_port integer 		default null comment 'im端口号',
  openfire_room varchar(50) 	default null comment 'im房间号',
  openfire_conference 	varchar(30) default null comment 'im聊天室',
  create_time 	datetime 		default null comment '创建时间',
  update_time 	datetime 		default null comment '更新时间',
  order_id		integer 		default null comment '排序号',
  fans 			integer 		default '0' comment '粉丝数',
  intro			text			default null comment '介绍',
  notice		text			default null comment '公告',
  game_id		char(32)		default null comment '游戏id',
  report_num	integer 		default null comment '举报人数',
  time_length	integer			default null comment '直播时长',
  start_time	varchar(100) 	default null comment '开播时间',
  base_number	integer			default 0 not null comment '观众基数',
  multiple_number	integer		default 1 not null comment '在线人数倍数',
  
  primary key (id)
) engine=innodb default charset=utf8;

create unique index idx_yw_user_room_id on yw_user_room(id_int);
alter table yw_user_room modify id_int integer null auto_increment;
alter table yw_user_room auto_increment=10000;

-- 直播间人数日志
create table yw_user_room_log (
	id				char(32)			not null comment 'id',
	room_id			char(32)			not null comment '房间id',
	user_id			char(32)			not null comment '用户id',
	number			integer				default 0 comment '人数',
	create_time		datetime			not null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment='直播间人数日志';

-- 热门推荐房间
create table yw_user_room_hot(
	id          char(32)		not null,
	room_id		char(32)		not null comment '房间id',
	order_id	integer			null comment '房间排序号',
	create_time	datetime		null comment '创建时间',

	primary key (id)
)engine=innodb default charset=utf8 comment='热门推荐房间';

-- 主播实名认证
create table yw_user_room_apply(
	id          	char(32)		not null,
	user_id     	char(32)		not null,
	realname		varchar(10)		null comment '真实姓名',
	identitycard	varchar(20)		null comment '身份证号码',
	pic1			varchar(100)		null comment '手持身份证正面',
	pic2			varchar(100)		null comment '身份证正面',
	pic3			varchar(100)		null comment '身份证反面',
	expiration_time	datetime		null comment '身份证到期时间',
	status			char(1)			null comment '状态(0:未审核,1:审核通过,2:审核不通过)',
	create_time		datetime		null comment '创建时间',
	remark 			varchar(200)	null comment '备注',
	aduit_uid		char(32)		null comment '审核人',
	aduit_time		datetime		null comment '审核时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment='热门推荐房间';

-- 主播管理员
create table yw_user_room_admin(
	id          	char(32)		not null,
	room_id        	char(32)		not null comment '房间id',
	user_id			char(32)		not null comment '管理员id',
    bi 				integer 		default 0 comment '贡献蓝鲨币',
    time_length		integer			default null comment '观看直播时长',
	nickname 		varchar(20) 	default '' comment '昵称',
	headpic 		varchar(100) 	default '' comment '头像地址',
	create_time		datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment='热门推荐房间';
