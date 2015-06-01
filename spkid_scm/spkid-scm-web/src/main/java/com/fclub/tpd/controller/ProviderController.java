package com.fclub.tpd.controller;

import static com.fclub.tpd.helper.ConstantsHelper.getBrandLogoUploadDir;
import static com.fclub.tpd.helper.ConstantsHelper.getPicRootPath;
import static com.fclub.tpd.helper.ConstantsHelper.getProviderLogoUploadDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alipay.util.AlipayNotify;
import com.fclub.common.dal.Page;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.ImageUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.biz.ProviderService;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.ProviderAccountLog;
import com.fclub.tpd.dataobject.ProviderBrand;
import com.fclub.tpd.dto.ProviderAccountLogSearch;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.web.util.UploadUtil;

@Controller
@RequestMapping("/provider")
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	/**
	 * 处理请求中的日期类型，将请求中制定格式的日期字符串，格式化为日期类型。
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(DateUtil.getDatetimeFormat(), true));
	}

	@RequestMapping("/main.htm")
	public String main(ModelMap modelMap) {
		Integer providerId = SessionHelper.getProvider().getProviderId();
		setModelMap(modelMap, providerId);
		return "tpd/providerShow";
	}

	@RequestMapping("/editTo.htm")
	public String editTo(ModelMap modelMap, Integer providerId) {
		setModelMap(modelMap, providerId);
		return "tpd/providerEdit";
	}

	@RequestMapping("/editToBrand.htm")
	public String editToBrand(ModelMap modelMap, Integer providerId) {
		setModelMap(modelMap, providerId);
		return "tpd/providerEditBrand";
	}

	private void setModelMap(ModelMap modelMap, Integer providerId) {
		Provider provider = providerService.findById(providerId);
		List<ProviderBrand> providerBrands = providerService
				.queryBrandListByProviderId(providerId);

		modelMap.put("provider", provider);
		modelMap.put("providerBrands", providerBrands);
	}

	@ResponseBody
	@RequestMapping("/edit.htm")
	public String edit(ModelMap modelMap, Provider provider,
			MultipartHttpServletRequest request) {
		String result = "";
		List<Brand> brandList = new ArrayList<Brand>();

		MultipartFile logoFile = request.getFile("logoFile");
		if (logoFile != null && !logoFile.isEmpty()) {
			result = checkLogoFileType(logoFile, "provider");
			if (StringUtil.notEquals(result, "success")) {
				return result;
			}
			String uploadPath = getPicRootPath() + "/"
					+ getProviderLogoUploadDir();
			String fileName = String.valueOf(DateUtil.getCurrentTime()
					.getTime());

			String logo = null;
			try {
				logo = UploadUtil.uploadImage(logoFile, uploadPath, fileName);
				provider.setLogo(getProviderLogoUploadDir() + "/" + logo);
			} catch (Exception e) {
				return "请上传图片格式文件！";
			}
		}

		List<ProviderBrand> providerBrands = providerService
				.queryBrandListByProviderId(provider.getProviderId());
		Brand brand = null;
		if (providerBrands != null && !providerBrands.isEmpty()) {
			for (ProviderBrand providerBrand : providerBrands) {
				brand = new Brand();
				brand.setBrandId(providerBrand.getBrandId());
				logoFile = request.getFile("logo_" + brand.getBrandId());

				if (logoFile != null && !logoFile.isEmpty()) {
					result = checkLogoFileType(logoFile, "brand");
					if (StringUtil.notEquals(result, "success")) {
						return result;
					}
					String uploadPath = getPicRootPath() + "/"
							+ getBrandLogoUploadDir();
					String fileName = String.valueOf(DateUtil.getCurrentTime()
							.getTime());

					String logo = null;
					try {
						logo = UploadUtil.uploadImage(logoFile, uploadPath,
								fileName);
						String logoPath = uploadPath + "/" + logo;
						String logoSuffix = logo.substring(logo
								.lastIndexOf("."));
						// 生成两张缩略图
						ImageUtil.resize(new File(logoPath), 76, 38, logoPath
								+ ".76x38" + logoSuffix);
						ImageUtil.resize(new File(logoPath), 108, 54, logoPath
								+ ".108x54" + logoSuffix);

						brand.setBrandLogo(getBrandLogoUploadDir() + "/" + logo);
					} catch (Exception e) {
						return "请上传图片格式文件！";
					}
					brandList.add(brand);
				}
			}
		}

		providerService.editProvider(provider, brandList);
		return "success";
	}

	private String checkLogoFileType(MultipartFile logoFile, String logoType) {
		String result = "success";
		if (logoFile != null && !logoFile.isEmpty()) {
			if (StringUtil.notEquals(logoFile.getContentType(), "image/jpg")
					&& StringUtil.notEquals(logoFile.getContentType(),
							"image/jpeg")) {
				result = "请上传JPG格式文件！";
			}
			// 判断图片尺寸
			try {
				BufferedImage image = ImageIO.read(logoFile.getInputStream());
				if (StringUtil.equals(logoType, "provider")) {
					if (image.getWidth() != 120 || image.getHeight() != 96) {
						result = "请上传120X96尺寸的店铺LOGO文件！";
					}
				} else if (StringUtil.equals(logoType, "brand")) {
					if (image.getWidth() < 108
							|| (image.getWidth() >= 108 && image.getWidth()
									/ image.getHeight() != 2)) {
						result = "请上传宽度大于108且宽度高度比例为2：1的品牌LOGO文件！";
					}
				}
			} catch (IOException e) {
				result = "请上传图片格式文件！";
			}

		}
		return result;
	}

	@RequestMapping("/account.htm")
	public String account(ModelMap modelMap) {
		Integer providerId = SessionHelper.getProvider().getProviderId();
		Provider provider = providerService.findById(providerId);
		modelMap.put("provider", provider);
		return "tpd/providerAccountShow";
	}

	@RequestMapping("/account/list.htm")
	public String accountList(ModelMap modelMap, Page<ProviderAccountLog> page,
			ProviderAccountLogSearch search) {
		Integer providerId = SessionHelper.getProvider().getProviderId();
		search.setOwner(false);
		search.setProviderId(providerId);
		if (search.getDateStart() != null
				&& !search.getDateStart().trim().equals("")) {
			search.setDateStart(search.getDateStart() + " 00:00:00");
		}
		if (search.getDateEnd() != null
				&& !search.getDateEnd().trim().equals("")) {
			search.setDateEnd(search.getDateEnd() + " 23:59:59");
		}
		page = providerService.queryAccountLogListByPage(page, search);
		modelMap.put("page", page);
		return "tpd/providerAccountLogList";
	}

	@RequestMapping("/chongzhi/show.htm")
	public String chongzhiShow(ModelMap modelMap) {
		return "tpd/providerAccountCzShow";
	}

	@RequestMapping(value = "/chongzhi/pay.htm", method = RequestMethod.POST)
	public String chongzhiPay(ModelMap modelMap, Double money) {
		aliPay(modelMap, money);
		return "tpd/providerAccountCzPay";
	}

	private void aliPay(ModelMap modelMap, Double money) {
		Integer providerId = SessionHelper.getProvider().getProviderId();
		ProviderAccountLog providerAccountLog = new ProviderAccountLog();
		providerAccountLog.setProviderId(providerId);
		providerAccountLog.setChangeType(0);
		providerAccountLog.setChangePrice(money);
		providerAccountLog.setOperateTime(new Date());
		providerAccountLog.setOperateAid(1);
		providerAccountLog.setOperateStatus(0);

		Boolean flag = providerService.insertAccountLog(providerAccountLog);
		if (flag) {
			modelMap.put(
					"payHtml",
					providerService.aliPay(providerId,
							providerAccountLog.getLogId(), money));
		} else {
			modelMap.put("payHtml", "支付失败，请联系系统管理员！");
		}

	}

	@RequestMapping(value = "/chongzhi/notify.htm")
	public String chongzhiNotify(ModelMap modelMap,
			@RequestParam Map<String, String> params) {
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(params.get("out_trade_no"));

		// 支付宝交易号

		// String trade_no = new String(params.get("trade_no"));

		// 交易状态
		String trade_status = new String(params.get("trade_status"));

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if (AlipayNotify.verify(params)) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if (trade_status.equals("TRADE_FINISHED") ||trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 该种交易状态只在两种情况下出现
				// 1、开通了普通即时到账，买家付款成功后。
				// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				ProviderAccountLog providerAccountLog = providerService
						.findAccountLogByLogId(Integer.valueOf(out_trade_no));
				// 增加未处理判断 2014-02-20
				if (providerAccountLog.getOperateStatus().intValue() == 0) {
					providerAccountLog.setOperateStatus(1);
					providerService.updateAccountLog(providerAccountLog);
					Provider provider = providerService.findById(providerAccountLog
							.getProviderId());
					provider.setProviderId(providerAccountLog.getProviderId());
					provider.setAccountBalance(provider.getAccountBalance()
							+ providerAccountLog.getChangePrice());
					providerService.update(provider);
				}
				modelMap.put("payHtml", "success"); // 请不要修改或删除			
			} 		
			modelMap.put("payHtml", "success"); // 请不要修改或删除

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			modelMap.put("payHtml", "支付失败，请联系系统管理员！");
		}
		return "tpd/providerAccountCzNotify";
	}

	@RequestMapping(value = "/chongzhi/return.htm", method = RequestMethod.GET)
	public String chongzhiReturn(ModelMap modelMap,
			@RequestParam Map<String, String> params) {
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(params.get("out_trade_no"));

		// 支付宝交易号

		// String trade_no = new String(params.get("trade_no"));

		// 交易状态
		String trade_status = new String(params.get("trade_status"));// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if (verify_result) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED")
					|| trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
			}

			// 该页面可做页面美工编辑
			modelMap.put("payHtml", "验证成功！"); // 请不要修改或删除
			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			// ProviderAccountLog
			// providerAccountLog=providerService.findAccountLogByLogId(Integer.valueOf(out_trade_no));
			// providerAccountLog.setOperateStatus(1);
			// providerService.updateAccountLog(providerAccountLog);
			// Provider provider
			// =providerService.findById(providerAccountLog.getProviderId());
			// provider.setProviderId(providerAccountLog.getProviderId());
			// provider.setAccountBalance(provider.getAccountBalance()+providerAccountLog.getChangePrice());
			// providerService.update(provider);
			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {
			// 该页面可做页面美工编辑
			modelMap.put("payHtml", "验证失败，请联系系统管理员！");

		}
		return "redirect:/provider/account.htm";
	}
}