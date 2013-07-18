package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

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
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("cmpType",  OgnlUtils.stringValue("cmpType", this));
		return jo;
	}

	public CmpType getCmpType() {
		return cmpType;
	}

	public void setCmpType(CmpType cmpType) {
		this.cmpType = cmpType;
	}

}
