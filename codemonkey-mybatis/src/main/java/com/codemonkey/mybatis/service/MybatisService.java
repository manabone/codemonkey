package com.codemonkey.mybatis.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface MybatisService {

	public List<Map<String, Object>> query(String id, JSONObject queryAndSort);

	public abstract long count(String id, JSONObject queryInfo);

}