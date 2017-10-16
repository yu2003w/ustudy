/*
Navicat MySQL Data Transfer

Source Server         : 192.168.111.190
Source Server Version : 50715
Source Host           : 192.168.111.190:3306
Source Database       : ustudy

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-09-14 17:09:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `exam_grade_subject`
-- ----------------------------
DROP TABLE IF EXISTS `exam_grade_subject`;
CREATE TABLE `exam_grade_subject` (
  `examination` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  `subject` int(11) NOT NULL,
  PRIMARY KEY (`examination`,`grade`,`subject`),
  KEY `fk_grade` (`grade`),
  KEY `fk_subject` (`subject`),
  CONSTRAINT `fk_exam` FOREIGN KEY (`examination`) REFERENCES `examination` (`id`),
  CONSTRAINT `fk_grade` FOREIGN KEY (`grade`) REFERENCES `grade` (`id`),
  CONSTRAINT `fk_subject` FOREIGN KEY (`subject`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试年级科目表';

-- ----------------------------
-- Records of exam_grade_subject
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_question_answer`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question_answer`;
CREATE TABLE `exam_question_answer` (
  `id` int(11) NOT NULL,
  `structure` int(11) DEFAULT NULL COMMENT '试卷',
  `exam_question` int(11) NOT NULL COMMENT '题号',
  `answer` varchar(255) NOT NULL COMMENT '答案',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_answer_str` (`structure`),
  CONSTRAINT `fk_answer_str` FOREIGN KEY (`structure`) REFERENCES `exam_questions_structure` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试题答案表';

-- ----------------------------
-- Records of exam_question_answer
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_question_scan`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question_scan`;
CREATE TABLE `exam_question_scan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_no` int(11) NOT NULL,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `question` int(11) NOT NULL,
  `answer` varchar(10) NOT NULL,
  `score` float DEFAULT '0',
  `status` varchar(1) NOT NULL COMMENT '0:正常,1:异常',
  `type` varchar(1) DEFAULT '0' COMMENT '0:客观题,1:主观题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='扫描试卷';

-- ----------------------------
-- Records of exam_question_scan
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_question_task`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question_task`;
CREATE TABLE `exam_question_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `questions` varchar(20) NOT NULL,
  `director` int(11) DEFAULT NULL,
  `type` varchar(1) NOT NULL DEFAULT '0' COMMENT '题型',
  `assignment_mode` varchar(1) NOT NULL DEFAULT '0' COMMENT '任务分配方式',
  `marking_model` varchar(1) NOT NULL COMMENT '阅卷模式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_question_task
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_question_template`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question_template`;
CREATE TABLE `exam_question_template` (
  `id` int(11) NOT NULL,
  `examination` int(11) NOT NULL,
  PRIMARY KEY (`id`,`examination`),
  KEY `fk_template_exam` (`examination`),
  CONSTRAINT `fk_template_exam` FOREIGN KEY (`examination`) REFERENCES `examination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试卷模板';

-- ----------------------------
-- Records of exam_question_template
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_questions_structure`
-- ----------------------------
DROP TABLE IF EXISTS `exam_questions_structure`;
CREATE TABLE `exam_questions_structure` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试题ID',
  `examination` int(11) NOT NULL COMMENT '所属考试',
  `grade` int(11) NOT NULL COMMENT '年级',
  `subject` int(11) DEFAULT '0' COMMENT '科目ID',
  `exam_title` varchar(10) NOT NULL DEFAULT '试题名称',
  `questions_count` int(3) NOT NULL DEFAULT '0' COMMENT '试题总数',
  `objective_questions_count` int(3) NOT NULL DEFAULT '0' COMMENT '客观题总数',
  `objective_questions` varchar(255) NOT NULL COMMENT '客观题题号',
  `subjective_questions_count` int(3) NOT NULL DEFAULT '0' COMMENT '主观题总数',
  `subjective_questions` varchar(255) NOT NULL COMMENT '主观题题号',
  `completion_questions_count` int(3) NOT NULL DEFAULT '0' COMMENT '填空题总数',
  `completion_questions` varchar(255) NOT NULL COMMENT '填空题题号',
  `create_user` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_str_grade` (`grade`),
  KEY `fk_str_subject` (`subject`),
  KEY `fk_str_exam` (`examination`),
  CONSTRAINT `fk_str_exam` FOREIGN KEY (`examination`) REFERENCES `examination` (`id`),
  CONSTRAINT `fk_str_grade` FOREIGN KEY (`grade`) REFERENCES `grade` (`id`),
  CONSTRAINT `fk_str_subject` FOREIGN KEY (`subject`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='试卷结构表';

-- ----------------------------
-- Records of exam_questions_structure
-- ----------------------------
INSERT INTO `exam_questions_structure` VALUES ('1', '1', '1', '1', '考试名称', '100', '60', '1-60', '20', '91-10', '30', '61-90', '0', '2017-09-14 10:31:00');

-- ----------------------------
-- Table structure for `exam_school`
-- ----------------------------
DROP TABLE IF EXISTS `exam_school`;
CREATE TABLE `exam_school` (
  `examination` int(11) NOT NULL,
  `school` int(11) NOT NULL,
  PRIMARY KEY (`examination`,`school`),
  KEY `fk_school_ec` (`school`),
  CONSTRAINT `fk_exam_ec` FOREIGN KEY (`examination`) REFERENCES `examination` (`id`),
  CONSTRAINT `fk_school_ec` FOREIGN KEY (`school`) REFERENCES `school` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试学校表';

-- ----------------------------
-- Records of exam_school
-- ----------------------------

-- ----------------------------
-- Table structure for `examination`
-- ----------------------------
DROP TABLE IF EXISTS `examination`;
CREATE TABLE `examination` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '考试标题',
  `type` varchar(1) NOT NULL DEFAULT '0' COMMENT '0:学校,1:联考,2:统考,3:教研室',
  `create_user` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `student_count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examination
-- ----------------------------
INSERT INTO `examination` VALUES ('1', '铜川一中16-17学年上学期期末考试', '0', '0', '2017-09-14 16:08:56', '0');

-- ----------------------------
-- Table structure for `excellent_question`
-- ----------------------------
DROP TABLE IF EXISTS `excellent_question`;
CREATE TABLE `excellent_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `question` int(11) NOT NULL,
  `student_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优标答案表';

-- ----------------------------
-- Records of excellent_question
-- ----------------------------

-- ----------------------------
-- Table structure for `faq_question`
-- ----------------------------
DROP TABLE IF EXISTS `faq_question`;
CREATE TABLE `faq_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `question` int(11) NOT NULL,
  `student_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='faq答案表';

-- ----------------------------
-- Records of faq_question
-- ----------------------------

-- ----------------------------
-- Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '年级名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', '一年级');
INSERT INTO `grade` VALUES ('2', '二年级');
INSERT INTO `grade` VALUES ('3', '三年级');
INSERT INTO `grade` VALUES ('4', '四年级');
INSERT INTO `grade` VALUES ('5', '五年级');
INSERT INTO `grade` VALUES ('6', '六年级');
INSERT INTO `grade` VALUES ('7', '初一');
INSERT INTO `grade` VALUES ('8', '初二');
INSERT INTO `grade` VALUES ('9', '初三');
INSERT INTO `grade` VALUES ('10', '高一');
INSERT INTO `grade` VALUES ('11', '高二');
INSERT INTO `grade` VALUES ('12', '高三');

-- ----------------------------
-- Table structure for `problem_exam_questions`
-- ----------------------------
DROP TABLE IF EXISTS `problem_exam_questions`;
CREATE TABLE `problem_exam_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题卷';

-- ----------------------------
-- Records of problem_exam_questions
-- ----------------------------

-- ----------------------------
-- Table structure for `question_type`
-- ----------------------------
DROP TABLE IF EXISTS `question_type`;
CREATE TABLE `question_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `type` varchar(1) NOT NULL DEFAULT '0' COMMENT '0:客观题,1:主观题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='题型';

-- ----------------------------
-- Records of question_type
-- ----------------------------
INSERT INTO `question_type` VALUES ('1', '单选题', '0');
INSERT INTO `question_type` VALUES ('2', '多选题', '0');
INSERT INTO `question_type` VALUES ('3', '填空题', '1');
INSERT INTO `question_type` VALUES ('4', '判断题', '1');
INSERT INTO `question_type` VALUES ('5', '解答题', '1');
INSERT INTO `question_type` VALUES ('6', '证明题', '1');
INSERT INTO `question_type` VALUES ('7', '作文题', '1');

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------

-- ----------------------------
-- Table structure for `strange_question`
-- ----------------------------
DROP TABLE IF EXISTS `strange_question`;
CREATE TABLE `strange_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `question` int(11) NOT NULL,
  `student_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奇葩答案表';

-- ----------------------------
-- Records of strange_question
-- ----------------------------

-- ----------------------------
-- Table structure for `student_question_score`
-- ----------------------------
DROP TABLE IF EXISTS `student_question_score`;
CREATE TABLE `student_question_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_no` int(11) NOT NULL,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `question` int(11) NOT NULL,
  `score` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生题目分数表';

-- ----------------------------
-- Records of student_question_score
-- ----------------------------

-- ----------------------------
-- Table structure for `student_score`
-- ----------------------------
DROP TABLE IF EXISTS `student_score`;
CREATE TABLE `student_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_no` int(11) NOT NULL,
  `examinnation` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `score` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生蛋壳总数表';

-- ----------------------------
-- Records of student_score
-- ----------------------------

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '语文');
INSERT INTO `subject` VALUES ('2', '数学');
INSERT INTO `subject` VALUES ('3', '英语');
INSERT INTO `subject` VALUES ('4', '物理');
INSERT INTO `subject` VALUES ('5', '生物');
INSERT INTO `subject` VALUES ('6', '政治');
INSERT INTO `subject` VALUES ('7', '化学');
INSERT INTO `subject` VALUES ('8', '历史');
INSERT INTO `subject` VALUES ('9', '地理');
INSERT INTO `subject` VALUES ('10', '文综');
INSERT INTO `subject` VALUES ('11', '理综');

-- ----------------------------
-- Table structure for `task_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `task_teacher`;
CREATE TABLE `task_teacher` (
  `task` int(11) NOT NULL,
  `teacher` int(11) NOT NULL,
  `count` int(11) NOT NULL DEFAULT '1' COMMENT '阅卷分数',
  PRIMARY KEY (`task`,`teacher`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_teacher
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher_question_record`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_question_record`;
CREATE TABLE `teacher_question_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examination` int(11) NOT NULL,
  `structure` int(11) NOT NULL,
  `question` int(11) NOT NULL,
  `student_no` int(11) DEFAULT NULL,
  `score` float NOT NULL DEFAULT '0',
  `time` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师阅卷记录表';

-- ----------------------------
-- Records of teacher_question_record
-- ----------------------------

-- ----------------------------
-- Table structure for `teacher_subject`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_subject`;
CREATE TABLE `teacher_subject` (
  `teacher` int(11) NOT NULL,
  `subject` int(11) NOT NULL,
  PRIMARY KEY (`teacher`,`subject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师科目表';

-- ----------------------------
-- Records of teacher_subject
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `sex` varchar(1) NOT NULL DEFAULT '1' COMMENT '0:女,1:男',
  `age` int(11) NOT NULL,
  `school` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  `class` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
