package com.codemonkey.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.json.JSONObject;

import com.codemonkey.security.RequestType;

@Entity
@DiscriminatorColumn
@DiscriminatorValue("URL_PERMISSION")
public class UrlPermission extends AppPermission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String componentId;
	
	private String url;
	
	@Enumerated(EnumType.STRING)
	private RequestType requestType;
	
	public UrlPermission(String permission , String componentId , String url , RequestType requestType){
		super(permission);
		this.componentId = componentId;
		this.url = url;
		this.setRequestType(requestType);
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("permission", getPermission());
		return jo;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
