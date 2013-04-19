package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.shiro.util.CollectionUtils;
import org.json.JSONObject;

import com.codemonkey.security.RequestType;

@Entity
@Table
public class AppPermission extends AbsMM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String permission;
	
	private String url;
	
	@Enumerated(EnumType.STRING)
	private RequestType requestType;
	
	@ManyToMany(mappedBy="appPermissions" , fetch=FetchType.EAGER)
	private Set<AppRole> appRoles = new HashSet<AppRole>();
	
	AppPermission(){}
	
	public AppPermission(String permission , String url , RequestType requestType){
		this.permission = permission;
		this.url = url;
		this.requestType = requestType;
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("permission", getPermission());
		jo.put("url", getUrl());
		return jo;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<AppRole> getAppRoles() {
		return appRoles;
	}

	public void setAppRoles(Set<AppRole> appRoles) {
		this.appRoles = appRoles;
	}
	
	public Set<String> getAllRoleNames(){
		Set<String> set = new HashSet<String>();
		if(CollectionUtils.isEmpty(getAppRoles())){
			return set;
		}
		
		for(AppRole appRole : getAppRoles()){
			set.add(appRole.getName());
		}
		return set;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

}
