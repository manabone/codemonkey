package com.codemonkey.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.IEntity;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.service.GenericService;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.ResourceUtils;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.converter.CustomConversionService;


public abstract class AbsExtController<T extends IEntity> extends AbsController {
	
	private Class<?> type;
	
	private Logger log;
	
	@Autowired private CustomConversionService ccService;
	
	@Autowired private SysUtils sysUtils;
	
	@Autowired private ResourceUtils resourceUtils;
	
	protected abstract GenericService<T> service();
	
	public AbsExtController(){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		log = SysUtils.getLog(getClass());
	}
	
	//----------------------
	// index
	//----------------------
//	@RequestMapping("index")
//    public String index(ModelMap modelMap , HttpSession session) {
//		modelMap.addAttribute("modelFields", MMHelper.getModelFields(type));
//    	modelMap.addAttribute("modelName", MMHelper.getModelName(type));
//    	modelMap.addAttribute(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
//		modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData(session));
//    	return ExtConstant.INDEX;
//    }

	protected JSONObject getPageData(HttpSession session) {
		JSONObject pageData = new JSONObject();
		List<String> fieldNames = ClassHelper.getAllFieldNames(type);
		pageData.put(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
		pageData.put("labels", labels(fieldNames));
		return pageData;
	}

	private JSONObject labels(List<String> fieldNames) {
		JSONObject labels = new JSONObject();
		if(CollectionUtils.isNotEmpty(fieldNames)){
			for(String fn : fieldNames){
				String key = StringUtils.uncapitalize(type.getSimpleName()) + "." + fn;
//				sysUtils.getAppSetting("setting.test");
				labels.put(key , resourceUtils.msg(key));
			}
		}
		return labels;
	}
	
	public String handleUpdate(String body) {
		
		JSONObject params = parseJson(body);
		
		T t = service().doSave(params , getCcService());
		
		return result(t);
	}
	
	public String result(T t) {
		JSONObject result = new JSONObject();
		result.put(ExtConstant.DATA, jsonResult(t));
		result.put(ExtConstant.SUCCESS, true);
		return result.toString();
	}
	
	public JSONObject jsonResult(T t) {
		return t.detailJson();
	}
	
	public JSONObject parseJson(String body){
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
	
    protected String buildJson(List<T> list) {
    	JSONObject jo = buildResult(list);
		return jo.toString();
	}
    
    protected String buildJson(List<T> list , long total) {
    	JSONObject jo = buildResult(list);
    	jo.put(ExtConstant.TOTAL_COUNT, total);
		return jo.toString();
	}
    
	private JSONObject buildResult(List<T> list) {
		JSONObject jo = new JSONObject();
    	if(list != null){
    		JSONArray data = new JSONArray();
    		for(T t : list){
    			data.put(buildRecord(t));
    		}
    		jo.put(ExtConstant.SUCCESS, true);
			jo.put(ExtConstant.DATA, data);
    	}
		return jo;
	}
	
	protected JSONObject buildRecord(T t) {
		return t.listJson();
	}

	protected List<AppPermission> convertToAppPermissionList(List<UrlPermission> list) {
		List<AppPermission> pList = new ArrayList<AppPermission>();
		if(CollectionUtils.isNotEmpty(list)){
			for(UrlPermission p : list){
				pList.add(p);
			}
		}
		return pList;
	}
	
	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public SysUtils getSysUtils() {
		return sysUtils;
	}

	public void setSysUtils(SysUtils sysUtils) {
		this.sysUtils = sysUtils;
	}

	public CustomConversionService getCcService() {
		return ccService;
	}

	public void setCcService(CustomConversionService ccService) {
		this.ccService = ccService;
	}

}
