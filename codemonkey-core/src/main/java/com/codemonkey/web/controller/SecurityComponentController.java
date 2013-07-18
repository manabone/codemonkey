package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.extcmp.ExtCol;
import com.codemonkey.extcmp.ExtFormField;
import com.codemonkey.service.SecurityComponentService;

@Controller
@RequestMapping("/ext/securityComponent/**")
public class SecurityComponentController extends AbsMMController<SecurityComponent>{

	@Autowired private SecurityComponentService securityComponentService;
	
	@Override
	protected List<ExtFormField> getFormFields(){
		return null;
	}
	
	@Override
	protected List<ExtCol> getListCols(){
  		List<ExtCol> items = new ArrayList<ExtCol>();
  		ExtCol col = new ExtCol("id");
  		col.setHidden(true);
  		items.add(col);
  		items.add(new ExtCol("permission"));
  		items.add(new ExtCol("url"));
  		return items;
  	}

	@Override
	protected SecurityComponentService service() {
		return securityComponentService;
	}

}
