-- 账户变动添加状态标示
ALTER TABLE `ty_provider_account_log`
	ADD COLUMN operate_status TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0-初始;1-成功;';

ALTER TABLE `ty_scm_sms` 
	ADD COLUMN memo VARCHAR(100) DEFAULT NULL COMMENT '备注';