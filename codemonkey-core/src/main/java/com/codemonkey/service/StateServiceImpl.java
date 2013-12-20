package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.State;

@Service
public class StateServiceImpl extends PhysicalServiceImpl<State> implements StateService {

	@Override
	public State createEntity() {
		return new State();
	}

}
