package com.codemonkey.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.Foo;
import com.codemonkey.service.GenericService;
import com.codemonkey.utils.EnumUtils;

@Controller
@RequestMapping("/ext/enum/**")
public class EnumController extends AbsExtController<Foo> {
	
	@RequestMapping("read")
	@ResponseBody
	public String read(@RequestParam String className , @RequestParam(required = false) String packageName  , @RequestParam(required = false) String method ) {
		return  EnumUtils.getEnmuDataByClazz(className , packageName , method).toString();
	}
	
	@Override
	protected GenericService<Foo> service() {
		return null;
	}

}
