/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.CooperationTipsService;
import com.fclub.tpd.dataobject.CooperationTips;
import com.fclub.common.lang.BizException;

/**
 * 
 * @author auto-gene
 * @version $Id: CooperationTipsController.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
@Controller
@RequestMapping("/cooperationtips")
public class CooperationTipsController {

    @Autowired
    private CooperationTipsService cooperationTipsService;
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/cooperationTips";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<CooperationTips> page, CooperationTips cooperationTips) {

        page = cooperationTipsService.findPage(page, cooperationTips);
        modelMap.put("page", page);
        return "tpd/cooperationTipsList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo() {
        return "tpd/cooperationTipsAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, CooperationTips cooperationTips) throws Exception {

        cooperationTipsService.save(cooperationTips);

        modelMap.addAttribute("currentUrl", "/cooperationtips/addTo.htm");
        modelMap.addAttribute("backUrl", "/cooperationtips/list/main.htm");
        return "commons/success2";
    }
	
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {
        
        modelMap.put("readOnly", 1);
        CooperationTips cooperationTips = cooperationTipsService.get(Integer.valueOf(id));
        modelMap.put("cooperationTips", cooperationTips);
        return "tpd/cooperationTipsEdit";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, String id) {

        modelMap.put("readOnly", 0);
        CooperationTips cooperationTips = cooperationTipsService.get(Integer.valueOf(id));
        modelMap.put("cooperationTips", cooperationTips);
        return "tpd/cooperationTipsEdit";
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, CooperationTips cooperationTips) throws Exception {
        cooperationTipsService.update(cooperationTips);
        modelMap.addAttribute("backUrl", "/cooperationtips/list/main.htm");
        return "commons/success2";
    }

    @RequestMapping(value = "/delete.htm")
    @ResponseBody
    public String delete(String id) {

        try {
            cooperationTipsService.delete(Integer.valueOf(id));
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
}