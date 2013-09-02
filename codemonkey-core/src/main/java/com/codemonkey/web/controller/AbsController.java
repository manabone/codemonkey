package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.PowerTree;
import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.error.AuthError;
import com.codemonkey.error.BadObjVersionError;
import com.codemonkey.error.SessionTimeoutError;
import com.codemonkey.error.SysError;
import com.codemonkey.error.ValidationError;


public abstract class AbsController implements SecurityController {
	
	public static final String LOCALE = "locale";
	
	public static final String DEFAULT_LOCALE = "zh_CN";
	
	@ExceptionHandler(SysError.class)
	@ResponseBody
	public String handleSysException(SysError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(AuthError.class)
	@ResponseBody
	public String handleAuthException(AuthError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(ValidationError.class)
	@ResponseBody
	public String handleValidationException(ValidationError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(BadObjVersionError.class)
	@ResponseBody
	public String handleBadVersionException(BadObjVersionError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@ExceptionHandler(SessionTimeoutError.class)
	@ResponseBody
	public String handleSessionTimeout(SessionTimeoutError ex, HttpServletRequest request) {
		return ex.json().toString();
	}
	
	@Override
	public List<AppPermission> regAppPermissions() {
		return new ArrayList<AppPermission>();
	}
	
	@Override
	public List<SecurityComponent> regSecurityComponents() {
		return new ArrayList<SecurityComponent>();
	}
	
	@Override
	public List<PowerTree> regPowerTrees() {
		return new ArrayList<PowerTree>();
	}
}
