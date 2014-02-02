package com.codemonkey.extcmp;

import org.json.JSONObject;


public class ExtSelect extends ExtFormField {

	private Class<?> enumClazz;
	
	public ExtSelect(String name , String lable , Class<?> enumClazz) {
		super(name , lable);
		setXtype(ExtCmp.SELECT);
		this.enumClazz = enumClazz; 
	}
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		jo.put("model", enumClazz.getSimpleName());
		return jo;
		
	}	
	
}
