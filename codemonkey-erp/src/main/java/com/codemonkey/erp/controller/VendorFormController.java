package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.Vendor;
import com.codemonkey.erp.service.VendorService;
import com.codemonkey.web.controller.AbsFormExtController;


@Controller
@RequestMapping("/ext/vendor/**")
public class VendorFormController extends AbsFormExtController<Vendor>{

	@Autowired private VendorService vendorService;
	
	@Override
	protected VendorService service() {
		return vendorService;
	}

}
