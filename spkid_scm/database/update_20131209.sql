/* mysql-5.5 databse schema for spkid scm */

-- database
USE mammytree;

ALTER TABLE ty_product_provider 
	MODIFY COLUMN provider_status TINYINT NOT NULL DEFAULT 0 COMMENT '登陆状态:0-正常，1-锁定';
	
ALTER TABLE ty_product_color 
	MODIFY COLUMN color_name VARCHAR(50) UNIQUE NOT NULL COMMENT '颜色名称';
ALTER TABLE ty_product_size 
	MODIFY COLUMN size_name VARCHAR(50) UNIQUE NOT NULL COMMENT '尺寸名称';