/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50156
Source Host           : localhost:3306
Source Database       : mimo

Target Server Type    : MYSQL
Target Server Version : 50156
File Encoding         : 65001

Date: 2012-02-10 13:41:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mimo_cms_channel`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_channel`;
CREATE TABLE `mimo_cms_channel` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `path` varchar(16) NOT NULL,
  `priority` int(11) NOT NULL,
  `meta_keyword` tinytext,
  `meta_title` tinytext,
  `meta_descr` tinytext,
  `father_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `path` (`path`),
  KEY `father_id` (`father_id`) USING BTREE,
  CONSTRAINT `mimo_cms_channel_ibfk_1` FOREIGN KEY (`father_id`) REFERENCES `mimo_cms_channel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of mimo_cms_channel
-- ----------------------------
INSERT INTO `mimo_cms_channel` VALUES ('1', 'asdas', 'dasdsa', '15', 'dasdas', 'dasda', 'asdas', null);
INSERT INTO `mimo_cms_channel` VALUES ('3', 'sfsfsdfs', 'fdsfds', '0', 'fds', 'fds', 'ds', '1');
