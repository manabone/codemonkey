package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.Foo;
import com.codemonkey.service.FooService;

@Controller
@RequestMapping("/ext/fooList/**")
public class FooListController extends AbsListExtController<Foo>{

	@Autowired private FooService fooService;
	
	@Override
	protected FooService service() {
		return fooService;
	}

}
