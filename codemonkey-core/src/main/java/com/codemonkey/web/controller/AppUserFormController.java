package com.codemonkey.web.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppUserService;
import com.codemonkey.utils.JsonArrayConverter;

@Controller
@RequestMapping("/ext/appUser/**")
public class AppUserFormController extends AbsFormExtController<AppUser>{

	@Autowired private AppUserService appUserService;
	
	@Override
	protected AppUserService service() {
		return appUserService;
	}

	@Override
	protected AppUser createEntity() {
		return new AppUser();
	}
	
	@Override
	protected AppUser buildEntity(JSONObject params){
		AppUser appUser = super.buildEntity(params);
		
		appUser.clearAppRoles();
		JsonArrayConverter<AppRole> appRolesConverter = new JsonArrayConverter<AppRole>();
		List<AppRole> roles = appRolesConverter.convert(params , "appRoles" , AppRole.class , getCcService());
		for(AppRole role : roles){
			appUser.addAppRole(role);
		}
		return appUser;
	}

	@Override
	String getControllers() {
		return "AppUserFormController";
	}

	@Override
	String getIndexView() {
		return "appuseredit";
	}

}
