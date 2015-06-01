/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.tpd.biz.SelfReturnService;
import com.fclub.tpd.dataobject.SelfReturn;
import com.fclub.tpd.dataobject.SelfReturnProduct;
import com.fclub.tpd.dataobject.SelfReturnSuggest;
import com.fclub.tpd.helper.SessionHelper;

/**
 * @version $Id: SelfReturnController.java, v 0.1 2013-07-12 16:18:00 auto-gene Exp $
 */
@Controller
@RequestMapping("/selfreturn")
public class SelfReturnController {

    @Autowired
    private SelfReturnService selfReturnService;

    /**
     * 处理请求中的日期类型，将请求中制定格式的日期字符串，格式化为日期类型。
     */
    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
            new CustomDateEditor(DateUtil.getDateFormat(), true));
    }

    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/selfReturn";
    }

    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<SelfReturn> page, SelfReturn selfReturn) {
        Integer providerId = SessionHelper.getProvider().getProviderId();
        selfReturn.setProviderId(providerId);
        page = selfReturnService.findPage(page, selfReturn);
        modelMap.put("page", page);
        return "tpd/selfReturnList";
    }

    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {

        modelMap.put("readOnly", 1);
        SelfReturn selfReturn = selfReturnService.get(Integer.valueOf(id));
        modelMap.put("selfReturn", selfReturn);
        return "tpd/selfReturnEdit";
    }

    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, String id) {
        modelMap.put("readOnly", 0);
        SelfReturn selfReturn = selfReturnService.get(Integer.valueOf(id));
        List<SelfReturnProduct> selfReturnProduct = selfReturnService.getSelfReturnProduct(Integer
            .valueOf(id));
        List<SelfReturnSuggest> selfReturnSuggest = selfReturnService.getSelfReturnSuggest(Integer
            .valueOf(id));
        modelMap.put("selfReturn", selfReturn);
        modelMap.put("selfReturnProduct", selfReturnProduct);
        modelMap.put("selfReturnSuggest", selfReturnSuggest);
        return "tpd/selfReturnEdit";
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, SelfReturnSuggest selfReturnSuggest) throws Exception {
        
        selfReturnSuggest.setCreateId(SessionHelper.getProvider().getProviderId());
        selfReturnSuggest.setCreateDate(new Date());
        selfReturnService.saveSuggest(selfReturnSuggest);
        modelMap.addAttribute("backUrl", "/selfreturn/list/main.htm");
        return "commons/success2";
    }

}