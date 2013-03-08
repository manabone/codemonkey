package com.codemonkey.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.CurrencyPlanning;
import com.codemonkey.erp.domain.CurrencyStockCard;
import com.codemonkey.erp.domain.CurrencyTransaction;
import com.codemonkey.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class CurrencyTransactionServiceImpl extends GenericServiceImpl<Transaction> implements CurrencyTransactionService{

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
			currencyStockCardService.save(stockCard);
			
			//create planning
			if(CollectionUtils.isNotEmpty(tran.createPlanning())){
				planningList.addAll(tran.createPlanning());
			}
		}
		
		//saving planning
		if(CollectionUtils.isNotEmpty(planningList)){
			for(CurrencyPlanning plan : planningList){
				currencyPlanningService.save(plan);
			}
		}
	}

	@Override
	public Transaction createEntity() {
		return null;
	}
}