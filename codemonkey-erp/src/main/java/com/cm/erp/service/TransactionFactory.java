package com.cm.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.Document;
import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.Transaction;

@Service
public abstract class TransactionFactory {

	@Autowired private TransactionService transactionService;

	public abstract List<Transaction> createTransactions(DocumentLine line);
	
	public List<Transaction> createTransactions(Document doc , DocumentLineService docLineService ){
		List<Transaction> list = new ArrayList<Transaction>();
		List<DocumentLine> lines =  docLineService.getLinesByHeader(doc);
		if(CollectionUtils.isNotEmpty(lines)){
			for(DocumentLine line : lines){
				List<Transaction> itemTrans = createTransactions(line);
				if(CollectionUtils.isNotEmpty(itemTrans)){
					list.addAll(itemTrans);
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(list)){
			for(Transaction tran : list){
				transactionService.doSave(tran);
			}
		}
		
		return list;
	}
}
