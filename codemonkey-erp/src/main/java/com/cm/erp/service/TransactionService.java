package com.cm.erp.service;

import java.util.List;

import com.cm.erp.domain.ItemTransaction;
import com.cm.erp.domain.Transaction;
import com.codemonkey.service.GenericService;

public interface TransactionService extends GenericService<Transaction>{

	void postInventoryTransactions(List<ItemTransaction> list);
	
}
