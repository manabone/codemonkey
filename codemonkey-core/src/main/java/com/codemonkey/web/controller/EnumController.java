package com.codemonkey.web.controller;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.service.GenericService;
import com.codemonkey.utils.EnumHolder;
import com.codemonkey.utils.EnumUtils;

@Controller
@RequestMapping("/ext/enum/**")
public class EnumController extends AbsExtController {
	
	@Autowired private EnumHolder enumHolder;
	
	@RequestMapping("read")
	@ResponseBody
	public String read(@RequestParam String className) {
		Class<?> ienum = enumHolder.getByClassName(className);
		JSONArray ja = EnumUtils.getEnmuDataByClazz(ienum);
		return ja.toString();
	}
	
	@Override
	public List<AppPermission> regAppPermission() {
		return null;
	}

	@Override
	protected GenericService service() {
		return null;
	}

}
