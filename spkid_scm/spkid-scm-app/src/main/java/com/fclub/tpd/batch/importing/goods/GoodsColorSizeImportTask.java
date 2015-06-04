/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.goods;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.RandomUtils;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.batch.importing.AbstractUploadImportTask;
import com.fclub.tpd.batch.importing.dto.GoodsColorSize;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.batch.importing.helper.ImportTaskHelper;
import com.fclub.tpd.common.jxl.ExcelUtil;
import com.fclub.tpd.dataobject.Color;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductSub;
import com.fclub.tpd.dataobject.Size;
import com.fclub.tpd.mapper.ColorMapper;
import com.fclub.tpd.mapper.ProductSubMapper;
import com.fclub.tpd.mapper.ProductMapper;
import com.fclub.tpd.mapper.SizeMapper;

/**
 * @version $Id: GoodsColorSizeImportTask.java, v 0.1 Jul 15, 2013 1:55:57 PM likaiping Exp $
 */
public class GoodsColorSizeImportTask extends AbstractUploadImportTask {
    @Autowired
    private ProductMapper      goodsMapper;
    @Autowired
    private ColorMapper      colorMapper;
    @Autowired
    private SizeMapper       sizeMapper;
    @Autowired
    private ProductSubMapper goodsLaborMapper;

