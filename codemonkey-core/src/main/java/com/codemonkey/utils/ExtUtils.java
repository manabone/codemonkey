package com.codemonkey.utils;

import org.json.JSONObject;

import com.codemonkey.domain.IPopupModule;
/**
 * 类描述：EXT工具类
 */
public class ExtUtils {

	/**
	 * 方法描述：生成linked column对应的数据
	 * 
	 * @param: entity 实体
	 * @return: json字符串
	 * @author: zhaobin
	 */
	public static String linkedColumnValue(IPopupModule entity){
		JSONObject jo = new JSONObject();
		if(entity != null){
			jo.put("id", entity.getId());
			jo.put("linkText", entity.getLinkText());
			jo.put("moduleId", entity.getModuleId());
		}else{
			jo.put("id", "");
			jo.put("linkText", "");
			jo.put("moduleId", "");
		}
		return jo.toString();
	}
	
}
