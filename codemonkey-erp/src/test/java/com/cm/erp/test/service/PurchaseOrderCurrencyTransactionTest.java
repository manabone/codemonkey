package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.CurrencyDemand;
import com.cm.erp.domain.CurrencyPlanning;
import com.cm.erp.domain.CurrencyStockCard;
import com.cm.erp.domain.PurchaseOrderCurrencyTransaction;
import com.cm.erp.domain.PurchaseOrderLine;
import com.cm.erp.domain.Transaction;


public class PurchaseOrderCurrencyTransactionTest extends CurrencyTransactionTest {
	
	@Test
	public void test4PurchaseOrderLine(){
		
		PurchaseOrderLine line = buildPurchaseOrderLine();
		
		List<Transaction> trans = createTransactions(line);
		
		verifyPurchaseOrderTransactions(trans);
		
		List<CurrencyStockCard> stackCardList = new ArrayList<CurrencyStockCard>();
		
		updateCurrencyStockCard(trans , stackCardList);
		
		verifyPurchaseOrderStockCard(stackCardList);
		
		List<CurrencyPlanning> planList = new ArrayList<CurrencyPlanning>();
		
		createCurrencyPlanning(trans , planList);
		
		verifyPurchaseOrderPlanning(planList);
		
	}
	
	private List<Transaction> verifyPurchaseOrderTransactions(List<Transaction> trans) {
		
		assertEquals(1 , trans.size());
		
		for(Transaction t : trans){
			
			verify(t);
			
			PurchaseOrderCurrencyTransaction t1 = (PurchaseOrderCurrencyTransaction) t;
			
			assertNotNull(t1.getVendor());
			
		}
		
		return trans;
		
	}
	
	private void verifyPurchaseOrderStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(AMOUNT , stockCard.getAmountOnPurchaseOrder());
		}
		
	}
	
	protected void verifyPurchaseOrderPlanning(List<CurrencyPlanning> planningList) {
		
		for(CurrencyPlanning plan : planningList){
			CurrencyDemand demand = (CurrencyDemand) plan;
			verify(demand);
			assertEquals(AMOUNT , demand.getAmount());
		}
	}
}
