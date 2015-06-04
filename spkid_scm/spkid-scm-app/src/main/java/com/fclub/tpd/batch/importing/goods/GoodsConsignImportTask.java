/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.goods;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.batch.importing.AbstractUploadImportTask;
import com.fclub.tpd.batch.importing.dto.GoodsConsign;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.dto.ProductCardDto;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.batch.importing.helper.ImportTaskHelper;
import com.fclub.tpd.common.jxl.ExcelUtil;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductCard;
import com.fclub.tpd.dataobject.ProductSub;
import com.fclub.tpd.mapper.InnerProductMapper;
import com.fclub.tpd.mapper.ProductCardMapper;
import com.fclub.tpd.mapper.ProductMapper;
import com.fclub.tpd.mapper.ProductSubMapper;
import com.fclub.tpd.mapper.SizeMapper;

/**
 * @version $Id: GoodsColorSizeImportTask.java, v 0.1 Jul 15, 2013 1:55:57 PM likaiping Exp $
 */
public class GoodsConsignImportTask extends AbstractUploadImportTask {
	
    @Autowired
    private SizeMapper         sizeMapper;
    @Autowired
    private ProductMapper      productMapper;
    @Autowired
    private ProductSubMapper   productSubMapper;
    @Autowired
    private InnerProductMapper innerProductMapper;
    @Autowired
    private ProductCardMapper  productCardMapper;
    
    private boolean isVirtual;

