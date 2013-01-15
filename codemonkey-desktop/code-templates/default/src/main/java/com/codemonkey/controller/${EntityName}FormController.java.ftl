package com.codemonkey.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.${EntityName};
import com.codemonkey.service.${EntityName}Service;

@Controller
@RequestMapping("/ext/${entityName}/**")
public class ${EntityName}FormController extends AbsFormExtController<${EntityName}>{

	@Autowired private ${EntityName}Service ${entityName}Service;
	
	@Override
	protected ${EntityName}Service service() {
		return ${entityName}Service;
	}

	@Override
	protected ${EntityName} createEntity() {
		return new ${EntityName}();
	}

	@Override
	String getControllers() {
		return "${EntityName}FormController";
	}

	@Override
	String getIndexView() {
		return "${entityname}edit";
	}

}
