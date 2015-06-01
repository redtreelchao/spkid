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
import com.fclub.tpd.biz.ProductSubService;
import com.fclub.tpd.dataobject.ProductSub;
import com.fclub.common.lang.BizException;

/**
 * 
 * @author auto-gene
 * @version $Id: GoodsLaborController.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Controller
@RequestMapping("/goodslabor")
public class ProductSubController {

    @Autowired
    private ProductSubService goodsLaborService;
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/goodsLabor";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<ProductSub> page, ProductSub goodsLabor) {

        page = goodsLaborService.findPage(page, goodsLabor);
        modelMap.put("page", page);
        return "tpd/goodsLaborList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo() {
        return "tpd/goodsLaborAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, ProductSub goodsLabor) throws Exception {

        goodsLaborService.save(goodsLabor);

        modelMap.addAttribute("currentUrl", "/goodslabor/addTo.htm");
        modelMap.addAttribute("backUrl", "/goodslabor/list/main.htm");
        return "commons/success2";
    }
	
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {
        
        modelMap.put("readOnly", 1);
        ProductSub goodsLabor = goodsLaborService.get(Integer.valueOf(id));
        modelMap.put("goodsLabor", goodsLabor);
        return "tpd/goodsLaborEdit";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, String id) {

        modelMap.put("readOnly", 0);
        ProductSub goodsLabor = goodsLaborService.get(Integer.valueOf(id));
        modelMap.put("goodsLabor", goodsLabor);
        return "tpd/goodsLaborEdit";
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, ProductSub goodsLabor) throws Exception {
        goodsLaborService.update(goodsLabor);
        modelMap.addAttribute("backUrl", "/goodslabor/list/main.htm");
        return "commons/success2";
    }

    @RequestMapping(value = "/delete.htm")
    @ResponseBody
    public String delete(String id) {

        try {
            goodsLaborService.delete(Integer.valueOf(id));
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
}