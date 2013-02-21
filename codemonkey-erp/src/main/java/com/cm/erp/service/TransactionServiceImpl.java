package com.cm.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemTransaction;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class TransactionServiceImpl extends GenericServiceImpl<Transaction> implements TransactionService{

	@Autowired private StockCardService stockCardService;
	@Autowired private ItemPlanningService planningService;
	
	public void postInventoryTransactions(List<ItemTransaction> list) {
		
		if(CollectionUtils.isEmpty(list)) {
			return;
		}
		
		List<ItemPlanning> planningList = new ArrayList<ItemPlanning>();
			
		for(ItemTransaction tran : list){
			//update stock card
			ItemStockCard stockCard = stockCardService.getStockCard(tran.getItem() , tran.getWarehouse());
			tran.updateStockCard(stockCard);
			stockCardService.doSave(stockCard);
			
			//create planning
			if(CollectionUtils.isNotEmpty(tran.createPlanning())){
				planningList.addAll(tran.createPlanning());
			}
		}
		
		//saving planning
		if(CollectionUtils.isNotEmpty(planningList)){
			for(ItemPlanning plan : planningList){
				planningService.doSave(plan);
			}
		}
	}
}