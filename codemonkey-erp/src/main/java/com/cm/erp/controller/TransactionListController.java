package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cm.erp.domain.Transaction;
import com.cm.erp.service.TransactionService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/transactionList/**")
public class TransactionListController extends AbsListExtController<Transaction>{

	@Autowired private TransactionService transactionService;
	
	@Override
	protected TransactionService service() {
		return transactionService;
	}

}
