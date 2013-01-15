package com.codemonkey.error;

import org.json.JSONObject;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ExtConstant;


public class BadObjVersionError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IEntity entity ;
	
	public BadObjVersionError(IEntity ee) {
		super(ee.getClass().getSimpleName() + ee.getId() + " has been modified ");
		this.setEntity(ee);
	}
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		jo.put(ExtConstant.DATA , this.getEntity().detailJson());
		return jo;
	}

	public IEntity getEntity() {
		return entity;
	}

	public void setEntity(IEntity entity) {
		this.entity = entity;
	}
	
}
