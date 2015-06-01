/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.biz;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fclub.tpd.dataobject.Category;

/**
 * 商品分类（后台分类）管理Service
 * @author tianliang.gao
 * @version $Id: CategoryService.java, v 0.1 2012-8-7 下午3:21:39 tianliang.gao Exp $
 */
public interface CategoryService {

    /**
     * @author tianliang.gao
     * 功能：添加分类
     * @param record
     */
    void insertCategory(Category record);

    /**
     * @author tianliang.gao
     * 功能：删除分类
     * @param catId
     */
    void deleteCategoryByCatId(Integer catId);

    /**
     * @author tianliang.gao
     * 功能：修改分类
     * @param record
     */
    void updateCategoryByCatId(Category record);

    /**
     * @author tianliang.gao
     * 功能：根据catId查询分类
     * @param catId
     * @return
     */
    Category queryCategoryByCatId(Integer catId);

    /**
     * @author tianliang.gao
     * 功能：查询所有父分类
     * @return
     */
    List<Category> queryParentCats();

    /**
     * @author tianliang.gao
     * 功能：唯一性检查
     * @param prop
     * @param value
     * @param catId
     * @return
     */
    boolean isUnique(String prop, String value, Integer catId);

    /**
     * @author tianliang.gao
     * 功能：确认分类是否被使用
     * @param catId
     * @return
     */
    boolean confirmBeingUsed(Integer catId);
    
    @SuppressWarnings("rawtypes")
    Map getCategoryTree();
    
    /**
     * @author tianliang.gao
     * 功能：查询所有子分类，按cat_id从大到小排序
     * @return
     */
    List<Category> queryChildCats();

    /**
     * 获取所有子分类ID
     * @param catId 分类ID 
     * @param containOneself 是否包含自己
     * @return 分类ID集合
     */
    Set<Integer> getAllSubCategoryById(Integer catId,boolean containOneself);

    List<Category> queryCategoryTree();
    
    // ---- 缓存相关操作接口定义 ----------------------------------------------
    public Category findCachedCategory(Integer catId);
    
    public List<Category> queryCachedCategoryTree(); 
    
    public List<Integer> queryCachedSubCategoryIdList(Integer catId);
    
}
