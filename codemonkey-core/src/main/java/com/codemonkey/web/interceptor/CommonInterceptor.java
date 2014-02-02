package com.codemonkey.web.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		setModule(request);
		
		syncToSysUtils(request);
		
		return true;
	}

	private void syncToSysUtils(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Enumeration<?> enumeration = session.getAttributeNames();
		while(enumeration.hasMoreElements()) {
			String name = (String)enumeration.nextElement();
			SysUtils.putAttribute(name , session.getAttribute(name));
		}
	}

	private void setModule(HttpServletRequest request) {
		String param = request.getParameter(ExtConstant.CURRENT_MODULE);
		HttpSession session = request.getSession();
		if(StringUtils.isNotBlank(param)){
			session.setAttribute(ExtConstant.CURRENT_MODULE, param);
		}
	}

	private void setLocale(HttpServletRequest request) {
		String param = request.getParameter(ExtConstant.LOCALE);
		HttpSession session = request.getSession();
		
		if(StringUtils.isBlank(param)){
			param = ExtConstant.DEFAULT_LOCALE;
		}
		
		session.setAttribute(ExtConstant.LOCALE, ExtConstant.DEFAULT_LOCALE);
	}

	private void setTheme(HttpServletRequest request) {
		String theme = request.getParameter(ExtConstant.THEME);
		if(StringUtils.isBlank(theme)){
			theme = ExtConstant.DEFAULT_THEME;
		}
		request.getSession().setAttribute(ExtConstant.THEME, theme);
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
		log.info(">>>>>>>>>>>>>>>>>>>>>>>> request >>>>>>>>>>>>>>>>>>>>>>>>");
		log.info(request.getRequestURI());
		Enumeration<?> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			log.info(name + "  =:=  " + request.getParameter(name));
		}
		log.info("<<<<<<<<<<<<<<<<<<<<<<<< request <<<<<<<<<<<<<<<<<<<<<<<<");
		
		log.info(">>>>>>>>>>>>>>>>>>>>>>>> session >>>>>>>>>>>>>>>>>>>>>>>>");
		HttpSession session = request.getSession();
		enumeration = session.getAttributeNames();
		while(enumeration.hasMoreElements()) {
			String name = (String)enumeration.nextElement();
			log.info(name  + "  =:=  " + session.getAttribute(name));
		}
		log.info("<<<<<<<<<<<<<<<<<<<<<<<< session <<<<<<<<<<<<<<<<<<<<<<<<");
	}

}
