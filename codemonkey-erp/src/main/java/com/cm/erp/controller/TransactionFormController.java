package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.Transaction;
import com.cm.erp.service.TransactionService;

@Controller
@RequestMapping("/ext/transaction/**")
public class TransactionFormController extends AbsFormExtController<Transaction>{

	@Autowired private TransactionService transactionService;
	
	@Override
	protected TransactionService service() {
		return transactionService;
	}

	@Override
	protected Transaction createEntity() {
		return null;
	}
}
