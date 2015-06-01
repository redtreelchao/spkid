/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Size;

/**
 * 
 * @author tianliang.gao
 * @version $Id: SizeMapper.java, v 0.1 2012-8-1 下午3:39:17 tianliang.gao Exp $
 */
public interface SizeMapper extends BaseMapper<Size> {

    int insert(Size record);

    int delete(Integer sizeId);

    int update(Size record);

    Size selectByPrimaryKey(Integer sizeId);

    Size selectByUniqueKey1(Map<String, Object> param);

    Size selectByUniqueKey2(Map<String, Object> param);

    boolean confirmBeingUsed(Integer sizeId);
    
    List<Size> queryAll();

    Integer getIdByName(String sizeName);
}
