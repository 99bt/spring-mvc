-- 初始化数据

-- 系统参数
delete from sys_option_type;
insert into sys_option_type(id, name, orderid)
value('00000000000000000000000000000001', '系统参数', 1);

delete from sys_option;
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000001', 'SYS.NAME', 1, '网络名称', '蓝鲨TV', '蓝鲨TV', 1, '00000000000000000000000000000001');
-- 静态文件版本号
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000002', 'SYS.STATIC.VERSION', 1, '静态文件版本号', '', '', 2, '00000000000000000000000000000001');
-- 短信服务是否启用
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000003', 'SYS.MT.USE', 1, '短信服务是否启用', '1', '1', 3, '00000000000000000000000000000001');

-- 短信发送服务器
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000005', 'SYS.MT.SERVER', 1, '短信服务-发送服务器', 'http://221.179.180.158:9007', 'http://221.179.180.158:9007', 5, '00000000000000000000000000000001');
-- 短信服务账户
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000006', 'SYS.MT.OPEN_ID', 1, '短信服务-账户', 'shrtwl', 'shrtwl', 6, '00000000000000000000000000000001');
-- 短信服务密码
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000007', 'SYS.MT.OPEN_PASS', 1, '短信服务-密码', 'shrt123', '', 7, '00000000000000000000000000000001');

-- 邮件设置
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000008', 'SYS.EMAIL.SMTP', 1, '邮件服务-SMTP服务地址', '', '', 8, '00000000000000000000000000000001');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000009', 'SYS.EMAIL.USER', 1, '邮件服务-帐户名', '', '', 9, '00000000000000000000000000000001');
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000010', 'SYS.EMAIL.PASSWORD', 1, '邮件服务-密码', '', '', 10, '00000000000000000000000000000001');

delete from sys_option where id = '00000000000000000000000000000011';
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000011', 'SYS.INDEX', 1, '首页地址', '', '', 11, '00000000000000000000000000000001');

-- store 目录
delete from sys_option where id = '00000000000000000000000000000012';
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000012', 'SYS.STORE.PATH', 1, 'store目录', '/opt/data/', '/opt/data_lansha/', 12, '00000000000000000000000000000001');

-- 域名
delete from sys_option where id = '00000000000000000000000000000013';
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000013', 'SYS.HOST.PATH', 1, '域名地址', 'http://www.lansha.tv', 'http://www.lansha.tv', 0, '00000000000000000000000000000001');

-- cdn静态文件域名
insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000014', 'SYS.CDN.HOST.PATH', 1, 'CDN域名缓存地址', 'http://cdn.lansha.tv', '', 14, '00000000000000000000000000000001');

-- 添加默认的管理员（密码md5加密"123456"）
delete from admin_user where username = 'admin';
insert into admin_user(id, username, password, state, create_time, is_sys)
value('00000000000000000000000000000001', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', now(), '1');

-- 系统版本
delete from sys_version where id = '00000000000000000000000000000001';
insert into sys_version(id, name, version, build, create_time, update_time)
value('00000000000000000000000000000001', '遥望-蓝鲨', '1.0.0', '20151130', now(), now());

-- 模块
delete from sys_model;
insert into sys_model(id, parent_id, name, img, is_use, order_id, create_time)
value('00000000000000000000000000000001', '00000000000000000000000000000000', '管理信息', '/static/images/leftico01.png', '1', '1', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000010001', '00000000000000000000000000000001', '系统管理', '/admin/system/system.html', '1', '1', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000010002', '00000000000000000000000000000001', '角色管理', '/admin/role/role.html', '1', '2', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000010003', '00000000000000000000000000000001', '管理员管理', '/admin/adminUser/adminUser.html', '1', '3', now());
insert into sys_model(id, parent_id, name, url, is_use, order_id, create_time)
value('00000000000000000000000000010004', '00000000000000000000000000000001', '日志管理', '/admin/log/log.html', '1', '4', now());

-- 角色
delete from admin_role;
insert into admin_role(id, name, state, is_sys, display_order, create_time)
value('00000000000000000000000000000001', '默认', '1', '1', '1', now());

-- 角色对应权限
delete from admin_role_model;
insert into admin_role_model(id, role_id, model_id)
value('00000000000000000000000000000001', '00000000000000000000000000000001', '00000000000000000000000000000001');

insert into admin_role_model(id, role_id, model_id)
value('00000000000000000000000000000003', '00000000000000000000000000000001', '00000000000000000000000000010001');
insert into admin_role_model(id, role_id, model_id)
value('00000000000000000000000000000004', '00000000000000000000000000000001', '00000000000000000000000000010002');
insert into admin_role_model(id, role_id, model_id)
value('00000000000000000000000000000005', '00000000000000000000000000000001', '00000000000000000000000000010003');
insert into admin_role_model(id, role_id, model_id)
value('00000000000000000000000000000010', '00000000000000000000000000000001', '00000000000000000000000000010004');

-- 管理员对应角色
delete from admin_to_role;
insert into admin_to_role(id, admin_id, role_id, create_time)
value('00000000000000000000000000000001', '00000000000000000000000000000001', '00000000000000000000000000000001', now());

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
