package com.codemonkey.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.codemonkey.erp.domain.CurrencyOrderDemand;
import com.codemonkey.erp.domain.CurrencyPlanning;
import com.codemonkey.erp.domain.CurrencyStockCard;
import com.codemonkey.erp.domain.PurchaseOrderCurrencyTransaction;
import com.codemonkey.erp.domain.PurchaseOrderLine;
import com.codemonkey.erp.domain.Transaction;


public class PurchaseOrderCurrencyTransactionTest extends CurrencyTransactionTest {
	
	@Test
	public void test(){
		
		PurchaseOrderLine line = buildPurchaseOrderLine();
		
		List<Transaction> trans = createTransactions(line);
		
		verifyTransactions(trans);
		
		List<CurrencyStockCard> stackCardList = new ArrayList<CurrencyStockCard>();
		
		updateCurrencyStockCard(trans , stackCardList);
		
		verifyStockCard(stackCardList);
		
		List<CurrencyPlanning> planList = new ArrayList<CurrencyPlanning>();
		
		createCurrencyPlanning(trans , planList);
		
		verifyPlanning(planList);
		
	}
	
	private List<Transaction> verifyTransactions(List<Transaction> trans) {
		
		assertEquals(1 , trans.size());
		
		for(Transaction t : trans){
			
			verify(t);
			
			PurchaseOrderCurrencyTransaction t1 = (PurchaseOrderCurrencyTransaction) t;
			
			assertNotNull(t1.getVendor());
			
			assertEquals(NEG_AMOUNT , t1.getAmountOnPurchaseOrder());
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(NEG_AMOUNT , stockCard.getAmountOnPurchaseOrder());
		}
		
	}
	
	protected void verifyPlanning(List<CurrencyPlanning> planningList) {
		
		for(CurrencyPlanning plan : planningList){
			CurrencyOrderDemand demand = (CurrencyOrderDemand) plan;
			verify(demand);
			assertEquals(AMOUNT , demand.getAmount());
		}
	}
}
