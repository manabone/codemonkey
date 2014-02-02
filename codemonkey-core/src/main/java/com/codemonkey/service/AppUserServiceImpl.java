package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppUser;

@Service
public class AppUserServiceImpl extends AbsAppUserServiceImpl<AppUser> implements AppUserService {

	@Override
	public AppUser createEntity() {
		return new AppUser();
	}

}
