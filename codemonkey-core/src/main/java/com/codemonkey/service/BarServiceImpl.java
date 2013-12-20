package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.Bar;

@Service
public class BarServiceImpl extends PhysicalServiceImpl<Bar> implements BarService{

	@Override
	public Bar createEntity() {
		return new Bar();
	}

}
