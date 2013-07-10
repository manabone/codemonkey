package com.codemonkey.mybatis.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppPermission;

@Controller
@RequestMapping("/ext/fooList/**")
public class FooListMybatisController extends AbsMybatisController{

	@Override
	public List<AppPermission> regAppPermission() {
		return null;
	}

	@Override
	String getQueryId() {
		return "selectFooList";
	}

}
