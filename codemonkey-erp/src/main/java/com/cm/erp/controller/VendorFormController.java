package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.Vendor;
import com.cm.erp.service.VendorService;

@Controller
@RequestMapping("/ext/vendor/**")
public class VendorFormController extends AbsFormExtController<Vendor>{

	@Autowired private VendorService vendorService;
	
	@Override
	protected VendorService service() {
		return vendorService;
	}

	@Override
	protected Vendor createEntity() {
		return new Vendor();
	}
}
