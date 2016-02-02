<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');


//是否支持货到付款
defined('SYS_USE_COD') || define('SYS_USE_COD', '2'); // 是否支持货到付款
defined('SYS_USE_COD_YES') || define('SYS_USE_COD_YES', '1'); // 支持
defined('SYS_USE_COD_NO') || define('SYS_USE_COD_NO', '2'); // 不支持

//可用券数
defined('COUPON_MAX_NUM') || define('COUPON_MAX_NUM', '1500'); 

//商品款号生成规则
defined('PRODUCT_SN_RULE') || define('PRODUCT_SN_RULE', 'cat_rand'); // 商品款号生成规则
defined('PRODUCT_SN_RULE_BRANDYEAR') || define('PRODUCT_SN_RULE_BRANDYEAR', 'brandyear'); // 品牌缩写+年(3位)+N位随机数
defined('PRODUCT_SN_RULE_CAT_RAND') || define('PRODUCT_SN_RULE_CAT_RAND', 'cat_rand'); // 分类编号+N位随机数
defined('PRODUCT_SN_RULE_WRITE') || define('PRODUCT_SN_RULE_WRITE', 'write'); // 手工输入

//商品款号随机数长度
defined('PRODUCT_RAND_LENGTH') || define('PRODUCT_RAND_LENGTH', '7'); // 商品款号随机数长度
defined('PRODUCT_RAND_LENGTH_SEVEN') || define('PRODUCT_RAND_LENGTH_SEVEN', '7'); // 7位
defined('PRODUCT_RAND_LENGTH_SIX') || define('PRODUCT_RAND_LENGTH_SIX', '6'); // 6位
defined('PRODUCT_RAND_LENGTH_FIVE') || define('PRODUCT_RAND_LENGTH_FIVE', '5'); // 5位

//三方发货合作方式
defined('THIRD_DELIVERY_COOP_ID') || define('THIRD_DELIVERY_COOP_ID', '3'); 

//首页默认产品个数
defined('MOBILE_INDEX_PRODUCT_NUM') || define('MOBILE_INDEX_PRODUCT_NUM', '8'); 

//手机首页最多显示产品数
defined('MOBILE_INDEX_MAX_PRODUCT_NUM') || define('MOBILE_INDEX_MAX_PRODUCT_NUM', '40'); 

//打印发票天数
defined('INVOICE_PRINT_DAYS') || define('INVOICE_PRINT_DAYS', '15'); 

//商品价格不算免邮字段
defined('FREE_FEE_EXCLUDE_PRODUCT_FIELD') || define('FREE_FEE_EXCLUDE_PRODUCT_FIELD', '(is_best=1 or is_offcode=1)'); 

//自营店铺
defined('SELF_SHOP') || define('SELF_SHOP', '1'); 

//订单发放积分天数
defined('SEND_ORDER_POINT_DAYS') || define('SEND_ORDER_POINT_DAYS', '15'); 

//字典类型
$dict_types = array (
  'diy_field' => '自定义字段',
  'cust_type' => '客户类型',
  'unit' => '单位',
);
defined('DICT_TYPES_DIY_FIELD') || define('DICT_TYPES_DIY_FIELD', 'diy_field'); // 自定义字段
defined('DICT_TYPES_CUST_TYPE') || define('DICT_TYPES_CUST_TYPE', 'cust_type'); // 客户类型
defined('DICT_TYPES_UNIT') || define('DICT_TYPES_UNIT', 'unit'); // 单位

//商品表字段对照
defined('PRODUCT_INFO_FIELD_MAP') || define('PRODUCT_INFO_FIELD_MAP', 'category_id, product_sn, product_name,brand_id,subhead, package_name,desc_material,desc_waterproof,desc_crowd,desc_expected_shipping_date,desc_composition,desc_dimensions,desc_use_explain,desc_function_exlain,desc_notes,register_id ,unit_name ,shop_id ,genre_id,flag_id ,product_weight ,size_image ,price_show ,limit_num,content_source ,is_stop ,provider_id ,provider_productcode ,brand_id'); 

//订单客户信息对照
defined('SYS_ORDER_CLIENT_MAP') || define('SYS_ORDER_CLIENT_MAP', 'name,mobile_phone,field1,field2,field3,field4,field5,field6,field7,field8,field9,field10'); 

//默认注册证号
defined('DEFAULT_REGISTER_NO') || define('DEFAULT_REGISTER_NO', '20'); 

//课程类型ID
defined('GENRE_COURSE_ID') || define('GENRE_COURSE_ID', '2'); 

//课程默认品牌
defined('DEFAULT_BRAND_ID') || define('DEFAULT_BRAND_ID', '112'); 
