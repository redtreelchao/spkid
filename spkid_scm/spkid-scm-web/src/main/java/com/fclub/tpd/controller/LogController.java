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
import com.fclub.tpd.biz.LogService;
import com.fclub.tpd.dataobject.Log;
import com.fclub.common.lang.BizException;

/**
 * 
 * @author auto-gene
 * @version $Id: LogController.java, v 0.1 2013-06-28 15:30:48 auto-gene Exp $
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/log";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<Log> page, Log log) {

        page = logService.findPage(page, log);
        modelMap.put("page", page);
        return "tpd/logList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo() {
        return "tpd/logAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, Log log) throws Exception {

        logService.save(log);

        modelMap.addAttribute("currentUrl", "/log/addTo.htm");
        modelMap.addAttribute("backUrl", "/log/list/main.htm");
        return "commons/success2";
    }
	
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {
        
        modelMap.put("readOnly", 1);
        Log log = logService.get(Integer.valueOf(id));
        modelMap.put("log", log);
        return "tpd/logEdit";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, String id) {

        modelMap.put("readOnly", 0);
        Log log = logService.get(Integer.valueOf(id));
        modelMap.put("log", log);
        return "tpd/logEdit";
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, Log log) throws Exception {
        logService.update(log);
        modelMap.addAttribute("backUrl", "/log/list/main.htm");
        return "commons/success2";
    }

    @RequestMapping(value = "/delete.htm")
    @ResponseBody
    public String delete(String id) {

        try {
            logService.delete(Integer.valueOf(id));
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
}