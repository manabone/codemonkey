package com.codemonkey.ibatis.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.utils.ExtConstant;

public final class IbatisUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(JSONObject queryAndSort) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(queryAndSort == null){
			return map;
		} 
		
		JSONObject query = queryAndSort.optJSONObject(ExtConstant.QUERY);
		
		if(query != null){
			Iterator<String> it = query.keys();
			while(it.hasNext()){
				String key = it.next();
				map.put(ExtConstant.QUERY + '_' + key , query.getString(key));
			}
		}
		
		JSONArray sort = queryAndSort.optJSONArray(ExtConstant.SORT);
		
		if(sort != null){
			for(int i = 0 ; i < sort.length() ; i++){
				JSONObject jo = sort.getJSONObject(i);
				map.put(ExtConstant.SORT + '_' + jo.getString("property") , jo.getString("direction"));
			}
		}
		
		return map;
	}

	
}
