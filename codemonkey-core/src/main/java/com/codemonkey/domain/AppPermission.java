package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Audited
public class AppPermission extends AbsMM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("权限编码")
	private String permission;
	
	@Label("控件ID")
	private String componentId;
	
	AppPermission(){}
	
	public AppPermission(String permission , String description){
		this.permission = permission;
		if(StringUtils.isNotBlank(permission)){
			this.componentId = permission.replace(':', '_');
		}
		this.setDescription(description);
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("permission", getPermission());
		return jo;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

}
