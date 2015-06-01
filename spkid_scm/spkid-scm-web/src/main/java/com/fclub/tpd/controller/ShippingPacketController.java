/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.tpd.biz.ShippingPacketService;
import com.fclub.tpd.common.jxl.JxlsUtil;
import com.fclub.tpd.dataobject.ShippingProduct;
import com.fclub.tpd.dataobject.ShippingPacket;
import com.fclub.tpd.dto.ShippingStatDTO;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.tpd.view.ShippingWaveExcelView;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingPacketController.java, v 0.1 2013-07-10 14:45:56 auto-gene Exp $
 */
@Controller
@RequestMapping("/shippingpacket")
public class ShippingPacketController {

    @Autowired
    private ShippingPacketService shippingPacketService;
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/shippingPacket";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<ShippingPacket> page, ShippingPacket shippingPacket) {

        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        shippingPacket.setOrderSn(StringUtils.trim(shippingPacket.getOrderSn()));
        shippingPacket.setPacketSn(StringUtils.trim(shippingPacket.getPacketSn()));
        shippingPacket.setConsignee(StringUtils.trim(shippingPacket.getConsignee()));
        shippingPacket.setProviderProductcode(StringUtils.trim(shippingPacket.getProviderProductcode()));
        shippingPacket.setMobile(StringUtils.trim(shippingPacket.getMobile()));
        
        page = shippingPacketService.findPage(page, shippingPacket);
        ShippingStatDTO stat = shippingPacketService.statOrder(shippingPacket);
        
        modelMap.put("page", page);
        modelMap.put("stat", stat);
        return "tpd/shippingPacketList";
    }
    
    @RequestMapping(value = "/export/order.htm")
    public ModelAndView exportOrder(ModelMap modelMap, ShippingPacket shippingPacket) throws Exception {

        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        List<ShippingPacket> result = shippingPacketService.findExportOrder(shippingPacket);
        if(result == null || result.size() == 0) {
            throw new BizException("没有要导出的资料");
        }
        modelMap.put("result", result);
        return new ModelAndView(new ShippingWaveExcelView());
    }
    
    @RequestMapping("/export/order/format.htm")
    public void exportAll(HttpServletResponse response, ShippingPacket shippingPacket) throws Exception {

        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        List<ShippingProduct> result = shippingPacketService.findExport(shippingPacket);
        if(result == null || result.size() == 0) {
            throw new BizException("没有要导出的资料");
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("result", result);
        String fileName = "发运订单导出" + DateUtil.getDateFormat("yyyyMMdd").format(new Date());
        JxlsUtil.exportExcel(response, fileName, beans, "ShippingOrder.xls");
    }
    
    @RequestMapping("/export/goods.htm")
    public void export(HttpServletResponse response, ShippingPacket shippingPacket) throws Exception {
        
        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        List<ShippingProduct> result = shippingPacketService.findExportGoods(shippingPacket);
        if(result == null || result.size() == 0) {
            throw new BizException("没有要导出的资料");
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("result", result);
        String fileName = "发运商品导出" + DateUtil.getDateFormat("yyyyMMdd").format(new Date());
        JxlsUtil.exportExcel(response, fileName, beans, "ShippingGoods.xls");
    }

}