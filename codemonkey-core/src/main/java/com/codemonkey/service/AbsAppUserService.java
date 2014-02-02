package com.codemonkey.service;

import org.json.JSONObject;

import com.codemonkey.domain.AbsAppUser;
import com.codemonkey.web.converter.CustomConversionService;

public interface AbsAppUserService<T extends AbsAppUser> extends GenericService<T>{

	boolean login(String username, String password);
	
	void isAuth(String url);
	
	T doChangePassword(JSONObject body , CustomConversionService ccService);

}
