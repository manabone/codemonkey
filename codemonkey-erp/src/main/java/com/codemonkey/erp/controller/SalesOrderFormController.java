package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.service.SalesOrderService;


@Controller
@RequestMapping("/ext/salesOrder/**")
public class SalesOrderFormController extends DocumentFormController<SalesOrder>{

	@Autowired private SalesOrderService salesOrderService;
	
	@Override
	protected SalesOrderService service() {
		return salesOrderService;
	}

	@Override
	void processPost(SalesOrder t) {
		salesOrderService.post(t);
	}

}
