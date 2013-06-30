package com.codemonkey.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.error.AuthError;
import com.codemonkey.error.BadObjVersionError;
import com.codemonkey.error.SessionTimeoutError;
import com.codemonkey.error.SysError;
import com.codemonkey.error.ValidationError;


public abstract class AbsController {
	
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
}
