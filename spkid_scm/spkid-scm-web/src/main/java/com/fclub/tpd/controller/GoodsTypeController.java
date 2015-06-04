/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fclub.common.dal.Page;
import com.fclub.tpd.biz.ProductTypeService;
import com.fclub.tpd.dataobject.ProductType;

/**
 * 
 * @author yinglin.dan
 * @version $Id: GoodsTypeController.java, v 0.1 2012-8-17 下午2:30:30 yinglin.dan Exp $
 */
@Controller
@RequestMapping("/goods/type")
public class GoodsTypeController {
    @Autowired
    private ProductTypeService goodsTypeService;
    
    @RequestMapping("/list/main.htm")
    public String main(ModelMap modelMap) {
        modelMap.put("levelOneTypes", goodsTypeService.getLevelOneTypes());
        return "tpd/type";
    }

    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<ProductType> page, ProductType goodsType,
                        @RequestParam(value="keyWord",required=false) String keyWord,
                        @RequestParam(value="goods_type_1",required=false) int goods_type_1) {
       
        page = goodsTypeService.queryGoodsTypeByPage(page, goodsType,keyWord.trim(),goods_type_1);
        modelMap.put("page", page);
        return "tpd/typeList";
    }
    
    @RequestMapping("/list/getChildTypes.htm")
    @ResponseBody
    public Map<Integer, String> getChildTypes(String key, String value) {
        
        Map<Integer, String> result = new LinkedHashMap<>();
        List<ProductType> list = new ArrayList<>();
        if (StringUtils.equals(key, "levelOne")) {
            list = goodsTypeService.getLevelTwoTypes(Integer.valueOf(value));
        } else if (StringUtils.equals(key, "levelTwo")) {
            list = goodsTypeService.getLevelThreeTypes(Integer.valueOf(value));
        }
        for(ProductType type : list) {
            result.put(type.getTypeId(), type.getTypeName());
        }
        return result;
    }
}
