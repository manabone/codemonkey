package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.codemonkey.annotation.Label;

@Entity
public class SecurityComponent extends AbsMM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	@Label("控件类型")
	private CmpType cmpType;
	
	SecurityComponent(){}
	
	public SecurityComponent(String code , CmpType cmpType , String description){
		this.setCode(code);
		this.setDescription(description);
		this.cmpType = cmpType;
	}

	public CmpType getCmpType() {
		return cmpType;
	}

	public void setCmpType(CmpType cmpType) {
		this.cmpType = cmpType;
	}

}
