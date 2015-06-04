package com.fclub.tpd.mapper;

import java.util.List;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.ReportForm;

public interface ReportFormMapper extends BaseMapper<ReportForm>{
	public ReportForm resultTotal(ReportForm reportForm);
	/*** 导出excel*/
	List<ReportForm> queryExportReportData(ReportForm reportForm);
	
}