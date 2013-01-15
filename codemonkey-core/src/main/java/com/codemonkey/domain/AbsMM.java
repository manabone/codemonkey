package com.codemonkey.domain;

import javax.persistence.MappedSuperclass;

import org.json.JSONObject;

@MappedSuperclass
public abstract class AbsMM extends AbsEntity implements MM{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2854462006876935499L;

	public JSONObject listJson() {
		return super.json();
	}

	@Override
	public JSONObject detailJson() {
		return super.json();
	}

}
