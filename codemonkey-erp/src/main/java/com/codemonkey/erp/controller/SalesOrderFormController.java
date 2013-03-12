package com.codemonkey.erp.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.service.SalesOrderService;
import com.codemonkey.web.converter.CustomConversionService;


@Controller
@RequestMapping("/ext/salesOrder/**")
public class SalesOrderFormController extends DocumentFormController<SalesOrder>{

	@Autowired private SalesOrderService salesOrderService;
	
	@Override
	protected SalesOrderService service() {
		return salesOrderService;
	}

	@Override
	SalesOrder post(JSONObject params, CustomConversionService ccService) {
		return salesOrderService.doPost(params , ccService);
	}

}
