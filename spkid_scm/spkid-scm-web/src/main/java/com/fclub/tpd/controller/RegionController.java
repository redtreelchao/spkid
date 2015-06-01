package com.fclub.tpd.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fclub.common.dal.Page;
import com.fclub.common.log.LogUtil;
import com.fclub.tpd.biz.ProviderService;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.Region;
import com.fclub.tpd.helper.JsonUtil;
import com.fclub.tpd.mapper.RegionMapper;

@Controller
@RequestMapping("/region")
public class RegionController extends BaseController {
	
	@Autowired
	private RegionMapper    regionMapper;
	@Autowired
	private ProviderService providerService;
	
	private final LogUtil logger = LogUtil.getLogger(getClass());
	
	@RequestMapping("/list/main.htm")
	public String main(){
		return "tpd/region";
	}
	
	@RequestMapping("/list/query.htm")
	public String index(ModelMap modelMap, Page<Region> page, Region region){
		Provider provider = providerService.findById(getProviderId());
		
		ShippingFeeConfig[] configs = null;
		String shippingFeeConfig = provider.getShippingFeeConfig();
		if (shippingFeeConfig != null) {
			try {
				configs = JsonUtil.json2Object(shippingFeeConfig, ShippingFeeConfig[].class);
			} catch (Exception e) {
				logger.error("解析JSON数据错误！", e);
			}
		}
		
		List<Region> regionList = regionMapper.findPage(page, region);
		
		if (configs != null && configs.length > 0) {
			for (Region r : regionList) {
				Integer regionId = r.getRegionId();
				for (ShippingFeeConfig config : configs) {
					if (regionId != null && config.getRegionId() == regionId.intValue()) {
						r.setFee(config.getFee());
						r.setPrice(config.getPrice());
					}
				}
			}
		}
		
		modelMap.put("regionList", regionList);
		
		return "tpd/regionList";
	}
	
	@RequestMapping("/config.htm")
	public String config(ModelMap modelMap, @RequestParam("regionId[]") int[] regionIds,
			@RequestParam("fee[]") String[] fees, @RequestParam("price[]") String[] prices){
		
		if (regionIds != null && regionIds.length > 0) {
			List<ShippingFeeConfig> configs = new ArrayList<ShippingFeeConfig>();
			
			for (int i = 0; i < regionIds.length; i++) {
				configs.add(new ShippingFeeConfig(regionIds[i], fees[i], prices[i]));
			}
			
			Provider provider = new Provider();
			provider.setProviderId(getProviderId());
			provider.setShippingFeeConfig(JsonUtil.objectToJson(configs));
			
			try {
				providerService.update(provider);
			} catch (Exception e) {
				logger.error("更新供应商区域运费配置信息失败！", e);
				return "更新区域运费配置信息失败！";
			}
		}
		
		return "redirect:/region/list/main.htm";
	}
	
	
	public static class ShippingFeeConfig {
		private int regionId;
		private int fee;
		private int price;
		
		public ShippingFeeConfig() {
		}
		public ShippingFeeConfig(int regionId, String minFee, String minPrice) {
			this.regionId = regionId;
			this.fee = toInt(minFee);
			this.price = toInt(minPrice);
		}

		public int getRegionId() {
			return regionId;
		}
		public void setRegionId(int regionId) {
			this.regionId = regionId;
		}
		public int getFee() {
			return fee;
		}
		public void setFee(int fee) {
			this.fee = fee;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		
		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
		}
		
		public int toInt(String value) {
			if (value == null || value.isEmpty()) {
				return 0;
			}
			
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		
	}

}
