package com.codemonkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.Warehouse;
import com.codemonkey.erp.service.WarehouseService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/warehouseList/**")
public class WarehouseListController extends AbsListExtController<Warehouse>{

	@Autowired private WarehouseService warehouseService;
	
	@Override
	protected WarehouseService service() {
		return warehouseService;
	}

}
