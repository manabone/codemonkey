package com.codemonkey.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.IEntity;
//import com.codemonkey.service.FooService;
import com.codemonkey.service.GenericService;

@Controller
@RequestMapping("/web/fooList/**")
public class FooListWebController extends AbsWebController<IEntity> {

	@Override
	protected GenericService<IEntity> service() {
		return null;
	}

//	@Autowired private FooService fooService;
//	
//	@Override
//	protected FooService service() {
//		return fooService;
//	}
//	
//	@RequestMapping(value="show")
//	public String show(ModelMap map){
//		map.addAttribute("list" , fooService.findAll());
//		return "fooList/fooList";
//	}
}
