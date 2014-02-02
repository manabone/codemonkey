package com.codemonkey.web.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.IEntity;
import com.codemonkey.service.GenericService;
import com.codemonkey.utils.EnumUtils;

@Controller
@RequestMapping("/ext/enum/**")
public class EnumController extends AbsExtController<IEntity> {
	
	@RequestMapping("read")
	@ResponseBody
	public String read(@RequestParam JSONObject queryInfo) {
		
		String className = queryInfo.optString("className");
		String packageName = queryInfo.optString("packageName");
		String method = queryInfo.optString("method");
		
		if(queryInfo.has("allowEmptyOption") && queryInfo.getBoolean("allowEmptyOption")){
			JSONObject jo = EnumUtils.getEnmuDataByClazz(className , packageName , method);
			JSONObject emptyOption = new JSONObject();
			emptyOption.put("name" , "");
			emptyOption.put("text" , "全部");
			
			JSONArray ja = jo.getJSONArray("data");
			JSONArray data = new JSONArray();
			data.put(emptyOption);
			for(int i = 0; i < ja.length(); i++){
				data.put(ja.get(i));
			}
			
			JSONObject njo = new JSONObject();
			njo.put("data", data);
			njo.put("totalCount", Integer.valueOf(String.valueOf(jo.get("totalCount"))) + 1);
			return njo.toString();
		}
		
		return EnumUtils.getEnmuDataByClazz(className , packageName , method).toString();
	}
	
	@Override
	protected GenericService<IEntity> service() {
		return null;
	}

}
