package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.SecurityButton;

@Service
public class SecurityButtonServiceImpl extends GenericServiceImpl<SecurityButton> implements SecurityButtonService{

	@Override
	public SecurityButton createEntity() {
		return new SecurityButton(null , null);
	}

}
