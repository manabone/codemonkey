package com.codemonkey.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.json.JSONObject;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.domain.MM;


public interface MMService {

	void deleteAndFlush(Long id);

	void saveAndFlush(MM entity);

	List<MM> findAll();

	MM get(Long id);
	
	void setDao(GenericDao<MM> dao);

	List<MM> find(Criterion... criterions);
	
	MM findBy(String query , Object... params);
	
	List<MM> findByQueryInfo(JSONObject queryInfo);

	long countByQueryInfo(JSONObject queryInfo);
}
