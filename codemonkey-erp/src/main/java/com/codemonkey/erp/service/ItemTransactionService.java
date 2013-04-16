package com.codemonkey.erp.service;

import java.util.List;

import com.codemonkey.erp.domain.ItemTransaction;
import com.codemonkey.erp.domain.Transaction;
import com.codemonkey.service.GenericService;

public interface ItemTransactionService extends GenericService<ItemTransaction>{

	void post(List<Transaction> list);
	
}
