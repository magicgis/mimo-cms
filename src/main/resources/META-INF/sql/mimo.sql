/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50156
Source Host           : localhost:3306
Source Database       : mimo

Target Server Type    : MYSQL
Target Server Version : 50156
File Encoding         : 65001

Date: 2012-02-16 18:32:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mimo_cms_article`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_article`;
CREATE TABLE `mimo_cms_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `on_top` bit(1) NOT NULL,
  `status` varchar(10) NOT NULL,
  `forbid_comments` bit(1) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `modify_time` bigint(13) NOT NULL,
  `channel_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `channel_id` (`channel_id`),
  KEY `create_time` (`create_time`),
  KEY `tags` (`tags`),
  KEY `status` (`status`),
  CONSTRAINT `mimo_cms_article_ibfk_1` FOREIGN KEY (`channel_id`) REFERENCES `mimo_cms_channel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_article
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_article_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_article_attachment`;
CREATE TABLE `mimo_cms_article_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `content_type` varchar(255) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `mimo_cms_article_attachment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mimo_cms_article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_article_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_article_comment`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_article_comment`;
CREATE TABLE `mimo_cms_article_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(32) NOT NULL,
  `content` mediumtext NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `create_time` (`create_time`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `mimo_cms_article_comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mimo_cms_article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_article_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_article_mining_task`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_article_mining_task`;
