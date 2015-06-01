package com.fclub.tpd.controller;

import static com.fclub.web.util.UploadUtil.deleteFile;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.tpd.batch.importing.dto.GoodsDescDTO;
import com.fclub.tpd.biz.ProductService;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.dataobject.Category;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.Product.GoodsColorVo;
import com.fclub.tpd.dataobject.Product.GoodsSizeVo;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.dataobject.ProductSub;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.helper.SessionHelper;
import com.fclub.tpd.mapper.BrandMapper;
import com.fclub.tpd.mapper.CategoryMapper;
import com.fclub.tpd.mapper.ProductGalleryMapper;
import com.fclub.tpd.mapper.ProductSubMapper;
import com.fclub.tpd.mapper.ProductTypeLinkMapper;

@Controller
@RequestMapping("/goods")
public class ProductController extends BaseController {

	private LogUtil logger = LogUtil.getLogger(ProductController.class);
	
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductTypeLinkMapper goodsTypeLinkMapper;
    @Autowired
    private ProductSubMapper productLaborMapper;
    @Autowired
    private ProductGalleryMapper productGalleryMapper;
    
    public static final Map<String, String> SEX_MAP  = new LinkedHashMap<String, String>();
    static {
        SEX_MAP.put("m", "男");
        SEX_MAP.put("w", "女");
        SEX_MAP.put("a", "全部");
    }
    
    @RequestMapping("/list/main.htm")
    public String main(ModelMap modelMap) {
    	List<Brand> brandList = brandMapper.findBrandsByProviderId(SessionHelper.getProvider().getProviderId());
    	modelMap.put("brandList", brandList);
        return "tpd/goods";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<Product> page, Product goods) {
    	List<Product> list = queryGoods(page, goods);
        modelMap.put("list", list);
        return "tpd/goodsList";
    }
    
    /**
     * 通过分页方式查询列表信息
     * @param page
     * @param goodsSeach
     * @return
     */
    private List<Product> queryGoods(Page<Product> page, Product goods) {
    	if (goods.getProviderGoods() != null) {
    		goods.setProviderGoods(goods.getProviderGoods().trim());
    	}
    	if (goods.getProviderCode() != null) {
    		goods.setProviderCode(goods.getProviderCode().trim());
    	}
    	if (goods.getGoodsSn() != null) {
    		goods.setGoodsSn(goods.getGoodsSn().trim());
    	}
    	if (!isAdmin()) {    		
    		goods.setProviderId(getProviderId());
    	}
    	
        page = productService.queryGoodsByPage(page, goods);
        //处理重复Color数据
        List<Product> goodsList = page.getResult();
        List<Product> list = new ArrayList<>();
        for (Product good : goodsList) {
            list.add(handleGoodsColorData(good));
        }
        return list;
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo() {
        return "tpd/goodsAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, Product goods) throws Exception {

        productService.save(goods);

        modelMap.addAttribute("currentUrl", "/goods/addTo.htm");
        modelMap.addAttribute("backUrl", "/goods/list/main.htm");
        return "commons/success2";
    }
	
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, @RequestParam("goodsId") String id) {
        modelMap.put("readOnly", 1);
        getGoodsInfo(modelMap, id);
        return "tpd/goodsEdit";
    }
    
    /**
     * 编辑商品名称。
     */
    @ResponseBody
    @RequestMapping("/editGoodsName.htm")
    public String editGoodsName(@RequestParam("upKey") Integer productId, 
                            @RequestParam("upVal") String goodsName) {
        Product product = new Product();
        product.setGoodsId(productId);
        product.setGoodsName(goodsName);
        productService.updateByScmProductId(product);
        return "success";
    }
    
    /**
     * 编辑妈咪价。
     */
    @ResponseBody
    @RequestMapping("/editShopPrice.htm")
    public String editShopPrice(@RequestParam("upKey") Integer productId, 
                            @RequestParam("upVal") BigDecimal shopPrice) {
        Product product = new Product();
        product.setGoodsId(productId);
        product.setShopPrice(shopPrice);
        productService.updateByScmProductId(product);
        return "success";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, @RequestParam("goodsId") String id) {
    	getGoodsInfo(modelMap, id);
        return "tpd/goodsEdit";
    }
    
