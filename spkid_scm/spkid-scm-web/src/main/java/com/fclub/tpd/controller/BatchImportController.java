/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.common.spring.SpringContextHolder;
import com.fclub.tpd.batch.importing.ImportService;
import com.fclub.tpd.batch.importing.dto.BatchImportSeach;
import com.fclub.tpd.batch.importing.dto.GoodsData;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportPolicy;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.batch.importing.enums.ImportType;
import com.fclub.tpd.biz.ProductService;
import com.fclub.tpd.common.holder.AdminAuthorityHolder;
import com.fclub.tpd.common.jxl.JxlsUtil;
import com.fclub.tpd.dataobject.BatchImport;
import com.fclub.tpd.dataobject.ImportList;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.helper.ZipUtil;
import com.fclub.tpd.vo.JsonResult;
import com.fclub.web.util.UploadUtil;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;

/**
 * @version $Id: BatchImportController.java, v 0.1 Jul 5, 2013 11:42:39 AM likaiping Exp $
 */
@Controller
@RequestMapping("/batchImport")
public class BatchImportController {
    LogUtil       logger = LogUtil.getLogger(getClass());
//    @Autowired
//    ImportService getImportService();
    @Autowired
    ProductService productService;
    
    private ImportService getImportService() {
    	return SpringContextHolder.getBean(ImportService.class);
    }

    @RequestMapping("/init.htm")
    public String init(ModelMap modelMap) {
        modelMap.put("batchNo", AdminAuthorityHolder.get().getProvider().getProviderCode());
        return "import/main";
    }

    @RequestMapping("/list/queryMainList.htm")
    public String queryMainList(ModelMap modelMap, Page<BatchImport> page, BatchImportSeach seach) {
        getImportService().findPage(page, seach);
        modelMap.put("page", page);
        return "import/importMainDetailList";
    }

    @RequestMapping("/list/query.htm")
    public String getPageByBatchNo(ModelMap modelMap, Page<ImportList> page, String type) {
        getImportService().getPageByBatchNo(page, type, AdminAuthorityHolder.get().getProvider()
            .getProviderCode());
        modelMap.put("page", page);
        return "import/importDetailList";
    }

    @RequestMapping("/importView.htm")
    public String importView(ModelMap modelMap, String batchNo, String type) {
        if (StringUtil.isBlank(batchNo)) {
            batchNo = AdminAuthorityHolder.get().getProvider().getProviderCode();
        }
        modelMap.put("batchNo", batchNo);
        return "import/" + type;
    }

