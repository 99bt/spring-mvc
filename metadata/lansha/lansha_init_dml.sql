-- ================================== 菜单 ===========================
delete from sys_model where id = '00000000000000000000000000000002' or parent_id = '00000000000000000000000000000002';
insert into sys_model(id, parent_id, name, img, is_use, order_id, create_time)
value('00000000000000000000000000000002', '00000000000000000000000000000000', '游戏管理', '/static/images/leftico01.png', '1', '2', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000020001', '00000000000000000000000000000002', '游戏类别', '/admin/game/gameCategory.html', '1', '1', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000020002', '00000000000000000000000000000002', '游戏维护', '/admin/game/game.html', '1', '2', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000020003', '00000000000000000000000000000002', '广告管理', '/admin/ad/adManage.html', '1', '3', now());

delete from sys_model where id = '00000000000000000000000000000003' or parent_id = '00000000000000000000000000000003';
insert into sys_model(id, parent_id, name, img, is_use, order_id, create_time)
value('00000000000000000000000000000003', '00000000000000000000000000000000', '主播管理', '/static/images/leftico01.png', '1', '3', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000030001', '00000000000000000000000000000003', '房间管理', '/admin/room/room.html', '1', '1', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000030003', '00000000000000000000000000000003', '主播审核', '/admin/audit/audit.html', '1', '2', now());

delete from sys_model where id = '00000000000000000000000000000004' or parent_id = '00000000000000000000000000000004';
insert into sys_model(id, parent_id, name, img, is_use, order_id, create_time)
value('00000000000000000000000000000004', '00000000000000000000000000000000', '会员管理', '/static/images/leftico01.png', '1', '4', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000040001', '00000000000000000000000000000004', '会员管理', '/admin/ywuser/ywuser.html', '1', '1', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000040002', '00000000000000000000000000000004', '观看记录', '/admin/record/recordManage.html', '1', '2', now());
-- insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
-- value('00000000000000000000000000040003', '00000000000000000000000000000004', '会员审核', '', '1', '3', now());


insert into sys_option_type(id, name, orderid)
value('00000000000000000000000000000002', '蓝鲨参数', 2);

-- ==================================openfire 聊天===========================
-- openfire地址
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000201', 'OPENFIRE.URL', 1, 'openfire地址', 'im.lansha.tv', 'im.lansha.tv', 1, '00000000000000000000000000000002');
-- openfire端口
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000202', 'OPENFIRE.PORT', 1, 'openfire端口', '5432', '5432', 2, '00000000000000000000000000000002');
-- openfire配置
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000203', 'OPENFIRE.CONFERENCE', 1, 'openfire配置', 'conference.0.0.0.0', 'conference.iz62gl46j2xz', 3, '00000000000000000000000000000002');
-- 管理员
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000204', 'OPENFIRE.ADMIN.USER', 1, 'openfire配置', 'admin', 'admin', 4, '00000000000000000000000000000002');
-- 管理员密码
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000205', 'OPENFIRE.ADMIN.PASSWORD', 1, 'openfire配置', 'admin', 'lansha123', 5, '00000000000000000000000000000002');

-- ================================流媒体====================================
-- 流媒体地址
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000206', 'FMS.RTMP', 1, 'FMS中的RTMP流媒体地址', 'rtmp://live.lansha.tv/live/', 'rtmp://live.lansha.tv/live/', 6, '00000000000000000000000000000002');
-- 上行流域名
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000207', 'FMS.RTMP.LIVE', 1, 'FMS网宿上行流域名', '', '', 7, '00000000000000000000000000000002');
-- 下行流域名
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000208', 'FMS.RTMP.SHOW', 1, 'FMS网宿下行流域名', '', '', 8, '00000000000000000000000000000002');
-- 回调地址
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000209', 'FMS.CALL.URL', 1, 'FMS远程调用主播状态接口地址', '', 'http://qualiter.wscdns.com/api/playerCount.jsp?u=show.98u.com&n=46wagnzhi&t=#t#&r=#r#&k=#k#', 9, '00000000000000000000000000000002');
-- 回调key
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000210', 'FMS.CALL.KEY', 1, 'FMS远程调用主播状态接口KEY', '39ADA03AB97AC05', '39ADA03AB97AC05', 10, '00000000000000000000000000000002');

-- 领取蓝鲨币时间
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000211', 'LANSHA.BI.TIME', 1, '领取蓝鲨币时间(秒)', '1800', '1800', 11, '00000000000000000000000000000002');

