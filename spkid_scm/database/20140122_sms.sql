ALTER TABLE `ty_scm_sms` 
	ADD COLUMN fail_times SMALLINT NOT NULL DEFAULT 0 COMMENT '发送失败次数';
ALTER TABLE `ty_scm_sms` 
	MODIFY COLUMN `status` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '状态:0-草稿;1-未审核;2-已审核;3-已发送;4-已作废;5-已结算;6-发送中';