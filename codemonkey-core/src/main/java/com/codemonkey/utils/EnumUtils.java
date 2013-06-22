package com.codemonkey.utils;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

public final class EnumUtils {

	private EnumUtils(){}
	
	public static JSONArray getEnmuDataByClazz(String className) {
		JSONArray ja = new JSONArray();
		if(StringUtils.isNotBlank(className)){
			try {
				return getEnmuDataByClazz(Class.forName(className));
			} catch (ClassNotFoundException e) {
				return ja;
			}
		}
		return ja;
	}

	public static JSONArray getEnmuDataByClazz(Class<?> clazz){
		JSONArray ja = new JSONArray();
		if(clazz.isEnum()){
			Object[] enumConstants = clazz.getEnumConstants();
			ja = buildEnmuCoboxData(Arrays.asList(enumConstants));
		}
		return ja;
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
