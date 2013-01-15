package com.codemonkey.error;

import org.json.JSONObject;

import com.codemonkey.utils.ExtConstant;

public abstract class SysError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String detailMessage;
	
	public SysError(String detailMessage){
		this.detailMessage = detailMessage;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
	
	public String getErrorKey(){
		return this.getClass().getSimpleName();
	}
	
	public JSONObject json(){
		JSONObject jo = new JSONObject();
		jo.put(ExtConstant.SUCCESS, false);
		jo.put(ExtConstant.ERROR_KEY, getErrorKey());
		jo.put(ExtConstant.ERROR_MSG , getDetailMessage());
		return jo;
	}

}
