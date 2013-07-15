package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

@Entity
@DiscriminatorValue("CMP_PERMISSION")
@Audited
public class CmpPermission extends AppPermission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String componentId;
	
	private CmpType cmpType;
	
	private CmpPermissionType cmpPermissionType;
	
	public CmpPermission(){}
	
	public CmpPermission(String permission , String componentId , CmpType cmpType , CmpPermissionType cmpPermissionType){
		super(permission);
		this.setCmpType(cmpType);
		this.setCmpPermissionType(cmpPermissionType);
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("permission", getPermission());
		return jo;
	}
	
	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public CmpType getCmpType() {
		return cmpType;
	}

	public void setCmpType(CmpType cmpType) {
		this.cmpType = cmpType;
	}

	public CmpPermissionType getCmpPermissionType() {
		return cmpPermissionType;
	}

	public void setCmpPermissionType(CmpPermissionType cmpPermissionType) {
		this.cmpPermissionType = cmpPermissionType;
	}

}
