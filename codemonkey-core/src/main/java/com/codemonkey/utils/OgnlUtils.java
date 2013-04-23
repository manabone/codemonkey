package com.codemonkey.utils;

import java.util.Date;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public final class OgnlUtils {

	private OgnlUtils(){}
	
	public static String stringValue(String expressionString , Object obj){
		Object value = objectValue(expressionString , obj);
		if(value != null){
			
			if(value instanceof Date){
				return SysUtils.formatDate((Date) value);
			}
			
			if(value instanceof Double){
				return SysUtils.formatDouble((Double) value);
			}
			
			return value.toString();
		}
		return "";
	}
	
	public static Object objectValue(String expressionString , Object obj){
	    Object value = null;
		try {
			Object expr = Ognl.parseExpression(expressionString);
			OgnlContext ctx = new OgnlContext();
			value = Ognl.getValue(expr, ctx, obj);
		} catch (OgnlException e) {
			return null;
		}
	    return value;
	}
	
	
}