    /** 
     * @see com.fclub.erp.biz.importing.AbstractUploadImportTask#execute()
     */
    @Override
    protected ImportResult execute() {
        File file = ImportTaskHelper.getHistoricalFile(importContext);
        List<Integer> goodsLaborIds = new ArrayList<Integer>();
        //完全成功状态
        boolean success = true;
        List<GoodsColorSize> goodsColorSizes = new ArrayList<>();
        InputStream input = null;
        try {
            List<String[]> list = ExcelUtil.readLines(file);
            // 读取第一行标题
            int i = 0;
            for (String[] line : list) {
                i++;
                if (i == 1) {
                    continue;
                }
                if (line.length < 1) {
                    continue;
                }
                if (line[0] == null || line[1] == null || line[2] == null || line[3] == null)
                    continue;
                GoodsColorSize colorSizeCSV = new GoodsColorSize(line);
                goodsColorSizes.add(colorSizeCSV);
            }
        } catch (IOException e) {
            logger.error("打开文件错误", e);
            return ImportTaskHelper.genResultError("系统错误，请联系管理员！");
        } finally {
            IOUtils.closeQuietly(input);
        }
        int i = 0;
        for (GoodsColorSize colorSizeCSV : goodsColorSizes) {
            i++;
            setProgress(goodsColorSizes.size(), i);
            ProductSub labor = new ProductSub();
            // 校验商品款号
            Integer goodsId = null;
            if (StringUtil.isBlank(colorSizeCSV.getGoodsSn())) {
                colorSizeCSV.getError().append("商品款号不能为空\n");
            } else {
                if (colorSizeCSV.getGoodsSn().length() < 10) {
                    colorSizeCSV.getError().append("商品款号长度必须大于或等于10位\n");
                } else {
                    goodsId = goodsMapper.findByGoodsSn(colorSizeCSV.getGoodsSn());
                    if (goodsId == null || goodsId <= 0) {
                        colorSizeCSV.getError().append("商品款号不存在\n");
                    } else {
                        labor.setGoodsId(goodsId);
                    }
                }
            }

            if (StringUtil.isBlank(colorSizeCSV.getError().toString())) {
                Product goods = goodsMapper.get(goodsId);
                if (StringUtil.notEquals(goods.getTpdGoodsStatus(), "0")) {
                    colorSizeCSV.getError().append("该款商品已经提交审核不允许设置颜色尺寸\n");
                }
            }
            Map<String, Object> param = new HashMap<String, Object>();
            Size size = null;
            Color color = null;
            if (StringUtil.isBlank(colorSizeCSV.getError().toString())) {
            	String colorName = colorSizeCSV.getColorCode();
                if (StringUtil.isBlank(colorName)) {
                    colorSizeCSV.getError().append("颜色名称不能为空\n");
                } else if (colorSizeCSV.getColorCode().length() > 3) {
                    colorSizeCSV.getError().append("颜色名称最大长度为3位\n");
                } else {
                    param.put("colorName", colorName);
                    color = colorMapper.selectByName(colorName);
                    if (color == null) {
                        color = new Color();
                        color.setGroupCode(0);
                        color.setColorColor("#FFFFFF");
                        color.setColorAid(importContext.getProviderId());
                        color.setColorTime(new Date());
                        color.setColorImg(colorSizeCSV.getColorCode() + ".jpg");
                        color.setColorCode(""); //不使用
                        color.setColorName(colorSizeCSV.getColorCode());
                        try {
                            colorMapper.insert(color);
                        } catch (Exception e) {
                            logger.error("生成颜色记录错误", e);
                            throw new BizException("生成颜色记录错误");
                        }
                    }
                    labor.setColorId(color.getColorId());

                }
            }
            // 校验尺寸编码
            param.clear();
            if (StringUtil.isBlank(colorSizeCSV.getError().toString())) {
                if (StringUtil.isBlank(colorSizeCSV.getSizeCode())) {
                    colorSizeCSV.getError().append("尺寸名称不能为空\n");
                } else if (colorSizeCSV.getSizeCode().length() > 5) {
                    colorSizeCSV.getError().append("尺寸名称最大长度为5位\n");
                } else {
                    param.put("sizeName", colorSizeCSV.getSizeCode());
                    size = sizeMapper.selectByUniqueKey2(param);
                    if (size == null || size.getSizeId() <= 0) {
                        size = new Size();
                        size.setSizeAid((short) importContext.getProviderId());
                        size.setSizeCode(""); // 不使用
                        size.setSizeName(colorSizeCSV.getSizeCode());
                        size.setSizeTime(new Date());
                        size.setSortOrder((short) 0);
                        try {
                            sizeMapper.insert(size);
                        } catch (Exception e) {
                            logger.error("生成尺寸记录错误", e);
                            throw new BizException("生成尺寸记录错误");
                        }
                    }
                    labor.setSizeId(size.getSizeId());
                }
            }

            // 校验某款商品是否存在（颜色和尺寸）
            param.clear();
            if (StringUtil.isBlank(colorSizeCSV.getError().toString())) {
                param.put("goodsId", goodsId);
                param.put("colorId", color.getColorId());
                param.put("sizeId", size.getSizeId());
                Integer colorSizeCount = goodsLaborMapper.findByGoodsIdColorIdSizeId(param);
                if (colorSizeCount >= 1) {
                    colorSizeCSV.getError().append("该颜色尺寸的商品已经存在\n");
                }
            }
            param.clear();
            // XXX: 支持一款多色导入
//            if (StringUtil.isBlank(colorSizeCSV.getError().toString())) {
//                param.put("goodsId", goodsId);
//                param.put("colorId", colorId);
//                Integer colorCount = goodsLaborMapper.findByGoodsIdWithOutColorId(param);
//                if (colorCount >= 1) {
//                    String msg = "商品已经存在其他颜色，系统暂不支持一款多色";
//                    colorSizeCSV.getError().append(msg).append("\n");
//                    addResultLog(colorSizeCSV);
//                    throw new BizException(msg);
//                }
//            }
            
            if (StringUtil.isBlank(colorSizeCSV.getError().toString())) {
                // 校验条形码
            	labor.setProviderBarcode(doGenerateProviderBarcode(colorSizeCSV.getGoodsSn(), color.getColorId(), size.getSizeId()));
                labor.setTpdProviderBarcode(colorSizeCSV.getBarCode());
                // 获得插入记录的id
                labor.setIsPic(0);
                labor.setConsignNum(0);
                labor.setSortOrder(0);
                goodsLaborMapper.insert(labor);
                goodsLaborIds.add(labor.getGlId());
            } else {
                success = success ? false : success;
            }
            addResultLog(colorSizeCSV);
        }
        if (!success) {
            return ImportTaskHelper.genResult(null, ImportStatus.FAIL, "导入失败");
        }
        return ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "导入完成");
    }

    @Override
    protected boolean doValidation(ImportContext importContext) {
        if (importContext.getImportType() == ImportType.GOODS_COLOR_SIZE) {
            return true;
        }
        return false;
    }

    @Override
    protected String getResultTemplateName() {
        return "import/result/colorsize_reg.vm";
    }

    public String generateSizeCode() {
        String sizeCode = null;
        for (int i = 0; i < 100; i++) {
            String firstNum = RandomUtils.generateUpperString(1);
            String nextNum = RandomUtils.generateString(2).toUpperCase();
            sizeCode = firstNum + nextNum;
            if (!sizeIsUnique("sizeCode", sizeCode, null)) {
                if (i == 99) {
                    throw new BizException("系统无法生成尺寸编码，请联系管理员");
                }
                continue;
            }
            break;
        }
        return sizeCode;
    }

    public boolean sizeIsUnique(String prop, String value, Integer sizeId) {
        Map<String, Object> param = new HashMap<>();
        if (sizeId != null) {
            param.put("sizeId", sizeId);
        }
        Size result = new Size();
        if (StringUtils.equals(prop, "sizeCode")) {
            param.put("sizeCode", value);
            result = sizeMapper.selectByUniqueKey1(param);
        } else if (StringUtils.equals(prop, "sizeName")) {
            param.put("sizeName", value);
            result = sizeMapper.selectByUniqueKey2(param);
        }
        if (result == null) {
            return true;
        }
        if (StringUtils.equals(prop, "sizeCode")) {
            logger.error("尺寸编码‘" + value + "’重复");
        }
        if (StringUtils.equals(prop, "sizeName")) {
            logger.error("尺寸名‘" + value + "’重复");
        }
        return false;
    }

    public String generateColorCode() {
        String colorCode = null;
        for (int i = 0; i < 100; i++) {
            String firstNum = RandomUtils.generateUpperString(1);
            String nextNum = RandomUtils.generateString(2).toUpperCase();
            colorCode = firstNum + nextNum;
            if (!colorIsUnique("colorCode", colorCode, null)) {
                if (i == 99) {
                    throw new BizException("系统无法生成颜色代码，请联系管理员");
                }
                continue;
            }
            break;
        }
        return colorCode;
    }

    public boolean colorIsUnique(String prop, String value, String colorId) {

        Map<String, Object> param = new HashMap<>();
        if (StringUtils.isNotBlank(colorId)) {
            param.put("colorId", Integer.valueOf(colorId));
        }
        Integer result = null;
        if (StringUtils.equals(prop, "colorCode")) {
            param.put("colorCode", value);
            result = colorMapper.queryByCode(param);
        } else if (StringUtils.equals(prop, "colorName")) {
            param.put("colorName", value);
            result = colorMapper.queryByName(param);
        }
        if (result == null) {
            return true;
        }
        return false;
    }
    
    private String doGenerateProviderBarcode(String goodsSn, Integer colorId, Integer sizeId) {
		return goodsSn + " " + fixLength(colorId.toString(), 4) + " " + fixLength(sizeId.toString(), 4);
	}
    
    private String fixLength(String value, int length) {
    	while (value.length() < length) {
    		value = "0" + value;
    	}
    	return value;
    }
    
}
