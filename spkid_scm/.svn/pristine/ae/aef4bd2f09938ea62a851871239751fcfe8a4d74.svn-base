/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.tpd.biz.ShippingPacketService;
import com.fclub.tpd.biz.ShippingWaveService;
import com.fclub.tpd.common.jxl.ExcelUtil;
import com.fclub.tpd.common.jxl.JxlsUtil;
import com.fclub.tpd.dataobject.ShippingProduct;
import com.fclub.tpd.dataobject.ShippingPacket;
import com.fclub.tpd.dataobject.ShippingWave;
import com.fclub.tpd.dto.ShippingImportDTO;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.tpd.view.ShippingWaveExcelView;
import com.fclub.web.util.UploadUtil;

/**
 * @version $Id: ShippingWaveController.java, v 0.1 2013-06-28 15:30:49 auto-gene Exp $
 */
@Controller
@RequestMapping("/shippingwave")
public class ShippingWaveController {

    private static final LogUtil  logger     = LogUtil.getLogger(ShippingWaveController.class);
    private static final String[] CHARACTERS = new String[] {
    	"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
    	"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    @Autowired
    private ShippingWaveService   shippingWaveService;
    @Autowired
    private ShippingPacketService shippingPacketService;

    @RequestMapping("/list/main.htm")
    public String main(ModelMap modelMap) {
        Integer orderNum = shippingPacketService.getExportOrderNum(SessionHelper.getProvider()
            .getProviderId());
        modelMap.put("orderNum", orderNum);
        return "tpd/shippingWave";
    }

    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<ShippingWave> page, ShippingWave shippingWave) {

        shippingWave.setProviderId(SessionHelper.getProvider().getProviderId());
        page = shippingWaveService.findPage(page, shippingWave);
        modelMap.put("page", page);
        return "tpd/shippingWaveList";
    }

    @RequestMapping("/list/getOrderNum.htm")
    @ResponseBody
    public Integer getOrderNum() {
        try {
            return shippingPacketService.getExportOrderNum(SessionHelper.getProvider()
                .getProviderId());
        } catch (Exception e) {
            logger.warn("get order num error: ", e);
            return 0;
        }
    }

    @RequestMapping(value = "/info/{waveSn}.htm", method = RequestMethod.GET)
    public String info(ModelMap modelMap, @PathVariable String waveSn) {
        modelMap.put("waveSn", waveSn);
        return "tpd/shippingWaveInfo";
    }

    @RequestMapping("/info/query.htm")
    public String queryInfo(ModelMap modelMap, ShippingPacket shippingPacket) {

        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        List<ShippingPacket> shippingPackets = shippingPacketService
            .getShippingPacket(shippingPacket);
        modelMap.put("shippingPackets", shippingPackets);
        return "tpd/shippingWaveInfoList";
    }

    @RequestMapping(value = "/export/{waveSn}.htm", method = RequestMethod.GET)
    public ModelAndView export(ModelMap modelMap, @PathVariable String waveSn) throws Exception {
        ShippingPacket shippingPacket = new ShippingPacket();
        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        shippingPacket.setWaveSn(waveSn);
        List<ShippingPacket> shippingPackets = shippingPacketService
            .getShippingPacket(shippingPacket);
        modelMap.put("waveSn", waveSn);
        modelMap.put("result", shippingPackets);
        return new ModelAndView(new ShippingWaveExcelView());
    }

    @RequestMapping(value = "/export/{waveSn}/format.htm", method = RequestMethod.GET)
    public void export2(HttpServletResponse response, @PathVariable String waveSn) throws Exception {

        ShippingPacket shippingPacket = new ShippingPacket();
        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        shippingPacket.setWaveSn(waveSn);
        List<ShippingProduct> result = shippingPacketService.findExport(shippingPacket);
        if (result == null || result.size() == 0) {
            throw new BizException("没有要导出的资料");
        }
        
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("result", result);
        String fileName = "波次订单导出" + waveSn;
        JxlsUtil.exportExcel(response, fileName, beans, "ShippingOrder.xls");
    }

