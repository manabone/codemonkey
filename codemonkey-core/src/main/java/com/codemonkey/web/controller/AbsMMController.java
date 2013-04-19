package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.MM;
import com.codemonkey.extcmp.ExtCol;
import com.codemonkey.extcmp.ExtFormField;
import com.codemonkey.extcmp.ExtHidden;
import com.codemonkey.extcmp.ExtText;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.service.GenericService;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.MMHelper;
import com.codemonkey.utils.SysUtils;


@Controller
public abstract class AbsMMController<T extends MM> extends AbsExtController<T> implements SecurityController{
	
	private Class<?> type;
	
	private Logger log;
	
	AbsMMController(){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		log = SysUtils.getLog(getClass());
	}
	
	//----------------------
    // index
    //----------------------
    @RequestMapping
    public String index(ModelMap modelMap , HttpSession session) {
    	modelMap.addAttribute("modelFields", MMHelper.getModelFields(type));
    	modelMap.addAttribute("modelName", MMHelper.getModelName(type));
    	modelMap.addAttribute(ExtConstant.THEME, SysUtils.getCurrentTheme(session));
    	modelMap.addAttribute(ExtConstant.PAGE_DATA, getPageData());
    	modelMap.addAttribute("formItems", getFormItems());
    	modelMap.addAttribute("cols", getCols());
    	
    	return ExtConstant.MM_INDEX;
    }
    
    protected JSONObject getPageData() {
		JSONObject pageData = new JSONObject();
//		List<String> fieldNames = ClassHelper.getAllFields(type);
		pageData.put(ExtConstant.THEME, SysUtils.getAttribute(ExtConstant.THEME));
//		pageData.put("labels", labels(fieldNames));
		return pageData;
	}

    //----------------------
    // create
    //----------------------
    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody String body) {
    	JSONObject result = new JSONObject();
		try{
			T t = service().createEntity();
			JSONObject params = new JSONObject(body);
			ClassHelper.build(params, t , getCcService());
			service().save(t);
			result.put(ExtConstant.DATA, t.listJson());
			result.put(ExtConstant.SUCCESS, true);
		}catch(Exception e){
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
    public String read(@RequestParam(required=false) String id,
    		@RequestParam(required=false) String query,
    		@RequestParam(required=false) JSONObject queryInfo) {
    	List<T> list = new ArrayList<T>();
    	
    	if(StringUtils.isNotBlank(id)){
    		T t = service().get(Long.valueOf(id));
    		list.add(t);
    	}else if(StringUtils.isNotBlank(query)){
    		Criterion[] criterions = {
				Restrictions.like("name", query, MatchMode.ANYWHERE)	
    		};
    		list = service().find(criterions);
    	}else if(queryInfo != null){
    		list = service().findByQueryInfo(queryInfo);
    	}else{
    		list = service().findAll();
    	}
    	return buildJson(list);
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
			T t = service().get(params.getLong(ExtConstant.ID));
			if(t != null){
				ClassHelper.build(params, t , getCcService());
				service().save(t);
				result.put(ExtConstant.SUCCESS, true);
			}
		}catch(Exception e){
			result.put(ExtConstant.SUCCESS, false);
			result.put(ExtConstant.ERROR_MSG, e.getMessage());
			e.printStackTrace();
		}
		return result.toString();
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
			MM t = service().get(params.getLong(ExtConstant.ID));
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
	
    //override by subclass if you needs
  	protected List<ExtCol> getListCols(){
  		List<ExtCol> items = new ArrayList<ExtCol>();
  		ExtCol col = new ExtCol("id");
  		col.setHidden(true);
  		items.add(col);
  		items.add(new ExtCol("name"));
  		items.add(new ExtCol("description"));
  		return items;
  	}
  	
  	//override by subclass if you needs
  	protected List<ExtFormField> getFormFields(){
  		List<ExtFormField> items = new ArrayList<ExtFormField>();
  		items.add(new ExtHidden("id"));
  		items.add(new ExtText("name"));
  		items.add(new ExtText("description"));
  		return items;
  	}

	private JSONArray getFormItems() {
		JSONArray items = new JSONArray();
		List<ExtFormField> fields = getFormFields();
		if(CollectionUtils.isNotEmpty(fields)){
			for(ExtFormField f : fields){
				items.put(f.json());
			}
		}
		return items;
	}
	
	private JSONArray getCols() {
    	JSONArray cols = new JSONArray();
    	List<ExtCol> fields = getListCols();
		for(ExtCol f : fields){
			cols.put(f.json());
		}
		return cols;
	}
    
    private String buildJson(List<T> list) {
    	JSONObject jo = new JSONObject();
    	if(list != null){
    		JSONArray data = new JSONArray();
    		for(MM t : list){
    			data.put(t.listJson());
    		}
			jo.put(ExtConstant.TOTAL_COUNT, list.size());	
    		jo.put(ExtConstant.SUCCESS, true);
			jo.put(ExtConstant.DATA, data);
    	}
		return jo.toString();
	}

	public List<AppPermission> regAppPermission(){
		return AppResourceHelper.mmPermissions(getType());
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

}
