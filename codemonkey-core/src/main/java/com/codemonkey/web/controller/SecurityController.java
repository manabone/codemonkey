package com.codemonkey.web.controller;

import java.util.List;

import com.codemonkey.domain.AppPermission;

public interface SecurityController {

	List<AppPermission> regAppPermission();
	
}
