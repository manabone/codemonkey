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
import org.hibernate.envers.Audited;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Audited
public class AppRole extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_role_url_permissions", 
		joinColumns={@JoinColumn(name="app_role")},
		inverseJoinColumns={@JoinColumn(name="url_permissions")})
	private Set<UrlPermission> urlPermissions = new HashSet<UrlPermission>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_role_cmp_permissions", 
		joinColumns={@JoinColumn(name="app_role")},
		inverseJoinColumns={@JoinColumn(name="cmp_permissions")})
	private Set<CmpPermission> cmpPermissions = new HashSet<CmpPermission>();
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		JSONArray urlPermissionsJa = new JSONArray();
		if(CollectionUtils.isNotEmpty(urlPermissions)){
			for(AppPermission p : urlPermissions){
				urlPermissionsJa.put(p.detailJson());
			}
		}
		jo.put("urlPermissions", urlPermissionsJa);
		
		JSONArray cmpPermissionsJa = new JSONArray();
		if(CollectionUtils.isNotEmpty(cmpPermissions)){
			for(AppPermission p : cmpPermissions){
				cmpPermissionsJa.put(p.detailJson());
			}
		}
		jo.put("cmpPermissions", cmpPermissionsJa);
		return jo;
	}
	
	public Set<String> getPermissions() {
		Set<String> permissions = new HashSet<String>();
		if(urlPermissions == null || urlPermissions.isEmpty()) {
			return permissions;
		}
		
		Iterator<UrlPermission> it = urlPermissions.iterator();
		while(it.hasNext()){
			UrlPermission ap = it.next();
			permissions.add(ap.getPermission());
		}
		return permissions;
	}
	
	public Set<UrlPermission> getUrlPermissions() {
		return urlPermissions;
	}

	public Set<AppPermission> getAppPermissions() {
		return null;
	}

	public void clearAppPermissions() {
		
	}

	public void addAppPermission(AppPermission appPermission) {
		
	}
	
}
