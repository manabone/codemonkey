package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.CmpPermission;
import com.codemonkey.extcmp.ExtCol;
import com.codemonkey.extcmp.ExtFormField;
import com.codemonkey.service.CmpPermissionService;

@Controller
@RequestMapping("/ext/cmpPermission/**")
public class CmpPermissionController extends AbsMMController<CmpPermission>{

	@Autowired private CmpPermissionService cmpPermissionService;
	
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
	protected CmpPermissionService service() {
		return cmpPermissionService;
	}

}
