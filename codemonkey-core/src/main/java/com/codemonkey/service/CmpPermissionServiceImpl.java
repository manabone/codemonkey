package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.CmpPermission;

@Service
public class CmpPermissionServiceImpl extends GenericServiceImpl<CmpPermission> implements CmpPermissionService{

	@Override
	public CmpPermission createEntity() {
		return new CmpPermission(null , null , null , null);
	}

}
