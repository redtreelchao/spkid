/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.goods;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.batch.importing.AbstractUploadImportTask;
import com.fclub.tpd.batch.importing.dto.GoodsDescDTO;
import com.fclub.tpd.batch.importing.dto.GoodsMainData;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.batch.importing.helper.ImportTaskHelper;
import com.fclub.tpd.biz.ProductTypeLinkService;
import com.fclub.tpd.common.jxl.ExcelUtil;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductType;
import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.dataobject.ProviderBrand;
import com.fclub.tpd.mapper.BrandMapper;
import com.fclub.tpd.mapper.GoodsMaterialMapper;
import com.fclub.tpd.mapper.ProductMapper;
import com.fclub.tpd.mapper.ProductTypeMapper;
import com.fclub.tpd.mapper.ProviderBrandMapper;
import com.fclub.tpd.mapper.ProviderMapper;

/**
 * 商品主要信息导入任务
 * @version $Id: GoodsMainImportTask.java, v 0.1 Oct 25, 2012 5:24:17 PM likaiping Exp $
 */
public class GoodsMainImportTask extends AbstractUploadImportTask {

    @Autowired
    private ProductMapper          goodsMapper;
    @Autowired
    private BrandMapper            brandMapper;
    @Autowired
    private ProductTypeLinkService goodsTypeLinkService;
    @Autowired
    private ProductTypeMapper      goodsTypeMapper;
    @Autowired
    private GoodsMaterialMapper    goodsMaterialMapper;
    @Autowired
    private ProviderMapper         providerMapper;
    @Autowired
    private ProviderBrandMapper    providerBrandMapper;

    @Override
    protected boolean doValidation(ImportContext importContext) {
        if (importContext.getImportType() == ImportType.GOODS_MAIN_INFORMATION) {
            return true;
        }
        return false;
    }

