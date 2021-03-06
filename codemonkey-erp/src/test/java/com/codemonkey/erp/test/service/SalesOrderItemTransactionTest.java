package com.codemonkey.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.codemonkey.erp.domain.ItemOrderDemand;
import com.codemonkey.erp.domain.ItemPlanning;
import com.codemonkey.erp.domain.ItemStockCard;
import com.codemonkey.erp.domain.SalesOrderItemTransaction;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.erp.domain.Transaction;


public class SalesOrderItemTransactionTest extends ItemTransactionTest {
	
	@Test
	public void test(){
		
		SalesOrderLine line = buildSalesOrderLine();
		
		List<Transaction> trans = createTransactions(line);
		
		verifyTransactions(trans);
		
		List<ItemStockCard> stackCardList = new ArrayList<ItemStockCard>();
		
		updateItemStockCard(trans , stackCardList);
		
		verifyStockCard(stackCardList);
		
		List<ItemPlanning> planList = new ArrayList<ItemPlanning>();
		
		createItemPlanning(trans , planList);
		
		verifyPlanning(planList);
		
	}
	
	private List<Transaction> verifyTransactions(List<Transaction> trans) {
		
		assertEquals(1 , trans.size());
		
		for(Transaction t : trans){
			
			verify(t);
			
			SalesOrderItemTransaction t1 = (SalesOrderItemTransaction) t;
			
			assertNotNull(t1.getCustomer());

			assertEquals(QTY, t1.getQty());
			
			assertEquals(QTY, t1.getQtyOnSalesOrder());
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<ItemStockCard> stackCardList) {
		
		for(ItemStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(QTY , stockCard.getQtyOnSalesOrder());
		}
		
	}
	
	protected void verifyPlanning(List<ItemPlanning> planningList) {
		
		for(ItemPlanning plan : planningList){
			ItemOrderDemand demand = (ItemOrderDemand) plan;
			verify(demand);
		}
	}
}
