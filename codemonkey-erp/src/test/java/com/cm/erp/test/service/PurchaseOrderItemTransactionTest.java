package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.ItemDemand;
import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.PurchaseOrderItemTransaction;
import com.cm.erp.domain.PurchaseOrderLine;
import com.cm.erp.domain.Transaction;


public class PurchaseOrderItemTransactionTest extends ItemTransactionTest {
	
	@Test
	public void test4PurchaseOrderLine(){
		
		//1-test transactions
		PurchaseOrderLine line = buildPurchaseOrderLine();
		
		List<Transaction> trans = createTransactions(line);
		
		verifyPurchaseOrderTransactions(trans);
		
		//2-test StockCard
		List<ItemStockCard> stackCardList = new ArrayList<ItemStockCard>();
		
		updateItemStockCard(trans , stackCardList);
		
		verifyPurchaseOrderStockCard(stackCardList);
		
		//3-test itemPlanning
		List<ItemPlanning> planList = new ArrayList<ItemPlanning>();
		
		createItemPlanning(trans , planList);
		
		verifyPurchaseOrderPlanning(planList);
		
	}
	
	private List<Transaction> verifyPurchaseOrderTransactions(List<Transaction> trans) {
		
		assertEquals(1 , trans.size());
		
		for(Transaction t : trans){
			
			verify(t);
			
			PurchaseOrderItemTransaction t1 = (PurchaseOrderItemTransaction) t;
			
			assertNotNull(t1.getVendor());
			
		}
		
		return trans;
		
	}
	
	private void verifyPurchaseOrderStockCard(List<ItemStockCard> stackCardList) {
		
		for(ItemStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(QTY , stockCard.getQtyOnPurchaseOrder());
		}
		
	}
	
	protected void verifyPurchaseOrderPlanning(List<ItemPlanning> planningList) {
		
		for(ItemPlanning plan : planningList){
			ItemDemand demand = (ItemDemand) plan;
			verify(demand);
		}
	}
}