    @Override
    protected ImportResult execute() {
        File file = ImportTaskHelper.getHistoricalFile(importContext);
        Integer i = 0, providerId = importContext.getProviderId();
        Integer coopId = 4; //第三方发运
        Provider provider = providerMapper.get(providerId);
        if (provider == null)
            return ImportTaskHelper.genResultError("供应商不可用");
        List<String[]> list;
        try {
            list = ExcelUtil.readLines(file);
        } catch (IOException e) {
            logger.error("文件打开失败", e);
            return ImportTaskHelper.genResultError("文件打开失败");
        }
        List<GoodsMainData> goodsMainDatas = new ArrayList<>();
        List<ProviderBrand> providerBrands = providerBrandMapper
            .selectBrandsByProviderId(providerId);
        try {
            logger.info("开始检查文件行数");
            if (list.size() > 500) {
                logger.warn("文件行数超过500条，导入流程出错");
                return ImportTaskHelper.genResultError("导入数据不能超过500条");
            }
            logger.info("文件行数【{0}】", list.size());
            logger.info("开始检查每行数据合法性");
            boolean success = true;
            i = 0;
            for (String[] items : list) {
                i++;
                if (i == 1) {
                    continue;
                }
                if (items == null || items.length < 1) {
                    continue;
                }
                GoodsMainData goodsCSV = new GoodsMainData(items);
                logger.debug("第【{0}】行：{1}", i, goodsCSV);
                try {
                    validate(goodsCSV);
                    goodsCSV.setTaxRate(provider.getProviderCess());
                    if (!isOwnerBrand(providerBrands, goodsCSV.getBrandId())) {
                        goodsCSV.getError().append("商品对应品牌不在签约品牌内\n");
                    }
                } catch (Exception e) {
                    logger.error("第【{0}】行：{1}", i, e.getMessage());
                    success = false;
                }
                if (StringUtil.isNotBlank(goodsCSV.getError().toString())) {
                    success = false;
                }
                super.addResultLog(goodsCSV);
                goodsMainDatas.add(goodsCSV);
            }
            if (!success) {
                logger.warn("检查每行数据合法性失败，具体见结果报告");
                throw new BizException("内容校验失败");
            }
            logger.info("完成每行数据合法性检查");
        } catch (BizException e) {
            return ImportTaskHelper.genResultError(e.getMessage());
        }
        logger.info("开始数据入库");
        // 插入到数据库的商品记录的id列表
        List<Integer> goodsIds = new ArrayList<Integer>();
        i = 0;
        for (GoodsMainData goodsCSV : goodsMainDatas) {
            i++;
            setProgress(goodsMainDatas.size(), i);
            try {
                // 生成商品款号
                String newGoodsSn;
                try {
                    newGoodsSn = generateGoodsSn(goodsCSV, importContext.getId(), i);
                } catch (Exception e) {
                    logger.error("款号生成失败，入库操作出错，所在行:{0}", goodsCSV);
                    throw e;
                }
                // 更新goods表的参数
                Product goods = new Product();
                goods.setCatId(goodsCSV.getCategoryId());
                goods.setBrandId(goodsCSV.getBrandId());
                goods.setSeasonId(goodsCSV.getSeasonId());
                goods.setProviderId(providerId);
                goods.setProviderName("");

                goods.setCoopId(coopId);

                goods.setAreaId(goodsCSV.getAreaId());

                goods.setProviderGoods(goodsCSV.getProviderSn());
                goods.setGoodsYear(goodsCSV.getYear());
                goods.setGoodsMonth(goodsCSV.getMonth());

                goods.setGoodsCess(goodsCSV.getTaxRate());//使用供应商税率
                if(goods.getGoodsCess() == null)
                    goods.setGoodsCess(BigDecimal.ZERO);
                
                Integer rushGoods = Integer.parseInt(goodsCSV.getRushGoods());
                goods.setIsRush(rushGoods);

                BigDecimal marketPrice = new BigDecimal(goodsCSV.getMarketPrice());
                goods.setMarketPrice(marketPrice);

                BigDecimal shopPrice = new BigDecimal(goodsCSV.getFclubPrice());
                goods.setShopPrice(shopPrice);

                BigDecimal costPrice = new BigDecimal(goodsCSV.getCostPrice());
                goods.setCostPrice(costPrice);

                BigDecimal consignPrice = new BigDecimal(goodsCSV.getConsignPrice());
                goods.setConsignPrice(consignPrice);
                goods.setConsignType(0);

                goods.setGoodsSn(newGoodsSn);
                goods.setGoodsName(goodsCSV.getGoodsName());

                goods.setGoodsDesc("");
                goods.setGoodsDescAdditional(goodsCSV.getGoodsDesc());
                goods.setStyleId(goodsCSV.getStyleId());
                goods.setUnitId(goodsCSV.getUnitId());
                goods.setUnitName(goodsCSV.getUnitCode());
                goods.setGoodsSex(goodsCSV.getSex());
                goods.setLastUpdate(providerId);
                goods.setTpdGoodsStatus("0");

                goods.setGoodsAudit(0);
                goods.setGoodsAuditAid(providerId);
                goods.setGoodsAuditTime(new Date());

                //～== deleted
                goods.setGoodsBrief("");
                goods.setGoodsType(0);//前台三级分类
                goods.setGoodsStuff("");
                goods.setGoodsMaterial("");
                goods.setGoodsMaterialNew("");
                goods.setGoodsAuditTime(new Date());
                goods.setScId(0);
                goods.setScDesc("");
                goods.setGoodsModelimg("");
                goods.setPagecatId(0);
                goods.setGoodsNameStyle("");
                goods.setClickCount(0);
                goods.setGoodsNumber(0);
                goods.setGoodsWeight(BigDecimal.ZERO);
                goods.setIsPromote(false);
                goods.setPromoteStartDate(0);
                goods.setPromoteEndDate(0);
                goods.setPromotePrice(BigDecimal.ZERO);
                goods.setConsignRate(BigDecimal.ZERO);
                goods.setWarnNumber(0);
                goods.setKeywords("");
                goods.setGoodsThumb("");
                goods.setGoodsImg("");
                goods.setOriginalImg("");
                goods.setIsReal(0);
                goods.setExtensionCode("");
                goods.setIsOnSale(false);
                goods.setIsDelete(false);
                goods.setIsAloneSale(false);
                goods.setIntegral(0);
                goods.setSortOrder(0);
                goods.setIsBest(false);
                goods.setIsHot(false);
                goods.setIsNew(false);
                goods.setIsGifts(0);
                goods.setIsOffcode(0);
                goods.setIsEmpty(0);
                goods.setBonusTypeId(0);
                goods.setSellerNote("");
                goods.setGiveIntegral(0);
                goods.setRankIntegral(0);
                goods.setGoodsStop(0);
                goods.setModelId(0);
                goods.setLimitNum(0);
                goods.setRecordStatus(-1);
                
                if (importContext.getContent() != null) {
                	Integer[] virtualArr = (Integer[]) importContext.getContent();
                	goods.setIsVirtual(StringUtil.equals(virtualArr[0].toString(), "1"));
                	goods.setGenerateMethod(virtualArr[1]);
                }

                //sub
                Integer goodsId;
                if (StringUtil.isBlank(goodsCSV.getGoodsSn())) {
                    goods.setGoodsAid(this.importContext.getImportAdmin());
                    goods.setGoodsTime(new Date());
                    goods.setUpdateTime(new Date());

                    goodsMapper.insert(goods);
                    goodsId = goods.getGoodsId();

                    goodsCSV.setGoodsSn(newGoodsSn);
                } else {
                    goodsId = goodsMapper.findByGoodsSn(goodsCSV.getGoodsSn());
                }
                goodsIds.add(goodsId);
                
                // 判断如果已存在则更新, 否则插入新记录
                if (goodsCSV.getTypeId() != null && goodsCSV.getTypeId().intValue() > 0) {                	
                	try {
                		goodsTypeLinkService.setGoodsType(goodsId, new String[]{goodsCSV.getTypeId().toString()});
                	} catch (Exception e) {
                		String msg = "前台分类操作失败";
                		goodsCSV.getError().append(msg).append("\n");
                		return ImportTaskHelper.genResultError(msg);
                	}
                }
            } catch (Exception e) {
                logger.error("入库操作出错，所在行:{0}", goodsCSV);
                throw e;
            }
        }
        if (goodsIds.size() > 0) {
            // 商品记录id列表插入表goods_batch_import
            StringBuilder gbiGoodsIds = new StringBuilder();
            for (int j = 0; j < goodsIds.size(); j++) {
                if (j == 0)
                    gbiGoodsIds.append(goodsIds.get(j));
                else
                    gbiGoodsIds.append(",").append(goodsIds.get(j));
            }
            importContext.setGoodsIds(gbiGoodsIds.toString());
        }
        return ImportTaskHelper.genResult(null, ImportStatus.SUCCESS, "导入完成");
    }

