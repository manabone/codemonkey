package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.SalesShipment;
import com.codemonkey.erp.service.SalesShipmentService;


@Controller
@RequestMapping("/ext/salesShipment/**")
public class SalesShipmentFormController extends DocumentFormController<SalesShipment>{

	@Autowired private SalesShipmentService salesShipmentService;
	
	@Override
	protected SalesShipmentService service() {
		return salesShipmentService;
	}

	@Override
	void processPost(SalesShipment t) {
		salesShipmentService.post(t);
	}

}
