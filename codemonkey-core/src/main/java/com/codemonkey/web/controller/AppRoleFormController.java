package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppRole;
import com.codemonkey.service.AppRoleService;

@Controller
@RequestMapping("/ext/appRole/**")
public class AppRoleFormController extends AbsFormExtController<AppRole>{

	@Autowired private AppRoleService appRoleService;
	
	@Override
	protected AppRoleService service() {
		return appRoleService;
	}
	
}
