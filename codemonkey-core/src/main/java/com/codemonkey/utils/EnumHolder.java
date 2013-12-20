package com.codemonkey.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
/**
 * 类描述：enum保持类
 */
@Component
public class EnumHolder {

	private static Set<Class<?>> set = new HashSet<Class<?>>();
	
	public Set<Class<?>> getSet() {
		return set;
	}
	
	public void addEnum(Class<?> e){
		if(set == null){
			set = new HashSet<Class<?>>();
		}
		set.add(e);
	}

	public Class<?> getByClassName(String className) {
		if(CollectionUtils.isNotEmpty(set)){
			for(Class<?> e : set){
				if(e.getClass().getSimpleName().equals(className)){
					return e;
				}
			}
		}
		return null;
	}
	
	
}
