package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.PowerTree;

@Service
public class PowerTreeServiceImpl extends GenericServiceImpl<PowerTree> implements PowerTreeService{

	@Override
	public PowerTree createEntity() {
		return new PowerTree(null , null , null , null);
	}
	
}
