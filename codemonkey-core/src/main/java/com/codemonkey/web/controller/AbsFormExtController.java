package com.codemonkey.web.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public abstract class AbsFormExtController<T extends EE> extends AbsExtController<T>{

	protected abstract T createEntity();
	
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
    	JSONObject result = new JSONObject();
		try{
			JSONObject params = new JSONObject(body);
			T t = buildEntity(params);
			service().doSave(t);
			result.put(ExtConstant.DATA, t.detailJson());
			result.put(ExtConstant.SUCCESS, true);
		}catch(ParseException e){
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, e.getMessage());
			e.printStackTrace();
		}
		return result.toString();
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
		JSONObject result = new JSONObject();
		try{
			JSONObject params = new JSONObject(body);
			T t = buildEntity(params);
			service().doSave(t);
			result.put(ExtConstant.DATA, t.detailJson());
			result.put(ExtConstant.SUCCESS, true);
		}catch(ParseException e){
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, e.getMessage());
			e.printStackTrace();
		}
		return result.toString();
	}
	
	protected T buildEntity(JSONObject params){
		T t = null;
		Long id = extractId(params);
		
		if(id == null){
			t = createEntity();
			ClassHelper.bulid(params, t , getCcService());
		}else{
			t = service().get(id);
			if(t != null){
				ClassHelper.bulid(params, t , getCcService());
			}
		}
		return t;
	}

	private Long extractId(JSONObject params) {
		Long id = null;
		if(params.has(ExtConstant.ID) && StringUtils.isNotBlank(params.getString(ExtConstant.ID)) && !"null".equals(params.getString(ExtConstant.ID))){
			id = params.getLong(ExtConstant.ID);
		}
		return id;
	}

	public List<AppPermission> regAppPermission(){
		return AppResourceHelper.formPermissions(getType());
	}
	
}
