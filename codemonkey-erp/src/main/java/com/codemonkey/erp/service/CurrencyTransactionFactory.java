package com.codemonkey.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.CurrencyTransaction;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.Transaction;

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
		transactionService.save(tran);
	}
	
}
