/**
 * f-club.cn
 * Copyright (c) 2009-2013 All Rights Reserved.
 */
package com.fclub.tpd.helper;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.fclub.tpd.common.SystemConstant;

/**
 * 
 * @author likaiping
 * @version $Id: ConstantsHelper.java, v 0.1 Jun 28, 2013 10:25:13 AM likaiping Exp $
 */
public class ConstantsHelper {

    //    private static Logger logger = LoggerFactory.getLogger(ConstantsHelper.class);

    public static String getStaticRootPath() {
        return getPram(SystemConstant.STATIC_ROOT_DIR);
    }

    public static String getFtpRootPath() {
        return getPram(SystemConstant.FTP_ROOT_DIR);
    }

    public static String getPram(String paramKey) {
        return System.getProperty(paramKey);
    }

    public static String getPicRootPath() {
        return getPram(SystemConstant.PIC_ROOT_DIR);
    }

    public static String getWorkOrderUploadDir() {
        return getPram(SystemConstant.UPLOAD_WORK_ORDER);
    }
    
    public static String getProviderLogoUploadDir() {
        return getPram(SystemConstant.UPLOAD_PROVIDER_LOGO);
    }
    
    public static String getBrandLogoUploadDir() {
        return getPram(SystemConstant.UPLOAD_BRAND_LOGO);
    }
    
    public static String getTuanUploadDir() {
        return getPram(SystemConstant.UPLOAD_TUAN);
    }

    public static String getImageDomain() {
        return getPram(SystemConstant.IMAGE_DOMAIN);
    }

    public static String getFrontDomain() {
        return getPram(SystemConstant.FRONT_DOMAIN);
    }
    
    public static String getBackDomain() {
        return getPram(SystemConstant.BACK_DOMAIN);
    }
    
    public static String getImageInPath() {
        return getPram(SystemConstant.BATCH_IMAGE_IN);
    }

    public static String getImageInPath(String channel) {
        return getFtpRootPath() + File.separator + channel + getPram(SystemConstant.BATCH_IMAGE_IN)
               + File.separator;
    }
    
    // --- FCK Editor
    /**
     * @return FCKeditor相关图片/文件的上传目录[/data/upload]
     */
    public static String getFckeditorUploadDir() {
        return getPram(SystemConstant.UPLOAD_FCKEDITOR);
    }

    /**
     * @return FCKeditor相关图片/文件的上传绝对路径
     */
    public static String getFckeditorUploadPath() {
        return getPicRootPath() + getFckeditorUploadDir();
    }

    /**
     * @return FCKeditor相关图片/文件的访问路径
     */
    public static String getFckeditorViewPath() {
        return getImageDomain() + getFckeditorUploadDir();
    }

    // -- img ------------------------------------------------------
    public static String getImageServerIp() {
        String ipAddress = "";
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ipAddress = getPram(SystemConstant.REMOTE_IMAGESERVICE_IP);
        }
        return ipAddress;
    }
    
    /**
     * 商品批量导入商品图地址(第三方平台)
     * @return
     */
    public static String getImageInPathTpd(String channel) {
        return getFtpRootPath() + File.separator + channel
               + getPram(SystemConstant.BATCH_IMAGE_IN_TPD) + File.separator;
    }
    
    // ---- 现金券
    /**
     * @return 现金券活动LOGO图片上传的目录[/data/voucher_logo]
     */
    public static String getVoucherCampaignLogoPath() {
        //return getPram(SystemConstant.UPLOAD_IMAGE_VOUCHER_CAMPAIGN);
    	return "";
    }

    /**
     * @return 现金券活动LOGO图片上传的绝对路径
     */
    public static String getVoucherCampaignLogoUploadPath() {
        return getPicRootPath() + getVoucherCampaignLogoPath();
    }
}
