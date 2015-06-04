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
import com.fclub.tpd.biz.ProductGalleryService;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.common.lang.BizException;

/**
 * 
 * @author auto-gene
 * @version $Id: GoodsGalleryController.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Controller
@RequestMapping("/goodsgallery")
public class ProductGalleryController {

    @Autowired
    private ProductGalleryService goodsGalleryService;
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/goodsGallery";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<ProductGallery> page, ProductGallery goodsGallery) {

        page = goodsGalleryService.findPage(page, goodsGallery);
        modelMap.put("page", page);
        return "tpd/goodsGalleryList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo() {
        return "tpd/goodsGalleryAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, ProductGallery goodsGallery) throws Exception {

        goodsGalleryService.save(goodsGallery);

        modelMap.addAttribute("currentUrl", "/goodsgallery/addTo.htm");
        modelMap.addAttribute("backUrl", "/goodsgallery/list/main.htm");
        return "commons/success2";
    }
	
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {
        
        modelMap.put("readOnly", 1);
        ProductGallery goodsGallery = goodsGalleryService.get(Integer.valueOf(id));
        modelMap.put("goodsGallery", goodsGallery);
        return "tpd/goodsGalleryEdit";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, String id) {

        modelMap.put("readOnly", 0);
        ProductGallery goodsGallery = goodsGalleryService.get(Integer.valueOf(id));
        modelMap.put("goodsGallery", goodsGallery);
        return "tpd/goodsGalleryEdit";
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, ProductGallery goodsGallery) throws Exception {
        goodsGalleryService.update(goodsGallery);
        modelMap.addAttribute("backUrl", "/goodsgallery/list/main.htm");
        return "commons/success2";
    }

    @RequestMapping(value = "/delete.htm")
    @ResponseBody
    public String delete(String id) {

        try {
            goodsGalleryService.delete(Integer.valueOf(id));
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
}