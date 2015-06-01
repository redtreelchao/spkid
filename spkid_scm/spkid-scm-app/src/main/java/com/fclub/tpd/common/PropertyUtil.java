package com.fclub.tpd.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.fclub.common.log.LogUtil;
import com.fclub.tpd.common.jxl.JxlsUtil;
import com.fclub.tpd.helper.ConstantsHelper;

/**
 * 
 * @author "baolm"
 * @version $Id: PropertyUtil.java, v 0.1 2013-5-29 上午11:07:48 "baolm" Exp $
 */
public class PropertyUtil {

    private static final LogUtil logger = LogUtil.getLogger(JxlsUtil.class);

    static {
        String filePath = ConstantsHelper.getStaticRootPath()
                          + System.getProperty(SystemConstant.BATCH_PROGRESS);
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            logger.info("create directory: {0}", file.getParentFile().getAbsolutePath());
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                logger.info("create file: {0}", file.getName());
            } catch (IOException e) {
                logger.error("cannot create upload file: " + file.getAbsolutePath(), e);
            }
        }
    }

    private static Properties getProperty(String propFile) throws IOException {

        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(propFile);
            //            in = PropertyUtil.class.getResourceAsStream(propFile);
            prop.load(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return prop;
    }

    private static void setProperty(String propFile, String key, String value, String comment)
                                                                                              throws Exception {

        Properties prop = getProperty(propFile);
        //        URL resourceUrl = PropertyUtil.class.getResource(propFile);
        //        System.out.println(resourceUrl.getFile());
        //        File file = new File(resourceUrl.toURI());
        File file = new File(propFile);
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            //            out = new FileOutputStream(propFile);
            prop.setProperty(key, value);
            prop.store(out, comment);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static void removeProperty(String propFile, String key) throws Exception {

        Properties prop = getProperty(propFile);
        //        URL resourceUrl = PropertyUtil.class.getResource(propFile);
        //        System.out.println(resourceUrl.getFile());
        //        File file = new File(resourceUrl.toURI());
        File file = new File(propFile);
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            //            out = new FileOutputStream(propFile);
            prop.remove(key);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static Object getPropertyValue(String propFile, String propKey) {
        try {
            return getProperty(propFile).get(propKey);
        } catch (IOException e) {
            logger.error(
                "get property [file: " + propFile + ",key: " + propKey + "] value error: ", e);
            throw new RuntimeException("get property [file: " + propFile + ",key: " + propKey
                                       + "] value error: ", e);
        }
    }

    public static void setPropertyValue(String propFile, String key, String value, String comment) {
        try {
            setProperty(propFile, key, value, comment);
        } catch (Exception e) {
            logger.error("set property [file: " + propFile + ",key: " + key + "] value error: ", e);
            throw new RuntimeException("set property [file: " + propFile + ",key: " + key
                                       + "] value error: ", e);
        }
    }

    public static void removePropertyValue(String propFile, String key) {
        try {
            removeProperty(propFile, key);
        } catch (Exception e) {
            logger.error("set property [file: " + propFile + ",key: " + key + "] value error: ", e);
            throw new RuntimeException("set property [file: " + propFile + ",key: " + key
                                       + "] value error: ", e);
        }
    }

    /** 用于批量导入进度条存取 */
    public static Object getProgressValue(String propKey) {
        return getPropertyValue(
            ConstantsHelper.getStaticRootPath() + System.getProperty(SystemConstant.BATCH_PROGRESS),
            propKey);
    }

    public static void setProgressValue(String key, String value) {
        setPropertyValue(
            ConstantsHelper.getStaticRootPath() + System.getProperty(SystemConstant.BATCH_PROGRESS),
            key, value, null);
    }

    public static void removeProgressValue(String key) {
        removePropertyValue(
            ConstantsHelper.getStaticRootPath() + System.getProperty(SystemConstant.BATCH_PROGRESS),
            key);
    }
}
