package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.State;
import com.codemonkey.service.StateService;

@Controller
@RequestMapping("/ext/state/**")
public class StateController extends AbsExtController<State>{

	@Autowired private StateService stateService;
	
	@Override
	protected StateService service() {
		return stateService;
	}

}
