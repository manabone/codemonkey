package com.codemonkey.web.interceptor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.security.RequestType;
import com.codemonkey.service.AppPermissionService;
import com.codemonkey.utils.ExtConstant;

@Component
@Lazy(false)
@Transactional(propagation=Propagation.REQUIRED)
public class AuthFilterServiceImpl extends AuthenticationFilter {

	@Autowired private AppPermissionService appPermissionService;
	
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
        	HttpServletRequest r = (HttpServletRequest) request;
        	String uri = r.getRequestURI();
        	String url = uri.replaceFirst(r.getContextPath(), "");
        	
    		AppPermission permission = appPermissionService.findBy("url_Like", url + "%");
        	
    		if(permission != null && RequestType.HTML.equals(permission.getRequestType())){
    			 saveRequestAndRedirectToLogin(request, response);
    		}else if(permission != null && RequestType.JSON.equals(permission.getRequestType())){
    			JSONObject jo = new JSONObject();
        		jo.put(ExtConstant.SUCCESS, false);
        		jo.put(ExtConstant.ERROR_KEY, "sessionTimeout");
        		jo.put(ExtConstant.ERROR_MSG , "session timeout , please login");
        		response.getWriter().write(jo.toString());
    		}else{
    			saveRequestAndRedirectToLogin(request, response);
    		}
    		return false;
        }
    }

}
