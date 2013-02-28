package com.codemonkey.erp.service;

import java.util.List;

import com.codemonkey.erp.domain.Transaction;
import com.codemonkey.service.GenericService;

public interface CurrencyTransactionService extends GenericService<Transaction>{

	void post(List<Transaction> list);
	
}
