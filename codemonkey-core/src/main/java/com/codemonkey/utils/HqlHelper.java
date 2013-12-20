package com.codemonkey.utils;
/**
 * 类描述：hql工具类
 */
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.domain.IPopupModule;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.ValidationError;


/*
	查询后缀
 	_LE 小于等于 
 	_LT 小于
 	_GE 大于等于
 	_GT 大于
 	_Like 模糊查询
 	_isNull 为空
 	_isNotNull 不为空
 	_notEQ 不等于
 	_EQ 等于(默认)
*/

public final class HqlHelper {

	private static final String OR_REG = "\\|\\|";

	private static final String AND = "&&";
	
//	private static final String OR = "||";

	private static final String ASC = "ASC";

	private static final String DESC = "DESC";
	
	private static final String _ASC = "_ASC";

	private static final String _DESC = "_DESC";

	private static final String ORDER_BY = "OrderBy";

	private static final String ILIKE = "_Ilike";

	private static final String LIKE = "_Like";

	private static final String BETWEEN = "_Between";

	private static final String GE = "_GE";

	private static final String GT = "_GT";

	private static final String LT = "_LT";
	
	private static final String LE = "_LE";

	private static final String IS_NULL = "_isNull";

	private static final String IS_NOT_NULL = "_isNotNull";

	private static final String EQUAL = "_EQ";

	private static final String NOT_EQUAL = "_notEQ";

	public static final String SELECT_FROM = "SELECT DISTINCT E FROM ";
	
	public static final String SELECT_COUNT_FROM = "SELECT COUNT(*) FROM ";
	
	private static final String LEFT_JOIN = " LEFT JOIN ";
	
	private static final String JOINS = "JOINS";
	
	
	
	private HqlHelper(){}
	
	
	/**
	 * 
	 * @param type
	 * @param findbyQuery
	 * @return
	 */
	public static String findBy(Class<?> type , String query){
		
 		return findBy(SELECT_FROM , type , query);
	}
	
