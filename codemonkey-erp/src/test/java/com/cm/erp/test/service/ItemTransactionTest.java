package com.cm.erp.test.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.ItemTransaction;
import com.cm.erp.domain.Transaction;
import com.cm.erp.service.ItemStockCardService;
import com.cm.erp.service.ItemTransactionFactory;

public class ItemTransactionTest extends AbsErpServiceTest {

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	@Autowired private ItemStockCardService itemStockCardService;
	
	@Test
	public void testServices(){
		assertNotNull(itemTransactionFactory);
		assertNotNull(itemStockCardService);
	}
	
	protected List<Transaction> createTransactions(DocumentLine line) {
		List<Transaction> trans = itemTransactionFactory.createTransactions(line);
		return trans;
	}
	
	protected void createItemPlanning(List<Transaction> trans,
			List<ItemPlanning> planList) {
		
		for(Transaction t : trans){
			
			ItemTransaction tran = (ItemTransaction) t;
			
			//create planning
			if(CollectionUtils.isNotEmpty(tran.createPlanning())){
				planList.addAll(tran.createPlanning());
			}
		}
	}
	
	void updateItemStockCard(List<Transaction> trans,
			List<ItemStockCard> stackCardList) {
		
		if(CollectionUtils.isEmpty(trans)) {
			return ;
		}
		
		for(Transaction t : trans){
			ItemTransaction tran = (ItemTransaction) t;
			ItemStockCard stockCard = itemStockCardService.getStockCard(tran.getItem() , tran.getWarehouse());
			tran.updateStockCard(stockCard);
			
			stackCardList.add(stockCard);
		}
		
	}
	
	public void verify(ItemPlanning plan) {
		assertNotNull(plan.getItem());
		assertNotNull(plan.getDate());
		assertNotNull(plan.getItemTransaction());
		assertNotNull(plan.getWarehouse());
	}

	public void verify(ItemStockCard stockCard) {
		assertNotNull(stockCard.getWarehouse());
		assertNotNull(stockCard.getItem());
	}

	protected void verify(Transaction tran) {
		ItemTransaction t = (ItemTransaction) tran;
		assertNotNull(t.getItem());
		assertNotNull(t.getQty());
		assertNotNull(t.getWarehouse());
		assertNotNull(t.getDocLine());
	}
}
