package com.codemonkey.extcmp;

import org.json.JSONObject;

public class ExtPopup extends ExtFormField {

	private String model;
	
	public ExtPopup(String id , String model) {
		super(id);
		setXtype(ExtCmp.POP_UP);
		this.model = model;
	}
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		JSONObject config = new JSONObject();
		config.put("model", model);
		jo.put("config", config);
		return jo;
	}
	
}
