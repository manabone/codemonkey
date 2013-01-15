package com.codemonkey.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.json.JSONObject;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.domain.MM;

public class MMServiceImpl extends AbsService implements MMService {

	private GenericDao<MM> dao;
	
	private Class<?> type;
	
	public MMServiceImpl(Class<?> type){
		this.type = type;
	}
	
	public void deleteAndFlush(Long id) {
		getDao().deleteAndFlush(id);
	}

	public void saveAndFlush(MM entity) {
		getDao().saveAndFlush(entity);
	}
	
	private GenericDao<MM> getDao() {
		return dao;
	}
	
	public void setDao(GenericDao<MM> dao) {
		this.dao = dao;
	}

	public List<MM> findAll() {
		return getDao().findAll();
	}

	public MM get(Long id) {
		return getDao().get(id);
	}

	public List<MM> find(Criterion... criterions) {
		return getDao().findByCriteria(criterions);
	}
	
	public MM findBy(String query , Object... params) {
		return getDao().findBy(query, params);
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public List<MM> findByQueryInfo(JSONObject queryInfo) {
		return getDao().findByQueryInfo(queryInfo);
	}

	public long countByQueryInfo(JSONObject queryInfo) {
		return getDao().countByQueryInfo(queryInfo);
	}
	
}
