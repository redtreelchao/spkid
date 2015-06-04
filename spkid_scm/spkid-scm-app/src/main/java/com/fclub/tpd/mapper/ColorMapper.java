package com.fclub.tpd.mapper;

import java.util.Map;

import com.fclub.common.dal.mapper.BaseMapper;
import com.fclub.tpd.dataobject.Color;

public interface ColorMapper extends BaseMapper<Color> {
    
    Integer queryByCode(Map<String, Object> param);
    
    Integer queryByName(Map<String, Object> param);
    
    Color selectByName(String colorName);
    
}