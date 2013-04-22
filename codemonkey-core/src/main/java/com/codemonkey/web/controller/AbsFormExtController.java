package com.codemonkey.web.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.IEntity;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public abstract class AbsFormExtController<T extends IEntity> extends AbsExtController<T>{

	// ----------------------
	// edit
	// ----------------------
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(ExtConstant.ID) Long id, ModelMap modelMap , HttpSession session) {
		modelMap.addAttribute(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
		modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData(session).put(ExtConstant.ID, id));
		return ExtConstant.EE_INDEX;
	}
	
	//----------------------
    // create
    //----------------------
    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody String body) {
    	return update(body);
    }
    
    //----------------------
    // read
    //----------------------
    @RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam Long id) {
    	
    	T t = service().get(id);
    	
    	JSONObject result = new JSONObject();
    	if(t != null){
    		result.put(ExtConstant.DATA, buildJson(t));
			result.put(ExtConstant.SUCCESS, true);
    	}
    	return result.toString();
    }
    
    protected JSONObject buildJson(T t){
    	return t.detailJson();
    }

	//----------------------
    // update
    //----------------------
	@RequestMapping("update")
    @ResponseBody
	public String update(@RequestBody String body){
		
		JSONObject params = parseJson(body);
		
		T t = service().doSave(params , getCcService());
		
		return result(t);
	}
	
	public String result(T t) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.DATA, t.detailJson());
		result.put(ExtConstant.SUCCESS, true);
		return result.toString();
	}
	
	protected JSONObject parseJson(String body){
		JSONObject params = new JSONObject();
		try {
			params = new JSONObject(body);
		} catch (ParseException e) {
			errorResult(e);
			e.printStackTrace();
		}
		return params;
	}

	public void errorResult(Exception e) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.SUCCESS, false);
		result.put(ExtConstant.ERROR_MSG, e.getMessage());
	}

	public List<AppPermission> regAppPermission(){
		return AppResourceHelper.formPermissions(getType());
	}
	
}
