package com.codemonkey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.domain.MM;

@Component
@Transactional
public class MMServiceHolderImpl implements MMServiceHolder{

	private static Map<Class<?> , MMService> holder = new HashMap<Class<?> , MMService>();
	
	@Autowired private SessionFactory sessionFactory;
	
	public MMService get(Class<?> type) {
		
		MMService service = getHolder().get(type);
		if(service != null){
			return service;
		}
		
		service = new MMServiceImpl(type);
		service.setDao(new GenericDao<MM>(sessionFactory , type));
		
		getHolder().put(type , service);
		
		return service;
	}

	public static Map<Class<?> , MMService> getHolder() {
		return holder;
	}

	public void deleteAndFlush(Class<?> clazz, Long id) {
		get(clazz).deleteAndFlush(id);
	}

	public void saveAndFlush(Class<?> clazz , MM entity) {
		get(clazz).saveAndFlush(entity);
	}

	public List<MM> findAll(Class<?> clazz) {
		return get(clazz).findAll();
	}

	public MM get(Class<?> clazz, Long id) {
		return get(clazz).get(id);
	}

	public List<MM> find(Class<?> clazz, Criterion... criterions) {
		return get(clazz).find(criterions);
	}

}
