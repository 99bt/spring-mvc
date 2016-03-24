-- 创建索引
create index idx_log_user_login_userid on log_user_login(user_id);
create index idx_yw_user_room_history_uid on yw_user_room_history(uid);
create index idx_yw_user_room_relation_uid on yw_user_room_relation(uid);
create index idx_lansha_gift_user_userid on lansha_gift_user(user_id);
create index idx_lansha_user_gift_stock_userid on lansha_user_gift_stock(user_id);
create index idx_lansha_user_record_userid on lansha_user_record(user_id);

create index idx_yw_user_username on yw_user(username);
alter table log_system add index idx_create_time (create_time);

-- 20160205
alter table activity_user_stock add index idx_user_id (user_id);
alter table yw_user add index idx_token (token);
alter table yw_user add index idx_regiest_device_id (regiest_device_id);
alter table yw_user add index idx_nickname (nickname);
alter table yw_user add index idx_parent_id (parent_id);
alter table activity_gift_stock add index idx_user_id (user_id, item_id);
alter table activity_gift_stock add index idx_item_id (item_id, create_time);
alter table yw_user_room_relation add index idx_room_id (room_id);
alter table lansha_live_history add index idx_room_id (room_id);

alter table yw_user add index idx_create_time (create_time);

alter table yw_user_room_history add index idx_status_create_time (status, create_time);
