package com.codemonkey.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;

import com.codemonkey.web.converter.CustomConversionService;

public interface GenericService<T> extends Converter<String, T> {

	T get(Long id);

    void save(T entity);

    void delete(Long id);
    
    long count(Criterion... criterions);
    
    List<T> find(Criterion... criterions);
    
    List<T> find(int start , int limit , Criterion... criterions);
    
    List<T> findAll();
    
    List<T> findAll(int start , int limit);
    
    long countBy(String query , Object... params);
    
    long countBy(String query , String[] joins  , Object... params);
    
    T findBy(String query , Object... params);
    
    T findBy(String query , String[] joins  , Object... params);
	
	List<T> findAllBy(String query , Object... params);
	
	List<T> findAllBy(String query , String[] joins , Object... params);

	List<T> findByQueryInfo(JSONObject queryInfo, Integer start, Integer limit);
	
	List<T> findByQueryInfo(JSONObject queryInfo);

	long countByQueryInfo(JSONObject queryInfo);

	T doSave(JSONObject body , CustomConversionService ccService);
	
	T buildEntity(JSONObject params , CustomConversionService ccService);
	
	T createEntity();
	
}
