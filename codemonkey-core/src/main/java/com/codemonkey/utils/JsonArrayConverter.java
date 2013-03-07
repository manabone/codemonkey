package com.codemonkey.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.web.converter.CustomConversionService;

public class JsonArrayConverter<T> {

	@SuppressWarnings("unchecked")
	public List<T> convert(JSONObject params , String key , Class<?> clazz , CustomConversionService ccService){
		List<T> list = new ArrayList<T>();
		if(params.has(key) && StringUtils.isNotBlank(params.getString(key))){
			JSONArray p = params.getJSONArray(key);
			for(int i = 0 ; i < p.length() ; i++){
				JSONObject jo = p.getJSONObject(i);
				T t = (T) ClassHelper.convert(jo.getString(ExtConstant.ID), clazz, ccService);
				
				if(t == null){
					t = createNewInstance(clazz);
				}
				ClassHelper.bulid(jo, t , ccService);
				list.add(t);
			}
		}
		return list;
		
	}

	@SuppressWarnings("unchecked")
	private T createNewInstance(Class<?> clazz) {
		try {
			T t = (T) clazz.newInstance();
			return t;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
