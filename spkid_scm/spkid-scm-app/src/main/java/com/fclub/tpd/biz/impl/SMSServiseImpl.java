package com.fclub.tpd.biz.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.biz.SMSServise;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.ProviderAccountLog;
import com.fclub.tpd.dataobject.SMS;
import com.fclub.tpd.mapper.ProviderAccountLogMapper;
import com.fclub.tpd.mapper.ProviderMapper;
import com.fclub.tpd.mapper.SMSMapper;

@Service
public class SMSServiseImpl implements SMSServise {
	@Autowired
	private SMSMapper smsMapper;
	@Autowired
	private ProviderMapper providerMapper;
	@Autowired
	private ProviderAccountLogMapper providerAccountLogMapper;

	@Override
	@Transactional
	public void save(SMS sms) {
		smsMapper.insert(sms);
	}

	@Override
	public SMS get(Serializable id) {
		return smsMapper.get(id);// smsMapper.querySms();
	}

	@Override
	public Page<SMS> findPage(Page<SMS> page, SMS sms) {
		List<SMS> smsList = smsMapper.findPage(page, sms);
		for (SMS smsTmp : smsList) {
			if (smsTmp.getStatus() == 2
					&& DateUtil.getCurrentTime().after(smsTmp.getSendTime())) {
				smsTmp.setStatus(5);
			}
		}
		page.setResult(smsList);
		return page;
	}

	@Override
	@Transactional
	public void add(List<String> list, SMS sms, String submitType) {
		Date curTime = DateUtil.getCurrentTime();
		// 更新任务表
		if (StringUtil.equals(submitType, "0")) {
			sms.setCreateTime(curTime);
			sms.setStatus(0);
			if (sms.getSmsId() == null || sms.getSmsId() == 0) {
				smsMapper.insert(sms);
			} else {
				smsMapper.update(sms);
			}
		} else if (StringUtil.equals(submitType, "1")) {
			sms.setCommitTime(curTime);
			sms.setStatus(1);
			if (sms.getSmsId() == null || sms.getSmsId() == 0) {
				sms.setCreateTime(curTime);
				smsMapper.insert(sms);
			} else {
				smsMapper.update(sms);
			}
		}

		List<SMS> smsList = new ArrayList<>();
		SMS smsTmp = null;

		// if (sms.getIsHistory()) {
		// if (list == null || list.isEmpty()) {
		// list = new ArrayList<>();
		// }
		// List<String> historyList = smsMapper.queryHistoryByProviderId(sms
		// .getProviderId());
		// list.addAll(historyList);
		// }

		if (list != null && !list.isEmpty()) {
			for (String mobile : list) {
				smsTmp = new SMS();
				smsTmp.setSmsId(sms.getSmsId());
				smsTmp.setProviderId(sms.getProviderId());
				smsTmp.setMobile(mobile);
				smsTmp.setSourceType("0");
				smsTmp.setUpdateTime(curTime);
				smsList.add(smsTmp);
			}
			// 更新发送表
			smsMapper.deleteSend(sms.getSmsId());
			for (SMS smsItem : smsList) {
				smsMapper.insertSend(smsItem);
			}
			// 更新用户表
			if (StringUtil.equals(submitType, "1")) {
				for (SMS smsItem : smsList) {
					smsMapper.insertUser(smsItem);
				}
			}
		}
	}

	@Override
	@Transactional
	public void delete(SMS sms) {
		smsMapper.delete(sms.getSmsId());
		smsMapper.deleteSend(sms.getSmsId());
	}

	@Override
	@Transactional
	public boolean check(SMS sms) {
		Integer adminId = sms.getCheckAdmin();
		sms = smsMapper.get(sms.getSmsId());
		Provider provider = providerMapper.get(sms.getProviderId());
		Integer cnt = smsMapper.queryMobileCount(sms.getSmsId());
		double money = sms.getSmsPrice() * cnt;
		if (provider.getAccountBalance() < money) {
			sms.setStatus(0);
			sms.setMemo("余额不足，请充值后再发短信息！");
			smsMapper.update(sms);
			return false;
		} else {
			sms.setStatus(2);
			smsMapper.update(sms);
			ProviderAccountLog providerAccountLog = new ProviderAccountLog();
			providerAccountLog.setProviderId(sms.getProviderId());
			providerAccountLog.setChangePrice(money * -1);
			providerAccountLog.setChangeType(2);
			providerAccountLog.setOperateAid(adminId);
			providerAccountLog.setOperateStatus(0);
			providerAccountLog.setRelatedId(sms.getSmsId());
			providerAccountLog.setOperateTime(new Date());
			providerAccountLogMapper.insert(providerAccountLog);
			provider.setAccountBalance(provider.getAccountBalance() - money);
			providerMapper.update(provider);
			return true;
		}
	}

	@Override
	public void reject(SMS sms) {
		sms.setStatus(0);
		smsMapper.update(sms);
	}

	@Override
	@Transactional
	public void cancel(SMS sms, Integer adminId) {
		sms = smsMapper.get(sms.getSmsId());
		if (sms.getStatus() == 2) {
			Provider provider = providerMapper.get(sms.getProviderId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("providerId", sms.getProviderId());
			map.put("relatedId", sms.getSmsId());
			map.put("changeType", "2");
			List<ProviderAccountLog> list = providerAccountLogMapper
					.queryAccountLog(map);
			if (list != null && list.size() > 0) {
				sms.setStatus(4);
				smsMapper.update(sms);
				ProviderAccountLog tmp = list.get(0);
				ProviderAccountLog providerAccountLog = new ProviderAccountLog();
				providerAccountLog.setProviderId(tmp.getProviderId());
				providerAccountLog.setChangePrice(tmp.getChangePrice() * -1);
				providerAccountLog.setChangeType(3);
				providerAccountLog.setOperateAid(adminId);
				providerAccountLog.setOperateStatus(0);
				providerAccountLog.setRelatedId(sms.getSmsId());
				providerAccountLog.setOperateTime(new Date());
				providerAccountLogMapper.insert(providerAccountLog);
				provider.setAccountBalance(provider.getAccountBalance()
						- tmp.getChangePrice());
				providerMapper.update(provider);
			}
		}
	}

	@Override
	public List<String> queryMobileList(Integer smsId) {
		List<String> mobileList = smsMapper.queryMobileList(smsId);
		int i = 6 - mobileList.size() % 6;
		while (i != 0) {
			mobileList.add(null);
			i--;
		}
		return mobileList;
	}

	@Override
	public Boolean hasHistory(Integer providerId) {
		Integer count = smsMapper.selectHistoryCountByProviderId(providerId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer queryHistoryCount(Integer providerId) {
		return smsMapper.selectHistoryCountByProviderId(providerId);
	}

	@Override
	public List<String> queryHistoryLimitList(Map<String, Object> map) {
		return smsMapper.queryHistoryByProviderId(map);
	}
}
