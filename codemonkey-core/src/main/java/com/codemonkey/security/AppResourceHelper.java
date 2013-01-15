package com.codemonkey.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.utils.ExtConstant;

public final class AppResourceHelper {
	
	private AppResourceHelper(){} 
	
	public static List<AppPermission> formPermissions(Class<?> clazz){
		List<AppPermission> permissions = new ArrayList<AppPermission>();
		permissions.add(new AppPermission(formPermission(clazz , Operation.CREATE) , formUrl(clazz , Operation.CREATE)));
		permissions.add(new AppPermission(formPermission(clazz , Operation.READ) , formUrl(clazz , Operation.READ)));
		permissions.add(new AppPermission(formPermission(clazz , Operation.UPDATE) , formUrl(clazz , Operation.UPDATE)));
		return permissions;
	}

	private static String formUrl(Class<?> clazz, Operation op) {
		StringBuffer buffer = new StringBuffer(ExtConstant.APP_ROOT);
		buffer.append(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append("/");
		if(op.equals(Operation.CREATE)){
			buffer.append("index");
		}else{
			buffer.append(op.toString().toLowerCase());
		}
		return buffer.toString() ;
	}

	private static String listPermission(Class<?> clazz , Operation op) {
		StringBuffer buffer = new StringBuffer(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append("List:");
		buffer.append(op.toString().toLowerCase());
		return buffer.toString() ;
	}
	
	private static String formPermission(Class<?> clazz , Operation op) {
		StringBuffer buffer = new StringBuffer(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append(":");
		buffer.append(op.toString().toLowerCase());
		return buffer.toString() ;
	}
	
	public static List<AppPermission> listPermissions(Class<?> clazz){
		List<AppPermission> permissions = new ArrayList<AppPermission>();
		permissions.add(new AppPermission(listPermission(clazz , Operation.READ) , listUrl(clazz , Operation.READ)));
		permissions.add(new AppPermission(listPermission(clazz , Operation.EDIT) , listUrl(clazz , Operation.EDIT)));
		permissions.add(new AppPermission(listPermission(clazz , Operation.DESTROY) , listUrl(clazz , Operation.DESTROY)));
		permissions.add(new AppPermission(listPermission(clazz , Operation.LIST) , listUrl(clazz , Operation.LIST)));
		return permissions;
	}
	
	private static String listUrl(Class<?> clazz, Operation op) {
		StringBuffer buffer = new StringBuffer(ExtConstant.APP_ROOT);
		buffer.append(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append("List/");
		if(op.equals(Operation.LIST)){
			buffer.append("index");
		}else{
			buffer.append(op.toString().toLowerCase());
		}
		
		return buffer.toString() ;
	}

	public static List<AppPermission> mmPermissions(Class<?> clazz){
		List<AppPermission> permissions = new ArrayList<AppPermission>();
		permissions.add(new AppPermission(formPermission(clazz , Operation.CREATE) , mmUrl(clazz , Operation.CREATE)));
		permissions.add(new AppPermission(formPermission(clazz , Operation.UPDATE) , mmUrl(clazz , Operation.UPDATE)));
		permissions.add(new AppPermission(formPermission(clazz , Operation.DESTROY) , mmUrl(clazz , Operation.DESTROY)));
		permissions.add(new AppPermission(formPermission(clazz , Operation.READ) , mmUrl(clazz , Operation.READ)));
		return permissions;
	}

	private static String mmUrl(Class<?> clazz, Operation op) {
		StringBuffer buffer = new StringBuffer(ExtConstant.APP_ROOT);
		buffer.append(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append("/");
		buffer.append(op.toString().toLowerCase());
		return buffer.toString() ;
	}
	
}
