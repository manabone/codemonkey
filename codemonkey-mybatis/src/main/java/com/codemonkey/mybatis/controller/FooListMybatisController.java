package com.codemonkey.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ext/fooList/**")
public class FooListMybatisController extends AbsMybatisController{

	@Override
	String getQueryId() {
		return "selectFooList";
	}

}
