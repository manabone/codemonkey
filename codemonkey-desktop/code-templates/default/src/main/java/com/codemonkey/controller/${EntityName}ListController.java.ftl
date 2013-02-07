package ${groupId}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ${groupId}.domain.${EntityName};
import ${groupId}.service.${EntityName}Service;

@Controller
@RequestMapping("/ext/${entityName}List/**")
public class ${EntityName}ListController extends AbsListExtController<${EntityName}>{

	@Autowired private ${EntityName}Service ${entityName}Service;
	
	@Override
	protected ${EntityName}Service service() {
		return ${entityName}Service;
	}

}
