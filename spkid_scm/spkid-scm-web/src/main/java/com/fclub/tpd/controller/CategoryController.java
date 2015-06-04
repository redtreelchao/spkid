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

import com.fclub.tpd.biz.CategoryService;
import com.fclub.tpd.dataobject.Category;

/**
 * 商品分类控制器
 * @author tianliang.gao
 * @version $Id: CategoryController.java, v 0.1 2012-8-7 下午4:05:04 tianliang.gao Exp $
 */
@Controller
@RequestMapping("/goods/category")
public class CategoryController {

    @Autowired
    private CategoryService catServ;

    @RequestMapping("/list/main.htm")
    public String main(ModelMap modelMap) {
        return "tpd/category";
    }

    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap) {
        List<Category> parentCats = catServ.queryParentCats();
        modelMap.put("parentCats", parentCats);
        return "tpd/categoryList";
    }
}
