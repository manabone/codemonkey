package com.codemonkey.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;

import com.codemonkey.dao.searchingInfo.SearchingInfo;

public interface GenericService<T> extends Converter<String, T> {

	T get(Long id);

    void doSave(T entity);

    void doDelete(Long id);
    
    long count(Criterion... criterions);
    
    List<T> find(Criterion... criterions);
    
    List<T> find(int start , int limit , Criterion... criterions);
    
    List<T> findAll();
    
    List<T> findAll(int start , int limit);
    
    List<T> findBySearchingInfo(SearchingInfo searchingInfo);
    
    long count(SearchingInfo searchingInfo);
    
    T findBy(String query , Object... params);
	
	List<T> findAllBy(String query , Object... params);

	List<T> findByQueryInfo(JSONObject queryInfo, Integer start, Integer limit);
	
	List<T> findByQueryInfo(JSONObject queryInfo);

	long countByQueryInfo(JSONObject queryInfo);

}
