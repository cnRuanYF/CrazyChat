/*
Navicat MySQL Data Transfer

Source Server         : Feng-MiBookAir_3306
Source Server Version : 50721
Source Host           : Feng-MiBookAir:3306
Source Database       : crazychatdb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-24 16:29:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_friend
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend`;
CREATE TABLE `tb_friend` (
  `userId` int(11) NOT NULL,
  `friendId` int(11) NOT NULL,
  `friendRemark` varchar(16) DEFAULT NULL,
  `associateTime` date DEFAULT NULL,
  PRIMARY KEY (`userId`,`friendId`),
  KEY `FK_tb_friends2` (`friendId`),
  CONSTRAINT `FK_tb_friends` FOREIGN KEY (`userId`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK_tb_friends2` FOREIGN KEY (`friendId`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_friend
-- ----------------------------
INSERT INTO `tb_friend` VALUES ('10026', '10027', null, null);
INSERT INTO `tb_friend` VALUES ('10026', '10028', null, null);
INSERT INTO `tb_friend` VALUES ('10026', '10029', null, null);
INSERT INTO `tb_friend` VALUES ('10026', '10032', null, null);
INSERT INTO `tb_friend` VALUES ('10027', '10026', null, null);
INSERT INTO `tb_friend` VALUES ('10027', '10028', null, null);
INSERT INTO `tb_friend` VALUES ('10027', '10029', null, null);
INSERT INTO `tb_friend` VALUES ('10027', '10030', null, null);
INSERT INTO `tb_friend` VALUES ('10027', '10031', null, null);
INSERT INTO `tb_friend` VALUES ('10027', '10032', null, null);
INSERT INTO `tb_friend` VALUES ('10028', '10026', null, null);
INSERT INTO `tb_friend` VALUES ('10028', '10027', null, null);
INSERT INTO `tb_friend` VALUES ('10028', '10029', null, null);
INSERT INTO `tb_friend` VALUES ('10028', '10030', null, null);
INSERT INTO `tb_friend` VALUES ('10028', '10031', null, null);
INSERT INTO `tb_friend` VALUES ('10029', '10026', null, null);
INSERT INTO `tb_friend` VALUES ('10029', '10027', null, null);
INSERT INTO `tb_friend` VALUES ('10029', '10028', null, null);
INSERT INTO `tb_friend` VALUES ('10029', '10030', null, null);
INSERT INTO `tb_friend` VALUES ('10030', '10027', null, null);
INSERT INTO `tb_friend` VALUES ('10030', '10028', null, null);
INSERT INTO `tb_friend` VALUES ('10030', '10029', null, null);
INSERT INTO `tb_friend` VALUES ('10031', '10027', null, null);
INSERT INTO `tb_friend` VALUES ('10031', '10028', null, null);
INSERT INTO `tb_friend` VALUES ('10032', '10026', null, null);
INSERT INTO `tb_friend` VALUES ('10032', '10027', null, null);

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(24) NOT NULL,
  `groupDesc` varchar(255) NOT NULL,
  `needVerify` tinyint(1) NOT NULL,
  `creatorId` int(11) NOT NULL,
  `createTime` date NOT NULL,
  `pictureId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_creator` (`creatorId`),
  CONSTRAINT `FK_creator` FOREIGN KEY (`creatorId`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10005 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_group
-- ----------------------------
INSERT INTO `tb_group` VALUES ('10004', '等风回起义军', 'CrazyChat项目开发组', '1', '10028', '2018-04-13', '0');

-- ----------------------------
-- Table structure for tb_groupmember
-- ----------------------------
DROP TABLE IF EXISTS `tb_groupmember`;
CREATE TABLE `tb_groupmember` (
  `groupId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `memberNickname` varchar(255) DEFAULT NULL,
  `joinTime` date DEFAULT NULL,
  PRIMARY KEY (`userId`,`groupId`),
  KEY `FK_tb_groupmember2` (`groupId`),
  CONSTRAINT `FK_tb_groupmember` FOREIGN KEY (`userId`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK_tb_groupmember2` FOREIGN KEY (`groupId`) REFERENCES `tb_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_groupmember
-- ----------------------------
INSERT INTO `tb_groupmember` VALUES ('10004', '10026', null, '2018-04-24');
INSERT INTO `tb_groupmember` VALUES ('10004', '10027', null, '2018-04-24');
INSERT INTO `tb_groupmember` VALUES ('10004', '10028', null, '2018-04-24');
INSERT INTO `tb_groupmember` VALUES ('10004', '10029', null, '2018-04-24');
INSERT INTO `tb_groupmember` VALUES ('10004', '10030', null, '2018-04-24');
INSERT INTO `tb_groupmember` VALUES ('10004', '10031', null, null);

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `messageType` int(11) NOT NULL,
  `senderId` int(11) NOT NULL,
  `receiverId` int(11) NOT NULL,
  `messageContent` varchar(1024) NOT NULL,
  `sendTime` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=376 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_message
-- ----------------------------
INSERT INTO `tb_message` VALUES ('343', '0', '10027', '10026', '雷猴呀', '2018-04-24');
INSERT INTO `tb_message` VALUES ('344', '0', '10027', '10026', '了解一下，美女', '2018-04-24');
INSERT INTO `tb_message` VALUES ('345', '0', '10026', '10027', '吼吼吼', '2018-04-24');
INSERT INTO `tb_message` VALUES ('346', '0', '10026', '10027', '什么产品啊', '2018-04-24');
INSERT INTO `tb_message` VALUES ('347', '0', '10028', '10027', 'Hello world', '2018-04-24');
INSERT INTO `tb_message` VALUES ('348', '0', '10026', '10027', 'SB', '2018-04-24');
INSERT INTO `tb_message` VALUES ('349', '0', '10027', '10026', 'SBCZJ', '2018-04-24');
INSERT INTO `tb_message` VALUES ('350', '0', '10027', '10030', '你好，老伙计。', '2018-04-24');
INSERT INTO `tb_message` VALUES ('351', '0', '10031', '10028', '123123', '2018-04-24');
INSERT INTO `tb_message` VALUES ('352', '0', '10027', '10031', '马猴烧酒', '2018-04-24');
INSERT INTO `tb_message` VALUES ('353', '0', '10030', '10027', '嘿', '2018-04-24');
INSERT INTO `tb_message` VALUES ('354', '0', '10028', '10031', '少壮不努力\n老大徒伤悲', '2018-04-24');
INSERT INTO `tb_message` VALUES ('355', '0', '10028', '10026', '约吗？', '2018-04-24');
INSERT INTO `tb_message` VALUES ('356', '0', '10026', '10028', '好啊 , 周五下课打台球去啊', '2018-04-24');
INSERT INTO `tb_message` VALUES ('357', '1', '10028', '10004', '群聊会怎样？', '2018-04-24');
INSERT INTO `tb_message` VALUES ('358', '1', '10027', '10004', '带噶好', '2018-04-24');
INSERT INTO `tb_message` VALUES ('359', '1', '10026', '10004', '我发了啊', '2018-04-24');
INSERT INTO `tb_message` VALUES ('360', '0', '10028', '10027', '发条长消息测试', '2018-04-24');
INSERT INTO `tb_message` VALUES ('363', '0', '10032', '10027', 'hello', '2018-04-24');
INSERT INTO `tb_message` VALUES ('364', '0', '10027', '10032', '你好，老伙计', '2018-04-24');
INSERT INTO `tb_message` VALUES ('365', '0', '10027', '10032', '是的呢', '2018-04-24');
INSERT INTO `tb_message` VALUES ('366', '0', '10032', '10027', '厉害了我的上帝呀', '2018-04-24');
INSERT INTO `tb_message` VALUES ('367', '0', '10032', '10027', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2018-04-24');
INSERT INTO `tb_message` VALUES ('368', '0', '10032', '10026', 'abc', '2018-04-24');
INSERT INTO `tb_message` VALUES ('369', '0', '10026', '10032', '你好', '2018-04-24');
INSERT INTO `tb_message` VALUES ('370', '0', '10026', '10032', '我是不是你最疼爱的人', '2018-04-24');
INSERT INTO `tb_message` VALUES ('371', '0', '10028', '10027', 'gdfgdf', '2018-04-24');
INSERT INTO `tb_message` VALUES ('372', '0', '10028', '10027', 'jhfgj', '2018-04-24');
INSERT INTO `tb_message` VALUES ('373', '0', '10028', '10027', 'jghkhgk', '2018-04-24');
INSERT INTO `tb_message` VALUES ('374', '0', '10028', '10027', 'khjkjhk', '2018-04-24');
INSERT INTO `tb_message` VALUES ('375', '0', '10028', '10027', 'khjkjh', '2018-04-24');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(40) NOT NULL,
  `gender` int(11) DEFAULT NULL,
  `nickname` varchar(24) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(24) DEFAULT NULL,
  `privacyPolicy` int(11) DEFAULT NULL,
  `needVerify` tinyint(1) DEFAULT NULL,
  `registerTime` date DEFAULT NULL,
  `registerIP` varchar(24) DEFAULT NULL,
  `pictureId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10033 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('10026', 'chenzj', '528daba7dbd7da201aab1960c4d1d03b32c1d2fb', '1', 'chenzhijun', '像我这样的人', '1993-04-24', '18965150950', '1165490720@qq.com', '0', '0', '2018-04-24', '169.254.63.196', '79');
INSERT INTO `tb_user` VALUES ('10027', 'suwq', 'c936ee79de418ae9f8d53a243886847667d4b65e', '1', 'VisonSun', '喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?喵喵喵...?', '2018-04-24', '18150139080', '396519195@qq.com', '0', '0', '2018-04-24', '169.254.63.196', '77');
INSERT INTO `tb_user` VALUES ('10028', 'ruanyf', '26e1d3b518966a82a6a52549dbd06a80052f3634', '1', '锋子', '阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅阮耀锋超帅', '1995-06-24', '15005005300', 'me@ryf.space', '0', '0', '2018-04-24', '169.254.63.196', '187');
INSERT INTO `tb_user` VALUES ('10029', 'qiuwy', '5290043a7e8aac614aec81d8ce5cfca2227b2a95', '0', 'qiuwy', null, null, null, null, '1', '1', '2018-04-24', '169.254.63.196', '65');
INSERT INTO `tb_user` VALUES ('10030', 'wangdh', '1aae841f9912d3f791bf962db79593a01c05e3eb', '1', 'wangdh', '我就在隔壁', '2018-04-24', '', '', '0', '0', '2018-04-24', '192.168.13.121', '91');
INSERT INTO `tb_user` VALUES ('10031', 'yeh', '7ecf16ebb75bea5bc8309fbecd3e3df799ab4371', '0', 'yeh', null, null, null, null, '1', '1', '2018-04-24', '169.254.63.196', '67');
INSERT INTO `tb_user` VALUES ('10032', 'test', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', '1', 'test', '123456', '2018-01-24', '123', 'abc', '0', '0', '2018-04-24', '169.254.63.196', '40');
