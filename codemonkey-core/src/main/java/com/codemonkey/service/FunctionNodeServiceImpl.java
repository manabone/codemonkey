package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.FunctionNode;

@Service
public class FunctionNodeServiceImpl extends PhysicalServiceImpl<FunctionNode> implements FunctionNodeService{

	@Override
	public FunctionNode createEntity() {
		return new FunctionNode();
	}
	
}
