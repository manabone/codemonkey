package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
public class AppRole extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_role_app_permissions", 
		joinColumns={@JoinColumn(name="app_role")},
		inverseJoinColumns={@JoinColumn(name="app_permissions")})
	private Set<AppPermission> appPermissions = new HashSet<AppPermission>();
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(appPermissions)){
			for(AppPermission p : appPermissions){
				ja.put(p.json());
			}
		}
		jo.put("appPermissions", ja);
		return jo;
	}
	
	public Set<String> getPermissions() {
		Set<String> permissions = new HashSet<String>();
		if(appPermissions == null || appPermissions.isEmpty()) {
			return permissions;
		}
		
		Iterator<AppPermission> it = appPermissions.iterator();
		while(it.hasNext()){
			AppPermission ap = it.next();
			permissions.add(ap.getPermission());
		}
		return permissions;
	}
	
	public Set<AppPermission> getAppPermissions() {
		return appPermissions;
	}

	public void addAppPermission(AppPermission appPermission1) {
		appPermissions.add(appPermission1);
	}
	
	public void clearAppPermissions(){
		if(CollectionUtils.isEmpty(appPermissions)) {
			return;
		}
		appPermissions.clear();
	}
	
}
