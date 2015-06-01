package com.fclub.tpd.controller;

import static com.fclub.tpd.helper.ConstantsHelper.getPicRootPath;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.tpd.biz.SMSServise;
import com.fclub.tpd.common.jxl.ExcelUtil;
import com.fclub.tpd.dataobject.SMS;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.web.util.UploadUtil;

@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {
	private static final LogUtil logger = LogUtil
			.getLogger(SmsController.class);

	@Autowired
	private SMSServise smsServise;

	/**
	 * 处理请求中的日期类型，将请求中制定格式的日期字符串，格式化为日期类型。
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(DateUtil.getDatetimeFormat(), true));
	}

	@RequestMapping("/list/main.htm")
	public String main() {
		return "tpd/sms";
	}

	@RequestMapping("/list/query.htm")
	public String querySMSList(ModelMap model, Page<SMS> page, SMS sms) {
		try {
			sms.setIsAdmin(isAdmin());
			sms.setProviderId(getProviderId());
			page = smsServise.findPage(page, sms);
			model.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tpd/smsList";
	}

	@RequestMapping("/list/show.htm")
	public String show(ModelMap model, Integer smsId) {
		SMS sms = smsServise.get(smsId);
		model.put("readOnly", 1);
		model.put("sms", sms);
		return "tpd/smsAdd";
	}

	@RequestMapping("/addTo.htm")
	public String addTo(ModelMap model, Integer smsId) {
		SMS sms = smsServise.get(smsId);
		model.put("hasHistory", smsServise.hasHistory(getProviderId()));
		model.put("readOnly", 0);
		model.put("sms", sms);
		return "tpd/smsAdd";
	}

	@RequestMapping("/downloadshortage.htm")
	public void downloadShortage(HttpServletResponse response) {
		try {
			UploadUtil.downloadFromClassPath(response, "smsModel.xls", "短信信息"
					+ DateUtil.getDateFormat("yyyyMMdd").format(new Date()));
		} catch (IOException e) {
			logger.error("download error: ", e);
			throw new BizException("下载失败");
		}
	}

	@RequestMapping("/download.htm")
	public void download(HttpServletResponse response,
			@RequestParam("name") String name) {
		try {
			String filePath = getPicRootPath();
			UploadUtil.download(response, filePath, name);
		} catch (IOException e) {
			logger.error("download error: ", e);
			throw new BizException("下载失败");
		}
	}

	@ResponseBody
	@RequestMapping("/upload.htm")
	public String uploadShortage(ModelMap modelMap,
			MultipartHttpServletRequest request, SMS sms, String submitType,
			String sendType) {
		String result = "success";
		try {
			List<String> list = null;
			if (sms.getSmsId() == null || sms.getSmsId() == 0) {
				if (sendType.equals("1")) {
					MultipartFile file = request.getFile("file");
					if (sms.getSmsId() == null || sms.getSmsId() == 0) {
						if ((file == null || file.isEmpty())) {
							return "Excel是个空文件，请检查！";
						}
					}
					if (file != null && !file.isEmpty()) {
						list = readLines(file);
					}
				} else if (smsServise.hasHistory(getProviderId())
						&& sms.getSmsNum() != null && sms.getSmsNum() > 0
						&& sms.getSmsNum() <= 5000) {
					if (sms.getSmsId() == null || sms.getSmsId() == 0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("providerId", getProviderId());
						map.put("smsNum", sms.getSmsNum());
						list = smsServise.queryHistoryLimitList(map);
					}
				} else if (sms.getSmsNum() != null && sms.getSmsNum() <= 0
						&& sms.getSmsNum() > 5000) {
					return "历史随机数量不合法，请检查！";
				} else {
					return "没有历史发送人群记录，请检查！";
				}
			}
			if (sms.getSmsId() == null || sms.getSmsId() == 0) {
				sms.setSmsPrice(this.getProvider().getSmsPrice());
			}
			sms.setProviderId(getProviderId());
			smsServise.add(list, sms, submitType);
		} catch (Exception e) {
			logger.error("upload error: ", e);
			result = "保存或提交失败: " + e.getMessage();
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.htm")
	public String delete(ModelMap modelMap, SMS sms) {
		smsServise.delete(sms);
		return "success";
	}

	@ResponseBody
	@RequestMapping("/check.htm")
	public String check(ModelMap modelMap, SMS sms) {
		sms.setCheckAdmin(SessionHelper.getProvider().getAdminId());
		sms.setCheckTime(new Date());
		boolean flag = smsServise.check(sms);
		if (flag) {
			return "success";
		}
		return "fail";
	}

	@ResponseBody
	@RequestMapping("/reject.htm")
	public String reject(ModelMap modelMap, SMS sms) {
		smsServise.reject(sms);
		return "success";
	}

	@RequestMapping("/reject/show.htm")
	public String rejectShow(ModelMap modelMap, SMS sms) {
		modelMap.put("sms", sms);
		return "tpd/smsRejectShow";
	}

	@ResponseBody
	@RequestMapping("/cancel.htm")
	public String cancel(ModelMap modelMap, SMS sms) {
		smsServise.cancel(sms, SessionHelper.getProvider().getAdminId());
		return "success";
	}

	@RequestMapping("/list/showDetail.htm")
	public String showDetail(ModelMap modelMap, SMS sms) {
		modelMap.put("mobileList", smsServise.queryMobileList(sms.getSmsId()));
		return "tpd/smsShowDetail";
	}

	private static List<String> readLines(MultipartFile sourceFile)
			throws IOException {
		Workbook workbook = ExcelUtil.getWorkbook(sourceFile);
		Sheet sheet = ExcelUtil.getSheet(workbook, 0);
		if (sheet == null)
			return null;
		List<String> list = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("0");

		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell cell = ExcelUtil.getCell(row, 0);
				String cellValue = ExcelUtil.getCellValue(cell);
				if (cellValue.toLowerCase().indexOf("e") > -1) {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cellValue = df.format(cell.getNumericCellValue());
				}

				System.out.println(cellValue);

				if (StringUtil.isEmpty(cellValue))
					continue;
				if (!checkMobile(cellValue)) {
					throw new BizException("Excel中第" + (i + 1)
							+ "行不是正确的手机号码，请检查！");
				}
				list.add(cellValue);
			}
		}
		if (list.isEmpty()) {
			throw new BizException("Excel是个空文件，请检查！");
		}
		return list;
	}

	private static Boolean checkMobile(String mobile) {// System.out.println(mobile);
		String regExp = "^[1][0-9]{10}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobile);
		return m.find();
	}
}