    /**
     * 查询商品基本信息
     */
    private void getGoodsInfo(ModelMap modelMap, String id) {
    	List<Brand> brandList = brandMapper.findBrandsByProviderId(SessionHelper.getProvider().getProviderId());
    	List<Category> categoryList = categoryMapper.querySubCategory();
    	Map<String, String> sexMap = SEX_MAP;
        
    	Product goods = productService.getExtGoodsById(Integer.valueOf(id));
    	//商品描述（新）
        String content = goods.getGoodsDescAdditional();
        ObjectMapper mapper = new ObjectMapper();
        GoodsDescDTO goodsDescVo = null;
        try {
            if (StringUtil.isNotBlank(content)) {
                goodsDescVo = mapper.readValue(content, GoodsDescDTO.class);
            } else {
                goodsDescVo = new GoodsDescDTO();
            }
        } catch (IOException e) {
            logger.error("商品描述（新）,json数据解析错误，原始数据：{0}", e, content);
            throw new BizException("编辑商品，数据错误，请联系管理员！");
        }
        // 前台分类关联信息
        List<String> goodsTypeNames = goodsTypeLinkMapper.selectGoodsTypeNamesByGoodsId(Integer.valueOf(id));
    	
        modelMap.put("brandList", brandList);
        modelMap.put("categoryList", categoryList);
        modelMap.put("sexMap", sexMap);
        modelMap.put("goods", handleGoodsColorData(goods));
        modelMap.put("goodsDescVo", goodsDescVo);
        modelMap.put("goodsTypeNames", goodsTypeNames);
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, Product goods, GoodsDescDTO goodsDescVo, @RequestParam("isSubmit") int isSubmit) throws Exception {
    	Product goodsTmp = new Product();
    	//过滤filter字段
        String[] ignoreProperties = filerDisabled(goods);
        BeanUtils.copyProperties(goods, goodsTmp, ignoreProperties);
    	if (isSubmit == 1) {
    		goodsTmp.setTpdGoodsStatus("1");
    		checkGoodsImportStatus(goodsTmp.getGoodsId());
        }
    	goodsTmp.setUpdateTime(new Date());
        //商品描述（新）
        StringWriter goodsDescStrW = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(goodsDescStrW, goodsDescVo);
        goodsTmp.setGoodsDescAdditional(goodsDescStrW.toString());
        
    	productService.updateByScmProductId(goodsTmp);
        modelMap.addAttribute("backUrl", "/goods/list/main.htm");
        return "commons/success2";
    }
    
