package com.cm.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.Document;
import com.cm.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;

@Service
public abstract class DocumentServiceImpl extends GenericServiceImpl<Document> implements DocumentService{

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	
	@Autowired private CurrencyTransactionFactory currencyTransactionFactory;
	
	@Autowired private ItemTransactionService itemTransactionService;
	
	@Autowired private CurrencyTransactionService currencyTransactionService;
	
	abstract DocumentLineService getDocumentLineService();
	
	abstract void validate4post(Document doc);
	
	
	
	public void post(Document doc) {
		
		doSave(doc);
		
		validate4post(doc);
		
		List<Transaction> itemTrans = itemTransactionFactory.createTransactions(doc, getDocumentLineService());
		
		itemTransactionService.post(itemTrans);
		
		List<Transaction> currencyTrans = currencyTransactionFactory.createTransactions(doc, getDocumentLineService());
		
		currencyTransactionService.post(currencyTrans);
		
	}

}
