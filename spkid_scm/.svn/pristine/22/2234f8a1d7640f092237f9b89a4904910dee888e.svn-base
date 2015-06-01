package com.fclub.tpd.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.biz.ProductCardService;
import com.fclub.tpd.common.jxl.JxlsUtil;
import com.fclub.tpd.dataobject.ProductCard;
import com.fclub.tpd.mapper.ProductCardMapper;

@Service
public class ProductCardServiceImpl implements ProductCardService {

	@Autowired
    private ProductCardMapper productCardMapper;
    
    @Override
    public ProductCard get(Integer id) {
        return productCardMapper.get(id);
    }

    @Override
    public Page<ProductCard> findPage(Page<ProductCard> page, ProductCard productCard) {
        page.setResult(productCardMapper.findPage(page, productCard));
        return page;
    }
    
    @Override
    @Transactional
    public void save(ProductCard productCard) {
    	productCardMapper.insert(productCard);
    }

    @Override
    @Transactional
    public void update(ProductCard productCard) {
    	productCardMapper.update(productCard);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
    	productCardMapper.delete(id);
    }

    @Override
    @Transactional
    public void batchUpdate(List<ProductCard> cardList) {
    	productCardMapper.batchUpdate(cardList);
    }

	@Override
	@Transactional
	public void batchUpdate(String ids) {
		String[] idAry = ids.split(",");
		ProductCard productCard = null;
		Date curTime = DateUtil.getCurrentTime();
		for (String id : idAry) {
    		if (StringUtil.isNotBlank(id)) {
    			productCard = get(Integer.valueOf(id));
    			productCard.setIsUsed(true);
    			productCard.setUseTime(curTime);
    			update(productCard);
    		}
		}
	}

	@Override
	@Transactional
	public void batchDelete(String ids) {
		String[] idAry = ids.split(",");
		ProductCard productCard = null;
		for (String id : idAry) {
    		if (StringUtil.isNotBlank(id)) {
    			productCard = get(Integer.valueOf(id));
    			if (productCard == null) {
            		throw new BizException("卡密不存在！");
            	}
            	if (productCard.getIsUsed()) {
            		throw new BizException("卡密已使用，不能删除！");
            	}
            	delete(Integer.valueOf(id));
    		}
		}
	}
	
	@Override
	public void export(ProductCard productCard, HttpServletResponse response) {
		List<ProductCard> cardList = productCardMapper.queryExportCardData(productCard);
		
		Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("result", cardList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "虚拟商品卡密信息" + dateFormat.format(new Date());
        JxlsUtil.exportExcel(response, fileName, beans, "ProductCardList.xls");
	}
	
}