/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : dashboard

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-14 23:29:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sec_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission`;
CREATE TABLE `sec_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`),
  KEY `permission_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sec_permission
-- ----------------------------
INSERT INTO `sec_permission` VALUES ('4', '/permission/list', '权限列表');
INSERT INTO `sec_permission` VALUES ('6', '/permission/insert', '权限添加');
INSERT INTO `sec_permission` VALUES ('7', '/permission/delete', '权限删除');
INSERT INTO `sec_permission` VALUES ('8', '/user/list', '用户列表');
INSERT INTO `sec_permission` VALUES ('9', '/user/insert', '新增用户');
INSERT INTO `sec_permission` VALUES ('10', '/user/delete', '删除用户');
INSERT INTO `sec_permission` VALUES ('11', '/user/detail', '用户详情');
INSERT INTO `sec_permission` VALUES ('12', '/user/update', '修改用户');
INSERT INTO `sec_permission` VALUES ('13', '/permission/detail', '权限详情');
INSERT INTO `sec_permission` VALUES ('14', '/user/deleteRole', '删除用户角色');
INSERT INTO `sec_permission` VALUES ('15', '/user/addRole', '用户分配角色');
INSERT INTO `sec_permission` VALUES ('16', '/role/delete', '删除角色');
INSERT INTO `sec_permission` VALUES ('17', '/role/insert', '添加角色');
INSERT INTO `sec_permission` VALUES ('18', '/role/list', '角色列表');
INSERT INTO `sec_permission` VALUES ('19', '/permission/update', '修改权限');
INSERT INTO `sec_permission` VALUES ('20', '/role/detail', '角色详情');
INSERT INTO `sec_permission` VALUES ('21', '/role/update', '修改角色');
INSERT INTO `sec_permission` VALUES ('22', '/role/addPermission', '角色分配权限');
INSERT INTO `sec_permission` VALUES ('23', '/role/deletePermission', '角色移除权限');

-- ----------------------------
-- Table structure for `sec_role`
-- ----------------------------
DROP TABLE IF EXISTS `sec_role`;
CREATE TABLE `sec_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `type` varchar(10) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sec_role
-- ----------------------------
INSERT INTO `sec_role` VALUES ('1', '系统管理员', '888888');
INSERT INTO `sec_role` VALUES ('3', '权限角色', '100003');
INSERT INTO `sec_role` VALUES ('4', '用户中心', '100002');

-- ----------------------------
-- Table structure for `sec_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sec_role_permission`;
CREATE TABLE `sec_role_permission` (
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sec_role_permission
-- ----------------------------
INSERT INTO `sec_role_permission` VALUES ('4', '8');
INSERT INTO `sec_role_permission` VALUES ('4', '9');
INSERT INTO `sec_role_permission` VALUES ('4', '10');
INSERT INTO `sec_role_permission` VALUES ('4', '11');
INSERT INTO `sec_role_permission` VALUES ('4', '12');
INSERT INTO `sec_role_permission` VALUES ('1', '4');
INSERT INTO `sec_role_permission` VALUES ('1', '6');
INSERT INTO `sec_role_permission` VALUES ('1', '7');
INSERT INTO `sec_role_permission` VALUES ('1', '8');
INSERT INTO `sec_role_permission` VALUES ('1', '9');
INSERT INTO `sec_role_permission` VALUES ('1', '10');
INSERT INTO `sec_role_permission` VALUES ('1', '11');
INSERT INTO `sec_role_permission` VALUES ('1', '12');
INSERT INTO `sec_role_permission` VALUES ('1', '13');
INSERT INTO `sec_role_permission` VALUES ('1', '14');
INSERT INTO `sec_role_permission` VALUES ('1', '15');
INSERT INTO `sec_role_permission` VALUES ('1', '16');
INSERT INTO `sec_role_permission` VALUES ('1', '17');
INSERT INTO `sec_role_permission` VALUES ('1', '18');
INSERT INTO `sec_role_permission` VALUES ('1', '19');
INSERT INTO `sec_role_permission` VALUES ('1', '20');
INSERT INTO `sec_role_permission` VALUES ('1', '21');
INSERT INTO `sec_role_permission` VALUES ('3', '4');
INSERT INTO `sec_role_permission` VALUES ('3', '6');
INSERT INTO `sec_role_permission` VALUES ('3', '7');
INSERT INTO `sec_role_permission` VALUES ('3', '8');
INSERT INTO `sec_role_permission` VALUES ('3', '9');
INSERT INTO `sec_role_permission` VALUES ('3', '10');
INSERT INTO `sec_role_permission` VALUES ('3', '11');
INSERT INTO `sec_role_permission` VALUES ('3', '12');
INSERT INTO `sec_role_permission` VALUES ('3', '13');
INSERT INTO `sec_role_permission` VALUES ('3', '14');
INSERT INTO `sec_role_permission` VALUES ('3', '15');
INSERT INTO `sec_role_permission` VALUES ('3', '16');
INSERT INTO `sec_role_permission` VALUES ('3', '17');
INSERT INTO `sec_role_permission` VALUES ('3', '18');
INSERT INTO `sec_role_permission` VALUES ('3', '19');
INSERT INTO `sec_role_permission` VALUES ('3', '20');
INSERT INTO `sec_role_permission` VALUES ('3', '21');
INSERT INTO `sec_role_permission` VALUES ('1', '22');
INSERT INTO `sec_role_permission` VALUES ('1', '23');

-- ----------------------------
-- Table structure for `sec_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sec_user_role`;
CREATE TABLE `sec_user_role` (
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sec_user_role
-- ----------------------------
INSERT INTO `sec_user_role` VALUES ('11', '3');
INSERT INTO `sec_user_role` VALUES ('11', '4');
INSERT INTO `sec_user_role` VALUES ('12', '3');
INSERT INTO `sec_user_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `sec_users`
-- ----------------------------
DROP TABLE IF EXISTS `sec_users`;
CREATE TABLE `sec_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) DEFAULT NULL COMMENT '登录名称',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `pswd` varchar(32) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` bigint(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sec_users
-- ----------------------------
INSERT INTO `sec_users` VALUES ('1', 'admin', 'admin', 'admin', null, 'admin', '2016-06-16 11:15:33', '2017-06-09 22:52:04', '1');
INSERT INTO `sec_users` VALUES ('11', 'soso', 'soso', '8446666@qq.com', null, 'd57ffbe486910dd5b26d0167d034f9ad', '2016-05-26 20:50:54', '2016-06-16 11:24:35', '1');
INSERT INTO `sec_users` VALUES ('12', '8446666', '8446666', '8446666', null, '4afdc875a67a55528c224ce088be2ab8', '2016-05-27 22:34:19', '2016-06-15 17:03:16', '1');
INSERT INTO `sec_users` VALUES ('13', 'loginname', 'name', 'email', 'phone', '57eb72e6b78a87a12d46a7f5e9315138', '2017-06-12 22:05:36', null, '0');
