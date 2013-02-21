package com.cm.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.ItemTransaction;
import com.cm.erp.domain.Transaction;

@Service
public class ItemTransactionFactory extends TransactionFactory{

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
