package ${groupId}.service;

import org.springframework.stereotype.Service;

import ${groupId}.domain.${EntityName};

@Service
public class ${EntityName}ServiceImpl extends GenericServiceImpl<${EntityName}> implements ${EntityName}Service{

	@Override
	public ${EntityName} createEntity() {
		return new ${EntityName}();
	}
}
