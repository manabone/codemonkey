package com.codemonkey.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.UrlPermission;
//import com.codemonkey.error.AuthError;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.ValidationError;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public class AppUserServiceImpl extends PhysicalServiceImpl<AppUser> implements
		AppUserService {

	@Autowired
	private UrlPermissionService urlPermissionService;

	@Override
	public AppUser buildEntity(JSONObject params,
			CustomConversionService ccService) {
		AppUser appUser = super.buildEntity(params, ccService);

		appUser.clearAppRoles();
		JsonArrayConverter<AppRole> appRolesConverter = new JsonArrayConverter<AppRole>();
		List<AppRole> roles = appRolesConverter.convert(params, "roles",
				AppRole.class, ccService);
		for (AppRole role : roles) {
			appUser.addAppRole(role);
		}
		return appUser;
	}

	public boolean login(String username, String password) {

		AppUser user = findBy("username", username);

		if (user != null) {
			String pass = AppUser.encodePassword(password, user.getSalt());
			if (pass.equals(user.getPassword())) {
				UsernamePasswordToken token = new UsernamePasswordToken(
						username, user.getPassword());
				SecurityUtils.getSubject().login(token);
				// SysUtils.putAttribute(SysUtils.CURRENCT_USER, username);
				// SysUtils.putAttribute(SysUtils.CURRENCT_USER_LOGININFO,
				// user);
				return true;
			}
		}
		return false;
	}

	public void isAuth(String url) {
		UrlPermission permission = urlPermissionService.findBy("url_Like", url + "%");

		Subject subject = SecurityUtils.getSubject();

		if (permission != null && subject != null) {
			boolean notPermitted = !subject.isPermitted(permission.getPermission());
			if (notPermitted) {
//				throw new AuthError(permission.getUrl());
			}
		}
	}

	@Override
	public AppUser createEntity() {
		return new AppUser();
	}

	@Override
	public AppUser doChangePassword(JSONObject body,
			CustomConversionService ccService) {
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		if (body.has("password")
				&& StringUtils.isNotBlank(body.getString("password"))
				&& body.has("password_ack")) {
			String password = body.getString("password");
			String passwordAck = body.getString("password_ack");

			if (!password.equals(passwordAck)) {
				set.add(new FormFieldValidation("password", "密码不同"));
			}

		}

		if (CollectionUtils.isNotEmpty(set)) {
			throw new ValidationError(set);
		}

		AppUser user = super.buildEntity(body, ccService);
		user.changePassword(user.getPassword());
		save(user);
		return user;
	}

}