    /**
     * 生成商品款号
     * 
     * @param goodsCSV
     * @return
     */
    private String generateGoodsSn(GoodsMainData goodsCSV, Integer importId, Integer index) {
        String newGoodsSn = String.valueOf(importId) + "P";
        String strIndex = String.valueOf(index);
        if (newGoodsSn.length() + strIndex.length() >= 10) {
            return newGoodsSn + strIndex;
        }
        return newGoodsSn + getIdenSN(index, 10 - newGoodsSn.length());
    }

    private String getIdenSN(int suffix, int len) {
        return StringUtil.leftPad(String.valueOf(suffix), len, "0");
    }

    private void validate(GoodsMainData goodsCSV) throws IOException {
        //～====== 商品分类 ==========
//        if (StringUtil.isBlank(goodsCSV.getCategoryCode())) {
//            goodsCSV.getError().append("商品分类名称不能为空\n");
//        } else {
//            Short categoryId = categoryMapper.findByCatName(goodsCSV.getCategoryCode());
//            if (categoryId == null) {
//                goodsCSV.getError().append("商品分类名称不存在\n");
//            } else {
//                Category category = categoryMapper.selectByPrimaryKey(categoryId.intValue());
//                if (new Integer(0).compareTo(category.getParentId()) >= 0) {
//                    goodsCSV.getError().append("商品分类不是二級分类\n");
//                } else {
//                    goodsCSV.setCategoryId(categoryId);
//                }
//            }
//        }
        //～====== 商品品牌 ==========
        if (goodsCSV.getBrandId() <= 0) {
            goodsCSV.getError().append("商品品牌ID不能为空\n");
        } else {
            Brand brand = brandMapper.selectById(goodsCSV.getBrandId());
            if (brand == null) {
                goodsCSV.getError().append("品牌ID["+goodsCSV.getBrandId()+"]不存在\n");
            }
        }
        //～====== 商品季节 不设置默认全季节 ==========
        //～====== 供应商货号 ==========
        if (StringUtil.isBlank(goodsCSV.getProviderSn())) {
            goodsCSV.getError().append("供应商货号不能为空\n");
        }
        //～====== 商品年份 默认本年==========
        goodsCSV.setYear(String.valueOf(DateUtil.getYear()));
        //～====== 商品月份 默认本月==========
        goodsCSV.setMonth(String.valueOf(getMontd()));
        //～====== 国家 默认中国 ==========
        goodsCSV.setAreaId(1);
        //～====== 商品税率 系统根据fclub后台编辑的税率默认； ==========

        //～====== 抢购商品 ==========
        goodsCSV.setRushGoods("0");
        //～====== 市场价 ==========
        if (StringUtil.isBlank(goodsCSV.getMarketPrice())) {
            goodsCSV.getError().append("市场价不能为空\n");
        }
        try {
            BigDecimal decimal = new BigDecimal(goodsCSV.getMarketPrice());
            if (decimal.compareTo(new BigDecimal(0)) <= 0) {
                goodsCSV.getError().append("市场价必须大于0\n");
            }
        } catch (NumberFormatException e) {
            goodsCSV.getError().append("市场价必须为数值\n");
        }
        //～====== fclub价 ==========
        if (StringUtil.isBlank(goodsCSV.getFclubPrice())) {
            goodsCSV.getError().append("妈咪价不能为空\n");
        }
        try {
            BigDecimal decimal = new BigDecimal(goodsCSV.getFclubPrice());
            if (decimal.compareTo(new BigDecimal(0)) < 0) {
                goodsCSV.getError().append("妈咪价必须大于等于0\n");
            }
            //decimal.intValueExact(); // will check decimal part
        } catch (Exception e) {
            goodsCSV.getError().append("妈咪价必须为整数\n");
        }

        //～====== 成本价 0 ==========
        goodsCSV.setCostPrice("0");
        //～====== 代销价 ==========
//        if (StringUtil.isBlank(goodsCSV.getConsignPrice())) {
//            goodsCSV.getError().append("代销价不能为空\n");
//        }
        goodsCSV.setConsignPrice("0");
        try {
            new BigDecimal(goodsCSV.getConsignPrice());
        } catch (NumberFormatException e) {
            goodsCSV.getError().append("代销价必须为数值\n");
        }
        //～====== 商品名称 ==========
        if (StringUtil.isBlank(goodsCSV.getGoodsName())) {
            goodsCSV.getError().append("商品名称不能为空\n");
        } else if (asciiCount(goodsCSV.getGoodsName()) > 72) {
            goodsCSV.getError().append("商品名称不能超过72个字符(1个汉字为2个字符)\n");
        }

        // 如果csv文件中填写了商品款号，该条数据不插入数据库
        if (!StringUtil.isBlank(goodsCSV.getGoodsSn())) {
            Integer goodsId = goodsMapper.findByGoodsSn(goodsCSV.getGoodsSn());
            if (goodsId == null || goodsId <= 0) {
                goodsCSV.getError().append("填写的商品款号有误\n");
            }
        }

        //~====== 商品风格 默认 时尚==========
        goodsCSV.setStyleId(50);
        //~====== 性别 ==========
        if (StringUtil.isBlank(goodsCSV.getSex())) {
            goodsCSV.getError().append("性别不能为空\n");
        } else {
            if (!StringUtil.equals("w", goodsCSV.getSex())
                && !StringUtil.equals("m", goodsCSV.getSex())
                && !StringUtil.equals("a", goodsCSV.getSex())) {
                goodsCSV.getError().append("性别必须输入[m,w,a]\n");
                goodsCSV.setSex(null);
            }
        }
        //~====== 计量单位 默认 件 ==========
//        goodsCSV.setUnitId(1);
        //~====== 商品三级分类 ==========
        if (StringUtil.isBlank(goodsCSV.getTypeCode())) {
            goodsCSV.getError().append("商品前台分类不能为空\n");
        } else {
        	String typeCode = goodsCSV.getTypeCode().trim();
        	ProductType type = goodsTypeMapper.selectL2ByCode(typeCode);
        	if (type == null) {
        		goodsCSV.getError().append("商品前台分类编码不存在\n");
        	} else {
        		goodsCSV.setTypeId(type.getTypeId());
        		goodsCSV.setCategoryId(type.getCategoryId());
        	}
        }
        //~====== 商品保养 ==========
//        if (StringUtil.isNotBlank(goodsCSV.getNewGoodsCode())) {
//            String[] materArr = StringUtil.split(goodsCSV.getNewGoodsCode(), "|");
//            if (materArr.length > 0) {
//                String strTemp = "";
//                for (String value : materArr) {
//                    value = value.trim();
//                    Integer materId = goodsMaterialMapper.getMaterIdByName(value);
//                    if (materId == null || materId < 1) {
//                        goodsCSV.getError().append("洗标名称【" + value + "】不存在\n");
//                    } else {
//                        strTemp += StringUtil.isBlank(strTemp) ? materId : "," + materId;
//                    }
//                }
//                if (StringUtil.isNotBlank(strTemp)) {
//                    // 把商品保养编码存起来
//                    goodsCSV.setNewGoodsCode(strTemp);// 商品保养
//                } else {
//                    goodsCSV.setNewGoodsCode(null);
//                }
//            }
//        }
        if(StringUtil.isBlank(goodsCSV.getDesc_composition()) && StringUtil.isBlank(goodsCSV.getDesc_material()))
            goodsCSV.getError().append("成分，材质 二者必须填写一项\n");
        //~====== 商品模特 没有模特 ==========

        // goodsWithBLOBs json数组
        Product goodsWithBLOBs = goodsMapper.getExtraGoodsBySn(goodsCSV.getGoodsSn());
        String content = (goodsWithBLOBs != null) ? (goodsWithBLOBs.getGoodsDescAdditional() != null) ? goodsWithBLOBs
            .getGoodsDescAdditional() : ""
            : "";
        ObjectMapper mapper = new ObjectMapper();

        GoodsDescDTO goodsDescDTO = null;
        if (goodsWithBLOBs == null || StringUtil.isBlank(content))
            goodsDescDTO = new GoodsDescDTO();
        else
            goodsDescDTO = mapper.readValue(content, GoodsDescDTO.class);
        try {
            goodsDescDTO.setDesc_composition(getGoodsDescValue(goodsCSV.getDesc_composition()));
            goodsDescDTO.setDesc_dimensions(getGoodsDescValue(goodsCSV.getDesc_dimensions()));
            goodsDescDTO.setDesc_material(getGoodsDescValue(goodsCSV.getDesc_material()));
            goodsDescDTO.setDesc_waterproof(getGoodsDescValue(goodsCSV.getDesc_waterproof()));
            goodsDescDTO.setDesc_crowd(getGoodsDescValue(goodsCSV.getDesc_crowd()));
            goodsDescDTO.setDesc_notes(getGoodsDescValue(goodsCSV.getDesc_notes()));
            // 保存商品描述；
            StringWriter goodsDescStrW = new StringWriter();
            mapper.writeValue(goodsDescStrW, goodsDescDTO);
            goodsCSV.setGoodsDesc(goodsDescStrW.toString());
        } catch (IOException e) {
            logger.error("商品描述（新）,json数据解析错误，原始数据：{0}", e, content);
            goodsCSV.getError().append("商品描述（新）,json数据解析错误\n");
            throw e;
        }
    }

