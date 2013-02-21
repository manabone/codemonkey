package com.cm.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.Transaction;

@Service
public class CurrencyTransactionFactory extends TransactionFactory{

	@Override
	public List<Transaction> createTransactions(DocumentLine line) {
		return line.createCurrencyTransactions();
	}
	
}