	public static String findBy(String selectFrom , Class<?> type , String query){
		
		String entityName =  type.getName();
		
		StringBuffer buffer = new StringBuffer(selectFrom).append(entityName).append(" E ");
		
		if (query == null || query.trim().length() == 0) {
            return buffer.toString();
        }
		
		List<String> joinsProps = new ArrayList<String>();
		
		buildLeftJoins(buffer , joinsProps , query , type);
		
		buffer.append(" where 1=1  ");
		
		Collections.sort(joinsProps , new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return o1.length() > o2.length() ? -1 : 1;
			}
			
		});
		
		buffer.append(findByToJPQL(query , joinsProps));
		buffer.append(orderByToJPQL(query , joinsProps));
		
        return buffer.toString();
	}
	
	
	private static void buildLeftJoins(StringBuffer buffer, List<String> joinsProps, String query , Class<?> type) {

		Set<String> props = new HashSet<String>();
		
		String[] parts = query.split(ORDER_BY);
		
		Set<String> partsSet = new HashSet<String>();
		
		for(int i = 0 ; i < parts.length ; i++){
			if(StringUtils.isNotBlank(parts[i])){
				
				String[] subParts1 = parts[i].split(AND);
				
				for(int k = 0 ; k < subParts1.length ; k++){
					if(StringUtils.isNotBlank(subParts1[k])){
						String[] subParts2 = subParts1[k].split(OR_REG);
						for(int j = 0 ; j < subParts2.length ; j++){
							if(StringUtils.isNotBlank(subParts2[j])){
								partsSet.add(subParts2[j]);
							}
						}
					}
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(partsSet)){
			for(String p : partsSet){
				props.add(extractPartFromQueryString(p));
			}
		}
			
		
		if(CollectionUtils.isNotEmpty(props)){
			for(String prop : props ){
				buildLeftJoin(prop , type , buffer , joinsProps , "E.");
			}
		}
	}

	private static void buildLeftJoin(String prop, Class<?> type , StringBuffer buffer , List<String> joinsProps , String prefix) {
		
		if(prop.indexOf('.') < 0){
			Field field = ClassHelper.getFieldFromClazz(type, prop);
			
			if(field.getAnnotation(ManyToOne.class) != null 
					|| field.getAnnotation(OneToMany.class) != null
					|| field.getAnnotation(OneToOne.class) != null
					|| field.getAnnotation(ManyToMany.class) != null){
				
				 buffer.append(LEFT_JOIN);
				 buffer.append(prefix);
				 buffer.append(prop);
				 buffer.append(" as ");
				 buffer.append(prop);
				 
				 joinsProps.add(prop);
			}
			
		}else{
			String prop0 = prop.substring(0 , prop.indexOf('.'));
			String prop1 = prop.substring(prop.indexOf('.') + 1);
			Field field1 = ClassHelper.getFieldFromClazz(type, prop0);
			Class<?> type1 = field1.getType();
			
			if(type1.equals(List.class) || type1.equals(Set.class)){
				
				if(!joinsProps.contains(prop0)){
					 buffer.append(LEFT_JOIN);
					 buffer.append(prefix);
					 buffer.append(prop0);
					 buffer.append(" as ");
					 buffer.append(prop0);
					 
					 if("E.".equals(prefix)){
						 joinsProps.add(prop0);
					 }else{
						 joinsProps.add(prefix + prop0);
					 }
					 
					
				}
			}else{
				Field field = ClassHelper.getFieldFromClazz(type, prop0);
				
				if(field.getAnnotation(ManyToOne.class) != null 
						|| field.getAnnotation(OneToMany.class) != null
						|| field.getAnnotation(OneToOne.class) != null
						|| field.getAnnotation(ManyToMany.class) != null){
					
					 buffer.append(LEFT_JOIN);
					 buffer.append(prefix);
					 buffer.append(prop0);
					 buffer.append(" as ");
					 buffer.append(prop0);
					 
					 joinsProps.add(prop0);
					 
					 buildLeftJoin(prop1 , type1 , buffer , joinsProps , prop0 + ".");
				}else{
					buildLeftJoin(prop1 , type1 , buffer , joinsProps , prop0 + ".");
				}
			}
		}
	}


	private static String extractPartFromQueryString(String queryString) {
		return StringUtils.uncapitalize(queryString.split("_")[0]);
	}
	
	private static String orderByToJPQL(String query , List<String> joinsProps) {
		
		StringBuffer jpql = new StringBuffer("");
		
		String orderBy = query;
		if(query.indexOf(ORDER_BY) >= 0){
			orderBy = query.substring(query.indexOf(ORDER_BY) + ORDER_BY.length());
			
			if(StringUtils.isNotBlank(orderBy)){
				
				jpql.append(" Order By ");
				
				String[] parts = orderBy.split(AND);
				for (int i = 0; i < parts.length; i++) {
					String part = parts[i];
			            
			        if (part.endsWith(_DESC)) {
		                String prop = extractProp(part, _DESC , joinsProps);
		                jpql.append(prop);
		                jpql.append(" ");
		                jpql.append(DESC);
		            } else if (part.endsWith(_ASC)) {
		            	String prop = extractProp(part, _ASC , joinsProps);
		                jpql.append(prop);
		                jpql.append(" ");
		                jpql.append(ASC);
	            	}
			        
			        if(i < parts.length - 1){
			        	 jpql.append(" , ");
			        }
	            }
			}
		}
		
		return jpql.toString();
	}

	public static String countBy(Class<?> type , String findbyQuery){
		String hql = findBy(type , findbyQuery);
		return  hql.replace(SELECT_FROM , SELECT_COUNT_FROM);
	}
	
	public static String findByQueryInfo(Class<?> type , JSONObject queryAndSort){
		
		String findBy = queryInfoToFindBy(queryAndSort);
		
		return findBy(type , findBy);
	}
	
	public static String countByQueryInfo(Class<?> type , JSONObject queryInfo){
		String findBy = queryInfoToFindBy(queryInfo);
		
		return countBy(type , findBy);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> extractParamsFromQueryInfo(Class<?> type , JSONObject queryAndSort){
		List<Object> params = new ArrayList<Object>();
		if(queryAndSort == null){
			return params;
		}
		
		if(!queryAndSort.has(ExtConstant.QUERY)){
			return params;
		}
		
		JSONObject queryInfo = queryAndSort.getJSONObject(ExtConstant.QUERY);
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
			if(key.equals(JOINS)){
				continue;
			}
			
			String[] orProps = key.split(OR_REG);
			if(orProps.length > 1){
				for(int i = 0 ; i < orProps.length ; i++){
					extractValue(key , orProps[i] , queryInfo , type ,  params);
				}
			}else{
				extractValue(key , orProps[0] , queryInfo , type ,  params);
			}
			
			
		}
		return params;
	}
	
	private static void extractValue(String key , String prop , JSONObject queryInfo , Class<?> type , List<Object> params){
		String value = queryInfo.getString(key);
		if(StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)){

			Field field = null;
			
			if(prop.indexOf('_') > 0){
				prop = prop.split("_")[0];
			}
					
			if(prop.indexOf('.') < 0){
				field = ClassHelper.getFieldFromClazz(type , prop);
			}else{
				Class<?> type2 = type;
				while(prop.indexOf('.') > 0){
					
					String[] p = prop.split("\\.");
					
					field = ClassHelper.getFieldFromClazz(type2 , p[0]);
					
					if(field.getType().equals(List.class) || field.getType().equals(Set.class)){
						
						ParameterizedType integerListType = (ParameterizedType) field.getGenericType();
					    Class<?> clazz = (Class<?>) integerListType.getActualTypeArguments()[0];
						
					    field = ClassHelper.getFieldFromClazz(clazz , p[1]);
					    type2 = clazz;
					}else{
						field = ClassHelper.getFieldFromClazz(field.getType() , p[1]);
					}
					prop = prop.substring(prop.indexOf('.')+1);
				}
			}
			
			try{
				
				if(field.getType().equals(String.class)){
					if(key.endsWith(LIKE) || key.endsWith(ILIKE)){
						params.add('%' + value + '%');
					}else{
						params.add(value);
					}
				}else if(field.getType().equals(Boolean.class)){
					params.add(Boolean.valueOf(value));
				}else if(field.getType().equals(Date.class)){
					params.add(SysUtils.toDate(value));
				}else if(field.getType().equals(Integer.class)){
					params.add(Integer.valueOf((value)));
				}else if(field.getType().equals(Double.class)){
					params.add(Double.valueOf((value)));
				}else if(field.getType().equals(Float.class)){
					params.add(Float.valueOf((value)));
				}else if(field.getType().equals(Long.class)){
					params.add(Long.valueOf((value)));
				}else if(field.getType().equals(Short.class)){
					params.add(Short.valueOf((value)));
				}else if(field.getType().isEnum()){
					params.add(ClassHelper.stringToEnum(field.getType() , value));
				}else{
					//entity id
					params.add(Long.valueOf((value)));
				}
			}catch(Exception e){
				Set<FieldValidation> set = new HashSet<FieldValidation>();
				set.add(new FormFieldValidation(field.getName(), "bad format"));
				if(CollectionUtils.isNotEmpty(set)){
					throw new ValidationError(set);
				}
			}
		}
	}
	
	
	//---------------------------------------//
	//             private functions         //
	//---------------------------------------//
	
	@SuppressWarnings("unchecked")
	private static String queryInfoToFindBy(JSONObject queryAndSort){
		StringBuffer buffer = new StringBuffer();
		if(queryAndSort == null){
			return null;
		}
		
		if(!queryAndSort.has(ExtConstant.QUERY)){
			return null;
		}
		
		JSONObject queryInfo = queryAndSort.getJSONObject(ExtConstant.QUERY);
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
			
			if(key.equals(JOINS)){
				continue;
			}
			
			String value = queryInfo.getString(key);
			if(StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)){
				buffer.append(key);
				buffer.append(AND);
			}
		}
		
		JSONArray sortArray = queryAndSort.optJSONArray(ExtConstant.SORT);
		
		if(sortArray != null){
			for(int i = 0 ; i < sortArray.length() ; i++){
				buffer.append(ORDER_BY);
				JSONObject sort = sortArray.getJSONObject(i);
				
				String prop = sort.getString("property");
				
				if(prop.startsWith(IPopupModule.LINK_PREFIX)){
					prop = prop.substring(IPopupModule.LINK_PREFIX.length());
				}
				
				prop = prop.replace("_", ".");
				
				buffer.append(prop);
				buffer.append('_');
				buffer.append(sort.getString("direction").toUpperCase());
				if(i < sortArray.length() - 1 ){
					buffer.append(AND);
				}
			}
		}
		return buffer.toString();
	}
	
	private static String findByToJPQL(String query , List<String> joinsProps) {

		String conditions = "";
		String findBy = query;
		if(query.indexOf(ORDER_BY) >= 0){
			findBy = query.substring( 0 , query.indexOf(ORDER_BY));
		}
		if(StringUtils.isNotBlank(findBy)){
			StringBuffer jpql = new StringBuffer();
			 
	        String[] parts = findBy.split(AND);
	        for (int i = 0; i < parts.length; i++) {
	            String part = parts[i];
	            
	            if (i <= parts.length - 1) {
	                jpql.append(" And ");
	            }
	            
	            jpql.append(extractHql(joinsProps, part));
	        }
	        return jpql.toString();
		}
		
		return conditions;
	}


	private static String extractHql(List<String> joinsProps, String part) {
		
		StringBuffer jpql = new StringBuffer();
		
		String[] orParts = part.split(OR_REG);
		if(orParts.length > 1){
			jpql.append(" ( ");
			for(int i = 0 ; i < orParts.length ; i++){
				jpql.append(extractHql(joinsProps, orParts[i]));
				if( i < orParts.length - 1){
					jpql.append(" OR ");
				}
			}
			jpql.append(" ) ");
		}else{
			if (part.endsWith(NOT_EQUAL)) {
			    String prop = extractProp(part, NOT_EQUAL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" <> ? ");
			} else if (part.endsWith(EQUAL)) {
			    String prop = extractProp(part, EQUAL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" = ? ");
			} else if (part.endsWith(IS_NOT_NULL)) {
			    String prop = extractProp(part, IS_NOT_NULL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" is not null ");
			} else if (part.endsWith(IS_NULL)) {
			    String prop = extractProp(part, IS_NULL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" is null ");
			} else if (part.endsWith(LT)) {
			    String prop = extractProp(part, LT , joinsProps);
			    jpql.append(prop);
			    jpql.append(" < ? ");
			} else if (part.endsWith(LE)) {
			    String prop = extractProp(part, LE , joinsProps);
			    jpql.append(prop);
			    jpql.append(" <= ? ");
			} else if (part.endsWith(GT)) {
			    String prop = extractProp(part, GT , joinsProps);
			    jpql.append(prop);
			    jpql.append(" > ? ");
			} else if (part.endsWith(GE)) {
			    String prop = extractProp(part, GE , joinsProps);
			    jpql.append(prop);
			    jpql.append(" >= ? ");
			} else if (part.endsWith(BETWEEN)) {
			    String prop = extractProp(part, BETWEEN , joinsProps);
			    jpql.append(prop);
			    jpql.append("  < ? ");
			    jpql.append(" AND ");
			    jpql.append(prop);
			    jpql.append(" > ? ");
			    
			} else if (part.endsWith(LIKE)) {
			    String prop = extractProp(part, LIKE , joinsProps);
			    jpql.append(prop);
			    jpql.append(" like ? ");
			} else if (part.endsWith(ILIKE)) {
			    String prop = extractProp(part, ILIKE , joinsProps);
			    jpql.append("LOWER(");
			    jpql.append(prop);
			    jpql.append(") like LOWER(?)");
			} else {
			    String prop = extractProp(part, "" , joinsProps);
			    jpql.append(prop);
			    jpql.append(" = ? ");
			}
		}
		
		return jpql.toString();
	}
	
	private static String extractProp(String part, String end , List<String> joinsProps) {
		String prop = part.substring(0, part.length() - end.length());
        prop = StringUtils.uncapitalize(prop);
        
        if(CollectionUtils.isNotEmpty(joinsProps)){
        	for(String joinsProp : joinsProps){
        		if(prop.startsWith(joinsProp + ".")){
        			String[] props = prop.split("\\.");
        			return props[props.length - 2] + "." +props[props.length - 1];
        		}
        	}
        }
        
        return "E." + prop;
    }
}
