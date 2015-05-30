-- MySQL dump 10.13  Distrib 5.6.14, for Linux (x86_64)
--
-- Host: localhost    Database: mammytree
-- ------------------------------------------------------
-- Server version	5.6.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ty_admin_action`
--

DROP TABLE IF EXISTS `ty_admin_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_admin_action` (
  `action_id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(5) unsigned NOT NULL DEFAULT '0',
  `action_code` varchar(30) NOT NULL,
  `action_name` varchar(25) NOT NULL DEFAULT '' COMMENT '权限名称',
  `menu_name` varchar(25) DEFAULT '' COMMENT '菜单名称',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单链接地址',
  `sort_order` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`action_id`),
  UNIQUE KEY `unique_parent_action` (`action_code`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=541 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_admin_action_group`
--

DROP TABLE IF EXISTS `ty_admin_action_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_admin_action_group` (
  `action_group_id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  `action_list` text NOT NULL,
  `create_admin` int(10) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`action_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='管理员权限分组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_admin_info`
--

DROP TABLE IF EXISTS `ty_admin_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_admin_info` (
  `admin_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '管理员编号',
  `admin_name` varchar(50) NOT NULL COMMENT '管理员登录',
  `realname` varchar(50) NOT NULL COMMENT '真实姓名',
  `admin_email` varchar(100) NOT NULL COMMENT 'email',
  `admin_password` varchar(50) NOT NULL COMMENT '密码',
  `sex` int(1) NOT NULL COMMENT '1男2女',
  `birthday` date NOT NULL COMMENT '生日',
  `join_date` date NOT NULL COMMENT '入职时间',
  `last_login` datetime NOT NULL COMMENT '最后登录时间',
  `last_ip` varchar(30) NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `action_list` text NOT NULL COMMENT '权限',
  `action_group_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '权限组编号',
  `user_status` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '1可用，0停用',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `is_online` int(1) NOT NULL DEFAULT '0' COMMENT '是否在线，0不在线，1在线',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_name` (`admin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='管理员基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_apply_return_info`
--

DROP TABLE IF EXISTS `ty_apply_return_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_apply_return_info` (
  `apply_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` mediumint(8) NOT NULL COMMENT '订单id',
  `user_id` decimal(10,0) NOT NULL COMMENT '用户id',
  `provider_id` mediumint(8) DEFAULT NULL COMMENT '供应商id',
  `shipping_name` varchar(45) NOT NULL COMMENT '快递名称',
  `invoice_no` varchar(45) NOT NULL COMMENT '运单号',
  `sent_user_name` varchar(45) NOT NULL COMMENT '寄件人姓名',
  `mobile` varchar(45) DEFAULT NULL COMMENT '寄件人手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '寄件人电话',
  `shipping_fee` decimal(10,2) NOT NULL COMMENT '运费',
  `back_address` varchar(255) NOT NULL COMMENT '退回地址',
  `product_number` int(4) NOT NULL COMMENT '总件数',
  `apply_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '申请状态 0:待处理 1:处理中 2:已处理 3:已取消 4:拒收(即全部退货)',
  `provider_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '供应商状态0未审核 1 正常审核 2 非正常审核',
  `order_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单类型 0 普通订单 1 第三方直发订单',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `cancel_reason` varchar(500) DEFAULT NULL COMMENT '取消理由',
  `cancel_admin_id` int(11) NOT NULL DEFAULT '0' COMMENT '取消人',
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='自助退货表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_apply_return_product`
--

DROP TABLE IF EXISTS `ty_apply_return_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_apply_return_product` (
  `rec_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `apply_id` mediumint(8) NOT NULL COMMENT '申请退货id',
  `product_id` mediumint(8) NOT NULL COMMENT '商品ID',
  `color_id` int(11) NOT NULL COMMENT '颜色ID',
  `size_id` int(11) NOT NULL COMMENT '尺寸ID',
  `product_price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `product_sn` varchar(60) DEFAULT NULL COMMENT '商品SN',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `product_number` int(4) NOT NULL COMMENT '商品数量',
  `return_reason` tinyint(4) NOT NULL COMMENT '退货理由0:尺寸偏大 1:尺寸偏小 2:款式不喜欢 3:配送错误 4:其他 5:商品质量有问题',
  `description` text COMMENT '问题描述',
  `img` varchar(600) DEFAULT NULL COMMENT '退货图片',
  PRIMARY KEY (`rec_id`),
  KEY `index_apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='申请退货商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_apply_return_suggest`
--

DROP TABLE IF EXISTS `ty_apply_return_suggest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_apply_return_suggest` (
  `rec_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `apply_id` mediumint(8) NOT NULL COMMENT '自助退货ID',
  `suggest_type` tinyint(4) NOT NULL COMMENT '意见类型 0:客服意见 1:供应商正常意见 2:供应商非正常意见 3:其他意见',
  `suggest_content` text COMMENT '意见内容',
  `create_id` mediumint(8) NOT NULL COMMENT '创建人ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`rec_id`),
  KEY `index_apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='申请退货意见表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_article_cat`
--

DROP TABLE IF EXISTS `ty_article_cat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_article_cat` (
  `cat_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '帮助中心文章分类编号',
  `cat_name` varchar(255) NOT NULL DEFAULT '' COMMENT '帮助中心文章分类名称',
  `keywords` varchar(255) NOT NULL DEFAULT '' COMMENT '帮助中心文章分类关键字',
  `cat_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '帮助中心文章分类描述',
  `sort_order` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父节点',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='帮助中心分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_article_info`
--

DROP TABLE IF EXISTS `ty_article_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_article_info` (
  `article_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '文章编号',
  `cat_id` int(10) NOT NULL DEFAULT '0' COMMENT '帮助中心文章分类',
  `title` varchar(150) NOT NULL COMMENT '文章标题',
  `title_color` varchar(20) NOT NULL COMMENT '标题颜色',
  `title_size` varchar(20) NOT NULL COMMENT '标题字体大小',
  `content` longtext NOT NULL COMMENT '内容',
  `author` varchar(30) NOT NULL COMMENT '作者',
  `keywords` varchar(255) NOT NULL COMMENT '文章关键字',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '外站文章链接地址',
  `is_use` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `source` varchar(64) NOT NULL COMMENT '文章来源',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='帮助中心文章基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_box`
--

DROP TABLE IF EXISTS `ty_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_box` (
  `box_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_code` varchar(30) DEFAULT NULL COMMENT '箱号,唯一',
  `doc_type` varchar(2) DEFAULT NULL COMMENT '单据类型 ',
  `doc_code` varchar(30) DEFAULT NULL COMMENT '单据code',
  `doc_id` int(10) DEFAULT NULL COMMENT '单据ID',
  `scan_number` int(10) DEFAULT '0' COMMENT '过程1件数',
  `scan_id` int(10) DEFAULT NULL COMMENT '过程1操作人',
  `scan_starttime` datetime DEFAULT NULL COMMENT '过程1开始时间',
  `scan_endtime` datetime DEFAULT NULL COMMENT '过程1结束时间',
  `shelve_number` int(10) DEFAULT '0' COMMENT '过程2件数',
  `shelve_id` int(10) DEFAULT NULL COMMENT '过程2操作人',
  `shelve_starttime` datetime DEFAULT NULL COMMENT '过程2开始时间',
  `shelve_endtime` datetime DEFAULT NULL COMMENT '过程2结束时间',
  PRIMARY KEY (`box_id`),
  UNIQUE KEY `box_code` (`box_code`),
  KEY `doc_code` (`doc_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='箱子主表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_box_leaf`
--

DROP TABLE IF EXISTS `ty_box_leaf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_box_leaf` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_id` int(10) NOT NULL COMMENT '箱号id',
  `box_code` varchar(30) DEFAULT NULL COMMENT '箱号,唯一',
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位ID',
  `num` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `box_id` (`box_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='箱子具体表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_box_sub`
--

DROP TABLE IF EXISTS `ty_box_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_box_sub` (
  `box_sub_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_id` int(10) NOT NULL COMMENT '箱号id',
  `box_code` varchar(30) DEFAULT NULL COMMENT '箱号,唯一',
  `doc_type` varchar(2) DEFAULT NULL COMMENT '单据类型 ',
  `doc_code` varchar(30) DEFAULT NULL COMMENT '单据code',
  `doc_id` int(10) DEFAULT NULL COMMENT '单据ID',
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `scan_number` int(10) DEFAULT '0' COMMENT '过程1件数',
  `scan_id` int(10) DEFAULT NULL COMMENT '过程1操作人',
  `scan_starttime` datetime DEFAULT NULL COMMENT '过程1开始时间',
  `scan_endtime` datetime DEFAULT NULL COMMENT '过程1结束时间',
  `shelve_number` int(10) DEFAULT '0' COMMENT '过程2件数',
  `shelve_id` int(10) DEFAULT NULL COMMENT '过程2操作人',
  `shelve_starttime` datetime DEFAULT NULL COMMENT '过程2开始时间',
  `shelve_endtime` datetime DEFAULT NULL COMMENT '过程2结束时间',
  PRIMARY KEY (`box_sub_id`),
  KEY `box_id` (`box_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='箱子子表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_cps`
--

DROP TABLE IF EXISTS `ty_cps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_cps` (
  `cps_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cps_sn` varchar(20) NOT NULL COMMENT 'cps SN',
  `cps_name` varchar(45) NOT NULL COMMENT 'cps名',
  `cps_cookie_time` int(10) NOT NULL DEFAULT '30' COMMENT 'cookie cook_cps生存周期（单位：天）',
  `cps_start_time` datetime DEFAULT NULL COMMENT 'cps开始时间',
  `cps_shut_time` datetime DEFAULT NULL COMMENT 'cps结束时间',
  `cps_status` int(1) NOT NULL DEFAULT '0' COMMENT 'cps状态（0：无效，1：有效）',
  `cps_data` text COMMENT 'cps特殊信息（例如KEY），存放每个CPS的特定参数，使用JSON格式',
  `cps_rtn_script` text COMMENT 'params:[$order_info[],$order_goods[[],...],$cps_params[],$cps_data[]]return:$script_src;',
  `create_admin` int(10) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  `confirm_admin` int(10) NOT NULL DEFAULT '0',
  `confirm_date` datetime NOT NULL,
  PRIMARY KEY (`cps_id`),
  UNIQUE KEY `cps_sn_UNIQUE` (`cps_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cps表，管理cps';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_cps_log`
--

DROP TABLE IF EXISTS `ty_cps_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_cps_log` (
  `cps_log_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cps_id` int(10) NOT NULL COMMENT '外键cps.cps_id',
  `user_id` int(10) NOT NULL COMMENT '外键用户ID',
  `cps_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT 'cps用户名',
  `order_id` int(10) NOT NULL COMMENT '外键订单ID',
  `user_ip` varchar(15) NOT NULL DEFAULT '0.0.0.0' COMMENT '用户IP地址',
  `cps_price` decimal(10,2) NOT NULL COMMENT 'cps订单价格',
  `cps_time` datetime NOT NULL,
  `cps_log_data` text COMMENT '存放cps传过来的信息（例如站长ID），使用JSON格式',
  PRIMARY KEY (`cps_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cps订单日志表，跟踪cps订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_in_main`
--

DROP TABLE IF EXISTS `ty_depot_in_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_in_main` (
  `depot_in_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `depot_in_code` varchar(50) NOT NULL COMMENT '入库单编号',
  `order_id` int(10) DEFAULT NULL COMMENT '关联采购单或出库单id,或收货箱ID,20130218修改',
  `order_sn` varchar(50) DEFAULT NULL COMMENT '来源订单编号',
  `depot_depot_id` int(10) NOT NULL DEFAULT '0',
  `depot_in_reason` varchar(255) NOT NULL COMMENT '进货说明',
  `depot_in_type` int(10) NOT NULL COMMENT '入库类型ID',
  `depot_in_number` int(10) NOT NULL COMMENT '入库总数',
  `depot_in_finished_number` int(10) DEFAULT '0' COMMENT '已完成数量。20130218添加',
  `depot_in_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '入库总金额',
  `depot_in_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '实际入库日期',
  `audit_admin` int(10) DEFAULT '0' COMMENT '仓库审核人',
  `audit_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '仓库审核时间',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `lock_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '锁定时间',
  `in_type` int(1) DEFAULT '0' COMMENT '入库类型1:普通入库 2:扫描入库,扫描入库自动审核订单。20130218添加',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除;0-未删除；1-已删除 20130218添加',
  PRIMARY KEY (`depot_in_id`),
  UNIQUE KEY `depot_in_code` (`depot_in_code`),
  KEY `order_id` (`order_id`),
  KEY `depot_in_type` (`depot_in_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品入库一级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_in_sub`
--

DROP TABLE IF EXISTS `ty_depot_in_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_in_sub` (
  `depot_in_sub_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `depot_in_id` int(10) NOT NULL COMMENT '主入库单ID',
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `product_name` varchar(120) NOT NULL COMMENT '商品名字',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '仓库ID',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位ID',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '入库数量',
  `product_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '入库总价',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  `product_finished_number` int(10) DEFAULT '0' COMMENT '实际完成数量,20130218添加',
  PRIMARY KEY (`depot_in_sub_id`),
  KEY `depot_in_id` (`depot_in_id`),
  KEY `product_color_size` (`product_id`,`color_id`,`size_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品出入库二级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_info`
--

DROP TABLE IF EXISTS `ty_depot_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_info` (
  `depot_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '仓库编号',
  `depot_name` varchar(100) NOT NULL COMMENT '仓库名称',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '1为启用,0为停用',
  `depot_position` varchar(200) NOT NULL COMMENT '仓库地点',
  `depot_priority` int(10) NOT NULL DEFAULT '0' COMMENT '优先级',
  `depot_type` int(1) NOT NULL DEFAULT '1' COMMENT '1=可售 0=不可售',
  `is_return` int(1) NOT NULL DEFAULT '0' COMMENT '是否为退货仓,0不是 1是',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `cooperation_id` int(10) NOT NULL DEFAULT '2' COMMENT '仓库属性 1-买断 2-代销  20130218添加',
  PRIMARY KEY (`depot_id`),
  UNIQUE KEY `depot_name` (`depot_name`),
  KEY `depot_type` (`depot_type`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='仓库管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_inventory`
--

DROP TABLE IF EXISTS `ty_depot_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_inventory` (
  `inventory_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '盘点ID',
  `inventory_sn` varchar(40) NOT NULL COMMENT '盘点编号（PD打头年月日+5位随机数）',
  `inventory_type` int(1) NOT NULL COMMENT '盘点类型：0-按货架范围盘点，1-按指定储位盘点',
  `inventory_note` text COMMENT '盘点备注',
  `depot_id` int(10) DEFAULT '0' COMMENT '仓库id',
  `shelf_from` varchar(8) DEFAULT NULL COMMENT '货架起始编号',
  `shelf_to` varchar(8) DEFAULT NULL COMMENT '货架终止编号',
  `location_id` int(10) DEFAULT NULL COMMENT '储 位ID',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建者ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `check_admin` int(10) DEFAULT NULL COMMENT '审核者ID',
  `check_date` datetime DEFAULT NULL COMMENT '审核时间',
  `stop_admin` int(10) DEFAULT NULL COMMENT '终止者ID',
  `stop_date` datetime DEFAULT NULL COMMENT '终止时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '系统状态：0-创建，1-确认，2-结束，3-终止，4-财审',
  `diff_admin` int(10) DEFAULT NULL COMMENT '生成差异单据人',
  `diff_date` datetime DEFAULT NULL COMMENT '生成差异单据时间',
  `gen_admin` int(10) DEFAULT NULL COMMENT '盘点单生成人',
  `gen_date` datetime DEFAULT NULL COMMENT '盘点单生成时间',
  `depot_in_sn` varchar(128) DEFAULT NULL COMMENT '入库单号',
  `depot_out_sn` varchar(128) DEFAULT NULL COMMENT '出库单号',
  `exclude_locations` mediumtext COMMENT '排除储位ID列表，多个使用,隔开',
  PRIMARY KEY (`inventory_id`),
  UNIQUE KEY `inventory_sn_UNIQUE` (`inventory_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盘点主信息表 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_inventory_detail`
--

DROP TABLE IF EXISTS `ty_depot_inventory_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_inventory_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `inventory_id` int(10) NOT NULL COMMENT '盘点ID',
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `depot_id` int(10) DEFAULT '0',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位ID',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '入库数量',
  `inventory_number` int(10) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建者ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_admin` int(10) DEFAULT NULL COMMENT '更新者ID',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盘点明细表 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_inventory_diff`
--

DROP TABLE IF EXISTS `ty_depot_inventory_diff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_inventory_diff` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `inventory_id` int(10) NOT NULL COMMENT '盘点ID',
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位ID',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '差异数量',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建者ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盘点结果差异表 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_iotype`
--

DROP TABLE IF EXISTS `ty_depot_iotype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_iotype` (
  `depot_type_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `depot_type_code` varchar(30) NOT NULL COMMENT '出入库类型编号',
  `depot_type_name` varchar(50) NOT NULL COMMENT '出入库类型名称',
  `depot_type_out` int(1) NOT NULL DEFAULT '0' COMMENT '出库入库标注，默认入库',
  `depot_type_special` int(1) NOT NULL DEFAULT '0' COMMENT '默认0：普通 1：从采购单入库 2：从出库单入库',
  `is_use` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用，默认启用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`depot_type_id`),
  KEY `depot_type_code` (`depot_type_code`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='出入库类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_out_box`
--

DROP TABLE IF EXISTS `ty_depot_out_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_out_box` (
  `box_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_code` varchar(30) DEFAULT NULL COMMENT '箱号,唯一',
  `depot_out_code` varchar(30) DEFAULT NULL COMMENT '出库单编号',
  `product_number` int(10) DEFAULT NULL COMMENT '总件数',
  `qc_id` int(10) DEFAULT NULL COMMENT '审核人',
  `qc_starttime` datetime DEFAULT NULL COMMENT '审核开始时间',
  `qc_endtime` datetime DEFAULT NULL COMMENT '审核结束时间',
  PRIMARY KEY (`box_id`),
  UNIQUE KEY `box_code` (`box_code`),
  KEY `depot_out_code` (`depot_out_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库箱子主表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_out_box_sub`
--

DROP TABLE IF EXISTS `ty_depot_out_box_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_out_box_sub` (
  `box_sub_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_id` int(10) DEFAULT NULL,
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  `qc_id` int(10) DEFAULT NULL COMMENT '审核人',
  `qc_starttime` datetime DEFAULT NULL COMMENT '审核开始时间',
  `qc_endtime` datetime DEFAULT NULL COMMENT '审核结束时间',
  PRIMARY KEY (`box_sub_id`),
  KEY `box_id` (`box_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库箱子子表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_out_main`
--

DROP TABLE IF EXISTS `ty_depot_out_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_out_main` (
  `depot_out_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `depot_out_code` varchar(30) NOT NULL COMMENT '出库单编号',
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '关联订单ID或者为空',
  `order_sn` varchar(30) NOT NULL COMMENT '来源订单编号',
  `provider_id` int(10) NOT NULL DEFAULT '0' COMMENT '关联退货经销商或者空',
  `depot_depot_id` int(10) NOT NULL DEFAULT '0',
  `depot_out_reason` varchar(255) NOT NULL COMMENT '出货说明',
  `depot_out_type` int(10) NOT NULL DEFAULT '0' COMMENT '出库类型ID',
  `depot_out_date` datetime NOT NULL COMMENT '实际出库时间',
  `depot_out_number` int(10) NOT NULL DEFAULT '0' COMMENT '出库商品总数',
  `depot_out_finished_number` int(10) DEFAULT '0' COMMENT '已完成数量。20130218添加',
  `depot_out_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '出库商品总价',
  `audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '仓库审核人',
  `audit_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '仓库审核时间',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `lock_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '锁定时间',
  `out_type` smallint(1) NOT NULL DEFAULT '1' COMMENT '出库类型1:普通出库 2:扫描出库',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除;0-未删除；1-已删除 20130218添加',
  `batch_id` int(10) DEFAULT '0' COMMENT '批次ID。20130218添加',
  PRIMARY KEY (`depot_out_id`),
  UNIQUE KEY `depot_out_code` (`depot_out_code`),
  KEY `order_id` (`order_id`),
  KEY `provider_id` (`provider_id`),
  KEY `depot_out_type` (`depot_out_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品出库一级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_out_sub`
--

DROP TABLE IF EXISTS `ty_depot_out_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_out_sub` (
  `depot_out_sub_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `depot_out_id` int(10) NOT NULL DEFAULT '0' COMMENT '主出库表ID',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品款式ID',
  `product_name` varchar(120) NOT NULL COMMENT '商品名字',
  `color_id` int(10) NOT NULL COMMENT '颜色ID',
  `size_id` int(10) NOT NULL COMMENT '尺码ID',
  `depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '仓库ID',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位ID',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '出货商品总数',
  `product_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '出货商品总价',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  `product_finished_number` int(10) DEFAULT '0' COMMENT '实际完成数量,20130218添加',
  PRIMARY KEY (`depot_out_sub_id`),
  KEY `depot_out_id` (`depot_out_id`),
  KEY `product_color_size` (`product_id`,`color_id`,`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品出库二级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_stockout`
--

DROP TABLE IF EXISTS `ty_depot_stockout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_stockout` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stockout_sn` varchar(20) DEFAULT NULL COMMENT '流水号',
  `trans_type` int(2) NOT NULL DEFAULT '0' COMMENT '事务类型 3-销售订单 4-退货单 ',
  `trans_sn` varchar(50) DEFAULT NULL COMMENT '关联单据sn, 订单，退货单',
  `depot_type` int(1) NOT NULL DEFAULT '0' COMMENT '出库入库标注，默认入库',
  `depot_sn` varchar(50) DEFAULT NULL COMMENT '关联盘点出入库单据sn',
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位ID',
  `batch_id` int(10) NOT NULL COMMENT '批次ID',
  `num` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  `memo` text COMMENT '备注',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_admin` int(10) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_stockout_sn` (`stockout_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='缺货登记表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_exchange_in`
--

DROP TABLE IF EXISTS `ty_exchange_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_exchange_in` (
  `exchange_leaf_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `exchange_id` int(10) NOT NULL DEFAULT '0' COMMENT '主调仓单ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '调仓数量',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品id',
  `dest_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '新仓库id',
  `dest_location_id` int(10) NOT NULL DEFAULT '0' COMMENT '新储位id',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  PRIMARY KEY (`exchange_leaf_id`),
  KEY `exchange_id` (`exchange_id`),
  KEY `product_color_size` (`product_id`,`color_id`,`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='调仓单管理三级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_exchange_main`
--

DROP TABLE IF EXISTS `ty_exchange_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_exchange_main` (
  `exchange_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `exchange_code` varchar(30) NOT NULL COMMENT '调仓单编号',
  `exchange_reason` varchar(255) NOT NULL COMMENT '调仓说明',
  `exchange_out_number` int(10) NOT NULL DEFAULT '0' COMMENT '调仓出总数',
  `exchange_in_number` int(10) NOT NULL DEFAULT '0' COMMENT '调仓入总数',
  `out_audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '仓库审核人',
  `out_audit_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '仓库审核时间',
  `out_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `out_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `in_admin` int(10) NOT NULL DEFAULT '0' COMMENT '入库人',
  `in_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '入库时间',
  `source_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '源库',
  `dest_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '目标库',
  `in_audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '入库审核人',
  `in_audit_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '入库审核时间',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `lock_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '锁定时间',
  `out_type` int(1) NOT NULL DEFAULT '1' COMMENT '出库类型1:普通出库 2:扫描出库',
  `exchange_out_finish_number` int(10) DEFAULT '0' COMMENT '实际出库数量 20130218添加',
  `exchange_in_finish_number` int(10) DEFAULT '0' COMMENT '实际入库数量 20130218添加',
  PRIMARY KEY (`exchange_id`),
  UNIQUE KEY `exchange_code` (`exchange_code`),
  KEY `out_type` (`out_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='调仓单管理主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_exchange_out`
--

DROP TABLE IF EXISTS `ty_exchange_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_exchange_out` (
  `exchange_sub_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `exchange_id` int(10) NOT NULL DEFAULT '0' COMMENT '主调仓单ID',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品款式ID',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '调库数量',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色id',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺寸id',
  `source_location_id` int(10) NOT NULL DEFAULT '0' COMMENT '源储位id',
  `memo` text NOT NULL COMMENT '备注',
  `source_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '源仓id',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  PRIMARY KEY (`exchange_sub_id`),
  KEY `exchange_id` (`exchange_id`),
  KEY `product_color_size` (`product_id`,`color_id`,`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='调仓单管理二级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_friend_link`
--

DROP TABLE IF EXISTS `ty_friend_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_friend_link` (
  `link_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '友情链接编号',
  `link_name` varchar(100) NOT NULL COMMENT '友情链接名称',
  `link_url` varchar(100) NOT NULL COMMENT '友情链接地址',
  `link_logo` varchar(100) NOT NULL COMMENT '友情链接LOGO',
  `sort_order` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序号',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `link_name` (`link_name`),
  KEY `show_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='友情链接表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_ad`
--

DROP TABLE IF EXISTS `ty_front_ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_ad` (
  `ad_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '广告编号',
  `position_id` int(10) NOT NULL DEFAULT '0' COMMENT '广告位置编号',
  `ad_name` varchar(255) NOT NULL DEFAULT '' COMMENT '广告名称',
  `ad_link` varchar(255) NOT NULL DEFAULT '' COMMENT '广告链接',
  `ad_code` text NOT NULL COMMENT '广告内容',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `click_count` mediumint(8) NOT NULL DEFAULT '0' COMMENT '点击数',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否启用,0未启用,1启用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `pic_url` varchar(45) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`ad_id`),
  KEY `position_id` (`position_id`),
  KEY `enabled` (`is_use`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='广告详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_ad_position`
--

DROP TABLE IF EXISTS `ty_front_ad_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_ad_position` (
  `position_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '广告位置编号',
  `position_tag` varchar(100) NOT NULL DEFAULT '' COMMENT '广告位置TAG',
  `position_name` varchar(255) NOT NULL DEFAULT '' COMMENT '广告位置名称',
  `page_name` varchar(100) NOT NULL DEFAULT '' COMMENT '所在页面名称',
  `brand_id` int(10) NOT NULL DEFAULT '0' COMMENT '所属品牌，可以为空',
  `category_id` int(10) NOT NULL DEFAULT '0' COMMENT '所属分类，可以为空',
  `ad_width` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '广告位宽度',
  `ad_height` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '广告位高度',
  `position_style` text NOT NULL COMMENT '广告样式',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='广告位置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_campaign`
--

DROP TABLE IF EXISTS `ty_front_campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_campaign` (
  `campaign_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '活动编号',
  `campaign_type` int(1) NOT NULL DEFAULT '1' COMMENT '1为全场满金额送赠品',
  `campaign_name` varchar(255) NOT NULL DEFAULT '' COMMENT '活动名称',
  `limit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '限制最小金额',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '赠送的商品ID',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '0未启用，1启用，2停止',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`campaign_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='网站赠送商品活动表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_cart`
--

DROP TABLE IF EXISTS `ty_front_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_cart` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL DEFAULT '0',
  `session_id` char(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `sub_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品子表中的主键sub_id',
  `product_id` int(10) NOT NULL DEFAULT '0',
  `color_id` int(10) NOT NULL DEFAULT '0',
  `size_id` int(10) NOT NULL DEFAULT '0',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '本店价',
  `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最终成交价',
  `product_num` int(10) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `package_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包ID，非礼包为0',
  `extension_id` int(10) NOT NULL DEFAULT '0' COMMENT '区分同一礼包多次购买',
  `discount_type` int(1) DEFAULT '0' COMMENT '折扣类型0.未折扣 1.限时抢购 2.礼包 3.手工更改 4.赠品',
  `create_date` datetime NOT NULL COMMENT '添加时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `tuan_id` int(10) NOT NULL DEFAULT '0' COMMENT '团购ID',
  PRIMARY KEY (`rec_id`),
  KEY `session_id` (`session_id`),
  KEY `UPDATE_TIME_INX` (`update_date`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_collect_product`
--

DROP TABLE IF EXISTS `ty_front_collect_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_collect_product` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL DEFAULT '0',
  `product_id` int(10) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  `product_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0:商品 1:礼包',
  PRIMARY KEY (`rec_id`),
  KEY `user_id` (`user_id`),
  KEY `goods_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户商品收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_focus_image`
--

DROP TABLE IF EXISTS `ty_front_focus_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_focus_image` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `focus_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `focus_url` varchar(255) DEFAULT NULL COMMENT '活动链接',
  `focus_img` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `focus_order` int(10) DEFAULT '0' COMMENT '排序',
  `focus_type` int(1) DEFAULT '0' COMMENT '1:首页焦点图 2:化妆品',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_admin` int(10) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='首页焦点图，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_hot_word`
--

DROP TABLE IF EXISTS `ty_front_hot_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_hot_word` (
  `hotword_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '热门关键字ID',
  `hotword_name` varchar(100) NOT NULL DEFAULT '' COMMENT '热门关键字名称',
  `hotword_url` varchar(255) NOT NULL DEFAULT '' COMMENT '热门关键字URL',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
  `click_count` int(10) NOT NULL DEFAULT '0' COMMENT '点击量',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`hotword_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='热门关键字管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_nav`
--

DROP TABLE IF EXISTS `ty_front_nav`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_nav` (
  `nav_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `category_ids` varchar(20) NOT NULL DEFAULT '' COMMENT '可填可不填，填的话必须是分类ID,用半角逗号分隔',
  `nav_name` varchar(100) NOT NULL DEFAULT '' COMMENT '无分类ID的则自己填写，有分类ID的则默认分类名称并可修改',
  `nav_url` varchar(255) NOT NULL DEFAULT '' COMMENT '无分类ID的，则必须填写，有分类ID则不填写',
  `nav_ad_img` varchar(100) NOT NULL DEFAULT '0' COMMENT '广告图片',
  `nav_ad_url` varchar(255) NOT NULL DEFAULT '' COMMENT '广告链接',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`nav_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='首页导航及二级导航管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_front_single_page`
--

DROP TABLE IF EXISTS `ty_front_single_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_front_single_page` (
  `single_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '单页编号',
  `page_name` varchar(255) NOT NULL DEFAULT '' COMMENT '单页名称',
  `page_content` text NOT NULL COMMENT '单页内容',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否启用，0未启用，1启用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`single_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='单页专题管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_inventory_warning`
--

DROP TABLE IF EXISTS `ty_inventory_warning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_inventory_warning` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `warn_type` int(1) NOT NULL COMMENT '预警类型：1-按商品款号，2-按指定批次',
  `warn_value` int(10) DEFAULT NULL COMMENT '预警值：存放商品ID或批次ID',
  `min_number` int(10) NOT NULL DEFAULT '0' COMMENT '最小预警库存数',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_admin` int(10) DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `warn_status` int(1) NOT NULL DEFAULT '0' COMMENT '预警状态：1-可用，2-结束',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存预警表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_invite_activity_leaf`
--

DROP TABLE IF EXISTS `ty_invite_activity_leaf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_invite_activity_leaf` (
  `activites_gifts_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activites_condition_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '子活动ID',
  `rank_id` int(11) NOT NULL COMMENT '邀请人级别',
  `reward_type` smallint(1) NOT NULL COMMENT '邀请人获得奖励类型',
  `reward_type_value` varchar(255) NOT NULL COMMENT '邀请人获得奖励类型值',
  `goods_id` text COMMENT '商品IDs',
  PRIMARY KEY (`activites_gifts_id`),
  KEY `index_name` (`activites_condition_id`,`rank_id`,`reward_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_invite_activity_main`
--

DROP TABLE IF EXISTS `ty_invite_activity_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_invite_activity_main` (
  `activites_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activites_title` varchar(255) NOT NULL COMMENT '活动名称',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `is_select` int(10) DEFAULT '0' COMMENT '是否是当前活动(0未选择,1 默认活动,2 当前活动)',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '状态（0为未审核、1为已审核、2为已停止）',
  `op_stop_aid` int(11) DEFAULT NULL COMMENT '删除人',
  `op_stop_time` datetime DEFAULT NULL COMMENT '删除时间',
  `op_add_aid` int(11) DEFAULT NULL COMMENT '添加人',
  `op_add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `op_check_aid` int(11) DEFAULT NULL COMMENT '审核人',
  `op_check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `op_update_aid` int(11) DEFAULT NULL COMMENT '修改人',
  `op_update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`activites_id`),
  KEY `index_name` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_invite_activity_sub`
--

DROP TABLE IF EXISTS `ty_invite_activity_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_invite_activity_sub` (
  `activites_condition_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activites_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '活动ID',
  `activites_title` varchar(255) NOT NULL COMMENT '子活动名称',
  `activites_num` int(6) NOT NULL COMMENT '邀请满多少人',
  `activites_condition` varchar(20) NOT NULL COMMENT '好友达到阶段',
  `activites_first_purchase` decimal(10,2) DEFAULT NULL COMMENT '首购金额',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除状态',
  `del_aid` int(11) DEFAULT NULL COMMENT '删除人',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`activites_condition_id`),
  UNIQUE KEY `unique_subacti` (`activites_id`,`activites_num`,`activites_condition`,`activites_first_purchase`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_invite_activity_user`
--

DROP TABLE IF EXISTS `ty_invite_activity_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_invite_activity_user` (
  `activites_user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `invite_user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '邀请人ID',
  `invited_user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '被邀请人ID',
  `activites_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '活动ID',
  `invited_tag` varchar(20) NOT NULL COMMENT '邀请方式tag参数',
  `order_id` int(11) DEFAULT NULL COMMENT '首购订单ID',
  `status` varchar(20) NOT NULL DEFAULT '0' COMMENT '1为注册登陆, 2为验证邮箱, 3为验证手机, 4为首次购买',
  PRIMARY KEY (`activites_user_id`),
  KEY `index_name` (`invite_user_id`,`activites_id`,`invited_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_invite_user_gifts`
--

DROP TABLE IF EXISTS `ty_invite_user_gifts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_invite_user_gifts` (
  `activites_gifts_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activites_condition_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '邀请子活动ID',
  `invite_user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '邀请人ID',
  `activites_gifts` text NOT NULL COMMENT '邀请人获得的奖励',
  `date` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`activites_gifts_id`),
  KEY `index_name` (`activites_condition_id`,`invite_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_invite_way`
--

DROP TABLE IF EXISTS `ty_invite_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_invite_way` (
  `invite_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `invite_name` varchar(50) NOT NULL COMMENT '邀请方式名称',
  `invite_tag` varchar(50) NOT NULL COMMENT '邀请参数',
  `invite_desc` varchar(255) NOT NULL COMMENT '邀请文案说明',
  `is_exclusive` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否为专属邀请方式：0为非专属链接，1为专属链接',
  `op_add_aid` mediumint(8) DEFAULT NULL COMMENT '添加人',
  `op_add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`invite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请方式表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_location_info`
--

DROP TABLE IF EXISTS `ty_location_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_location_info` (
  `location_id` int(10) NOT NULL AUTO_INCREMENT,
  `depot_id` int(10) NOT NULL COMMENT '仓库id',
  `location_code1` varchar(3) NOT NULL COMMENT '储位编码 1',
  `location_code2` varchar(2) NOT NULL COMMENT '储位编码 2',
  `location_code3` varchar(2) NOT NULL COMMENT '储位编码 3',
  `location_code4` varchar(2) NOT NULL COMMENT '储位编码 4',
  `location_name` varchar(100) NOT NULL COMMENT '储位名称',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '1为启用,0为停用',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人id, int5改成int10 20130218修改',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `location_name` (`location_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='仓库储位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_mail_log`
--

DROP TABLE IF EXISTS `ty_mail_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_mail_log` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `mail_from` varchar(255) NOT NULL COMMENT '发件人',
  `mail_to` varchar(255) NOT NULL COMMENT '收件人',
  `template_id` int(10) NOT NULL DEFAULT '0' COMMENT 'mail模板',
  `template_subject` varchar(255) NOT NULL COMMENT '邮件标题',
  `template_content` text NOT NULL COMMENT '邮件内容',
  `template_priority` int(10) DEFAULT '0' COMMENT '邮件优先级',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人，无则不填',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `send_date` datetime NOT NULL COMMENT '发送日期',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '发送状态，0未发，1已发，2失败',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='邮件发送记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_mail_templates`
--

DROP TABLE IF EXISTS `ty_mail_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_mail_templates` (
  `template_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '模板编号',
  `template_code` varchar(100) NOT NULL COMMENT '模板TAG',
  `template_name` varchar(100) NOT NULL COMMENT '模板名称',
  `is_html` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是HTML,0不是',
  `template_subject` varchar(200) NOT NULL DEFAULT '' COMMENT '模板标题',
  `template_content` text NOT NULL COMMENT '模板内容',
  `template_priority` int(10) NOT NULL DEFAULT '0' COMMENT '0优先级最低',
  `sms_content` varchar(255) NOT NULL DEFAULT '' COMMENT '对应的短信模板',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`template_id`),
  UNIQUE KEY `template_code` (`template_code`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='邮件模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_mami_tuan`
--

DROP TABLE IF EXISTS `ty_mami_tuan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_mami_tuan` (
  `tuan_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品ID编号',
  `tuan_name` varchar(255) NOT NULL COMMENT '团购名称',
  `buy_num` int(10) DEFAULT '0' COMMENT '购买人数',
  `tuan_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '团购价',
  `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '团购状态（0为未审核、1为已审核、2为已停止、3为已结束）',
  `tuan_online_time` datetime NOT NULL COMMENT '团购开始时间',
  `tuan_offline_time` datetime NOT NULL COMMENT '团购结束时间',
  `tuan_desc` text NOT NULL COMMENT '购买需知',
  `userdefine1` text COMMENT '头部描述',
  `userdefine2` text COMMENT '中部描述',
  `userdefine3` text COMMENT '底部描述',
  `userdefine4` text COMMENT '团购商品详情页右上角',
  `img_315_207` varchar(100) NOT NULL COMMENT '团购首页商品图',
  `img_168_110` varchar(100) NOT NULL COMMENT '团购首页最近浏览小图',
  `img_500_450` varchar(100) NOT NULL COMMENT '团购商品详情页大图',
  `tuan_img` varchar(100) NOT NULL DEFAULT '' COMMENT '团购图片',
  `product_discount` decimal(2,1) DEFAULT '0.0' COMMENT '团购折扣',
  `tuan_sort` int(11) DEFAULT '0' COMMENT '手工排序',
  `op_stop_aid` int(11) DEFAULT NULL COMMENT '删除人',
  `op_stop_time` datetime DEFAULT NULL COMMENT '删除时间',
  `op_add_aid` int(11) DEFAULT NULL COMMENT '添加人',
  `op_add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `op_check_aid` int(11) DEFAULT NULL COMMENT '审核人',
  `op_check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `op_update_aid` int(11) DEFAULT NULL COMMENT '修改人',
  `op_update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`tuan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_mobile_blacklist`
--

DROP TABLE IF EXISTS `ty_mobile_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_mobile_blacklist` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `reason` varchar(255) NOT NULL DEFAULT '' COMMENT '拒收原因',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='手机黑名单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_new_goods_num`
--

DROP TABLE IF EXISTS `ty_new_goods_num`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_new_goods_num` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `color_id` int(11) DEFAULT NULL,
  `on_sale` tinyint(1) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '0',
  `num` mediumint(2) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `size_ids` varchar(64) DEFAULT NULL,
  `goods_sn` varchar(64) DEFAULT NULL,
  `goods_name` varchar(64) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `brand_name` varchar(64) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `rush_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_img_id` (`img_id`),
  UNIQUE KEY `idx_goods_color_id` (`goods_id`,`color_id`),
  KEY `idx_on_sale` (`on_sale`),
  KEY `idx_num` (`num`),
  KEY `idx_size_ids` (`size_ids`),
  KEY `idx_type_id` (`type_id`),
  KEY `idx_brand_id` (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_online_support_log`
--

DROP TABLE IF EXISTS `ty_online_support_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_online_support_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_id` int(11) NOT NULL DEFAULT '0' COMMENT 'issue_id',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
  `closed` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否关闭0为未闭,1为关闭',
  `create_admin` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `close_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_online_support_main`
--

DROP TABLE IF EXISTS `ty_online_support_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_online_support_main` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `admin_id` int(10) NOT NULL DEFAULT '0' COMMENT '管理员ID',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户编号，0为匿名',
  `session_id` varchar(50) NOT NULL DEFAULT '' COMMENT '会话ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0待接，1打开，2关闭',
  `user_close` int(1) NOT NULL DEFAULT '0' COMMENT '是否用户关闭，0未关闭，1关闭',
  `ipaddress` varchar(40) NOT NULL DEFAULT '' COMMENT '用户IP地址',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='在线客服信息主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_online_support_sub`
--

DROP TABLE IF EXISTS `ty_online_support_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_online_support_sub` (
  `message_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `rec_id` int(10) NOT NULL DEFAULT '0' COMMENT '在线客服主表主键',
  `content` text NOT NULL COMMENT '聊天信息',
  `qora` int(1) NOT NULL DEFAULT '0' COMMENT '问题还是回复，0问题，1回复',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `admin_id` int(10) NOT NULL DEFAULT '0' COMMENT '管理员ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='在线客服信息子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_onlinepay_log`
--

DROP TABLE IF EXISTS `ty_onlinepay_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_onlinepay_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `pay_code` varchar(20) NOT NULL DEFAULT '',
  `data` text,
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_action`
--

DROP TABLE IF EXISTS `ty_order_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_action` (
  `action_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '操作编号',
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `is_return` int(1) NOT NULL DEFAULT '0' COMMENT '0为订单，2为退货单，3为换货单',
  `order_status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态',
  `shipping_status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '发货状态',
  `pay_status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '支付状态',
  `action_note` varchar(255) NOT NULL DEFAULT '' COMMENT '操作备注',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`action_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单/退货/换货单操作记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_advice`
--

DROP TABLE IF EXISTS `ty_order_advice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_advice` (
  `advice_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '建议ID',
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单/退货/换货单ID',
  `type_id` int(10) NOT NULL DEFAULT '0' COMMENT '建议类型ID',
  `is_return` int(1) NOT NULL DEFAULT '1' COMMENT '1为订单，2为退货单，3为换货单',
  `advice_content` varchar(255) NOT NULL COMMENT '建议内容',
  `advice_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `advice_date` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`advice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单/退货/换货单建议记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_advice_type`
--

DROP TABLE IF EXISTS `ty_order_advice_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_advice_type` (
  `type_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '建议类型ID',
  `type_name` varchar(120) NOT NULL COMMENT '建议类型名称',
  `type_code` varchar(20) NOT NULL COMMENT '建议类型CODE',
  `type_color` varchar(10) NOT NULL COMMENT '建议类型颜色',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否启用',
  `create_admin` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单建议类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_change_info`
--

DROP TABLE IF EXISTS `ty_order_change_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_change_info` (
  `change_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '换货单编号',
  `change_sn` varchar(20) NOT NULL COMMENT '换货单号',
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '关联订单号',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户编号',
  `consignee` varchar(60) NOT NULL DEFAULT '' COMMENT '收货人',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `country` int(10) NOT NULL DEFAULT '0' COMMENT '国家',
  `province` int(10) NOT NULL DEFAULT '0' COMMENT '省',
  `city` int(10) NOT NULL DEFAULT '0' COMMENT '市',
  `district` int(10) NOT NULL DEFAULT '0' COMMENT '区',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  `zipcode` varchar(50) NOT NULL DEFAULT '' COMMENT '邮编',
  `tel` varchar(60) NOT NULL DEFAULT '' COMMENT '电话',
  `mobile` varchar(60) NOT NULL DEFAULT '' COMMENT '手机',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `lock_date` datetime DEFAULT NULL COMMENT '锁定时间',
  `change_status` int(1) NOT NULL DEFAULT '0' COMMENT '换货单状态',
  `confirm_admin` int(10) DEFAULT '0' COMMENT '客服审核人',
  `confirm_date` datetime NOT NULL COMMENT '客服审核日期',
  `shipped_status` int(1) NOT NULL DEFAULT '0' COMMENT '收货状态',
  `shipped_admin` int(10) NOT NULL DEFAULT '0' COMMENT '收货人',
  `shipped_date` datetime NOT NULL COMMENT '收货日期',
  `shipping_status` int(1) NOT NULL DEFAULT '0' COMMENT '发货状态',
  `shipping_admin` int(10) NOT NULL DEFAULT '0' COMMENT '发货人',
  `shipping_date` datetime NOT NULL COMMENT '发货日期',
  `cancel_admin` int(10) NOT NULL DEFAULT '0' COMMENT '作废人',
  `cancel_date` datetime NOT NULL COMMENT '作废日期',
  `is_ok` int(1) NOT NULL DEFAULT '0' COMMENT '完结状态',
  `is_ok_admin` int(10) NOT NULL DEFAULT '0' COMMENT '完结人',
  `is_ok_date` datetime NOT NULL COMMENT '完结日期',
  `change_reason` varchar(255) NOT NULL DEFAULT '' COMMENT '换货理由',
  `to_buyer` varchar(255) NOT NULL DEFAULT '' COMMENT '客服对客户留言',
  `shipping_id` tinyint(3) NOT NULL DEFAULT '0' COMMENT '快递方式',
  `invoice_no` varchar(50) NOT NULL DEFAULT '' COMMENT '快递单号',
  `real_shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际运费',
  `shipping_true` int(1) NOT NULL DEFAULT '1' COMMENT '是否实际发货，0未发货，1实发货，默认为1',
  `pick_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '拣货单号',
  `odd` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否异常单 1:异常单 0:正常单',
  PRIMARY KEY (`change_id`,`shipped_date`),
  KEY `change_status` (`change_status`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品换货单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_change_product`
--

DROP TABLE IF EXISTS `ty_order_change_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_change_product` (
  `cp_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '换货单商品自增ID',
  `change_id` int(10) NOT NULL COMMENT '换货单编号',
  `op_id` int(10) NOT NULL DEFAULT '0' COMMENT '原订单商品表自增ID',
  `product_id` int(10) NOT NULL COMMENT '商品编号',
  `src_color_id` int(10) NOT NULL COMMENT '原颜色',
  `src_size_id` int(10) NOT NULL COMMENT '原尺寸',
  `color_id` int(10) NOT NULL COMMENT '现颜色',
  `size_id` int(10) NOT NULL COMMENT '现尺寸',
  `change_num` int(10) NOT NULL DEFAULT '0' COMMENT '换货数',
  `src_consign_num` int(10) NOT NULL DEFAULT '0' COMMENT '原代销数量（用于推算实虚库数量）',
  `consign_num` int(10) NOT NULL DEFAULT '0' COMMENT '现代销数量（用于推算实虚库数量）',
  `parent_cp_id` int(10) NOT NULL DEFAULT '0' COMMENT '父级',
  `change_admin` int(10) NOT NULL DEFAULT '0' COMMENT '换货时间',
  `change_date` datetime NOT NULL COMMENT '换货人',
  `package_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包ID',
  `extension_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包识别ID',
  PRIMARY KEY (`cp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='换货单商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_info`
--

DROP TABLE IF EXISTS `ty_order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_info` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '订单SN',
  `source_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单来源',
  `user_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户编号',
  `consignee` varchar(60) NOT NULL DEFAULT '' COMMENT '收货人',
  `country` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '国家',
  `province` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '省',
  `city` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '市',
  `district` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '县/区',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '详细地址',
  `zipcode` varchar(60) NOT NULL DEFAULT '' COMMENT '邮编',
  `tel` varchar(60) NOT NULL DEFAULT '' COMMENT '联系电话',
  `mobile` varchar(60) NOT NULL DEFAULT '' COMMENT '手机',
  `best_time` varchar(120) NOT NULL DEFAULT '' COMMENT '最佳送货时间',
  `user_notice` varchar(255) NOT NULL DEFAULT '' COMMENT '客户留言',
  `invoice_title` varchar(120) NOT NULL DEFAULT '' COMMENT '发票抬头,不填则代表无需发票',
  `invoice_content` varchar(255) NOT NULL DEFAULT '' COMMENT '发票内容',
  `product_num` int(10) NOT NULL DEFAULT '0' COMMENT '商品总数量',
  `order_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费，=0不收运费 >0收运费',
  `shipping_id` int(10) NOT NULL DEFAULT '0' COMMENT '快递方式',
  `real_shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '物流实际发生运费',
  `paid_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已经支付金额',
  `user_ip` varchar(50) NOT NULL DEFAULT '' COMMENT '用户IP地址',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人id int10修改为有符号 20130218修改',
  `create_date` datetime NOT NULL COMMENT '添加人',
  `confirm_admin` int(10) DEFAULT NULL COMMENT '审核人id int10修改为有符号 20130218修改',
  `confirm_date` datetime NOT NULL COMMENT '审核时间',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `order_status` int(1) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `shipping_status` int(1) NOT NULL DEFAULT '0' COMMENT '发货状态',
  `shipping_admin` int(10) NOT NULL DEFAULT '0' COMMENT '发货人',
  `shipping_date` datetime NOT NULL COMMENT '发货时间',
  `pay_status` int(1) DEFAULT '0' COMMENT '支付状态',
  `pay_id` int(10) NOT NULL DEFAULT '0' COMMENT '支付方式',
  `bank_code` varchar(20) NOT NULL DEFAULT '' COMMENT '银行代码',
  `finance_admin` int(10) NOT NULL DEFAULT '0' COMMENT '财审人',
  `finance_date` datetime NOT NULL COMMENT '财审时间',
  `invoice_no` varchar(50) NOT NULL DEFAULT '' COMMENT '运单号',
  `to_buyer` varchar(255) NOT NULL DEFAULT '' COMMENT '客服对客户留言',
  `is_ok` int(1) NOT NULL DEFAULT '0' COMMENT '是否完结',
  `is_ok_admin` int(10) NOT NULL DEFAULT '0' COMMENT '完结人',
  `is_ok_date` datetime NOT NULL COMMENT '完结时间',
  `point_amount` int(10) NOT NULL DEFAULT '0' COMMENT '积分数量',
  `point_sent` int(1) NOT NULL DEFAULT '0' COMMENT '积分是否已送出，1为送出',
  `shipping_true` int(1) NOT NULL DEFAULT '1' COMMENT '是否实际发货，0未发货，1实发货，默认为1',
  `pick_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '拣货单号',
  `odd` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否异常单 1:异常单 0:正常单',
  `is_pick` int(1) DEFAULT '0' COMMENT '是否拣货 0-未拣; 1-已拣,20130218添加',
  `pick_admin` int(10) DEFAULT NULL COMMENT '拣货人,20130218添加',
  `pick_date` datetime DEFAULT NULL COMMENT '拣货开始时间,20130218添加',
  `is_qc` int(1) DEFAULT '0' COMMENT '是否复核 0-未 ；1-已 20130218添加',
  `qc_admin` int(10) DEFAULT NULL COMMENT '复核人,20130218添加',
  `qc_date` datetime DEFAULT NULL COMMENT '复核开始时间,20130218添加',
  `order_weight` decimal(10,3) DEFAULT '0.000' COMMENT '订单的重量,20130218添加',
  `order_weight_unreal` decimal(10,3) DEFAULT '0.000' COMMENT '订单的虚拟重量,20130523添加',
  `is_virtual` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否虚拟商品',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_sn` (`order_sn`),
  KEY `user_id` (`user_id`),
  KEY `source_id` (`source_id`),
  KEY `shipping_id` (`shipping_id`),
  KEY `pay_id` (`pay_id`),
  KEY `lock_admin` (`lock_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_pay_track`
--

DROP TABLE IF EXISTS `ty_order_pay_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_pay_track` (
  `track_id` int(11) NOT NULL AUTO_INCREMENT,
  `track_sn` varchar(20) NOT NULL COMMENT '跟踪号',
  `order_ids` varchar(255) NOT NULL DEFAULT '' COMMENT '支付的订单ID，多个用中横线-连接',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `pay_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `pay_id` int(11) NOT NULL DEFAULT '0' COMMENT '支付方式ID',
  `bank_code` varchar(10) NOT NULL DEFAULT '' COMMENT '银行编码',
  `pay_status` tinyint(3) NOT NULL COMMENT '0 未支付 1已成功支付',
  `add_time` datetime DEFAULT NULL COMMENT '记录生成时间',
  PRIMARY KEY (`track_id`),
  UNIQUE KEY `track_sn_UNIQUE` (`track_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='订单支付跟踪表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_payment`
--

DROP TABLE IF EXISTS `ty_order_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_payment` (
  `payment_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单编号',
  `is_return` int(1) NOT NULL DEFAULT '0' COMMENT '是否是退款',
  `pay_id` int(11) NOT NULL DEFAULT '0' COMMENT '支付方式编号',
  `bank_code` varchar(20) NOT NULL DEFAULT '' COMMENT '银行代码',
  `payment_account` varchar(255) DEFAULT NULL COMMENT '支付帐号',
  `payment_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `trade_no` varchar(255) DEFAULT NULL COMMENT '交易号',
  `payment_remark` varchar(255) DEFAULT NULL COMMENT '支付备注',
  `payment_admin` int(10) DEFAULT NULL COMMENT '创建人id int11改成int10 20130218修改',
  `payment_date` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`payment_id`),
  KEY `PAYID_INX` (`pay_id`),
  KEY `ORDERID_INX` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单支付记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_point`
--

DROP TABLE IF EXISTS `ty_order_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_point` (
  `rec_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL DEFAULT '0',
  `order_sn` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `point_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `op_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `buying_point_rate` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '购物送积分率',
  `online_rate` decimal(10,2) NOT NULL DEFAULT '1.00' COMMENT '在线支付双倍积分',
  PRIMARY KEY (`rec_id`),
  KEY `NewIndex1` (`order_id`),
  KEY `NewIndex2` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_product`
--

DROP TABLE IF EXISTS `ty_order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_product` (
  `op_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单编号',
  `product_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品编号',
  `product_num` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '商品数量',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'f-club价',
  `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单品价格',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺寸ID',
  `total_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '该商品总价格，等于product_price*product_num',
  `package_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包ID',
  `extension_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包识别ID',
  `tuan_id` int(11) NOT NULL DEFAULT '0' COMMENT '团购ID',
  `discount_type` int(1) NOT NULL DEFAULT '0' COMMENT '折扣类型0.未折扣 1.限时抢购 2.礼包 3.手工更改 4.赠品',
  `consign_num` int(10) NOT NULL DEFAULT '0' COMMENT '代销库存数量',
  `consign_mark` int(10) NOT NULL DEFAULT '0' COMMENT '虚库数量，仅作标记,转实际库存是不变化',
  PRIMARY KEY (`op_id`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_return_info`
--

DROP TABLE IF EXISTS `ty_order_return_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_return_info` (
  `return_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '退货单编号',
  `return_sn` varchar(20) NOT NULL COMMENT '退货单SN',
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '原订单号',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户编号',
  `consignee` varchar(60) NOT NULL DEFAULT '' COMMENT '退货人',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  `zipcode` varchar(50) NOT NULL DEFAULT '' COMMENT '邮编',
  `tel` varchar(60) NOT NULL DEFAULT '' COMMENT '电话',
  `mobile` varchar(60) NOT NULL DEFAULT '' COMMENT '手机',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `product_num` int(10) NOT NULL DEFAULT '0' COMMENT '商品总数量',
  `return_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退货单金额',
  `paid_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已退金额',
  `return_shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '返还订单运费金额',
  `return_status` int(1) NOT NULL DEFAULT '0' COMMENT '退货单状态',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `lock_date` datetime NOT NULL COMMENT '锁定日期',
  `confirm_admin` int(10) NOT NULL DEFAULT '0' COMMENT '客服审核人',
  `confirm_date` datetime NOT NULL COMMENT '客服审核日期',
  `pay_status` int(1) NOT NULL DEFAULT '0' COMMENT '是否退款',
  `finance_admin` int(10) NOT NULL DEFAULT '0' COMMENT '财务审核人',
  `finance_date` datetime NOT NULL COMMENT '财务审核日期',
  `cancel_admin` int(10) NOT NULL DEFAULT '0' COMMENT '作废人',
  `cancel_date` datetime NOT NULL COMMENT '作废日期',
  `shipping_status` int(1) NOT NULL DEFAULT '0' COMMENT '收货状态',
  `shipping_admin` int(10) NOT NULL DEFAULT '0' COMMENT '收货人',
  `shipping_date` datetime NOT NULL COMMENT '收货日期',
  `invoice_no` varchar(50) NOT NULL DEFAULT '' COMMENT '运单号',
  `to_buyer` varchar(255) NOT NULL DEFAULT '' COMMENT '客服给客户留言',
  `return_reason` varchar(255) NOT NULL DEFAULT '' COMMENT '退货原因',
  `hope_time` datetime NOT NULL COMMENT '期望退货日期',
  `is_ok` int(1) NOT NULL DEFAULT '0' COMMENT '是否完结',
  `is_ok_admin` int(10) NOT NULL DEFAULT '0' COMMENT '完结时间',
  `is_ok_date` datetime NOT NULL COMMENT '完结管理员ID',
  `apply_id` mediumint(8) DEFAULT NULL COMMENT '申请退货单号',
  PRIMARY KEY (`return_id`),
  UNIQUE KEY `return_sn` (`return_sn`),
  KEY `order_id` (`order_id`),
  KEY `return_status` (`return_status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_return_product`
--

DROP TABLE IF EXISTS `ty_order_return_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_return_product` (
  `rp_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '退货单商品自增ID',
  `return_id` int(10) NOT NULL DEFAULT '0' COMMENT '退货单编号',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '产品编号',
  `product_num` int(10) NOT NULL DEFAULT '1' COMMENT '产品数量',
  `max_number` int(10) NOT NULL DEFAULT '1' COMMENT '最大退货数量',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '市场价格',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '本站价格',
  `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单品成交价格',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色编号',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺寸编号',
  `total_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '该商品总价格，等于product_price*product_num',
  `cp_id` int(10) NOT NULL DEFAULT '0' COMMENT '换货单商品表自增ID',
  `op_id` int(10) NOT NULL DEFAULT '0' COMMENT '原订单商品表自增ID',
  `package_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包id',
  `extension_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包识别ID',
  `consign_num` int(10) NOT NULL DEFAULT '0' COMMENT '代销库存数量',
  PRIMARY KEY (`rp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_return_record`
--

DROP TABLE IF EXISTS `ty_order_return_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_return_record` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `shipping_sn` varchar(20) NOT NULL COMMENT '退包运单号',
  `box_no` varchar(20) NOT NULL COMMENT '退包跟踪号，箱号',
  `back_cat` char(1) DEFAULT NULL COMMENT '退包类别 0-退货 1-拒收',
  `scan_admin` int(10) NOT NULL COMMENT '退包录入人',
  `scan_date` datetime NOT NULL COMMENT '退包录入时间',
  `bind_admin` int(10) DEFAULT NULL COMMENT '退包绑定操作者id',
  `bind_date` datetime DEFAULT NULL COMMENT '退包绑定操作时间',
  `shipping_name` varchar(30) DEFAULT NULL COMMENT '退包快递公司名称',
  `back_num` int(2) DEFAULT NULL COMMENT '退包商品数量',
  `back_memo` varchar(30) DEFAULT NULL COMMENT '异常备注,固定内容：正常、物流锁定、私自寄回、发错货、未退回赠品、非我司商品',
  `back_memo_desc` varchar(200) DEFAULT NULL COMMENT '其他备注描述',
  `back_type` varchar(10) DEFAULT NULL COMMENT '退包类型 固定内容：正品、次品、半次品',
  `invoice_sn` varchar(20) DEFAULT NULL COMMENT '原订单面单号',
  `order_sn` varchar(20) DEFAULT NULL COMMENT '原订单 No',
  `return_sn` varchar(20) DEFAULT NULL COMMENT '对应退货单 No',
  `update_admin` int(10) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `scan_serial_no` varchar(50) DEFAULT NULL COMMENT '扫描流水号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户订单退包记录表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_return_shipping_fee`
--

DROP TABLE IF EXISTS `ty_order_return_shipping_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_return_shipping_fee` (
  `return_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '退货单编号',
  `return_sn` varchar(20) NOT NULL COMMENT '退货单SN',
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '原订单号',
  `user_shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '客户退包运费',
  `finance_admin` int(10) NOT NULL DEFAULT '0' COMMENT '财务审核人',
  `finance_date` datetime NOT NULL COMMENT '财务审核日期',
  `shipping_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户退包运费记录表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_routing`
--

DROP TABLE IF EXISTS `ty_order_routing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_routing` (
  `routing_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '路由ID',
  `source_id` int(11) NOT NULL DEFAULT '0' COMMENT '订单来源ID',
  `shipping_id` int(11) NOT NULL DEFAULT '0' COMMENT '快递方式ID',
  `pay_id` int(11) NOT NULL DEFAULT '0' COMMENT '支付方式ID',
  `show_type` int(1) NOT NULL DEFAULT '1' COMMENT '1:前台显示 2:后台显示 3:前后台全部显示 4:前后台均不显示',
  `routing` char(1) NOT NULL DEFAULT '' COMMENT 'F:先财审, S:先发货',
  PRIMARY KEY (`routing_id`),
  KEY `NewIndex1` (`source_id`),
  KEY `NewIndex2` (`shipping_id`),
  KEY `NewIndex3` (`pay_id`),
  KEY `FROM_SHIP_PAY_INX` (`source_id`,`shipping_id`,`pay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单发货及财审路由表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_shipping_status`
--

DROP TABLE IF EXISTS `ty_order_shipping_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_shipping_status` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shipping_id` int(10) NOT NULL DEFAULT '0' COMMENT '快递公司',
  `invoice_no` varchar(50) NOT NULL DEFAULT '0' COMMENT '运单号',
  `company` varchar(30) NOT NULL COMMENT '快递公司编码,一律用小写字母',
  `invoice_state` int(2) DEFAULT NULL COMMENT '快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态',
  `shipping_detail` text COMMENT '快递单明细,json 格式文本',
  `create_date` datetime NOT NULL COMMENT '添加时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递单明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_source`
--

DROP TABLE IF EXISTS `ty_order_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_source` (
  `source_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '订单来源ID',
  `source_code` varchar(20) NOT NULL COMMENT '订单来源CODE',
  `source_name` varchar(50) NOT NULL COMMENT '订单来源名称',
  `is_use` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否使用',
  `create_admin` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单来源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_package_area`
--

DROP TABLE IF EXISTS `ty_package_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_package_area` (
  `area_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `package_id` int(10) NOT NULL COMMENT '礼包id',
  `area_name` varchar(255) NOT NULL COMMENT '区域名称',
  `min_number` int(10) NOT NULL DEFAULT '1' COMMENT '最少购买量',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `area_type` int(1) NOT NULL DEFAULT '1' COMMENT '1:商品 2:自定义内容',
  `area_text` mediumtext NOT NULL COMMENT '自定义区域内容',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品礼包区域表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_package_area_product`
--

DROP TABLE IF EXISTS `ty_package_area_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_package_area_product` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `package_id` int(10) NOT NULL DEFAULT '0' COMMENT '礼包id',
  `area_id` int(10) NOT NULL DEFAULT '0' COMMENT '区域id',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品id',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '商品排序',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '本店价',
  `default_color_id` int(10) NOT NULL DEFAULT '0' COMMENT '默认颜色id',
  `cost_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品成本价',
  `consign_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '代销成本价',
  `consign_rate` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '浮动代销率',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='礼包区域商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_package_info`
--

DROP TABLE IF EXISTS `ty_package_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_package_info` (
  `package_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '礼包id',
  `package_name` varchar(255) NOT NULL DEFAULT '' COMMENT '礼包名称',
  `package_image` varchar(255) DEFAULT NULL COMMENT '礼包图片',
  `package_homepage_image` varchar(255) DEFAULT NULL COMMENT '礼包首页图片',
  `package_status` int(1) NOT NULL DEFAULT '0' COMMENT '0:已提交 1:已启用 2:已停用',
  `package_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '礼包总价',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '添加人id',
  `create_date` datetime NOT NULL COMMENT '添加时间',
  `check_admin` int(10) NOT NULL DEFAULT '0' COMMENT '审核人即启用人',
  `check_date` datetime NOT NULL COMMENT '审核时间',
  `over_admin` int(10) NOT NULL DEFAULT '0' COMMENT '停用人',
  `over_date` datetime NOT NULL COMMENT '停用时间',
  `over_note` varchar(255) NOT NULL COMMENT '停用说明',
  `is_recommend` int(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `package_desc` mediumtext NOT NULL COMMENT '礼包简介',
  `package_goods_number` int(10) NOT NULL DEFAULT '0' COMMENT '礼包商品数',
  `package_type` int(1) NOT NULL DEFAULT '0' COMMENT '礼包类型 0=单区域固定 1=单区多数量 2=多区每区1个 3=多区固定最小数量',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '礼包排序',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '礼包市场价',
  `own_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '礼包本店价',
  `package_other_config` mediumtext NOT NULL COMMENT '礼包其他配置 格式：礼包商品数量|||礼包价格|||礼包fclub价格|||礼包市场价格&&&（另一组格式）',
  `is_liuyan` int(1) NOT NULL DEFAULT '0' COMMENT '0:不设留言 1:设置留言',
  `is_empty` int(1) NOT NULL DEFAULT '0' COMMENT '售空是否显示',
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品礼包基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_payment_info`
--

DROP TABLE IF EXISTS `ty_payment_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_payment_info` (
  `pay_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `pay_code` varchar(50) NOT NULL DEFAULT '',
  `pay_name` varchar(120) NOT NULL DEFAULT '',
  `pay_fee` varchar(10) NOT NULL DEFAULT '0',
  `pay_desc` text NOT NULL,
  `sort_order` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `pay_config` text NOT NULL,
  `enabled` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_cod` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_online` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `pay_logo` varchar(255) DEFAULT NULL,
  `is_discount` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：支付;1：折扣',
  `back_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '退货处理方式1:折分;2:返等价物;3:二选一',
  PRIMARY KEY (`pay_id`),
  UNIQUE KEY `pay_code` (`pay_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_pick_info`
--

DROP TABLE IF EXISTS `ty_pick_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_pick_info` (
  `pick_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '拣货单号',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '拣货类型 order ordercod change',
  `shipping_id` int(11) NOT NULL DEFAULT '0' COMMENT '配送方式',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '总单数',
  `over_num` int(11) NOT NULL DEFAULT '0' COMMENT '已完成单数',
  `create_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人id int11改成int10 20130218修改',
  `pick_admin` int(10) DEFAULT NULL COMMENT '拣货人,20130218添加',
  `pick_date` datetime DEFAULT NULL COMMENT '拣货开始时间,20130218添加',
  `pick_status` int(1) DEFAULT '0' COMMENT '拣货状态 0-未拣货;1-拣货中; 2-已拣;20130218添加',
  `qc_admin` int(10) DEFAULT NULL COMMENT '复核人,20130218添加',
  `qc_date` datetime DEFAULT NULL COMMENT '复核开始时间,20130218添加',
  `pick_type` int(1) NOT NULL DEFAULT '0' COMMENT '拣货方式， 0-扫描拣货，1-手工拣货 20130218增加',
  `is_print` int(1) NOT NULL DEFAULT '0' COMMENT '是否打印 0-未打印 1-已打印 20130218添加',
  PRIMARY KEY (`pick_sn`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_pick_sub`
--

DROP TABLE IF EXISTS `ty_pick_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_pick_sub` (
  `sub_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pick_sn` varchar(20) NOT NULL COMMENT '拣货单号',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品款号',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色id',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺寸id',
  `depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '仓库id',
  `batch_id` int(10) unsigned NOT NULL COMMENT '批次id',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位id',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  `rel_no` varchar(20) DEFAULT NULL COMMENT '关联单据,如订单号',
  `pick_cell` int(2) DEFAULT NULL COMMENT '订单格编号',
  `pick_num` int(10) NOT NULL DEFAULT '0' COMMENT '已拣数量',
  `pick_admin` int(10) NOT NULL DEFAULT '0' COMMENT '拣货人',
  `pick_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '拣货开始时间',
  `qc_num` int(10) NOT NULL DEFAULT '0' COMMENT '复核数量',
  `qc_admin` int(10) NOT NULL DEFAULT '0' COMMENT '复核人',
  `qc_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '复核开始时间',
  PRIMARY KEY (`sub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='拣货单子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_printer_info`
--

DROP TABLE IF EXISTS `ty_printer_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_printer_info` (
  `printer_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`printer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='打印管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_brand`
--

DROP TABLE IF EXISTS `ty_product_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_brand` (
  `brand_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '品牌编号',
  `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
  `brand_logo` varchar(100) NOT NULL COMMENT '原始图片',
  `brand_banner` varchar(100) NOT NULL COMMENT '广告位',
  `brand_video` varchar(100) NOT NULL COMMENT '视频',
  `brand_info` varchar(255) NOT NULL DEFAULT '' COMMENT '品牌简介',
  `brand_story` text NOT NULL COMMENT '品牌故事',
  `brand_initial` varchar(1) NOT NULL COMMENT '品牌首字母',
  `sort_order` int(3) unsigned NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用',
  `flag_id` int(10) NOT NULL DEFAULT '0' COMMENT '国旗',
  `logo_75_34` varchar(255) NOT NULL DEFAULT '' COMMENT '75×34',
  `logo_110_50` varchar(255) NOT NULL DEFAULT '' COMMENT '110×50',
  `logo_160_73` varchar(255) NOT NULL DEFAULT '' COMMENT '160×73',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `cat_content` text COMMENT '前台分类',
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `brand_name` (`brand_name`),
  KEY `is_show` (`is_use`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品品牌基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_card`
--

DROP TABLE IF EXISTS `ty_product_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_card` (
  `card_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `sub_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品skuID',
  `card_no` varchar(50) NOT NULL DEFAULT '' COMMENT '卡号',
  `card_pwd` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `op_id` int(11) NOT NULL DEFAULT '0' COMMENT '订单商品ID',
  `is_used` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已使用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `order_time` datetime DEFAULT NULL COMMENT '购买时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`card_id`),
  UNIQUE KEY `card_index` (`sub_id`,`card_no`,`card_pwd`) COMMENT '产品卡密唯一键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟卡表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_carelabel`
--

DROP TABLE IF EXISTS `ty_product_carelabel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_carelabel` (
  `carelabel_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '洗标编号',
  `carelabel_name` varchar(50) NOT NULL COMMENT '洗标名称',
  `carelabel_url` varchar(100) NOT NULL COMMENT '洗标图片地址',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`carelabel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品洗标基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_category`
--

DROP TABLE IF EXISTS `ty_product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_category` (
  `category_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `parent_id` int(10) NOT NULL DEFAULT '0' COMMENT '父分类编号',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品分类基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_color`
--

DROP TABLE IF EXISTS `ty_product_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_color` (
  `color_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '颜色编号',
  `color_sn` varchar(4) NOT NULL COMMENT '颜色号码，比如0062，用4位表示',
  `group_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色分组编号',
  `color_name` varchar(50) NOT NULL COMMENT '颜色名称',
  `color_img` varchar(100) NOT NULL COMMENT '颜色图片路径',
  `color_color` varchar(100) NOT NULL COMMENT '颜色码',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`color_id`),
  UNIQUE KEY `color_name` (`color_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1144 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品颜色基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_color_group`
--

DROP TABLE IF EXISTS `ty_product_color_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_color_group` (
  `group_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '颜色分组编号',
  `group_name` varchar(50) NOT NULL COMMENT '颜色分组名称',
  `group_img` varchar(100) NOT NULL COMMENT '颜色分组图片',
  `group_color` varchar(100) NOT NULL COMMENT '颜色分组颜色码',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品颜色分组基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_cooperation`
--

DROP TABLE IF EXISTS `ty_product_cooperation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_cooperation` (
  `cooperation_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '合作编号',
  `cooperation_name` varchar(50) NOT NULL COMMENT '合作名称',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`cooperation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品合作关系基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_cost`
--

DROP TABLE IF EXISTS `ty_product_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_cost` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int(10) unsigned NOT NULL COMMENT '商品编号',
  `batch_id` int(10) unsigned NOT NULL,
  `consign_price` decimal(10,2) DEFAULT NULL COMMENT '代销成本价',
  `cost_price` decimal(10,2) DEFAULT NULL COMMENT '买断成本价',
  `consign_rate` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '浮动代销率',
  `consign_type` int(1) unsigned DEFAULT '0' COMMENT '0为非代销1为固定代销价2为浮动代销率',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_admin` int(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `provider_id` int(10) NOT NULL DEFAULT '0' COMMENT '供应商id',
  `product_cess` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '税率 20130218添加',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2641 DEFAULT CHARSET=utf8 COMMENT='商品成本价格表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_cost_record`
--

DROP TABLE IF EXISTS `ty_product_cost_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_cost_record` (
  `price_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '调价编号',
  `batch_id` int(10) unsigned NOT NULL COMMENT '批次id',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品id',
  `cost_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '成本价',
  `consign_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '代销价',
  `consign_rate` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '浮动代销率',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`price_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品成本价记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_flag`
--

DROP TABLE IF EXISTS `ty_product_flag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_flag` (
  `flag_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '国旗编号',
  `flag_name` varchar(50) NOT NULL COMMENT '国旗名称',
  `flag_url` varchar(100) NOT NULL COMMENT '国旗图片地址',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，未使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`flag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品国旗基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_gallery`
--

DROP TABLE IF EXISTS `ty_product_gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_gallery` (
  `image_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '图片编号',
  `image_type` varchar(20) NOT NULL COMMENT 'default 默认,part 局部,tonal 色片',
  `product_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品编号',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色编号',
  `img_desc` varchar(255) NOT NULL COMMENT '图片描述',
  `sort_order` int(10) NOT NULL DEFAULT '0',
  `create_admin` int(10) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  `img_url` varchar(100) DEFAULT NULL,
  `img_318_318` varchar(255) DEFAULT NULL COMMENT 'rush页商品图,20130218添加',
  `img_418_418` varchar(255) DEFAULT NULL COMMENT '商品默认大图,20130218添加',
  `img_85_85` varchar(255) DEFAULT NULL COMMENT '商品缩略图,20130218添加',
  `img_760_760` varchar(255) DEFAULT NULL COMMENT '细节大图,20130218添加',
  `img_850_850` varchar(255) DEFAULT NULL COMMENT '放大镜全局图,20130218添加',
  `img_215_215` varchar(255) DEFAULT NULL COMMENT '个人中心首页热卖商品图,20130218添加',
  `img_58_58` varchar(255) DEFAULT NULL COMMENT '订单详情页查看订单详情,20130218添加',
  `img_48_48` varchar(255) DEFAULT NULL COMMENT '收藏列表 商品点评 商品咨询,20130218添加',
  `img_40_40` varchar(255) DEFAULT NULL COMMENT '后台使用,20130218添加',
  `img_170_170` varchar(255) DEFAULT NULL COMMENT '用于购买过,20130218添加',
  `img_140_140` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `idx_image_type` (`image_type`),
  KEY `idx_product_color` (`product_id`,`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26515 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品图片管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_gallery_imp_list`
--

DROP TABLE IF EXISTS `ty_product_gallery_imp_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_gallery_imp_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imp_catgory` int(1) DEFAULT NULL COMMENT '导入类型 1-图片导入',
  `product_id_list` text COMMENT '商品id集合，用逗号隔开',
  `status` char(2) DEFAULT NULL COMMENT '导入状态 02-执行中，03-执行失败，06-执行成功 ',
  `create_admin` int(10) DEFAULT NULL COMMENT '导入人',
  `create_date` datetime DEFAULT NULL COMMENT '导入时间',
  `log_file` varchar(50) DEFAULT NULL COMMENT '操作日志文件',
  `result_file` varchar(50) DEFAULT NULL COMMENT '结果报告文件',
  `batch_no` varchar(50) DEFAULT NULL COMMENT '批次',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片导入记录表2 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_gallery_imp_record`
--

DROP TABLE IF EXISTS `ty_product_gallery_imp_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_gallery_imp_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品id',
  `product_sn` varchar(60) DEFAULT NULL COMMENT '商品号码',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色id',
  `color_sn` varchar(4) DEFAULT NULL COMMENT '颜色号码',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8 COMMENT='商品图片导入记录表 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_imp_list`
--

DROP TABLE IF EXISTS `ty_product_imp_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_imp_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `product_id_list` text COMMENT '商品id集合，用逗号隔开',
  `status` char(2) DEFAULT NULL COMMENT '导入状态 02-执行中，03-执行失败，06-执行成功 ',
  `create_admin` int(10) DEFAULT NULL COMMENT '导入人',
  `create_date` datetime DEFAULT NULL COMMENT '导入时间',
  `confirm_admin` int(10) DEFAULT NULL COMMENT '审核人',
  `confirm_date` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品历次导入记录表 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_info`
--

DROP TABLE IF EXISTS `ty_product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_info` (
  `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `category_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分类编号',
  `product_sn` varchar(60) NOT NULL COMMENT '商品号码',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称',
  `brand_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属品牌',
  `product_weight` decimal(10,3) unsigned NOT NULL DEFAULT '1.000' COMMENT '商品重量',
  `market_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '市场价格',
  `shop_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '本网站价格',
  `promote_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '促销价格',
  `promote_start_date` datetime DEFAULT NULL COMMENT '促销开始时间',
  `promote_end_date` datetime DEFAULT NULL COMMENT '促销结束时间',
  `keywords` varchar(255) NOT NULL COMMENT '关键字',
  `product_desc` text COMMENT '商品描述',
  `sort_order` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_best` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '精品',
  `is_new` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '新品',
  `is_hot` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '热销',
  `is_promote` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '促销',
  `is_offcode` int(1) NOT NULL DEFAULT '0' COMMENT '断码',
  `style_id` int(10) NOT NULL DEFAULT '0' COMMENT '风格id',
  `season_id` int(10) NOT NULL DEFAULT '0' COMMENT '季节id',
  `provider_productcode` varchar(50) NOT NULL COMMENT '供应商货号',
  `product_year` varchar(4) NOT NULL COMMENT '年',
  `product_month` varchar(2) NOT NULL COMMENT '月',
  `product_sex` int(1) NOT NULL DEFAULT '0' COMMENT '1男2女',
  `unit_name` varchar(50) NOT NULL COMMENT '计量单位id,直接写中文单位',
  `goods_carelabel` varchar(255) NOT NULL COMMENT '洗标，多个洗标用逗号分隔',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `flag_id` int(10) NOT NULL DEFAULT '0' COMMENT '国旗编号',
  `model_id` int(10) NOT NULL DEFAULT '0' COMMENT '模特编号',
  `size_image_id` int(10) NOT NULL DEFAULT '0' COMMENT '对应尺寸详情图',
  `size_image` varchar(255) NOT NULL DEFAULT '' COMMENT '针对该商品的尺寸详情图',
  `is_gifts` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否赠品，0不是',
  `is_audit` int(1) NOT NULL DEFAULT '0' COMMENT '是否审核，0未审核',
  `audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '审核人',
  `audit_date` datetime DEFAULT NULL COMMENT '审核时间',
  `is_onsale` int(1) NOT NULL DEFAULT '0' COMMENT '是否上下架，0为上架',
  `is_stop` int(1) NOT NULL DEFAULT '0' COMMENT '是否停止订货，0未停止',
  `stop_admin` int(10) NOT NULL DEFAULT '0' COMMENT '停止订货人',
  `stop_date` datetime DEFAULT NULL COMMENT '停止订货时间',
  `min_month` int(11) NOT NULL DEFAULT '0' COMMENT '最小岁段(月)',
  `max_month` int(11) NOT NULL DEFAULT '0' COMMENT '最大岁段(月)',
  `provider_id` int(10) DEFAULT NULL COMMENT '供应商ID',
  `is_single_order` int(1) DEFAULT '0' COMMENT '本商品是否单独生成订单 0-不单独；1-单独.20130218添加',
  `is_cod` int(1) DEFAULT '1' COMMENT '本商品是否支持COD 0-不支持；1-支持.20130218添加',
  `related_id` int(10) DEFAULT '0' COMMENT '关联商品ID，默认为0。0的业务意义即本身 20130218添加',
  `product_desc_additional` text COMMENT '商品附加详细信息,有7个属性,json格式：desc_composition(成分)、desc_dimensions(尺寸规格)、desc_material(材质)、desc_waterproof(防水性)、desc_crowd(适合人群)、desc_notes(温馨提示)、desc_expected_shipping_date(预计发货日期)。20130218添加',
  `product_desc_detail` text COMMENT '用于商品细节展示 20130218添加',
  `update_time` datetime DEFAULT NULL,
  `size_table` text COMMENT '尺寸表内容',
  `limit_num` smallint(6) DEFAULT '0' COMMENT '限购数量',
  `limit_day` smallint(6) DEFAULT '0' COMMENT '限购天数',
  `scm_product_id` int(11) DEFAULT '0' COMMENT '供应商商品ID',
  `is_virtual` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否虚拟商品',
  `generate_method` tinyint(1) NOT NULL DEFAULT '0' COMMENT '虚拟卡生成方式 1系统生成 2手工导入',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `goods_sn` (`product_sn`),
  KEY `idx_min_month` (`min_month`),
  KEY `idx_max_month` (`max_month`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB AUTO_INCREMENT=2651 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品信息主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_link`
--

DROP TABLE IF EXISTS `ty_product_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_link` (
  `link_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '关联商品编号',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '原商品编号',
  `link_product_id` int(10) NOT NULL DEFAULT '0' COMMENT '关联商品编号',
  `is_bothway` int(1) NOT NULL DEFAULT '0' COMMENT '是否双向关联，默认为0，不双向关联',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`link_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品关联基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_liuyan`
--

DROP TABLE IF EXISTS `ty_product_liuyan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_liuyan` (
  `comment_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `tag_type` int(10) NOT NULL COMMENT '关联类型：1是商品，2是礼包等',
  `tag_id` int(10) DEFAULT NULL COMMENT '对应商品ID或者礼包ID',
  `comment_type` int(10) NOT NULL COMMENT '评论类型：1：咨询，2：评价',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户编号，0为匿名',
  `user_name` varchar(100) DEFAULT '' COMMENT '后台可写入评价的用户名',
  `comment_title` varchar(255) DEFAULT NULL,
  `comment_content` text NOT NULL COMMENT '评论内容',
  `comment_date` datetime NOT NULL COMMENT '评论时间',
  `comment_ip` varchar(30) NOT NULL COMMENT '用户IP',
  `is_audit` int(1) NOT NULL DEFAULT '0' COMMENT '是否审核，0未审核',
  `audit_admin_id` int(10) NOT NULL DEFAULT '0' COMMENT '审核人编号',
  `reply_admin_id` int(10) NOT NULL DEFAULT '0' COMMENT '回复人编号',
  `reply_content` text NOT NULL COMMENT '管理员回复内容',
  `reply_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '回复时间',
  `height` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '身高(cm) ',
  `weight` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '体重(kg) ',
  `size_id` int(11) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `suitable` int(3) NOT NULL DEFAULT '0' COMMENT '尺码感受：1,2,3偏小，正好，偏大',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，是为1否为0',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '后台创建人，前台创建则不填写',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品留言表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_liuyan_copy`
--

DROP TABLE IF EXISTS `ty_product_liuyan_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_liuyan_copy` (
  `comment_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `tag_type` int(10) NOT NULL COMMENT '关联类型：1是商品，2是礼包等',
  `tag_id` int(10) DEFAULT NULL COMMENT '对应商品ID或者礼包ID',
  `comment_type` int(10) NOT NULL COMMENT '评论类型：1：咨询，2：评价',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户编号，0为匿名',
  `user_name` varchar(100) DEFAULT '' COMMENT '后台可写入评价的用户名',
  `comment_title` varchar(255) DEFAULT NULL,
  `comment_content` text NOT NULL COMMENT '评论内容',
  `comment_date` datetime NOT NULL COMMENT '评论时间',
  `comment_ip` varchar(30) NOT NULL COMMENT '用户IP',
  `is_audit` int(1) NOT NULL DEFAULT '0' COMMENT '是否审核，0未审核',
  `audit_admin_id` int(10) NOT NULL DEFAULT '0' COMMENT '审核人编号',
  `reply_admin_id` int(10) NOT NULL DEFAULT '0' COMMENT '回复人编号',
  `reply_content` text NOT NULL COMMENT '管理员回复内容',
  `reply_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '回复时间',
  `height` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '身高(cm) ',
  `weight` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '体重(kg) ',
  `size_id` int(11) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `suitable` int(3) NOT NULL DEFAULT '0' COMMENT '尺码感受：1,2,3偏小，正好，偏大',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，是为1否为0',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '后台创建人，前台创建则不填写',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品留言表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_model`
--

DROP TABLE IF EXISTS `ty_product_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_model` (
  `model_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '模特编号',
  `model_name` varchar(50) NOT NULL COMMENT '模特名称',
  `model_image` varchar(100) NOT NULL COMMENT '模特图片地址',
  `create_admin` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品模特照片图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_onsale_record`
--

DROP TABLE IF EXISTS `ty_product_onsale_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_onsale_record` (
  `onsale_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '上下架记录编号',
  `sub_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品信息子表ID',
  `sr_onsale` int(1) NOT NULL COMMENT '操作 0.下架,1.上架',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `onsale_memo` varchar(200) DEFAULT NULL COMMENT '上下架备注，如在商品列表上下架，则“商品列表手工操作”,如在限抢手工添加移除，则“限抢手工上下架，rushID=xxx”,如随着限抢一起脚本执行上下架，则“限抢系统上下架，rushid=xxx”',
  PRIMARY KEY (`onsale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1739 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品上下架记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_price_record`
--

DROP TABLE IF EXISTS `ty_product_price_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_price_record` (
  `price_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '调价编号',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品id',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '本店价',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`price_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2543 DEFAULT CHARSET=utf8 COMMENT='商品调价记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_provider`
--

DROP TABLE IF EXISTS `ty_product_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_provider` (
  `provider_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '供应商编号',
  `provider_name` varchar(100) NOT NULL COMMENT '供应商名称',
  `official_name` varchar(100) NOT NULL COMMENT '供应商公司名称',
  `provider_bank` varchar(100) NOT NULL COMMENT '供应商开户银行',
  `provider_account` varchar(100) NOT NULL COMMENT '银行帐号',
  `tax_no` varchar(255) NOT NULL COMMENT '纳税号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `provider_code` varchar(20) NOT NULL DEFAULT '' COMMENT '供应商代码。20130218添加',
  `provider_cooperation` int(10) NOT NULL DEFAULT '2' COMMENT '供应商合作方式，关联ty_product_cooperation.cooperation_id。20130218添加',
  `logo` varchar(255) DEFAULT '' COMMENT 'LOGO图片',
  `display_name` varchar(128) DEFAULT '' COMMENT '前台显示名称',
  `return_address` varchar(255) DEFAULT '' COMMENT '退货地址',
  `return_postcode` varchar(32) DEFAULT '' COMMENT '退货邮编',
  `return_consignee` varchar(32) DEFAULT '' COMMENT '退货收货人',
  `return_mobile` varchar(16) DEFAULT '' COMMENT '退货收货人手机',
  `shipping_fee_config` text COMMENT '运费配置',
  `cat_content` text NOT NULL COMMENT '前台分类',
  `product_num` int(10) NOT NULL DEFAULT '0' COMMENT '正在销售的商品总数',
  `provider_cess` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '税率',
  `user_name` varchar(50) DEFAULT NULL COMMENT '登陆用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '登陆密码',
  `provider_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '登陆状态:0-正常，1-锁定',
  `official_address` varchar(255) DEFAULT NULL COMMENT '公司地址',
  `scm_responsible_user` varchar(20) DEFAULT NULL COMMENT '负责人',
  `scm_responsible_phone` varchar(20) DEFAULT NULL COMMENT '负责手机',
  `scm_responsible_qq` varchar(20) DEFAULT NULL COMMENT '负责QQ',
  `scm_responsible_mail` varchar(50) DEFAULT NULL COMMENT '负责email',
  `scm_order_process_user` varchar(20) DEFAULT NULL COMMENT '订单处理联系人',
  `scm_order_process_phone` varchar(20) DEFAULT NULL COMMENT '订单处理人电话',
  `scm_order_process_qq` varchar(20) DEFAULT NULL COMMENT '订单处理人QQ',
  `scm_order_process_mail` varchar(50) DEFAULT NULL COMMENT '订单处理人email',
  `provider_ad` text COMMENT '自定义店铺',
  `provider_ad_sdate` datetime DEFAULT NULL COMMENT '自定义店铺开始时间',
  `provider_ad_edate` datetime DEFAULT NULL COMMENT '自定义店铺结束时间',
  `account_balance` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '账户余额',
  `sms_price` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '短信发送单价',
  PRIMARY KEY (`provider_id`),
  UNIQUE KEY `provider_code` (`provider_code`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品供应商基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_season`
--

DROP TABLE IF EXISTS `ty_product_season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_season` (
  `season_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '季节编号',
  `season_name` varchar(50) NOT NULL COMMENT '季节名称',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`season_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品季节基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_size`
--

DROP TABLE IF EXISTS `ty_product_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_size` (
  `size_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '尺寸编码',
  `size_sn` varchar(4) NOT NULL COMMENT '尺寸号码，比如0032，用4位表示',
  `size_name` varchar(50) NOT NULL COMMENT '尺寸名称',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`size_id`),
  UNIQUE KEY `size_name` (`size_name`)
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品尺寸基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_size_image`
--

DROP TABLE IF EXISTS `ty_product_size_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_size_image` (
  `size_image_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '商品尺寸详情图编号',
  `brand_id` int(10) NOT NULL DEFAULT '0' COMMENT '所属品牌编号',
  `category_id` int(10) NOT NULL DEFAULT '0' COMMENT '所属分类编号',
  `sex` int(1) NOT NULL COMMENT '1男2女',
  `image_url` varchar(100) NOT NULL COMMENT '尺寸详情图地址',
  `size_table` text COMMENT '尺寸表内容',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`size_image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品尺寸详情图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_style`
--

DROP TABLE IF EXISTS `ty_product_style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_style` (
  `style_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '风格编号',
  `style_name` varchar(50) NOT NULL COMMENT '风格名称',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`style_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品风格基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_sub`
--

DROP TABLE IF EXISTS `ty_product_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_sub` (
  `sub_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `product_id` int(10) NOT NULL COMMENT '商品ID',
  `color_id` int(10) NOT NULL COMMENT '颜色ID',
  `size_id` int(10) NOT NULL COMMENT '尺寸ID',
  `gl_num` int(10) NOT NULL DEFAULT '0' COMMENT '实际库存',
  `is_on_sale` int(1) NOT NULL DEFAULT '0' COMMENT '0为下架,1为上架',
  `consign_num` int(10) NOT NULL DEFAULT '-1' COMMENT '代销库存:-2--不限量代销;-1:不代销;>=0限量代销',
  `wait_num` int(11) NOT NULL DEFAULT '0' COMMENT '被占用的代销库存',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `provider_barcode` varchar(60) NOT NULL COMMENT '供应商条码 修改为非空 20130218修改',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `is_pic` int(1) NOT NULL DEFAULT '0' COMMENT '是否拍摄 0-未拍摄；1-已拍摄 20130218添加',
  `lock_num` int(10) NOT NULL DEFAULT '0' COMMENT '锁定库存数量，动态变化 20130218添加',
  `lock_num_mark` int(10) NOT NULL DEFAULT '0' COMMENT '锁定库存数量 仅作标记 20130218添加',
  `scm_provider_barcode` varchar(64) DEFAULT NULL COMMENT '供应商条码(第三方平台)',
  PRIMARY KEY (`sub_id`),
  UNIQUE KEY `unique_good_color_size` (`product_id`,`color_id`,`size_id`),
  KEY `color_id` (`product_id`,`color_id`),
  KEY `provider_barcode` (`provider_barcode`),
  KEY `idx_is_on_sale` (`is_on_sale`)
) ENGINE=InnoDB AUTO_INCREMENT=16489 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品信息子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_type`
--

DROP TABLE IF EXISTS `ty_product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_type` (
  `type_id` int(10) NOT NULL AUTO_INCREMENT,
  `type_code` varchar(10) NOT NULL COMMENT '分类代号',
  `type_name` varchar(20) DEFAULT NULL,
  `parent_id` int(10) DEFAULT '0' COMMENT '一级分类ID',
  `parent_id2` int(10) DEFAULT '0' COMMENT '二级分类ID',
  `is_show_cat` int(1) DEFAULT '1' COMMENT '是否前台显示 0-不显示 1-显示',
  `sort_order` int(4) DEFAULT '0',
  `cat_content` text NOT NULL COMMENT '前台分类',
  `category_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应后台分类',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_admin` int(10) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_code` (`type_code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_parent_id2` (`parent_id2`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='前台分类表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_type_link`
--

DROP TABLE IF EXISTS `ty_product_type_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_type_link` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `product_id` int(10) NOT NULL,
  `type_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_type_id` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2706 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品前台分类关系表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_provider_account_log`
--

DROP TABLE IF EXISTS `ty_provider_account_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_provider_account_log` (
  `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `provider_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `change_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '变动类型:0-充值;1-提现;2-短信消费;',
  `change_price` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '变动金额:充值-正数;消费-负数;',
  `related_id` int(10) unsigned DEFAULT NULL COMMENT '关联ID:如短信发送任务ID',
  `operate_aid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '操作人ID:-1-系统;',
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `operate_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0-初始;1-成功;',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='供应商账户变动记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_provider_brand`
--

DROP TABLE IF EXISTS `ty_provider_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_provider_brand` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `provider_id` int(10) NOT NULL COMMENT '供应商ID',
  `brand_id` int(10) NOT NULL COMMENT '品牌ID',
  `is_used` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用 0-未启用 1-启用',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_admin` int(10) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `commission` varchar(20) DEFAULT NULL COMMENT '扣点',
  `commission_history` text COMMENT '扣点历史：json格式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='供应商品牌关系表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_provider_fee`
--

DROP TABLE IF EXISTS `ty_provider_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_provider_fee` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `provider_id` int(10) NOT NULL COMMENT '供应商ID',
  `batch_id` int(10) DEFAULT NULL COMMENT '批次号ID',
  `brand_id` int(10) DEFAULT NULL,
  `category_id` int(10) DEFAULT NULL COMMENT '费用名目id',
  `detail_price` double(10,2) DEFAULT NULL COMMENT '金额',
  `remark` text COMMENT '备注',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `check_admin` int(10) DEFAULT NULL,
  `check_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商费用明细表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_provider_fee_category`
--

DROP TABLE IF EXISTS `ty_provider_fee_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_provider_fee_category` (
  `category_id` int(10) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) DEFAULT NULL,
  `is_use` int(1) NOT NULL DEFAULT '1' COMMENT '是否使用 0-未使用 1-已使用',
  `create_admin` int(10) NOT NULL DEFAULT '-1',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商费用名目表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_batch`
--

DROP TABLE IF EXISTS `ty_purchase_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_batch` (
  `batch_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `batch_code` varchar(30) DEFAULT NULL COMMENT '批次号',
  `provider_id` int(10) DEFAULT NULL COMMENT '供应商id',
  `batch_status` int(1) DEFAULT '1' COMMENT '批次状态 0-关闭, 1- 打开；',
  `batch_type` int(1) DEFAULT NULL COMMENT '批次类型 0-采购单；1-代转买批次；2-盘赢；3-其他',
  `plan_num` int(10) DEFAULT NULL COMMENT '预计数量',
  `batch_name` varchar(50) DEFAULT NULL COMMENT '批次名称',
  `plan_arrive_date` varchar(10) DEFAULT NULL COMMENT '到货日期',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_admin` int(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `related_id` int(10) DEFAULT '0' COMMENT '关联批次号，batch_id',
  `close_admin` int(10) DEFAULT NULL COMMENT '批次关闭id',
  `close_date` datetime DEFAULT NULL COMMENT '批次关闭时间',
  `lock_admin` int(10) DEFAULT NULL COMMENT '锁定人',
  `lock_date` datetime DEFAULT NULL COMMENT '锁定时间',
  `is_reckoned` int(1) NOT NULL DEFAULT '0' COMMENT '批次是否结算，0-未；1-已',
  `reckon_admin` int(10) DEFAULT NULL COMMENT '设置已结算id',
  `reckon_date` datetime DEFAULT NULL COMMENT '设置已结算时间',
  `is_consign` int(1) DEFAULT '0' COMMENT '是否代销采购 0-大货先到； 1-代销采购',
  `brand_id` int(10) NOT NULL DEFAULT '0' COMMENT '品牌ID',
  PRIMARY KEY (`batch_id`),
  UNIQUE KEY `batch_code` (`batch_code`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='采购单批次信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_box_main`
--

DROP TABLE IF EXISTS `ty_purchase_box_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_box_main` (
  `box_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_code` varchar(30) DEFAULT NULL COMMENT '箱号,唯一',
  `purchase_code` varchar(30) DEFAULT NULL COMMENT '采购单号',
  `product_number` int(10) DEFAULT NULL COMMENT '收货总件数',
  `product_shelve_num` int(10) DEFAULT NULL COMMENT '上架总件数',
  `scan_id` int(10) DEFAULT NULL COMMENT '收货人',
  `scan_start_time` datetime DEFAULT NULL COMMENT '收货开始时间',
  `scan_end_time` datetime DEFAULT NULL COMMENT '收货结束时间',
  `shelve_id` int(10) DEFAULT NULL COMMENT '上架人',
  `shelve_starttime` datetime DEFAULT NULL COMMENT '上架人开始时间',
  `shelve_endtime` datetime DEFAULT NULL COMMENT '上架人结束时间',
  PRIMARY KEY (`box_id`),
  UNIQUE KEY `box_code` (`box_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货箱号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_box_sub`
--

DROP TABLE IF EXISTS `ty_purchase_box_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_box_sub` (
  `box_sub_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_id` int(10) DEFAULT NULL,
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `provider_barcode` varchar(60) DEFAULT NULL COMMENT '条形码',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '入库数量',
  `scan_id` int(10) DEFAULT NULL COMMENT '收货人',
  `scan_starttime` datetime DEFAULT NULL COMMENT '收货开始时间',
  `scan_endtime` datetime DEFAULT NULL COMMENT '收货结束时间',
  `over_num` int(10) DEFAULT NULL COMMENT '上架数量',
  `shelve_id` int(10) DEFAULT NULL COMMENT '上架人',
  `shelve_starttime` datetime DEFAULT NULL COMMENT '上架人开始时间',
  `shelve_endtime` datetime DEFAULT NULL COMMENT '上架人结束时间',
  PRIMARY KEY (`box_sub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货箱号子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_consign`
--

DROP TABLE IF EXISTS `ty_purchase_consign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_consign` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `provider_id` int(10) NOT NULL COMMENT '供应商编码',
  `start_time` datetime DEFAULT NULL COMMENT '订单创建开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '订单创建结束时间',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `purchase_code` varchar(32) NOT NULL DEFAULT '' COMMENT '采购单号',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID',
  `brand_id` int(10) NOT NULL DEFAULT '0' COMMENT '品牌ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟代销表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_consign_detail`
--

DROP TABLE IF EXISTS `ty_purchase_consign_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_consign_detail` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `purchase_code` varchar(32) NOT NULL COMMENT '采购单号',
  `provider_id` int(10) DEFAULT NULL COMMENT '供应商编码',
  `brand_id` int(10) DEFAULT NULL COMMENT '品牌ID',
  `batch_id` int(10) DEFAULT NULL COMMENT '批次ID',
  `order_id` int(10) NOT NULL COMMENT '订单ID',
  `op_id` int(10) NOT NULL COMMENT '订单子表ID',
  `confirm_date` datetime DEFAULT NULL COMMENT '订单客服审核时间',
  `product_id` int(10) NOT NULL COMMENT '商品款式ID',
  `color_id` int(10) NOT NULL COMMENT '颜色ID',
  `size_id` int(10) NOT NULL COMMENT '尺码ID',
  `consign_num` int(10) NOT NULL DEFAULT '0' COMMENT '代销库存数量',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0-拉取时状态',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代销采购明细表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_log`
--

DROP TABLE IF EXISTS `ty_purchase_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `related_id` int(10) NOT NULL COMMENT '关联ID，如箱子ID',
  `related_type` int(2) NOT NULL COMMENT '关联类型 0-修改收货箱商品数量；1-取消收货箱',
  `desc_content` text NOT NULL COMMENT '具体内容',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购入库相关日志表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_main`
--

DROP TABLE IF EXISTS `ty_purchase_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_main` (
  `purchase_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_code` varchar(30) NOT NULL COMMENT '采购单编号',
  `purchase_from` int(10) NOT NULL DEFAULT '0' COMMENT '原采购单id',
  `purchase_order_date` datetime NOT NULL COMMENT '采购发起时间',
  `purchase_provider` int(10) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `discount` decimal(10,2) NOT NULL DEFAULT '1.00' COMMENT '折扣',
  `purchase_type` int(10) NOT NULL DEFAULT '0' COMMENT '采购类型',
  `purchase_delivery` datetime NOT NULL COMMENT '采购预期交货时间',
  `purchase_brand` int(10) NOT NULL DEFAULT '0' COMMENT '采购品牌',
  `purchase_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '采购单总金额',
  `purchase_number` int(10) NOT NULL DEFAULT '0' COMMENT '采购总数',
  `purchase_finished_number` int(10) NOT NULL DEFAULT '0' COMMENT '实际收货数量,20130218修改',
  `purchase_check_admin` int(10) NOT NULL DEFAULT '0' COMMENT '商品部审核人',
  `purchase_check_date` datetime NOT NULL COMMENT '商品部审核时间',
  `purchase_finished` int(1) NOT NULL DEFAULT '0' COMMENT '采购单未完成/完成',
  `purchase_break` int(1) NOT NULL DEFAULT '0' COMMENT '采购单中常/被中止',
  `purchase_break_admin` int(10) NOT NULL DEFAULT '0' COMMENT '中止人',
  `purchase_break_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '中止时间',
  `purchase_remark` varchar(255) NOT NULL COMMENT '备注',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `lock_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '锁定时间',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  `purchase_shelved_number` int(10) NOT NULL DEFAULT '0' COMMENT '实际上架数量,20130218添加',
  PRIMARY KEY (`purchase_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品采购一级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_sub`
--

DROP TABLE IF EXISTS `ty_purchase_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_sub` (
  `purchase_sub_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_id` int(10) NOT NULL DEFAULT '0' COMMENT '主采购单ID',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品款式ID',
  `product_name` varchar(120) NOT NULL COMMENT '商品名字',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺码ID',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  `product_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总价',
  `product_finished_number` int(10) NOT NULL DEFAULT '0' COMMENT '完工数',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`purchase_sub_id`),
  UNIQUE KEY `purchase_id` (`purchase_id`,`product_id`,`color_id`,`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品采购二级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_purchase_type`
--

DROP TABLE IF EXISTS `ty_purchase_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_purchase_type` (
  `purchase_type_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_type_name` varchar(50) NOT NULL COMMENT '采购类型名称',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否启用，默认为0，不启用',
  `create_admin` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`purchase_type_id`),
  UNIQUE KEY `purchase_type_code` (`purchase_type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='采购单类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_region_info`
--

DROP TABLE IF EXISTS `ty_region_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_region_info` (
  `region_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '地区编号',
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父编号',
  `region_name` varchar(120) NOT NULL COMMENT '地区名称',
  `region_type` int(10) NOT NULL DEFAULT '0' COMMENT '地区级别，比如省为1，下级为2',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3726 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='地区管理基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_region_ip`
--

DROP TABLE IF EXISTS `ty_region_ip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_region_ip` (
  `ip_start` varchar(15) NOT NULL DEFAULT '' COMMENT 'IP段起始',
  `ip_end` varchar(15) NOT NULL DEFAULT '' COMMENT 'IP段结束',
  `long_start` int(11) NOT NULL DEFAULT '0' COMMENT 'Long起始',
  `long_end` int(11) NOT NULL DEFAULT '0' COMMENT 'Long结束',
  `region_name` varchar(10) NOT NULL DEFAULT '' COMMENT '地区名称',
  `region_id` int(11) NOT NULL DEFAULT '0' COMMENT '地区ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='地域IP匹配表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_region_shipping_fee`
--

DROP TABLE IF EXISTS `ty_region_shipping_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_region_shipping_fee` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `province_id` int(10) NOT NULL COMMENT '地区关联省份ID',
  `online_shipping_fee` decimal(10,2) DEFAULT NULL COMMENT '在线支付运费',
  `cod_shipping_fee` decimal(10,2) DEFAULT NULL COMMENT '货到付款运费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_rush_info`
--

DROP TABLE IF EXISTS `ty_rush_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_rush_info` (
  `rush_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '限时抢购编号',
  `nav_id` int(10) NOT NULL COMMENT '对应导航ID',
  `cat_content` text NOT NULL COMMENT '前台分类',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0未激活,1已激活,2停止,3结束',
  `desc` text NOT NULL COMMENT '限时抢购描述',
  `image_before_url` varchar(255) NOT NULL COMMENT '列表页banner未开始',
  `image_ing_url` varchar(255) NOT NULL COMMENT '列表页banner进行中',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '激活人',
  `audit_date` datetime NOT NULL COMMENT '激活日期',
  `modify_admin` int(10) NOT NULL DEFAULT '0' COMMENT '修改人',
  `modify_date` datetime NOT NULL COMMENT '修改时间',
  `stop_admin` int(10) NOT NULL DEFAULT '0' COMMENT '强行停止人',
  `stop_date` datetime NOT NULL COMMENT '强行停止日期',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `jump_url` varchar(100) NOT NULL COMMENT '自定义跳转页面地址',
  `rush_tag` varchar(15) DEFAULT NULL COMMENT '好评100% 等显示',
  `rush_index` varchar(20) DEFAULT NULL COMMENT '限抢索引',
  `rush_brand` varchar(20) DEFAULT NULL COMMENT '限抢品牌',
  `rush_category` varchar(20) DEFAULT NULL COMMENT '限抢分类',
  `rush_prompt` varchar(45) DEFAULT NULL COMMENT '限抢提\r\n示',
  `rush_discount` varchar(10) DEFAULT NULL COMMENT '限抢折扣',
  PRIMARY KEY (`rush_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品限时抢购表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_rush_mobile`
--

DROP TABLE IF EXISTS `ty_rush_mobile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_rush_mobile` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户ID，0代表未注册用户',
  `area` varchar(20) NOT NULL DEFAULT '' COMMENT '地区',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `send_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发送日期',
  PRIMARY KEY (`rec_id`),
  UNIQUE KEY `IDX_MOBILE` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='限时抢购用户手机记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_rush_notice`
--

DROP TABLE IF EXISTS `ty_rush_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_rush_notice` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `rush_id` int(10) NOT NULL COMMENT '限时抢购编号',
  `account` varchar(100) NOT NULL COMMENT '帐号',
  `type` int(1) NOT NULL COMMENT '1手机2Email',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='抢购通知（email或者手机）记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_rush_notice_template`
--

DROP TABLE IF EXISTS `ty_rush_notice_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_rush_notice_template` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `rush_id` varchar(255) NOT NULL DEFAULT '0' COMMENT '限时抢购编号，多个以逗号分开',
  `mobile_template` varchar(255) NOT NULL COMMENT '短信模板',
  `email_subject` varchar(255) NOT NULL DEFAULT '' COMMENT '邮件标题',
  `email_template` text NOT NULL COMMENT 'EMAIL模板',
  `start_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发送起始时间',
  `end_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发送结束时间',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建日期',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='限时抢购通知模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_rush_product`
--

DROP TABLE IF EXISTS `ty_rush_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_rush_product` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `rush_id` int(10) NOT NULL DEFAULT '0' COMMENT '限时抢购编号',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品编号',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '限时抢购价格',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `category_id` int(10) NOT NULL DEFAULT '0' COMMENT '分类编号',
  `image_before_url` varchar(255) NOT NULL DEFAULT '' COMMENT '列表页banner未开始',
  `image_ing_url` varchar(255) NOT NULL DEFAULT '' COMMENT '列表页banner进行中',
  `desc` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='限时抢购商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_bcs_imp`
--

DROP TABLE IF EXISTS `ty_scm_bcs_imp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_bcs_imp` (
  `imp_id` int(11) NOT NULL AUTO_INCREMENT,
  `brand_id` int(11) NOT NULL COMMENT '品牌ID',
  `cat_id` int(11) DEFAULT NULL COMMENT '分类ID',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别(m-男,w-女,a-中性)',
  `imp_status` int(1) NOT NULL DEFAULT '0' COMMENT '导入状态(0-失败,1-成功)',
  `image_url` varchar(50) DEFAULT NULL COMMENT '图片路径',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`imp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台品牌尺寸图导入';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_notice`
--

DROP TABLE IF EXISTS `ty_scm_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(50) DEFAULT NULL COMMENT '公告标题',
  `content` varchar(1000) DEFAULT NULL COMMENT '公告内容',
  `audit_user` int(11) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '删除标识',
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='第三方平台公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_product_batch_import`
--

DROP TABLE IF EXISTS `ty_scm_product_batch_import`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_product_batch_import` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `imp_batch_no` varchar(30) DEFAULT NULL COMMENT '导入批次号',
  `imp_goods_ids` text COMMENT '导入的商品ids',
  `maindata_filename` varchar(50) DEFAULT NULL COMMENT '主要数据导入文件名',
  `is_impmain` char(1) DEFAULT NULL COMMENT '是否成功导入主要信息',
  `is_impcolorsize` char(1) DEFAULT NULL COMMENT '是否成功导入颜色尺寸',
  `is_audit` char(1) DEFAULT NULL COMMENT '是否已经审核0-未，1-已',
  `audit_id` int(8) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `is_imppurchase` char(1) DEFAULT NULL COMMENT '是否成功导入采购单',
  `is_impsecinfo` char(1) DEFAULT NULL COMMENT '是否成功导入次要信息',
  `is_impbcsimg` char(1) DEFAULT NULL COMMENT '是否成功导入尺寸对照图',
  `is_imppic` char(1) DEFAULT NULL COMMENT '是否成功导入图片',
  `crtuser` int(8) DEFAULT NULL COMMENT '主要数据导入人',
  `crttime` datetime DEFAULT NULL COMMENT '主要数据导入时间',
  `uptuser` int(8) DEFAULT NULL,
  `upttime` datetime DEFAULT NULL,
  `upttype` char(2) DEFAULT NULL,
  `provider_id` int(11) DEFAULT NULL COMMENT '供应商ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='商品批量导入列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_product_gallery`
--

DROP TABLE IF EXISTS `ty_scm_product_gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_product_gallery` (
  `img_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `color_id` mediumint(8) NOT NULL COMMENT '颜色id',
  `img_url` varchar(255) NOT NULL COMMENT '原图相同-用来使用的(可能加水印)',
  `img_desc` varchar(255) NOT NULL DEFAULT '',
  `thumb_url` varchar(255) NOT NULL COMMENT '缩略图(72*96)',
  `middle_url` varchar(255) NOT NULL COMMENT '600*800 图',
  `big_url` varchar(255) NOT NULL COMMENT '1200*1600 图',
  `teeny_url` varchar(255) NOT NULL COMMENT '30*40 图',
  `small_url` varchar(255) NOT NULL COMMENT '180*240 图',
  `img_original` varchar(255) NOT NULL COMMENT '原始图片',
  `img_default` varchar(10) NOT NULL DEFAULT 'part' COMMENT 'default 默认,part 局部,tonal 色片',
  `img_aid` smallint(5) NOT NULL,
  `img_time` datetime NOT NULL,
  `url_120_160` varchar(255) NOT NULL COMMENT '102*160',
  `url_99_132` varchar(255) NOT NULL COMMENT '99*132',
  `url_480_640` varchar(255) NOT NULL COMMENT '480*640',
  `url_56_84` varchar(255) NOT NULL COMMENT '63*84',
  `url_222_296` varchar(255) NOT NULL,
  `sort_order` int(11) NOT NULL DEFAULT '0',
  `url_342_455` varchar(255) DEFAULT NULL COMMENT '342*455',
  `url_170_227` varchar(255) DEFAULT NULL COMMENT '170*227',
  `url_135_180` varchar(255) DEFAULT NULL COMMENT 'iphone3终端商品列表图',
  `url_251_323` varchar(255) DEFAULT NULL COMMENT 'iphone3终端商品详情大图',
  `url_502_646` varchar(255) DEFAULT NULL COMMENT 'ipad终端商品详情大图',
  `url_1200_1600` varchar(255) DEFAULT NULL COMMENT '1200*1600 详情放大镜',
  PRIMARY KEY (`img_id`),
  KEY `goods_id` (`goods_id`),
  KEY `goods_id_color_id` (`goods_id`,`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=812 DEFAULT CHARSET=utf8 COMMENT='第三方平台商品图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_product_import_list`
--

DROP TABLE IF EXISTS `ty_scm_product_import_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_product_import_list` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `imp_batch_no` varchar(30) DEFAULT NULL COMMENT '导入批次号',
  `filename` varchar(50) DEFAULT NULL COMMENT '导入文件名',
  `imp_type` char(2) DEFAULT NULL COMMENT '01主要数据;02颜色尺寸;03统一审核;04次要信息;05采购单;06:图片;07:尺寸对照图08:商品虚库;',
  `imp_time` datetime DEFAULT NULL COMMENT '导入时间',
  `status` char(2) DEFAULT NULL COMMENT '状态  02-执行中，03-执行失败，06-执行成功',
  `imp_id` int(8) DEFAULT NULL COMMENT '导入人',
  `log_file` varchar(50) DEFAULT NULL COMMENT '操作日志文件',
  `result_file` varchar(50) DEFAULT NULL COMMENT '结果报告文件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 COMMENT='商品批量导入经过列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_product_info`
--

DROP TABLE IF EXISTS `ty_scm_product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_product_info` (
  `goods_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `cat_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `pagecat_id` int(11) NOT NULL DEFAULT '0' COMMENT '前台分类',
  `goods_sn` varchar(60) NOT NULL COMMENT '商品编码',
  `goods_name` varchar(120) NOT NULL DEFAULT '',
  `goods_name_style` varchar(60) NOT NULL DEFAULT '+',
  `click_count` int(10) unsigned NOT NULL DEFAULT '0',
  `brand_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `provider_name` varchar(100) NOT NULL DEFAULT '',
  `goods_number` smallint(5) unsigned NOT NULL DEFAULT '0',
  `goods_weight` decimal(10,3) unsigned NOT NULL DEFAULT '1.000',
  `market_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `shop_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `promote_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `consign_price` decimal(10,2) NOT NULL COMMENT '代销价',
  `cost_price` decimal(10,2) NOT NULL COMMENT '成本价',
  `consign_rate` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '浮动代销率',
  `consign_type` smallint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0为非代销1为固定代销价2为浮动代销率',
  `promote_start_date` int(11) unsigned NOT NULL DEFAULT '0',
  `promote_end_date` int(11) unsigned NOT NULL DEFAULT '0',
  `warn_number` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `keywords` varchar(255) NOT NULL DEFAULT '',
  `goods_brief` varchar(255) NOT NULL COMMENT '商品备注',
  `goods_desc` text NOT NULL COMMENT 'f-club点评',
  `goods_thumb` varchar(255) NOT NULL DEFAULT '',
  `goods_img` varchar(255) NOT NULL DEFAULT '',
  `original_img` varchar(255) NOT NULL DEFAULT '',
  `is_real` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `extension_code` varchar(30) NOT NULL DEFAULT '',
  `is_on_sale` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0为下架,1为上架',
  `is_alone_sale` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `integral` int(10) unsigned NOT NULL DEFAULT '0',
  `sort_order` smallint(4) unsigned NOT NULL DEFAULT '0',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '回收站 1',
  `is_best` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_new` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_hot` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_promote` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_offcode` smallint(6) NOT NULL DEFAULT '0' COMMENT '断码',
  `is_empty` smallint(6) NOT NULL DEFAULT '0',
  `bonus_type_id` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `last_update` int(10) unsigned NOT NULL DEFAULT '0',
  `goods_type` smallint(5) unsigned NOT NULL DEFAULT '0',
  `seller_note` varchar(255) NOT NULL DEFAULT '',
  `give_integral` int(11) NOT NULL DEFAULT '-1',
  `rank_integral` int(11) NOT NULL DEFAULT '-1',
  `style_id` int(11) NOT NULL COMMENT '风格id',
  `season_id` int(11) NOT NULL COMMENT '季节id',
  `provider_id` int(11) NOT NULL COMMENT '供应商id',
  `coop_id` int(11) NOT NULL COMMENT '合作方式id',
  `goods_stop` smallint(1) NOT NULL DEFAULT '0' COMMENT '1为启用,0为停止订货',
  `provider_goods` varchar(20) NOT NULL COMMENT '供应商货号',
  `goods_year` varchar(4) NOT NULL COMMENT '年',
  `goods_month` varchar(2) NOT NULL COMMENT '月',
  `goods_sex` varchar(2) NOT NULL DEFAULT 'a' COMMENT '性别(m-男,w-女,a-全部)',
  `unit_id` int(11) NOT NULL COMMENT '计量单位id',
  `unit_name` varchar(64) NOT NULL DEFAULT '' COMMENT '计量单位名称',
  `goods_audit` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否审核(0 为未审核,1.为审核通过)',
  `goods_stuff` text NOT NULL COMMENT '面料',
  `goods_material` text NOT NULL COMMENT '保养',
  `goods_material_new` varchar(255) NOT NULL,
  `goods_audit_aid` smallint(5) NOT NULL DEFAULT '0' COMMENT '审核管理员id',
  `goods_audit_time` datetime NOT NULL COMMENT '审核时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `goods_aid` smallint(5) NOT NULL,
  `goods_time` datetime NOT NULL,
  `area_id` smallint(6) NOT NULL DEFAULT '0' COMMENT '国旗',
  `model_id` int(11) NOT NULL DEFAULT '0',
  `goods_cess` decimal(10,2) NOT NULL COMMENT '税率',
  `sc_id` int(11) NOT NULL,
  `sc_desc` text NOT NULL,
  `sc_image_content` text COMMENT '尺寸图JSON内容',
  `goods_modelimg` varchar(255) NOT NULL COMMENT '商品模特图',
  `is_rush` smallint(1) DEFAULT '0',
  `is_gifts` tinyint(4) NOT NULL DEFAULT '0' COMMENT '赠品',
  `goods_desc2` text,
  `goods_desc_img` text,
  `record_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'wms同步状态-1为不同步，0为未同步，1为已同步',
  `goods_desc_additional` text COMMENT '成分、尺寸规格、材质、防水性、适合人群、温馨提示信息，格式为JSON',
  `limit_num` smallint(6) DEFAULT '0' COMMENT '限购数量',
  `limit_day` smallint(6) DEFAULT '0' COMMENT '限购天数',
  `diagram_code` varchar(128) DEFAULT NULL COMMENT '尺寸示意图编码',
  `tpd_goods_status` varchar(1) DEFAULT NULL COMMENT '商品状态(0-草稿,1-待审核,2-已审核)',
  `is_virtual` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否虚拟商品',
  `generate_method` tinyint(4) NOT NULL DEFAULT '0' COMMENT '虚拟卡生成方式 1系统生成 2手工导入',
  PRIMARY KEY (`goods_id`),
  UNIQUE KEY `goods_sn` (`goods_sn`),
  KEY `cat_id` (`cat_id`),
  KEY `last_update` (`last_update`),
  KEY `brand_id` (`brand_id`),
  KEY `goods_weight` (`goods_weight`),
  KEY `promote_end_date` (`promote_end_date`),
  KEY `promote_start_date` (`promote_start_date`),
  KEY `goods_number` (`goods_number`),
  KEY `sort_order` (`sort_order`),
  KEY `record_status` (`record_status`),
  KEY `idx_provider_id` (`provider_id`),
  KEY `goods_audit` (`goods_audit`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8 COMMENT='第三方平台商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_product_sub`
--

DROP TABLE IF EXISTS `ty_scm_product_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_product_sub` (
  `gl_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `color_id` int(11) NOT NULL COMMENT '颜色ID',
  `size_id` int(11) NOT NULL COMMENT '尺寸ID',
  `is_pic` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否拍摄',
  `consign_num` int(11) NOT NULL DEFAULT '-1' COMMENT '代销库存:-2--不限量代销;-1:不代销;>=0限量代销',
  `sort_order` smallint(6) NOT NULL DEFAULT '0' COMMENT '大的在前面',
  `provider_barcode` varchar(64) DEFAULT NULL COMMENT '供应商条码(聚尚条码)',
  `tpd_provider_barcode` varchar(64) DEFAULT NULL COMMENT '供应商条码',
  PRIMARY KEY (`gl_id`),
  UNIQUE KEY `unique_good_color_size` (`goods_id`,`color_id`,`size_id`),
  UNIQUE KEY `provider_barcode` (`provider_barcode`),
  KEY `color_id` (`goods_id`,`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=302 DEFAULT CHARSET=utf8 COMMENT='第三方平台商品二级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_product_type_link`
--

DROP TABLE IF EXISTS `ty_scm_product_type_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_product_type_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_goods_id` (`goods_id`),
  KEY `idx_type_id` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8 COMMENT='第三方平台';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_shipping_packet`
--

DROP TABLE IF EXISTS `ty_scm_shipping_packet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_shipping_packet` (
  `packet_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `wave_sn` varchar(20) DEFAULT NULL COMMENT '波次号',
  `provider_id` int(11) unsigned DEFAULT NULL COMMENT '供应商ID',
  `order_id` int(11) unsigned DEFAULT NULL COMMENT '订单ID',
  `op_id` int(11) unsigned DEFAULT NULL COMMENT '订单商品明细ID',
  `shipping_id` smallint(4) unsigned DEFAULT NULL COMMENT '物流公司',
  `packet_sn` varchar(20) DEFAULT NULL COMMENT '运单号',
  `shipping_fee` decimal(5,2) DEFAULT NULL COMMENT '运费',
  `virtual_shipping` tinyint(1) DEFAULT '0' COMMENT '0为实发,1为虚发',
  `status` smallint(1) DEFAULT '0' COMMENT '0为拣货中，1已发货，2为缺货',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`packet_id`),
  KEY `IDX_PROVIDER_ID` (`provider_id`),
  KEY `IDX_ORDER_ID` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='第三方平台发货订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_shipping_wave`
--

DROP TABLE IF EXISTS `ty_scm_shipping_wave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_shipping_wave` (
  `wave_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `wave_sn` varchar(20) DEFAULT NULL COMMENT '波次号',
  `order_num` int(5) unsigned DEFAULT NULL COMMENT '订单数量',
  `shipping_num` int(5) unsigned DEFAULT NULL COMMENT '发货数量',
  `shortages` int(5) unsigned DEFAULT NULL COMMENT '缺货数量',
  `provider_id` int(11) unsigned DEFAULT NULL COMMENT '供应商ID',
  `wave_status` tinyint(1) DEFAULT '0' COMMENT '波次状态(0为拣货中、1为部分发货、2为完全发货)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `is_print_box` int(1) DEFAULT '0' COMMENT '是否打印装箱单',
  PRIMARY KEY (`wave_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='第三方平台波次';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_sms`
--

DROP TABLE IF EXISTS `ty_scm_sms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_sms` (
  `sms_id` smallint(6) NOT NULL AUTO_INCREMENT,
  `provider_id` smallint(6) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `content` text COMMENT '短信内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `commit_time` datetime NOT NULL COMMENT '提交时间',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `check_admin` smallint(6) DEFAULT NULL COMMENT '审核人',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0-草稿;1-未审核;2-已审核;3-已发送;4-已作废;5-已结算;6-发送中',
  `sms_price` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '短信发送单价',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `fail_times` smallint(6) NOT NULL DEFAULT '0' COMMENT '发送失败次数',
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='第三方平台短信任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_sms_send`
--

DROP TABLE IF EXISTS `ty_scm_sms_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_sms_send` (
  `ss_id` smallint(6) NOT NULL AUTO_INCREMENT,
  `sms_id` smallint(6) NOT NULL DEFAULT '0' COMMENT '短信任务ID',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号码',
  `source_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '号码来源:0-供应商导入;1-MT平台用户;',
  PRIMARY KEY (`ss_id`),
  UNIQUE KEY `mobile_index` (`sms_id`,`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT='第三方平台短信发送表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_sms_user`
--

DROP TABLE IF EXISTS `ty_scm_sms_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_sms_user` (
  `su_id` smallint(6) NOT NULL AUTO_INCREMENT,
  `provider_id` smallint(6) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号码',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`su_id`),
  UNIQUE KEY `mobile_index` (`provider_id`,`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='第三方平台供应商短信用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_scm_work_order`
--

DROP TABLE IF EXISTS `ty_scm_work_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_scm_work_order` (
  `wo_id` int(11) NOT NULL AUTO_INCREMENT,
  `wo_no` varchar(50) NOT NULL COMMENT '工单号',
  `wo_type` varchar(2) DEFAULT NULL COMMENT '工单类型(01-我方发起,02-第三方发起)',
  `provider_id` int(11) DEFAULT NULL COMMENT '供应商',
  `order_sn` varchar(20) DEFAULT NULL COMMENT '订单号',
  `content` varchar(500) DEFAULT NULL COMMENT '工单内容',
  `wo_status` varchar(1) DEFAULT NULL COMMENT '工单状态(0-草稿,1-待处理,2-已处理)',
  `wo_file` varchar(200) DEFAULT NULL COMMENT '工单附件',
  `reply_user` int(11) DEFAULT NULL COMMENT '回复人',
  `reply_option` varchar(200) DEFAULT NULL COMMENT '回复意见',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `reply_file` varchar(200) DEFAULT NULL COMMENT '回复附件',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`wo_id`),
  UNIQUE KEY `unique_wo_no` (`wo_no`),
  KEY `provider_id` (`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='第三方平台工单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_sessions`
--

DROP TABLE IF EXISTS `ty_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_sessions` (
  `session_id` varchar(40) NOT NULL DEFAULT '0',
  `ip_address` varchar(16) NOT NULL DEFAULT '0',
  `user_agent` varchar(255) DEFAULT NULL,
  `last_activity` int(10) unsigned NOT NULL DEFAULT '0',
  `user_data` text NOT NULL,
  PRIMARY KEY (`session_id`),
  KEY `last_activity_idx` (`last_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_shipping_area`
--

DROP TABLE IF EXISTS `ty_shipping_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_shipping_area` (
  `shipping_area_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '快递自定义区域编号',
  `shipping_area_name` varchar(150) NOT NULL DEFAULT '' COMMENT '快递自定义区域名称，如江浙沪等',
  `shipping_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '对应快递编号',
  `is_cod` int(1) NOT NULL DEFAULT '0' COMMENT '是否可以做COD',
  `configure` text NOT NULL COMMENT '收费金额等配置参数',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`shipping_area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='快递自定义区域管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_shipping_area_region`
--

DROP TABLE IF EXISTS `ty_shipping_area_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_shipping_area_region` (
  `shipping_area_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '快递自定义区域编号',
  `region_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '对应支持的地区编号',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`shipping_area_id`,`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='快递自定义区域对应地区管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_shipping_fcheck`
--

DROP TABLE IF EXISTS `ty_shipping_fcheck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_shipping_fcheck` (
  `batch_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `batch_sn` varchar(30) DEFAULT NULL COMMENT '导入批次号',
  `batch_type` int(1) NOT NULL DEFAULT '1' COMMENT '对帐类型（1：运费+COD，2：COD，3：运费）',
  `shipping_id` int(10) NOT NULL DEFAULT '0' COMMENT '配送方式ID',
  `lock_admin` int(10) NOT NULL DEFAULT '0' COMMENT '锁定人',
  `lock_date` datetime DEFAULT NULL COMMENT '锁定时间',
  `shipping_check` int(1) NOT NULL DEFAULT '0' COMMENT '是否物流审核',
  `shipping_check_admin` int(10) NOT NULL DEFAULT '0' COMMENT '物流审核人',
  `shipping_check_date` datetime DEFAULT NULL COMMENT '物流审核时间',
  `finance_check` int(1) NOT NULL DEFAULT '0' COMMENT '是否财务审核',
  `finance_check_admin` int(10) NOT NULL DEFAULT '0' COMMENT '财审人',
  `finance_check_date` datetime DEFAULT NULL COMMENT '财审时间',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `from_time` datetime DEFAULT NULL COMMENT '对帐开始时间',
  `to_time` datetime DEFAULT NULL COMMENT '对帐结束时间',
  PRIMARY KEY (`batch_id`),
  UNIQUE KEY `INX_BATCH_SN` (`batch_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流对帐表主表 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_shipping_fcheck_sub`
--

DROP TABLE IF EXISTS `ty_shipping_fcheck_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_shipping_fcheck_sub` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID',
  `shipping_id` int(10) NOT NULL DEFAULT '0' COMMENT '配送方式ID',
  `invoice_no` varchar(50) NOT NULL DEFAULT '' COMMENT '运单号',
  `destination` varchar(255) NOT NULL DEFAULT '' COMMENT '配送目的地',
  `weight` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '重量',
  `goods_number` int(11) NOT NULL DEFAULT '0' COMMENT '商品件数',
  `order_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单待付金额',
  `cod_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际代收金额',
  `express_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `cod_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '代收手续费',
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单换货单ID',
  `rec_type` int(1) NOT NULL DEFAULT '0' COMMENT '1为订单2为换货单',
  `sign_date` datetime DEFAULT NULL COMMENT '签收日期',
  `create_admin` int(10) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `batch_type` int(1) DEFAULT NULL COMMENT '对帐类型（1：运费+COD，2：COD，3：运费）',
  PRIMARY KEY (`id`),
  KEY `INX_INV_NO` (`invoice_no`),
  KEY `INX_ORDER_ID` (`order_id`),
  KEY `batch_id` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流对帐明细表 20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_shipping_info`
--

DROP TABLE IF EXISTS `ty_shipping_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_shipping_info` (
  `shipping_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '快递方式编号',
  `shipping_code` varchar(20) NOT NULL DEFAULT '' COMMENT '快递方式编码',
  `shipping_name` varchar(120) NOT NULL DEFAULT '' COMMENT '快递方式名称',
  `shipping_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '快递方式表述',
  `is_use` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用，默认为0，停用',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `track_name` varchar(50) NOT NULL DEFAULT '' COMMENT '运单跟踪用到的快递公司名称',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `shipping_company_100` varchar(30) NOT NULL DEFAULT ' ' COMMENT '对应快递100接口中的快递公司编码',
  PRIMARY KEY (`shipping_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='快递方式管理基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_shipping_package_interface`
--

DROP TABLE IF EXISTS `ty_shipping_package_interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_shipping_package_interface` (
  `sp_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `shipping_id` int(5) NOT NULL DEFAULT '0' COMMENT '物流公司ID',
  `order_id` int(11) NOT NULL DEFAULT '0' COMMENT '系统订单ID号',
  `order_sn` varchar(50) NOT NULL COMMENT '传给物流公司的订单号',
  `mailno` varchar(50) DEFAULT NULL COMMENT '运单号',
  `filter_status` int(2) DEFAULT '0' COMMENT '筛单状态(1为可收派、2为不可收派、3为待人工确认、4为其他)',
  `filter_remark` varchar(255) DEFAULT NULL COMMENT '处理失败的原因',
  `dist_code` varchar(10) DEFAULT NULL COMMENT '目的地代码',
  `result` int(2) DEFAULT '0' COMMENT '称重修改结果(1为成功、2为失败)',
  `result_remark` varchar(255) DEFAULT NULL COMMENT '称重修改失败备注',
  `add_time` datetime DEFAULT NULL COMMENT '获取运单号时间',
  `finish_time` datetime DEFAULT NULL COMMENT '包裹称重修改时间',
  PRIMARY KEY (`sp_id`),
  UNIQUE KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_shop`
--

DROP TABLE IF EXISTS `ty_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(45) NOT NULL,
  `shop_sn` varchar(45) DEFAULT NULL COMMENT '作为表之间关联',
  `is_cod` int(1) DEFAULT '1' COMMENT '是不支持货到付款 0-不支持；1-支持',
  `single_order` int(1) DEFAULT '0' COMMENT '单独商品生成订单 0-不单独；1-单独；',
  `shop_shipping` int(1) DEFAULT '0' COMMENT '是否从供应商发货 0-不；1-是',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_admin` int(10) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `shop_status` int(1) NOT NULL DEFAULT '1' COMMENT '1可用；0不可用',
  PRIMARY KEY (`shop_id`),
  UNIQUE KEY `shop_name_UNIQUE` (`shop_name`),
  UNIQUE KEY `shop_sn_UNIQUE` (`shop_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_sms_log`
--

DROP TABLE IF EXISTS `ty_sms_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_sms_log` (
  `rec_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `sms_from` varchar(255) NOT NULL COMMENT '发件人',
  `sms_to` varchar(255) NOT NULL COMMENT '收件人',
  `template_id` int(10) NOT NULL DEFAULT '0' COMMENT 'mail模板',
  `template_content` text NOT NULL COMMENT '邮件内容',
  `sms_priority` int(10) DEFAULT '0' COMMENT '优先级',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人，无则不填',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `send_date` datetime NOT NULL COMMENT '发送日期',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '发送状态，0未发，1已发，2失败',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='sms发送记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_subject`
--

DROP TABLE IF EXISTS `ty_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_subject` (
  `subject_id` int(10) NOT NULL AUTO_INCREMENT,
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `page_file` varchar(255) NOT NULL COMMENT '专题文件名称',
  `subject_title` varchar(255) NOT NULL COMMENT '专题主题,用作生成html页面的title',
  `subject_type` int(1) NOT NULL DEFAULT '1' COMMENT '专题类型，1表示活动页；2表示edm',
  `subject_keyword` varchar(255) NOT NULL COMMENT '专题关键词，用空格隔开,用作生成html页面的meta.keywords',
  `page_desc` text COMMENT '专题页详细描述 用作生成html页面的meta.description',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `gen_admin` int(10) DEFAULT NULL COMMENT '最后生成人ID',
  `gen_date` datetime DEFAULT NULL COMMENT '最后生成时间',
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专题主表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_subject_module`
--

DROP TABLE IF EXISTS `ty_subject_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_subject_module` (
  `module_id` int(10) NOT NULL AUTO_INCREMENT,
  `subject_id` int(10) DEFAULT NULL COMMENT '专题主表id',
  `module_title` varchar(255) NOT NULL COMMENT '模块显示名称',
  `module_text` text COMMENT '模块具体内容，json格式',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '模块在专题中的排列顺序',
  `module_type` int(10) NOT NULL DEFAULT '1' COMMENT '模块类型，1分类，2品牌，3品牌(加分类)，4自定义内容，5自动商品，6手动商品，7幻灯片，8留言板，9落地页，10轮播,团购，11正在抢购活动，12即将结束活动，13即将开始活动，14合作品牌集',
  `module_location` char(1) NOT NULL DEFAULT 't' COMMENT '模块位置 t:头；l:左；r:右;b:底 ',
  `product_num` int(10) NOT NULL DEFAULT '0' COMMENT '显示商品的数量 0全部显示',
  `create_admin` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专题子表，20130218添加';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_system_alert_log`
--

DROP TABLE IF EXISTS `ty_system_alert_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_system_alert_log` (
  `sys_log_id` int(12) NOT NULL AUTO_INCREMENT,
  `admin_id` int(10) NOT NULL DEFAULT '0',
  `date_insert` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '0:无错 1:有错',
  `content` text NOT NULL,
  PRIMARY KEY (`sys_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='后台系统数据验证日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_system_log`
--

DROP TABLE IF EXISTS `ty_system_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_system_log` (
  `sys_log_id` int(12) NOT NULL AUTO_INCREMENT,
  `admin_id` int(10) NOT NULL DEFAULT '0',
  `date_insert` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `url` varchar(255) NOT NULL,
  `ip` varchar(30) NOT NULL DEFAULT '',
  `content` text NOT NULL,
  PRIMARY KEY (`sys_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=614065 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='后台系统管理日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_third_region_info`
--

DROP TABLE IF EXISTS `ty_third_region_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_third_region_info` (
  `region_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '地区ID',
  `parent_id` smallint(5) DEFAULT '0' COMMENT '地区等级关联ID',
  `region_name` varchar(120) DEFAULT NULL COMMENT '地区名称',
  `region_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '区域等级',
  `ty_region_id` smallint(5) DEFAULT NULL COMMENT '宝贝购地区关联ID',
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_tmall_category`
--

DROP TABLE IF EXISTS `ty_tmall_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_tmall_category` (
  `cid` varchar(20) NOT NULL,
  `name` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_tmall_item`
--

DROP TABLE IF EXISTS `ty_tmall_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_tmall_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `num_iid` varchar(20) NOT NULL COMMENT '天猫商品ID\n',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '商品标题',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '站内售价',
  `tmall_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '天猫成本价',
  `reserve_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `nick` varchar(100) NOT NULL DEFAULT '' COMMENT '卖家昵称',
  `image` varchar(100) NOT NULL DEFAULT '' COMMENT '商品主图',
  `product_id` int(11) NOT NULL DEFAULT '0',
  `brand_id` int(11) NOT NULL DEFAULT '0' COMMENT '站内品牌ID\n',
  `provider_id` int(11) NOT NULL DEFAULT '0' COMMENT '站内供应商ID\n',
  `category_id` int(11) NOT NULL DEFAULT '0' COMMENT '站内分类ID\n',
  `sex` tinyint(3) NOT NULL DEFAULT '0' COMMENT '性别 ',
  `sku_adjust` text COMMENT 'sku校正，包括 alias别名， del 删除',
  `desc` text,
  `sync_data` text COMMENT '天猫数据',
  `sync_status` tinyint(3) NOT NULL COMMENT '同步状态 0待同步 1已同步',
  `sync_time` datetime DEFAULT NULL,
  `check_status` tinyint(3) NOT NULL COMMENT '审核状态 0待审核 1已审核 ',
  `check_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `stock_time` datetime DEFAULT NULL COMMENT '库存同步时间',
  `desc_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `num_iid_UNIQUE` (`num_iid`),
  KEY `stock_time_UNIQUE` (`stock_time`) COMMENT '同步库存时间',
  KEY `idx_desc_time` (`desc_time`)
) ENGINE=InnoDB AUTO_INCREMENT=7011 DEFAULT CHARSET=utf8 COMMENT='天猫商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_tmall_order_track`
--

DROP TABLE IF EXISTS `ty_tmall_order_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_tmall_order_track` (
  `track_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '跟踪ID',
  `order_sn` varchar(128) NOT NULL DEFAULT '' COMMENT '后台订单号',
  `track_order_sn` varchar(128) NOT NULL COMMENT '天猫订单号',
  `track_shipping_sn` varchar(128) DEFAULT NULL COMMENT '天猫物流单号',
  `track_create_aid` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `track_create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`track_id`),
  UNIQUE KEY `order_sn` (`order_sn`),
  UNIQUE KEY `track_order_sn` (`track_order_sn`),
  UNIQUE KEY `track_shipping_sn` (`track_shipping_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='天猫订单跟踪表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_tmall_return_track`
--

DROP TABLE IF EXISTS `ty_tmall_return_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_tmall_return_track` (
  `track_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '跟踪ID',
  `apply_id` varchar(128) NOT NULL DEFAULT '' COMMENT '后台自助退货申请ID',
  `track_return_sn` varchar(128) NOT NULL COMMENT '天猫退单号',
  `track_shipping_name` varchar(16) NOT NULL COMMENT '退货物流公司',
  `track_shipping_sn` varchar(128) DEFAULT NULL COMMENT '天猫退货物流单号',
  `track_create_aid` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `track_create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`track_id`),
  UNIQUE KEY `apply_id` (`apply_id`),
  UNIQUE KEY `track_return_sn` (`track_return_sn`),
  UNIQUE KEY `track_shipping_sn` (`track_shipping_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='天猫退单跟踪表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_tmall_sku`
--

DROP TABLE IF EXISTS `ty_tmall_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_tmall_sku` (
  `num_iid` varchar(20) NOT NULL COMMENT '天猫商品ID',
  `sku_id` varchar(20) NOT NULL COMMENT '天猫系统的sku_id',
  `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品ID',
  `sub_id` int(11) NOT NULL COMMENT 'product_sub表外键',
  PRIMARY KEY (`num_iid`,`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='天猫SKU与sub表的对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_tmp_order_point`
--

DROP TABLE IF EXISTS `ty_tmp_order_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_tmp_order_point` (
  `rec_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL DEFAULT '0',
  `order_sn` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `point_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `point_rate` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '购物送积分率',
  PRIMARY KEY (`rec_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_transaction_info`
--

DROP TABLE IF EXISTS `ty_transaction_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_transaction_info` (
  `transaction_id` int(10) NOT NULL AUTO_INCREMENT,
  `trans_type` int(2) NOT NULL DEFAULT '0' COMMENT '事务类型',
  `trans_status` int(2) NOT NULL DEFAULT '0' COMMENT '状态,1待出 2已出 3待入 4已入 5作废',
  `trans_sn` varchar(100) NOT NULL COMMENT '事务单号',
  `product_id` int(10) NOT NULL DEFAULT '0' COMMENT '商品款号',
  `color_id` int(10) NOT NULL DEFAULT '0' COMMENT '颜色id',
  `size_id` int(10) NOT NULL DEFAULT '0' COMMENT '尺寸id',
  `product_number` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  `depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '仓库id',
  `location_id` int(10) NOT NULL DEFAULT '0' COMMENT '储位code',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_admin` int(10) NOT NULL DEFAULT '0' COMMENT '更新人',
  `update_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `cancel_admin` int(10) NOT NULL DEFAULT '0' COMMENT '取消人',
  `cancel_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '取消时间',
  `trans_direction` int(1) NOT NULL DEFAULT '0' COMMENT '0=出库 1=入库',
  `sub_id` int(10) NOT NULL DEFAULT '0' COMMENT '子表主键',
  `finance_check_admin` int(10) NOT NULL DEFAULT '0' COMMENT '财审人',
  `finance_check_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '财审时间',
  `related_id` int(10) NOT NULL DEFAULT '0' COMMENT '关联ID',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  `shop_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '本网站价格',
  `consign_price` decimal(10,2) NOT NULL COMMENT '代销成本价',
  `cost_price` decimal(10,2) NOT NULL COMMENT '买断成本价',
  `consign_rate` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '浮动代销率',
  `product_cess` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '税率 20130218添加',
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='库存事务处理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_account_log`
--

DROP TABLE IF EXISTS `ty_user_account_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_account_log` (
  `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `link_id` int(10) NOT NULL DEFAULT '0' COMMENT '关联id',
  `user_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户编号',
  `user_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '变动金额',
  `pay_points` int(10) NOT NULL DEFAULT '0' COMMENT '变动支付积分',
  `change_desc` varchar(255) NOT NULL COMMENT '变动原因',
  `change_code` varchar(50) NOT NULL COMMENT '变动类型，硬编码',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户账户金额积分变动记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_account_log_kind`
--

DROP TABLE IF EXISTS `ty_user_account_log_kind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_account_log_kind` (
  `change_code` varchar(50) NOT NULL COMMENT '用户账户变动CODE',
  `change_name` varchar(100) NOT NULL COMMENT '用户账户变动名称',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用',
  `change_type` int(1) NOT NULL DEFAULT '0' COMMENT '0是金额，1是积分',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`change_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户余额变动类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_address`
--

DROP TABLE IF EXISTS `ty_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_address` (
  `address_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '联系地址编号',
  `user_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户编号',
  `consignee` varchar(60) NOT NULL DEFAULT '' COMMENT '联系人',
  `country` int(10) NOT NULL DEFAULT '0' COMMENT '国家编号',
  `province` int(10) NOT NULL DEFAULT '0' COMMENT '省编号',
  `city` int(10) NOT NULL DEFAULT '0' COMMENT '市编号',
  `district` int(10) NOT NULL DEFAULT '0' COMMENT '县/区',
  `address` varchar(120) NOT NULL DEFAULT '' COMMENT '详细地址',
  `zipcode` varchar(60) NOT NULL DEFAULT '' COMMENT '邮编',
  `tel` varchar(60) NOT NULL DEFAULT '' COMMENT '固定电话',
  `mobile` varchar(60) NOT NULL DEFAULT '' COMMENT '手机',
  `is_used` int(1) NOT NULL DEFAULT '0' COMMENT '是否默认，1为默认地址',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户联系地址管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_baby_info`
--

DROP TABLE IF EXISTS `ty_user_baby_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_baby_info` (
  `bid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `baby_name` varchar(50) NOT NULL,
  `baby_sex` smallint(1) NOT NULL,
  `birthday` datetime NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_info`
--

DROP TABLE IF EXISTS `ty_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_info` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `email` varchar(100) DEFAULT NULL COMMENT 'EMAIL',
  `user_name` varchar(50) NOT NULL COMMENT '用户名，默认为email前面部分，可修改',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `password` varchar(50) NOT NULL COMMENT '密码，RSA加密',
  `user_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '账户金额',
  `paid_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '累计销费金额',
  `pay_points` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '账户积分',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别，1男2女',
  `birthday` date NOT NULL COMMENT '生日',
  `address_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '默认地址编号',
  `last_date` datetime NOT NULL COMMENT '最后登录日期',
  `last_ip` varchar(15) NOT NULL COMMENT '最后登录IP',
  `visit_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '访问次数',
  `rank_id` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '用户等级',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `email_validated` int(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Email是否验证',
  `recom_email` varchar(100) NOT NULL COMMENT '推荐人email',
  `identity_code` varchar(21) NOT NULL DEFAULT '' COMMENT '身份证号',
  `passport_code` varchar(32) NOT NULL DEFAULT '' COMMENT '护照号',
  `user_from_url` varchar(50) NOT NULL COMMENT '来源网站',
  `union_sina` varchar(100) NOT NULL DEFAULT '' COMMENT '新浪联合登录',
  `union_qq` varchar(100) NOT NULL COMMENT 'QQ联合登录',
  `union_zhifubao` varchar(100) NOT NULL DEFAULT '' COMMENT '支付宝联合登录',
  `union_fclub` varchar(100) DEFAULT NULL COMMENT '聚尚联合登录',
  `user_type` int(10) NOT NULL DEFAULT '0' COMMENT '会员类型1为代销商默认为0普通会员',
  `discount_percent` decimal(3,2) NOT NULL DEFAULT '1.00' COMMENT '会员折扣率，默认为1',
  `mobile_checked` int(1) NOT NULL DEFAULT '0' COMMENT '手机是否验证',
  `baby_name` varchar(50) NOT NULL DEFAULT '' COMMENT '宝宝名称',
  `baby_sex` int(1) NOT NULL DEFAULT '0' COMMENT '宝宝性别，1男2女',
  `baby_birthday` date NOT NULL COMMENT '宝宝生日',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '默认为0，不停用，1为停用',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人，用户自己注册则不填',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `favorite_category` int(11) NOT NULL COMMENT '最喜欢的分类',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `mobile_phone` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_rank`
--

DROP TABLE IF EXISTS `ty_user_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_rank` (
  `rank_id` tinyint(3) NOT NULL AUTO_INCREMENT COMMENT '用户等级编号',
  `rank_name` varchar(90) NOT NULL COMMENT '用户等级名称',
  `min_points` int(10) NOT NULL DEFAULT '0' COMMENT '最少累计消费金额',
  `max_points` int(10) NOT NULL DEFAULT '0' COMMENT '最多累计消费金额',
  `regist_point` int(10) NOT NULL DEFAULT '0' COMMENT '注册送积分数',
  `buying_point_rate` decimal(10,2) NOT NULL DEFAULT '1.00' COMMENT '购买商品积分倍数，默认为1',
  `comment_point` int(10) NOT NULL DEFAULT '0' COMMENT '评论送积分数',
  `profile_point` int(10) NOT NULL DEFAULT '0' COMMENT '完善信息送积分数',
  `invite_point` int(10) NOT NULL DEFAULT '0' COMMENT '邀请送积分数',
  `friendby_point` int(10) NOT NULL DEFAULT '0' COMMENT '被邀请人购买首次下单送积分数',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`rank_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户等级基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_recharge`
--

DROP TABLE IF EXISTS `ty_user_recharge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_recharge` (
  `recharge_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '充值编号',
  `user_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户编号',
  `amount` decimal(10,2) NOT NULL COMMENT '充值金额',
  `is_paid` int(1) DEFAULT '0' COMMENT '是否支付，默认为0，未支付',
  `paid_date` datetime NOT NULL COMMENT '支付时间',
  `admin_note` varchar(255) NOT NULL COMMENT '管理员备注',
  `user_note` varchar(255) NOT NULL COMMENT '用户备注',
  `pay_id` int(10) NOT NULL COMMENT '支付方式编号',
  `is_audit` int(1) NOT NULL DEFAULT '0' COMMENT '是否审核',
  `audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '审核人',
  `audit_date` datetime NOT NULL COMMENT '审核日期',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否被删除，默认为0，未删除',
  `del_admin` int(10) NOT NULL DEFAULT '0' COMMENT '删除人',
  `del_date` datetime NOT NULL COMMENT '删除日期',
  PRIMARY KEY (`recharge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户充值记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_user_subscribe`
--

DROP TABLE IF EXISTS `ty_user_subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_user_subscribe` (
  `subscribe_id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(128) NOT NULL COMMENT '电邮或手机',
  `rush_id` int(11) DEFAULT '0',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`subscribe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户订阅提醒记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_voucher_campaign`
--

DROP TABLE IF EXISTS `ty_voucher_campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_voucher_campaign` (
  `campaign_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '现金券活动编号',
  `campaign_name` varchar(255) NOT NULL COMMENT '活动名称',
  `campaign_type` varchar(50) NOT NULL COMMENT '活动类型，电子，印刷，注册等',
  `campaign_status` int(1) NOT NULL DEFAULT '0' COMMENT '活动状态0:未启用1:启用2:停用',
  `start_date` datetime NOT NULL COMMENT '活动开始时间',
  `end_date` datetime NOT NULL COMMENT '活动结束时间',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '添加时间',
  `create_date` datetime NOT NULL COMMENT '添加人',
  `audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '是否启用',
  `audit_date` datetime NOT NULL COMMENT '启用人',
  `stop_admin` int(10) NOT NULL DEFAULT '0' COMMENT '停用时间',
  `stop_date` datetime NOT NULL COMMENT '停用人',
  `stop_reason` varchar(255) NOT NULL COMMENT '停用原因',
  `brand` text NOT NULL COMMENT '品牌列表',
  `category` text NOT NULL COMMENT '分类列表',
  `provider` text NOT NULL COMMENT '供应商列表',
  `product` text NOT NULL COMMENT '商品列表',
  `sex` varchar(50) NOT NULL COMMENT '性别',
  `season` varchar(50) NOT NULL COMMENT '季节',
  `desc` varchar(255) NOT NULL COMMENT '备注',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`campaign_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='现金券促销活动表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_voucher_record`
--

DROP TABLE IF EXISTS `ty_voucher_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_voucher_record` (
  `voucher_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `campaign_id` int(10) NOT NULL DEFAULT '0' COMMENT '活动ID',
  `release_id` int(10) NOT NULL DEFAULT '0' COMMENT '发放ID',
  `voucher_sn` varchar(30) DEFAULT NULL COMMENT '序号',
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `voucher_status` int(1) NOT NULL DEFAULT '0' COMMENT '0:未使用;1:已使用',
  `repeat_number` int(11) NOT NULL DEFAULT '1' COMMENT '可复用次数',
  `used_number` int(11) NOT NULL DEFAULT '0' COMMENT '已使用次数',
  `start_date` datetime NOT NULL COMMENT '有效时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `voucher_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '面值',
  `min_order` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最小订单金额',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`voucher_id`),
  UNIQUE KEY `voucher_voucher_sn_index` (`voucher_sn`),
  KEY `voucher_userid_index` (`user_id`),
  KEY `INX_START_TIME` (`start_date`),
  KEY `INX_END_TIME` (`end_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='现金券活动具体券号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_voucher_release`
--

DROP TABLE IF EXISTS `ty_voucher_release`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_voucher_release` (
  `release_id` int(10) NOT NULL AUTO_INCREMENT,
  `campaign_id` int(10) NOT NULL DEFAULT '0' COMMENT '活动ID',
  `voucher_name` varchar(255) NOT NULL COMMENT '抵用券描述',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '添加时间',
  `create_date` datetime NOT NULL COMMENT '添加人',
  `audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '发放时间',
  `audit_date` datetime NOT NULL COMMENT '发放人',
  `back_admin` int(10) NOT NULL COMMENT '撤销时间',
  `back_date` datetime NOT NULL COMMENT '撤销人',
  `voucher_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '面值',
  `repeat_number` int(10) NOT NULL DEFAULT '1' COMMENT '可重复使用次数',
  `voucher_count` int(10) NOT NULL DEFAULT '0' COMMENT '发放数量',
  `min_order` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最小订单金额',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `worth` int(10) NOT NULL DEFAULT '0' COMMENT '兑换所用的积分点数',
  `expire_days` int(10) NOT NULL DEFAULT '0' COMMENT '有效天数',
  `logo` varchar(255) NOT NULL DEFAULT '' COMMENT '现金券LOGO',
  `release_status` int(1) NOT NULL DEFAULT '0' COMMENT '0:未发放,1:已发放,2:已撒销',
  `release_rules` text COMMENT '规则',
  `release_note` varchar(255) DEFAULT NULL COMMENT '备注',
  `back_note` varchar(255) DEFAULT NULL COMMENT '撤销备注',
  PRIMARY KEY (`release_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='现金券活动发放表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-09 15:46:57
