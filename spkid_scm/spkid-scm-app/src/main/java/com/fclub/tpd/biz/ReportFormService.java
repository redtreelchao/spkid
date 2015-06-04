package com.fclub.tpd.biz;

import javax.servlet.http.HttpServletResponse;

import com.fclub.common.dal.Page;
import com.fclub.tpd.dataobject.ReportForm;

public interface ReportFormService {
	Integer TPD_DEPOT_ID = 50;
    Integer TPD_PACKET_ID = 90176;
    Integer TPD_DEPOT_TYPE = 28;
    
	/**
     * 根据Id查询ReportForm
     */
    ReportForm get(Integer id);
    
    /**
     * 分页查询ReportForm
     */
    Page<ReportForm> findPage(Page<ReportForm> page, ReportForm reportForm);
    
    /**
     * 总计
     */
    ReportForm resultTotal(ReportForm reportForm);
    
    
    void exportReportData(HttpServletResponse response, ReportForm reportForm);
    
}
