/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.FileUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.biz.BcsImpService;
import com.fclub.tpd.biz.CategoryService;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.dataobject.BcsImp;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.tpd.mapper.BrandMapper;
import com.fclub.web.util.UploadUtil;

/**
 * @version $Id: BcsImpController.java, v 0.1 2013-08-20 14:56:57 auto-gene Exp $
 */
@Controller
@RequestMapping("/bcsimp")
public class BcsImpController {

    @Autowired
    private BcsImpService bcsImpService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryService     categoryService;
    
    /** 尺寸图宽度 */
    public static final int BCS_IMAGE_WIDTH = 700;
    
    public static final Map<String, String>  SEX_MAP = new LinkedHashMap<String, String>();
    static {
        SEX_MAP.put("m", "男");
        SEX_MAP.put("w", "女");
        SEX_MAP.put("a", "全部");
    }
    
    @RequestMapping("/list/main.htm")
    public String main(ModelMap modelMap) {
    	initPage(modelMap);
        return "tpd/bcsImp";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<BcsImp> page, BcsImp bcsImp) {
        bcsImp.setProviderId(SessionHelper.getProvider().getProviderId());
    	page = bcsImpService.findPage(page, bcsImp);
        modelMap.put("page", page);
        return "tpd/bcsImpList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo(ModelMap modelMap) {
    	initPage(modelMap);
        return "tpd/bcsImpAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, BcsImp bcsImp, @RequestParam("file") MultipartFile imageFile) throws Exception {

    	if (bcsImp.getBrandId() == null || bcsImp.getCatId() == null || StringUtil.isBlank(bcsImp.getSex())
    			|| bcsImp.getBrandId() == 0 || bcsImp.getCatId() == 0) {
    		throw new BizException("必填项不得为空！");
    	}
    	
    	if (bcsImpService.checkExists(bcsImp) != null) {
    		throw new BizException("此品牌类别性别尺寸图已导入！");
    	}
    	
    	Integer createAdmin = SessionHelper.getProvider().getProviderId();
    	Date createTime = DateUtil.getCurrentTime();
    	
    	bcsImp.setCreateUser(createAdmin);
    	bcsImp.setCreateTime(createTime);
    	
        try {
        	bcsImp.setImageUrl(doUploadImg(imageFile, bcsImp));
        	bcsImp.setImpStatus(1);
        } catch (Exception e) {
        	bcsImp.setImpStatus(0);
        	throw e;
        }
        
        try {
        	bcsImpService.saveOpt(bcsImp);
        } catch (Exception e) {
        	doDeleteImg(bcsImp.getImageUrl());
        	throw e;
        }

        modelMap.addAttribute("currentUrl", "/bcsimp/addTo.htm");
        modelMap.addAttribute("backUrl", "/bcsimp/list/main.htm");
        return "commons/success2";
    }
	
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {
        
        modelMap.put("readOnly", 1);
        BcsImp bcsImp = bcsImpService.get(Integer.valueOf(id));
        modelMap.put("bcsImp", bcsImp);
        return "tpd/bcsImpEdit";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, String id) {

    	initPage(modelMap);
    	modelMap.put("readOnly", 0);
        BcsImp bcsImp = bcsImpService.get(Integer.valueOf(id));
        modelMap.put("bcsImp", bcsImp);
        return "tpd/bcsImpEdit";
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, BcsImp bcsImp, @RequestParam("file") MultipartFile imageFile) throws Exception {
    	bcsImp = bcsImpService.get(bcsImp.getImpId());
    	String imageUrl = bcsImp.getImageUrl();
    	
    	try {
        	bcsImp.setImageUrl(doUploadImg(imageFile, bcsImp));
        	bcsImp.setImpStatus(1);
        } catch (Exception e) {
        	bcsImp.setImageUrl(imageUrl);
        	bcsImp.setImpStatus(0);
        	throw e;
        }
        
        try {
        	bcsImpService.updateOpt(bcsImp);
        } catch (Exception e) {
        	doDeleteImg(bcsImp.getImageUrl());
        	throw e;
        }
        if (bcsImp.getImpStatus() == 1) {
        	doDeleteImg(imageUrl);
        }
    	
        modelMap.addAttribute("backUrl", "/bcsimp/list/main.htm");
        return "commons/success2";
    }

    @RequestMapping(value = "/delete.htm")
    @ResponseBody
    public String delete(String id) {

        BcsImp bcsImp = bcsImpService.get(Integer.valueOf(id));
        String imageUrl = bcsImp.getImageUrl();
    	try {
            bcsImpService.deleteOpt(bcsImp);
        } catch (BizException e) {
            return e.getMessage();
        }
        doDeleteImg(imageUrl);
        
        return "success";
    }
    
    private void initPage(ModelMap modelMap) {
    	List<Brand> brandList = brandMapper.findBrandsByProviderId(SessionHelper.getProvider().getProviderId());
    	modelMap.put("brandList", brandList);
    	modelMap.put("categoryList", categoryService.queryCachedCategoryTree());
    	modelMap.put("sexMap", SEX_MAP);
    }
    
    private String doUploadImg(MultipartFile imageFile, BcsImp bcsImp) {
        String imageName = null;
        
        try {
        	BufferedImage bi = ImageIO.read(imageFile.getInputStream());
            if (bi.getWidth() != BCS_IMAGE_WIDTH) {
            	throw new BizException("上传尺寸图宽度 " + bi.getWidth() + " 不等于 " + BCS_IMAGE_WIDTH);
            }
        } catch (IOException e) {
            throw new BizException("上传图片出错");
        }
        
        try {
            String imageBcsInPath = ConstantsHelper.getPicRootPath() + System.getProperty(SystemConstant.UPLOAD_IMAGE_BCS);
            String fileName = bcsImp.getBrandId() + "_" + bcsImp.getCatId() + "_" + bcsImp.getSex() + "_" + DateUtil.getCurrentTime().getTime();
            imageName = UploadUtil.uploadFile(imageFile, imageBcsInPath, fileName);
        } catch (IOException e) {
            throw new BizException("上传图片出错");
        }
        
        return System.getProperty(SystemConstant.UPLOAD_IMAGE_BCS) + "/" + imageName;
	}
    
    private void doDeleteImg(String imgUrl) {
    	if (StringUtil.isEmpty(imgUrl)) {
    		return;
    	}
    	
    	File imageFile = new File((ConstantsHelper.getPicRootPath() + imgUrl));
    	if (imageFile.exists() && imageFile.isFile()) {
    		FileUtil.deleteFile(imageFile);
    	}
	}
    
}