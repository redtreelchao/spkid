/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.batch.importing.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.FileUtil;
import com.fclub.common.lang.utils.RandomUtils;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.common.spring.SpringContextHolder;
import com.fclub.tpd.batch.importing.dto.ImportContext;
import com.fclub.tpd.batch.importing.dto.ImportResult;
import com.fclub.tpd.batch.importing.enums.ImportStatus;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.helper.ConstantsHelper;

/**
 * 导入任务帮助类
 * @author likaiping
 * @version $Id: ImportTaskHelper.java, v 0.1 Oct 25, 2012 5:40:20 PM likaiping Exp $
 */
public abstract class ImportTaskHelper {
    /** 默认导入日志 */
    public static final String IMPORT_DETAULT_LOGGER = "IMPORT_DETAULT_LOGGER";

    static LogUtil             logger                = LogUtil.getLogger(IMPORT_DETAULT_LOGGER);

    /**
     * 生成导入结果
     * @param result 结果对象-可以为空
     * @param importStatus -导入状态
     * @param msg 消息
     * @return
     */
    public static ImportResult genResult(ImportResult result, ImportStatus importStatus, String msg) {
        if (result == null) {
            result = new ImportResult();
        }
        result.setMsg(msg);
        result.setImportStatus(importStatus);
        return result;
    }

    /**
     * 生成导入错误结果-设置状态为ERROR
     * @param result  结果对象-可以为空
     * @param msg 消息
     * @return
     */
    public static ImportResult genResultError(ImportResult result, String msg) {
        return genResult(result, ImportStatus.ERROR, msg);
    }

    /**
     * 生成导入错误结果-设置状态为ERROR
     * @param result  结果对象-可以为空
     * @param msg 消息
     * @return
     */
    public static ImportResult genResultError(String msg) {
        return genResult(null, ImportStatus.ERROR, msg);
    }

    /**
     * 获取备份文件地址
     * @param importContext
     * @return
     */
    public static File getHistoricalFile(ImportContext importContext) {
        File filePath = new File(ConstantsHelper.getStaticRootPath()
                                 + ConstantsHelper.getPram(SystemConstant.BATCH_HISTORY)
                                 + File.separator + importContext.getBatchNo());
        return new File(filePath, importContext.getHistoricalFileName());
    }

    /**
     * 存储上传文件至历史记录
     * @param importContext
     * @throws IOException 
     * @throws IllegalStateException 
     */
    public static String storageOfHistorical(ImportContext importContext)
                                                                         throws IllegalStateException,
                                                                         IOException {

        MultipartFile file = importContext.getFile();
        String suffix = file.getOriginalFilename().substring(
            file.getOriginalFilename().lastIndexOf("."));
        String fileName = null;
        File filePath = new File(ConstantsHelper.getStaticRootPath()
                                 + ConstantsHelper.getPram(SystemConstant.BATCH_HISTORY)
                                 + File.separator + importContext.getBatchNo());
        if (!filePath.exists())
            filePath.mkdirs();
        File destFile = null;
        for (int i = 0; i < 100; i++) {
            fileName = genHistoryFileName(importContext) + suffix;
            destFile = new File(filePath, fileName);
            if (destFile.exists()) {
                if (i > 50) {
                    //重试50次失败，
                    throw new BizException("系统重试50次,存在重复文件");
                } else {
                    continue;
                }
            } else {
                break;
            }
        }

        if (destFile != null)
            file.transferTo(destFile);
        return fileName;
    }

    private static String genHistoryFileName(ImportContext importContext) {
        StringBuffer buffer = new StringBuffer("I_");
        buffer.append(importContext.getImportType().getCode()).append("_");
        buffer.append(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        buffer.append(RandomUtils.generateString(2));
        return buffer.toString();
    }

    /**
     * 生成导入批次号
     * @return
     */
    public static String genBatchNo() {
        StringBuffer buffer = new StringBuffer("IMP_");
        buffer.append(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        buffer.append(RandomUtils.generateString(2));
        String batchNo = buffer.toString();
        return batchNo;
    }

    /**
     * 使用模板生成html文件
     * 
     * @param content
     *            模板参数的内容
     * @param errors
     * @param rootDir
     *            存放数据文件的根目录
     * @param templatePath
     *            模板文件的路径
     * @param htmlFileName
     *            生成的html文件的名字
     * @throws Exception
     * @throws ParseErrorException
     * @throws ResourceNotFoundException
     */
    public static boolean generateHtml(ImportResult result, List<? extends Object> content,
                                       String tempalteName, String htmlFilePath, String fileName) {
        PrintWriter pw = null;
        try {
            VelocityConfigurer velocityConfigurer = SpringContextHolder.getBean("velocityConfigurer");
            VelocityEngine engine = velocityConfigurer.getVelocityEngine();
            Template template = engine.getTemplate(tempalteName);
            Context context = new VelocityContext();
            context.put("result", result);
            context.put("content", content);
            context.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            File htmlPath = new File(htmlFilePath);
            if (!htmlPath.exists())
                htmlPath.mkdirs();

            File htmlFile = new File(htmlPath, fileName);
            pw = new PrintWriter(htmlFile, "GBK");
            template.merge(context, pw);

            pw.flush();
            return true;
        } catch (Exception e) {
            logger.error("根据模板生成HTML文件失败;tempalteName:{0};htmlFilePath:{1};fileName:{2}", e,
                tempalteName, htmlFilePath, fileName);
            return false;
        } finally {
            if (pw != null)
                pw.close();
        }

    }

    /**
     * 检测文件是否是utf-8编码
     * 
     * @param file
     * @return
     */
    public static boolean isUTF8(InputStream inputStream) {
        // 读取第一行
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine().trim();
            br.close();
            if (line == null)
                return false;

            for (int i = 0; i < line.length(); i++) {
                int codePoint = line.codePointAt(i);

                if (codePoint >= 2048 && codePoint <= 65535) {
                    String s = line.substring(i, i + 1);
                    byte[] b = s.getBytes();
                    String s1 = Integer.toBinaryString(b[0]);
                    if (!s1.substring(24, 28).equals("1110"))
                        return false;
                    String s2 = Integer.toBinaryString(b[1]);
                    if (!s2.substring(24, 26).equals("10"))
                        return false;
                    String s3 = Integer.toBinaryString(b[2]);
                    if (!s3.substring(24, 26).equals("10"))
                        return false;
                }
            }
        } catch (IOException e) {
            logger.error("文件读取错误", e);
            return false;
        }
        return true;
    }

    /**
     * 检测文件是否是utf-8编码
     */
    public static boolean isUtf8(InputStream in) {
        byte[] b = new byte[3];
        try {
            in.read(b);
        } catch (IOException e) {
            logger.error("文件读取错误: ", e);
            //        } finally {
            //            try {
            //                in.close();
            //            } catch (IOException e) {
            //                logger.error("文件读取错误: ", e);
            //            }
        }
        if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
            return true;
        }
        return true;
    }

    /**
     * 检测文件内容是否是UTF-8
     * @param file
     * @return
     */
    public static boolean isUTF8(File file){
        try {
            String encode = FileUtil.getFileEncode(file.getPath());
            if (StringUtil.equalsIgnoreCase(encode, "UTF-8")) {
                return true;
            }
        } catch (IOException e) {
            logger.error("文件读取错误: ", e);
        }
        return false;
     }

}
