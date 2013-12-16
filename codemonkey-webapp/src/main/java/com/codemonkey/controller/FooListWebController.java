package com.codemonkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.Foo;
import com.codemonkey.service.FooService;

@Controller
@RequestMapping("/web/fooList/**")
public class FooListWebController extends AbsWebController<Foo> {

	@Autowired private FooService fooService;
	
	@Override
	protected FooService service() {
		return fooService;
	}
	
	@RequestMapping(value="show")
	public String show(ModelMap map){
		map.addAttribute("list" , fooService.findAll());
		return "fooList/fooList";
	}
}
