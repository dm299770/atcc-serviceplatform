/*
Navicat MySQL Data Transfer

Source Server         : locahost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : idm

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-04-30 18:00:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tc_oauth_refreshtoken
-- ----------------------------
DROP TABLE IF EXISTS `tc_oauth_refreshtoken`;
CREATE TABLE `tc_oauth_refreshtoken` (
  `KEY_ID` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `REFRESHTOKEN` varchar(50) DEFAULT NULL COMMENT '刷新码',
  `CLIENT_ID` varchar(50) DEFAULT NULL COMMENT '奇瑞分配的第三方账号',
  `APP_ACCOUNT_ID` varchar(50) DEFAULT NULL COMMENT '用户唯一标识',
  `EXPIREIN` varchar(50) DEFAULT NULL COMMENT '过期时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`KEY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tc_oauth_refreshtoken
-- ----------------------------
INSERT INTO `tc_oauth_refreshtoken` VALUES ('1', 'fdcaa4fe38fe999c702a46c3d112a165', 'oauth_clientid_baidu', '2807', '1544079726', null);
INSERT INTO `tc_oauth_refreshtoken` VALUES ('2', '05b6bccb71572423937b9ccf5dc64bae', 'oauth_clientid_aligenie', '2807', '1544755106', null);
INSERT INTO `tc_oauth_refreshtoken` VALUES ('3', 'd086b58d490daa4406f605fd1b6efc12', 'oauth_clientid_aligenie', 'null', '1549697687', null);
INSERT INTO `tc_oauth_refreshtoken` VALUES ('4', '76562de15fd47787f1c7a72d4cf5af01', 'oauth_clientid_miui', '18101671990', '1558682307', null);
INSERT INTO `tc_oauth_refreshtoken` VALUES ('5', '1de06266141b29d157555f0b1f3622cb', 'oauth_clientid_miui', 'e5792c05-fa1c-4ffa-9adb-aa66f2cd8863', '1558855125', null);
INSERT INTO `tc_oauth_refreshtoken` VALUES ('6', '11f97aee6739cfa558ed716b5e43c1cd', 'oauth_clientid_miui', 'bbf7d4eb-7945-4c2f-89d3-54150c5f1d26', '1558855379', null);

-- ----------------------------
-- Table structure for tc_oauth_thirdparty
-- ----------------------------
DROP TABLE IF EXISTS `tc_oauth_thirdparty`;
CREATE TABLE `tc_oauth_thirdparty` (
  `KEY_ID` decimal(8,0) DEFAULT NULL COMMENT '主键',
  `CLIENT_ID` varchar(50) DEFAULT NULL COMMENT '奇瑞分配的第三方账号',
  `CLIENT_SECRET` varchar(50) DEFAULT NULL COMMENT '奇瑞分配的第三方账号密码',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `SCOPE` varchar(50) DEFAULT NULL COMMENT '第三方访问权限范围',
  `REDIRECT_URI` varchar(300) DEFAULT NULL COMMENT '第三方重定向地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tc_oauth_thirdparty
-- ----------------------------
INSERT INTO `tc_oauth_thirdparty` VALUES ('3', 'oauth_clientid_jd', 'oauth_clientsecret_jd', null, 'read', 'https://alphadev.jd.com/engine/api/voice/oauth2/authorize/alpha/2a1c006a553e4860aecc9ad3e3a16cdb');
INSERT INTO `tc_oauth_thirdparty` VALUES ('2', 'oauth_clientid_baidu', 'oauth_clientsecret_baidu', null, 'scp01001', 'http://sandbox.codriver.baidu.com/codriverapi/oauthredirecturi');
INSERT INTO `tc_oauth_thirdparty` VALUES ('1', 'oauth_clientid_auto', 'oauth_clientsecret_auto', null, 'scp02001', 'https://www.amap.com/');
INSERT INTO `tc_oauth_thirdparty` VALUES ('4', 'oauth_clientid_aligenie', 'oauth_clientid_aligenie', null, 'scp03001', 'https://i.ai.mi.com/skills/bind/oauth/2.0/379326851299935232');
INSERT INTO `tc_oauth_thirdparty` VALUES ('5', 'oauth_clientid_miui', 'oauth_clientid_miui', null, 'scp04001', 'https://i.ai.mi.com/skills/bind/oaut');

-- ----------------------------
-- Table structure for tm_charge_account
-- ----------------------------
DROP TABLE IF EXISTS `tm_charge_account`;
CREATE TABLE `tm_charge_account` (
  `USER_ACCOUNT` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户外键',
  `BALANCE` varchar(255) DEFAULT NULL COMMENT '余额',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '最后修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tm_charge_account
-- ----------------------------
INSERT INTO `tm_charge_account` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', '0', '2018-12-19 16:24:34', null);
INSERT INTO `tm_charge_account` VALUES ('dedfa0ae-a01f-4c50-859f-a9e57d4eb262', '0', '2019-03-15 03:05:49', null);

-- ----------------------------
-- Table structure for tr_user_vin
-- ----------------------------
DROP TABLE IF EXISTS `tr_user_vin`;
CREATE TABLE `tr_user_vin` (
  `USER_ID` varchar(36) NOT NULL COMMENT '用户id',
  `VIN` varchar(17) DEFAULT NULL COMMENT '车辆唯一标识码 17位',
  `PLATE_NUM` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_SIX_PHONENUM` varchar(6) DEFAULT NULL,
  `ENGINE_NUM` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DEFAULT_VEHICLE` tinyint(4) DEFAULT NULL,
  `BINDING_DATE` datetime DEFAULT NULL COMMENT '绑定时间',
  `UNBINDING_DATE` datetime DEFAULT NULL COMMENT '解绑日期',
  `IS_EFFCTIVE` tinyint(1) DEFAULT NULL COMMENT '是否有效 0-无效,1-有效',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tr_user_vin
-- ----------------------------
INSERT INTO `tr_user_vin` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', 'A123456789012345', '沪A79023', '671990', 'WDDNG5GB0AA', '0', '2018-10-31 16:15:52', '2018-10-31 16:10:32', '0', '2018-10-31 16:15:52');
INSERT INTO `tr_user_vin` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', 'A123456789054321', '沪A64634', '671990', 'BWDBC6DB0AA', '0', '2018-10-31 16:20:14', null, '1', '2018-10-31 16:20:14');
INSERT INTO `tr_user_vin` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', 'NISSAN0000000000', '沪A22213', '671990', 'RMDBC6DB0AA', '0', '2018-10-31 16:21:01', '2019-03-21 14:03:51', '0', '2018-10-31 16:21:01');
INSERT INTO `tr_user_vin` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', 'B323456789054321', '沪A22213', '671990', 'RMDBC6DB0AA', '0', '2019-03-15 03:28:39', '2019-03-15 15:03:45', '0', '2019-03-15 03:28:39');
INSERT INTO `tr_user_vin` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', 'B323456789054322', '沪A33316', '671990', 'RMDBC6DB0AAB', '1', '2019-03-15 05:31:46', null, '1', '2019-03-15 05:31:46');

-- ----------------------------
-- Table structure for ts_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user` (
  `USER_ID` varchar(36) NOT NULL COMMENT 'uuid 主键',
  `PHONE_NUM` varchar(15) NOT NULL COMMENT '用户手机号',
  `PASSWORD` varchar(32) NOT NULL COMMENT '密码 md5加密',
  `IS_EFFCTIVE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效 0-无效 , 1-有效',
  `CREATE_DATE` datetime DEFAULT NULL,
  `TOKEN` varchar(255) DEFAULT NULL COMMENT '暂留 未来匹配Kamereon',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_user
-- ----------------------------
INSERT INTO `ts_user` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', '18101671990', '21232f297a57a5a743894a0e4a801fc3', '1', '2018-12-19 16:24:34', null);
INSERT INTO `ts_user` VALUES ('bf57f345-cab8-4fbb-b7f9-eccdb5cf14cf', '13312675004', '21232f297a57a5a743894a0e4a801fc3', '1', '2018-10-16 16:50:31', null);
INSERT INTO `ts_user` VALUES ('dedfa0ae-a01f-4c50-859f-a9e57d4eb262', '15900798629', '21232f297a57a5a743894a0e4a801fc3', '1', '2019-03-15 03:05:49', null);
INSERT INTO `ts_user` VALUES ('e5792c05-fa1c-4ffa-9adb-aa66f2cd8863', '18101671990', '21232f297a57a5a743894a0e4a801fc3', '0', '2018-09-28 16:40:40', null);

-- ----------------------------
-- Table structure for ts_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_user_info`;
CREATE TABLE `ts_user_info` (
  `USER_ID` varchar(36) NOT NULL COMMENT '用户id',
  `PROFILE_PHOTO` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `NICK_NAME` varchar(30) DEFAULT NULL COMMENT '昵称',
  `REAL_NAME` varchar(30) DEFAULT NULL COMMENT '姓名',
  `SEXUAL` varchar(10) DEFAULT NULL,
  `EMEY_CONTACT` varchar(36) DEFAULT NULL COMMENT '紧急联系人',
  `IS_EFFCTIVE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效 0-无效 ,1-有效',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_user_info
-- ----------------------------
INSERT INTO `ts_user_info` VALUES ('3431b8b5-85df-4024-a3d4-a83af1b1aa27', '127.0.0.1:8888/bf57f345-cab8-4fbb-b7f9-eccdb5cf14cf/1540193681718.jpg', 'LILEI', 'leo', '男', null, '1', '2018-10-16 16:50:31');
INSERT INTO `ts_user_info` VALUES ('dedfa0ae-a01f-4c50-859f-a9e57d4eb262', null, 'user_1919.372715126989', null, null, null, '1', '2019-03-15 03:05:49');

-- ----------------------------
-- Table structure for tt_charge_flow
-- ----------------------------
DROP TABLE IF EXISTS `tt_charge_flow`;
CREATE TABLE `tt_charge_flow` (
  `ID` varchar(36) NOT NULL COMMENT '流水单号',
  `USER_ACCOUNT` varchar(36) DEFAULT NULL COMMENT '关联用户表id',
  `CHARGE_DATE` datetime DEFAULT NULL COMMENT '下单日期',
  `CHARGE_FROM` varchar(20) DEFAULT NULL COMMENT '发起方',
  `CHARGE_TO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '到达方',
  `DIRECTION` tinyint(1) DEFAULT NULL COMMENT '增减标识(-1减少 1增加)',
  `AMOUNT` double(20,0) DEFAULT NULL COMMENT '金额',
  `COMMENT` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tt_charge_flow
-- ----------------------------
