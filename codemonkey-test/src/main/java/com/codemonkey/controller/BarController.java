package com.codemonkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.Bar;
import com.codemonkey.service.BarService;
import com.codemonkey.web.controller.AbsBatchedController;

@Controller
@RequestMapping("/ext/bar/**")
public class BarController extends AbsBatchedController<Bar>{

	@Autowired private BarService barService;
	
	@Override
	protected BarService service() {
		return barService;
	}

}
