package com.fclub.tpd.controller;

import static com.fclub.tpd.helper.ConstantsHelper.getImageDomain;
import static com.fclub.tpd.helper.ConstantsHelper.getPicRootPath;
import static com.fclub.tpd.helper.ConstantsHelper.getWorkOrderUploadDir;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fclub.common.dal.Page;
import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.tpd.biz.WorkOrderService;
import com.fclub.tpd.dataobject.WorkOrder;
import com.fclub.tpd.dto.WorkOrderGoods;
import com.fclub.web.util.UploadUtil;
import com.fclub.web.util.WebUtil;

@Controller
@RequestMapping("/workorder")
public class WorkOrderController extends BaseController {

    @Autowired
    private WorkOrderService workOrderService;
    
    /**
     * 处理请求中的日期类型，将请求中制定格式的日期字符串，格式化为日期类型。
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtil.getDateFormat(), true));
    }
    
    @RequestMapping("/list/main.htm")
    public String main() {
        return "tpd/workOrder";
    }
    
    @RequestMapping("/list/query.htm")
    public String query(ModelMap modelMap, Page<WorkOrder> page, WorkOrder workOrder) {
    	if (!isAdmin()) {
    		workOrder.setProviderId(getProviderId());
    	}
    	
        page = workOrderService.findPage(page, workOrder);
        for (WorkOrder item : page.getResult()) {
        	item.setWoFile(doBuildWorkOrderFile(item.getWoFile()));
        	item.setReplyFile(doBuildWorkOrderFile(item.getReplyFile()));
        }
        
        modelMap.put("page", page);
        return "tpd/workOrderList";
    }
    
    @RequestMapping("/addTo.htm")
    public String addTo() {
        return "tpd/workOrderAdd";
    }
    
    @RequestMapping("/add.htm")
    public String add(ModelMap modelMap, WorkOrder workOrder, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
    	Integer operaterId = getOperaterId();
    	workOrder.setCreateUser(operaterId);
    	
    	Integer providerId = getProviderId();
    	if (isAdmin()) {
    		providerId = Integer.valueOf(request.getParameter("providerId"));
    	}
		workOrder.setProviderId(providerId);
    	workOrder.setCreateTime(DateUtil.getCurrentTime());
		workOrder.setWoType(isAdmin() ? "01" : "02");
    	workOrder.setWoStatus(request.getParameter("type"));
    	workOrder.setWoNo(autoCreateWoNo(operaterId));
    	
    	if(file != null && !file.isEmpty()) {
        	String imagePath = getPicRootPath() + getWorkOrderUploadDir();        	
        	String fileName = file.getOriginalFilename();
        	fileName = fileName.substring(0,fileName.lastIndexOf(".")) + "_" + System.currentTimeMillis();
        	
        	//工单图片附件
        	String woFile = null;
        	try {
        		woFile = UploadUtil.uploadImage(file, imagePath, fileName);
			} catch (Exception e) {
				throw new BizException("请上传图片格式的文件！");
			}	
        	workOrder.setWoFile(woFile);
    	}
    	
        workOrderService.save(workOrder);
        
        modelMap.addAttribute("currentUrl", "/workorder/addTo.htm");
        modelMap.addAttribute("backUrl", "/workorder/list/main.htm");
        return "commons/success2";
    }
    
    @RequestMapping("/list/show.htm")
    public String show(ModelMap modelMap, String id) {
        WorkOrder workOrder = workOrderService.get(Integer.valueOf(id));
        workOrder.setWoFile(doBuildWorkOrderFile(workOrder.getWoFile()));
        workOrder.setReplyFile(doBuildWorkOrderFile(workOrder.getReplyFile()));
        modelMap.put("readOnly", 1);
        modelMap.put("workOrder", workOrder);
        return "tpd/workOrderShow";
    }
    
    @RequestMapping("/editTo.htm")
    public String editTo(ModelMap modelMap, String id, HttpServletResponse response) {
        WorkOrder workOrder = workOrderService.get(Integer.valueOf(id));
        if (workOrder == null) {
        	WebUtil.send404Error(response, "记录不存在！");
        	return null;
        } else {
        	workOrder.setWoFile(doBuildWorkOrderFile(workOrder.getWoFile()));
        	modelMap.put("readOnly", 0);
        	modelMap.put("workOrder", workOrder);
        	return "tpd/workOrderEdit";
        }
    }

    @RequestMapping("/edit.htm")
    public String edit(ModelMap modelMap, WorkOrder workOrder, HttpServletRequest request, @RequestParam(value="workImgFile",required=false)MultipartFile workImgFile) throws Exception {
        
    	workOrder.setUpdateUser(getOperaterId());
        workOrder.setUpdateTime(DateUtil.getCurrentTime());
        workOrder.setWoStatus(request.getParameter("type"));
        if(workImgFile != null && !workImgFile.isEmpty()){
        	String fileName = workImgFile.getOriginalFilename();
        	String imagePath = getPicRootPath() + getWorkOrderUploadDir();
        
        	//工单图片附件
        		String goodsModelimg = null;
        	try {
        		goodsModelimg = UploadUtil.uploadImage(workImgFile, imagePath, fileName.substring(0, fileName.lastIndexOf(".")));
			} catch (Exception e) {
				throw new BizException("请上传图片格式文件！");
			}
        	workOrder.setWoFile(goodsModelimg);
        }
        
    	workOrderService.update(workOrder);
        modelMap.addAttribute("backUrl", "/workorder/list/main.htm");
        return "commons/success2";
    }

    @RequestMapping("/replyTo.htm")
    public String replyTo(ModelMap modelMap, String id, HttpServletResponse response) {
    	WorkOrder workOrder = workOrderService.get(Integer.valueOf(id));
    	if(workOrder == null){
    		WebUtil.send404Error(response, "记录不存在！");
    		return null;
    	}else{
    		modelMap.put("readOnly", 0);
            modelMap.put("workOrder", workOrder);
    	}
        return "tpd/workOrderReply";
    }

    @RequestMapping("/reply.htm")
    public String reply(ModelMap modelMap, WorkOrder workOrder, HttpServletRequest request, @RequestParam(value="file", required=false) MultipartFile replyFile) throws Exception {
    	workOrder.setReplyUser(getOperaterId());
    	workOrder.setReplyTime(DateUtil.getCurrentTime());
    	workOrder.setWoStatus(request.getParameter("type"));//更改工单状态

    	if (replyFile != null && !replyFile.isEmpty()) {
    		String fileName = replyFile.getOriginalFilename();
    		String imagePath = getPicRootPath() + getWorkOrderUploadDir();
    		//工单图片附件
    		String goodsModelimg = null;
    		try {
    			goodsModelimg = UploadUtil.uploadImage(replyFile, imagePath, fileName.substring(0,fileName.lastIndexOf(".")));
			} catch (Exception e) {
				throw new BizException("请上传图片格式文件！");
			}
    			workOrder.setReplyFile(goodsModelimg);
        }
        workOrderService.replyUpdate(workOrder);
        modelMap.addAttribute("backUrl", "/workorder/list/main.htm");
        return "commons/success2";
    }
    
    
    @RequestMapping(value = "/delete.htm")
    @ResponseBody
    public String delete(Integer id) {
        try {
            workOrderService.delete(id);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
    @RequestMapping(value = "/deleteImage.htm")
    @ResponseBody
    public String deleteImage(Integer woId, Integer type) {
    	try {
            workOrderService.deleteImage(woId,type);
        } catch (BizException e) {
            return e.getMessage();
        }
        return "success";
    }
    
    @ResponseBody
    @RequestMapping("/list/checkOrderSnExist.htm")
    public Integer checkOrderSnExist(String orderSn) {
        return workOrderService.checkOrderSnExist(orderSn);
    }
    
    @RequestMapping(value = "/list/getOrderGoods/{orderSn}.htm", method = RequestMethod.GET)
    public String getOrderGoods(ModelMap modelMap, @PathVariable String orderSn){
        List<WorkOrderGoods> list = workOrderService.getOrderGoods(orderSn);
        modelMap.put("list", list);
        return "tpd/workOrderGoods";
    }
    
    @RequestMapping("/check.htm")
    @ResponseBody
    public String check(String id) throws Exception {
    	WorkOrder workOrder = workOrderService.get(Integer.valueOf(id));
    	if (workOrder == null) {
    		return "工单不存在！";
    	}
        workOrder.setUpdateUser(getOperaterId());
        workOrder.setUpdateTime(DateUtil.getCurrentTime());
        workOrder.setWoStatus("1");
    	workOrderService.update(workOrder);
        return "success";
    }
    
    @RequestMapping("/checkCancel.htm")
    @ResponseBody
    public String checkCancel(String id) throws Exception {
    	WorkOrder workOrder = workOrderService.get(Integer.valueOf(id));
    	if (workOrder == null) {
    		return "工单不存在！";
    	}
        workOrder.setUpdateUser(getOperaterId());
        workOrder.setUpdateTime(DateUtil.getCurrentTime());
        workOrder.setWoStatus("4");
    	workOrderService.update(workOrder);
        return "success";
    }
    
    private String autoCreateWoNo(Integer providerId){
    	StringBuilder buff = new StringBuilder();
    	buff.append(isAdmin() ? "MGD-" : "PGD-");
       	buff.append(providerId);
       	buff.append("-");
    	buff.append(DateUtil.getDateFormat("yyyyMMddHHmmss").format(DateUtil.getCurrentTime()));
    	
    	return buff.toString();
    }
    
    private String doBuildWorkOrderFile(String woFile) {
    	if (StringUtil.isNotEmpty(woFile)) {
    		return getImageDomain() + getWorkOrderUploadDir() + "/" + woFile;
    	}
    	return woFile;
    }

}