-- 短信发送
DROP TABLE IF EXISTS ty_scm_sms;
CREATE TABLE ty_scm_sms (
  	sms_id SMALLINT NOT NULL AUTO_INCREMENT,
  	provider_id SMALLINT NOT NULL DEFAULT 0 COMMENT '供应商ID',
  	content TEXT COMMENT '短信内容',
  	create_time DATETIME NOT NULL COMMENT '创建时间',
  	commit_time DATETIME NOT NULL COMMENT '提交时间',
  	check_time DATETIME COMMENT '审核时间',
  	check_admin SMALLINT DEFAULT NULL COMMENT '审核人',
  	send_time DATETIME COMMENT '发送时间',
  	status TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0-草稿;1-未审核;2-已审核;3-已发送;4-已作废;',
  	PRIMARY KEY (sms_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='第三方平台短信任务表';

DROP TABLE IF EXISTS ty_scm_sms_send;
CREATE TABLE ty_scm_sms_send (
	ss_id SMALLINT NOT NULL AUTO_INCREMENT,
	sms_id SMALLINT NOT NULL DEFAULT 0 COMMENT '短信任务ID',
  	mobile VARCHAR(11) NOT NULL DEFAULT '' COMMENT '手机号码',
  	source_type TINYINT NOT NULL DEFAULT 0 COMMENT '号码来源:0-供应商导入;1-MT平台用户;',
  	PRIMARY KEY (ss_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='第三方平台短信发送表';

DROP TABLE IF EXISTS ty_scm_sms_user;
CREATE TABLE ty_scm_sms_user (
	su_id SMALLINT NOT NULL AUTO_INCREMENT,
	provider_id SMALLINT NOT NULL DEFAULT 0 COMMENT '供应商ID',
  	mobile VARCHAR(11) NOT NULL DEFAULT '' COMMENT '手机号码',
  	update_time DATETIME NOT NULL COMMENT '更新时间',
  	PRIMARY KEY (su_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='第三方平台供应商短信用户表';


ALTER TABLE ty_scm_sms_send   
	ADD  UNIQUE INDEX mobile_index (sms_id, mobile);
ALTER TABLE ty_scm_sms_user   
	ADD  UNIQUE INDEX mobile_index (provider_id, mobile);


-- 供应商添加自定义店铺
ALTER TABLE ty_product_provider   
	ADD COLUMN provider_ad TEXT COMMENT '自定义店铺' AFTER scm_order_process_mail,
  	ADD COLUMN provider_ad_sdate DATETIME DEFAULT NULL COMMENT '自定义店铺开始时间' AFTER provider_ad,
  	ADD COLUMN provider_ad_edate DATETIME DEFAULT NULL COMMENT '自定义店铺结束时间' AFTER provider_ad_sdate;
  	
  	


  	

-- 供应商充值/提现
ALTER TABLE ty_product_provider 
	ADD COLUMN account_balance DECIMAL(10,3) NOT NULL DEFAULT 0.000 COMMENT '账户余额',
	ADD COLUMN sms_price DECIMAL(10,3) NOT NULL DEFAULT 0.000 COMMENT '短信发送单价';

ALTER TABLE ty_scm_sms 
	ADD COLUMN sms_price DECIMAL(10,3) NOT NULL DEFAULT 0.000 COMMENT '短信发送单价',
	MODIFY COLUMN STATUS TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0-草稿;1-未审核;2-已审核;3-已发送;4-已作废;5-已结算;';

DROP TABLE IF EXISTS ty_provider_account_log;
CREATE TABLE ty_provider_account_log (
	log_id INT UNSIGNED AUTO_INCREMENT COMMENT '日志ID',
	provider_id INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '供应商ID',
	change_type TINYINT NOT NULL DEFAULT 0 COMMENT '变动类型:0-充值;1-提现;2-短信消费;',
	change_price DECIMAL(10,3) NOT NULL DEFAULT 0.000 COMMENT '变动金额:充值-正数;消费-负数;',
	related_id INT UNSIGNED COMMENT '关联ID:如短信发送任务ID',
	operate_aid INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '操作人ID:-1-系统;',
	operate_time DATETIME NOT NULL COMMENT '操作时间',
	PRIMARY KEY (log_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='供应商账户变动记录表';