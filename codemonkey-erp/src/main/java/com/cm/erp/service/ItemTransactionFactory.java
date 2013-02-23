package com.cm.erp.service;

import java.util.List;

import com.cm.erp.domain.Document;
import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.Transaction;

public interface ItemTransactionFactory {

	List<Transaction> createTransactions(DocumentLine line);

	void saveTransaction(Transaction t);

	List<Transaction> createTransactions(Document doc, DocumentLineService documentLineService);
	
}
