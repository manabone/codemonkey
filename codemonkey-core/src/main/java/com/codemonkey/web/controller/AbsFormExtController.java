package com.codemonkey.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.IEntity;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.utils.ExtConstant;

@Controller
public abstract class AbsFormExtController<T extends IEntity> extends AbsExtController<T>{

	public static final long NEW_ENTITY_ID = -1;
	
	// ----------------------
	// edit
	// ----------------------
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(ExtConstant.ID) Long id, ModelMap modelMap , HttpSession session) {
		modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData(session).put(ExtConstant.ID, id));
		modelMap.addAttribute("pageView", getType().getSimpleName() + "FormView");
		return ExtConstant.EE_INDEX;
	}
	
	//----------------------
    // create
    //----------------------
    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody String body) {
    	return handleUpdate(body);
    }

	//----------------------
    // read
    //----------------------
    @RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam Long id) {
    	return handleRead(id);
    }

    protected String handleRead(Long id) {
    	
		T t = null;
    	if(id == NEW_ENTITY_ID){
    		t = service().createEntity();
    	}else{
    		t = service().get(id);
    	}
    	
    	JSONObject result = new JSONObject();
    	if(t != null){
    		result.put(ExtConstant.DATA, buildJson(t));
			result.put(ExtConstant.SUCCESS, true);
    	}
    	return result.toString();
	}
    
    protected JSONObject buildJson(T t){
    	return jsonResult(t);
    }

	//----------------------
    // update
    //----------------------
	@RequestMapping("update")
    @ResponseBody
	public String update(@RequestBody String body){
		
		return handleUpdate(body);
	}

	public List<AppPermission> regAppPermissions(){
		List<UrlPermission> list = AppResourceHelper.formPermissions(getType());
		return convertToAppPermissionList(list);
	}
}
