package com.cm.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.CurrencyTransaction;
import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.Transaction;

@Service
public class CurrencyTransactionFactory extends TransactionFactory{

	@Autowired private CurrencyTransactionService transactionService;
	
	@Override
	public List<Transaction> createTransactions(DocumentLine line) {
		return line.createCurrencyTransactions();
	}

	@Override
	public void saveTransaction(Transaction t) {
		CurrencyTransaction tran = (CurrencyTransaction) t;
		transactionService.doSave(tran);
	}
	
}
