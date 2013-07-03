package com.codemonkey.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppPermission;

@Controller
@RequestMapping("/ext/fooListMybatis/**")
public class FooListMybatisController extends AbsIbatisController{

	@Override
	public List<AppPermission> regAppPermission() {
		return null;
	}

	@Override
	String getQueryId() {
		return "selectFooList";
	}

}
