-- 创建库
create database lansha character set utf8; 

-- 参数类型
drop table if exists sys_option_type;
create table sys_option_type (
	id          char(32)		not null,
	name 		varchar(50) 	not null,		-- 名称
	orderid 	integer 		not null,		-- 排序号

	primary key (id)
)engine=innodb default charset=utf8;

-- 系统参数
drop table if exists sys_option;
create table sys_option(
	id          char(32)		not null,
	iniid 		varchar(50) 	not null,			-- 编号
	name 		varchar(50) 	not null,			-- 名称
	dvalue 		text		 	null,				-- 默认值
	nowvalue 	text		 	null,				-- 当前值
	description varchar(200) 	null,				-- 说明
	viewable 	integer not 	null,				-- 范围
	orderid 	integer not 	null,				-- 排序号
	validatejs 	varchar(100) 	null,				-- 验证规则
	type_id		char(32) 		not null,			-- 类型id

	primary key (id)
)engine=innodb default charset=utf8;

-- 管理员用户表
drop table if exists admin_user;
create table admin_user (
	id              char(32)		not null,
	username		varchar(15) binary		not null,	-- 用户名
	password		varchar(35)		null,				-- 密码md5加密
	photo			varchar(30)		null,				-- 头像路径
	state 			char			not null,			-- 状态（0：删除。1：正常，2：关闭）
	create_time		datetime		not null,			-- 创建时间
	login_time		datetime		null,				-- 最后登录时间
	login_ip		varchar(100)	null,				-- 最后登录ip
	is_sys 			char(1) 		null,				-- 是否系统内置
	email 			varchar(100) 	null,				-- 邮箱
	telphone 		varchar(100) 	null,				-- 手机号码
	realname 		varchar(20) 	null,

	primary key (id)
)engine=innodb default charset=utf8;

-- 管理员登录日志表
drop table if exists log_admin_login;
create table log_admin_login (
	id              char(32)		not null,
	user_id			char(32)		not null,			-- 用户id
	login_time		datetime		null,				-- 最后登录时间
	login_ip		varchar(100)	null,				-- 最后登录ip

	primary key (id)
)engine=innodb default charset=utf8;

-- 系统日志表
drop table if exists log_system;
create table log_system (
	id              char(32)		not null,
	type			varchar(50)		null,
	value			text			null,
	create_time		datetime		not null,			-- 创建时间
	
	primary key (id)
)engine=innodb default charset=utf8;

-- 系统版本
drop table if exists sys_version;
create table sys_version (
    id          char(32)		not null,
    name		varchar(20)		not null,
    version		varchar(10)		not null,
    build		varchar(10)		not null,
    create_time datetime		not null,
    update_time	datetime		null,
    
    primary key  (id)
) engine=innodb default charset=utf8;

-- 后台模块
drop table if exists sys_model;
create table sys_model (
    id          char(32)		not null,
    parent_id	char(32)		not null,
    name		varchar(20)		not null,
    img			varchar(30)		null,
    url			varchar(50)		null,
    is_use		char(1)			not null,
	order_id	integer			not null,
    create_time datetime		not null,
    
    primary key  (id)
) engine=innodb default charset=utf8;

-- 后台角色
drop table if exists admin_role;
create table admin_role (
	id              char(32)		not null,
	name			varchar(20)		not null,			-- 角色名
	state			char(1)			not null,			-- 状态（0：删除，1：正常，2：停用）
	is_sys			char(1)			not null,			-- 是否系统内置（0：否，1：是）
	display_order	integer			null,				-- 排序号
	create_time		datetime		not null,			-- 创建时间
	index_url 		varchar(200) 	null,

	primary key (id)
)engine=innodb default charset=utf8;

-- 角色对应权限表
drop table if exists admin_role_model;
create table admin_role_model (
	id              char(32)		not null,
	role_id			char(32)		not null,			-- 角色id
	model_id		char(32)		not null,			-- 用户id

	primary key (id)
)engine=innodb default charset=utf8;

-- 管理员对应角色表
drop table if exists admin_to_role;
create table admin_to_role (
	id              char(32)		not null,
	admin_id		char(32)		not null,			-- 用户id
	role_id			char(32)		not null,			-- 角色id
	create_time		datetime		not null,			-- 创建时间

	primary key (id)
)engine=innodb default charset=utf8;

-- 微代码
create table sys_mcode_list  (
   id                   char(32)                 not null,
   name           		varchar(50),
   length         		int,
   type 		        char(1),
   remark        		varchar(255),
   is_using             char(1),
   this_id 				varchar(20) not null,
   
   primary key (id)
)engine=innodb default charset=utf8;

create table sys_mcode_detail  (
   id                   char(32)                  not null,
   list_id             	varchar(20)               not null,
   this_id              varchar(20)               not null,
   content        		varchar(255),
   is_using             char(1),
   mcode_type           char(1),
   display_order        integer,

   primary key (id)
)engine=innodb default charset=utf8;

-- 短信发送日志
create table log_mt(
	id				integer			not null auto_increment,
	telphone		varchar(20)		null comment '手机号码',
	time			datetime		null comment '发送时间',
	ip				varchar(100)	null comment '下载ip',
	content			varchar(200)	null comment '发送内容',
	error			text			null comment '错误消息',
	code			varchar(10)		null comment '验证码',
	status			char(1)			null comment '状态（0：有效，1：已验证，2：发送失败）',

	primary key (id)
)engine=innodb default charset=utf8;

-- 邮件发送日志
create table log_email(
	id				integer			not null auto_increment,
	email			varchar(1000)	null comment '邮箱',
	time			datetime		null comment '发送时间',
	title			varchar(100)	null comment '标题',
	content			text			null comment '发送内容',
	error			text			null comment '错误消息',
	status			char(1)			null comment '状态（0：有效，1：已验证，2：发送失败）',

	primary key (id)
)engine=innodb default charset=utf8;
