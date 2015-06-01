package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Category;

public interface CategoryMapper extends BaseMapper<Category> {

    Integer selectGoodSnNumByCatId(Integer catId);

    Category selectByPrimaryKey(Integer catId);

    Category selectByUniqueKey2(Map<String, Object> param);

    boolean confirmBeingUsed(Integer catId);

    List<Category> queryAll();

    List<Category> selectAllParentIds();
    
    List<Category> querySubCategory();
    /**
     * 根据父ID查询对应的子分类
     */
    List<Category>  selectByParentId(Integer catId);
    
    /**
     * 验证子分类是否存在
     * @param catId
     * @return
     */
    boolean selectSubByParentId(Integer catId);

    @SuppressWarnings("rawtypes")
    List<Map> getAllCategory();
    
    Short findByCatName(String catName);
    
    // ----------------------------------------------
    public List<Integer> selectParentCategoryIdList();

    public List<Integer> selectChildCategoryIdList(Integer catId);
    
}