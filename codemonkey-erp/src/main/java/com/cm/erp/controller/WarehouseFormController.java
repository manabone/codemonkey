package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.Warehouse;
import com.cm.erp.service.WarehouseService;

@Controller
@RequestMapping("/ext/warehouse/**")
public class WarehouseFormController extends AbsFormExtController<Warehouse>{

	@Autowired private WarehouseService warehouseService;
	
	@Override
	protected WarehouseService service() {
		return warehouseService;
	}

	@Override
	protected Warehouse createEntity() {
		return new Warehouse();
	}
}
