package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.ItemOrderSupply;
import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.PurchaseOrderItemTransaction;
import com.cm.erp.domain.PurchaseOrderLine;
import com.cm.erp.domain.Transaction;


public class PurchaseOrderItemTransactionTest extends ItemTransactionTest {
	
	@Test
	public void test(){
		
		//1-test transactions
		PurchaseOrderLine line = buildPurchaseOrderLine();
		
		List<Transaction> trans = createTransactions(line);
		
		verifyTransactions(trans);
		
		//2-test StockCard
		List<ItemStockCard> stackCardList = new ArrayList<ItemStockCard>();
		
		updateItemStockCard(trans , stackCardList);
		
		verifyStockCard(stackCardList);
		
		//3-test itemPlanning
		List<ItemPlanning> planList = new ArrayList<ItemPlanning>();
		
		createItemPlanning(trans , planList);
		
		verifyPlanning(planList);
		
	}
	
	private List<Transaction> verifyTransactions(List<Transaction> trans) {
		
		assertEquals(1 , trans.size());
		
		for(Transaction t : trans){
			
			verify(t);
			
			PurchaseOrderItemTransaction t1 = (PurchaseOrderItemTransaction) t;
			
			assertNotNull(t1.getVendor());
			assertEquals(QTY, t1.getQty());
			assertEquals(QTY, t1.getQtyOnPurchaseOrder());
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<ItemStockCard> stackCardList) {
		
		for(ItemStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(QTY , stockCard.getQtyOnPurchaseOrder());
		}
		
	}
	
	protected void verifyPlanning(List<ItemPlanning> planningList) {
		
		for(ItemPlanning plan : planningList){
			ItemOrderSupply supply = (ItemOrderSupply) plan;
			verify(supply);
			
			assertEquals(QTY , supply.getQty());
		}
	}
}
