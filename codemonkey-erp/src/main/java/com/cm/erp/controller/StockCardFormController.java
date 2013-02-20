package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.StockCard;
import com.cm.erp.service.StockCardService;

@Controller
@RequestMapping("/ext/stockCard/**")
public class StockCardFormController extends AbsFormExtController<StockCard>{

	@Autowired private StockCardService stockCardService;
	
	@Override
	protected StockCardService service() {
		return stockCardService;
	}

	@Override
	protected StockCard createEntity() {
		return new StockCard();
	}
}
