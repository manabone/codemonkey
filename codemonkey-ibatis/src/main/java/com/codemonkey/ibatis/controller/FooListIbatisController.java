package com.codemonkey.ibatis.controller;


//@Controller
//@RequestMapping("/ext/fooIbatisList/**")
public class FooListIbatisController extends AbsIbatisController{

	@Override
	public String getQueryId() {
		return "selectFooList";
	}

}
