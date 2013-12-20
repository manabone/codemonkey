package com.codemonkey.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.IEntity;
import com.codemonkey.security.Operation;
import com.codemonkey.utils.ExtConstant;

@Transactional
public abstract class LogicalServiceImpl<T extends IEntity> extends GenericServiceImpl<T> {

	public long count(){
		return getDao().count(Restrictions.eq("delFlg", false));
	}
	
	public T get(Long id) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		T t = getDao().findBy("id&&delFlg", id , false);
		stopWatch.stop();
		getLog().info(stopWatch);
		return t;
	}
	
	public List<T> findAll() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list =  getDao().findAllBy("delFlg", false);

		stopWatch.stop();
		getLog().info(stopWatch);

		return list;
	}
	
	public T findBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		List<Object> list = new ArrayList<Object>();
		list.add(false);
		if(params != null){
			list.addAll(Arrays.asList(params));
		}

		T t = getDao().findBy("delFlg&&" + query, list.toArray());

		stopWatch.stop();
		getLog().info(stopWatch);

		return t;
	}

	@Override
	public long countBy(String query, Object... params) {
		
		List<Object> list = new ArrayList<Object>();
		list.add(false);
		if(params != null){
			list.addAll(Arrays.asList(params));
		}
		return getDao().countBy("delFlg&&" + query, list.toArray());
	}

	public List<T> findAllBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findAllBy("delFlg&&" + query, false , params);

		stopWatch.stop();
		getLog().info(stopWatch);

		return list;
	}

	public List<T> findByQueryInfo(JSONObject queryAndSort, Integer start,
			Integer limit) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);

		List<T> list = getDao().findByQueryInfo(queryAndSort, start, limit);

		stopWatch.stop();
		getLog().info(stopWatch);

		return list;

	}

	public List<T> findByQueryInfo(JSONObject queryAndSort) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);
		
		List<T> list = getDao().findByQueryInfo(queryAndSort);

		stopWatch.stop();
		getLog().info(stopWatch);

		return list;

	}

	public long countByQueryInfo(JSONObject queryAndSort) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		queryAndSort.getJSONObject(ExtConstant.QUERY).put("delFlg", false);
		
		long count = getDao().countByQueryInfo(queryAndSort);

		stopWatch.stop();
		getLog().info(stopWatch);

		return count;

	}
	
	@Override
	public void delete(Long id) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		SyncService srv = getSyncService();

		T t = getDao().get(id);
		if (srv != null) {
			srv.dosync(t, Operation.DESTROY);
		}

		t.setDelFlg(true);
		this.save(t);

		stopWatch.stop();
		getLog().info(stopWatch);

	}

}
