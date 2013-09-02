package com.codemonkey.ibatis.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface IbatisService {

	public List<Map<String, Object>> query(String id, JSONObject queryAndSort);

	public abstract long count(String id, JSONObject queryInfo);
	
	public void insert(String id, Map<String, Object> param);
	
	public void update(String id, Map<String, Object> param);
	
	public List<Map<String, Object>> querybylist(String id, Map<String, Object> param);

}