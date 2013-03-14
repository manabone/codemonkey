package com.codemonkey.error;

import javax.validation.ConstraintViolation;

import org.json.JSONObject;


public class FieldValidation{

	public static final String NOT_DRAFT = " is not draft ";

	public static final String LESS_THAN_ZERO = " is less than 0 ";

	public static String EMPTY = " is empty ";
	
	private String fieldName;
	
	private String message;
	
	public FieldValidation(String fieldName , String message){
		this.setFieldName(fieldName);
		this.setMessage(message);
	}

	public FieldValidation(ConstraintViolation<?> c) {
		this(c.getPropertyPath().toString() , c.getMessage());
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONObject json() {
		JSONObject jo = new JSONObject();
		jo.put(getFieldName(), getMessage());
		jo.put("fieldName" , getFieldName());
		jo.put("message" , getMessage());
		return jo;
	}
	
}