CREATE TABLE `mimo_cms_article_mining_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `items_expression` varchar(255) NOT NULL,
  `title_expression` varchar(255) NOT NULL,
  `content_expression` varchar(255) NOT NULL,
  `source_expression` varchar(255) NOT NULL,
  `tags_expression` varchar(255) DEFAULT NULL,
  `create_time_expression` varchar(255) DEFAULT NULL,
  `channel_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `name` (`name`),
  KEY `channel_id` (`channel_id`),
  CONSTRAINT `mimo_cms_article_mining_task_ibfk_1` FOREIGN KEY (`channel_id`) REFERENCES `mimo_cms_channel` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_article_mining_task
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_channel`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_channel`;
CREATE TABLE `mimo_cms_channel` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `path` varchar(16) NOT NULL,
  `about` mediumtext,
  `priority` int(11) NOT NULL,
  `meta_keyword` tinytext,
  `meta_title` tinytext,
  `meta_descr` tinytext,
  `father_id` int(32) DEFAULT NULL,
  `self_template_id` int(32) DEFAULT NULL,
  `article_template_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `path` (`path`),
  KEY `father_id` (`father_id`) USING BTREE,
  KEY `name` (`name`),
  KEY `self_template_id` (`self_template_id`),
  KEY `article_template_id` (`article_template_id`),
  CONSTRAINT `mimo_cms_channel_ibfk_1` FOREIGN KEY (`father_id`) REFERENCES `mimo_cms_channel` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mimo_cms_channel_ibfk_2` FOREIGN KEY (`self_template_id`) REFERENCES `mimo_cms_template` (`id`),
  CONSTRAINT `mimo_cms_channel_ibfk_3` FOREIGN KEY (`article_template_id`) REFERENCES `mimo_cms_template` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of mimo_cms_channel
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_guestbook`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_guestbook`;
CREATE TABLE `mimo_cms_guestbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `content` mediumtext NOT NULL,
  `create_time` bigint(13) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_guestbook
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_monitoring_record`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_monitoring_record`;
CREATE TABLE `mimo_cms_monitoring_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actor` varchar(255) NOT NULL,
  `action` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `target` varchar(255) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  `elapsed_time` bigint(13) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `actor` (`actor`),
  KEY `action` (`action`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_monitoring_record
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_recycle_resource`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_recycle_resource`;
CREATE TABLE `mimo_cms_recycle_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `version` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `recycle_path` varchar(255) NOT NULL,
  `create_time` bigint(13) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_recycle_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `mimo_cms_security_authority`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_security_authority`;
CREATE TABLE `mimo_cms_security_authority` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `permission` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `permission` (`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_security_authority
-- ----------------------------
INSERT INTO `mimo_cms_security_authority` VALUES ('031412c5553111e1badbc8b1208a5409', '删除文章', 'article:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('0f37e4c8561111e1badbc8b1208a5409', '删除文章附件', 'article-attachment:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('28129b5c561111e1badbc8b1208a5409', '上传文章附件', 'article-attachment:upload');
INSERT INTO `mimo_cms_security_authority` VALUES ('282f8ef8564911e1badbc8b1208a5409', '查看资源副本', 'recycle-resource:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('2a20751f553111e1badbc8b1208a5409', '查看网站资源文件', 'resource:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('2d771e86560a11e1badbc8b1208a5409', '查看留言', 'guestbook:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('31125de9564911e1badbc8b1208a5409', '还原资源副本', 'recycle-resource:recycle');
INSERT INTO `mimo_cms_security_authority` VALUES ('3377ef58561111e1badbc8b1208a5409', '下载文章附件', 'article-attachment:download');
INSERT INTO `mimo_cms_security_authority` VALUES ('375b9291554011e1badbc8b1208a5409', '删除网站安全资源文件', 'security-resource:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('37da5484558511e1badbc8b1208a5409', '查看图片资源文件', 'photo-resource:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('3c9ff420553111e1badbc8b1208a5409', '上传网站资源文件', 'resource:upload');
INSERT INTO `mimo_cms_security_authority` VALUES ('44587c52558511e1badbc8b1208a5409', '上传图片资源文件', 'photo-resource:upload');
INSERT INTO `mimo_cms_security_authority` VALUES ('4b7b01e3553111e1badbc8b1208a5409', '下载网站资源文件', 'resource:download');
INSERT INTO `mimo_cms_security_authority` VALUES ('4d189dde561111e1badbc8b1208a5409', '删除文章评论', 'article-comment:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('5ab322b1558511e1badbc8b1208a5409', '下载图片资源文件', 'photo-resource:download');
INSERT INTO `mimo_cms_security_authority` VALUES ('5bc1994d553111e1badbc8b1208a5409', '查看网站安全资源文件', 'security-resource:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('69e85e5d553111e1badbc8b1208a5409', '上传网站安全资源文件', 'security-resource:upload');
INSERT INTO `mimo_cms_security_authority` VALUES ('6b8a1d0c561111e1badbc8b1208a5409', '创建文章评论', 'article-comment:create');
INSERT INTO `mimo_cms_security_authority` VALUES ('6f6c2e22558511e1badbc8b1208a5409', '删除图片资源文件', 'photo-resource:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('75a77701561111e1badbc8b1208a5409', '修改文章评论', 'article-comment:edit');
INSERT INTO `mimo_cms_security_authority` VALUES ('7a9b21de553111e1badbc8b1208a5409', '下载网站安全资源文件', 'security-resource:download');
INSERT INTO `mimo_cms_security_authority` VALUES ('7c7a286f587d11e195de360472309768', '查看文章采集任务', 'article-mining-task:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('7e4bc13b553011e1badbc8b1208a5409', '查看栏目', 'channel:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('8875b634562d11e1badbc8b1208a5409', '删除网站资源', 'resource:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('8b54140e587d11e195de360472309768', '创建文章采集任务', 'article-mining-task:create');
INSERT INTO `mimo_cms_security_authority` VALUES ('9749d24c587d11e195de360472309768', '修改文章采集任务', 'article-mining-task:edit');
INSERT INTO `mimo_cms_security_authority` VALUES ('982f0461553011e1badbc8b1208a5409', '创建栏目', 'channel:create');
INSERT INTO `mimo_cms_security_authority` VALUES ('a0455799587d11e195de360472309768', '删除文章采集任务', 'article-mining-task:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('a16df686553011e1badbc8b1208a5409', '编辑栏目', 'channel:edit');
INSERT INTO `mimo_cms_security_authority` VALUES ('a1812cb0560a11e1badbc8b1208a5409', '创建留言', 'guestbook:create');
INSERT INTO `mimo_cms_security_authority` VALUES ('a922a95a553011e1badbc8b1208a5409', '删除栏目', 'channel:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('aafc75a6560a11e1badbc8b1208a5409', '删除留言', 'guestbook:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('b4ed4707560a11e1badbc8b1208a5409', '编辑留言', 'guestbook:edit');
INSERT INTO `mimo_cms_security_authority` VALUES ('b6d662d7553011e1badbc8b1208a5409', '查看模板', 'template:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('c09eee18553011e1badbc8b1208a5409', '创建模板', 'template:create');
INSERT INTO `mimo_cms_security_authority` VALUES ('c847b24e553011e1badbc8b1208a5409', '编辑模板', 'template:edit');
INSERT INTO `mimo_cms_security_authority` VALUES ('cf89ee4f553011e1badbc8b1208a5409', '删除模板', 'template:delete');
INSERT INTO `mimo_cms_security_authority` VALUES ('ddc34a4e553011e1badbc8b1208a5409', '查看文章', 'article:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('e4788f2e561011e1badbc8b1208a5409', '查看文章评论', 'article-comment:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('e5906cd7587d11e195de360472309768', '启动文章采集任务', 'article-mining-task:run');
INSERT INTO `mimo_cms_security_authority` VALUES ('e6f805e1553011e1badbc8b1208a5409', '创建文章', 'article:create');
INSERT INTO `mimo_cms_security_authority` VALUES ('ed485c5d561011e1badbc8b1208a5409', '查看文章附件', 'article-attachment:list');
INSERT INTO `mimo_cms_security_authority` VALUES ('f41f4602553011e1badbc8b1208a5409', '编辑文章', 'article:edit');

-- ----------------------------
-- Table structure for `mimo_cms_security_role`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_security_role`;
CREATE TABLE `mimo_cms_security_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `show_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_security_role
-- ----------------------------
INSERT INTO `mimo_cms_security_role` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'admin', '管理员');

-- ----------------------------
-- Table structure for `mimo_cms_security_role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_security_role_authority`;
CREATE TABLE `mimo_cms_security_role_authority` (
  `role_id` varchar(32) NOT NULL,
  `authority_id` varchar(32) DEFAULT NULL,
  KEY `role_id` (`role_id`),
  KEY `authority_id` (`authority_id`),
  CONSTRAINT `mimo_cms_security_role_authority_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `mimo_cms_security_role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mimo_cms_security_role_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `mimo_cms_security_authority` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_security_role_authority
-- ----------------------------
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '4b7b01e3553111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'c09eee18553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '6b8a1d0c561111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '031412c5553111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '6f6c2e22558511e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'e4788f2e561011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '31125de9564911e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '282f8ef8564911e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '982f0461553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'a922a95a553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'a1812cb0560a11e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'b6d662d7553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'a16df686553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '375b9291554011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'e5906cd7587d11e195de360472309768');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '69e85e5d553111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '0f37e4c8561111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '44587c52558511e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '5bc1994d553111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'aafc75a6560a11e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '75a77701561111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'b4ed4707560a11e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '7e4bc13b553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '3377ef58561111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '7a9b21de553111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '2d771e86560a11e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'ddc34a4e553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '5ab322b1558511e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '9749d24c587d11e195de360472309768');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'f41f4602553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '3c9ff420553111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '2a20751f553111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'ed485c5d561011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '8875b634562d11e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'c847b24e553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'a0455799587d11e195de360472309768');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '28129b5c561111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '7c7a286f587d11e195de360472309768');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '37da5484558511e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '8b54140e587d11e195de360472309768');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', '4d189dde561111e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'cf89ee4f553011e1badbc8b1208a5409');
INSERT INTO `mimo_cms_security_role_authority` VALUES ('10edd41d553a11e1badbc8b1208a5409', 'e6f805e1553011e1badbc8b1208a5409');

-- ----------------------------
-- Table structure for `mimo_cms_security_user`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_security_user`;
CREATE TABLE `mimo_cms_security_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `account_non_locked` (`account_non_locked`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_security_user
-- ----------------------------
INSERT INTO `mimo_cms_security_user` VALUES ('373b4947553c11e1badbc8b1208a5409', 'admin', 'AZICOnu9cyUFFvBp3xi1AA==', '');

-- ----------------------------
-- Table structure for `mimo_cms_security_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_security_user_role`;
CREATE TABLE `mimo_cms_security_user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `mimo_cms_security_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mimo_cms_security_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mimo_cms_security_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `mimo_cms_security_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_security_user_role
-- ----------------------------
INSERT INTO `mimo_cms_security_user_role` VALUES ('373b4947553c11e1badbc8b1208a5409', '10edd41d553a11e1badbc8b1208a5409');

-- ----------------------------
-- Table structure for `mimo_cms_template`
-- ----------------------------
DROP TABLE IF EXISTS `mimo_cms_template`;
CREATE TABLE `mimo_cms_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `path` tinytext NOT NULL,
  `encode` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mimo_cms_template
-- ----------------------------
