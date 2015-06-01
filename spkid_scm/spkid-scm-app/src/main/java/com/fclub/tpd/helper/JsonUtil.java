/**
 * 
 */
package com.fclub.tpd.helper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.map.ObjectMapper;

import com.fclub.common.lang.BizException;
import com.fclub.common.log.LogUtil;

public class JsonUtil {

	private static final LogUtil logger = LogUtil.getLogger(JsonUtil.class);
	
	public static final String objectToJson(Object o){
		ObjectMapper om = new ObjectMapper();
    	Writer w = new StringWriter();
    	String jsonDesc = null;
    	try {
			om.writeValue(w, o);
	    	jsonDesc = w.toString();
	    	w.close();
		} catch (IOException e) {
			logger.error("对象转换成json出错: " + e.getMessage());
			throw new BizException("对象转换成json出错: " + e.getMessage());
		}
		return jsonDesc;
	}
	
	public static <T> T json2Object(String json, Class<T> clazz) {
        T instance = null;
        try {
            instance = new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new BizException("json转换成Java对象出错: " + e.getMessage());
        }
        
        return instance;
    }
	
}
