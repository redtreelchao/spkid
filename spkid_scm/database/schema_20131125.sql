/* mysql-5.5 databse schema for spkid scm */

-- database
USE mammytree;

ALTER TABLE ty_product_color 
	MODIFY COLUMN color_name VARCHAR(50) NOT NULL COMMENT '颜色名称';
ALTER TABLE ty_product_size 
	MODIFY COLUMN size_name VARCHAR(50) NOT NULL COMMENT '尺寸名称';	

-- provider
ALTER TABLE ty_product_provider 
	ADD COLUMN provider_cess DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '税率',
	ADD COLUMN user_name VARCHAR(50) COMMENT '登陆用户名',
	ADD COLUMN password VARCHAR(50) COMMENT '登陆密码',
	ADD COLUMN provider_status VARCHAR(50) COMMENT '登陆状态',
	ADD COLUMN official_address VARCHAR(255) COMMENT '公司地址',
	ADD COLUMN scm_responsible_user VARCHAR(20) COMMENT '负责人',
	ADD COLUMN scm_responsible_phone VARCHAR(20) COMMENT '负责手机',
	ADD COLUMN scm_responsible_qq VARCHAR(20) COMMENT '负责QQ',
	ADD COLUMN scm_responsible_mail VARCHAR(50) COMMENT '负责email',
	ADD COLUMN scm_order_process_user VARCHAR(20) COMMENT '订单处理联系人',
	ADD COLUMN scm_order_process_phone VARCHAR(20) COMMENT '订单处理人电话',
	ADD COLUMN scm_order_process_qq VARCHAR(20) COMMENT '订单处理人QQ',
	ADD COLUMN scm_order_process_mail VARCHAR(50) COMMENT '订单处理人email',
	ADD COLUMN admin_id SMALLINT DEFAULT 0 COMMENT '关联管理员ID';

-- provider brand
ALTER TABLE ty_provider_brand 
	ADD COLUMN commission VARCHAR(20) COMMENT '扣点';

ALTER TABLE ty_product_info 
	ADD COLUMN scm_product_id INT DEFAULT 0 comment '供应商商品ID';
	
ALTER TABLE ty_product_sub 
	ADD COLUMN scm_provider_barcode varchar(64) comment '供应商条码(第三方平台)';

-- notice
DROP TABLE IF EXISTS ty_scm_notice;
CREATE TABLE ty_scm_notice(
  	`notice_id` INT(11) NOT NULL AUTO_INCREMENT,
  	`notice_title` VARCHAR(50) DEFAULT NULL COMMENT '公告标题',
  	`content` VARCHAR(1000) DEFAULT NULL COMMENT '公告内容',
  	`audit_user` INT(11) DEFAULT NULL COMMENT '审核人',
  	`audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  	`is_delete` TINYINT(1) DEFAULT NULL COMMENT '删除标识',
  	`create_user` INT(11) DEFAULT NULL,
  	`create_time` DATETIME DEFAULT NULL,
  	`update_user` INT(11) DEFAULT NULL,
  	`update_time` DATETIME DEFAULT NULL,
  	PRIMARY KEY (`notice_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='第三方平台公告';

-- work order
DROP TABLE IF EXISTS ty_scm_work_order;
CREATE TABLE ty_scm_work_order (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台工单';

-- shipping wave
DROP TABLE IF EXISTS ty_scm_shipping_wave;
CREATE TABLE ty_scm_shipping_wave (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '第三方平台波次';

-- shipping packet
DROP TABLE IF EXISTS ty_scm_shipping_packet;
CREATE TABLE ty_scm_shipping_packet (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '第三方平台发货订单';

-- bcs image
DROP TABLE IF EXISTS ty_scm_bcs_imp;
CREATE TABLE ty_scm_bcs_imp (
   	imp_id int(11) not null auto_increment,
   	brand_id int(11) not null comment '品牌ID',
   	cat_id int(11) comment '分类ID',
   	sex varchar(2) comment '性别(m-男,w-女,a-中性)',
   	imp_status int(1) not null default 0 comment '导入状态(0-失败,1-成功)',
   	image_url varchar(50) comment '图片路径',
   	create_user int(11) COMMENT '创建人',
   	create_time datetime COMMENT '创建时间',
   	primary key (imp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '第三方平台品牌尺寸图导入';

-- scm product info
DROP TABLE IF EXISTS ty_scm_product_info;
CREATE TABLE ty_scm_product_info (
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
  `limit_num` int(2) NOT NULL DEFAULT '0' COMMENT '每ID限购数量，不超过99，默认0不限制限购数量',
  `diagram_code` varchar(128) DEFAULT NULL COMMENT '尺寸示意图编码',
  `tpd_goods_status` varchar(1) DEFAULT NULL COMMENT '商品状态(0-草稿,1-待审核,2-已审核)',
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
) ENGINE=InnoDB AUTO_INCREMENT=61280 DEFAULT CHARSET=utf8 COMMENT='第三方平台商品表';

-- scm product sub
DROP TABLE IF EXISTS ty_scm_product_sub;
CREATE TABLE ty_scm_product_sub (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台商品二级表';

-- scm product gallery
DROP TABLE IF EXISTS ty_scm_product_gallery;
CREATE TABLE ty_scm_product_gallery (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台商品图片表';

-- scm product gallery
DROP TABLE IF EXISTS ty_scm_product_type_link;
CREATE TABLE ty_scm_product_type_link (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_goods_id` (`goods_id`),
  KEY `idx_type_id` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '第三方平台';

-- product batch import
DROP TABLE IF EXISTS ty_scm_product_batch_import;
CREATE TABLE ty_scm_product_batch_import (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品批量导入列表';

-- product import list
DROP TABLE IF EXISTS ty_scm_product_import_list;
CREATE TABLE ty_scm_product_import_list (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品批量导入经过列表';
