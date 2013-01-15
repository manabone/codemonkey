package com.codemonkey.domain;

import javax.persistence.MappedSuperclass;

import org.json.JSONObject;

@MappedSuperclass
public abstract class AbsEE extends AbsEntity implements EE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public JSONObject listJson() {
		return super.json();
	}

	public JSONObject detailJson() {
		return listJson();
	}
	
}
