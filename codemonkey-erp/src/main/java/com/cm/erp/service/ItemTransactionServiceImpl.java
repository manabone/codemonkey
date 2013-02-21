package com.cm.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.ItemTransaction;
import com.cm.erp.domain.Transaction;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class ItemTransactionServiceImpl extends GenericServiceImpl<Transaction> implements ItemTransactionService{

	@Autowired private ItemStockCardService itemStockCardService;

	@Autowired private ItemPlanningService itemPlanningService;
	
	public void post(List<Transaction> list) {
		
		if(CollectionUtils.isEmpty(list)) {
			return;
		}
		
		List<ItemPlanning> planningList = new ArrayList<ItemPlanning>();
			
		for(Transaction t : list){
			
			ItemTransaction tran = (ItemTransaction) t;
			
			//update stock card
			ItemStockCard stockCard = itemStockCardService.getStockCard(tran.getItem() , tran.getWarehouse());
			tran.updateStockCard(stockCard);
			itemStockCardService.doSave(stockCard);
			
			//create planning
			if(CollectionUtils.isNotEmpty(tran.createPlanning())){
				planningList.addAll(tran.createPlanning());
			}
		}
		
		//saving planning
		if(CollectionUtils.isNotEmpty(planningList)){
			for(ItemPlanning plan : planningList){
				itemPlanningService.doSave(plan);
			}
		}
	}
}