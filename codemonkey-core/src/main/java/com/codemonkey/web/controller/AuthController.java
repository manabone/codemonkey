package com.codemonkey.web.controller;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.CmpPermission;
import com.codemonkey.domain.PowerTree;
import com.codemonkey.service.AppUserService;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/auth/**")
public class AuthController {

	private static final String MODULE = "module";

	private static final String LOGOUT = "logout";

	private static final String LOGIN = "login";

	private static final String UNAUTHORIZED = "unauthorized";

	private static final String HOME_PAGE = "desktop/desktop";
	
	private static final String HOME = "home";

	private static final String SIGNUP = "signup";
	
	@Autowired private AppUserService appUserService;
	
	private Logger log = SysUtils.getLog(AuthController.class);
	
	@RequestMapping(SIGNUP)
    public String signup(ModelMap map , @RequestParam(required = false) String username , @RequestParam(required = false) String password) {
        
		if(StringUtils.isNotBlank(username)){
			map.put("username", username);
		}
		if(StringUtils.isNotBlank(password)){
			map.put("password", password);
		}
		return SIGNUP;
    }
	
	@RequestMapping(HOME)
    public String home(HttpSession session , ModelMap map) {

		JSONObject pageData = new JSONObject();
		JSONArray cmpPermissions = new JSONArray();
		JSONArray powerTrees = new JSONArray();
		
		AppUser user = (AppUser) session.getAttribute(SysUtils.CURRENCT_USER);
		
		Set<AppRole> roles = user.getRoles();
		Set<Long> powerTreeIdSet = new HashSet<Long>();
		
		if(CollectionUtils.isNotEmpty(roles)){
			for(AppRole role : roles){
				Set<CmpPermission> cmpSet = role.getCmpPermissions();
				if(CollectionUtils.isNotEmpty(cmpSet)){
					for(CmpPermission cmp : cmpSet){
						cmpPermissions.put(cmp.listJson());
					}
				}
				
				
				Set<PowerTree> powerTreesSet = role.getPowerTrees();
				if(CollectionUtils.isNotEmpty(powerTreesSet)){
					for(PowerTree p : powerTreesSet){
						if(powerTreeIdSet.add(p.getId())){
							powerTrees.put(p.listJson());
						}
					}
				}
			}
		}
		
		pageData.put(ExtConstant.CMP_PERMISSIONS, cmpPermissions);
		pageData.put(ExtConstant.POWER_TREES, powerTrees);
		pageData.put(ExtConstant.CURRENT_USER_ID, user.getId());
		map.put(ExtConstant.PAGE_DATA, pageData);
		
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
	
	@ModelAttribute(MODULE)
    public String getFromURI(@RequestParam(value=MODULE, required=false) String module, HttpServletRequest request) {
    	return StringUtils.isNotEmpty(module) ? module : "erp";
    }
	
	@ModelAttribute(AbsController.LOCALE)
    public String locale(Locale locale) {
		if(locale == null){
			return AbsController.DEFAULT_LOCALE;
		}
    	return locale.toString();
    }
	
}
