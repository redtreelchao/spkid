/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.mapper;

import java.util.List;
import java.util.Map;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Season;

/**
 * 
 * @author tianliang.gao
 * @version $Id: SeasonMapper.java, v 0.1 2012-7-25 上午10:04:17 tianliang.gao Exp $
 */
public interface SeasonMapper extends BaseMapper<Season> {

    int insert(Season record);

    int deleteByPrimaryKey(Integer seasonId);

    int updateByPrimaryKey(Season record);

    Season selectByPrimaryKey(Integer seasonId);

    boolean confirmBeingUsed(Integer seasonId);

    Season selectByUniqueKey1(Map<String, Object> param);

    Season selectByUniqueKey2(Map<String, Object> param);
    
    List<Season> queryAll();
    
    Integer findBySeasonCode(String seasonCode);
}
