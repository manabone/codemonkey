package com.codemonkey.web.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppRole;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.utils.JsonArrayConverter;

@Controller
@RequestMapping("/ext/appRole/**")
public class AppRoleFormController extends AbsFormExtController<AppRole>{

	@Autowired private AppRoleService appRoleService;
	
	@Override
	protected AppRoleService service() {
		return appRoleService;
	}
	
	@Override
	protected AppRole buildEntity(JSONObject params){
		AppRole appRole = super.buildEntity(params);
		
		appRole.clearAppPermissions();
		JsonArrayConverter<AppPermission> appRolesConverter = new JsonArrayConverter<AppPermission>();
		List<AppPermission> permissions = appRolesConverter.convert(params , "appPermissions" , AppPermission.class , getCcService());
		for(AppPermission appPermission : permissions){
			appRole.addAppPermission(appPermission);
		}
		return appRole;
	}

	@Override
	protected AppRole createEntity() {
		return new AppRole();
	}
	
	@Override
	String getControllers() {
		return "AppRoleFormController";
	}

	@Override
	String getIndexView() {
		return "approleedit";
	}

}
