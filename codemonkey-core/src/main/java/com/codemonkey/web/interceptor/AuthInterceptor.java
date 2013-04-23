package com.codemonkey.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.codemonkey.service.AppUserService;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private AppUserService appUserService;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		isAuth(request);
		
		return true;
	}

	private void isAuth(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String url = uri.replaceFirst(request.getContextPath(), "");
		appUserService.isAuth(url);
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
