/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.NoticeService;
import com.fclub.tpd.dataobject.Notice;

/**
 * @version $Id: NoticeController.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;
    
    @RequestMapping("/list/main.htm")
    public String main(ModelMap modelMap) {
        return "tpd/notice";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<Notice> page, Notice notice) {
    	if (!isAdmin()) {
    		//XXX: 将ID设置在createUser中，标示供应商查询
    		notice.setCreateUser(getProviderId());
    	}
    	
    	page = noticeService.findPage(page, notice);
        modelMap.put("page", page);
        
        return "tpd/noticeList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo() {
        return "tpd/noticeAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, Notice notice, HttpServletRequest request) throws Exception {
    	notice.setCreateUser(getOperaterId());
        noticeService.save(notice);

        modelMap.addAttribute("currentUrl", "/notice/addTo.htm");
        modelMap.addAttribute("backUrl", "/notice/list/main.htm");
        return "commons/success2";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, Integer id) {
    	 modelMap.put("readOnly", 0);
         Notice notice = noticeService.get(id);
         modelMap.put("notice", notice);
         return "tpd/noticeEdit";
    }
    
    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, Notice notice, HttpServletRequest request) throws Exception {
    	notice.setUpdateUser(getOperaterId());
        noticeService.update(notice);

        modelMap.addAttribute("currentUrl", "/notice/editTo.htm?id="+notice.getNoticeId());
        modelMap.addAttribute("backUrl", "/notice/list/main.htm");
        return "commons/success2";
    }
	
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {
        
        modelMap.put("readOnly", 1);
        Notice notice = noticeService.get(Integer.valueOf(id));
        modelMap.put("notice", notice);
        return "tpd/noticeEdit";
    }
    
    @ResponseBody
    @RequestMapping("/audit.htm")
    public String edit(ModelMap modelMap, Notice notice) throws Exception {
    	notice.setAuditUser(getOperaterId());
    	noticeService.update(notice);
    	return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/delete.htm")
    public String delete(String id) {
        noticeService.delete(Integer.valueOf(id));
        return "success";
    }
    
}