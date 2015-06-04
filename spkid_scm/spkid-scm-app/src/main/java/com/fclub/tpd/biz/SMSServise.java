package com.fclub.tpd.biz;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.SMS;

public interface SMSServise {
	void save(SMS sms);

	// SMS querySms();
	SMS get(Serializable id);

	/**
	 * 分页
	 * 
	 * @param page
	 * @param sms
	 * @return
	 */
	Page<SMS> findPage(Page<SMS> page, SMS sms);

	/**
	 * 保存或提交任务
	 * 
	 * @param list
	 * @param providerId
	 */
	void add(List<String> list, SMS sms, String submitType);

	/**
	 * 删除任务
	 * 
	 * @param list
	 * @param providerId
	 */
	void delete(SMS sms);

	/**
	 * 审核任务
	 * 
	 * @param list
	 * @param providerId
	 */
	boolean check(SMS sms);

	/**
	 * 驳回任务
	 * 
	 * @param list
	 * @param providerId
	 */
	void reject(SMS sms);

	/**
	 * 作废任务
	 * 
	 * @param list
	 * @param providerId
	 */
	void cancel(SMS sms, Integer adminId);

	/**
	 * 查看详情
	 * 
	 * @param smsId
	 * @return
	 */
	List<String> queryMobileList(Integer smsId);

	/**
	 * 是否有历史记录
	 * 
	 * @param providerId
	 * @return
	 */
	Boolean hasHistory(Integer providerId);

	/**
	 * 查询历史记录数量
	 * 
	 * @param providerId
	 * @return
	 */
	public Integer queryHistoryCount(Integer providerId);

	/**
	 * 查询历史记录n条
	 * 
	 * @param providerId
	 * @return
	 */
	public List<String> queryHistoryLimitList(Map<String, Object> map);
}
