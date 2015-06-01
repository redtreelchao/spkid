/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.tpd.biz.ProductCardService;
import com.fclub.tpd.dataobject.ProductCard;

/**
 * 
 * @author auto-gene
 * @version $Id: ProductCardController.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Controller
@RequestMapping("/productcard")
public class ProductCardController extends BaseController {

	@Autowired
    private ProductCardService productCardService;
    
    @RequestMapping("/list/main.htm")
    public String main(ModelMap modelMap, String productSn) {
    	modelMap.put("productSn", productSn);
        return "tpd/productCard";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<ProductCard> page, ProductCard productCard) {
    	if (!isAdmin()) {
    		productCard.setProviderId(getProviderId());
    	}
    	page = productCardService.findPage(page, productCard);
        modelMap.put("page", page);
        return "tpd/productCardList";
    }
    
    @RequestMapping(value = "/edit.json")
    @ResponseBody
    public String edit(String id) {
        try {
        	ProductCard productCard = new ProductCard();
        	productCard.setCardId(Integer.valueOf(id));
        	productCard.setIsUsed(true);
        	productCard.setUseTime(DateUtil.getCurrentTime());
        	productCardService.update(productCard);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
    @RequestMapping(value = "/batchEdit.json")
    @ResponseBody
    public String batchEdit(String ids) {
        try {
        	productCardService.batchUpdate(ids);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping(value = "/delete.json")
    @ResponseBody
    public String delete(String id) {
        try {
        	ProductCard productCard = productCardService.get(Integer.valueOf(id));
        	if (productCard == null) {
        		return "卡密不存在！";
        	}
        	if (productCard.getIsUsed()) {
        		return "卡密已使用，不能删除！";
        	}
        	productCardService.delete(Integer.valueOf(id));
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
    @RequestMapping(value = "/batchDelete.json")
    @ResponseBody
    public String batchDelete(String ids) {
        try {
        	productCardService.batchDelete(ids);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
    @RequestMapping("/list/export.htm")
    public void export(ProductCard productCard, HttpServletResponse response) {
    	if (!isAdmin()) {
    		productCard.setProviderId(getProviderId());
    	}
    	productCardService.export(productCard, response);
    }
}