package com.codemonkey.ibatis.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.ibatis.dao.IbatisDao;
import com.codemonkey.utils.SysUtils;

@Service
public class IbatisServiceImpl implements IbatisService {

	@Autowired private IbatisDao dao;
	
	private Logger logger = SysUtils.getLog(getClass());

	@Override
	public List<Map<String,Object>> query(String id , Object param){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<Map<String,Object>> list = dao.query(id , param);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return list;
	}
	
	@Override
	public long count(String id , Object obj){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Long count = dao.count(id, obj);
		
		if(count == null){
			count = 0L;
		}
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return count;
	}
	
	@Override
	public List<Map<String,Object>> querybylist(String id , Object obj){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<Map<String,Object>> list = dao.query(id , obj);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return list;
	}
	
	@Override
	public List<Map<String,Object>> querybylist(String id){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<Map<String,Object>> list = dao.query(id);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return list;
	}
	
	@Override
	public Map<String,Object> querybyobject(String id , Object obj){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Map<String,Object> map = dao.queryobj(id, obj);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return map;
	}
	
	
	@Override
	public Map<String,Object> querybyobject(String id){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Map<String,Object> map = dao.queryobj(id);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
		return map;
	}

	@Override
	public void insert(String id , Object obj){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		dao.insert(id, obj);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
	}
	
	@Override
	public void update(String id , Object obj){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		dao.update(id, obj);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
	}
	
	@Override
	public void delete(String id , Object obj){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		dao.delete(id, obj);
		
		stopWatch.stop();
		logger.info(stopWatch);
		
	}
	
}
