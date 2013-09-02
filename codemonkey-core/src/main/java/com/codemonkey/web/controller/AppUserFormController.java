package com.codemonkey.web.controller;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppUserService;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/ext/appUser/**")
public class AppUserFormController extends AbsFormExtController<AppUser>{

	@Autowired private AppUserService appUserService;
	
	@Override
	protected AppUserService service() {
		return appUserService;
	}
	
	@RequestMapping("changePassword")
	@ResponseBody
	public String changePassword(@RequestBody String body){
			
		JSONObject params = parseJson(body);
		
		if(!params.has("id") || StringUtils.isBlank(params.getString("id"))){
			AppUser user = SysUtils.getCurrentUser();
			params.put("id", user.getId());
		}
		
		AppUser t = appUserService.doChangePassword(params , getCcService());

		return result(t);
	}
}

