package com.codemonkey.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
public class AppUser extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	@NotNull
	private String password;
	
	private String salt;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<AppRole> roles = new ArrayList<AppRole>();

	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private AppUserGroup appUserGroup;
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("username", OgnlUtils.stringValue("username", this));
		jo.put("password", OgnlUtils.stringValue("password", this));
		jo.put("firstName", OgnlUtils.stringValue("firstName", this));
		jo.put("lastName", OgnlUtils.stringValue("lastName", this));
		jo.put("email", OgnlUtils.stringValue("email", this));
		jo.put("status", OgnlUtils.stringValue("status", this));
		jo.put("appUserGroup", OgnlUtils.stringValue("appUserGroup", this));
		return jo;
	}
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(roles)){
			for(AppRole role : roles){
				ja.put(role.listJson());
			}
		}
		jo.put("appRoles", ja);
		return jo;
	}
	
	public Set<AppPermission> getPermissions(){
		Set<AppPermission> permissions = new HashSet<AppPermission>();
		if(CollectionUtils.isEmpty(getRoles())){
			return permissions;
		}
		
		Set<AppRole> roles = getRoles();
		if(roles == null){
			roles = new HashSet<AppRole>();
		}
		
		if(getAppUserGroup() != null && CollectionUtils.isNotEmpty(getAppUserGroup().getRoles())){
			roles.addAll(getAppUserGroup().getRoles());
		}
		
		for(AppRole role : getRoles()){
			if(CollectionUtils.isNotEmpty(role.getAppPermissions())){
				permissions.addAll(role.getAppPermissions());
			}
		}
		
		return permissions;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
		setCode(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<AppRole> getRoles() {
		
		Set<AppRole> roles = new HashSet<AppRole>();
		if(CollectionUtils.isNotEmpty(this.roles)){
			roles.addAll(roles);
		}
		
		if(this.getAppUserGroup() != null && CollectionUtils.isNotEmpty(this.getAppUserGroup().getRoles())){
			roles.addAll(this.getAppUserGroup().getRoles());
		}
		
		return roles;
	}

	public void setRoles(List<AppRole> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AppUserGroup getAppUserGroup() {
		return appUserGroup;
	}

	public void setAppUserGroup(AppUserGroup appUserGroup) {
		this.appUserGroup = appUserGroup;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public void addAppRole(AppRole appRole1) {
		roles.add(appRole1);
	}

	public void clearAppRoles() {
		if(roles != null){
			roles.clear();
		}
	}

	public boolean isAdmin() {
		if("admin".equals(getUsername())){
			return true;
		}
		return false;
	}
}
