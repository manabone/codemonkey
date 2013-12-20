package com.codemonkey.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.HqlHelper;
import com.codemonkey.utils.SysUtils;

@SuppressWarnings("unchecked")
public class GenericDao<T extends IEntity> {

	private Class<?> type;
	
	private Logger logger = SysUtils.getLog(getClass());
    
	private SessionFactory sessionFactory;
	
	@Autowired
	public GenericDao(){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
	}
	
    @Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
    	this.sessionFactory = sessionFactory;
	}
    
    public Session getSession(){
    	return sessionFactory.getCurrentSession();
    }
    
	public T get(Long id){
    	return (T) getSession().get(getType(), id);
    }
	
	public void saveAndFlush(T t){
		save(t);
	    getSession().flush();
	}

    public void save(T t){
    	
    	if(t.getId() == null){
    		t.setCreationDate(new Date());
    		t.setCreatedBy(SysUtils.getCurrentUsername());
    		
//    		SequenceCreator sc = t.getSequenceCreator();
//    		if(sc != null){
//    			String code = sequenceCreatorDao.fetch(sc);
//    			t.setCode(code);
//    		}
    		
    	}else{
    		t.setModificationDate(new Date());
    		t.setModifiedBy(SysUtils.getCurrentUsername());
    	}
    	
		getSession().saveOrUpdate(t);
    }
    
    public void deleteAndFlush(Long id){
    	delete(id);
    	getSession().flush();
    }

    public void delete(Long id){
    	
    	if(id == null){
    		return;
    	}
    	
    	T t = get(id);
    	
    	if(t == null){
    		return;
    	}
    	
    	getSession().delete(t);
    }
    
    public List<T> findAll(){
    	return buildCriteria().list();
    }
    
    public List<T> findAll(int start, int limit) {
    	Criteria c = buildCriteria();
    	c.setFirstResult(start);
    	c.setMaxResults(limit);
    	return c.list();
	}

	private Criteria buildCriteria() {
		return getSession().createCriteria(getType());
	}
    
    private Criteria buildCriteria(List<Criterion> criterions) {
		return buildCriteria(criterions , null);
	}

	private Criteria buildCriteria(List<Criterion> criterions , List<Order> orders) {
		Criteria c = buildCriteria();
    	
    	if(criterions != null && !criterions.isEmpty()){
    		for(Criterion cr : criterions){
    			c.add(cr);
    		}
    	}
    	
    	if(orders != null && !orders.isEmpty()){
    		for(Order order : orders){
    			c.addOrder(order);
    		}
    	}
		return c;
	}
	
	public List<T> findByCriteria(Criterion... criterions){
		List<Criterion> conditions = new ArrayList<Criterion>();
		if(criterions != null){
			conditions = Arrays.asList(criterions);
		}
    	return findByCriteria(conditions);
    }
	
	public List<T> findByCriteria(int start, int limit, Criterion... criterions){
    	return findByCriteria(start , limit , Arrays.asList(criterions));
    }
	
	public List<T> findByCriteria(List<Criterion> criterions){
    	Criteria c = buildCriteria(criterions);
    	return c.list();
    }
	
	public List<T> findByCriteria(int start, int limit, List<Criterion> criterions){
    	Criteria c = buildCriteria(criterions);
    	c.setFirstResult(start);
    	c.setMaxResults(limit);
    	return c.list();
    }
    
	public long count(Criterion... criterions){
		List<Criterion> conditions = new ArrayList<Criterion>();
		if(criterions != null){
			conditions = Arrays.asList(criterions);
		}
		return count(conditions);
    }
	
	public long count(List<Criterion> criterions){
    	Criteria c = buildCriteria(criterions);
    	c.setProjection(Projections.rowCount());
    	return countResult((Long) c.uniqueResult());
    }
    
	public List<T> findAllBy(String query, Object... params) {
		
		String hql = HqlHelper.findBy(getType(), query);
		
		Query hqlQuery = builHqlQuery(hql, params);
		
		return hqlQuery.list();
	}
	
	public T findBy(String query, Object... params) {
		
		String hql = HqlHelper.findBy(getType(), query);
		Query hqlQuery = builHqlQuery(hql, params);
		return (T) hqlQuery.uniqueResult();
	
	}
	
	public long countBy(String query, Object... params) {
		String hql = HqlHelper.countBy(getType(), query);
		Query hqlQuery = builHqlQuery(hql, params);
		return countResult((Long) hqlQuery.uniqueResult());
	}
	
	private Query builHqlQuery(String hql, Object... params) {
		
		logger.debug(hql);
		
		Query hqlQuery = getSession().createQuery(hql);
		
		if(params != null && params.length > 0) {
			for(int i = 0 ; i < params.length ; i++){
				hqlQuery.setParameter(i, params[i]);
			}
		}
		return hqlQuery;
	}
	
	public List<T> findByQueryInfo(JSONObject queryAndSort, Integer start, Integer limit) {
		
		String hql = HqlHelper.findByQueryInfo(getType(), queryAndSort);
		
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return hqlQuery.setMaxResults(limit).setFirstResult(start).list();
	}

	public List<T> findByQueryInfo(JSONObject queryAndSort) {
		String hql = HqlHelper.findByQueryInfo(getType(), queryAndSort);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return hqlQuery.list();
	}
	
	public List<Object> findByQueryInfo(String hqlSelect, JSONObject queryAndSort, Integer start, Integer limit) {
		String hql = HqlHelper.findByQueryInfo(getType(), queryAndSort);
		hql = hql.replace(HqlHelper.SELECT_FROM , hqlSelect);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return hqlQuery.setMaxResults(limit).setFirstResult(start).list();
	}

	public long countByQueryInfo(JSONObject queryAndSort) {
		String hql = HqlHelper.countByQueryInfo(getType(), queryAndSort);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return countResult((Long) hqlQuery.uniqueResult());
	}
	
	public long countByQueryInfo(String hqlSelect , JSONObject queryAndSort) {
		String hql = HqlHelper.countByQueryInfo(getType() , queryAndSort);
		hql = hql.replace(HqlHelper.SELECT_FROM , hqlSelect);
		List<Object> params = HqlHelper.extractParamsFromQueryInfo(getType(), queryAndSort);
		Query hqlQuery = builHqlQuery(hql , params.toArray());
		return countResult((Long) hqlQuery.uniqueResult());
	}
	
	private long countResult(Long count){
		if(count == null){
			return 0;
		}
		return count;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}
}
