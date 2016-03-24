/*
Navicat MySQL Data Transfer

Source Server         : mysqldb163
Source Server Version : 50519
Source Host           : 192.168.0.163:3306
Source Database       : lansha

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2016-02-26 15:24:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for yw_user_room_contract
-- ----------------------------
DROP TABLE IF EXISTS `yw_user_room_contract`;
CREATE TABLE `yw_user_room_contract` (
  `user_id` char(32) NOT NULL,
  `room_id` char(32) NOT NULL,
  `time_target` int(11) DEFAULT '0' COMMENT '直播时长指标',
  `vaild_days` int(11) DEFAULT '0' COMMENT '有效天数指标',
  `ticket_target` int(11) DEFAULT '0' COMMENT '日票指标',
  `salary` decimal(11,2) DEFAULT '0.00' COMMENT '基本薪资',
  `manager` varchar(100) DEFAULT NULL COMMENT '负责人',
  `examine` int(11) DEFAULT '1' COMMENT '是否参加考核(0:不参加,1:参加)',
  `start_time` datetime DEFAULT NULL COMMENT '签约开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '签约结束时间',
  `status` int(11) DEFAULT '1' COMMENT '状态(0:无效,1:有效)',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`user_id`,`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签约主播管理';



DROP TABLE IF EXISTS `yw_user_room_day_data`;
CREATE TABLE `yw_user_room_day_data` (
  `room_id` char(32) NOT NULL,
 `user_id` char(32) DEFAULT NULL COMMENT 'userId',
  `ranking_id` char(32) DEFAULT NULL COMMENT '关联lansha_room_ranking',
  `score` decimal(11,5) DEFAULT NULL COMMENT '当天积分',
  `ranking` int(11) DEFAULT NULL COMMENT '积分排名',
  `pay_standard` decimal(11,2) DEFAULT NULL COMMENT '当天收入标准',
  `salary` decimal(11,2) DEFAULT NULL COMMENT '当天应得收入',
  `focus_on` int(11) DEFAULT NULL COMMENT '当天关注数',
  `focus_off` int(11) DEFAULT NULL COMMENT '当天取消关注数',
  `bonus` decimal(11,2) DEFAULT NULL COMMENT '奖金',
  `forfeit` decimal(11,2) DEFAULT NULL COMMENT '罚金',
  `day` date DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`ranking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间日数据';


DROP TABLE IF EXISTS `yw_room_month_settlement`;
CREATE TABLE `yw_room_month_settlement` (
  `month` varchar(6) NOT NULL COMMENT '月份(yyyyMM)',
  `room_id` char(32) NOT NULL COMMENT '房间id',
  `user_id` char(32) DEFAULT NULL COMMENT 'userId',
  `live_time` int(11) DEFAULT 0 COMMENT '直播时长',
  `day_num` int(11) DEFAULT 0 COMMENT '有效天数',
  `bonus` decimal(11,2) DEFAULT NULL COMMENT '奖金',
  `forfeit` decimal(11,2) DEFAULT NULL COMMENT '罚金',
  `due` decimal(11,2) DEFAULT NULL COMMENT '应得收入',
  `real` decimal(11,2) DEFAULT NULL COMMENT '实得收入',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`month`,`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间月结';

DROP TABLE IF EXISTS `yw_user_pay`;
CREATE TABLE `yw_user_pay` (
  `card_no` varchar(20) NOT NULL COMMENT '银行卡号',
  `user_id` char(32) DEFAULT NULL COMMENT '用户ID,关联yw_user',
  `name` varchar(50) DEFAULT NULL  COMMENT '开户人姓名',
  `bank` varchar(200) DEFAULT NULL  COMMENT '开户银行',
  PRIMARY KEY (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='用户财物信息表';

INSERT INTO `lansha_gift` (`id`, `name`, `status`, `bi`, `img`, `orderid`, `create_time`) VALUES ('00000000000000000000000000000004', '日票', '4', 4, '', 4, NOW());
INSERT INTO `lansha_gift` (`id`, `name`, `status`, `bi`, `img`, `orderid`, `create_time`) VALUES ('00000000000000000000000000000005', '收到的日票', '5', 5, '', 5, NOW());

UPDATE `sys_model` SET `order_id` = `order_id` + 1 WHERE `parent_id` = '00000000000000000000000000000003' AND `order_id` >= 2;
INSERT INTO `sys_model` (`id`, `parent_id`, `name`, `img`, `url`, `is_use`, `order_id`, `create_time`) VALUES ('00000000000000000000000000030007', '00000000000000000000000000000003', '签约主播管理', NULL, '/admin/room/contract.html', '1', 2, NOW());

ALTER TABLE lansha_room_ranking ADD ticket_number int(11) DEFAULT 0 COMMENT '日票数';
ALTER TABLE lansha_room_ranking ADD cancel_number int(11) DEFAULT 0 COMMENT '取消关注';
ALTER TABLE yw_user_room_apply ADD card_no int(11) DEFAULT NULL COMMENT '银行卡号';



UPDATE sys_option SET dvalue ='1,1,1,1,1,1,1',nowvalue='1,1,1,1,1,1,1' WHERE id = '00000000000000000000000000000221'

insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000024', 'LANSHA.ROOM.RANKING.EFFECTIVE', 1, '有效时长标准', '30', '30', 1, '00000000000000000000000000000001');


INSERT INTO `sys_model` (`id`, `parent_id`, `name`, `img`, `url`, `is_use`, `order_id`, `create_time`) VALUES ('00000000000000000000000000030006', '00000000000000000000000000000003', '主播工资报表', NULL, '/admin/room/salaryReport.html', '1', 6, NOW());
INSERT INTO `sys_option` (`id`, `iniid`, `name`, `dvalue`, `nowvalue`, `description`, `viewable`, `orderid`, `validatejs`, `type_id`) VALUES ('00000000000000000000000000000023', 'LANSHA.ROOM.RANKING.MAX', '有效时长上限', '4', '4', NULL, '1', '1', NULL, '00000000000000000000000000000001');

INSERT INTO `activity_gift` ( `id`, `item_id`, `name`, `status`, `img`, `order_id`, `remark`, `create_time`, `money`, `type`, `bi`, `object_id`, `number`, `stock` ) VALUES (  '00000000000000000000000000002011',  '00000000000000000000000000000002',  '1个Q币',  '1',  NULL,  '5',  NULL,  NOW(),  '1',  '3',  '1',  '',  '0',  '1' );

ALTER TABLE lansha_room_ranking ADD score decimal(11,2)  DEFAULT 0 COMMENT '积分';
UPDATE activity_gift SET `status` = '0' WHERE id = '00000000000000000000000000002004'

alter table lansha_gift_user add index idx_gift_id (gift_id);

insert into sys_option(id, iniid, viewable, name, dvalue, nowvalue, orderid, type_id)
value('00000000000000000000000000000024', 'LANSHA.ROOM.VALID.TIME', 1, '有效播放时长区间','8,24', '8,24', 1, '00000000000000000000000000000001');

ALTER TABLE yw_user_room_day_data ADD live_time int(11) DEFAULT 0  COMMENT '有效直播时长（分）';

