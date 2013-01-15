package com.codemonkey.extcmp;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public abstract class ExtFormField extends ExtCmp {

	private String name;
	
	private String fieldLabel;
	
	private String value;

	public ExtFormField(String id) {
		super("form_" + id);
		this.name = id;
		this.fieldLabel = id;
	}
	
	public JSONObject json(){
		JSONObject jo = super.json();
		
		if(StringUtils.isNotBlank(name)){
			jo.put("name", name);
		}
		
		if(StringUtils.isNotBlank(fieldLabel)){
			jo.put("fieldLabel", fieldLabel);
		}
		
		return jo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
