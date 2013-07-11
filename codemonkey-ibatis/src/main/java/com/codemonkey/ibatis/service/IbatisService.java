package com.codemonkey.ibatis.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface IbatisService {

	public List<Map<String, Object>> query(String id, JSONObject queryAndSort);

	public abstract long count(String id, JSONObject queryInfo);

}