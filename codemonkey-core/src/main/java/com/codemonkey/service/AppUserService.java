package com.codemonkey.service;

import com.codemonkey.domain.AppUser;

public interface AppUserService extends GenericService<AppUser>{

	boolean login(String username, String password);
	
	void isAuth(String url);

}
