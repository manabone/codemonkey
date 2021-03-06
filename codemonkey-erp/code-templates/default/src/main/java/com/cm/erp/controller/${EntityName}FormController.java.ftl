package ${groupId}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import ${groupId}.domain.${EntityName};
import ${groupId}.service.${EntityName}Service;

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
}
