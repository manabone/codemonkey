package com.codemonkey.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.ValidationError;

public final class HqlHelper {

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

	private static final String SELECT_FROM = "SELECT E FROM ";
	
	private static final String SELECT_COUNT_FROM = "SELECT COUNT(*) FROM ";
	
	private static final String LEFT = "_LEFT";
	
	private static final String LEFT_JOIN = " LEFT JOIN ";
	
	private static final String JOINS = "JOINS";
	
	
	
	private HqlHelper(){}
	
	
	/**
	 * 
	 * @param type
	 * @param findbyQuery
	 * @return
	 */
	public static String findyBy(Class<?> type , String findbyQuery){
		return findyBy(type , findbyQuery , null);
	}
	
	public static String findyBy(Class<?> type, String query, String[] joins) {

		String entityName =  type.getName();
		
		StringBuffer buffer = new StringBuffer(SELECT_FROM).append(entityName).append(" E ");
		
		if (query == null || query.trim().length() == 0) {
            return buffer.toString();
        }
		
		if(joins != null){
			for(int i = 0 ; i < joins.length ; i++){
				
				 if (joins[i].endsWith(LEFT)) {
					 
					 String prop = extractProp(joins[i], LEFT);
					 
					 buffer.append(LEFT_JOIN);
					 buffer.append(" E.");
					 buffer.append(prop);
					 buffer.append(" as ");
					 buffer.append(prop);
				 }	 
					 
			}
		}
		
		buffer.append(" where 1=1  ");
		buffer.append(findByToJPQL(query));
		
        return buffer.toString();
		
	}
	
	public static String countBy(Class<?> type , String findbyQuery){
		return countBy(type , findbyQuery , null);
	}
	
	public static String countBy(Class<?> type , String findbyQuery, String[] joins){
		String hql = findyBy(type , findbyQuery , joins);
		return hql.replace(SELECT_FROM , SELECT_COUNT_FROM);
	}
	
	public static String findByQueryInfo(Class<?> type , JSONObject queryAndSort){
		
		String findBy = queryInfoToFindBy(queryAndSort);
		
		if(queryAndSort.has("query") && queryAndSort.getJSONObject("query").has(JOINS)){
			findBy = findyBy(type , findBy , queryAndSort.getJSONObject("query").getString(JOINS).split(","));
		}else{
			findBy = findyBy(type , findBy);
		}
		
		String orderBy = orderByQueryInfo(queryAndSort);
		
		return findBy + orderBy;
	}
	
	public static String countByQueryInfo(Class<?> type , JSONObject queryInfo){
		String findBy = queryInfoToFindBy(queryInfo);
		
		if(queryInfo.has(JOINS)){
			return countBy(type , findBy , queryInfo.getString(JOINS).split(","));
		}
		
		return countBy(type , findBy);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> extractParamsFromQueryInfo(Class<?> type , JSONObject queryAndSort){
		List<Object> params = new ArrayList<Object>();
		if(queryAndSort == null){
			return params;
		}
		
		if(!queryAndSort.has("query")){
			return params;
		}
		
		JSONObject queryInfo = queryAndSort.getJSONObject("query");
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
			String prop = key;
			if(key.indexOf('_') > 0){
				prop = key.split("_")[0];
			}
			
			if(key.equals(JOINS)){
				continue;
			}
			
			String value = queryInfo.getString(key);
			if(StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)){

				Field field = null;
						
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
		return params;
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
		
		if(!queryAndSort.has("query")){
			return null;
		}
		
		JSONObject queryInfo = queryAndSort.getJSONObject("query");
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
			
			if(key.equals(JOINS)){
				continue;
			}
			
			String value = queryInfo.getString(key);
			if(StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)){
				buffer.append(key);
				buffer.append("And");
			}
		}
		return buffer.toString();
	}
	
	private static String findByToJPQL(String query) {

		String conditions = "";
		String findBy = query;
		if(query.indexOf("OrderBy") >= 0){
			findBy = query.substring( 0 , query.indexOf("OrderBy"));
		}
		if(StringUtils.isNotBlank(findBy)){
			StringBuffer jpql = new StringBuffer();
			 
			
	        String[] parts = findBy.split("And");
	        for (int i = 0; i < parts.length; i++) {
	            String part = parts[i];
	            
	            if (i <= parts.length - 1) {
	                jpql.append(" AND ");
	            }
	            
	            if (part.endsWith(NOT_EQUAL)) {
	                String prop = extractProp(part, NOT_EQUAL);
	                jpql.append(prop);
	                jpql.append(" <> ? ");
	            } else if (part.endsWith(EQUAL)) {
	                String prop = extractProp(part, EQUAL);
	                jpql.append(prop);
	                jpql.append(" = ? ");
	            } else if (part.endsWith(IS_NOT_NULL)) {
	                String prop = extractProp(part, IS_NOT_NULL);
	                jpql.append(prop);
	                jpql.append(" is not null ");
	            } else if (part.endsWith(IS_NULL)) {
	                String prop = extractProp(part, IS_NULL);
	                jpql.append(prop);
	                jpql.append(" is null ");
	            } else if (part.endsWith(LT)) {
	                String prop = extractProp(part, LT);
	                jpql.append(prop);
	                jpql.append(" < ? ");
	            } else if (part.endsWith(LE)) {
	                String prop = extractProp(part, LE);
	                jpql.append(prop);
	                jpql.append(" <= ? ");
	            } else if (part.endsWith(GT)) {
	                String prop = extractProp(part, GT);
	                jpql.append(prop);
	                jpql.append(" > ? ");
	            } else if (part.endsWith(GE)) {
	                String prop = extractProp(part, GE);
	                jpql.append(prop);
	                jpql.append(" >= ? ");
	            } else if (part.endsWith(BETWEEN)) {
	                String prop = extractProp(part, BETWEEN);
	                jpql.append(prop);
	                jpql.append("  < ? ");
	                jpql.append(" AND ");
	                jpql.append(prop);
	                jpql.append(" > ? ");
	                
	            } else if (part.endsWith(LIKE)) {
	                String prop = extractProp(part, LIKE);
	                jpql.append(prop);
	                jpql.append(" like ? ");
	            } else if (part.endsWith(ILIKE)) {
	                String prop = extractProp(part, ILIKE);
	                jpql.append("LOWER(");
	                jpql.append(prop);
	                jpql.append(") like LOWER(?)");
	            } else {
	                String prop = extractProp(part, "");
	                jpql.append(prop);
	                jpql.append(" = ? ");
	            }
	        }
	        return jpql.toString();
		}
		
		return conditions;
	}
	
	private static String extractProp(String part, String end) {
        String prop = part.substring(0, part.length() - end.length());
        prop = (prop.charAt(0) + "").toLowerCase() + prop.substring(1);
        return prop;
    }


	public static String orderByQueryInfo(JSONObject queryInfo) {
		if(queryInfo == null){
			return null;
		}
		
		if(!queryInfo.has("sort")){
			return null;
		}
		
		StringBuffer buffer = new StringBuffer(" ORDER BY ");
		
		JSONArray sortArray = queryInfo.getJSONArray("sort");
		
		for(int i = 0 ; i < sortArray.length() ; i++){
			JSONObject sort = sortArray.getJSONObject(i);
			
			buffer.append(sort.get("property"));
			buffer.append(' ');
			buffer.append(sort.get("direction"));
			
			if(i < sortArray.length() - 1 ){
				buffer.append(" , ");
			}
		}
		
		return buffer.toString();
	}
	
}
