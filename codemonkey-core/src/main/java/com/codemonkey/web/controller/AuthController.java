package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AbsAppUserService;
import com.codemonkey.service.AppUserService;

@Controller
@RequestMapping("/auth/**")
public class AuthController extends AbsAuthController<AppUser>{

	@Autowired private AppUserService appUserService;

	@Override
	protected AbsAppUserService<AppUser> getUserService() {
		return appUserService;
	}

}
