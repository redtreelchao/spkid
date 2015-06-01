package com.fclub.tpd.biz.impl;

import static com.fclub.web.util.UploadUtil.deleteFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.batch.importing.dto.GoodsDescDTO;
import com.fclub.tpd.biz.ProductService;
import com.fclub.tpd.biz.ProductTypeLinkService;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.common.jxl.JxlsUtil;
import com.fclub.tpd.dataobject.Batch;
import com.fclub.tpd.dataobject.BcsImp;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.dataobject.ProductSub;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.mapper.InnerProductMapper;
import com.fclub.tpd.mapper.ProductGalleryMapper;
import com.fclub.tpd.mapper.ProductMapper;
import com.fclub.tpd.mapper.ProductSubMapper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper          productMapper;
    @Autowired
    private ProductSubMapper       productSubMapper;
    @Autowired
    private ProductTypeLinkService productTypeLinkService;
    @Autowired
    private InnerProductMapper     innerProductMapper;
    @Autowired
    private ProductGalleryMapper   productGalleryMapper;
    
    @Override
    public Product get(Integer id) {
        return productMapper.get(id);
    }

    @Override
    public Page<Product> findPage(Page<Product> page, Product goods) {

        page.setResult(productMapper.findPage(page, goods));
        return page;
    }

    @Override
    @Transactional
    public void save(Product goods) {
        productMapper.insert(goods);
    }

    @Override
    @Transactional
    public void update(Product goods) {
        productMapper.update(goods);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        productMapper.delete(id);
    }

    @Override
    public ProductGallery getDefaultImg(int goodsId) {
        return productGalleryMapper.getDefaultGallery(goodsId);
    }
    
    @Override
    public Page<Product> queryGoodsByPage(Page<Product> page, Product goods) {
    	page.setResult(productMapper.queryGoodsByPage(page, goods));
    	return page;
    }
    
    @Override
    public void validation(Product goods) {
    	if (goods != null)
            if (StringUtil.isNotBlank(goods.getGoodsSn())) {
                checkGoodsSnExsits(goods.getGoodsSn(),
                    goods.getGoodsId() != null ? goods.getGoodsId() : 0);
            }
    }
    
    /**
     * 验证商品款号是否已经存在
     * 
     * @param goodsSn
     * @param oldId
     */
    public void checkGoodsSnExsits(String goodsSn, int oldId) {
        if (StringUtil.isNotBlank(goodsSn)) {
            goodsSn = StringUtil.upperCase(goodsSn);
            Product goods = productMapper.getExtraGoodsBySn(goodsSn);
            if (goods != null) {
                if ((oldId > 0 && goods.getGoodsId() != oldId)
                    || (oldId == 0 && goods.getGoodsId() != 0)) {
                    throw new BizException("商品款号已经存在");
                }
            }
        }
    }
    
    @Override
    public Product getExtGoodsById(Integer id) {
    	return productMapper.selectExtGoodsById(id);
    }
    
    @Override
    public void batchSubmit(String ids) {
    	String[] idAry = ids.split(",");
    	Product goods = null;
    	for (String id : idAry) {
    		if (StringUtil.isNotBlank(id)) {
	    		goods = get(Integer.valueOf(id));
	    		if (goods != null) {
	    			goods.setTpdGoodsStatus("1");
	    			update(goods);
	    		}
    		}
    	}
    }
    
    @Override
    public void deleteGoods(Integer goodsId) {
    	Product goods = productMapper.get(goodsId);
    	if (goods == null) {
            throw new BizException("商品不存在");
        } else {
        	String goodsScDesc = goods.getScDesc();
        	if (StringUtil.isNotBlank(goodsScDesc)) {
        		doDeleteBcsImageFile(goodsScDesc);
        	}
        }
    	productMapper.delete(goodsId);
    	productSubMapper.deleteByGoodsId(goodsId);
        productGalleryMapper.deleteByGoodsId(goodsId);
        productTypeLinkService.deleteGoodsTypeLinkByGoodsId(goodsId);
    }
    
    @Override
    public void batchDelete(String ids) {
    	String[] idAry = ids.split(",");
    	for (String id : idAry) {
    		if (StringUtil.isNotBlank(id)) {
    			deleteGoods(Integer.valueOf(id));
    		}
    	}
    }
    
    @Override
    public void exportGoodsData(HttpServletResponse response, Product goods) {
    	List<Product> goodsList = productMapper.queryExportGoodsData(goods);
    	String goodsDescAdditional = null;
    	ObjectMapper mapper = new ObjectMapper();
    	GoodsDescDTO goodsDescDTO = null;
    	for (Product good : goodsList) {
    		goodsDescAdditional = good.getGoodsDescAdditional();
    		if (StringUtil.isNotBlank(goodsDescAdditional)) {
    			try {
    				goodsDescDTO = mapper.readValue(goodsDescAdditional, GoodsDescDTO.class);
    				good.setDesc_composition(goodsDescDTO.getDesc_composition());
    				good.setDesc_dimensions(goodsDescDTO.getDesc_dimensions());
    				good.setDesc_material(goodsDescDTO.getDesc_material());
    				good.setDesc_waterproof(goodsDescDTO.getDesc_waterproof());
    				good.setDesc_crowd(goodsDescDTO.getDesc_crowd());
    				good.setDesc_notes(goodsDescDTO.getDesc_notes());
    			} catch (IOException e) {
    	            throw new BizException("编辑商品，数据错误，请联系管理员！");
    	        }
    		}
    		
    		if (StringUtil.isNotBlank(good.getGoodsSex())) {
    			if (good.getGoodsSex().equals("m")) {
    				good.setGoodsSex("男");
    			} else if (good.getGoodsSex().equals("w")) {
    				good.setGoodsSex("女");
    			} else if (good.getGoodsSex().equals("a")) {
    				good.setGoodsSex("中性");
    			}
    		}
    	}
    	
    	Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("result", goodsList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "第三方商品信息" + dateFormat.format(new Date());
        JxlsUtil.exportExcel(response, fileName, beans, "TpdGoodsList.xls");
    }
    
    @Override
    public void batchUpdateBcsUrl(BcsImp bcsImp) {
    	if (bcsImp == null) return;
    	Product goods = new Product();
    	goods.setCatId(bcsImp.getCatId());
    	goods.setBrandId(bcsImp.getBrandId());
    	goods.setGoodsSex(bcsImp.getSex());
    	if (StringUtil.isNotBlank(bcsImp.getImageUrl())) {
    		goods.setScDesc(genGoodsScDesc(bcsImp.getImageUrl()));
    	} else {
    		goods.setScDesc("");
    	}
    	productMapper.updateByBcsImp(goods);
    }
    
    @Override
    public void check(Integer adminId, Integer... ids) {
    	// TODO:校验
    	List<Product> checkList = new ArrayList<Product>();
    	for (Integer id : ids) {
	    	Product product = productMapper.get(Integer.valueOf(id));
	    	if (product == null) {
	    		throw new BizException("商品不存在，不能审核！");
	    	} else if (product.getTpdGoodsStatus() == "0") {
	    		throw new BizException("存在草稿状态的商品，不能审核！");
	    	} else if (product.getTpdGoodsStatus() == "2") {
	    		throw new BizException("存在已审核的商品，不能审核！");
	    	} else if (product.getTpdGoodsStatus() == "3") {
	    		throw new BizException("存在已上架的商品，不能审核！");
	    	}
	    	
	    	List<ProductSub> subList = productSubMapper.selectByGoodsIdGroupByColorId(id);
	    	if (subList == null || subList.size() == 0) {
	    		throw new BizException("商品未导入颜色尺寸，不能审核！");
	    	}
	    	for (ProductSub sub : subList) {
	    		HashMap<String, Object> param = new HashMap<String, Object>();
	    		param.put("goodsId", sub.getGoodsId());
	    		param.put("colorId", sub.getColorId());
	    		List<ProductGallery> tpdGoodsGalleryList = productGalleryMapper.selectByGoodsId(param);
	    		boolean defaultExist = false, partExist = false;
	    		for (ProductGallery tpdGoodsGallery : tpdGoodsGalleryList) {
	    			if ("default".equals(tpdGoodsGallery.getImgDefault()))
	    				defaultExist = true;
	    			if ("part".equals(tpdGoodsGallery.getImgDefault()))
	    				partExist = true;
	    		}
	    		if (!defaultExist) {
	    			throw new BizException("存在未导入默认图的商品，不能审核！");
	    		} else if (!partExist) {
	    			throw new BizException("存在未导入局部图的商品，不能审核！");
	    		}
	    	}
	    	
	    	checkList.add(product);
    	}
    	
    	for (Product product : checkList) {
    		try {
    			doCheck(product, adminId);
    		} catch (Exception e) {
    			throw new BizException("数据库操作异常！", e);
    		}
    	}
    }

	@Override
    public void reject(Integer... ids) {
    	for (Integer id : ids) {
    		Product goods = productMapper.get(id);
            if (goods != null) {
                goods.setTpdGoodsStatus("0");
            	productMapper.update(goods);
            }
    	}
    }
	
	@Override
    public void online(Integer adminId, Integer... ids) {
		// TODO: 校验商品信息
		Product product = null;
		for (Integer id : ids) {
			product = productMapper.get(id);
			if (product == null) {
				throw new BizException("商品不存在，不能上架！");
			} else if (!"2".equals(product.getTpdGoodsStatus())) {
				throw new BizException("存在未审核的商品，不能上架！");
			}
		}
		
		// 批量上架product_sub
        innerProductMapper.batchOnsale(ids, true);
        
        // 记录商品SKU上架记录
        innerProductMapper.batchInsertOnsaleRecord(ids, true, adminId);
        
        // 供应商平台商品上架
        productMapper.batchOnsale(ids, true);
    }

	@Override
    public void offline(Integer adminId, Integer... ids) {
		// TODO: 校验商品信息
		Product product = null;
		for (Integer id : ids) {
			product = productMapper.get(id);
			if (product == null) {
				throw new BizException("商品不存在，不能下架！");
			} else if(!"3".equals(product.getTpdGoodsStatus())) {
				throw new BizException("存在未上架的商品，不能下架！");
			}
		}
		
		// 批量上架product_sub
        innerProductMapper.batchOnsale(ids, false);
        
        // 记录商品SKU上架记录
        innerProductMapper.batchInsertOnsaleRecord(ids, false, adminId);
        
        // 供应商平台商品下架
        productMapper.batchOnsale(ids, false);
    }
	
	@Override
	public void updateConsignNum(ProductSub sub) {
		ProductSub innerSub = innerProductMapper.selectProductSub(sub.getGlId());
		
		productSubMapper.update(sub);
		if (innerSub != null) {
			innerSub.setConsignNum(sub.getConsignNum());
			innerProductMapper.batchUpdateConsignNum(Arrays.asList(new ProductSub[]{innerSub}));
		}
	}
	
	@Override
	public void updateByScmProductId(Product product) {
		productMapper.update(product);
		innerProductMapper.updateByScmProdutId(product);
	}
    
    private String genGoodsScDesc(String imageUrl) {
    	String imagePath = System.getProperty(SystemConstant.IMAGE_DOMAIN) + imageUrl;
    	return "<img title=\"尺寸图\" src=\"" + imagePath + "\">";
    }

	private void doDeleteBcsImageFile(String goodsScDesc) {
		String filePath = getBcsImageFilePath(goodsScDesc);
		if (filePath == null) {
			return;
		}
		deleteFile(filePath);
	}

	private String getBcsImageFilePath(String goodsScDesc) {
		String[] tmpAry = StringUtil.split(goodsScDesc, "\"");
    	if (tmpAry.length != 5) {
    		// 系统中包含老的数据，使用<table>记录，这种不符合要求。
    		// 新的尺寸图使用<img>标记，格式一定为<img title="尺寸图" 
    		// src="http://img.domain/dir/goodsSn.jpg">，故按 " 分割后，长度为5。
    		return null;
    	}
    	
    	String imgSrc = tmpAry[3];
    	String imgPath = imgSrc.substring(imgSrc.indexOf(System.getProperty(SystemConstant.UPLOAD_IMAGE_BCS)));
    	return ConstantsHelper.getPicRootPath() + imgPath;
	}
	
    private void doCheck(Product product, Integer adminId) {
    	Date auditTime = new Date();    	
    	Integer brandId = product.getBrandId();
    	Integer productId = product.getGoodsId();
    	Integer providerId = product.getProviderId();
    	
    	// 1 检查/创建批次信息
    	Integer batchId = innerProductMapper.selectBatchId(providerId, brandId);
    	if (batchId == null || batchId.intValue() <= 0) {
    		Batch batch = new Batch();
    		batch.setBrandId(brandId);
    		batch.setCreateAdmin(adminId);
    		batch.setProviderId(providerId);
    		batch.setBatchCode(doGenerateBatchCode(providerId, brandId));
    		
    		innerProductMapper.insertBatch(batch);
    		batchId = batch.getBatchId();
    	}
    	
    	// 2  更新TPD商品状态
    	product.setGoodsAuditAid(adminId);
    	product.setGoodsAuditTime(auditTime);
	    product.setGoodsAudit(1);
	    product.setTpdGoodsStatus("2");
	    productMapper.update(product);
	    
	    // 3  复制商品基本信息
	    innerProductMapper.copyProductInfo(productId, adminId);
	    
    	// 4 复制商品价格信息 
	    innerProductMapper.copyProductCost(productId, batchId, providerId, adminId);
	    
	    // 5  复制商品前台分类
	    innerProductMapper.copyProductType(productId);
	    
	    // 6  复制商品颜色尺寸
	    innerProductMapper.copyProductSub(productId, adminId);
	    
	    // 7  复制商品图片信息
	    innerProductMapper.copyProductGallery(productId, adminId);
    }
    
	private String doGenerateBatchCode(Integer providerId, Integer brandId) {
		return "BT" + providerId + brandId + System.currentTimeMillis();
	}
    
}