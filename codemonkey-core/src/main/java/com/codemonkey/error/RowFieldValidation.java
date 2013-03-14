package com.codemonkey.error;

import org.json.JSONObject;



public class RowFieldValidation extends FieldValidation{

	private Integer rowId;
	
	public RowFieldValidation(String fieldName, String message , Integer rowId) {
		super(fieldName, message);
		this.rowId = rowId;
	}

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	
	public JSONObject json() {
		JSONObject jo = super.json();
		jo.put("rowId", getRowId());
		return jo;
	}
	
}
