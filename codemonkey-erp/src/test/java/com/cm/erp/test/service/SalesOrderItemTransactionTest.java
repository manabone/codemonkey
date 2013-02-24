package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.ItemDemand;
import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.SalesOrderItemTransaction;
import com.cm.erp.domain.SalesOrderLine;
import com.cm.erp.domain.Transaction;


public class SalesOrderItemTransactionTest extends ItemTransactionTest {
	
	@Test
	public void test4SalesOrderLine(){
		
		SalesOrderLine line = buildSalesOrderLine();
		
		List<Transaction> trans = createTransactions(line);
		
		verifySalesOrderTransactions(trans);
		
		List<ItemStockCard> stackCardList = new ArrayList<ItemStockCard>();
		
		upadteItemStockCard(trans , stackCardList);
		
		verifySalesOrderStockCard(stackCardList);
		
		List<ItemPlanning> planList = new ArrayList<ItemPlanning>();
		
		createItemPlanning(trans , planList);
		
		verifySalesOrderPlanning(planList);
		
	}
	
	private List<Transaction> verifySalesOrderTransactions(List<Transaction> trans) {
		
		assertEquals(1 , trans.size());
		
		for(Transaction t : trans){
			
			verify(t);
			
			SalesOrderItemTransaction t1 = (SalesOrderItemTransaction) t;
			
			assertNotNull(t1.getCustomer());
			
		}
		
		return trans;
		
	}
	
	private void verifySalesOrderStockCard(List<ItemStockCard> stackCardList) {
		
		for(ItemStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(QTY , stockCard.getQtyRequired());
		}
		
	}
	
	protected void verifySalesOrderPlanning(List<ItemPlanning> planningList) {
		
		for(ItemPlanning plan : planningList){
			ItemDemand demand = (ItemDemand) plan;
			verify(demand);
		}
	}
}
