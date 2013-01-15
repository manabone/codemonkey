package com.codemonkey.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.Foo;
import com.codemonkey.error.FieldValidation;

@Service
public class FooServiceImpl extends GenericServiceImpl<Foo> implements FooService{

	@Autowired private AppRoleService appRoleService;
	@Override
	protected Set<FieldValidation> validate(Foo foo) {
		Set<FieldValidation> set = super.validate(foo);
		if(foo.getFnumber() == null || foo.getFnumber() > 5){
			set.add(new FieldValidation("fnumber" , "invalid number greater than 5"));
		}
		return set;
	}
//	测试是否开启事务
//	@Override
//	public void doSave(Foo entity){
//		super.doSave(entity);
//		if(true){
//			throw new BadObjVersionError(entity);
//		}
//		AppRole role = new AppRole();
//		appRoleService.doSave(role);
//		
//	}
	
	
}
