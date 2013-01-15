package com.codemonkey.web.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.Foo;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.FooService;

@Controller
@RequestMapping("/ext/foo/**")
public class FooFormController extends AbsFormExtController<Foo>{

	@Autowired private FooService fooService;
	
	@Autowired private AppRoleService appRoleService;
	
	@Override
	protected FooService service() {
		return fooService;
	}

	@Override
	protected Foo createEntity() {
		return new Foo();
	}
	
	@Override
	protected JSONObject buildJson(Foo foo) {
		JSONObject data = super.buildJson(foo);
		
		List<AppRole> appRoles = appRoleService.findAll();
		
		if(CollectionUtils.isNotEmpty(appRoles)){
			JSONArray ja = new JSONArray();
			for(AppRole appRole : appRoles){
				ja.put(appRole.listJson());
			}
			data.put("appRoles", ja);
		}
		return data;
	}

}
