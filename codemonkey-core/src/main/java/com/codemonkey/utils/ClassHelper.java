package com.codemonkey.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.WebDataBinder;

import com.codemonkey.domain.AbsEntity;
import com.codemonkey.service.MMService;
import com.codemonkey.service.MMServiceHolderImpl;
import com.codemonkey.web.converter.CustomConversionService;

@Component
public final class ClassHelper {

	private static Logger logger = LoggerFactory.getLogger(ClassHelper.class);
	
	private ClassHelper(){}
	
	public static Object getFieldValue(AbsEntity entity, String fieldName) {
		
		Field field = null;
		try {
			field = getFieldFromClazz(entity.getClass() , fieldName);
			return callGetter(entity , field);
		} catch (Exception e) {
			logger.info(" call get" + StringUtils.capitalize(field.getName()) + " FAILED!!!!! ");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void callSetter(Object obj , Field field, Object value) {
		callSetter(obj , field.getName() , value);
	}
	
	public static void callSetter(Object obj, String fieldName, Object value) {
		
		Method setter = null;
		
		try{
			String setterName = "set"+ StringUtils.capitalize(fieldName);
			Field field = getFieldFromClazz(obj.getClass(), fieldName);
			if(field != null){
				setter = ClassUtils.getMethodIfAvailable(obj.getClass() , setterName , field.getType());
				
				if(setter != null && field != null){
//					logger.info(setter.getName() + " || " + value);
					setter.invoke(obj, value);
				}else{
					logger.info(setterName + " is NOT FOUND !!!");
				}
			}
			
		}catch(Exception e){
			logger.info("call "+ setter.getName() + " IN " + obj.getClass().getSimpleName() + " FAILED!!!! ");
			e.printStackTrace();
		}
	}
	
	
	public static Field getFieldFromClazz(Class<?> clazz , String fieldName) {
		while(!clazz.equals(Object.class)){
			Field[] fields = clazz.getDeclaredFields();
			if(fields != null){
				for(int i = 0 ; i < fields.length ; i++){
					if(fields[i].getName().equals(fieldName)){
						return fields[i];
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	public static Object callGetter(Object obj, Field field) {
		return callGetter(obj,field.getName());
	}
	
	public static Object callGetter(Object obj, String fieldName) {
		Method getter = null;
		Object value = null;
		if(StringUtils.isBlank(fieldName)){
			return null;
		}
		try{
			getter = ClassUtils.getMethodIfAvailable(obj.getClass() , "get"+ StringUtils.capitalize(fieldName));
			
			if(getter != null){
				value = getter.invoke(obj);
			}
			return value;
			
		}catch(Exception e){
			logger.info("call "+ getter.getName() + " FAILED!!!! ");
			e.printStackTrace();
		}
		return null;
	}

	public static Object getFieldValue(String columnName, Class<?> type, SqlRowSet rowSet) {
		Object value = rowSet.getObject(columnName);
		if(value != null && value.getClass().equals(BigDecimal.class)){
			value = ((BigDecimal)value).doubleValue();
		}
		return value;
	}
	
	public static List<String> getAllFieldNames(Class<?> type){
		List<String> list = new ArrayList<String>();
		try{
			while(!type.equals(Object.class)){
				Field[] fields = type.getDeclaredFields();
				if(fields != null){
					for(int i = 0 ; i < fields.length ; i++){
						String name = fields[i].getName();
						if(!"serialVersionUID".equals(name)){
							list.add(name);
						}
					}
				}
				type = type.getSuperclass();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Field> getAllFields(Class<?> clazz){
		List<Field> fields = new ArrayList<Field>();
		while(!clazz.equals(Object.class)){
			Field[] fs = clazz.getDeclaredFields();
			if(fs != null){
				for(int i = 0 ; i < fs.length ; i++){
					Field f = fs[i];
					if(!f.getName().equals("serialVersionUID") && f.getAnnotation(Version.class) == null){
						fields.add(f);
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return fields;
	}
	
	@SuppressWarnings("unchecked")
	public static void bulid(JSONObject params, Object model ,  WebDataBinder binder) {
		Iterator<String> it = params.keys();
		while(it.hasNext()){
			String key = it.next();
			if(key.endsWith("_text")){
				continue;
			}
			
			Field field = ReflectionUtils.findField(model.getClass(), key);
			if(field != null && !ExtConstant.ID.equals(key)){
				Object value = getValueFromJson(field , key , params, binder);
				ClassHelper.callSetter(model, field, value);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void bulid(JSONObject params, Object model ,  CustomConversionService ccService) {
		Iterator<String> it = params.keys();
		while(it.hasNext()){
			String key = it.next();
			if(key.endsWith("_text")){
				continue;
			}
			
			Field field = ReflectionUtils.findField(model.getClass(), key);
			if(field != null && !ExtConstant.ID.equals(key)){
				Object value = getValueFromJson(field , key , params, ccService);
				ClassHelper.callSetter(model, field, value);
			}
		}
	}
	
	private static Object getValueFromJson(Field field, String name, JSONObject params, CustomConversionService ccService) {
		
		if(StringUtils.isBlank(params.getString(name)) || params.getString(name).equals("null") || params.getString(name).equals("undefined")){
			return null;
		}
		
		if(field == null || field.getType().equals(List.class) || field.getType().equals(Set.class)){
			return null;
		}
		
		return convert(params.getString(name) , field.getType() , ccService);
	}
	
	private static Object getValueFromJson(Field field, String name, JSONObject params, WebDataBinder binder) {
		
		if(StringUtils.isBlank(params.getString(name)) || params.getString(name).equals("null") || params.getString(name).equals("undefined")){
			return null;
		}
		
		if(field == null || field.getType().equals(List.class) || field.getType().equals(Set.class)){
			return null;
		}
		
		return convert(params.getString(name) , field.getType() , binder);
	}
	
	public static Object convert(String id , Class<?> clazz , CustomConversionService ccService){
	
		MMService service = MMServiceHolderImpl.getHolder().get(clazz);
		if(service != null){
			return service.get(Long.valueOf(id));
		}
		return ccService.getObject().convert(id, clazz);
	}
	
	public static Object convert(String id , Class<?> clazz , WebDataBinder binder){
		
		MMService service = MMServiceHolderImpl.getHolder().get(clazz);
		if(service != null){
			return service.get(Long.valueOf(id));
		}
		return binder.convertIfNecessary(id, clazz);
	}
	
	public static Class<?> getSuperClassGenricType(Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			//logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			//logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
//			getLog(SysUtils.class).warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class<?>) params[index];
	}
	
	public static Object stringToEnum(Class<?> clazz  , String value){
		try {
			Method method = ClassUtils.getMethodIfAvailable(clazz , "valueOf" , String.class);
			if(method != null){
				return method.invoke(clazz , value);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
