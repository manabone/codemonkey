package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.ItemStockCard;
import com.codemonkey.erp.service.ItemStockCardService;
import com.codemonkey.web.controller.AbsFormExtController;


@Controller
@RequestMapping("/ext/stockCard/**")
public class StockCardFormController extends AbsFormExtController<ItemStockCard>{

	@Autowired private ItemStockCardService stockCardService;
	
	@Override
	protected ItemStockCardService service() {
		return stockCardService;
	}

	@Override
	protected ItemStockCard createEntity() {
		return null;
	}
}