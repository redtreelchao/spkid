/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.view;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.fclub.common.lang.utils.DateUtil;
import com.fclub.tpd.common.DictUtil;
import com.fclub.tpd.dataobject.ShippingProduct;
import com.fclub.tpd.dataobject.ShippingPacket;

/**
 * 
 * @author likaiping
 * @version $Id: ViewExcel.java, v 0.1 Sep 3, 2012 4:33:56 PM likaiping Exp $
 */
public class ShippingWaveExcelView extends AbstractExcelView {

    @SuppressWarnings("unchecked")
    public void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
                                   HttpServletRequest request, HttpServletResponse response)
                                                                                            throws Exception {
        
        //设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
        response.setContentType("APPLICATION/OCTET-STREAM");

        String waveSn = (String) model.get("waveSn");
        List<ShippingPacket> packetList = (List<ShippingPacket>) model.get("result");
        
        HSSFSheet sheet = workbook.createSheet("wave");
        sheet.setDefaultColumnWidth(18);

        int rowIndex = 0;
        int colIndex = 0;
        
        HSSFRow row = sheet.createRow(rowIndex++);
        if(StringUtils.isNotBlank(waveSn)) {
            row.createCell(0).setCellValue("波次号：");
            row.createCell(1).setCellValue(waveSn);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + waveSn + ".xls\"");
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"订单导出" + DateUtil.getDateFormat().format(new Date()) + ".xls\"");
        }
        
        DateFormat df = DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for(ShippingPacket packet : packetList) {
            rowIndex++; // 空一行
            row = sheet.createRow(rowIndex++);
            colIndex = 0;
            row.createCell(colIndex++).setCellValue("订单号");
            row.createCell(colIndex++).setCellValue("运单号");
            row.createCell(colIndex++).setCellValue("物流公司");
            row.createCell(colIndex++).setCellValue("订单客审时间");
            row.createCell(colIndex++).setCellValue("订单导出时间");
            row.createCell(colIndex++).setCellValue("订单发货时间");
            row.createCell(colIndex++).setCellValue("发货状态");
            row.createCell(colIndex++).setCellValue("送货地址");
            row.createCell(colIndex++).setCellValue("配送地址");
            row.createCell(colIndex++).setCellValue("收件人");
            row.createCell(colIndex++).setCellValue("手机号码");
            row.createCell(colIndex++).setCellValue("电话号码");
            row.createCell(colIndex++).setCellValue("预约送货时间");
            row.createCell(colIndex++).setCellValue("客户备注");
            
            row = sheet.createRow(rowIndex++);
            colIndex = 0;
            row.createCell(colIndex++).setCellValue(packet.getOrderSn());
            row.createCell(colIndex++).setCellValue(packet.getPacketSn());
            row.createCell(colIndex++).setCellValue(packet.getShippingName());
            if(packet.getCreateTime() != null) {
                row.createCell(colIndex++).setCellValue(df.format(packet.getCreateTime()));
            } else {
            	row.createCell(colIndex++).setCellValue("");
            }
            if(packet.getOrderConfirmTime() != null) {
                row.createCell(colIndex++).setCellValue(df.format(packet.getOrderConfirmTime()));
            } else {
            	row.createCell(colIndex++).setCellValue("");
            }
            if(packet.getFinishTime() != null) {
                row.createCell(colIndex++).setCellValue(df.format(packet.getFinishTime()));
            } else {
            	row.createCell(colIndex++).setCellValue("");
            }
            row.createCell(colIndex++).setCellValue(DictUtil.getDictValue("ShippingPacket.status", packet.getStatus()));
            row.createCell(colIndex++).setCellValue(packet.getRegion());
            row.createCell(colIndex++).setCellValue(packet.getAddress());
            row.createCell(colIndex++).setCellValue(packet.getConsignee());
            row.createCell(colIndex++).setCellValue(packet.getMobile());
            row.createCell(colIndex++).setCellValue(packet.getTel());
            row.createCell(colIndex++).setCellValue(packet.getBestTime());
            row.createCell(colIndex++).setCellValue(packet.getUserNotice());
            
            row = sheet.createRow(rowIndex++);
            colIndex = 0;
            row.createCell(colIndex++).setCellValue("商品款号");
            row.createCell(colIndex++).setCellValue("商品名称");
            row.createCell(colIndex++).setCellValue("品牌");
            row.createCell(colIndex++).setCellValue("供应商货号");
            row.createCell(colIndex++).setCellValue("颜色");
            row.createCell(colIndex++).setCellValue("尺码");
            row.createCell(colIndex++).setCellValue("商品数量");
            row.createCell(colIndex++).setCellValue("售价");
            row.createCell(colIndex++).setCellValue("商品条码");
            for(ShippingProduct product : packet.getProductList()) {
                row = sheet.createRow(rowIndex++);
                colIndex = 0;
                row.createCell(colIndex++).setCellValue(product.getSku());
                row.createCell(colIndex++).setCellValue(product.getProductName());
                row.createCell(colIndex++).setCellValue(product.getBrandName());
                row.createCell(colIndex++).setCellValue(product.getProviderProductcode());
                row.createCell(colIndex++).setCellValue(product.getColorName());
                row.createCell(colIndex++).setCellValue(product.getSizeName());
                row.createCell(colIndex++).setCellValue(product.getProductNum());
                row.createCell(colIndex++).setCellValue(product.getShopPrice().doubleValue());
                row.createCell(colIndex++).setCellValue(product.getProviderBarcode());
            }
        }

    }
}
