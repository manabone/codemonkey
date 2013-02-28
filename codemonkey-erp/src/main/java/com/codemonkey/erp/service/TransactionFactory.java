package com.codemonkey.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.Transaction;

@Service
public abstract class TransactionFactory {

	public abstract List<Transaction> createTransactions(DocumentLine line);

	public abstract void saveTransaction(Transaction tran);

	
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
				saveTransaction(tran);
			}
		}
		
		return list;
	}
}
