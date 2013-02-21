package com.cm.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.CurrencyPlanning;
import com.cm.erp.domain.CurrencyStockCard;
import com.cm.erp.domain.CurrencyTransaction;
import com.cm.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class CurrencyTransactionServiceImpl extends GenericServiceImpl<Transaction> implements ItemTransactionService{

	@Autowired private CurrencyStockCardService currencyStockCardService;

	@Autowired private CurrencyPlanningService currencyPlanningService;
	

	public void post(List<Transaction> list) {
		if(CollectionUtils.isEmpty(list)) {
			return;
		}
		
		List<CurrencyPlanning> planningList = new ArrayList<CurrencyPlanning>();
			
		for(Transaction t : list){
			CurrencyTransaction tran = (CurrencyTransaction) t;
			//update stock card
			CurrencyStockCard stockCard = currencyStockCardService.getStockCard(tran.getCurrency());
			tran.updateStockCard(stockCard);
			currencyStockCardService.doSave(stockCard);
			
			//create planning
			if(CollectionUtils.isNotEmpty(tran.createPlanning())){
				planningList.addAll(tran.createPlanning());
			}
		}
		
		//saving planning
		if(CollectionUtils.isNotEmpty(planningList)){
			for(CurrencyPlanning plan : planningList){
				currencyPlanningService.doSave(plan);
			}
		}
	}
}