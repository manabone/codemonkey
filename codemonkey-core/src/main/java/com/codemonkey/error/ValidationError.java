package com.codemonkey.error;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.utils.ExtConstant;


public class ValidationError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Set<FieldValidation> errorSet = new HashSet<FieldValidation>();
	
	public ValidationError(String detailMsg) {
		super(detailMsg);
	}
	
	public ValidationError(Set<FieldValidation> set) {
		super("");
		this.errorSet = set;
	}
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		if(CollectionUtils.isNotEmpty(errorSet)){
			JSONArray errors = new JSONArray();
			for(FieldValidation fv : errorSet){
				errors.put(fv.json());
			}
			jo.put(ExtConstant.ERROR_FIELDS , errors);
		}
		
		return jo;
	}

	public String getDetailMessage() {
		StringBuffer buffer = new StringBuffer("validateion error : ");
		buffer.append("<br>");
		
		if(StringUtils.isNotBlank(super.getDetailMessage())){
			buffer.append(super.getDetailMessage());
			return buffer.toString();
		}
		
		if(CollectionUtils.isNotEmpty(errorSet)){
			for(FieldValidation fv : errorSet){
				buffer.append(fv.getFieldName());
				buffer.append(" : ");
				buffer.append(fv.getMessage());
				buffer.append("<br>");
			}
		}
		
		return buffer.toString();
	}
	
}
