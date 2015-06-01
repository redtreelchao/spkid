package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.SMS;

public interface SMSMapper extends BaseMapper<SMS> {
	SMS querySms();

	void insertSend(SMS sms);

	void deleteSend(Integer smsId);

	void insertUser(SMS sms);

	List<String> queryMobileList(Integer smsId);

	Integer selectHistoryCountByProviderId(Integer providerId);

	List<String> queryHistoryByProviderId(Map<String, Object> map);

	Integer queryMobileCount(Integer smsId);

}
