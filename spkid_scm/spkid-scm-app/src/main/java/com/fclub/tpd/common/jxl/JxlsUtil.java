package com.fclub.tpd.common.jxl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fclub.common.lang.SystemException;
import com.fclub.common.log.LogUtil;

public class JxlsUtil {

	private static final LogUtil logger = LogUtil.getLogger(JxlsUtil.class);
	public static final String UTF_8 = "UTF-8";
	public static final String ISO8859_1 = "ISO8859-1";

	public static final String TEMPLATE_DIR = "jxlsTemplates/";

	/**
	 * 构造Jxls数据流
	 * 
	 * @param beans
	 * @param templateName
	 */
	public static InputStream initJxlsInputStream(Map<?, ?> beans,
			String templateName) throws Exception {

		XLSTransformer transformer = new XLSTransformer();
		InputStream isTemplate = JxlsUtil.class.getClassLoader()
				.getResourceAsStream(TEMPLATE_DIR + templateName);
		Workbook workbook = transformer.transformXLS(isTemplate, beans);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		byte[] data = bos.toByteArray();
		InputStream inputStream = new ByteArrayInputStream(data);
		return inputStream;
	}

	public static void exportExcel(HttpServletResponse response,
			String fileName, Map<String, Object> beans, String template) {
		InputStream isTemplate = null;
		OutputStream os = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			String agent = request.getHeader("User-Agent");
			if (agent == null) {
				agent = "MSIE";
			}
			agent = agent.toUpperCase();
			if (agent.indexOf("MSIE") != -1) {
				fileName = java.net.URLEncoder.encode(fileName, UTF_8);
			} else {
				fileName = new String(fileName.getBytes(UTF_8), ISO8859_1);
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ fileName + ".xls");

			XLSTransformer transformer = new XLSTransformer();
			isTemplate = JxlsUtil.class.getClassLoader().getResourceAsStream(
					TEMPLATE_DIR + template);
			Workbook workbook = transformer.transformXLS(isTemplate, beans);
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
		} catch (Exception e) {
			logger.error("exportExcel error: ", e);
			throw new RuntimeException("exportExcel error: ", e);
		} finally {
			try {
				if (isTemplate != null) {
					isTemplate.close();
				}
			} catch (IOException e) {
				throw new SystemException("exportExcel error: ", e);
			}
		}
	}

	public static void exportExcel(HttpServletResponse response, String path,
			String fileName, Map<String, Object> beans, String template) {
		InputStream isTemplate = null;
		OutputStream os = null;
		try {
			fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
			response.setCharacterEncoding("GBK");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ fileName + ".xls");

			XLSTransformer transformer = new XLSTransformer();
			isTemplate = JxlsUtil.class.getClassLoader().getResourceAsStream(
					TEMPLATE_DIR + template);
			Workbook workbook = transformer.transformXLS(isTemplate, beans);
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
		} catch (Exception e) {
			logger.error("exportExcel error: ", e);
			throw new RuntimeException("exportExcel error: ", e);
		} finally {
			try {
				if (isTemplate != null) {
					isTemplate.close();
				}
			} catch (IOException e) {
				throw new SystemException("exportExcel error: ", e);
			}
		}
	}

	public static void saveExcel(String filePath, Map<String, Object> beans,
			String template) {
		InputStream is = null;
		OutputStream os = null;
		try {
			XLSTransformer transformer = new XLSTransformer();
			is = JxlsUtil.class.getClassLoader().getResourceAsStream(
					TEMPLATE_DIR + template);
			Workbook workbook = transformer.transformXLS(is, beans);
			os = new FileOutputStream(new File(filePath));
			workbook.write(os);
			os.flush();
		} catch (Exception e) {
			logger.error("exportExcel error: ", e);
			throw new RuntimeException("exportExcel error: ", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {

					os.close();

				}
			} catch (IOException e) {
				throw new SystemException("exportExcel error: ", e);
			}
		}
	}
}
