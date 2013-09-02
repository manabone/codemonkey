package com.codemonkey.service;

import org.json.JSONObject;

import com.codemonkey.domain.AppUser;
import com.codemonkey.web.converter.CustomConversionService;

public interface AppUserService extends GenericService<AppUser>{

	boolean login(String username, String password);
	
	void isAuth(String url);
	
	AppUser doChangePassword(JSONObject body , CustomConversionService ccService);

}