    @Override
    protected ImportResult execute() {
        File file = ImportTaskHelper.getHistoricalFile(importContext);
        
        //完全成功状态
        boolean success = true;
        ImportResult result = null;
        setVirtual(StringUtil.equals(importContext.getContent().toString(), "1"));
        // 普通商品
        if (!isVirtual()) {
	        List<GoodsConsign> goodsConsignList = new ArrayList<GoodsConsign>();
	        InputStream input = null;
	        try {
	        	List<String[]> list = ExcelUtil.readLines(file);
	            // 读取第一行标题
	            int i = 0;
	            for (String[] line : list) {
	                i++;
	                if (i == 1) {
	                    continue;
	                } else if (line.length < 1) {
	                    continue;
	                }
	                
	                GoodsConsign goodsConsign = newGoodsConsign(line);
	                goodsConsignList.add(goodsConsign);
	            }
	        } catch (IOException e) {
	            logger.error("打开文件错误", e);
	            return ImportTaskHelper.genResultError("系统错误，请联系管理员！");
	        } finally {
	            IOUtils.closeQuietly(input);
	        }
	        
	        
	        int i = 0;
	        List<ProductSub> subList = new ArrayList<ProductSub>();
	        for (GoodsConsign goodsConsign : goodsConsignList) {
	            i++;
	            setProgress(goodsConsignList.size(), i);
	            
	            ProductSub sub = doValidate(goodsConsign);
	            
	            if (StringUtil.isBlank(goodsConsign.getError().toString())) {
	            	subList.add(sub);
	            } else {
	                success = false;
	            }
	            
	            addResultLog(goodsConsign);
	        }
	        
	        if (!success) {
	        	result = ImportTaskHelper.genResult(null, ImportStatus.FAIL, "导入失败");
	        } else {
	        	if (subList.size() > 0) {
		        	// 更新内部表商品库存
		        	innerProductMapper.batchUpdateConsignNum(subList);
		        	// 更新供应商表商品库存
		        	productSubMapper.batchUpdateConsignNum(subList);
	        	}
	        	result = ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "导入完成");
	        }
        }
        // 虚拟商品
        else {
        	List<ProductCardDto> productCardDtoList = new ArrayList<ProductCardDto>();
	        InputStream input = null;
	        try {
	        	List<String[]> list = ExcelUtil.readLines(file);
	            // 读取第一行标题
	            int i = 0;
	            for (String[] line : list) {
	                i++;
	                if (i == 1) {
	                    continue;
	                } else if (line.length < 1) {
	                    continue;
	                }
	                
	                ProductCardDto productCardDto = newProductCardDto(line);
	                productCardDtoList.add(productCardDto);
	            }
	        } catch (IOException e) {
	            logger.error("打开文件错误", e);
	            return ImportTaskHelper.genResultError("系统错误，请联系管理员！");
	        } finally {
	            IOUtils.closeQuietly(input);
	        }
	        
	        
	        int i = 0;
	        List<ProductCard> cardList = new ArrayList<ProductCard>();
	        for (ProductCardDto productCardDto : productCardDtoList) {
	            i++;
	            setProgress(productCardDtoList.size(), i);
	            
	            ProductCard sub = doValidate(productCardDto, StringUtil.isNotEmpty(productCardDtoList.get(0).getCardNo()));
	            // 校验文件中是否有重复商品SKU、卡号、密码
	            if (cardList.contains(sub)) {
	            	productCardDto.getError().append("商品SKU+卡号+密码在上传文件中存在重复记录\n");
	            }
	            
	            if (StringUtil.isBlank(productCardDto.getError().toString())) {
	            	cardList.add(sub);
	            } else {
	                success = false;
	            }
	            
	            addResultLog(productCardDto);
	        }
	        
	        if (!success) {
	        	result = ImportTaskHelper.genResult(null, ImportStatus.FAIL, "导入失败");
	        } else {
	        	if (cardList.size() > 0) {
		        	// 插入卡密表数据
	        		productCardMapper.batchInsert(cardList);
	        		// 更新内部表商品库存
		        	innerProductMapper.batchUpdateConsignNumByCard(cardList);
		        	// 更新供应商表商品库存
		        	productSubMapper.batchUpdateConsignNumByCard(cardList);
	        	}
	        	result = ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "导入完成");
	        }
        }
        
        return result;
    }
	
	private ProductSub doValidate(GoodsConsign goodsConsign) {
		String goodsSn = goodsConsign.getGoodsSn();
		String colorName = goodsConsign.getColorName();
		String sizeName = goodsConsign.getSizeName();
		Integer consignNum = goodsConsign.getConsignNum();
		Integer providerId = importContext.getProviderId();
		
		// 校验商品款号
		Product goods = null;
        if (StringUtil.isBlank(goodsSn)) {
            goodsConsign.getError().append("商品款号不能为空\n");
        } else {
        	Product scmGoods = productMapper.findTpdGoodsBySn(goodsSn);
        	if (scmGoods == null) {
        		goodsConsign.getError().append("商品款号不存在\n");
        	} else if(!"2".equals(scmGoods.getTpdGoodsStatus())) {
        		goodsConsign.getError().append("商品状态：["+scmGoods.getTpdGoodsStatusName()+"]，不能导入虚库\n");
        	} else {        		
        		if (!scmGoods.getIsVirtual() && scmGoods.getGenerateMethod().intValue() != 1) {
        			goodsConsign.getError().append("商品不是普通商品或者卡密生成方式为系统生成的虚拟商品\n");
        		}
        		goods = innerProductMapper.selectProductBySn(goodsSn);
        		if (goods == null) {
        			goodsConsign.getError().append("商品未审核\n");
        		} else if (!providerId.equals(goods.getProviderId())) {
        			goodsConsign.getError().append("此商品款号不属于当前供应商\n");
        		}
        	}
        }
        
        // 校验颜色名称
        Integer colorId = null;
        if (StringUtil.isBlank(colorName)) {
            goodsConsign.getError().append("颜色名称不能为空\n");
        } else {
            colorId = innerProductMapper.selectColorIdByName(colorName);
            if (colorId == null || colorId <= 0) {
            	goodsConsign.getError().append("颜色名称不存在\n");
            }
        }
        
        // 校验尺寸名称
        Integer sizeId = null;
        if (StringUtil.isBlank(sizeName)) {
            goodsConsign.getError().append("尺寸名称不能为空\n");
        } else {
            sizeId = innerProductMapper.selectSizeIdByName(sizeName);
            if (sizeId == null || sizeId <= 0) {
            	goodsConsign.getError().append("尺寸名称不存在\n");
            }
        }
        
        // 校验虚库库存
        if (consignNum == null) {
        	consignNum = Integer.valueOf(0);
        } else if (consignNum.intValue() < 0) {
        	goodsConsign.getError().append("虚库库存不能小于0\n");
        }
		
        // 校验SKU
        ProductSub sub = null;
        if (goods != null && colorId != null && sizeId != null) {
	        sub = innerProductMapper.selectByPCSId(goods.getGoodsId(), colorId, sizeId);
	        if (sub == null) {
	        	goodsConsign.getError().append("款号+颜色名称+尺寸名称对应的商品不存在\n");
	        } else {
	        	sub.setConsignNum(consignNum);
	        	sub.setTpdGlId(sub.getTpdGlId());
	        	sub.setTpdGoodsId(goods.getTpdGoodsId());
	        }
        }
        
        return sub;
	}
	
	private ProductCard doValidate(ProductCardDto productCardDto, boolean priHasCardNo) {
		String goodsSn = productCardDto.getGoodsSn();
		String colorName = productCardDto.getColorName();
		String sizeName = productCardDto.getSizeName();
		String cardNo = productCardDto.getCardNo();
		String cardPwd = productCardDto.getCardPwd();
		Integer providerId = importContext.getProviderId();
		
		if (priHasCardNo != StringUtil.isNotEmpty(cardNo)) {
			productCardDto.getError().append("卡号需要统一为空或者统一不为空\n");
		}
		
		// 校验商品款号
		Product goods = null;
		Product scmGoods = null;
        if (StringUtil.isBlank(goodsSn)) {
        	productCardDto.getError().append("商品款号不能为空\n");
        } else {
        	scmGoods = productMapper.findTpdGoodsBySn(goodsSn);
        	if (scmGoods == null) {
        		productCardDto.getError().append("商品款号不存在\n");
        	} else if(!"2".equals(scmGoods.getTpdGoodsStatus())) {
        		productCardDto.getError().append("商品状态：["+scmGoods.getTpdGoodsStatusName()+"]，不能导入虚库\n");
        	} else {        		
        		if (scmGoods.getIsVirtual()==null || !scmGoods.getIsVirtual()) {
        			productCardDto.getError().append("商品不是虚拟商品\n");
        		}
        		if (scmGoods.getIsVirtual()==null || scmGoods.getGenerateMethod() != 2) {
        			productCardDto.getError().append("商品卡密生成方式不是手工导入\n");
        		}
        		goods = innerProductMapper.selectProductBySn(goodsSn);
        		if (goods == null) {
        			productCardDto.getError().append("商品未审核\n");
        		} else if (!providerId.equals(goods.getProviderId())) {
        			productCardDto.getError().append("此商品款号不属于当前供应商\n");
        		}
        	}
        }
        
        // 校验颜色名称
        Integer colorId = null;
        if (StringUtil.isBlank(colorName)) {
        	productCardDto.getError().append("颜色名称不能为空\n");
        } else {
            colorId = innerProductMapper.selectColorIdByName(colorName);
            if (colorId == null || colorId <= 0) {
            	productCardDto.getError().append("颜色名称不存在\n");
            }
        }
        
        // 校验尺寸名称
        Integer sizeId = null;
        if (StringUtil.isBlank(sizeName)) {
        	productCardDto.getError().append("尺寸名称不能为空\n");
        } else {
            sizeId = innerProductMapper.selectSizeIdByName(sizeName);
            if (sizeId == null || sizeId <= 0) {
            	productCardDto.getError().append("尺寸名称不存在\n");
            }
        }
        
        // 校验密码
        if (StringUtil.isEmpty(cardPwd)) {
        	productCardDto.getError().append("密码不得为空\n");
        }
		
        // 校验SKU
        ProductSub sub = null;
        ProductCard productCard = null;
        if (goods != null && colorId != null && sizeId != null) {
	        sub = innerProductMapper.selectByPCSId(goods.getGoodsId(), colorId, sizeId);
	        if (sub == null) {
	        	productCardDto.getError().append("款号+颜色名称+尺寸名称对应的商品不存在\n");
	        } else {
	        	// 校验商品SKU、卡号、密码是否已存在
	            if (productCardMapper.getCountBySubNoPwd(sub.getGlId(), cardNo, cardPwd) > 0) {
	            	productCardDto.getError().append("商品SKU+卡号+密码已存在\n");
	            } else {
		        	productCard = new ProductCard();
		        	productCard.setSubId(sub.getGlId());
		        	productCard.setCardNo(cardNo);
		        	productCard.setCardPwd(cardPwd);
		        	productCard.setGlId(productSubMapper.findByGoodsColorSizeId(scmGoods.getGoodsId(), colorId, sizeId).getGlId());
	            }
	        }
        }
        
        return productCard;
	}

	@Override
    protected boolean doValidation(ImportContext importContext) {
        return importContext.getImportType() == ImportType.GOODS_CONSIGN;
    }

    @Override
    protected String getResultTemplateName() {
        String resultTemplateName = "import/result/consign.vm";
        if (isVirtual()) {
        	resultTemplateName = "import/result/productCard.vm";
        }
    	return resultTemplateName;
    }
    
	private GoodsConsign newGoodsConsign(String[] line) {
		GoodsConsign consign = new GoodsConsign();
		if (line.length >= 4) {
			consign.setGoodsSn(getStringValueFromArray(line, 0));
			consign.setColorName(getStringValueFromArray(line, 1));
			consign.setSizeName(getStringValueFromArray(line, 2));
			consign.setConsignNum(getIntegerValueFromArray(line, 3));
		}
		
		return consign;
	}
	
	private ProductCardDto newProductCardDto(String[] line) {
		ProductCardDto productCardDto = new ProductCardDto();
		if (line.length >= 4) {
			productCardDto.setGoodsSn(getStringValueFromArray(line, 0));
			productCardDto.setColorName(getStringValueFromArray(line, 1));
			productCardDto.setSizeName(getStringValueFromArray(line, 2));
			String cardNo = getStringValueFromArray(line, 3);
			if (StringUtil.isEmpty(cardNo)) {
				cardNo = "";
			}
			productCardDto.setCardNo(cardNo);
			productCardDto.setCardPwd(getStringValueFromArray(line, 4));
		}
		
		return productCardDto;
	}
    
    private Integer getIntegerValueFromArray(String[] items, int index) {
    	Integer field = 0;
    	if (items.length > index) {
			String itemValue = items[index];
			if (itemValue != null && !itemValue.isEmpty()) {
				if (itemValue.matches("^[0-9]\\d*\\.0*$")) {
					field = Double.valueOf(itemValue).intValue();
				} else {
					field = Integer.parseInt(items[index]);
				}
			}
		}
    	return field;
	}

	private String getStringValueFromArray(String[] items, int index) {
		String field = "";
		if (items.length > index) {
			field = StringUtil.trim(items[index]);
			if (StringUtil.isNotEmpty(field) && field.matches("^[0-9]\\d*\\.0*$")) {
				field = String.valueOf(Double.valueOf(field).intValue());
			}
		}
		return field;
	}

	public boolean isVirtual() {
		return isVirtual;
	}

	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

}
