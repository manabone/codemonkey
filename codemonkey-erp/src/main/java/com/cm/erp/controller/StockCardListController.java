package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cm.erp.domain.StockCard;
import com.cm.erp.service.StockCardService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/stockCardList/**")
public class StockCardListController extends AbsListExtController<StockCard>{

	@Autowired private StockCardService stockCardService;
	
	@Override
	protected StockCardService service() {
		return stockCardService;
	}

}
