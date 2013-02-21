package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.service.ItemStockCardService;

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
