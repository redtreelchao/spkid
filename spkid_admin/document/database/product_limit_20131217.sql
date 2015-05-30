ALTER TABLE ty_product_info 
	ADD COLUMN limit_num SMALLINT DEFAULT 0 COMMENT '限购数量' AFTER , -- size_table
	ADD COLUMN limit_day SMALLINT DEFAULT 0 COMMENT '限购天数' AFTER limit_num;

ALTER TABLE ty_scm_product_info
	MODIFY COLUMN limit_num  SMALLINT DEFAULT 0 COMMENT '限购数量',
	ADD COLUMN limit_day SMALLINT DEFAULT 0 COMMENT '限购天数' AFTER limit_num;
