package com.cm.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.PurchaseOrder;
import com.cm.erp.domain.SalesOrder;
import com.cm.erp.domain.SalesOrderLine;
import com.cm.erp.domain.Transaction;

@Service
public class TransactionFactory {

	@Autowired private SalesOrderLineService salesOrderLineService;
	
	@Autowired private PurchaseOrderLineService purchaseOrderLineService;
	
	@Autowired private TransactionService transactionService;
	
	public List<Transaction> createTransactions(SalesOrder so){
		List<Transaction> list = new ArrayList<Transaction>();
		List<SalesOrderLine> soLines =  salesOrderLineService.findAllBy("salesOrder", so.getId());
		
		if(CollectionUtils.isNotEmpty(soLines)){
			for(SalesOrderLine soLine : soLines){
				if(CollectionUtils.isNotEmpty(soLine.createTransactions())){
					list.addAll(soLine.createTransactions());
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
	
	public List<Transaction> createTransactions(PurchaseOrder po){
		List<Transaction> list = new ArrayList<Transaction>();
		
		return list;
	}
}
