package com.codemonkey.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

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

	private static final String EQUAL = "_equal";

	private static final String NOT_EQUAL = "_notEqual";

	private static final String FROM = "from ";
	
	private HqlHelper(){}
	
	
	/**
	 * 
	 * @param type
	 * @param findbyQuery
	 * @return
	 */
	public static String findyBy(Class<?> type , String findbyQuery){
		String entityName =  type.getName();
		
		if (findbyQuery == null || findbyQuery.trim().length() == 0) {
            return FROM + entityName;
        }
        return FROM + entityName + " where 1=1 " + findByToJPQL(findbyQuery);
	}
	
	public static String countBy(Class<?> type , String findbyQuery){
		String hql = findyBy(type , findbyQuery);
		return "SELECT COUNT(*) " + hql;
	}
	
	public static String findByQueryInfo(Class<?> type , JSONObject queryInfo){
		String findBy = queryInfoToFindBy(type , queryInfo);
		return findyBy(type , findBy);
	}
	
	public static String countByQueryInfo(Class<?> type , JSONObject queryInfo){
		String findBy = queryInfoToFindBy(type , queryInfo);
		return countBy(type , findBy);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> extractParamsFromQueryInfo(Class<?> type , JSONObject queryInfo){
		List<Object> params = new ArrayList<Object>();
		if(queryInfo == null){
			return null;
		}
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
			String prop = key;
			if(key.indexOf('_') > 0){
				prop = key.split("_")[0];
			}
			
			String value = queryInfo.getString(key);
			if(StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)){

				Field field = null;
						
				if(prop.indexOf('.') < 0){
					field = ClassHelper.getFieldFromClazz(type , prop);
				}else{
					field = ClassHelper.getFieldFromClazz(type , prop.substring(0 , prop.indexOf('.')));
					field = ClassHelper.getFieldFromClazz(field.getType() , prop.substring(prop.indexOf('.')+1));
				}
				
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
			}
		}
		return params;
	}
	
	
	//---------------------------------------//
	//             private functions         //
	//---------------------------------------//
	
	@SuppressWarnings("unchecked")
	private static String queryInfoToFindBy(Class<?> type , JSONObject queryInfo){
		StringBuffer buffer = new StringBuffer();
		if(queryInfo == null){
			return null;
		}
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
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
	
}