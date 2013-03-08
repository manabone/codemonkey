package com.codemonkey.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;

@Service
public abstract class DocumentServiceImpl<T extends Document , E extends DocumentLine> extends GenericServiceImpl<T> implements DocumentService<T , E>{

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	
	@Autowired private CurrencyTransactionFactory currencyTransactionFactory;
	
	@Autowired private ItemTransactionService itemTransactionService;
	
	@Autowired private CurrencyTransactionService currencyTransactionService;
	
	abstract DocumentLineService<E> getDocumentLineService();
	
	abstract void validate4post(Document doc);
	
	public void post(T doc) {
		
		save(doc);
		
		validate4post(doc);
		
		List<?> lines = getDocumentLineService().getLinesByHeader(doc);
		
		List<Transaction> itemTrans = itemTransactionFactory.createTransactions(doc, lines);
		
		itemTransactionService.post(itemTrans);
		
		List<Transaction> currencyTrans = currencyTransactionFactory.createTransactions(doc, lines);
		
		currencyTransactionService.post(currencyTrans);
		
	}

}
