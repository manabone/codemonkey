package com.codemonkey.web.validator;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppUserService;

@Component
public class AppUserValidator extends DefaultValidator<AppUser>{

	@Autowired private AppUserService appUserService;
	
	public void validate(AppUser user, BindingResult bindingResult) {
		
		long count = appUserService.count(Restrictions.eq("username", user.getUsername()));
		
		if(count > 0){
			bindingResult.rejectValue("username", "", "user name is not unique");
		}
	}
}
