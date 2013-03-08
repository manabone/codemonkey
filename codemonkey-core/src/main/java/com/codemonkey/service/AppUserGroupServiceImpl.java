package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppUserGroup;

@Service
public class AppUserGroupServiceImpl extends GenericServiceImpl<AppUserGroup> implements AppUserGroupService{

	@Override
	public AppUserGroup createEntity() {
		return new AppUserGroup();
	}
}
