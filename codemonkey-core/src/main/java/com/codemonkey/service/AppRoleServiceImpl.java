package com.codemonkey.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppRole;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public class AppRoleServiceImpl extends GenericServiceImpl<AppRole> implements AppRoleService{

	@Override
	public AppRole buildEntity(JSONObject params , CustomConversionService ccService){
		AppRole appRole = super.buildEntity(params , ccService);
		
		appRole.clearAppPermissions();
		JsonArrayConverter<AppPermission> appRolesConverter = new JsonArrayConverter<AppPermission>();
		List<AppPermission> permissions = appRolesConverter.convert(params , "appPermissions" , AppPermission.class , ccService);
		for(AppPermission appPermission : permissions){
			appRole.addAppPermission(appPermission);
		}
		return appRole;
	}
	
	@Override
	public AppRole createEntity() {
		return new AppRole();
	}
}