    @RequestMapping("/generateWave.htm")
    @ResponseBody
    public String generateWave() throws Exception {
        try {
            shippingWaveService.generateWave(SessionHelper.getProvider().getProviderId());
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping(value = "/printWave/{waveSn}.htm", method = RequestMethod.GET)
    public String printWave(ModelMap modelMap, @PathVariable String waveSn) {

        ShippingPacket shippingPacket = new ShippingPacket();
        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        shippingPacket.setWaveSn(waveSn);
        List<ShippingPacket> shippingPackets = shippingPacketService
            .getShippingPacket(shippingPacket);
        shippingWaveService.updatePrintStatus(waveSn);
        modelMap.put("provider", SessionHelper.getProvider());
        modelMap.put("shippingPackets", shippingPackets);

        return "tpd/shippingPrint";
    }

    @RequestMapping(value = "/printOrder/{orderSn}.htm", method = RequestMethod.GET)
    public String printOrder(ModelMap modelMap, @PathVariable String orderSn) {

        ShippingPacket shippingPacket = new ShippingPacket();
        shippingPacket.setProviderId(SessionHelper.getProvider().getProviderId());
        shippingPacket.setOrderSn(orderSn);
        List<ShippingPacket> shippingPackets = shippingPacketService
            .getShippingPacket(shippingPacket);
        modelMap.put("provider", SessionHelper.getProvider());
        modelMap.put("shippingPackets", shippingPackets);

        return "tpd/shippingPrint";
    }

    @RequestMapping("/shipping/main.htm")
    public String shippingMain() {
        return "tpd/batchShipping";
    }

    @RequestMapping("shipping/downloadshipping.htm")
    public void downloadShipping(HttpServletResponse response) {
        try {
            UploadUtil.downloadFromClassPath(response, "orderShipping.xls",
                "已发货批量导入" + DateUtil.getDateFormat("yyyyMMdd").format(new Date()));
        } catch (IOException e) {
            logger.error("download error: ", e);
            throw new BizException("下载失败");
        }
    }

    @RequestMapping("shipping/downloadshortage.htm")
    public void downloadShortage(HttpServletResponse response) {
        try {
            UploadUtil.downloadFromClassPath(response, "orderShortage.xls",
                "缺货批量导入" + DateUtil.getDateFormat("yyyyMMdd").format(new Date()));
        } catch (IOException e) {
            logger.error("download error: ", e);
            throw new BizException("下载失败");
        }
    }

    @RequestMapping(value = "/shipping/import.htm")
    public String importShipping(ModelMap modelMap, HttpServletResponse response,
                                 @RequestParam("file") MultipartFile file, String type) {

        if (StringUtils.isBlank(type)) {
            throw new BizException("无效的类型");
        }

        List<ShippingImportDTO> shippingList = readExcelData(file, type);

        shippingList = shippingWaveService.batchShipping(SessionHelper.getProvider()
            .getProviderId(), type, shippingList);

        modelMap.put("type", type);
        modelMap.put("shippingList", shippingList);
        return "tpd/batchShippingList";
    }

    /**
     * 读取Excel
     */
    private List<ShippingImportDTO> readExcelData(MultipartFile file, String type) {

        List<ShippingImportDTO> shippingList = new ArrayList<>();

        int rowIndex = 0;
        int colIndex = 0;
        try {
            Workbook workbook = ExcelUtil.getWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row root = sheet.getRow(rowIndex);
            if (root == null) {
                throw new BizException("未读取到数据");
            }
            if (StringUtils.equals(type, "2")) {
                if (root.getLastCellNum() != 1) {
                    throw new BizException("缺货导入格式不对，请下载相关模板");
                }
            } else {
                if (root.getLastCellNum() != 3) {
                    throw new BizException("已发货导入格式不对，请下载相关模板");
                }
            }

            Set<String> orderSnSet = new HashSet<>(shippingList.size());
            Set<String> invoiceNoSet = new HashSet<>(shippingList.size());
            for (rowIndex = 1; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                colIndex = 0;
                //第一列为空则next
                if (row == null || row.getCell(colIndex) == null
                    || row.getCell(colIndex).getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }
                ShippingImportDTO dto = new ShippingImportDTO();
                String orderSn = row.getCell(colIndex).getStringCellValue();
                if (StringUtils.isBlank(orderSn)) {
                    throw new BizException("订单号不能为空");
                }
                dto.setOrderSn(orderSn);
                if (StringUtils.equals(type, "1")) {
                    String shippingCode = row.getCell(++colIndex).getStringCellValue();
                    if (StringUtils.isBlank(shippingCode)) {
                        throw new BizException("物流公司不能为空");
                    }
                    dto.setShippingCode(shippingCode.trim());
                    if (row.getCell(++colIndex).getCellType() == Cell.CELL_TYPE_STRING) {
                        String invoiceNo = row.getCell(colIndex).getStringCellValue();
                        if(StringUtils.isNotBlank(invoiceNo)) {
                            dto.setInvoiceNo(invoiceNo.trim());
                        }
                    } else if (row.getCell(colIndex).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        BigDecimal tmp = new BigDecimal(row.getCell(colIndex).getNumericCellValue());
                        tmp = tmp.setScale(0, BigDecimal.ROUND_DOWN);
                        dto.setInvoiceNo(tmp.toString());
                    }
                    if (StringUtils.isBlank(dto.getInvoiceNo())) {
                        throw new BizException("运单号不能为空");
                    }
                }
                shippingList.add(dto);
                
                // 校验订单号和物流单号不能重复
                if (orderSnSet.contains(dto.getOrderSn())) {
                	throw new BizException("订单号不能重复");
                } else if (invoiceNoSet.contains(dto.getInvoiceNo())) {
                	throw new BizException("运单号不能重复");
                } else {
                	orderSnSet.add(dto.getOrderSn());
                	invoiceNoSet.add(dto.getInvoiceNo());
                }
            }
        } catch (Exception e) {
            logger.error("read file data error [ row={0}, col={1} ] :", (rowIndex + 1),
                CHARACTERS[colIndex], e);
            throw new BizException("读取文件错误 [ 第 " + (rowIndex + 1) + " 行, 第 " + CHARACTERS[colIndex]
                                   + " 列 ] : " + e.getMessage());
        }
        return shippingList;
    }

}