    @ResponseBody
    @RequestMapping("/main.htm")
    public JsonResult importMain(ModelMap modelMap, HttpServletRequest request) {
    	JsonResult result = new JsonResult();
    	
    	Integer isVirtual = Integer.valueOf(request.getParameter("isVirtual").toString());
        Integer generateMethod = Integer.valueOf(request.getParameter("generateMethod").toString());
    	if (isVirtual == -1) {
    		result.setMsg("请选择商品类型！");
            result.setSuccess(false);
            return result;
        }
    	
    	if (isVirtual == 1 && generateMethod == 0) {
    		result.setMsg("请选择虚拟卡生成方式！");
            result.setSuccess(false);
            return result;
        }
    	
    	Integer[] virtualArr = new Integer[2];
    	virtualArr[0] = 0;
    	virtualArr[1] = 0;
    	
        try {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multiRequest.getFile("file");
            ImportContext importContext = new ImportContext();
            importContext.setImportType(ImportType.GOODS_MAIN_INFORMATION);
            importContext.setFile(file);
            importContext.setProviderId(AdminAuthorityHolder.get().getProvider().getProviderId());
            if (isVirtual == 1) {
            	virtualArr[0] = isVirtual;
            	virtualArr[1] = generateMethod;
            }
            importContext.setContent(virtualArr);
            ImportResult importResult = getImportService().importProcess(importContext);
            result.setResult(importResult);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("文件上传异常，请联系管理员", e);
            result.setMsg("文件上传异常，请联系管理员");
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/importConsign.htm")
    public JsonResult importConsign(ModelMap modelMap, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        
        Integer isVirtual = Integer.valueOf(request.getParameter("isVirtual").toString());
    	if (isVirtual == -1) {
    		result.setMsg("请选择商品类型！");
            result.setSuccess(false);
            return result;
        }
        
        try {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multiRequest.getFile("file");
            ImportContext importContext = new ImportContext();
            importContext.setImportType(ImportType.GOODS_CONSIGN);
            importContext.setFile(file);
            importContext.setProviderId(AdminAuthorityHolder.get().getProvider().getProviderId());
            importContext.setBatchNo(AdminAuthorityHolder.get().getProvider().getProviderCode());
            importContext.setContent(isVirtual);
            ImportResult importResult = getImportService().importProcess(importContext);
            result.setResult(importResult);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("文件上传异常，请联系管理员", e);
            result.setMsg("文件上传异常，请联系管理员");
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/importColorSize.htm")
    public JsonResult importColorSize(ModelMap modelMap, HttpServletRequest request, String batchNo) {
        JsonResult result = new JsonResult();
        try {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multiRequest.getFile("file");
            ImportContext importContext = new ImportContext();
            importContext.setBatchNo(AdminAuthorityHolder.get().getProvider().getProviderCode());
            importContext.setImportType(ImportType.GOODS_COLOR_SIZE);
            importContext.setFile(file);
            ImportResult importResult = getImportService().importProcess(importContext);
            result.setResult(importResult);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("importColorSize导入异常", e);
            result.setMsg("系统导入异常，请联系管理员");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/resultData.htm")
    public void resultData(ModelMap modelMap, HttpServletResponse response, int listId) {
        String resultFilePath = getImportService().getResultData(listId);
        InputStream is = null;
        OutputStream os = null;
        File file = new File(resultFilePath);
        if (!file.exists()) {
            throw new BizException("结果文件不存在");
        }
        try {
            is = new FileInputStream(resultFilePath);

            byte[] c = new byte[4096];
            response.reset();
            os = response.getOutputStream();
            int len = 0;
            while ((len = is.read(c)) != -1) {
                os.write(c, 0, len);
            }
            os.flush();

        } catch (Exception e) {
            logger.error("读取结果文件错误,resultFilePath：{0}", e, resultFilePath);
            throw new BizException("读取结果文件错误");
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
            } catch (Exception e) {
            }
        }
    }

    @RequestMapping("/resultDatas.htm")
    public String result(String batchNo, String type) {
        ImportList importList = null;
        List<ImportList> importLists = getImportService().getLastByBatchNo(type, batchNo);
        if (importLists != null) {
            ImportList temp = importLists.get(0);
            if (StringUtil.equals(temp.getImpBatchNo(), batchNo)) {
                importList = temp;
            }
        }
        if (importList == null) {
            return "redirect:/batchImport/resultData.htm?listId=0";
        }
        return "redirect:/batchImport/resultData.htm?listId=" + importList.getId();
    }

    @RequestMapping("/downloadMain.htm")
    public void downloadMain(ModelMap modelMap, HttpServletRequest request,
                             HttpServletResponse response, String batchNo) {
    	response.reset();
        byte[] bs = new byte[1024];
        int readLen = 0;
        String fileName = "goods.xls";
        String contextPath = request.getSession().getServletContext().getRealPath("/");
        String filePath = contextPath + "/cachefile/" + fileName;
        File file = new File(filePath);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = null;
        InputStream is = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            is = new BufferedInputStream(new FileInputStream(file));
            while ((readLen = is.read(bs, 0, 1024)) != -1) {
                outputStream.write(bs, 0, readLen);
            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error("下载批次模板文件,文件下载失败，batchNo：{0}", e, batchNo);
            throw new BizException("下载批次模板文件,请联系管理员");
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (is != null)
                    is.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 导出商品颜色尺寸信息
     * */
    @RequestMapping("/downloadColorSize.htm")
    public void downloadColorSize(HttpServletResponse response, String batchNo) throws IOException {
        if (StringUtil.isBlank(batchNo)) {
            throw new BizException("参数不能为空");
        }
        List<GoodsData> dataList = getImportService().getGoodsColorSizeData(batchNo);
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("result", dataList);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "商品颜色尺寸信息" + dateFormat.format(new Date());
        JxlsUtil.exportExcel(response, fileName, beans, "ColorSize.xls");
    }

    @ResponseBody
    @RequestMapping("/getProgressOf.json")
    public JsonResult getProgressOf(String type, String iden) {
        JsonResult result = new JsonResult();
        if (StringUtil.isBlank(type)) {
            result.setSuccess(false);
            result.setMsg("请传递请求类型");
            return result;
        }
        String batchNO = type + "/" + iden;
        String progress = getImportService().getProgress(batchNO);
        result.setResult(progress);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping("/progressBar.json")
    public JsonResult getProgressBar(String type, String iden) {
        JsonResult result = new JsonResult();
        if (StringUtil.isBlank(type)) {
            result.setSuccess(false);
            result.setMsg("请传递请求类型");
            return result;
        }
        String batchNo = type + "/" + iden;
        try {
            String progress = getImportService().getProgressBar(batchNo);
            logger.info("progress of {0} is {1}", batchNo, progress);
            result.setResult(progress);
            result.setSuccess(true);
        } catch (NumberFormatException e) {
            logger.error("getProgressBar error: ", e);
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getState.json")
    public JsonResult getState(String type) {
        JsonResult result = new JsonResult();
        if (StringUtil.isBlank(type)) {
            result.setSuccess(false);
            result.setMsg("请传递请求类型");
            return result;
        }
        ImportStatus status = getImportService().getState(type, AdminAuthorityHolder.get().getProvider()
            .getProviderCode());
        result.setResult(status);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping("/getStateOf.json")
    public JsonResult getStateOf(String type, String iden) {
        JsonResult result = new JsonResult();
        if (StringUtil.isBlank(type)) {
            result.setSuccess(false);
            result.setMsg("请传递请求类型");
            return result;
        }
        ImportStatus status = getImportService().getState(type, iden);
        result.setResult(status);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping("/goodsGallery.htm")
    public JsonResult goodsGallery(ModelMap modelMap) {
        JsonResult result = new JsonResult();
        try {
            ImportContext importContext = new ImportContext();
            importContext.setImportType(ImportType.GOODS_GALLERY);
            importContext.setImportPolicy(ImportPolicy.MULTI_PROCESSING);
            importContext.setBatchNo(AdminAuthorityHolder.get().getProvider().getProviderCode());
            ImportResult importResult = getImportService().importProcess(importContext);
            result.setResult(importResult);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("导入goodsGallery异常", e);
            result.setMsg("系统导入异常，请联系管理员");
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/forceStopGoodsGallery.json")
    public JsonResult forceStopGoodsGallery(ModelMap modelMap, int listId) {
        JsonResult result = new JsonResult();
        try {
            getImportService().stopImportGoodsGallery(listId);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("中止goodsGallery异常", e);
            result.setMsg("中止图片导入异常，请联系管理员");
            result.setSuccess(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/goodsBcs.htm")
    public JsonResult goodsBcs(ModelMap modelMap) {
        JsonResult result = new JsonResult();
        try {
            ImportContext importContext = new ImportContext();
            importContext.setImportType(ImportType.GOODS_BCS_IMAGE);
            importContext.setImportPolicy(ImportPolicy.SINGLE_PROCESSING);
            importContext.setBatchNo(AdminAuthorityHolder.get().getProvider().getProviderCode());
            ImportResult importResult = getImportService().importProcess(importContext);
            result.setResult(importResult);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("导入goodsBcs异常", e);
            result.setMsg("系统导入异常，请联系管理员");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/downloadTotal.htm")
    public void downloadShipping(HttpServletResponse response) {
        try {
            UploadUtil.downloadFromClassPath(response, "goodsImportTotal.xlsx", "商品录入资料总表");
        } catch (IOException e) {
            logger.error("download error: ", e);
            throw new BizException("下载失败");
        }
    }
    
    @RequestMapping("/downloadConsign.htm")
    public void downloadConsign(HttpServletResponse response, Integer importType) throws IOException {
    	try {
    		if (importType == 0) {
    			UploadUtil.downloadFromClassPath(response, "goodsConsign.xlsx", "普通商品虚库导入模板");
    		} else {
    			UploadUtil.downloadFromClassPath(response, "productCard.xlsx", "虚拟商品虚库导入模板");
    		}
        } catch (IOException e) {
            logger.error("download error: ", e);
            throw new BizException("下载失败");
        }
    }
    
    @ResponseBody
    @RequestMapping("/exportImageDir.htm")
    public String toExportImageDir(String batchNo, HttpServletResponse response) {
    	BatchImport batchImport = getImportService().getBatchImportByNo(batchNo);
    	if (batchImport == null) {
    		return "批次不存在！";
    	} else if (StringUtil.isEmpty(batchImport.getImpGoodsIds())) {
    		return "批次中无商品！";
    	} else {
    		String impGoodsIds = batchImport.getImpGoodsIds();
    		String[] goodsIdAry = impGoodsIds.split(",");
    		Set<Integer> goodsIds = new HashSet<Integer>();
    		for (String id : goodsIdAry) {
    			if (StringUtil.isNotBlank(id)) {
    				goodsIds.add(Integer.valueOf(id.trim()));
    			}
    		}
    		List<ProductGallery> gallerys = getImportService().queryGallerys(goodsIds);
    		if (gallerys == null || gallerys.isEmpty()) {
    			return "批次中无相关商品！";
    		}
    		
    		// 压缩文件夹
    		String rootDir = ConstantsHelper.getStaticRootPath() + "/exportImg";
    		String tmpDir = rootDir + "/" + batchNo;
    		File tmpFile = new File(tmpDir);
    		if (!tmpFile.exists()) {
    			tmpFile.mkdirs();
    		}
    		for (ProductGallery item : gallerys) {
    			new File(buildImagePath(tmpDir, item)).mkdir();
    		}
    		
    		String fileName = batchNo + ".zip";
    		File file = new File(rootDir + "/" + fileName);
    		try {
    			ZipUtil.compressedFile(tmpDir, rootDir);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "压缩文件出错，请联系管理员！";
			} finally {
				deleteDir(tmpFile);
			}
    		
    		logger.info("下载商品图片导入目录:{0}", file.getAbsoluteFile());
    		
    		// 输出
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = null;
            InputStream is = null;
            byte[] bs = new byte[1024];
            int readLen = 0;
            try {
                outputStream = new BufferedOutputStream(response.getOutputStream());
                is = new BufferedInputStream(new FileInputStream(file));
                while ((readLen = is.read(bs, 0, 1024)) != -1) {
                    outputStream.write(bs, 0, readLen);
                }
                outputStream.flush();
            } catch (Exception e) {
            	logger.error("下载商品图片导入目录出错，请联系管理员！", e);
                return "下载商品图片导入目录出错，请联系管理员！";
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                	logger.error(e.getMessage(), e);
                }
            }
            
            // 删除
            file.delete();
    	}
    	
    	return "success";
    }
    
    private String buildImagePath(String path, ProductGallery gallery) {
    	StringBuilder buff = new StringBuilder();
    	buff.append(path)
    		.append("/")
    		.append(gallery.getProviderGoods())
    		.append("-")
    		.append(gallery.getColorName())
    		.append("-")
    		.append(gallery.getGoodsSn());
    	return buff.toString();
    }
    
    private void deleteDir(File dir) {
    	File[] childs = dir.listFiles();
    	if (childs != null && childs.length > 0) {
    		for (File child : childs) {
    			if (child.isFile()) {
    				child.delete();
    			} else {
    				deleteDir(child);
    			}
    		}
    	}
		dir.delete();
    }
    
}
