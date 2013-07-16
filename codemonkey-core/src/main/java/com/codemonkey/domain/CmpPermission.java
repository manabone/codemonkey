package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@DiscriminatorValue("CMP_PERMISSION")
@Audited
@Label("控件可见性")
public class CmpPermission extends AppPermission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	@Label("控件类型")
	private CmpType cmpType;
	
	@Enumerated(EnumType.STRING)
	@Label("操作权限")
	private CmpPermissionType cmpPermissionType;
	
	public CmpPermission(){}
	
	public CmpPermission(String permission , CmpType cmpType , CmpPermissionType cmpPermissionType , String description){
		super(permission , description);
		this.setCmpType(cmpType);
		this.setCmpPermissionType(cmpPermissionType);
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("cmpType", OgnlUtils.stringValue("cmpType", this));
		jo.put("cmpPermissionType", OgnlUtils.stringValue("cmpPermissionType", this));
		return jo;
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
