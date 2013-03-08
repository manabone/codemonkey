package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.erp.service.SalesOrderLineService;
import com.codemonkey.web.controller.AbsFormExtController;


@Controller
@RequestMapping("/ext/salesOrderLine/**")
public class SalesOrderLineFormController extends AbsFormExtController<SalesOrderLine>{

	@Autowired private SalesOrderLineService salesOrderLineService;
	
	@Override
	protected SalesOrderLineService service() {
		return salesOrderLineService;
	}

}
