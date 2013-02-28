package com.codemonkey.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.ItemTransaction;
import com.codemonkey.erp.domain.Transaction;

@Service
public class ItemTransactionFactoryImpl extends TransactionFactory implements ItemTransactionFactory {

	@Autowired private ItemTransactionService transactionService;
	
	@Override
	public List<Transaction> createTransactions(DocumentLine line) {
		return line.createItemTransactions();
	}

	@Override
	public void saveTransaction(Transaction t) {
		ItemTransaction tran = (ItemTransaction) t;
		transactionService.doSave(tran);
	}
}
