/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fclub.tpd.biz.ShippingService;
import com.fclub.tpd.dataobject.Shipping;

/**
 * 
 * @author auto-gene
 * @version $Id: ShippingController.java, v 0.1 2013-07-03 15:58:45 auto-gene Exp $
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/shipping";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap) {

        List<Shipping> shippingList = shippingService.findAll();
        modelMap.put("shippingList", shippingList);
        return "tpd/shippingList";
    }

}