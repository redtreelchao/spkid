/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.fclub.common.dal.Page;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.biz.ProviderService;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.ProviderAccountLog;
import com.fclub.tpd.dataobject.ProviderBrand;
import com.fclub.tpd.dto.ProviderAccountLogSearch;
import com.fclub.tpd.mapper.BrandMapper;
import com.fclub.tpd.mapper.ProviderAccountLogMapper;
import com.fclub.tpd.mapper.ProviderBrandMapper;
import com.fclub.tpd.mapper.ProviderMapper;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private ProviderMapper providerMapper;
	@Autowired
	private ProviderBrandMapper providerBrandMapper;
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private ProviderAccountLogMapper providerAccountLogMapper;

	@Override
	public void update(Provider provider) {
		providerMapper.update(provider);
	}

	@Override
	public Provider findById(Integer providerId) {
		return providerMapper.get(providerId);
	}

	@Override
	public Provider findByUserName(String userName) {
		return providerMapper.selectByUserName(userName);
	}

	@Override
	public List<ProviderBrand> queryBrandListByProviderId(Integer id) {
		return providerBrandMapper.selectBrandsByProviderId(id);
	}

	@Override
	@Transactional
	public void editProvider(Provider provider, List<Brand> brandList) {
		if (provider != null && (StringUtil.isNotEmpty(provider.getDisplayName()) || StringUtil.isNotEmpty(provider.getLogo()))) {
			providerMapper.update(provider);
		}
		if (brandList != null && !brandList.isEmpty()) {
			for (Brand brand : brandList) {
				brandMapper.update(brand);
			}
		}
	}

	public Page<ProviderAccountLog> queryAccountLogListByPage(
			Page<ProviderAccountLog> page, ProviderAccountLogSearch search) {
		page.setResult(providerAccountLogMapper.findPage(page, search));
		return page;
	}

	@Override
	public String aliPay(Integer providerId, Integer orderId, Double money) {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		String out_trade_no = orderId.toString();
		String body = "充值";
		String payment_type = "1";
		String subject = "帐户充值";
		// 防钓鱼时间戳
		// String anti_phishing_key = "";
		// 若要使用请调用类文件submit中的query_timestamp函数
		// 客户端的IP地址
		// String exter_invoke_ip = "";
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", money.toString());
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", AlipayConfig.show_url);
		// sParaTemp.put("anti_phishing_key", anti_phishing_key);
		// sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		return sHtmlText;
	}

	@Override
	public boolean insertAccountLog(ProviderAccountLog providerAccountLog) {
		if (providerAccountLogMapper.insert(providerAccountLog) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void updateAccountLog(ProviderAccountLog providerAccountLog) {
		providerAccountLogMapper.update(providerAccountLog);
	}

	@Override
	public ProviderAccountLog findAccountLogByLogId(Integer logId) {
		return providerAccountLogMapper.get(logId);
		
	}

}
