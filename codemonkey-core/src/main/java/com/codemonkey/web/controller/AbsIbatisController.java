package com.codemonkey.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.service.MybatisService;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public abstract class AbsIbatisController extends AbsController implements SecurityController{
	
	private Logger log;
	
	@Autowired MybatisService mybatisService;
	
	AbsIbatisController(){
		log = SysUtils.getLog(getClass());
	}
	
	abstract String getQueryId();
	
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
    	
    	long total = mybatisService.count(getQueryId(), queryInfo);
    	List<Map<String , Object>> list = null;
    	if(total > 0){
    		JSONObject queryAndSort = new JSONObject().put("sort", sort).put("query", queryInfo);
    		list = mybatisService.query(getQueryId(), queryAndSort);
    	}
    	
    	return buildJson(list , total);
    }	
	
	
    protected String buildJson(List<Map<String,Object>> list) {
    	JSONObject jo = buildResult(list);
		return jo.toString();
	}
    
    protected String buildJson(List<Map<String,Object>> list , long total) {
    	JSONObject jo = buildResult(list);
    	jo.put(ExtConstant.TOTAL_COUNT, total);
		return jo.toString();
	}
    
	private JSONObject buildResult(List<Map<String,Object>> list) {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		if(CollectionUtils.isEmpty(list)){
    		result.put(ExtConstant.SUCCESS, true);
    		result.put(ExtConstant.DATA, data);
    		return result;
    	}	
    		
		for(Map<String,Object> m : list){
			Set<Entry<String,Object>> set = m.entrySet();
			JSONObject jo = new JSONObject();
			Iterator<Entry<String,Object>> it = set.iterator();
			while(it.hasNext()){
				Entry<String,Object> e = it.next();
				jo.put(e.getKey().toString() , e.getValue());
				data.put(jo);
			}
		}
		result.put(ExtConstant.SUCCESS, true);
		result.put(ExtConstant.DATA, data);
		return result;
	}
	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
