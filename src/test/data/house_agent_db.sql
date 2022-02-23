/*
Navicat MySQL Data Transfer

Source Server         : localhost3306
Source Server Version : 80017
Source Host           : localhost:3306

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2022-02-22 19:54:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
                           `admin_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
                           `password` varchar(20) NOT NULL COMMENT '密码',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
                           `username` varchar(20) NOT NULL,
                           `admin_name` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='管理员信息表';

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', '2022-01-17 15:36:15', '2022-02-14 14:49:15', 'admin', 'admin');

-- ----------------------------
-- Table structure for t_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_agent`;
CREATE TABLE `t_agent` (
                           `agent_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '中介id',
                           `agent_name` varchar(20) NOT NULL COMMENT '中介名',
                           `password` varchar(20) NOT NULL COMMENT '密码',
                           `sex` char(1) DEFAULT NULL COMMENT '性别 (1：男，0：女)',
                           `phone_number` varchar(15) NOT NULL COMMENT '电话号码',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
                           PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='中介信息表';

-- ----------------------------
-- Records of t_agent
-- ----------------------------
INSERT INTO `t_agent` VALUES ('1', '李四', '233444', '1', '13412345688', '2022-01-16 23:04:07', '2022-02-21 15:23:19');
INSERT INTO `t_agent` VALUES ('3', 'Park', '123134', '1', '12312345678', '2022-01-17 15:10:54', '2022-01-17 15:10:54');
INSERT INTO `t_agent` VALUES ('4', '小六', '123134', '0', '12312345678', '2022-01-17 15:16:43', '2022-02-15 09:40:16');
INSERT INTO `t_agent` VALUES ('6', 'Hello', '233333', '0', '13312323456', '2022-01-17 15:55:45', '2022-01-17 15:55:45');
INSERT INTO `t_agent` VALUES ('7', 'Hello2', '233333', '0', '13312323457', '2022-01-17 15:57:30', '2022-02-14 21:07:54');
INSERT INTO `t_agent` VALUES ('8', 'Tom', '123456', '1', '12312345678', '2022-01-17 16:56:07', '2022-01-17 16:56:07');
INSERT INTO `t_agent` VALUES ('9', 'Jerry', '123456', '1', '12312345678', '2022-01-17 16:58:55', '2022-01-17 16:58:55');
INSERT INTO `t_agent` VALUES ('10', '你好', '123456', '1', '12312345678', '2022-01-17 17:00:29', '2022-01-17 17:00:29');
INSERT INTO `t_agent` VALUES ('11', 'Wang', '123232', '1', '13789891234', '2022-01-18 13:57:46', '2022-01-18 13:57:46');
INSERT INTO `t_agent` VALUES ('12', '啊哈', '123232', '1', '13789891234', '2022-01-18 13:57:57', '2022-01-18 13:57:57');
INSERT INTO `t_agent` VALUES ('13', '钱五', '234678', '1', '15683922990', '2022-01-19 15:43:57', '2022-01-19 15:43:57');
INSERT INTO `t_agent` VALUES ('14', 'abcss', '123456', '1', '13925224861', '2022-02-14 21:26:23', '2022-02-14 21:26:23');
INSERT INTO `t_agent` VALUES ('15', 'abcddd', '123456', '1', '1213218221', '2022-02-14 21:39:13', '2022-02-14 21:39:13');
INSERT INTO `t_agent` VALUES ('16', 'abcdefe', '123456', '1', '13658912227', '2022-02-15 09:36:27', '2022-02-15 09:36:27');
INSERT INTO `t_agent` VALUES ('17', '钱五', '234678', '1', '15683922997', '2022-02-15 09:39:29', '2022-02-15 09:39:29');
INSERT INTO `t_agent` VALUES ('18', '恩比德', '123456', '1', '135589122547', '2022-02-21 11:14:03', '2022-02-21 11:14:03');
INSERT INTO `t_agent` VALUES ('19', '李六', '123456', '0', '139523232144', '2022-02-21 15:23:34', '2022-02-21 15:23:34');

-- ----------------------------
-- Table structure for t_house
-- ----------------------------
DROP TABLE IF EXISTS `t_house`;
CREATE TABLE `t_house` (
                           `house_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '房屋id',
                           `owner_id` bigint(20) NOT NULL COMMENT '房主用户id',
                           `agent_id` bigint(20) NOT NULL COMMENT '中介id',
                           `house_name` varchar(20) NOT NULL COMMENT '楼盘名称',
                           `is_published` char(1) NOT NULL COMMENT '发布状态，未发布：0；已发布：1',
                           `is_rent` char(1) NOT NULL COMMENT '出租状态，未出租：0；已出租：1',
                           `is_sold` char(1) NOT NULL COMMENT '销售状态，未销售：0；已销售：1',
                           `is_finished` char(1) NOT NULL COMMENT '交易状态，未完成：0；已完成：1',
                           `area` double NOT NULL COMMENT '楼盘面积',
                           `rent_money` double DEFAULT NULL COMMENT '租金/月',
                           `unit_price` double DEFAULT NULL COMMENT '单元价/平方米',
                           `room` varchar(20) NOT NULL COMMENT '厅室',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
                           PRIMARY KEY (`house_id`),
                           KEY `agentId` (`agent_id`),
                           KEY `ownerId` (`owner_id`),
                           CONSTRAINT `t_house_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `t_user` (`user_id`),
                           CONSTRAINT `t_house_ibfk_2` FOREIGN KEY (`agent_id`) REFERENCES `t_agent` (`agent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='房屋信息表';

-- ----------------------------
-- Records of t_house
-- ----------------------------
INSERT INTO `t_house` VALUES ('1', '2', '1', '滕王阁', '1', '0', '1', '2', '3000', '4000', '4', '三室一厅', '2022-01-17 10:32:48', '2022-02-15 13:25:48');
INSERT INTO `t_house` VALUES ('2', '1', '4', '岳阳楼', '1', '1', '0', '1', '2334', '2345', '5', '四室二厅', '2022-01-17 17:53:31', '2022-02-15 14:27:16');
INSERT INTO `t_house` VALUES ('3', '2', '1', '商品房1号', '1', '0', '1', '2', '2313.33', '1233', '4', '三室二厅', '2022-01-17 17:55:14', '2022-02-20 23:43:20');
INSERT INTO `t_house` VALUES ('9', '5', '4', '商品房2号', '1', '1', '0', '1', '233', '123.22', '6', '三室二厅', '2022-01-18 23:27:01', '2022-02-15 10:10:28');
INSERT INTO `t_house` VALUES ('10', '3', '1', '商品房3号', '0', '0', '1', '1', '234', '1283.22', '5.3', '三室二厅', '2022-01-19 10:55:13', '2022-02-15 09:51:32');
INSERT INTO `t_house` VALUES ('11', '6', '3', '商品房4号', '1', '0', '1', '1', '2345', '4567', '8.9', '四室二厅', '2022-01-19 10:59:02', '2022-02-13 10:53:44');
INSERT INTO `t_house` VALUES ('12', '1', '3', '商品房5号', '1', '1', '0', '1', '11111', '4567', '8.9', '四室二厅', '2022-01-19 12:26:16', '2022-02-15 12:44:30');
INSERT INTO `t_house` VALUES ('13', '2', '1', '商品房6号', '1', '1', '0', '1', '2165', '46278', '3.2', '三室二厅', '2022-01-19 14:59:04', '2022-02-20 22:56:23');
INSERT INTO `t_house` VALUES ('14', '1', '1', '商品房7号', '1', '0', '1', '2', '2165', '46278', '3.2', '三室二厅', '2022-01-19 15:16:43', '2022-02-15 12:53:04');
INSERT INTO `t_house` VALUES ('15', '2', '1', '商品房8号', '1', '1', '0', '0', '2165', '46278', '3.2', '三室二厅', '2022-01-19 15:41:54', '2022-02-20 23:42:39');
INSERT INTO `t_house` VALUES ('16', '2', '1', '商品房9号', '0', '0', '1', '1', '2165', '46278', '3.2', '三室二厅', '2022-01-19 18:08:26', '2022-02-15 10:03:33');
INSERT INTO `t_house` VALUES ('17', '3', '1', '商品房11号', '1', '1', '0', '1', '2165', '46278', '3.2', '三室二厅', '2022-01-21 20:19:26', '2022-02-13 10:55:49');
INSERT INTO `t_house` VALUES ('18', '2', '1', '富贵园', '0', '0', '1', '0', '180', null, '2.1', '三室两厅', '2022-02-13 11:56:07', '2022-02-15 10:01:22');
INSERT INTO `t_house` VALUES ('19', '2', '3', '鸿荣源', '2', '1', '0', '0', '180', '3000', '0', '三室一厅', '2022-02-13 12:55:11', '2022-02-14 08:51:01');
INSERT INTO `t_house` VALUES ('20', '2', '1', '八号当铺', '0', '1', '0', '0', '230', '3000', '0', '三室两厅', '2022-02-15 10:06:49', '2022-02-20 22:56:39');
INSERT INTO `t_house` VALUES ('21', '2', '6', '九号当铺', '0', '0', '1', '0', '800', '0', '2.1', '三室两厅', '2022-02-15 10:07:09', '2022-02-15 10:07:09');
INSERT INTO `t_house` VALUES ('22', '1', '1', '十号当铺', '1', '0', '1', '2', '270', '0', '2.1', '三室一厅', '2022-02-15 13:57:18', '2022-02-21 10:55:51');
INSERT INTO `t_house` VALUES ('23', '2', '1', '十一号当铺', '1', '0', '1', '0', '167', '0', '2.1', '两室一厅', '2022-02-21 09:54:00', '2022-02-21 10:37:46');
INSERT INTO `t_house` VALUES ('24', '1', '1', '大鹏展翅', '1', '1', '0', '2', '180', '2000', '0', '三室一厅', '2022-02-21 11:05:03', '2022-02-21 11:06:58');
INSERT INTO `t_house` VALUES ('25', '2', '1', '恒大1号', '1', '0', '1', '2', '180', '0', '2.1', '三室一厅', '2022-02-21 15:05:53', '2022-02-21 15:09:17');
INSERT INTO `t_house` VALUES ('26', '1', '1', '恒大三号', '1', '0', '1', '2', '188', '0', '2.1', '三室一厅', '2022-02-21 15:13:28', '2022-02-21 15:16:38');
INSERT INTO `t_house` VALUES ('27', '1', '1', '恒大五号', '1', '1', '0', '2', '180', '2000', '0', '三室一厅', '2022-02-21 15:18:40', '2022-02-21 15:21:14');
INSERT INTO `t_house` VALUES ('28', '1', '1', '恒大七号', '0', '0', '1', '0', '188', '0', '2.1', '三室一厅', '2022-02-21 15:40:48', '2022-02-21 15:41:06');
INSERT INTO `t_house` VALUES ('29', '1', '1', '恒大八号', '1', '0', '1', '2', '180', '0', '2.1', '三室一厅', '2022-02-21 15:44:17', '2022-02-21 15:46:47');

-- ----------------------------
-- Table structure for t_rent_record
-- ----------------------------
DROP TABLE IF EXISTS `t_rent_record`;
CREATE TABLE `t_rent_record` (
                                 `rent_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `begin_time` datetime(6) NOT NULL,
                                 `house_id` bigint(20) DEFAULT NULL,
                                 `rent_length` int(11) DEFAULT NULL,
                                 `renter_id` bigint(20) DEFAULT NULL,
                                 `valid` char(1) NOT NULL COMMENT '不显示：0；已显示：1',
                                 PRIMARY KEY (`rent_id`),
                                 KEY `FK1kgkghvhowmwjehea4uomi4sr` (`house_id`),
                                 KEY `FKdlwbve31n65p1eycjl8b6ybd5` (`renter_id`),
                                 CONSTRAINT `FK1kgkghvhowmwjehea4uomi4sr` FOREIGN KEY (`house_id`) REFERENCES `t_house` (`house_id`),
                                 CONSTRAINT `FKdlwbve31n65p1eycjl8b6ybd5` FOREIGN KEY (`renter_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_rent_record
-- ----------------------------
INSERT INTO `t_rent_record` VALUES ('1', '2022-01-19 18:45:23.339000', '2', '3', '5', '1');
INSERT INTO `t_rent_record` VALUES ('2', '2022-01-23 22:13:08.618000', '12', '4', '2', '1');
INSERT INTO `t_rent_record` VALUES ('3', '2022-01-23 22:17:29.002000', '15', '2', '3', '1');
INSERT INTO `t_rent_record` VALUES ('7', '2022-02-15 10:10:28.873000', '9', '8', '2', '0');
INSERT INTO `t_rent_record` VALUES ('8', '2022-02-15 12:15:29.856000', '12', '7', '2', '0');
INSERT INTO `t_rent_record` VALUES ('9', '2022-02-15 12:44:30.808000', '12', '1', '2', '0');
INSERT INTO `t_rent_record` VALUES ('10', '2022-02-20 23:42:10.302000', '15', '1', '1', '0');
INSERT INTO `t_rent_record` VALUES ('11', '2022-02-21 11:06:26.325000', '24', '1', '2', '2');
INSERT INTO `t_rent_record` VALUES ('12', '2022-02-21 15:20:23.214000', '27', '1', '2', '2');

-- ----------------------------
-- Table structure for t_sale_record
-- ----------------------------
DROP TABLE IF EXISTS `t_sale_record`;
CREATE TABLE `t_sale_record` (
                                 `sale_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `buy_time` datetime(6) NOT NULL,
                                 `buyer_id` bigint(20) DEFAULT NULL,
                                 `house_id` bigint(20) DEFAULT NULL,
                                 `valid` char(1) NOT NULL COMMENT '不显示：0；已显示：1',
                                 PRIMARY KEY (`sale_id`),
                                 KEY `FKftsaghp2pqadvbmm3pxwkkvw7` (`house_id`),
                                 KEY `FK2n7wod73ssn40jsojjgg3p8ng` (`buyer_id`),
                                 CONSTRAINT `FK2n7wod73ssn40jsojjgg3p8ng` FOREIGN KEY (`buyer_id`) REFERENCES `t_user` (`user_id`),
                                 CONSTRAINT `FKftsaghp2pqadvbmm3pxwkkvw7` FOREIGN KEY (`house_id`) REFERENCES `t_house` (`house_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_sale_record
-- ----------------------------
INSERT INTO `t_sale_record` VALUES ('1', '2022-01-27 22:08:02.000000', '1', '3', '2');
INSERT INTO `t_sale_record` VALUES ('2', '2022-01-29 11:55:48.000000', '6', '14', '2');
INSERT INTO `t_sale_record` VALUES ('7', '2022-02-13 10:53:45.002000', '2', '11', '1');
INSERT INTO `t_sale_record` VALUES ('8', '2022-02-15 10:09:58.270000', '2', '14', '1');
INSERT INTO `t_sale_record` VALUES ('15', '2022-02-15 13:25:01.392000', '1', '1', '2');
INSERT INTO `t_sale_record` VALUES ('16', '2022-02-21 10:35:40.014000', '2', '23', '0');
INSERT INTO `t_sale_record` VALUES ('17', '2022-02-21 10:55:34.120000', '2', '22', '2');
INSERT INTO `t_sale_record` VALUES ('18', '2022-02-21 15:08:12.106000', '1', '25', '2');
INSERT INTO `t_sale_record` VALUES ('19', '2022-02-21 15:15:39.639000', '2', '26', '2');
INSERT INTO `t_sale_record` VALUES ('20', '2022-02-21 15:46:14.028000', '2', '29', '2');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
                          `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                          `username` varchar(20) NOT NULL COMMENT '用户名',
                          `password` varchar(20) NOT NULL COMMENT '密码',
                          `sex` char(1) DEFAULT NULL COMMENT '性别 (1：男，0：女)',
                          `phone_number` varchar(15) NOT NULL COMMENT '电话号码',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
                          PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '小明0', '122330', '1', '12332111288', '2022-01-16 21:58:27', '2022-02-21 15:22:40');
INSERT INTO `t_user` VALUES ('2', '小张', '233333', '0', '12333333318', '2022-01-16 21:58:27', '2022-02-14 21:38:40');
INSERT INTO `t_user` VALUES ('3', '小明2', '122332', '1', '12332111212', '2022-01-16 21:58:27', '2022-01-16 21:58:27');
INSERT INTO `t_user` VALUES ('4', '小明3', '122333', '0', '12332111219', '2022-01-16 21:58:27', '2022-02-14 21:13:37');
INSERT INTO `t_user` VALUES ('5', 'Tony', '122334', '1', '12332111214', '2022-01-16 21:58:27', '2022-01-17 00:00:30');
INSERT INTO `t_user` VALUES ('6', '小明5', '122335', '0', '12332111215', '2022-01-16 21:58:27', '2022-01-16 21:58:27');
INSERT INTO `t_user` VALUES ('7', 'Lucy2', '122336', '1', '12332111216', '2022-01-16 21:58:27', '2022-01-17 15:48:35');
INSERT INTO `t_user` VALUES ('8', '小明7', '122337', '0', '12332111218', '2022-01-16 21:58:27', '2022-02-14 21:04:09');
INSERT INTO `t_user` VALUES ('9', '小明8', '122338', '1', '12332111222', '2022-01-16 21:58:27', '2022-02-14 20:53:50');
INSERT INTO `t_user` VALUES ('10', '小明9', '122339', '0', '12332111217', '2022-01-16 21:58:27', '2022-02-14 17:27:47');
INSERT INTO `t_user` VALUES ('11', '老王', '233333', '1', '14523456723', '2022-01-18 14:04:03', '2022-01-18 14:04:03');
INSERT INTO `t_user` VALUES ('12', '大明', '233333', '1', '12333333313', '2022-01-18 14:51:50', '2022-01-18 14:51:50');
INSERT INTO `t_user` VALUES ('13', '小红', '123331', '0', '13523456723', '2022-01-19 11:11:08', '2022-01-19 11:11:08');
INSERT INTO `t_user` VALUES ('14', '小绿', '123331', '1', '13523456723', '2022-01-19 11:51:52', '2022-01-19 11:51:52');
INSERT INTO `t_user` VALUES ('15', 'Henry', '123333', '1', '394729347', '2022-01-19 15:42:54', '2022-01-19 15:42:54');
INSERT INTO `t_user` VALUES ('16', '小李', '123333', '1', '1345255252', '2022-01-19 16:50:54', '2022-01-19 16:50:54');
INSERT INTO `t_user` VALUES ('17', '你好', '123333', '1', '1345255252', '2022-01-21 20:18:51', '2022-01-21 20:18:51');
INSERT INTO `t_user` VALUES ('18', '对象', '123333', '1', '1345255252', '2022-01-23 20:43:26', '2022-01-23 20:43:26');
INSERT INTO `t_user` VALUES ('19', 'obj', '123456', '1', '13925224851', '2022-02-15 11:39:54', '2022-02-15 11:39:54');
INSERT INTO `t_user` VALUES ('20', 'joe', '123456', '1', '138122468741', '2022-02-15 11:40:54', '2022-02-15 11:40:54');
INSERT INTO `t_user` VALUES ('21', '特雷杨', '123456', '1', '1456978921114', '2022-02-21 11:16:03', '2022-02-21 11:16:03');
INSERT INTO `t_user` VALUES ('22', '张九', '123456', '1', '13982314586111', '2022-02-21 15:25:02', '2022-02-21 15:25:02');
