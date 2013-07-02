package com.codemonkey.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.dao.MybatisDao;
import com.codemonkey.utils.SysUtils;

@Service
public class MybatisServiceImpl implements MybatisService {

	@Autowired private MybatisDao dao;
	
	private Logger logger = SysUtils.getLog(getClass());

	@Override
	public List<Map<String,Object>> query(String id , JSONObject queryAndSort){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Map<String , Object> param = SysUtils.jsonToMap(queryAndSort);
		
		List<Map<String,Object>> list = dao.query(id , param);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return list;
	}
	
	@Override
	public long count(String id , JSONObject queryInfo){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Map<String , Object> param = SysUtils.jsonToMap(queryInfo);
		
		Long count = dao.count(id, param);
		
		if(count == null){
			count = 0L;
		}
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return count;
	}
	
}
