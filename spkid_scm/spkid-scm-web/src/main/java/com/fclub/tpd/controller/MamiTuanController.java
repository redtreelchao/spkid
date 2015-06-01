package com.fclub.tpd.controller;

import static com.fclub.tpd.helper.ConstantsHelper.getPicRootPath;
import static com.fclub.tpd.helper.ConstantsHelper.getTuanUploadDir;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.FileUtil;
import com.fclub.common.lang.utils.ImageUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.batch.importing.dto.GoodsDescDTO;
import com.fclub.tpd.biz.MamiTuanService;
import com.fclub.tpd.biz.ProductService;
import com.fclub.tpd.dataobject.MamiTuan;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.mapper.InnerProductMapper;
import com.fclub.tpd.vo.JsonResult;
import com.fclub.web.util.UploadUtil;
import com.fclub.web.util.WebUtil;

@Controller
@RequestMapping("/mamituan")
public class MamiTuanController extends BaseController {

    @Autowired
    private MamiTuanService mamiTuanService;
    @Autowired
    private ProductService	productService;
    @Autowired
    private InnerProductMapper innerProductMapper;
    
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtil.getDatetimeFormat(), true));
    }
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/mamiTuan";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<MamiTuan> page, MamiTuan mamiTuan) {
    	if (!isAdmin()) {
    		mamiTuan.setProvider(getProvider());
    	}
    	
        List<MamiTuan> tuanList = mamiTuanService.queryByPage(page, mamiTuan);
        page.setResult(tuanList);
        
        modelMap.put("page", page);
        return "tpd/mamiTuanList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo(ModelMap modelMap) {
    	modelMap.put("readOnly", 0);
    	return "tpd/mamiTuanAdd";
    }
    
    @RequestMapping("/add/query.htm")
    @ResponseBody
    public JsonResult addQuery(Product product, Integer tuanId) {
    	JsonResult result = new JsonResult();
    	if (tuanId == null) {
	    	product = innerProductMapper.selectProductBySn(product.getGoodsSn());
	    	if (product == null) {
	    		result.setSuccess(false);
	        	result.setMsg("此商品不存在！");
	        	return result;
	    	} else if (!product.getProviderId().equals(getProviderId())) {
	    		result.setSuccess(false);
	        	result.setMsg("此商品不是您公司的商品！");
	        	return result;
	    	}
	    	if (mamiTuanService.checkByProductId(product.getGoodsId())) {
	    		result.setSuccess(false);
	        	result.setMsg("此商品已存在团购！");
	        	return result;
	    	}
	    	
	    	Product goods = productService.getExtGoodsById(Integer.valueOf(product.getTpdGoodsId()));
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
	        	result.setSuccess(false);
	        	result.setMsg("载入商品，数据错误，请联系管理员！");
	        }
	        Map<String, Object> root = new HashMap<String, Object>();
	        root.put("goodsDescVo", goodsDescVo);
	        String tuanDesc = FileUtil.generateStringByTemplate(root, "/tpd/mamiTuanAddGoodsDesc.vm") + goods.getGoodsDesc();
	        result.setMsg(tuanDesc);
        }
        result.setSuccess(true);
        result.setResult(product.getGoodsId());
        return result;
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, MamiTuan tuan, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
    	String isSubmit = request.getParameter("isSubmit");
    	if (StringUtil.equals(isSubmit, "0")) {
    		tuan.setStatus(4);
    	} else if(StringUtil.equals(isSubmit, "1")) {
    		tuan.setStatus(0);
    	}
    	
    	if(file == null || file.isEmpty()) {
    		if (tuan.getTuanId() == null) {
    			throw new BizException("请上传团购商品图！");
    		}
    	} else {
        	String imagePath = getPicRootPath() + "/" + getTuanUploadDir();        	
        	
        	try {
        		String img = UploadUtil.uploadImage(file, imagePath, String.valueOf(DateUtil.getCurrentTime().getTime()));
        		tuan.setTuanImg(getTuanUploadDir() + "/" + img);
        		
        		String imgPath = imagePath + "/" + img;
        		String imgSuffix = img.substring(img.lastIndexOf("."));
        		// 生成三张缩略图 540*330 380*232 160*98
        		System.out.println("imgPath: " + imgPath);
        		System.out.println("imgPath1: " + imgPath + ".540*330" + imgSuffix);
				ImageUtil.resize(new File(imgPath), 540, 330, imgPath
						+ ".540x330" + imgSuffix);
				ImageUtil.resize(new File(imgPath), 380, 232, imgPath
						+ ".380x232" + imgSuffix);
				ImageUtil.resize(new File(imgPath), 160, 98, imgPath
						+ ".160x98" + imgSuffix);
			} catch (Exception e) {
				throw new BizException("请上传图片格式的文件！");
			}	
    	}
    	if (tuan.getTuanId() == null) {
	    	tuan.setOpAddAid(getOperaterId());
	    	mamiTuanService.add(tuan);
    	} else {
    		tuan.setOpUpdateAid(getOperaterId());
    		mamiTuanService.update(tuan);
    	}
        
        modelMap.addAttribute("currentUrl", "/mamituan/addTo.htm");
        modelMap.addAttribute("backUrl", "/mamituan/list/main.htm");
        return "commons/success2";
    }
    
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, Integer tuanId) {
        MamiTuan mamiTuan = mamiTuanService.findById(tuanId);
        String goodsSn = innerProductMapper.selectProductSnById(mamiTuan.getProductId());
        modelMap.put("readOnly", 1);
        modelMap.put("tuan", mamiTuan);
        modelMap.put("goodsSn", goodsSn);
        return "tpd/mamiTuanAdd";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, Integer tuanId, HttpServletResponse response) {
        MamiTuan mamiTuan = mamiTuanService.findById(tuanId);
        if (mamiTuan == null) {
        	WebUtil.send404Error(response, "记录不存在！");
        	return null;
        } else {
        	String goodsSn = innerProductMapper.selectProductSnById(mamiTuan.getProductId());
        	modelMap.put("readOnly", 0);
        	modelMap.put("tuan", mamiTuan);
        	modelMap.put("goodsSn", goodsSn);
        	return "tpd/mamiTuanAdd";
        }
    }
    
    @RequestMapping(value = "/check.htm")
    @ResponseBody
    public String check(Integer tuanId) {
        try {
        	MamiTuan mamiTuan = mamiTuanService.findById(tuanId);
        	mamiTuan.setStatus(1);
        	mamiTuan.setOpCheckAid(getOperaterId());
        	mamiTuanService.update(mamiTuan);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
    @RequestMapping(value = "/reject.htm")
    @ResponseBody
    public String reject(Integer tuanId) {
        try {
        	MamiTuan mamiTuan = mamiTuanService.findById(tuanId);
        	mamiTuan.setStatus(0);
        	mamiTuan.setOpUpdateAid(getOperaterId());
        	mamiTuanService.update(mamiTuan);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
    @RequestMapping(value = "/stop.htm")
    @ResponseBody
    public String stop(Integer tuanId) {
        try {
        	MamiTuan mamiTuan = mamiTuanService.findById(tuanId);
        	mamiTuan.setStatus(2);
        	mamiTuan.setOpStopAid(getOperaterId());
        	mamiTuanService.update(mamiTuan);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping(value = "/delete.htm")
    @ResponseBody
    public String delete(Integer tuanId) {
        try {
        	mamiTuanService.remove(tuanId);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }

}