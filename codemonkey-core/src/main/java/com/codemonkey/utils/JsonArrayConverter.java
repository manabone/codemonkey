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
			JSONArray permissions = params.getJSONArray(key);
			for(int i = 0 ; i < permissions.length() ; i++){
				JSONObject jo = permissions.getJSONObject(i);
				T t = (T) ClassHelper.convert(jo.getString(ExtConstant.ID), clazz, ccService);
				list.add(t);
			}
		}
		return list;
		
	}
}
