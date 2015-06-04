/* mysql-5.5 databse schema for spkid scm */

-- database
USE mammytree;

ALTER TABLE ty_product_provider 
	DROP COLUMN admin_id;

ALTER TABLE ty_provider_brand  
	ADD COLUMN commission_history TEXT DEFAULT NULL COMMENT '扣点历史：json格式';