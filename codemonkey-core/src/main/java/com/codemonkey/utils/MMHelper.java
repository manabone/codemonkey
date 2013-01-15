package com.codemonkey.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

public final class MMHelper {

	private MMHelper(){} 
	
	public static JSONArray getModelFields(Class<?> type) {
		JSONArray feildsArray = new JSONArray();
		
		List<String> fields = ClassHelper.getAllFieldNames(type);
		if(CollectionUtils.isNotEmpty(fields)){
			for(String f : fields){
				feildsArray.put(f);
			}
		}
		return feildsArray;
	}

	public static String getModelName(Class<?> type) {
		return StringUtils.uncapitalize(type.getSimpleName());
	}
	
	public static JSONArray getEnmuDataByClazz(Class<?> clazz){
		JSONArray ja = new JSONArray();
		if(clazz.isEnum()){
			Object[] enumConstants = clazz.getEnumConstants();
			ja = buildEnmuCoboxData(Arrays.asList(enumConstants));
			
		}
		return ja;
	}
	
	public static JSONArray getEnmuDataByClazz(Class<?> clazz , String methodName){
		JSONArray ja = new JSONArray();
		try {
			if(StringUtils.isNotBlank(methodName)){
				return getEnmuDataByClazz(clazz);
			}
			if(clazz.isEnum()){
				Method m = clazz.getMethod(methodName);
				Object d = m.invoke(clazz);
				if(d instanceof Collection) {
					Collection<?> result = (Collection<?>) m.invoke(clazz);
					ja = buildEnmuCoboxData(result);
				}	
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return ja;
	}
	
	public static JSONArray buildEnmuCoboxData(Collection<?> coll) {
		JSONArray array = new JSONArray();
		try {
			if (!CollectionUtils.isEmpty(coll)) {
				for (Object obj : coll) {
					JSONArray jso = new JSONArray();
					jso.put(ClassHelper.callGetter(obj, "name"));
					jso.put(ClassHelper.callGetter(obj, "text"));
					array.put(jso);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
}
