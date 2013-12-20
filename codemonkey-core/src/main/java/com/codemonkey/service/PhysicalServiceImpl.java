package com.codemonkey.service;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.IEntity;
import com.codemonkey.security.Operation;

@Transactional
public abstract class PhysicalServiceImpl<T extends IEntity> extends GenericServiceImpl<T> {
	
	public long count(){
		return getDao().count();
	}
	
	public T get(Long id) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		T t = getDao().get(id);
		stopWatch.stop();
		getLog().info(stopWatch);
		return t;
	}
	
	public List<T> findAll() {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findAll();

		stopWatch.stop();
		getLog().info(stopWatch);

		return list;
	}
	
	public T findBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		T t = getDao().findBy(query, params);

		stopWatch.stop();
		getLog().info(stopWatch);

		return t;
	}

	@Override
	public long countBy(String query, Object... params) {
		return getDao().countBy(query, params);
	}

	public List<T> findAllBy(String query, Object... params) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findAllBy(query, params);

		stopWatch.stop();
		getLog().info(stopWatch);

		return list;
	}

	public List<T> findByQueryInfo(JSONObject queryAndSort, Integer start,
			Integer limit) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<T> list = getDao().findByQueryInfo(queryAndSort, start, limit);

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
	
	@Override
	public void delete(Long id) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		T t = getDao().get(id);
		SyncService srv = getSyncService();

		if (srv != null) {
			srv.dosync(t, Operation.DESTROY);
		}

		getDao().delete(id);

		stopWatch.stop();
		getLog().info(stopWatch);

	}


}
