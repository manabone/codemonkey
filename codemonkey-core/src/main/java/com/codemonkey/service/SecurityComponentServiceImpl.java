package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.SecurityComponent;

@Service
public class SecurityComponentServiceImpl extends GenericServiceImpl<SecurityComponent> implements SecurityComponentService{

	@Override
	public SecurityComponent createEntity() {
		return new SecurityComponent(null , null);
	}

}
