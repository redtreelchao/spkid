package com.fclub.tpd.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ReportFormService;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.dataobject.ReportForm;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.tpd.mapper.BrandMapper;

@Controller
@RequestMapping("/reportform")
public class ReportFormController {
	
    @Autowired
    private ReportFormService reportFormService;
    @Autowired
    private BrandMapper brandMapper;
	 @RequestMapping("/list/main.htm")
	    public String main(ModelMap modelMap) {
		 List<Brand> brandList = brandMapper.findBrandsByProviderId(SessionHelper.getProvider().getProviderId());
	    	modelMap.put("brandList", brandList);
	    	return "tpd/reportForm";
	    }
	 
	 @RequestMapping("/list/query.htm")
	    public String query(ModelMap modelMap, Page<ReportForm> page, ReportForm reportForm) {
		 		reportForm.setProviderId(SessionHelper.getProvider().getProviderId());
		 		//总计
		 		ReportForm totalReport=reportFormService.resultTotal(reportForm);
		 		page = reportFormService.findPage(page, reportForm);
		        modelMap.put("page", page);
		        if(totalReport!=null){
		        	modelMap.put("totalReport", totalReport);
		        }else{
		        	modelMap.put("totalReport", reportForm);
		        }
	        return "tpd/reportFormList";
	    }
	 	@RequestMapping("/list/export.htm")
	    public void export(ReportForm reportForm, HttpServletResponse response) {
		 	reportForm.setProviderId(SessionHelper.getProvider().getProviderId());
	    	reportFormService.exportReportData(response, reportForm);
	    }
}
