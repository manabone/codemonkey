package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cm.erp.domain.Transaction;
import com.cm.erp.service.ItemTransactionService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/itemTransactionList/**")
public class ItemTransactionListController extends AbsListExtController<Transaction>{

	@Autowired private ItemTransactionService transactionService;
	
	@Override
	protected ItemTransactionService service() {
		return transactionService;
	}

}
