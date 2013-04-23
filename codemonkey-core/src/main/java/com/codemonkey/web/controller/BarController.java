package com.codemonkey.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.Bar;
import com.codemonkey.extcmp.ExtFormField;
import com.codemonkey.extcmp.ExtPopup;
import com.codemonkey.service.BarService;

@Controller
@RequestMapping("/ext/bar/**")
public class BarController extends AbsMMController<Bar>{

	@Autowired BarService barService;
	
	@Override
	protected List<ExtFormField> getFormFields(){
  		List<ExtFormField> items = super.getFormFields();
  		items.add(new ExtPopup("foo" , "FooList"));
  		items.add(new ExtPopup("bar" , "Bar"));
  		return items;
  	}

	@Override
	protected BarService service() {
		return barService;
	}

}
