package com.codemonkey.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.domain.IEntity;
import com.codemonkey.error.BadObjVersionError;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.ValidationError;
import com.codemonkey.security.Operation;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.ResourceUtils;
import com.codemonkey.web.converter.CustomConversionService;

@Transactional
public abstract class GenericServiceImpl<T extends IEntity> extends AbsService
		implements GenericService<T> {

	@Autowired private ResourceUtils resourceUtils;
	
	private GenericDao<T> dao;

	private Class<?> type;

	@Override
	public abstract T createEntity();

	protected SyncService getSyncService() {
		return null;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		dao = new GenericDao<T>();
		dao.setSessionFactory(sessionFactory);
		dao.setType(getType());
	}

	protected GenericDao<T> getDao() {
		return dao;
	}

	public void save(T entity) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		if (entity.isOptimisticLockingFailure()) {
			T t = get(entity.getId());
			t.setOriginVersion(null);
			throw new BadObjVersionError(t.detailJson());
		}

		entity.setOriginVersion(null);

		Set<FieldValidation> set = validate(entity);
		if (CollectionUtils.isNotEmpty(set)) {
			throw new ValidationError(set);
		}

		getDao().save(entity);

		SyncService srv = getSyncService();

		if (srv != null) {
			srv.dosync(entity, Operation.UPDATE);
		}

		stopWatch.stop();
		getLog().info(stopWatch);

	}

	// implements by subclass if needed
	// if validation failed , throw ValidationError exception
	protected Set<FieldValidation> validate(T entity) {
		Set<FieldValidation> errorSet = new HashSet<FieldValidation>();
		if (entity == null) {
			return errorSet;
		}

		if (StringUtils.isNotBlank(entity.getCode())) {
			if (!isUnique(entity, "code", entity.getCode())) {
				errorSet.add(new FormFieldValidation("code", resourceUtils.msg("code")));
			}
		}

		// List<Field> fields = ClassHelper.getAllFields(type);
		// if(CollectionUtils.isNotEmpty(fields)){
		// for(Field f : fields){
		// if(f.getAnnotation(Length.class) != null){
		// Length len = f.getAnnotation(Length.class);
		//
		// String value = OgnlUtils.stringValue(f.getName(), entity);
		// if(value.length() > len.max()){
		// errorSet.add(new FormFieldValidation(f.getName() , "数据超过最大长度:" +
		// len.max()));
		// }
		//
		// }
		// }
		// }

		return errorSet;
	}

	public T convert(String source) {

		if (StringUtils.isEmpty(source)) {
			return null;
		}

		return getDao().get(Long.valueOf(source));
	}

	@Override
	public void doDelete(List<Long> list) {
		if(CollectionUtils.isNotEmpty(list)){
			for(Long id : list){
				delete(id);
			}
		}
	}

	public T doSave(JSONObject body, CustomConversionService ccService) {
		validateInput(body);
		T t = buildEntity(body, ccService);
		save(t);
		return t;
	}

	public boolean isUnique(T t, String query, Object... params) {

		long count = countBy(query, params);

		if (t.isNew()) {
			if (count > 0) {
				return false;
			}
		} else {
			if (count > 1) {
				return false;
			}
		}
		return true;
	}

	private void validateInput(JSONObject body) {
		Set<FieldValidation> errorSet = validateJson(body);
		errorSet.remove(null);
		if (CollectionUtils.isNotEmpty(errorSet)) {
			throw new ValidationError(errorSet);
		}
	}

	protected Set<FieldValidation> validateJson(JSONObject body) {
		return new HashSet<FieldValidation>();
	}

	public T buildEntity(JSONObject params, CustomConversionService ccService) {
		T t = null;
		Long id = extractId(params);

		if (id == null) {
			t = createEntity();
			ClassHelper.build(params, t, ccService);
		} else {
			t = get(id);
			if (t != null) {
				ClassHelper.build(params, t, ccService);
			}
		}
		return t;
	}

	private Long extractId(JSONObject params) {
		Long id = null;
		if (params.has(ExtConstant.ID)
				&& StringUtils.isNotBlank(params.getString(ExtConstant.ID))
				&& !"null".equals(params.getString(ExtConstant.ID))) {
			id = params.getLong(ExtConstant.ID);
		}
		return id;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}
