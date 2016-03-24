insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000016', 'OSS.ACCESS.KEY.ID', 1, 'OSS Server access ID', 'ptZVa4kLuanuQklf', 'ptZVa4kLuanuQklf', 1, '00000000000000000000000000000001');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000017', 'OSS.ACCESS.KEY.SECRET', 1, 'OSS Server access secret', 'E0oiaAmMuJMn3EN3IxvVa0ISY4a7Sg', 'E0oiaAmMuJMn3EN3IxvVa0ISY4a7Sg', 1, '00000000000000000000000000000001');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000018', 'OSS.NET.DOMAINNAME', 1, 'OSS 内网域名', 'oss-cn-hangzhou-internal.aliyuncs.com', 'oss-cn-hangzhou-internal.aliyuncs.com', 1, '00000000000000000000000000000001');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000019', 'OSS.SERVER.BUCKETNAME', 1, 'OSS BUCKETNAME', 'lansha', 'lansha', 1, '00000000000000000000000000000001');

-- 20160217
alter table yw_user add(token_id varchar(100) null comment '设备号（消息推送）');


insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000020', 'LANSHA.UMENG.APPKEY', 1, '友盟推送AppKey', '5681f04767e58e6b230033cb', '5681f04767e58e6b230033cb', 1, '00000000000000000000000000000001');

insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000021', 'LANSHA.UMENG.APPMASTERSCRET', 1, '友盟推送appMasterSecret', '5ens1w96mresygqzqmzcqzle6tcj6ieu', '5ens1w96mresygqzqmzcqzle6tcj6ieu', 1, '00000000000000000000000000000001');

update sys_option set nowvalue = 'http://oss.lansha.tv' where iniid = 'SYS.UPLOAD.HOST.PATH';


-- 20160225
-- 房间禁言用户
create table lansha_room_blacklist (
	id 			char(32) 		not null,
	room_id		char(32)		not null comment '房间id',
	user_id		char(32)		not null comment '被禁言用户id',
	im_room		varchar(100)	not null comment 'im房间名称',
	admin_id	char(32)		not null comment '房管用户id',
	type		varchar(5)		not null comment '类型(1:禁言1小时,2:禁言1天,3:禁言)',
	valid_time	datetime		null comment '有效时间',
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '房间禁言用户';

alter table lansha_room_blacklist add index idx_im_room (im_room, user_id);
alter table lansha_room_blacklist add index idx_room_id (room_id, user_id);

-- 发言得虾米概率
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000224', 'LANSHA.IM.XIAOMI.PROBABILITY', 1, '发言得虾米概率(%)', '5', '5', 24, '00000000000000000000000000000002');

-- 发言得虾米记录
create table lansha_im_give_log (
	id 			char(32) 		not null,
	room_id		char(32)		not null comment '房间id',
	user_id		char(32)		not null comment '用户id',
	xiami		integer			not null comment '赠送虾米数量',			
	create_time	datetime		null comment '创建时间',
	
	primary key (id)
)engine=innodb default charset=utf8 comment '发言得虾米记录';
alter table lansha_im_give_log add index idx_user_id (user_id);
