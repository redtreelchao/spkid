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

import com.fclub.tpd.biz.NoticeService;
import com.fclub.tpd.biz.ShippingPacketService;
import com.fclub.tpd.dataobject.Notice;
import com.fclub.tpd.helper.SessionHelper;

@Controller
public class IndexController {

    @Autowired
    private NoticeService         noticeService;
    @Autowired
    private ShippingPacketService shippingPacketService;

    @RequestMapping("/index.htm")
    public String index() {
        return "index";
    }

    @RequestMapping("/main/top.htm")
    public String top(ModelMap model) {
        List<Notice> list = noticeService.queryTop();
        model.put("list", list);
        return "main/top";
    }

    @RequestMapping("/main/left.htm")
    public String left() {
        return "main/left";
    }

    @RequestMapping("/main/drag.htm")
    public String drag() {
        return "main/drag";
    }

    @RequestMapping("/main/main.htm")
    public String main(ModelMap modelMap) {
        Integer orderNum = shippingPacketService.getExportOrderNum(SessionHelper.getProvider()
            .getProviderId());
        modelMap.put("orderNum", orderNum);
        return "tpd/shippingWave";
    }
}
