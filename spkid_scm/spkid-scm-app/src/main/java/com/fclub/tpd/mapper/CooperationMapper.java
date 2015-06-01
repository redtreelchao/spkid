package com.fclub.tpd.mapper;

import java.util.List;

import com.fclub.tpd.dataobject.Cooperation;

public interface CooperationMapper {
    List<Cooperation> selectCooperationByPage();
    
    int deleteByPrimaryKey(Integer coopId);

    int insert(Cooperation record);

    int insertSelective(Cooperation record);

    Cooperation selectByPrimaryKey(Integer coopId);

    int updateByPrimaryKeySelective(Cooperation record);

    int updateByPrimaryKey(Cooperation record);
    
    boolean confirmBeingUsed(Integer coopId);

    /**
     * 
     * @param coopCode
     * @return
     */
    Cooperation selectByCoopCode(Cooperation record);

    /**
     * 
     * @param coopName
     * @return
     */
    Cooperation selectByCoopName(Cooperation record);
    
    Integer findByCoopCode(String coopCode);
}