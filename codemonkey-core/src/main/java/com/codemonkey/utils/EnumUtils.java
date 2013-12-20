package com.codemonkey.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 类描述：enum工具类
 */
public final class EnumUtils {

	private EnumUtils(){}
	
	public static JSONObject getEnmuDataByClazz(String className , String pacakageName , String method) {
		JSONObject ja = new JSONObject();
		
		if(StringUtils.isNotBlank(className)){
			
			String pName = null;
			
			if(StringUtils.isBlank(pacakageName)){
				pName = "com.codemonkey.domain.";
			}else{
				pName = pacakageName + '.';
			}
			String cName = pName + className;
			
			try {
				return getEnmuDataByClazz(Class.forName(cName) , method);
			} catch (ClassNotFoundException e) {
				return ja;
			}
		}
		return ja;
	}

	public static JSONObject getEnmuDataByClazz(Class<?> clazz , String method){
		JSONObject ja = new JSONObject();
		if(clazz != null && clazz.isEnum()){
			Object[] enumConstants = clazz.getEnumConstants();
			ja = buildJsonData(Arrays.asList(enumConstants));
		}
		return ja;
	}

	private static JSONObject buildJsonData(List<Object> coll) {
		JSONObject jo = new JSONObject();
		JSONArray data = new JSONArray();
		try {
			if (coll != null && !coll.isEmpty()) {
				for (Object obj : coll) {
					JSONObject jso = new JSONObject();
					jso.put("name" , ClassHelper.callGetter(obj, "name"));
					jso.put("text" , ClassHelper.callGetter(obj, "text"));
					data.put(jso);
				}
				jo.put("data", data);
				jo.put("totalCount", coll.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	public static JSONArray buildEnmuCoboxData(Collection<?> coll) {
		JSONArray array = new JSONArray();
		try {
			if (coll != null && !coll.isEmpty()) {
				for (Object obj : coll) {
					JSONArray jso = new JSONArray();
					jso.put(ClassHelper.callGetter(obj, "name"));
					array.put(jso);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

}
