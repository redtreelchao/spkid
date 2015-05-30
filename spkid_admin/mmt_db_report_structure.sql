-- MySQL dump 10.13  Distrib 5.6.14, for Linux (x86_64)
--
-- Host: localhost    Database: mmt_etl0
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
-- Current Database: `mmt_etl0`
--

-- CREATE DATABASE /*!32312 IF NOT EXISTS*/ `mmt_etl0` /*!40100 DEFAULT CHARACTER SET utf8 */;

-- USE `mmt_etl0`;

--
-- Table structure for table `etl_all_tables`
--

DROP TABLE IF EXISTS `etl_all_tables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_all_tables` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(50) DEFAULT NULL,
  `insert_sql` varchar(2000) DEFAULT NULL,
  `etl_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_brand_sale_detail`
--

DROP TABLE IF EXISTS `etl_brand_sale_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_brand_sale_detail` (
  `rush_id` int(10) DEFAULT '0' COMMENT '限时抢购编号',
  `rush_name` varchar(200) DEFAULT NULL COMMENT '开始时间',
  `order_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT '0' COMMENT '商品编号',
  `provider_productcode` varchar(50) DEFAULT NULL,
  `product_sn` varchar(60) DEFAULT NULL COMMENT '商品号码',
  `product_sex` int(1) DEFAULT NULL COMMENT '1男2女',
  `brand_id` int(10) unsigned DEFAULT '0' COMMENT '品牌编号',
  `brand_name` varchar(50) DEFAULT NULL COMMENT '品牌名称',
  `category_name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `pcategory_id` int(10) DEFAULT NULL,
  `category_id` int(10) DEFAULT '0' COMMENT '分类编号',
  `provider_code` varchar(20) DEFAULT '' COMMENT '供应商代码。20130218添加',
  `provider_id` int(10) DEFAULT '0' COMMENT '供应商编号',
  `color_id` int(10) DEFAULT '0' COMMENT '颜色ID',
  `color_name` varchar(50) DEFAULT NULL COMMENT '颜色名称',
  `size_id` int(10) DEFAULT '0' COMMENT '尺寸ID',
  `size_name` varchar(50) DEFAULT NULL COMMENT '尺寸名称',
  `shop_price` decimal(10,2) DEFAULT '0.00' COMMENT 'f-club价',
  `gl_num` bigint(12) DEFAULT NULL,
  `gl_amount` decimal(29,2) DEFAULT NULL,
  `product_num` int(10) unsigned DEFAULT '1' COMMENT '商品数量',
  `product_amount` decimal(20,2) DEFAULT NULL,
  `return_num` bigint(11) NOT NULL DEFAULT '0',
  `return_amount` decimal(20,2) NOT NULL DEFAULT '0.00',
  `create_date` datetime NOT NULL COMMENT '添加人',
  `confirm_date` datetime NOT NULL COMMENT '审核时间',
  `finance_date` datetime NOT NULL COMMENT '财审时间',
  `season_id` int(10) NOT NULL,
  `season_name` varchar(50) NOT NULL,
  `market_price` decimal(10,2) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  KEY `dl_index` (`rush_id`,`product_id`,`size_id`,`color_id`,`season_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_brand_sale_detail_gl`
--

DROP TABLE IF EXISTS `etl_brand_sale_detail_gl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_brand_sale_detail_gl` (
  `rush_id` int(10) NOT NULL DEFAULT '0' COMMENT '限时抢购编号',
  `rush_name` varchar(200) NOT NULL COMMENT '开始时间',
  `product_id` int(10) DEFAULT '0' COMMENT '商品编号',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '限时抢购价格',
  `category_id` int(10) DEFAULT '0' COMMENT '分类编号',
  `color_id` int(10) DEFAULT NULL COMMENT '颜色ID',
  `size_id` int(10) DEFAULT NULL COMMENT '尺寸ID',
  `gl_num` bigint(12) DEFAULT NULL,
  `etl_date` varchar(10) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  KEY `index_gl` (`product_id`,`color_id`,`size_id`),
  KEY `gl_index` (`rush_id`,`product_id`,`size_id`,`color_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_brand_sale_detail_rush`
--

DROP TABLE IF EXISTS `etl_brand_sale_detail_rush`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_brand_sale_detail_rush` (
  `rush_id` int(10) NOT NULL DEFAULT '0' COMMENT '限时抢购编号',
  `rush_name` varchar(200) NOT NULL COMMENT '开始时间',
  `product_id` int(10) DEFAULT '0' COMMENT '商品编号',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '限时抢购价格',
  `category_id` int(10) DEFAULT '0' COMMENT '分类编号',
  `color_id` int(10) DEFAULT NULL COMMENT '颜色ID',
  `size_id` int(10) DEFAULT NULL COMMENT '尺寸ID',
  `gl_num` bigint(12) DEFAULT NULL,
  `etl_date` varchar(10) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `provider_productcode` varchar(50) DEFAULT NULL,
  `product_sn` varchar(60) DEFAULT NULL COMMENT '商品号码',
  `product_sex` int(1) DEFAULT NULL COMMENT '1男2女',
  `brand_id` int(10) unsigned DEFAULT '0' COMMENT '品牌编号',
  `brand_name` varchar(50) DEFAULT NULL COMMENT '品牌名称',
  `category_name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `pcategory_id` int(10) DEFAULT NULL,
  `provider_code` varchar(20) DEFAULT '' COMMENT '供应商代码。20130218添加',
  `provider_id` int(10) DEFAULT '0' COMMENT '供应商编号',
  `color_name` varchar(50) DEFAULT NULL COMMENT '颜色名称',
  `size_name` varchar(50) DEFAULT NULL COMMENT '尺寸名称',
  `season_id` int(10) NOT NULL,
  `season_name` varchar(50) NOT NULL,
  `market_price` decimal(10,2) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  KEY `rush_index` (`rush_id`,`product_id`,`size_id`,`color_id`),
  KEY `index_rush` (`product_id`,`color_id`,`size_id`,`season_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_cps_order_product_info`
--

DROP TABLE IF EXISTS `etl_cps_order_product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_cps_order_product_info` (
  `cps_id` int(10) DEFAULT NULL,
  `cps_sn` varchar(20) DEFAULT NULL,
  `cps_name` varchar(45) DEFAULT NULL,
  `cps_log_data` text,
  `order_id` int(10) DEFAULT NULL,
  `order_sn` varchar(20) DEFAULT NULL,
  `source_id` int(2) DEFAULT NULL,
  `source_name` varchar(50) DEFAULT NULL,
  `order_status` varchar(20) DEFAULT NULL,
  `order_price` decimal(10,2) DEFAULT NULL,
  `paid_price` decimal(10,2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `union_name` varchar(100) DEFAULT NULL,
  `union_qq` varchar(100) DEFAULT NULL,
  `order_address` varchar(200) DEFAULT NULL,
  `consignee` varchar(60) DEFAULT NULL,
  `mobile` varchar(60) DEFAULT NULL,
  `new_mobile` varchar(60) DEFAULT NULL,
  `tel` varchar(60) DEFAULT NULL,
  `product_id` int(10) DEFAULT NULL,
  `product_sn` varchar(30) DEFAULT NULL,
  `brand_id` int(10) DEFAULT NULL,
  `brand_name` varchar(30) DEFAULT NULL,
  `category_id` int(10) DEFAULT NULL,
  `category_name` varchar(30) DEFAULT NULL,
  `product_num` int(10) DEFAULT NULL,
  `product_price` decimal(10,2) DEFAULT NULL,
  `order_voucher` decimal(10,2) DEFAULT NULL,
  `product_voucher` decimal(10,2) DEFAULT NULL,
  `order_num` int(10) DEFAULT NULL,
  `return_num` int(10) DEFAULT NULL,
  `return_amt` decimal(10,2) DEFAULT NULL,
  `is_first` int(1) DEFAULT NULL,
  `has_return` int(1) DEFAULT NULL,
  `voucher_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '面值',
  `min_order` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最小订单金额',
  `make_order_price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_cps_user_order`
--

DROP TABLE IF EXISTS `etl_cps_user_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_cps_user_order` (
  `user_id` int(10) DEFAULT NULL,
  KEY `index_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_dict_category`
--

DROP TABLE IF EXISTS `etl_dict_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_dict_category` (
  `category_id` int(10) NOT NULL COMMENT '分类编号',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `parent_id` int(10) NOT NULL DEFAULT '0' COMMENT '父分类编号',
  `parent_name` varchar(50) NOT NULL DEFAULT '0' COMMENT '父分类名称',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用，默认为0，不使用',
  `etl_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`category_id`),
  KEY `index_id` (`category_id`),
  KEY `index_p_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_last_goods_erp_detail`
--

DROP TABLE IF EXISTS `etl_last_goods_erp_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_last_goods_erp_detail` (
  `transaction_id` int(11) NOT NULL COMMENT 'transId',
  `brand_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `pcategory_id` int(11) DEFAULT NULL,
  `brand_name` varchar(200) DEFAULT NULL,
  `category_name` varchar(100) DEFAULT NULL,
  `pcategory_name` varchar(100) DEFAULT NULL,
  `color_name` varchar(50) DEFAULT NULL,
  `size_name` varchar(50) DEFAULT NULL,
  `provider_code` varchar(100) DEFAULT NULL,
  `provider_name` varchar(100) DEFAULT NULL,
  `provider_id` int(11) DEFAULT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `product_sn` varchar(100) DEFAULT NULL,
  `trans_type_name` varchar(50) DEFAULT NULL,
  `product_number` int(11) DEFAULT NULL,
  `product_amt` double DEFAULT NULL,
  `elt_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `batch_id` int(11) DEFAULT NULL,
  `direction` bigint(11) DEFAULT NULL,
  `coop_id` int(11) DEFAULT NULL,
  `out_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_log`
--

DROP TABLE IF EXISTS `etl_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_log` (
  `log_id` int(10) NOT NULL AUTO_INCREMENT,
  `log_name` varchar(100) DEFAULT NULL,
  `etl_start_date` datetime DEFAULT NULL,
  `etl_end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `index_log_id` (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=745 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_discount`
--

DROP TABLE IF EXISTS `etl_order_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_discount` (
  `order_discount_id` int(10) NOT NULL AUTO_INCREMENT,
  `order_id` int(10) DEFAULT NULL,
  `order_sn` varchar(50) DEFAULT NULL,
  `return_id` int(10) DEFAULT NULL,
  `return_sn` varchar(50) DEFAULT NULL,
  `source_id` int(10) DEFAULT NULL,
  `order_status` int(2) DEFAULT NULL,
  `order_price` decimal(10,2) DEFAULT NULL,
  `nodiscount_price` decimal(10,2) DEFAULT NULL,
  `return_price` decimal(10,2) DEFAULT NULL,
  `paid_price` decimal(10,2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `voucher` decimal(10,2) DEFAULT NULL,
  `group_discount` decimal(10,2) DEFAULT NULL,
  `promote_discount` decimal(10,2) DEFAULT NULL,
  `pay_id_array` varchar(200) DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `etl_date` datetime DEFAULT NULL,
  `shipping_true` int(1) DEFAULT NULL,
  PRIMARY KEY (`order_discount_id`),
  KEY `index_order_id` (`order_id`),
  KEY `index_return_id` (`return_id`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_discount_all`
--

DROP TABLE IF EXISTS `etl_order_discount_all`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_discount_all` (
  `order_discount_id` int(10) NOT NULL AUTO_INCREMENT,
  `order_id` int(10) DEFAULT NULL,
  `order_sn` varchar(50) DEFAULT NULL,
  `return_id` int(10) DEFAULT NULL,
  `return_sn` varchar(50) DEFAULT NULL,
  `source_id` int(10) DEFAULT NULL,
  `order_status` int(2) DEFAULT NULL,
  `order_price` decimal(10,2) DEFAULT NULL,
  `nodiscount_price` decimal(10,2) DEFAULT NULL,
  `return_price` decimal(10,2) DEFAULT NULL,
  `paid_price` decimal(10,2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `voucher` decimal(10,2) DEFAULT NULL,
  `group_discount` decimal(10,2) DEFAULT NULL,
  `promote_discount` decimal(10,2) DEFAULT NULL,
  `pay_id_array` varchar(200) DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `etl_date` datetime DEFAULT NULL,
  `shipping_true` int(1) DEFAULT NULL,
  PRIMARY KEY (`order_discount_id`),
  KEY `index_order_id` (`order_id`),
  KEY `index_return_id` (`return_id`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_nodiscount`
--

DROP TABLE IF EXISTS `etl_order_nodiscount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_nodiscount` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单编号',
  `return_id` int(10) NOT NULL DEFAULT '0',
  `nodiscount_price` decimal(32,4) DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `index_order` (`order_id`),
  KEY `index_return` (`return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_nodiscount_all`
--

DROP TABLE IF EXISTS `etl_order_nodiscount_all`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_nodiscount_all` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单编号',
  `return_id` int(10) NOT NULL DEFAULT '0',
  `nodiscount_price` decimal(32,4) DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `index_order_id` (`order_id`),
  KEY `index_return_id` (`return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_pay`
--

DROP TABLE IF EXISTS `etl_order_pay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_pay` (
  `order_pay_id` int(10) NOT NULL AUTO_INCREMENT,
  `order_id` int(10) DEFAULT NULL,
  `order_status` int(2) DEFAULT NULL,
  `pay_id_array` varchar(200) DEFAULT NULL,
  `pay_name_array` varchar(200) DEFAULT NULL,
  `pay_name_money_array` varchar(200) DEFAULT NULL,
  `pay_code_money_array` varchar(200) DEFAULT NULL,
  `order_paid_money` decimal(20,0) DEFAULT NULL,
  `is_return` int(10) DEFAULT NULL,
  `etl_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_pay_id`),
  KEY `index_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_product_discount`
--

DROP TABLE IF EXISTS `etl_order_product_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_product_discount` (
  `order_id` int(10) DEFAULT NULL,
  `return_id` int(10) DEFAULT NULL,
  `return_sn` varchar(15) DEFAULT NULL,
  `order_sn` varchar(15) DEFAULT NULL,
  `source_id` int(10) DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT '0' COMMENT '商品编号',
  `color_id` int(10) DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) DEFAULT '0' COMMENT '尺寸ID',
  `order_status` int(2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT '0.00',
  `voucher` decimal(28,8) DEFAULT '0.00000000',
  `group_discount` decimal(28,8) DEFAULT '0.00000000',
  `promote_discount` decimal(28,8) DEFAULT '0.00000000',
  `confirm_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `product_num` int(10) DEFAULT NULL,
  `shipping_true` int(1) DEFAULT NULL,
  KEY `index_e_opd_product_id` (`product_id`),
  KEY `index_e_opd_color_id` (`color_id`),
  KEY `index_e_opd_size_id` (`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_product_discount_all`
--

DROP TABLE IF EXISTS `etl_order_product_discount_all`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_product_discount_all` (
  `order_id` int(10) DEFAULT NULL,
  `return_id` int(10) DEFAULT NULL,
  `return_sn` varchar(15) DEFAULT NULL,
  `order_sn` varchar(15) DEFAULT NULL,
  `source_id` int(10) DEFAULT NULL,
  `product_id` int(10) unsigned DEFAULT '0' COMMENT '商品编号',
  `color_id` int(10) DEFAULT '0' COMMENT '颜色ID',
  `size_id` int(10) DEFAULT '0' COMMENT '尺寸ID',
  `order_status` int(2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT '0.00',
  `voucher` decimal(28,8) DEFAULT '0.00000000',
  `group_discount` decimal(28,8) DEFAULT '0.00000000',
  `promote_discount` decimal(28,8) DEFAULT '0.00000000',
  `confirm_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `product_num` int(10) DEFAULT NULL,
  `shipping_true` int(1) DEFAULT NULL,
  KEY `index_e_opd_product_id` (`product_id`),
  KEY `index_e_opd_color_id` (`color_id`),
  KEY `index_e_opd_size_id` (`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_product_sale`
--

DROP TABLE IF EXISTS `etl_order_product_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_product_sale` (
  `sale_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id 无意义',
  `order_id` int(10) NOT NULL,
  `order_sn` varchar(20) DEFAULT NULL,
  `source_id` int(10) DEFAULT NULL,
  `trans_type` int(2) DEFAULT NULL,
  `trans_status` int(2) DEFAULT NULL,
  `deny_status` int(2) DEFAULT NULL,
  `product_id` int(10) DEFAULT NULL,
  `product_sn` varchar(60) DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `product_num` decimal(10,2) DEFAULT NULL,
  `shop_price` decimal(10,2) DEFAULT NULL,
  `product_price` int(10) DEFAULT NULL,
  `brand_id` int(10) DEFAULT NULL,
  `brand_name` varchar(50) DEFAULT NULL,
  `provider_id` int(10) DEFAULT NULL,
  `provider_code` varchar(20) DEFAULT NULL,
  `provider_name` varchar(100) DEFAULT NULL,
  `color_id` int(10) DEFAULT NULL,
  `color_name` varchar(30) DEFAULT NULL,
  `size_id` int(10) DEFAULT NULL,
  `size_name` varchar(30) DEFAULT NULL,
  `cat_id` int(10) DEFAULT NULL,
  `cat_name` varchar(30) DEFAULT NULL,
  `pcat_id` int(10) DEFAULT NULL,
  `pcat_name` varchar(30) DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `payment_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `etl_date` datetime DEFAULT NULL,
  `return_id` int(10) DEFAULT NULL,
  `return_sn` varchar(15) DEFAULT NULL,
  `provider_productcode` varchar(50) DEFAULT NULL,
  `change_id` int(10) DEFAULT NULL,
  `change_sn` varchar(20) DEFAULT NULL,
  `direction` int(1) DEFAULT NULL COMMENT '-1为退货或换货入;1为订单或换货出',
  `status` int(1) DEFAULT NULL,
  `shipping_true` int(1) DEFAULT NULL,
  PRIMARY KEY (`sale_id`),
  KEY `index_order_id` (`order_id`),
  KEY `index_order_sku` (`order_id`,`product_id`,`color_id`,`size_id`),
  KEY `index_return_id` (`return_id`),
  KEY `index_e_ops_product_id` (`product_id`),
  KEY `index_e_ops_color_id` (`color_id`),
  KEY `index_e_ops_size_id` (`size_id`)
) ENGINE=InnoDB AUTO_INCREMENT=519 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_return`
--

DROP TABLE IF EXISTS `etl_order_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_return` (
  `order_id` int(10) NOT NULL,
  `order_num` int(10) NOT NULL DEFAULT '0',
  `return_num` int(10) NOT NULL DEFAULT '0',
  `order_amt` decimal(10,2) NOT NULL DEFAULT '0.00',
  `return_amt` decimal(10,2) NOT NULL DEFAULT '0.00',
  `etl_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `index_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_order_transaction_info`
--

DROP TABLE IF EXISTS `etl_order_transaction_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_order_transaction_info` (
  `transaction_id` int(11) NOT NULL COMMENT 'transId',
  `trans_sn` varchar(50) DEFAULT NULL COMMENT 'trans_code',
  `sub_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_sn` varchar(100) DEFAULT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `color_id` int(11) DEFAULT NULL,
  `color_name` varchar(50) DEFAULT NULL,
  `size_id` int(11) DEFAULT NULL,
  `size_name` varchar(50) DEFAULT NULL,
  `provider_id` int(11) DEFAULT NULL,
  `provider_code` varchar(50) DEFAULT NULL,
  `provider_name` varchar(100) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `brand_name` varchar(200) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `category_name` varchar(100) DEFAULT NULL,
  `pcategory_id` int(11) DEFAULT NULL,
  `pcategory_name` varchar(100) DEFAULT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `product_number` int(11) DEFAULT NULL,
  `cost_price` double DEFAULT NULL,
  `shop_price` double DEFAULT NULL,
  `product_cess` double DEFAULT NULL,
  `consign_price` double DEFAULT NULL,
  `consign_rate` double DEFAULT NULL,
  `product_price` double DEFAULT NULL,
  `trans_status` int(11) DEFAULT NULL,
  `trans_type` int(11) DEFAULT NULL,
  `depot_io_name` varchar(50) DEFAULT NULL,
  `coop_id` int(11) DEFAULT NULL,
  `trans_direction` int(11) DEFAULT NULL,
  `depot_id` int(11) DEFAULT NULL,
  `source_id` int(11) DEFAULT NULL,
  `finance_check_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `cancel_date` datetime DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `shipping_true` int(1) DEFAULT NULL,
  `etl_date` datetime DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `index_sku` (`product_id`,`color_id`,`size_id`),
  KEY `index_sst` (`trans_sn`,`sub_id`,`trans_type`),
  KEY `index_product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_payment_delivery_memory`
--

DROP TABLE IF EXISTS `etl_payment_delivery_memory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_payment_delivery_memory` (
  `order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '订单SN',
  `payment_money` decimal(10,2) DEFAULT '0.00' COMMENT '支付金额',
  `pay_name` varchar(120) DEFAULT '',
  `shipping_name` varchar(120) DEFAULT '' COMMENT '快递方式名称',
  `pay_id` int(11) DEFAULT '0' COMMENT '支付方式编号',
  `shipping_id` int(10) unsigned DEFAULT '0' COMMENT '快递方式编号',
  `out_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_region_info`
--

DROP TABLE IF EXISTS `etl_region_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_region_info` (
  `district_id` int(10) NOT NULL,
  `city_id` int(10) NOT NULL,
  `province_id` int(10) NOT NULL,
  `province_name` varchar(120) NOT NULL,
  `city_name` varchar(120) NOT NULL,
  `district_name` varchar(120) NOT NULL,
  `area` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`district_id`,`city_id`,`province_id`),
  KEY `index_district_id` (`district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_rush_sale_detail`
--

DROP TABLE IF EXISTS `etl_rush_sale_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_rush_sale_detail` (
  `rush_id` varchar(10) DEFAULT NULL,
  `rush_name` varchar(200) DEFAULT NULL,
  `beihuo_num` bigint(10) DEFAULT NULL,
  `beihuo_amount` double(29,2) DEFAULT NULL,
  `product_num` bigint(10) DEFAULT NULL,
  `product_amount` double(29,2) DEFAULT NULL,
  `c_product_num` bigint(10) DEFAULT NULL,
  `c_product_amount` double(29,2) DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `order_num` bigint(10) DEFAULT NULL,
  `user_num` bigint(10) DEFAULT NULL,
  `return_num` bigint(10) DEFAULT NULL,
  `return_amount` double(29,2) DEFAULT NULL,
  `c_order_num` bigint(10) DEFAULT NULL,
  `c_user_num` bigint(10) DEFAULT NULL,
  `c_return_num` bigint(10) DEFAULT NULL,
  `c_return_amount` double(29,2) DEFAULT NULL,
  `product_sex` bigint(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_shipping_fcheck_sub`
--

DROP TABLE IF EXISTS `etl_shipping_fcheck_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_shipping_fcheck_sub` (
  `order_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单换货单ID',
  `shipping_id` int(10) NOT NULL DEFAULT '0' COMMENT '配送方式ID',
  `express_fee` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `index_order` (`order_id`),
  KEY `index_ship` (`shipping_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_tmp_rpt_users_total`
--

DROP TABLE IF EXISTS `etl_tmp_rpt_users_total`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_tmp_rpt_users_total` (
  `user_money` decimal(10,2) NOT NULL DEFAULT '0.00',
  `change_type` varchar(50) NOT NULL DEFAULT '',
  `change_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  KEY `index_user` (`user_id`),
  KEY `index_type` (`change_type`),
  KEY `index_time` (`change_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_transaction_info`
--

DROP TABLE IF EXISTS `etl_transaction_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_transaction_info` (
  `transaction_id` int(11) NOT NULL COMMENT 'transId',
  `trans_sn` varchar(50) DEFAULT NULL COMMENT 'trans_code',
  `sub_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_sn` varchar(100) DEFAULT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `color_id` int(11) DEFAULT NULL,
  `color_name` varchar(50) DEFAULT NULL,
  `size_id` int(11) DEFAULT NULL,
  `size_name` varchar(50) DEFAULT NULL,
  `provider_id` int(11) DEFAULT NULL,
  `provider_code` varchar(50) DEFAULT NULL,
  `provider_name` varchar(100) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `brand_name` varchar(200) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `category_name` varchar(100) DEFAULT NULL,
  `pcategory_id` int(11) DEFAULT NULL,
  `pcategory_name` varchar(100) DEFAULT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `product_number` int(11) DEFAULT NULL,
  `cost_price` double DEFAULT NULL,
  `shop_price` double DEFAULT NULL,
  `product_cess` double DEFAULT NULL,
  `consign_price` double DEFAULT NULL,
  `consign_rate` double DEFAULT NULL,
  `product_price` double DEFAULT NULL,
  `trans_status` int(11) DEFAULT NULL,
  `trans_type` int(11) DEFAULT NULL,
  `depot_io_name` varchar(50) DEFAULT NULL,
  `coop_id` int(11) DEFAULT NULL,
  `trans_direction` int(11) DEFAULT NULL,
  `depot_id` int(11) DEFAULT NULL,
  `source_id` int(11) DEFAULT NULL,
  `finance_check_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `cancel_date` datetime DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `shipping_true` int(1) DEFAULT NULL,
  `etl_date` datetime DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `index_sku` (`product_id`,`color_id`,`size_id`),
  KEY `index_type` (`trans_type`),
  KEY `index_ship_true` (`shipping_true`),
  KEY `index_sub` (`sub_id`),
  KEY `index_sn` (`trans_sn`),
  KEY `index_status` (`trans_status`),
  KEY `index_finance_time` (`finance_check_date`),
  KEY `index_confirm_time` (`confirm_date`),
  KEY `index_provider` (`provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `etl_user_min_order`
--

DROP TABLE IF EXISTS `etl_user_min_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `etl_user_min_order` (
  `user_id` int(10) DEFAULT NULL,
  `order_id` int(10) DEFAULT NULL,
  KEY `index_user` (`user_id`),
  KEY `index_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '订单SN',
  `order_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费，=0不收运费 >0收运费'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_cw_finance_income_payment_second`
--

DROP TABLE IF EXISTS `tmp_cw_finance_income_payment_second`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_cw_finance_income_payment_second` (
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `payment_id` int(11) DEFAULT '0',
  `order_id` int(11) DEFAULT '0',
  `order_from_id` int(11) DEFAULT '0',
  `pay_id` int(11) DEFAULT '0',
  `pay_code` varchar(50) DEFAULT '',
  `payment_amount` decimal(10,4) DEFAULT '0.0000',
  `payment_desc` varchar(255) DEFAULT '',
  `is_discount` tinyint(1) DEFAULT '0',
  `undiscount_amount` decimal(10,4) DEFAULT '0.0000',
  `shipping_fee` decimal(10,2) DEFAULT '0.00',
  `split_shipping_fee` decimal(10,4) DEFAULT '0.0000',
  `is_return` tinyint(1) DEFAULT '0',
  `pro_fee` decimal(10,2) DEFAULT '0.00',
  KEY `newindex1` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_cw_income_way_order`
--

DROP TABLE IF EXISTS `tmp_cw_income_way_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_cw_income_way_order` (
  `order_id` mediumint(12) unsigned NOT NULL DEFAULT '0',
  `is_return` int(1) NOT NULL DEFAULT '0',
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  KEY `is_return` (`is_return`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_cw_merge_gross_goods`
--

DROP TABLE IF EXISTS `tmp_cw_merge_gross_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_cw_merge_gross_goods` (
  `order_id` int(15) DEFAULT '0',
  `order_sn` varchar(25) DEFAULT '0',
  `return_id` int(15) DEFAULT '0',
  `return_sn` varchar(25) DEFAULT '0',
  `finance_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `product_id` int(11) DEFAULT '0',
  `product_sn` varchar(15) DEFAULT NULL,
  `provider_productcode` varchar(50) DEFAULT NULL,
  `batch_id` int(11) DEFAULT '0',
  `brand_id` int(11) DEFAULT '0',
  `category_id` int(11) DEFAULT '0',
  `parent_id` int(11) DEFAULT NULL,
  `color_id` int(11) DEFAULT '0',
  `color_name` varchar(20) DEFAULT NULL,
  `size_id` int(11) DEFAULT '0',
  `size_name` varchar(20) DEFAULT NULL,
  `product_num` int(11) DEFAULT '0',
  `shop_price` decimal(12,6) DEFAULT '0.000000',
  `product_price` decimal(12,6) DEFAULT '0.000000',
  `total_price` decimal(12,6) DEFAULT '0.000000',
  `order_price` decimal(12,6) DEFAULT NULL,
  `provider_cooperation` int(2) DEFAULT '0',
  `cost_price` decimal(12,6) DEFAULT '0.000000',
  `consign_price` decimal(12,6) DEFAULT '0.000000',
  `consign_rate` decimal(10,6) DEFAULT '0.000000' COMMENT '浮动成本率',
  `product_cess` decimal(3,2) DEFAULT '0.00',
  `category_name` varchar(30) DEFAULT NULL,
  `parent_name` varchar(30) DEFAULT NULL,
  `discount` decimal(12,6) DEFAULT NULL,
  `voucher` decimal(12,6) DEFAULT NULL,
  `group_discount` decimal(12,6) DEFAULT NULL,
  `promote_discount` decimal(12,6) DEFAULT NULL,
  `provider_id` int(5) DEFAULT NULL,
  `provider_code` varchar(40) DEFAULT '0',
  `provider_name` varchar(50) DEFAULT '0',
  `brand_name` varchar(50) DEFAULT '0',
  KEY `NewIndex1` (`order_id`),
  KEY `NewIndex2` (`return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_cw_merge_gross_goods_result`
--

DROP TABLE IF EXISTS `tmp_cw_merge_gross_goods_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_cw_merge_gross_goods_result` (
  `order_id` int(15) DEFAULT NULL,
  `order_sn` varchar(20) DEFAULT NULL,
  `return_id` int(15) DEFAULT NULL,
  `return_sn` varchar(20) DEFAULT NULL,
  `finance_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `provider_cooperation` int(2) DEFAULT NULL,
  `product_sn` varchar(25) DEFAULT NULL,
  `provider_productcode` varchar(50) DEFAULT '''''',
  `goods_amount` decimal(10,6) DEFAULT '0.000000',
  `ctb_costp` decimal(20,6) DEFAULT '0.000000',
  `ctb_consignp` decimal(20,6) DEFAULT '0.000000',
  `cost_amount` decimal(20,6) DEFAULT '0.000000',
  `baby_amount` decimal(20,6) DEFAULT '0.000000',
  `provider_cess` decimal(10,6) DEFAULT NULL,
  `product_number` int(10) DEFAULT '0',
  `category_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `brand_id` int(10) DEFAULT NULL,
  `color_id` int(5) DEFAULT NULL,
  `color_name` varchar(20) DEFAULT NULL,
  `size_id` int(5) DEFAULT NULL,
  `size_name` varchar(20) DEFAULT NULL,
  `product_id` int(10) DEFAULT NULL,
  `coop_id` int(5) DEFAULT NULL,
  `provider_id` int(10) DEFAULT NULL,
  `discount_amount` decimal(20,6) DEFAULT '0.000000',
  `netsale_amount` decimal(20,6) DEFAULT '0.000000',
  `norate_netsale_amount` decimal(20,6) DEFAULT '0.000000',
  `nofaxcost_amount` decimal(20,6) DEFAULT '0.000000',
  `product_cess` decimal(3,2) DEFAULT NULL,
  `gross_amount` decimal(20,6) DEFAULT NULL,
  `gross_percent` decimal(20,6) DEFAULT '0.000000',
  `discount` decimal(20,6) DEFAULT '0.000000',
  `voucher_amount` decimal(20,6) DEFAULT '0.000000',
  `group_amount` decimal(20,6) DEFAULT '0.000000',
  `promote_amount` decimal(20,6) DEFAULT '0.000000',
  `provider_cost` decimal(10,6) DEFAULT '0.000000',
  `provider_commission` varchar(50) DEFAULT NULL,
  `provider_code` varchar(20) DEFAULT NULL,
  `provider_name` varchar(100) DEFAULT NULL,
  `brand_name` varchar(50) DEFAULT NULL,
  `category_name` varchar(30) DEFAULT NULL,
  `parent_name` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_cw_provider_reckoning_detail`
--

DROP TABLE IF EXISTS `tmp_cw_provider_reckoning_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_cw_provider_reckoning_detail` (
  `finance_check_date` datetime DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `is_ok_date` datetime DEFAULT NULL,
  `shipping_date` datetime DEFAULT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `provider_id` int(11) DEFAULT NULL,
  `trans_sn` varchar(50) DEFAULT NULL COMMENT 'trans_code',
  `pcat_id` int(10) DEFAULT '0' COMMENT '分类编号',
  `pcat_name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `cat_id` int(10) unsigned DEFAULT '0' COMMENT '分类编号',
  `cat_name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `provider_barcode` varchar(60) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_sn` varchar(100) DEFAULT NULL,
  `provider_productcode` varchar(50) DEFAULT NULL COMMENT '供应商货号',
  `color_id` int(11) DEFAULT NULL,
  `color_name` varchar(50) DEFAULT NULL,
  `size_id` int(11) DEFAULT NULL,
  `size_name` varchar(50) DEFAULT NULL,
  `product_number` int(11) DEFAULT NULL,
  `product_price` double DEFAULT '0',
  `coop_id` int(11) DEFAULT NULL,
  `product_cess` double DEFAULT NULL,
  `cost_price` double DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_payment_first`
--

DROP TABLE IF EXISTS `tmp_order_payment_first`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_payment_first` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `source_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单来源',
  `shipping_date` datetime DEFAULT NULL COMMENT '发货时间',
  `finance_date` datetime NOT NULL COMMENT '财审时间',
  `payment_date` datetime DEFAULT NULL COMMENT '创建日期',
  `deny_status` int(2) DEFAULT NULL,
  `pay_id` decimal(3,0) DEFAULT NULL,
  `pay_name` varchar(120) DEFAULT NULL,
  `paid_price` decimal(10,2) DEFAULT '0.00' COMMENT '已经支付金额',
  `payment_money` decimal(10,2) DEFAULT '0.00' COMMENT '支付金额',
  `shipping_fee` decimal(26,8) DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `index_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_payment_first_mark`
--

DROP TABLE IF EXISTS `tmp_order_payment_first_mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_payment_first_mark` (
  `order_id` int(10) DEFAULT NULL,
  `mark` int(2) DEFAULT NULL,
  KEY `index_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_payment_first_temp`
--

DROP TABLE IF EXISTS `tmp_order_payment_first_temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_payment_first_temp` (
  `shipping_fee` decimal(10,4) DEFAULT NULL,
  `order_id` int(10) DEFAULT NULL,
  `tag` int(1) DEFAULT NULL,
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_payment_first_temp2`
--

DROP TABLE IF EXISTS `tmp_order_payment_first_temp2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_payment_first_temp2` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `source_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单来源',
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime NOT NULL COMMENT '财审时间',
  `payment_date` datetime DEFAULT NULL COMMENT '创建日期',
  `deny_status` int(1) NOT NULL DEFAULT '0',
  `pay_id` tinyint(3) unsigned DEFAULT '0',
  `pay_name` varchar(120) DEFAULT '',
  `paid_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已经支付金额',
  `payment_money` decimal(13,2) DEFAULT NULL,
  `shipping_fee` decimal(26,8) DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `index_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_payment_first_temp3`
--

DROP TABLE IF EXISTS `tmp_order_payment_first_temp3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_payment_first_temp3` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `source_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单来源',
  `shipping_date` datetime DEFAULT NULL,
  `finance_date` datetime NOT NULL COMMENT '财审时间',
  `payment_date` datetime NOT NULL COMMENT '发货时间',
  `deny_status` int(1) NOT NULL DEFAULT '0',
  `pay_id` int(10) NOT NULL DEFAULT '0' COMMENT '支付方式',
  `pay_name` varchar(126) DEFAULT NULL,
  `paid_price` decimal(13,2) DEFAULT NULL,
  `order_shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费，=0不收运费 >0收运费',
  `payment_money` decimal(13,2) DEFAULT NULL,
  `shipping_fee` decimal(29,8) DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `index_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_payment_first_temp4`
--

DROP TABLE IF EXISTS `tmp_order_payment_first_temp4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_payment_first_temp4` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `payment_money` decimal(32,2) DEFAULT NULL,
  KEY `index_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_payment_second`
--

DROP TABLE IF EXISTS `tmp_order_payment_second`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_payment_second` (
  `order_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单ID',
  `source_id` int(10) NOT NULL DEFAULT '0' COMMENT '订单来源',
  `shipping_date` datetime NOT NULL COMMENT '发货时间',
  `finance_date` datetime NOT NULL COMMENT '财审时间',
  `payment_date` datetime DEFAULT NULL COMMENT '创建日期',
  `pay_id` decimal(3,0) DEFAULT NULL,
  `pay_name` varchar(120) DEFAULT NULL,
  `paid_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已经支付金额',
  `payment_money` decimal(10,2) DEFAULT '0.00' COMMENT '支付金额',
  `shipping_fee` decimal(26,8) DEFAULT NULL,
  `etl_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `index_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  UNIQUE KEY `cps_sn_UNIQUE` (`cps_sn`),
  KEY `cps_id` (`cps_id`)
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
  PRIMARY KEY (`cps_log_id`),
  KEY `index_cps_id` (`cps_id`),
  KEY `index_user` (`user_id`),
  KEY `index_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='cps订单日志表，跟踪cps订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_depot_in_main`
--

DROP TABLE IF EXISTS `ty_depot_in_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_depot_in_main` (
  `depot_in_id` int(10) NOT NULL COMMENT '自增ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品入库一级表';
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
  PRIMARY KEY (`depot_in_sub_id`),
  KEY `depot_in_id` (`depot_in_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商品出入库二级表';
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='出入库类型表';
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
  `batch_id` int(10) DEFAULT NULL,
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
  PRIMARY KEY (`depot_out_sub_id`),
  KEY `depot_out_id` (`depot_out_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品出库二级表';
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
  `source_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '原仓库id',
  `source_location_id` int(10) NOT NULL DEFAULT '0' COMMENT '原储位id',
  `dest_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '新仓库id',
  `dest_location_id` int(10) NOT NULL DEFAULT '0' COMMENT '新储位id',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  PRIMARY KEY (`exchange_leaf_id`),
  KEY `exchange_id` (`exchange_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调仓单管理三级表';
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
  PRIMARY KEY (`exchange_id`),
  KEY `exchange_code` (`exchange_code`),
  KEY `out_type` (`out_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调仓单管理主表';
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
  `dest_location_id` int(10) NOT NULL DEFAULT '0' COMMENT '新储位id',
  `memo` text NOT NULL COMMENT '备注',
  `source_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '源仓id',
  `dest_depot_id` int(10) NOT NULL DEFAULT '0' COMMENT '目标仓id',
  `shop_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `batch_id` int(10) NOT NULL DEFAULT '0' COMMENT '批次ID,20130218添加',
  PRIMARY KEY (`exchange_sub_id`),
  KEY `exchange_id` (`exchange_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调仓单管理二级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_change_info`
--

DROP TABLE IF EXISTS `ty_order_change_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_change_info` (
  `change_id` int(10) NOT NULL COMMENT '换货单编号',
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
  `odd` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否异常单 1:异常单 0:正常单'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品换货单';
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
  PRIMARY KEY (`order_id`),
  KEY `index_order` (`order_id`),
  KEY `index_source` (`source_id`),
  KEY `index_user` (`user_id`),
  KEY `index_sn` (`order_sn`),
  KEY `index_ship` (`shipping_id`),
  KEY `index_pay` (`pay_id`),
  KEY `index_ship_date` (`shipping_date`),
  KEY `index_ship_true` (`shipping_true`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='订单主表';
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
  `bank_code` varchar(20) NOT NULL COMMENT '银行代码',
  `payment_account` varchar(255) DEFAULT NULL COMMENT '支付帐号',
  `payment_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `trade_no` varchar(255) DEFAULT NULL COMMENT '交易号',
  `payment_remark` varchar(255) DEFAULT NULL COMMENT '支付备注',
  `payment_admin` int(10) DEFAULT NULL COMMENT '创建人id int11改成int10 20130218修改',
  `payment_date` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`payment_id`),
  KEY `orderid_inx` (`order_id`),
  KEY `payid_inx` (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单支付记录表';
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
  `discount_type` int(1) NOT NULL DEFAULT '0' COMMENT '折扣类型0.未折扣 1.限时抢购 2.礼包 3.手工更改 4.赠品',
  `consign_num` int(10) NOT NULL DEFAULT '0' COMMENT '代销库存数量',
  `consign_mark` int(10) NOT NULL DEFAULT '0' COMMENT '虚库数量，仅作标记,转实际库存是不变化',
  PRIMARY KEY (`op_id`),
  KEY `index_order_id` (`order_id`),
  KEY `index_sku` (`product_id`,`color_id`,`size_id`),
  KEY `index_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 COMMENT='订单商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_order_return_info`
--

DROP TABLE IF EXISTS `ty_order_return_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_order_return_info` (
  `return_id` int(10) NOT NULL COMMENT '退货单编号',
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
  PRIMARY KEY (`return_id`),
  KEY `index_return` (`return_id`),
  KEY `index_order` (`order_id`),
  KEY `index_sn` (`return_sn`),
  KEY `index_user` (`user_id`),
  KEY `index_return_status` (`return_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`rp_id`),
  KEY `index_ret` (`return_id`),
  KEY `index_sku` (`product_id`,`color_id`,`size_id`),
  KEY `index_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='订单来源表';
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
  `brand_info` varchar(255) NOT NULL COMMENT '品牌简介',
  `brand_story` text NOT NULL COMMENT '品牌故事',
  `brand_initial` varchar(1) NOT NULL COMMENT '品牌首字母',
  `sort_order` int(3) unsigned NOT NULL DEFAULT '0' COMMENT '排序号',
  `is_use` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用',
  `flag_id` int(10) NOT NULL DEFAULT '0' COMMENT '国旗',
  `logo_75_34` varchar(255) NOT NULL COMMENT '75×34',
  `logo_110_50` varchar(255) NOT NULL COMMENT '110×50',
  `logo_160_73` varchar(255) NOT NULL COMMENT '160×73',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`brand_id`),
  KEY `brand_name` (`brand_name`),
  KEY `is_show` (`is_use`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='商品品牌基础表';
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
  PRIMARY KEY (`category_id`),
  KEY `index_id` (`category_id`),
  KEY `index_p_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品分类基础表';
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
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`color_id`),
  KEY `index_id` (`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1144 DEFAULT CHARSET=utf8 COMMENT='商品颜色基础表';
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2641 DEFAULT CHARSET=utf8 COMMENT='商品成本价格表';
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
  `promote_start_date` datetime NOT NULL COMMENT '促销开始时间',
  `promote_end_date` datetime NOT NULL COMMENT '促销结束时间',
  `keywords` varchar(255) NOT NULL COMMENT '关键字',
  `product_desc` text NOT NULL COMMENT '商品描述',
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
  `product_sex` int(1) NOT NULL COMMENT '1男2女',
  `unit_name` varchar(50) NOT NULL COMMENT '计量单位id,直接写中文单位',
  `goods_carelabel` varchar(255) NOT NULL COMMENT '洗标，多个洗标用逗号分隔',
  `create_admin` int(10) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `flag_id` int(10) NOT NULL DEFAULT '0' COMMENT '国旗编号',
  `model_id` int(10) NOT NULL DEFAULT '0' COMMENT '模特编号',
  `size_image_id` int(10) NOT NULL COMMENT '对应尺寸详情图',
  `size_image` varchar(255) NOT NULL DEFAULT '' COMMENT '针对该商品的尺寸详情图',
  `is_gifts` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否赠品，0不是',
  `is_audit` int(1) NOT NULL DEFAULT '0' COMMENT '是否审核，0未审核',
  `audit_admin` int(10) NOT NULL DEFAULT '0' COMMENT '审核人',
  `audit_date` datetime NOT NULL COMMENT '审核时间',
  `is_onsale` int(1) NOT NULL DEFAULT '0' COMMENT '是否上下架，0为上架',
  `is_stop` int(1) NOT NULL DEFAULT '0' COMMENT '是否停止订货，0未停止',
  `stop_admin` int(10) NOT NULL DEFAULT '0' COMMENT '停止订货人',
  `stop_date` datetime NOT NULL COMMENT '停止订货时间',
  `min_month` int(11) NOT NULL DEFAULT '0' COMMENT '最小岁段(月)',
  `max_month` int(11) NOT NULL DEFAULT '0' COMMENT '最大岁段(月)',
  `provider_id` int(10) DEFAULT NULL COMMENT '供应商ID',
  `is_single_order` int(1) DEFAULT '0' COMMENT '本商品是否单独生成订单 0-不单独；1-单独.20130218添加',
  `is_cod` int(1) DEFAULT '1' COMMENT '本商品是否支持COD 0-不支持；1-支持.20130218添加',
  `related_id` int(10) DEFAULT '0' COMMENT '关联商品ID，默认为0。0的业务意义即本身 20130218添加',
  `product_desc_additional` text COMMENT '商品附加详细信息,有7个属性,json格式：desc_composition(成分)、desc_dimensions(尺寸规格)、desc_material(材质)、desc_waterproof(防水性)、desc_crowd(适合人群)、desc_notes(温馨提示)、desc_expected_shipping_date(预计发货日期)。20130218添加',
  `product_desc_detail` text COMMENT '用于商品细节展示 20130218添加',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `goods_sn` (`product_sn`),
  KEY `idx_min_month` (`min_month`),
  KEY `idx_max_month` (`max_month`),
  KEY `ty_index` (`product_id`),
  KEY `index_brand` (`brand_id`),
  KEY `index_cat` (`category_id`),
  KEY `index_provider` (`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2651 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='商品信息主表';
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
  `provider_code` varchar(20) NOT NULL COMMENT '供应商代码。20130218添加',
  `provider_cooperation` int(10) NOT NULL DEFAULT '2' COMMENT '供应商合作方式，关联ty_product_cooperation.cooperation_id。20130218添加',
  PRIMARY KEY (`provider_id`),
  KEY `provider_code` (`provider_code`),
  KEY `index_id` (`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='商品供应商基础表';
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
  PRIMARY KEY (`season_id`),
  KEY `index_id` (`season_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商品季节基础表';
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
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`size_id`),
  KEY `index_id` (`size_id`)
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=utf8 COMMENT='商品尺寸基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_product_sub`
--

DROP TABLE IF EXISTS `ty_product_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_product_sub` (
  `sub_id` int(10) NOT NULL DEFAULT '0' COMMENT '自增编号',
  `product_id` int(10) NOT NULL COMMENT '商品ID',
  `color_id` int(10) NOT NULL COMMENT '颜色ID',
  `size_id` int(10) NOT NULL COMMENT '尺寸ID',
  `gl_num` int(10) NOT NULL DEFAULT '0' COMMENT '实际库存',
  `is_on_sale` int(1) NOT NULL DEFAULT '0' COMMENT '0为下架,1为上架',
  `consign_num` int(10) NOT NULL DEFAULT '-1' COMMENT '代销库存:-2--不限量代销;-1:不代销;>=0限量代销',
  `wait_num` int(11) NOT NULL DEFAULT '0' COMMENT '被占用的代销库存',
  `sort_order` int(10) NOT NULL DEFAULT '0' COMMENT '排序号',
  `provider_barcode` varchar(60) NOT NULL COMMENT '供应商条码 修改为非空 20130218修改',
  `create_admin` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `is_pic` int(1) NOT NULL DEFAULT '0' COMMENT '是否拍摄 0-未拍摄；1-已拍摄 20130218添加',
  `lock_num` int(10) NOT NULL DEFAULT '0' COMMENT '锁定库存数量，动态变化 20130218添加',
  `lock_num_mark` int(10) NOT NULL DEFAULT '0' COMMENT '锁定库存数量 仅作标记 20130218添加',
  KEY `index_p_id` (`product_id`),
  KEY `index_sku` (`product_id`,`color_id`,`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`region_id`),
  KEY `index_id` (`region_id`),
  KEY `index_p_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3621 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='地区管理基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_rush_info`
--

DROP TABLE IF EXISTS `ty_rush_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_rush_info` (
  `rush_id` int(10) NOT NULL DEFAULT '0' COMMENT '限时抢购编号',
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
  KEY `index_rush_id` (`rush_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_rush_product`
--

DROP TABLE IF EXISTS `ty_rush_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_rush_product` (
  `rec_id` int(10) NOT NULL DEFAULT '0' COMMENT '自增编号',
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
  KEY `ind_rush_id` (`rush_id`),
  KEY `ind_p_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  KEY `batch_id` (`batch_id`),
  KEY `inx_ship` (`shipping_id`)
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
  PRIMARY KEY (`shipping_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='快递方式管理基础表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_transaction_info`
--

DROP TABLE IF EXISTS `ty_transaction_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_transaction_info` (
  `transaction_id` int(10) NOT NULL,
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
  PRIMARY KEY (`transaction_id`),
  KEY `index_sn` (`trans_sn`),
  KEY `index_product` (`product_id`),
  KEY `index_status` (`trans_status`),
  KEY `index_type` (`trans_type`),
  KEY `index_sub` (`sub_id`),
  KEY `index_finance_time` (`finance_check_date`),
  KEY `index_batch` (`batch_id`),
  KEY `index_color` (`color_id`),
  KEY `index_size` (`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存事务处理表';
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
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `mobile_phone` (`mobile`),
  KEY `index_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户基本信息表';
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
  KEY `voucher_voucher_sn_index` (`voucher_sn`),
  KEY `inx_end_time` (`end_date`),
  KEY `inx_start_time` (`start_date`),
  KEY `voucher_userid_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='现金券活动具体券号表';
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

--
-- Current Database: `mmt_report`
--

-- CREATE DATABASE /*!32312 IF NOT EXISTS*/ `mmt_report` /*!40100 DEFAULT CHARACTER SET utf8 */;

-- USE `mmt_report`;

--
-- Table structure for table `ty_check_email_user`
--

DROP TABLE IF EXISTS `ty_check_email_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_check_email_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_type` smallint(6) DEFAULT '1',
  `user_status` smallint(6) DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_check_result`
--

DROP TABLE IF EXISTS `ty_check_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_check_result` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `qt_type` smallint(4) DEFAULT NULL,
  `qt_desc` mediumtext,
  `is_report` tinyint(4) DEFAULT NULL,
  `add_aid` int(11) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `report_aid` int(11) DEFAULT NULL,
  `report_time` datetime DEFAULT NULL,
  `resolve_aid` int(11) DEFAULT NULL,
  `resolve_time` datetime DEFAULT NULL,
  `sort` smallint(6) DEFAULT NULL,
  `st_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_provider_prepay`
--

DROP TABLE IF EXISTS `ty_provider_prepay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_provider_prepay` (
  `prepay_key` varchar(200) NOT NULL DEFAULT '',
  `prepay_money` double(20,4) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`prepay_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_sys_admin_role`
--

DROP TABLE IF EXISTS `ty_sys_admin_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_sys_admin_role` (
  `admin_id` int(10) NOT NULL COMMENT '管理用户ID',
  `role_id` int(10) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`admin_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_sys_resource`
--

DROP TABLE IF EXISTS `ty_sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_sys_resource` (
  `resource_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(100) NOT NULL COMMENT '资源名称',
  `resource_type` varchar(20) NOT NULL COMMENT '资源类型：menu菜单；resource资源',
  `resource_code` varchar(50) DEFAULT NULL COMMENT '资源代码',
  `resource_url` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `resource_order` int(10) DEFAULT NULL COMMENT '菜单顺序',
  `status` varchar(3) DEFAULT NULL COMMENT '状态：000停用；001启用',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `resource_owner_id` int(10) DEFAULT NULL COMMENT '资源所属菜单ID',
  `create_date` datetime DEFAULT NULL,
  `resource_parent_id` int(10) DEFAULT NULL COMMENT '父菜单ID',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_sys_role`
--

DROP TABLE IF EXISTS `ty_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_sys_role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) NOT NULL COMMENT '角色代码',
  `status` varchar(3) DEFAULT NULL COMMENT '状态：000停用；001启用',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ty_sys_role_resource`
--

DROP TABLE IF EXISTS `ty_sys_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ty_sys_role_resource` (
  `role_id` int(10) NOT NULL,
  `resource_id` int(10) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-09 15:55:59
