package com.codemonkey.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.dao.searchingInfo.SearchingInfo;
import com.codemonkey.domain.EE;
import com.codemonkey.error.BadObjVersionError;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.ValidationError;
import com.codemonkey.utils.ClassHelper;

@Transactional
public abstract class GenericServiceImpl<T extends EE> extends AbsService implements GenericService<T> {

	private GenericDao<T> dao;
	
	private Class<?> type;
	
	public GenericServiceImpl(){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
	}
	
	@Autowired private Validator validator;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		dao = new GenericDao<T>(sessionFactory , type);
	}
	
	protected GenericDao<T> getDao() {
		return dao;
	}
	
	public T get(Long id) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		T t = getDao().get(id);
		stopWatch.stop();
		getLog().info(stopWatch);
		return t;
	}

	public void doSave(T entity) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Set<FieldValidation> set = validate(entity);
		if(CollectionUtils.isNotEmpty(set)){
			throw new ValidationError(set);
		}
		getDao().save(entity);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
	}

	//implements by subclass if needed
	//if validation failed , throw ValidationError exception
	protected Set<FieldValidation> validate(T entity) {
		Set<FieldValidation> errorSet = new HashSet<FieldValidation>();
		if(entity == null){
			return errorSet;
		}
		
		if(entity.isOptimisticLockingFailure()){
			throw new BadObjVersionError(entity);
		}
		
		Set<ConstraintViolation<T>> set = validator.validate(entity, javax.validation.groups.Default.class);
		
		if(StringUtils.isNotBlank(entity.getCode())){
			long count = getDao().countBy("code" , entity.getCode());
			if(count > 0){
				errorSet.add(new FieldValidation("code" , "code must be unique"));
			}
		}
		
		if(CollectionUtils.isNotEmpty(set)){
			for(ConstraintViolation<T> c : set){
				errorSet.add(new FieldValidation(c));
			}
		}
		return errorSet;
	}

	public void doDelete(Long id) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		getDao().delete(id);
		
		stopWatch.stop();
		getLog().info(stopWatch);
	
	}

	public List<T> findAll() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<T> list = getDao().findAll();
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
	}
	
	public List<T> findAll(int start , int limit){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<T> list = getDao().findAll(start , limit);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
	}
	
	public List<T> find(Criterion... criterions){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<T> list = getDao().findByCriteria(criterions);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
	}
	
	public List<T> find(int start, int limit, Criterion... criterions) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<T> list = getDao().findByCriteria(start , limit , criterions);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
	}

	public List<T> findBySearchingInfo(SearchingInfo searchingInfo){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<T> list = getDao().findBySearchingInfo(searchingInfo);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
	}
	
	public T findBy(String query , Object... params){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		T t = getDao().findBy(query , params);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return t;
	}
	
	public List<T> findAllBy(String query , Object... params){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<T> list = getDao().findAllBy(query , params);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
	}
	
	public long count(SearchingInfo searchingInfo){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		long count = getDao().count(searchingInfo);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return count;
	}
	
	public long count(Criterion... criterions){
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		long count = getDao().count(criterions);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return count;
	}
	
	public T convert(String source) {
		
		if(StringUtils.isEmpty(source)){
			return null;
		}
		
		return getDao().get(Long.valueOf(source));
	}
	
	public List<T> findByQueryInfo(JSONObject queryInfo, Integer start, Integer limit) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<T> list = getDao().findByQueryInfo(queryInfo , start , limit);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
		
	}

	public List<T> findByQueryInfo(JSONObject queryInfo) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findByQueryInfo(queryInfo);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return list;
		
	}

	public long countByQueryInfo(JSONObject queryInfo) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		long count = getDao().countByQueryInfo(queryInfo);
		
		stopWatch.stop();
		getLog().info(stopWatch);
		
		return count;
		
	}
}