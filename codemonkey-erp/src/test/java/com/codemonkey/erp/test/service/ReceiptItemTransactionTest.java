package com.codemonkey.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.codemonkey.erp.domain.ItemOnhandSupply;
import com.codemonkey.erp.domain.ItemOrderSupply;
import com.codemonkey.erp.domain.ItemPlanning;
import com.codemonkey.erp.domain.ItemStockCard;
import com.codemonkey.erp.domain.ReceiptItemTransaction;
import com.codemonkey.erp.domain.ReceiptLine;
import com.codemonkey.erp.domain.Transaction;


public class ReceiptItemTransactionTest extends ItemTransactionTest {
	
	@Test
	public void test(){
		
		//1-test transactions
		ReceiptLine line = buildReceiptLine();
		
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
			
			ReceiptItemTransaction t1 = (ReceiptItemTransaction) t;
			
			assertNotNull(t1.getVendor());
			
			assertEquals(QTY, t1.getQty());
			
			assertEquals(NEG_QTY, t1.getQtyOnPurchaseOrder());
			
			assertEquals(QTY, t1.getQtyOnHand());
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<ItemStockCard> stackCardList) {
		
		for(ItemStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(NEG_QTY , stockCard.getQtyOnPurchaseOrder());
			assertEquals(QTY , stockCard.getQtyOnHand());
		}
		
	}
	
	protected void verifyPlanning(List<ItemPlanning> planningList) {
		
		for(ItemPlanning plan : planningList){
			verify(plan);
		}
		
		ItemOrderSupply orderSupply = (ItemOrderSupply) planningList.get(0);
		assertEquals(NEG_QTY , orderSupply.getQty());
		
		ItemOnhandSupply onhandsupply = (ItemOnhandSupply)  planningList.get(1);
		assertEquals(QTY , onhandsupply.getQty());
		
	}
}
