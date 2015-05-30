-- 2013-11-19
USE spkid;

-- apply return 
DROP TABLE IF EXISTS ty_apply_return_info;
CREATE TABLE ty_apply_return_info (
    apply_id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
    order_id mediumint(8) NOT NULL COMMENT '订单id',
    user_id decimal(10,0) NOT NULL COMMENT '用户id',
    provider_id mediumint(8) DEFAULT NULL COMMENT '供应商id',
    shipping_name varchar(45) NOT NULL COMMENT '快递名称',
    invoice_no varchar(45) NOT NULL COMMENT '运单号',
    sent_user_name varchar(45) NOT NULL COMMENT '寄件人姓名',
    mobile varchar(45) DEFAULT NULL COMMENT '寄件人手机号',
    tel varchar(45) DEFAULT NULL COMMENT '寄件人电话',
    shipping_fee decimal(10,2) NOT NULL COMMENT '运费',
    back_address varchar(255) NOT NULL COMMENT '退回地址',
    product_number int(4) NOT NULL COMMENT '总件数',
    apply_status tinyint(2) NOT NULL DEFAULT '0' COMMENT '申请状态 0:待处理 1:处理中 2:已处理 3:已取消 4:拒收(即全部退货)',
    provider_status tinyint(2) NOT NULL DEFAULT '0' COMMENT '供应商状态0未审核 1 正常审核 2 非正常审核',
    order_type tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单类型 0 普通订单 1 第三方直发订单',
    apply_time datetime NOT NULL COMMENT '申请时间',
    cancel_time datetime DEFAULT NULL COMMENT '取消时间',
    cancel_reason varchar(500) DEFAULT NULL COMMENT '取消理由',
    cancel_admin_id int(11) NOT NULL DEFAULT '0' COMMENT '取消人',
    PRIMARY KEY (apply_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自助退货表';

DROP TABLE IF EXISTS ty_apply_return_product;
CREATE TABLE ty_apply_return_product (
    rec_id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
    apply_id mediumint(8) NOT NULL COMMENT '申请退货id',
    product_id mediumint(8) NOT NULL COMMENT '商品ID',
    color_id int(11) NOT NULL COMMENT '颜色ID',
    size_id int(11) NOT NULL COMMENT '尺寸ID',
    product_price decimal(10,2) NOT NULL COMMENT '商品价格',
    product_sn varchar(60) DEFAULT NULL COMMENT '商品SN',
    product_name varchar(255) DEFAULT NULL COMMENT '商品名称',
    product_number int(4) NOT NULL COMMENT '商品数量',
    return_reason tinyint(4) NOT NULL COMMENT '退货理由0:尺寸偏大 1:尺寸偏小 2:款式不喜欢 3:配送错误 4:其他 5:商品质量有问题',
    description text COMMENT '问题描述',
    img VARCHAR(255) DEFAULT NULL COMMENT '退货图片',
    PRIMARY KEY (rec_id),
    KEY index_apply_id (apply_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请退货商品表';

DROP TABLE IF EXISTS ty_apply_return_suggest;
CREATE TABLE ty_apply_return_suggest (
    rec_id mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
    apply_id mediumint(8) NOT NULL COMMENT '自助退货ID',
    suggest_type tinyint(4) NOT NULL COMMENT '意见类型 0:客服意见 1:供应商正常意见 2:供应商非正常意见 3:其他意见',
    suggest_content text COMMENT '意见内容',
    create_id mediumint(8) NOT NULL COMMENT '创建人ID',
    create_date datetime NOT NULL COMMENT '创建日期',
    PRIMARY KEY (rec_id),
    KEY index_apply_id (apply_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请退货意见表';

ALTER TABLE ty_order_return_info 
    ADD COLUMN apply_id MEDIUMINT(8) NULL COMMENT '申请退货单号' AFTER is_ok_date;

-- add columns to ty_voucher_campaign
ALTER TABLE ty_voucher_campaign 
    ADD COLUMN provider TEXT NOT NULL COMMENT '供应商列表' AFTER category;

-- add columns to ty_product_provider
ALTER TABLE ty_product_provider 
    ADD COLUMN logo VARCHAR(255) DEFAULT '' COMMENT 'LOGO图片',
    ADD COLUMN display_name VARCHAR(128) DEFAULT '' COMMENT '前台显示名称',
    ADD COLUMN return_address VARCHAR(255) DEFAULT '' COMMENT '退货地址',
    ADD COLUMN return_postcode VARCHAR(32) DEFAULT '' COMMENT '退货邮编',
    ADD COLUMN return_consignee VARCHAR(32) DEFAULT '' COMMENT '退货收货人',
    ADD COLUMN return_mobile VARCHAR(16) DEFAULT '' COMMENT '退货收货人手机';

-- create table ty_spkid_order_track
DROP TABLE IF EXISTS ty_spkid_order_track;
CREATE TABLE ty_spkid_order_track(
    track_id SMALLINT UNSIGNED AUTO_INCREMENT COMMENT '跟踪ID',
    order_sn VARCHAR(128) UNIQUE NOT NULL DEFAULT '' COMMENT '后台订单号',
    track_order_sn VARCHAR(128) UNIQUE NOT NULL COMMENT '天猫订单号',
    track_shipping_sn VARCHAR(128) UNIQUE COMMENT '天猫物流单号',
    track_create_aid SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建人ID',
    track_create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY(track_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '天猫订单跟踪表';

-- create table ty_spkid_return_track
DROP TABLE IF EXISTS ty_spkid_return_track;
CREATE TABLE ty_spkid_return_track(
    track_id SMALLINT UNSIGNED AUTO_INCREMENT COMMENT '跟踪ID',
    apply_id VARCHAR(128) UNIQUE NOT NULL DEFAULT '' COMMENT '后台自助退货申请ID',
    track_return_sn VARCHAR(128) UNIQUE NOT NULL COMMENT '天猫退单号',
    track_shipping_name VARCHAR(16) NOT NULL COMMENT '退货物流公司',
    track_shipping_sn VARCHAR(128) UNIQUE COMMENT '天猫退货物流单号',
    track_create_aid SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建人ID',
    track_create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY(track_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '天猫退单跟踪表';