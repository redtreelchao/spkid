package com.fclub.tpd.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ReportFormService;
import com.fclub.tpd.common.jxl.JxlsUtil;
import com.fclub.tpd.dataobject.ReportForm;
import com.fclub.tpd.mapper.ReportFormMapper;

@Service
@Scope("prototype")
public class ReportFormServiceImpl implements ReportFormService {

	
	@Autowired
	private ReportFormMapper reportFormMapper;
	@Override
	public ReportForm get(Integer id) {
		return reportFormMapper.get(id);
	}

	@Override
	public Page<ReportForm> findPage(Page<ReportForm> page,
			ReportForm reportForm) {
		 page.setResult(reportFormMapper.findPage(page, reportForm));
		return page;
	}

	@Override
	public ReportForm resultTotal(ReportForm reportForm) {
		return reportFormMapper.resultTotal(reportForm);
	}

	@Override
	public void exportReportData(HttpServletResponse response,
			ReportForm reportForm) {
			List<ReportForm> reportList=reportFormMapper.queryExportReportData(reportForm);
			Map<String, Object> beans = new HashMap<String, Object>();
			ReportForm report=reportFormMapper.resultTotal(reportForm);
	        beans.put("result", reportList);
	        beans.put("report", report);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		    String fileName = "第三方报表信息" + dateFormat.format(new Date());
		    JxlsUtil.exportExcel(response, fileName, beans, "TpdReportList.xls");
	}

}
