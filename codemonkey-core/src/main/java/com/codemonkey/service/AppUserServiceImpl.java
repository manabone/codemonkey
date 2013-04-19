package com.codemonkey.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.error.AuthError;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public class AppUserServiceImpl extends GenericServiceImpl<AppUser> implements AppUserService{

	@Autowired private AppPermissionService appPermissionService;
	
	@Override
	public void save(AppUser user) {
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = rng.nextBytes().toBase64();
		String hashedPasswordBase64 = encodePassword(user.getPassword() , salt);
		user.setPassword(hashedPasswordBase64);
		user.setSalt(salt);
		getDao().save(user);
	}
	
	@Override
	public AppUser buildEntity(JSONObject params , CustomConversionService ccService){
		AppUser appUser = super.buildEntity(params , ccService);
		
		appUser.clearAppRoles();
		JsonArrayConverter<AppRole> appRolesConverter = new JsonArrayConverter<AppRole>();
		List<AppRole> roles = appRolesConverter.convert(params , "appRoles" , AppRole.class , ccService);
		for(AppRole role : roles){
			appUser.addAppRole(role);
		}
		return appUser;
	}

	private String encodePassword(String password , String salt) {
		return new Sha256Hash(password , salt, 1024).toBase64();
	}

	public boolean login(String username, String password) {
		
		AppUser user = findBy("username" , username);
		
		if(user != null){
			String pass = encodePassword(password , user.getSalt());
			if(pass.equals(user.getPassword())){
				UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPassword());
	    		SecurityUtils.getSubject().login(token);
	    		SysUtils.putAttribute(SysUtils.CURRENCT_USER, username);
	    		return true;
	    	}
		}
		return false;
	}

	public void isAuth(String url) {
		AppPermission permission = appPermissionService.findBy("url_Like", url + "%");
		
		Subject subject = SecurityUtils.getSubject();
		
		if(permission != null && subject != null){
			if(!subject.isPermitted(permission.getPermission())){
				throw new AuthError(permission.getUrl());
			}
		}
	}

	@Override
	public AppUser createEntity() {
		return new AppUser();
	}
}
