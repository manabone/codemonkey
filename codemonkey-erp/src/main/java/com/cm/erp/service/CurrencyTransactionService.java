package com.cm.erp.service;

import java.util.List;

import com.cm.erp.domain.Transaction;
import com.codemonkey.service.GenericService;

public interface CurrencyTransactionService extends GenericService<Transaction>{

	void post(List<Transaction> list);
	
}