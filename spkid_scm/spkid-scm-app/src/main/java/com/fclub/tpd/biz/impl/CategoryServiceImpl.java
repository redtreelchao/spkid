/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fclub.cache.CacheDriver;
import com.fclub.common.lang.BizException;
import com.fclub.tpd.biz.CategoryService;
import com.fclub.tpd.dataobject.Category;
import com.fclub.tpd.mapper.CategoryMapper;

/**
 * 商品后台分类服务接口的实现。
 * 
 * @author tianliang.gao
 * @version $Id: CategoryServiceImpl.java, v 0.1 2012-8-7 下午3:37:27 tianliang.gao Exp $
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMap;

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#insertCategory(com.fclub.erp.dal.dataobject.goods.Category)
     */
    @Override
    public void insertCategory(Category record) {
        if (!isUnique("catName", record.getCategoryName(), record.getCategoryId())) {
            throw new BizException("商品分类名称已被使用");
        }
        categoryMap.insert(record);
        
        removeLevel1CategoryIdListFromCache();
        removeSubCategoryIdListFromCache(record.getParentId());
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#deleteCategoryByCatId(java.lang.Integer)
     */
    @Override
    public void deleteCategoryByCatId(Integer catId) {
        if (categoryMap.confirmBeingUsed(catId)) {
            throw new BizException("该商品分类下含有商品，不能删除！");
        }
        if (categoryMap.selectSubByParentId(catId)) {
            throw new BizException("该商品分类含有子分类，不能删除！");
        }
        categoryMap.delete(catId);
        
        removeCategoryFromCache(catId);
        removeLevel1CategoryIdListFromCache();
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#updateCategoryByCatId(com.fclub.erp.dal.dataobject.goods.Category)
     */
    @Override
    public void updateCategoryByCatId(Category record) {
        if (!isUnique("catName", record.getCategoryName(), record.getCategoryId())) {
            throw new BizException("商品分类名称已被使用");
        }

        Category category = categoryMap.selectByPrimaryKey(record.getCategoryId());

        //分类是否含有商品
        boolean beingUsed = categoryMap.confirmBeingUsed(record.getCategoryId());
        //分类是否为父类
        boolean isParent = false;
        //注意：页面的parentId为0时，返回至category对象时值为null(parentId为Integer包装类)
        if (record.getParentId() == null) {
            isParent = true;
        }
        //含有商品的子类或父类含有子类，只更新“排序、关键字、分类描述、管理员ID、时间”
        if ((beingUsed && !isParent) || (isParent && !category.getSubCategoryList().isEmpty())) {
            Category c1 = new Category();
            c1.setCategoryId(record.getCategoryId());
            c1.setSortOrder(record.getSortOrder() == null ? 0 : record.getSortOrder());

            categoryMap.update(c1);
        }
        //不含有商品的子类或父类没有子类，需更新“父类id、分类名称、排序、关键字、分类描述、管理员ID、时间”
        if ((!beingUsed && !isParent) || (isParent && category.getSubCategoryList().isEmpty())) {
            Category c2 = new Category();
            c2.setCategoryId(record.getCategoryId());
            c2.setParentId(record.getParentId());
            c2.setCategoryName(record.getCategoryName());
            c2.setSortOrder(record.getSortOrder() == null ? 0 : record.getSortOrder());

            categoryMap.update(c2);
        }
        
        Integer catId = record.getCategoryId();
        removeCategoryFromCache(catId);
        removeLevel1CategoryIdListFromCache();
        removeSubCategoryIdListFromCache(catId);
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#queryCategoryByCatId(java.lang.Integer)
     */
    @Override
    public Category queryCategoryByCatId(Integer catId) {
        return categoryMap.selectByPrimaryKey(catId);
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#isUnique(java.lang.String, java.lang.String, java.lang.Integer)
     */
    @Override
    public boolean isUnique(String prop, String value, Integer catId) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (catId != null) {
            param.put("catId", catId);
        }
        Category result = new Category();
        if (StringUtils.equals("catName", prop)) {
            param.put("catName", value);
            result = categoryMap.selectByUniqueKey2(param);
        }
        if (result == null) {
            return true;
        }
        return false;
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#confirmBeingUsed(java.lang.Integer, java.lang.String)
     */
    @Override
    public boolean confirmBeingUsed(Integer catId) {
        boolean beingUsed = categoryMap.confirmBeingUsed(catId);
        if (beingUsed) {
            return true;
        }
        return false;
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#queryParentCats()
     */
    @Override
    public List<Category> queryParentCats() {
        return categoryMap.queryAll();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map getCategoryTree() {
        List<Map> list = categoryMap.getAllCategory();
        Map result = new LinkedHashMap();
        for (Map<String, Object> cate : list) {
            if (result.get(cate.get("PARENT_ID")) == null) {
                if (cate.get("PARENT_ID") != null) {
                    result.put(cate.get("PARENT_ID"), cate.get("PARENT_NAME"));
                }
            }
            if (cate.get("CAT_ID") != null) {
                result.put(cate.get("CAT_ID"),
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + cate.get("CAT_NAME"));
            }
        }

        return result;
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#queryChildCats()
     */
    @Override
    public List<Category> queryChildCats() {
        return categoryMap.querySubCategory();
    }

    /** 
     * @see com.fclub.erp.biz.goods.CategoryService#getAllSubCategoryById(java.lang.Integer, boolean)
     */
    @Override
    public Set<Integer> getAllSubCategoryById(Integer catId, boolean containOneself) {
        Set<Integer> cadIds=new HashSet<>();
        if(catId != null && queryCategoryByCatId(catId)!=null){
            if(containOneself){
                cadIds.add(catId);
            }
            appendSubCategoryId(catId, cadIds);
        }
        return cadIds;
    }
    
    /**
     * 附加分类ID至Set
     * @param catId
     * @param cadIds
     */
    private void appendSubCategoryId(int catId,Set<Integer> cadIds){
        if(categoryMap.selectSubByParentId(catId)){
            List<Category> categories=categoryMap.selectByParentId(catId);
            for(Category category: categories){
                int tempId=category.getCategoryId();
                cadIds.add(tempId);
                appendSubCategoryId(tempId, cadIds);
            }
        }
    }
    
    @Override
    public List<Category> queryCategoryTree() {
        List<Integer> level1CategoryIdList = categoryMap.selectParentCategoryIdList();
        if (level1CategoryIdList == null || level1CategoryIdList.isEmpty()) {
            return Collections.emptyList();
        }
        
        Category category = null;
        List<Integer> subCategoryIdList = null;
        List<Category> subCategoryList = null;
        List<Category> categoryList = new ArrayList<Category>(level1CategoryIdList.size());
        for (Integer catId : level1CategoryIdList) {
            category = categoryMap.selectByPrimaryKey(catId);
            if (category == null) {
                continue;
            } else {
                categoryList.add(category);
            }
            
            subCategoryIdList = categoryMap.selectChildCategoryIdList(catId);
            if (subCategoryIdList == null || subCategoryIdList.isEmpty()) {
                continue;
            } else {
                subCategoryList = new ArrayList<Category>(subCategoryIdList.size());
                for (Integer subCatId : subCategoryIdList) {
                    subCategoryList.add(findCachedCategory(subCatId));
                }
                category.setSubCategoryList(subCategoryList);
            }
        }
        
        return categoryList;
    }
    
    
    // ---- 缓存相关操作接口实现 ---------------------------------------------------------------------
    @Autowired
    private CacheDriver cacheDriver;
    
    private static final long CATEGORY_CACHE_ERPIRED_TIME = 7 * 24 * 60 * 60 * 1000L;

    private static final String CATEGORY_CACHE_KEY_PROFIX = "fclub.category.";
    private static final String LEVEL1_CATEGORY_ID_LIST_CACHE_KEY = "fclub.category.level1.idlist";
    private static final String CATEGORY_SUBID_LIST_CACHE_KEY_PREFIX = "fclub.category.sub.idlist.";
    
    public Category findCachedCategory(Integer catId) {
        if (catId == null || catId.intValue() <= 0) {
            return null;
        }
        
        String categoryCacheKey = generateCategoryCacheKey(catId);
        Category category = cacheDriver.get(categoryCacheKey);
        
        if (category == null) {
            category = categoryMap.selectByPrimaryKey(catId);
            if (category != null) {
                cacheDriver.put(categoryCacheKey, category, CATEGORY_CACHE_ERPIRED_TIME);
            }
        }
        
        return category; 
    }
    
    public List<Category> queryCachedCategoryTree() {
        List<Integer> level1CategoryIdList = findLevel1CategoryIdList();
        if (level1CategoryIdList == null || level1CategoryIdList.isEmpty()) {
            return Collections.emptyList();
        }
        
        Category category = null;
        List<Integer> subCategoryIdList = null;
        List<Category> subCategoryList = null;
        List<Category> categoryList = new ArrayList<Category>(level1CategoryIdList.size());
        for (Integer catId : level1CategoryIdList) {
            category = findCachedCategory(catId);
            if (category == null) {
                continue;
            } else {
                categoryList.add(category);
            }
            
            subCategoryIdList = queryCachedSubCategoryIdList(catId);
            if (subCategoryIdList == null || subCategoryIdList.isEmpty()) {
                continue;
            } else {
                subCategoryList = new ArrayList<Category>(subCategoryIdList.size());
                for (Integer subCatId : subCategoryIdList) {
                    subCategoryList.add(findCachedCategory(subCatId));
                }
                category.setSubCategoryList(subCategoryList);
            }
        }
        
        return categoryList;
    }
    
    public List<Integer> queryCachedSubCategoryIdList(Integer catId) {
        if (catId == null || catId.intValue() <= 0) {
            return Collections.emptyList();
        }
        
        String subCategoryIdListCacheKey = generateSubCategoryIdListCacheKey(catId);
        List<Integer> subCategoryIdList = cacheDriver.get(subCategoryIdListCacheKey);
        
        if (subCategoryIdList == null) {
            subCategoryIdList = categoryMap.selectChildCategoryIdList(catId);
            if (subCategoryIdList == null || subCategoryIdList.isEmpty()) {
                subCategoryIdList = new ArrayList<Integer>();
            }
            cacheDriver.put(subCategoryIdListCacheKey, subCategoryIdList, CATEGORY_CACHE_ERPIRED_TIME);
        }
        
        return subCategoryIdList;
    }
    
    private List<Integer> findLevel1CategoryIdList() {
        List<Integer> level1CategoryIdList = cacheDriver.get(LEVEL1_CATEGORY_ID_LIST_CACHE_KEY);
        
        if (level1CategoryIdList == null) {
            level1CategoryIdList = categoryMap.selectParentCategoryIdList();
            if (level1CategoryIdList == null || level1CategoryIdList.isEmpty()) {
                level1CategoryIdList = new ArrayList<Integer>();
            }
            cacheDriver.put(LEVEL1_CATEGORY_ID_LIST_CACHE_KEY, level1CategoryIdList, CATEGORY_CACHE_ERPIRED_TIME);
        }
        
        return level1CategoryIdList;
    }
    
    private String generateCategoryCacheKey(Integer catId) {
        return CATEGORY_CACHE_KEY_PROFIX + catId;
    }
    
    private String generateSubCategoryIdListCacheKey(Integer catId) {
        return CATEGORY_SUBID_LIST_CACHE_KEY_PREFIX + catId;
    }
    
    private void removeCategoryFromCache(Integer catId) {
        cacheDriver.remove(generateCategoryCacheKey(catId));
    }
    
    private void removeSubCategoryIdListFromCache(Integer catId) {
        cacheDriver.remove(generateSubCategoryIdListCacheKey(catId));
    }
    
    private void removeLevel1CategoryIdListFromCache() {
        cacheDriver.remove(LEVEL1_CATEGORY_ID_LIST_CACHE_KEY);
    }
    
}
