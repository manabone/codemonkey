package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.codemonkey.security.RequestType;
import com.codemonkey.utils.OgnlUtils;

@Entity
@DiscriminatorValue("URL_PERMISSION")
@Audited
public class UrlPermission extends AppPermission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;
	
	@Enumerated(EnumType.STRING)
	private RequestType requestType;
	
	public UrlPermission(){}
	
	public UrlPermission(String permission , String componentId , String url , RequestType requestType){
		super(permission , componentId);
		this.url = url;
		this.setRequestType(requestType);
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("componentId", OgnlUtils.stringValue("componentId", this));
		jo.put("url", OgnlUtils.stringValue("url", this));
		jo.put("requestType", OgnlUtils.stringValue("requestType", this));
		return jo;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
