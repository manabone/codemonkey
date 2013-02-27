package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.ItemOnhandSupply;
import com.cm.erp.domain.ItemOrderDemand;
import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.ShipmentItemTransaction;
import com.cm.erp.domain.ShipmentLine;
import com.cm.erp.domain.Transaction;


public class ShipmentItemTransactionTest extends ItemTransactionTest {
	
	@Test
	public void test(){
		
		//1-test transactions
		ShipmentLine line = buildShipmentLine();
		
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
			
			ShipmentItemTransaction t1 = (ShipmentItemTransaction) t;
			
			assertNotNull(t1.getCustomer());
			
			assertEquals(QTY, t1.getQty());
			
			assertEquals(NEG_QTY, t1.getQtyOnHand());
			
			assertEquals(NEG_QTY, t1.getQtyOnSalesOrder());
			
		
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<ItemStockCard> stackCardList) {
		
		for(ItemStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(NEG_QTY , stockCard.getQtyOnSalesOrder());
			assertEquals(NEG_QTY , stockCard.getQtyOnHand());
		}
		
	}
	
	protected void verifyPlanning(List<ItemPlanning> planningList) {
		
		for(ItemPlanning plan : planningList){
			verify(plan);
		}
		
		ItemOrderDemand orderDemand = (ItemOrderDemand) planningList.get(0);
		assertEquals(NEG_QTY , orderDemand.getQty());
		
		ItemOnhandSupply onhandsupply = (ItemOnhandSupply)  planningList.get(1);
		assertEquals(NEG_QTY , onhandsupply.getQty());
		
	}
}
