package com.codemonkey.erp.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.erp.service.SalesOrderService;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.web.controller.AbsFormExtController;


@Controller
@RequestMapping("/ext/salesOrder/**")
public class SalesOrderFormController extends AbsFormExtController<SalesOrder>{

	@Autowired private SalesOrderService salesOrderService;
	
	@Override
	protected SalesOrder buildEntity(JSONObject params){
		SalesOrder so = super.buildEntity(params);
		
		JsonArrayConverter<SalesOrderLine> converter = new JsonArrayConverter<SalesOrderLine>();
		List<SalesOrderLine> lines = converter.convert(params , "lines" , SalesOrderLine.class , getCcService());
		so.setLines(lines);
		return so;
	}
	
	@Override
	protected SalesOrderService service() {
		return salesOrderService;
	}

	@Override
	protected SalesOrder createEntity() {
		return new SalesOrder();
	}
}