    @RequestMapping(value = "/batchSubmit.json")
    @ResponseBody
    public String batchSubmit(String ids) {
    	String result = "";
    	try {
    		String[] optStatusArr = {"0"};
    		for (String id : ids.split(",")) {
    			checkGoodsStatus(Integer.valueOf(id), optStatusArr, "提交审核");
    			checkGoodsImportStatus(Integer.valueOf(id));
    		}
    		productService.batchSubmit(ids);
        } catch (BizException e) {
        	logger.error("批量提交审核商品失败,goodsId:{0}", e, ids);
        	result = "批量提交审核商品失败：" + e.getMessage();
        } catch (Exception e) {
        	logger.error("批量提交审核商品失败,goodsId:{0}", e, ids);
        	result = "批量提交审核商品失败：" + e.getMessage();
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = {"/online.json"})
    public String online(@RequestParam("ids[]") Integer[] ids) {
    	String result = "";
    	try {
    		productService.online(getOperaterId(), ids);
        } catch (Exception e) {
        	logger.error("上架商品失败,goodsId:{0}", e, Arrays.toString(ids));
        	result = "上架商品失败：" + e.getMessage();
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = {"/offline.json"})
    public String offline(@RequestParam("ids[]") Integer[] ids) {
    	String result = "";
    	try {
    		productService.offline(getOperaterId(), ids);
        } catch (Exception e) {
        	logger.error("下架商品失败,goodsId:{0}", e, Arrays.toString(ids));
        	result = "下架商品失败：" + e.getMessage();
        }
        return result;
    }
    
    // ---- admin -------------------------------------------------------------
    @ResponseBody
    @RequestMapping(value = {"/check.json"})
    public String batchCheck(@RequestParam("ids[]") Integer[] ids) {
    	String result = "";
    	try {
    		productService.check(getOperaterId(), ids);
        } catch (Exception e) {
        	logger.error("审核商品失败,goodsId:{0}", e, Arrays.toString(ids));
        	result = "审核商品失败：" + e.getMessage();
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = {"/reject.json"})
    public String batchReject(@RequestParam("ids[]") Integer[] ids) {
    	String result = "";
    	try {
    		productService.reject(ids);
        } catch (Exception e) {
        	logger.error("驳回商品失败,goodsId:{0}", e, Arrays.toString(ids));
        	result = "驳回商品失败：" + e.getMessage();
        }
        return result;
    }
    
    @RequestMapping("/limit/show.htm")
    public String shouLimit(Integer goodsId, ModelMap modelMap) {
    	Product product = productService.get(goodsId);
    	modelMap.put("product", product);
    	return "tpd/goodsLimit";
    }
    
    @ResponseBody
    @RequestMapping("/limit/update.htm")
    public String updateLimit(Product product) {
    	Integer limitNum = product.getLimitNum();
    	if (limitNum == null || limitNum.intValue() < 0) {
    		product.setLimitNum(Integer.valueOf(0));
    		product.setLimitDay(Integer.valueOf(0));
    	} else {
    		product.setLimitNum(limitNum);
    		product.setLimitDay(Integer.valueOf(1)); //XXX:默认1天
    	}
    	
    	try {
    		productService.updateByScmProductId(product);
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    		return "限购设置失败！";
    	}
    	return "success";
    }

    // 检查商品信息状态
    private void checkGoodsStatus(Integer id, String[] statusArr, String optMsg) {
        Product product = productService.get(id);
        boolean correctStatus = false;
    	for (String status : statusArr) {
	        if (StringUtil.equals(product.getTpdGoodsStatus(), status)) {
	    		correctStatus = true;
	    		break;
			}
    	}
    	if (!correctStatus) {
    		throw new BizException(product.getGoodsSn() + " 为" + product.getTpdGoodsStatusName() + "状态，不能" + optMsg);
    	}
    }
    
    // 检查商品信息导入状态
    private void checkGoodsImportStatus(Integer id) {
        String goodsSn = productService.get(id).getGoodsSn();
    	List<ProductSub> goodsLaborList = productLaborMapper.selectByGoodsIdGroupByColorId(id);
        if (goodsLaborList == null || goodsLaborList.size() == 0)
        	throw new BizException(goodsSn + " 还没有导入颜色尺寸.不能提交审核");
        Boolean[] validateGalleryResult = new Boolean[2];
        for (ProductSub goodsLabor : goodsLaborList) {
        	validateGalleryResult = validateGoodsGallery(goodsLabor);
        	if (!validateGalleryResult[0]) {
    			throw new BizException(goodsSn + " 还没有导入商品默认图.不能提交审核");
    		}
    		if (!validateGalleryResult[1]) {
    			throw new BizException(goodsSn + " 还没有导入商品局部图.不能提交审核");
    		}
    	}
    }
    
    /**
     * 过滤disabled字段
     * @param goods
     */
    private String[] filerDisabled(Product goods) {
        List<String> arrsList = new ArrayList<>();
        if (StringUtil.isBlank(goods.getGoodsSn())) {
            arrsList.add("goodsSn");
        }
        if (goods.getCatId() == null) {
            arrsList.add("catId");
        }
        if (goods.getBrandId() == null) {
            arrsList.add("brandId");
        }
        if (goods.getStyleId() == null) {
            arrsList.add("styleId");
        }
        if (goods.getCoopId() == null) {
            arrsList.add("coopId");
        }
        if (goods.getUnitId() == null) {
            arrsList.add("unitId");
        }
        if (goods.getProviderId() == null) {
            arrsList.add("providerId");
        }
        if (goods.getGoodsYear() == null) {
            arrsList.add("goodsYear");
        }
        if (goods.getGoodsMonth() == null) {
            arrsList.add("goodsMonth");
        }
        String[] ignoreProperties = new String[arrsList.size()];
        for (int i = 0; i < ignoreProperties.length; i++) {
            ignoreProperties[i] = arrsList.get(i);
        }
        return ignoreProperties;
    }

    @RequestMapping(value = "/delete.json")
    @ResponseBody
    public String delete(@RequestParam("goodsId") String id) {
    	String result = "";
    	try {
    		String[] optStatusArr = {"0", "1"};
    		checkGoodsStatus(Integer.valueOf(id), optStatusArr, "删除");
    		List<ProductGallery> goodsGallerys = getGallerysByGoodsId(id);
            productService.deleteGoods(Integer.valueOf(id));
            deleteGalleryFileByGoodsId(goodsGallerys);
        } catch (BizException e) {
        	logger.error("删除商品失败,goodsId:{0}", e, id);
        	result = "删除商品失败";
        } catch (Exception e) {
        	logger.error("删除商品失败,goodsId:{0}", e, id);
        	result = "删除商品失败";
        }
        return result;
    }
    
    @RequestMapping(value = "/batchDelete.json")
    @ResponseBody
    public String batchDelete(String ids) {
    	String result = "";
    	try {
    		String[] optStatusArr = {"0", "1"};
    		List<List<ProductGallery>> goodsGalleryLists = new ArrayList<List<ProductGallery>>();
    		for (String id : ids.split(",")) {
        		checkGoodsStatus(Integer.valueOf(id), optStatusArr, "删除");
    			goodsGalleryLists.add(getGallerysByGoodsId(id));
    		}
    		productService.batchDelete(ids);
    		for (List<ProductGallery> goodsGallerys : goodsGalleryLists) {
    			deleteGalleryFileByGoodsId(goodsGallerys);
    		}
        } catch (BizException e) {
        	logger.error("批量删除商品失败,goodsId:{0}", e, ids);
        	result = "批量删除商品失败：" + e.getMessage();
        } catch (Exception e) {
        	logger.error("批量删除商品失败,goodsId:{0}", e, ids);
        	result = "批量删除商品失败：" + e.getMessage();
        }
        return result;
    }
    
    @RequestMapping("/validation.json")
    @ResponseBody
    public String validation(@RequestParam("key") String key,
                                 @RequestParam("value") String value, Product goods) {
        String result = "";
        try {
            if (StringUtil.equals(key, "goodsSn")) {
                goods.setGoodsSn(value);
            }
            productService.validation(goods);
        } catch (BizException e) {
            logger.warn("Goods验证,验证失败", e);
            result = "商品款号已经存在";
        } catch (IllegalArgumentException e) {
            logger.warn("Goods验证,验证失败", e);
            result = "商品款号已经存在";
        } catch (Exception e) {
        	logger.error("Goods验证,验证失败", e);
        	result = "商品款号已经存在";
        }
        return result;
    }
    
    @RequestMapping("/deleteGallery.json")
    @ResponseBody
    public String deleteGallery(@RequestParam("imgId") String imgId) {
        String result = "";
        try {
        	ProductGallery goodsGallery = productGalleryMapper.get(Integer.valueOf(imgId));
        	productGalleryMapper.delete(Integer.valueOf(imgId));
        	doDeleteGalleryFile(goodsGallery);
        } catch (BizException e) {
        	logger.error("删除商品图失败,imgId:{0}", e, imgId);
        	result = "删除商品图失败";
        } catch (Exception e) {
        	logger.error("删除商品图失败,imgId:{0}", e, imgId);
        	result = "删除商品图失败";
        }
        return result;
    }
    
    @RequestMapping("/deleteBcs.json")
    @ResponseBody
    public String deleteBcs(@RequestParam("goodsId") String goodsId) {
        String result = "";
        try {
        	Product goods = productService.get(Integer.valueOf(goodsId));
        	if (goods != null) {
        		Product goodsTmp = new Product();
        		goodsTmp.setGoodsId(goods.getGoodsId());
        		goodsTmp.setScDesc("");
        		productService.update(goodsTmp);
        	}
        } catch (BizException e) {
        	logger.error("删除尺寸对照图失败,imgId:{0}", e, goodsId);
        	result = "删除尺寸对照图失败";
        } catch (Exception e) {
        	logger.error("删除尺寸对照图失败,imgId:{0}", e, goodsId);
        	result = "删除尺寸对照图失败";
        }
        return result;
    }
    
    /**
     * 处理重复Color数据
     * @param goods
     * @return
     */
    private Product handleGoodsColorData(Product good) {
    	List<ProductSub> goodsLabors = good.getGoodsLabors();

        Product goodsVo = new Product();
        BeanUtils.copyProperties(good, goodsVo);
        //
        List<GoodsColorVo> colorVos = new ArrayList<>();
        if (goodsLabors != null && goodsLabors.size() > 0)
            for (ProductSub goodsLabor : goodsLabors) {
                GoodsColorVo vo = genColorVo(colorVos, goodsLabor);
                //
                GoodsSizeVo sizeVo = new GoodsSizeVo();
                sizeVo.setConsignNum(goodsLabor.getConsignNum());
                sizeVo.setGlId(goodsLabor.getGlId());
                sizeVo.setSizeCode(goodsLabor.getSizeCode());
                sizeVo.setSizeName(goodsLabor.getSizeName());
                sizeVo.setProviderBarcode(goodsLabor.getTpdProviderBarcode());
                sizeVo.setPic(Boolean.parseBoolean(goodsLabor.getIsPic().toString()));
                //
                List<GoodsSizeVo> sizeList = vo.getSizeList();
                if (sizeList == null) {
                    sizeList = new ArrayList<>();
                }
                sizeList.add(sizeVo);
                vo.setSizeList(sizeList);
                vo.setGalleryList(goodsLabor.getGoodsGallerys());
            }
        goodsVo.setColorVos(colorVos);
        
        // 检查商品信息导入状态
        goodsVo.setImportStatus("Red");
        List<ProductSub> goodsLaborList = productLaborMapper.selectByGoodsIdGroupByColorId(good.getGoodsId());
        if (goodsLaborList != null && goodsLaborList.size() > 0) {
        	goodsVo.setImportStatus("Yellow");
        	
        	Boolean galleryCheck = true;
	        Boolean[] validateGalleryResult = new Boolean[2];
	        for (ProductSub goodsLabor : goodsLaborList) {
	        	validateGalleryResult = validateGoodsGallery(goodsLabor);
	        	if (!validateGalleryResult[0] || !validateGalleryResult[1]) {
	        		galleryCheck = false;
	        		break;
	        	}
	    	}
	        if (galleryCheck) {
	        	goodsVo.setImportStatus("Blue");
	        	if (goodsVo.getScDesc() !=null && !"".equals(goodsVo.getScDesc()))
	        		goodsVo.setImportStatus("Green");
	        }
        }
    	return goodsVo;
    }
    
    private Boolean[] validateGoodsGallery(ProductSub goodsLabor) {
    	Boolean[] result = new Boolean[2];
    	HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("goodsId", goodsLabor.getGoodsId());
		param.put("colorId", goodsLabor.getColorId());
		List<ProductGallery> goodsGalleryList = productGalleryMapper.selectByGoodsId(param);
		boolean defaultExist = false, partExist = false;
		for (ProductGallery goodsGallery : goodsGalleryList) {
			if ("default".equals(goodsGallery.getImgDefault()))
				defaultExist = true;
			if ("part".equals(goodsGallery.getImgDefault()))
				partExist = true;
		}
		result[0] = defaultExist;
		result[1] = partExist;
		return result;
    }
    
    /**
     * 根据GoodsLabor判断,如果在List中存在,则直接返回,如果不存在则构建此对象设在在List中,返回此对象
     * @param list
     * @param goodsLabor
     * @return
     */
    public static GoodsColorVo genColorVo(List<GoodsColorVo> list, ProductSub goodsLabor) {
        for (GoodsColorVo vo : list) {
            if (vo.getColorId() == goodsLabor.getColorId()) {
                return vo;
            }
        }
        GoodsColorVo colorVo = new GoodsColorVo();
        colorVo.setColorId(goodsLabor.getColorId());
        colorVo.setColorName(goodsLabor.getColorName());
        colorVo.setIsPic(goodsLabor.getIsPic());
        list.add(colorVo);
        return colorVo;
    }
    
    @RequestMapping("/list/export.htm")
    public void export(Product goods, HttpServletResponse response) {
    	goods.setProviderId(SessionHelper.getProvider().getProviderId());
    	productService.exportGoodsData(response, goods);
    }
    
    @RequestMapping("/consign/toUpdate.htm")
    public String toUpdateConsignNum(Integer glId, ModelMap modelMap) {
    	modelMap.put("glId", glId);
    	return "tpd/goodsConsignNum";
    }
    
    @ResponseBody
    @RequestMapping("/consign/update.htm")
    public String updateConsignNum(@RequestParam("upKey") Integer glId, @RequestParam("upVal") String consignNum) {
    	Integer consign = null;
    	try {
    		consign = Integer.valueOf(consignNum);
    	} catch (Exception e) {
    		logger.error("修改商品虚库库存失败：不是有效的数字！", e);
    		return "商品库存必须为0或正整数！";
    	}
    	
    	ProductSub sub = productLaborMapper.get(glId);
    	if (sub == null) {
    		return "指定的商品SKU不存在！";
    	}
    	
    	sub.setConsignNum(consign);
    	productService.updateConsignNum(sub);
    	
    	return "success";
    }
    
    private List<ProductGallery> getGallerysByGoodsId(String id) {
    	List<ProductGallery> goodsGallerys = new ArrayList<ProductGallery>();
    	List<ProductSub> goodsLabors = productLaborMapper.selectByGoodsId(Integer.valueOf(id));
		if (goodsLabors != null && goodsLabors.size() > 0) {
    		for (ProductSub goodsLabor : goodsLabors) {
	    		HashMap<String, Object> param = new HashMap<String, Object>();
	    		param.put("goodsId", goodsLabor.getGoodsId());
	    		param.put("colorId", goodsLabor.getColorId());
	    		List<ProductGallery> goodsGalleryList = productGalleryMapper.selectByGoodsId(param);
	    		if (goodsGalleryList != null && goodsGalleryList.size() > 0) {
	    			for (ProductGallery goodsGallery : goodsGalleryList) {
	    				goodsGallerys.add(goodsGallery);
	    			}
	    		}
    		}
		}
		return goodsGallerys;
    }
    
    private void deleteGalleryFileByGoodsId(List<ProductGallery> goodsGalleryList) {
    	for (ProductGallery goodsGallery : goodsGalleryList) {
			doDeleteGalleryFile(goodsGallery);
		}
    }
    
    private void doDeleteGalleryFile(ProductGallery goodsGallery) {
    	doDeleteFile(goodsGallery.getImgUrl());
    	doDeleteFile(goodsGallery.getThumbUrl());
    	doDeleteFile(goodsGallery.getMiddleUrl());
    	doDeleteFile(goodsGallery.getBigUrl());
    	doDeleteFile(goodsGallery.getTeenyUrl());
    	doDeleteFile(goodsGallery.getSmallUrl());
    	doDeleteFile(goodsGallery.getImgOriginal());
    	doDeleteFile(goodsGallery.getUrl120160());
    	doDeleteFile(goodsGallery.getUrl99132());
    	doDeleteFile(goodsGallery.getUrl480640());
    	doDeleteFile(goodsGallery.getUrl5684());
    	doDeleteFile(goodsGallery.getUrl222296());
    	doDeleteFile(goodsGallery.getUrl342455());
    	doDeleteFile(goodsGallery.getUrl170227());
    	doDeleteFile(goodsGallery.getUrl135180());
    	doDeleteFile(goodsGallery.getUrl251323());
    	doDeleteFile(goodsGallery.getUrl502646());
    	doDeleteFile(goodsGallery.getUrl12001600());
    }
    
    private void doDeleteFile(String fileName) {
    	if (fileName == null || fileName.isEmpty()) {
            return;
        }
        deleteFile(ConstantsHelper.getPicRootPath() + "/" + fileName);
    }
    
}