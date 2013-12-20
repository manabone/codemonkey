package com.codemonkey.ibatis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.ibatis.service.IbatisService;
import com.codemonkey.ibatis.utils.IbatisUtils;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.AbsController;
import com.codemonkey.web.controller.SecurityController;


public abstract class AbsIbatisController extends AbsController implements SecurityController{
	
	private Logger log;
	
	public static String SUBFIX_COUNT = "_count";
	
	@Autowired private IbatisService ibatisService;
	
	public AbsIbatisController(){
		log = SysUtils.getLog(getClass());
	}
	
	public abstract String getQueryId();
	
	
	@Override
	public List<AppPermission> regAppPermissions() {
		return new ArrayList<AppPermission>();
	}

	@Override
	public List<SecurityComponent> regSecurityComponents() {
		return new ArrayList<SecurityComponent>();
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
    		@RequestParam(required = false , defaultValue = "[]") JSONArray sort,
    		@RequestParam(required = false , defaultValue = "{}") JSONObject queryInfo) {
    	
    	return handleRead(start, limit, id, query, sort, queryInfo);
    	
    }
	
	protected String handleRead(Integer start, Integer limit, String id,
			String query, JSONArray sort, JSONObject queryInfo) {
		
		JSONObject queryAndSort = new JSONObject().put(ExtConstant.SORT, sort).put(ExtConstant.QUERY, queryInfo);
		
		long total = ibatisService.count(getQueryId() + SUBFIX_COUNT , IbatisUtils.jsonToMap(queryAndSort));
    	List<Map<String , Object>> list = null;
    	if(total > 0){
    		list = ibatisService.query(getQueryId(), IbatisUtils.jsonToMap(queryAndSort));
    	}
    	return IbatisUtils.buildJson(list , total);
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
