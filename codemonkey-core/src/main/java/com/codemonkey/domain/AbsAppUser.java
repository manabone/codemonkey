package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype", discriminatorType = DiscriminatorType.STRING)
@Table(name="app_user")
@Audited
public class AbsAppUser extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String salt;
	
	private String workerMobilephone;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_user_roles", 
		joinColumns={@JoinColumn(name="app_user")},
		inverseJoinColumns={@JoinColumn(name="roles")})
	@NotAudited
	private Set<AppRole> roles = new HashSet<AppRole>();

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
		jo.put("workerMobilephone", OgnlUtils.stringValue("workerMobilephone", this));
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
		jo.put("roles", ja);
		return jo;
	}
	
	public void changePassword(String newPassword){
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = rng.nextBytes().toBase64();
		String hashedPasswordBase64 = encodePassword(newPassword , salt);
		setPassword(hashedPasswordBase64);
		setSalt(salt);
	}
	
	public static String encodePassword(String password , String salt) {
		return new Sha256Hash(password , salt, 1024).toBase64();
	}
	
	public Set<AppPermission> getPermissions(){
		Set<AppRole> appRoles = getRoles();
		Set<AppPermission> permissions = new HashSet<AppPermission>();
		if(CollectionUtils.isEmpty(appRoles)){
			return permissions;
		}
		
		if(getAppUserGroup() != null && CollectionUtils.isNotEmpty(getAppUserGroup().getRoles())){
			appRoles.addAll(getAppUserGroup().getRoles());
		}
		
		for(AppRole role : appRoles){
			if(CollectionUtils.isNotEmpty(role.getUrlPermissions())){
				permissions.addAll(role.getUrlPermissions());
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
		
		Set<AppRole> appRoles = new HashSet<AppRole>();
		if(CollectionUtils.isNotEmpty(roles)){
			appRoles.addAll(roles);
		}
		
		if(this.getAppUserGroup() != null && CollectionUtils.isNotEmpty(this.getAppUserGroup().getRoles())){
			appRoles.addAll(this.getAppUserGroup().getRoles());
		}
		
		return appRoles;
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
	
	public String getWorkerMobilephone() {
		return this.workerMobilephone;
	}
	
	public void setWorkerMobilephone(String value) {
		this.workerMobilephone = value;
	}
}
