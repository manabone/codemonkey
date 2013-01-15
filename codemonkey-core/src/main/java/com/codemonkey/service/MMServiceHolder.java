package com.codemonkey.service;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.codemonkey.domain.MM;

public interface MMServiceHolder {

	void deleteAndFlush(Class<?> clazz , Long id);

	void saveAndFlush(Class<?> clazz  , MM entity);

	List<MM> findAll(Class<?> clazz );

	MM get(Class<?> clazz , Long id);
	
	List<MM> find(Class<?> clazz , Criterion... criterions);

	MMService get(Class<?> type);

}
