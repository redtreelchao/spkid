/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fclub.tpd.helper.SessionHelper;
import com.fclub.tpd.mapper.OrderReturnQueryMapper;
import com.fclub.tpd.vo.JsonResult;

/**
 * 
 * @author auto-gene
 * @version $Id: OrderReturnQueryController.java, v 0.1 2013-07-10 14:45:56 auto-gene Exp $
 */
@Controller
@RequestMapping("/orderreturnquery")
public class OrderReturnQueryController {

    @Autowired
    private OrderReturnQueryMapper orderReturnQueryMapper;
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/orderreturnquery";
    }
    
    @RequestMapping("/list/query.htm")
    @ResponseBody
    public JsonResult query(ModelMap modelMap, @RequestParam("queryType") Integer queryType, @RequestParam("queryValue") String queryValue) {
    	JsonResult jsonResult = new JsonResult();
    	Integer providerId = SessionHelper.getProvider().getProviderId();
    	if (queryType == 1) {
    		jsonResult.setResult(orderReturnQueryMapper.queryReturnSnByOrderSn(queryValue, providerId));
    	} else if (queryType == 2) {
    		jsonResult.setResult(orderReturnQueryMapper.findOrderSnByReturnSn(queryValue, providerId));
    	}
        return jsonResult;
    }
    
}