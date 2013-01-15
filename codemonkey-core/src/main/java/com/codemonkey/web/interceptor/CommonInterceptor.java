package com.codemonkey.web.interceptor;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;

public class CommonInterceptor implements HandlerInterceptor {

	private Logger log = SysUtils.getLog(CommonInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		watchingRequestParameters(request);
		
		setTheme(request);
		
		setLocale(request);
		
		return true;
	}

	private void setLocale(HttpServletRequest request) {
		if(StringUtils.isNotBlank(request.getParameter(ExtConstant.LOCALE))){
			SysUtils.putAttribute(ExtConstant.LOCALE, new Locale(request.getParameter(ExtConstant.LOCALE)));
		}
	}

	private void setTheme(HttpServletRequest request) {
		String theme = request.getParameter(ExtConstant.THEME);
		if(StringUtils.isBlank(theme)){
			theme = ExtConstant.DEFAULT_THEME;
		}
		request.getSession().setAttribute(ExtConstant.THEME, theme);
		SysUtils.putAttribute(ExtConstant.THEME, theme);
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	private void watchingRequestParameters(HttpServletRequest request) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(request.getRequestURI());
		Enumeration<?> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			log.info(name + "  =:=  " + request.getParameter(name));
		}
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<");
	}

}
