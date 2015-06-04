/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据字典<br>
 * Key请使用类名.属性名<br>
 * 页面Select框可使用 #dictSelect($dictKey $eleName $val $validate $showRoot) 来调用<br>
 * 显示值则使用 #dictShow($dictKey $val) 来调用<br>
 * 例：#dictSelect("BatchNotice.coopId" "coopId" $!batchNotice.coopId false false)<br>
 *    #dictShow("BatchNotice.coopId" $!batchNotice.coopId)<br>
 * 具体使用可查看macros.vm，例子可查看品牌运营相关功能
 * 
 * @author baolm
 * @version $Id: DictUtil.java, v 0.1 Dec 21, 2012 1:54:45 PM baolm Exp $
 */
public class DictUtil {

    private static final Map<String, Map<Serializable, String>> DICT_MAP = new HashMap<>();

    static {
        //----------------通用----------------//
        Map<Serializable, String> _Common_tf = new LinkedHashMap<>();
        _Common_tf.put(0, "否");
        _Common_tf.put(1, "是");
        DICT_MAP.put("Common.tf", _Common_tf);
        //----------------通用----------------//
        //----------------订单发运----------------//
        Map<Serializable, String> _ShippingWave_waveStatus = new LinkedHashMap<>();
        _ShippingWave_waveStatus.put(0, "拣货中");
        _ShippingWave_waveStatus.put(1, "部分发货");
        _ShippingWave_waveStatus.put(2, "完全发货");
        DICT_MAP.put("ShippingWave.waveStatus", _ShippingWave_waveStatus);
        
        Map<Serializable, String> _ReportForm = new LinkedHashMap<>();
        _ReportForm.put(4, "第三方发运");
        _ReportForm.put(2, "代销");
        DICT_MAP.put("ReportForm.coop_name", _ReportForm);
        
        Map<Serializable, String> _ShippingPacket_status = new LinkedHashMap<>();
        _ShippingPacket_status.put(0, "拣货中");
        _ShippingPacket_status.put(1, "已发货");
        _ShippingPacket_status.put(2, "缺货");
        DICT_MAP.put("ShippingPacket.status", _ShippingPacket_status);
        Map<Serializable, String> _BatchShipping_type = new LinkedHashMap<>();
        _BatchShipping_type.put(1, "已发货");
        _BatchShipping_type.put(2, "缺货");
        DICT_MAP.put("BatchShipping.type", _BatchShipping_type);
        //----------------订单发运----------------//
        //----------------自助退货----------------//
        Map<Serializable, String> _SelfReturn_type = new LinkedHashMap<>();
        _SelfReturn_type.put(0, "未审核");
        _SelfReturn_type.put(1, "正常审核");
        _SelfReturn_type.put(2, "异常审核");
        DICT_MAP.put("SelfReturn.providerStatus", _SelfReturn_type);
        Map<Serializable, String> _SelfReturnSuggest_suggestType = new LinkedHashMap<>();
        _SelfReturnSuggest_suggestType.put(1, "正常");
        _SelfReturnSuggest_suggestType.put(2, "异常");
        DICT_MAP.put("SelfReturnSuggest.suggestType", _SelfReturnSuggest_suggestType);
        Map<Serializable, String> _SelfReturnGoods_returnReason = new LinkedHashMap<>();
        _SelfReturnGoods_returnReason.put(0, "尺寸偏大");
        _SelfReturnGoods_returnReason.put(1, "尺寸偏小");
        _SelfReturnGoods_returnReason.put(2, "款式不喜欢");
        _SelfReturnGoods_returnReason.put(3, "配送错误");
        _SelfReturnGoods_returnReason.put(4, "其他");
        _SelfReturnGoods_returnReason.put(5, "商品质量问题");
        DICT_MAP.put("SelfReturnGoods.returnReason", _SelfReturnGoods_returnReason);
        //----------------自助退货----------------//
        //----------------工单管理 工单状态----------------//
        Map<Serializable, String> _WorkOrder_type = new LinkedHashMap<>();
        _WorkOrder_type.put("0", "草稿");
        _WorkOrder_type.put("1", "待处理");
        _WorkOrder_type.put("2", "已处理");
        _WorkOrder_type.put("3", "待审核");
        _WorkOrder_type.put("4", "已作废");
        DICT_MAP.put("WorkOrder.woStatus", _WorkOrder_type);
        //----------------工单管理 工单状态----------------//
        //----------------工单管理 工单状态----------------//
        Map<Serializable, String> _WorkOrder_wotype = new LinkedHashMap<>();
        _WorkOrder_wotype.put("01", "妈咪树");
        _WorkOrder_wotype.put("02", "供应商");
        DICT_MAP.put("WorkOrder.woType", _WorkOrder_wotype);
        //----------------工单管理 工单状态----------------//
    }

    /**
     * 获取数据字典
     * 
     * @param dictKey 数据字典Key
     */
    public static Map<Serializable, String> getDictMap(String dictKey) {
        return DICT_MAP.get(dictKey);
    }
    
    /**
     * 获取数据字典的值
     * 
     * @param dictKey 数据字典Key
     * @param key
     */
    public static String getDictValue(String dictKey, Serializable key) {
        return DICT_MAP.get(dictKey).get(key);
    }
}
