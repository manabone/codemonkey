package com.codemonkey.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.CmpType;
import com.codemonkey.domain.Foo;
import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.service.FooService;

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
		list.add(new SecurityComponent("#appRoleFormModule_window button[action=create]" , CmpType.Button , "foo create"));
		return list;
	}
}
