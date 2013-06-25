package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.EE;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public abstract class AbsListExtController<T extends EE> extends AbsExtController<T>{

	
	//----------------------
	// create
	//----------------------
	@RequestMapping("new")
    public String createNew(ModelMap modelMap , HttpSession session) {
    	modelMap.addAttribute(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
		modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData(session));
    	return ExtConstant.EE_INDEX;
    }
	
	//----------------------
	// list
	//----------------------
	@RequestMapping("list")
    public String list(ModelMap modelMap , HttpSession session) {
    	modelMap.addAttribute(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
		modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData(session));
    	return ExtConstant.EE_INDEX;
    }

	
	//----------------------
    // read
    //----------------------
    @RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam(required = false) Integer page , 
    		@RequestParam(required = false , defaultValue = "0") Integer start , 
    		@RequestParam(required = false , defaultValue = "25") Integer limit ,
    		@RequestParam(required = false) String id,
    		@RequestParam(required = false) String query,
    		@RequestParam(required = false) JSONArray sort,
    		@RequestParam(required = false) JSONObject queryInfo) {
    	
    	List<T> list = new ArrayList<T>();
    	long total = 0;
    	
    	if(StringUtils.isNotBlank(id)){
    		T t = service().get(Long.valueOf(id));
    		total = 1;
    		list.add(t);
    	}else if(StringUtils.isNotBlank(query)){
    		Criterion[] criterions = {
				Restrictions.like("name", query, MatchMode.ANYWHERE)
    		};
    		list = service().find(criterions);
    		total = service().count(criterions);
    	}else if(sort != null || queryInfo != null){
    		JSONObject queryAndSort = new JSONObject().put("sort", sort).put("query", queryInfo);
    		list = service().findByQueryInfo(queryAndSort , start , limit);
    		total = service().countByQueryInfo(queryAndSort);
    	}else{
    		list = service().findAll(start , limit);
    		total = service().count();
    	}
    	
    	return buildJson(list , total);
    }
    
	//----------------------
    // destroy
    //---------------------- 
	@RequestMapping("destroy")
    @ResponseBody
	public String destroy(@RequestBody String body){
		JSONObject result = new JSONObject();
		try{
			JSONObject params = new JSONObject(body);
			T t = service().get(params.getLong(ExtConstant.ID));
			if(t != null){
				service().delete(params.getLong(ExtConstant.ID));
			}
			result.put(ExtConstant.SUCCESS, true);
		}catch(Exception e){
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, e.getMessage());
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public List<AppPermission> regAppPermission(){
		return AppResourceHelper.listPermissions(getType());
	}
}
