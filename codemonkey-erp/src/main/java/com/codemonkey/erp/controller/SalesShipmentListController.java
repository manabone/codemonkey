package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.SalesShipment;
import com.codemonkey.erp.service.SalesShipmentService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/salesShipmentList/**")
public class SalesShipmentListController extends AbsListExtController<SalesShipment>{

	@Autowired private SalesShipmentService salesShipmentService;
	
	@Override
	protected SalesShipmentService service() {
		return salesShipmentService;
	}

}
