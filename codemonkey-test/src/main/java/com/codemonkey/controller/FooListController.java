package com.codemonkey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.Foo;
import com.codemonkey.domain.SecurityButton;
import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.service.FooService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/fooList/**")
public class FooListController extends AbsListExtController<Foo>{

	@Autowired private FooService fooService;
	
	@Override
	protected FooService service() {
		return fooService;
	}
	
	@Override
	public List<SecurityComponent> regSecurityComponents() {
		List<SecurityComponent> list = super.regSecurityComponents();
		list.add(new SecurityButton("#appRoleFormModule_window button[action=create]" , "foo create"));
		return list;
	}
}
