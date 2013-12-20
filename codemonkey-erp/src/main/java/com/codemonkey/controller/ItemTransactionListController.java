package com.codemonkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.ItemTransaction;
import com.codemonkey.erp.service.ItemTransactionService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/itemTransactionList/**")
public class ItemTransactionListController extends AbsListExtController<ItemTransaction>{

	@Autowired private ItemTransactionService transactionService;
	
	@Override
	protected ItemTransactionService service() {
		return transactionService;
	}

}
