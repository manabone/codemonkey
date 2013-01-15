package com.codemonkey.error;

import javax.validation.ConstraintViolation;


public class FieldValidation{

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
	
}
