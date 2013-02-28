package com.codemonkey.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppUserService;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/auth/**")
public class AuthController {

	private static final String LOGOUT = "logout";

	private static final String LOGIN = "login";

	private static final String UNAUTHORIZED = "unauthorized";

	private static final String HOME_PAGE = "desktop/desktop";
	
	private static final String HOME = "home";

	private static final String SIGNUP = "signup";

	@Autowired private AppUserService appUserService;
	
	private Logger log = SysUtils.getLog(AuthController.class);
	
	@RequestMapping(SIGNUP)
    public String signup() {
        return SIGNUP;
    }
	
	@RequestMapping(HOME)
    public String home() {
        return HOME_PAGE;
    }
	
	@RequestMapping(UNAUTHORIZED)
    public String unauthorized() {
        return UNAUTHORIZED;
    }
	
	@RequestMapping(LOGIN)
	public String login(@RequestParam(required = false) String username , @RequestParam(required = false) String password , HttpServletRequest req){
        
		log.debug(username + ": " + password);
		
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ){
			 return SIGNUP;
		}
		
    	boolean flag = appUserService.login(username , password);
    	
    	if(!flag){
    		 return SIGNUP;
    	}
    	
    	AppUser user = appUserService.findBy("username", username);
    	req.getSession().setAttribute(SysUtils.CURRENCT_USER, user);
    	
		return "redirect:home";
	}
	
	@RequestMapping(LOGOUT)
	public String logout(){
		SecurityUtils.getSubject().logout();
		return "redirect:signup";
	}
	
	@ModelAttribute("module")
    public String getFromURI(@RequestParam(value="module", required=false) String module, HttpServletRequest request) {
    	return StringUtils.isNotEmpty(module) ? module : "erp";
    }
}
