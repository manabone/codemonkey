package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.SalesOrder;
import com.cm.erp.service.SalesOrderService;

@Controller
@RequestMapping("/ext/salesOrder/**")
public class SalesOrderFormController extends AbsFormExtController<SalesOrder>{

	@Autowired private SalesOrderService salesOrderService;
	
	@Override
	protected SalesOrderService service() {
		return salesOrderService;
	}

	@Override
	protected SalesOrder createEntity() {
		return new SalesOrder();
	}
}
