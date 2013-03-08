package com.codemonkey.erp.service;

import java.util.List;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.Transaction;

public interface ItemTransactionFactory {

	List<Transaction> createTransactions(DocumentLine line);

	void saveTransaction(Transaction t);

	List<Transaction> createTransactions(Document doc, List<?> lines);
	
}
