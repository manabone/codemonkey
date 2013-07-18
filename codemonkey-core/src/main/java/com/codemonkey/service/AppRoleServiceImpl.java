package com.codemonkey.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.CmpPermission;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public class AppRoleServiceImpl extends GenericServiceImpl<AppRole> implements AppRoleService{

	@Autowired private CmpPermissionService cmpPermissionService;
	
	@Override
	public AppRole buildEntity(JSONObject params , CustomConversionService ccService){
		AppRole appRole = super.buildEntity(params , ccService);
		
		appRole.clearUrlPermissions();
		JsonArrayConverter<UrlPermission> urlPermissionConverter = new JsonArrayConverter<UrlPermission>();
		List<UrlPermission> permissions = urlPermissionConverter.convert(params , "urlPermissions" , UrlPermission.class , ccService);
		for(UrlPermission urlPermission : permissions){
			appRole.addUrlPermission(urlPermission);
		}
		
		appRole.clearCmpPermissions();
		JsonArrayConverter<CmpPermission> cmpPermissionConverter = new JsonArrayConverter<CmpPermission>();
		List<CmpPermission> cmpPermissions = cmpPermissionConverter.convert(params , "cmpPermissions" , CmpPermission.class , ccService);
		for(CmpPermission cmpPermission : cmpPermissions){
			cmpPermissionService.save(cmpPermission);
			appRole.addCmpPermission(cmpPermission);
		}
		return appRole;
	}
	
	@Override
	public AppRole createEntity() {
		return new AppRole();
	}

}