    private String getGoodsDescValue(String value) {
        if (StringUtil.isBlank(value)) {
            return "";
        } else {
            value = StringUtil.trim(value);
            if (StringUtil.notEquals(value, "{delete}")) {
                return StringUtil.defaultIfBlank(value);
            } else {
                return "";
            }
        }
    }

    /**
     * 验证是否所所属品牌
     */
    private boolean isOwnerBrand(List<ProviderBrand> providerBrands, Integer brandId) {
        if (providerBrands == null || providerBrands.isEmpty())
            return false;
        for (Iterator<ProviderBrand> iterator = providerBrands.iterator(); iterator.hasNext();) {
            ProviderBrand providerBrand = iterator.next();
            if (providerBrand.getBrandId().compareTo(brandId) == 0)
                return true;
        }
        return false;
    }

    @Override
    protected String getResultTemplateName() {
        return "import/result/goods_reg.vm";
    }

    private static int getMontd() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
    
    public static int asciiCount(String value) {
        int count = 0;
        if (value != null) {
            for (int i = 0; i < value.length(); i++) {
                char point = value.charAt(i);
                // char in ASCII table[0, 127] count 1, else count 2. 
                count += (point >= 0 && point <= 127) ? 1 : 2;
            }
        }
        
        return count;
    }
    
}
