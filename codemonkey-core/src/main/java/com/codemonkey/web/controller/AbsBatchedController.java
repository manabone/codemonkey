package com.codemonkey.web.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ExtConstant;

@Controller
public abstract class AbsBatchedController<T extends IEntity> extends AbsListExtController<T>{

	
	//----------------------
    // create
    //----------------------
	@RequestMapping("create")
    @ResponseBody
	public String create(@RequestBody String body){
		return handleUpdate(body);
	}
	
	//----------------------
    // update
    //----------------------
	@RequestMapping("update")
    @ResponseBody
	public String update(@RequestBody String body){
		return handleUpdate(body);
	}
	
	@Override
	public String handleUpdate(String body) {
		try{
			JSONObject params = new JSONObject(body);
			
			service().doBatchedSave(params, getCcService());
			
			JSONObject result = new JSONObject();
			result.put(ExtConstant.SUCCESS, true);
			return result.toString();
			
		}catch(Exception e){
			JSONObject result = new JSONObject();
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
