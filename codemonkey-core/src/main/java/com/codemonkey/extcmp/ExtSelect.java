package com.codemonkey.extcmp;

import org.json.JSONObject;

import com.codemonkey.utils.MMHelper;


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
		jo.put("data", MMHelper.getEnmuDataByClazz(enumClazz));
		return jo;
		
	}	
	
}
