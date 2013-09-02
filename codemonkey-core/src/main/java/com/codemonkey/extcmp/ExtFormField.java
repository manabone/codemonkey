package com.codemonkey.extcmp;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public abstract class ExtFormField extends ExtCmp {

	private String name;
	
	private String fieldLable;
	
	private String value;

	public ExtFormField(String name , String lable) {
		super("form_" + name);
		this.name = name;
		this.fieldLable = lable;
	}
	
	public JSONObject json(){
		JSONObject jo = super.json();
		
		if(StringUtils.isNotBlank(name)){
			jo.put("name", name);
		}
		
		if(StringUtils.isNotBlank(getFieldLable())){
			jo.put("fieldLabel", getFieldLable());
		}
		
		return jo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFieldLable() {
		return fieldLable;
	}

	public void setFieldLable(String fieldLable) {
		this.fieldLable = fieldLable;
	}
	
	
}
