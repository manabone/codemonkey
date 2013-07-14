package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import org.apache.shiro.util.CollectionUtils;
import org.hibernate.envers.Audited;
import org.json.JSONObject;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Audited
public class AppPermission extends AbsMM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String permission;
	
	@ManyToMany(mappedBy="appPermissions" , fetch=FetchType.EAGER)
	private Set<AppRole> appRoles = new HashSet<AppRole>();
	
	AppPermission(){}
	
	public AppPermission(String permission){
		this.permission = permission;
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

}
