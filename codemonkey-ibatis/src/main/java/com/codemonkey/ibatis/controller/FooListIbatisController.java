package com.codemonkey.ibatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ext/fooList/**")
public class FooListIbatisController extends AbsIbatisController{

	@Override
	String getQueryId() {
		return "selectFooList";
	}

}
