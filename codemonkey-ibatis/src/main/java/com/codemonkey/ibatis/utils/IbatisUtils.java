package com.codemonkey.ibatis.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.util.HtmlUtils;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;

/**
 * 类描述：Ibatis工具类
 * 
 */
public final class IbatisUtils {

	
	public static final String ORDER_BY_KEY = "ORDER_BY_KEY";
	/**
	 * 方法描述：json对象转换成Map查询对象
	 * 
	 * @param: JSONObject json查询对象
	 * @return: map查询对象
	 * @author: wy
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(JSONObject queryAndSort) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (queryAndSort == null) {
			return map;
		}

		JSONObject query = queryAndSort.optJSONObject(ExtConstant.QUERY);

		if (query != null) {
			Iterator<String> it = query.keys();
			while (it.hasNext()) {
				String key = it.next();
				if("null".equals(query.getString(key))){
					map.put(ExtConstant.QUERY + '_' + key, "");
				}else{
					Pattern p = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}:\\d{2})"); 
					// 创建 Matcher 对象 
					Matcher m = p.matcher(query.getString(key)); 
					if(m.matches()){
						// 替换 
						map.put(ExtConstant.QUERY + '_' + key, m.replaceAll("$1 $2"));
					}else{
						map.put(ExtConstant.QUERY + '_' + key, query.get(key));
					}
				}
			}
		}

		JSONArray sort = queryAndSort.optJSONArray(ExtConstant.SORT);

		StringBuffer buffer = new StringBuffer();
		if (sort != null) {
			for (int i = 0; i < sort.length() ; i++) {
				JSONObject jo = sort.getJSONObject(i);
				
				buffer.append(SysUtils.propToColumn(jo.getString("property")));
				buffer.append(" ");
				buffer.append(jo.getString("direction"));
				
				if(i < sort.length() - 1){
					buffer.append(",");
				}
			}
			map.put(ORDER_BY_KEY , buffer.toString());
		}
		
		return map;
	}

	/**
	 * 方法描述：List<Map>转换成json对象
	 * 
	 * @param: list ibais查询结果
	 * @return: json对象
	 * @author: wy
	 */
	public static String buildJson(List<Map<String, Object>> list) {
		JSONObject jo = buildResult(list);
		return jo.toString();
	}

	/**
	 * 方法描述：List<Map>转换成json对象并记录数
	 * 
	 * @param: list ibais查询结果
	 * @param: total 记录数
	 * @return: json对象
	 * @author: wy
	 */
	public static String buildJson(List<Map<String, Object>> list, long total) {
		JSONObject jo = buildResult(list);
		jo.put(ExtConstant.TOTAL_COUNT, total);
		return jo.toString();
	}

	/**
	 * 方法描述：List<Map>转换成json对象
	 * 
	 * @param: list ibais查询结果
	 * @return: json对象
	 * @author: wy
	 */
	public static JSONObject buildResult(List<Map<String, Object>> list) {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		if (CollectionUtils.isEmpty(list)) {
			result.put(ExtConstant.SUCCESS, true);
			result.put(ExtConstant.DATA, data);
			return result;
		}

		for (Map<String, Object> m : list) {
			Set<Entry<String, Object>> set = m.entrySet();
			JSONObject jo = new JSONObject();
			Iterator<Entry<String, Object>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, Object> e = it.next();
				jo.put(SysUtils.columnToProp(e.getKey().toString()),
						HtmlUtils.htmlEscape(e.getValue() != null ? e.getValue().toString() : ""));
			}
			data.put(jo);
		}
		result.put(ExtConstant.SUCCESS, true);
		result.put(ExtConstant.DATA, data);
		return result;
	}
}
