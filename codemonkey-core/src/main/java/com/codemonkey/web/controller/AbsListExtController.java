package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.IEntity;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.view.ExcelView;

@Controller
public abstract class AbsListExtController<T extends IEntity> extends AbsExtController<T>{

	
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
	@RequestMapping("show")
    public String show(ModelMap modelMap , HttpSession session) {
    	modelMap.addAttribute(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
		modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData(session));
		modelMap.addAttribute("pageView", getType().getSimpleName() + "ListView");
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
    		@RequestParam(required = false , defaultValue="[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue="{}") JSONObject queryInfo) {
    	
    	return handleRead(start, limit, id, query, sort, queryInfo);
    }

	protected String handleRead(Integer start, Integer limit, String id,
			String query, JSONArray sort, JSONObject queryInfo) {
		List<T> list = new ArrayList<T>();
    	long total = 0;
    	
    	if(StringUtils.isNotBlank(id)){
    		T t = service().get(Long.valueOf(id));
    		total = 1;
    		list.add(t);
    	}else if(StringUtils.isNotBlank(query)){
    		list = service().findAllBy("name_Like" , '%' + query + '%');
    		total = service().countBy("name_Like" , '%' + query + '%');
    	}else if(sort != null || queryInfo != null){
    		JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
    		total = service().countByQueryInfo(queryAndSort);
    		if(total > 0){
    			list = service().findByQueryInfo(queryAndSort , start , limit);
    		}
    	}
    	return buildJson(list , total);
	}
    
	//----------------------
    // destroy
    //---------------------- 
	@RequestMapping("destroy")
    @ResponseBody
	public String destroy(@RequestBody String body){
		return handleDestroy(body);
	}

	protected String handleDestroy(String body) {
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
	
	//----------------------
    // export
    //----------------------
	@RequestMapping("export")
	public ModelAndView export(ModelMap modelMap , 
			@RequestParam JSONArray cols , 
			@RequestParam(required = false) String scope,
			@RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit , 
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false , defaultValue = "[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue = "{}") JSONObject queryInfo){
		List<T> list = null;
		JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
		if("".equals(scope)){
			list = service().findByQueryInfo(queryAndSort);
		}else if("当前页".equals(scope)){
			list = service().findByQueryInfo(queryAndSort , start , limit);
		}
		
		List<IEntity> list2 = new ArrayList<IEntity>();
		
		if(CollectionUtils.isNotEmpty(list)){
			list2.addAll(list);
		}
		
		ExcelView view = new ExcelView(cols , list2  , fileName);
		return new ModelAndView(view , modelMap);
	}
	
	public List<AppPermission> regAppPermissions(){
		return convertToAppPermissionList(AppResourceHelper.listPermissions(getType()));
	}
}
