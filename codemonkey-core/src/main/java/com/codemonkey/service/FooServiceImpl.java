package com.codemonkey.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.Foo;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;

@Service
public class FooServiceImpl extends GenericServiceImpl<Foo> implements FooService{

	@Override
	public Foo createEntity() {
		return new Foo();
	}
	
	@Override
	protected Set<FieldValidation> validate(Foo foo) {
		Set<FieldValidation> set = super.validate(foo);
		if(foo.getFnumber() == null || foo.getFnumber() > 10){
			set.add(new FormFieldValidation("fnumber" , "invalid number greater than 10"));
		}
		
		if(!unique(foo , "fstring" , foo.getFstring())){
			set.add(new FormFieldValidation("fstring" , "fstring not unique"));
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
