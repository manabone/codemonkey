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
import com.codemonkey.domain.EE;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public abstract class AbsFormExtController<T extends EE> extends AbsExtController<T>{

	// ----------------------
	// edit
	// ----------------------
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(ExtConstant.ID) Long id, ModelMap modelMap , HttpSession session) {
		modelMap.addAttribute(INDEX_VIEW, getIndexView());
		modelMap.addAttribute(CONTROLLERS, getControllers());
		modelMap.addAttribute(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
		modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData(session).put(ExtConstant.ID, id));
		return ExtConstant.INDEX;
	}
	
	//----------------------
    // create
    //----------------------
    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody String body) {
    	JSONObject params = new JSONObject();
		try {
			params = new JSONObject(body);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return service().doSave(params , getCcService()).toString();
    }
    
    //----------------------
    // read
    //----------------------
    @RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam Long id) {
    	JSONObject result = new JSONObject();
    	T t = service().get(id);
    	
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
		
		JSONObject params = new JSONObject();
		try {
			params = new JSONObject(body);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return service().doSave(params , getCcService()).toString();
	}

	public List<AppPermission> regAppPermission(){
		return AppResourceHelper.formPermissions(getType());
	}
	
}
