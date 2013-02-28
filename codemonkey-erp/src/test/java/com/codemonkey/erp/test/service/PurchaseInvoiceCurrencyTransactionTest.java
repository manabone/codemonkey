package com.codemonkey.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.codemonkey.erp.domain.CurrencyInvoiceDemand;
import com.codemonkey.erp.domain.CurrencyOrderDemand;
import com.codemonkey.erp.domain.CurrencyPlanning;
import com.codemonkey.erp.domain.CurrencyStockCard;
import com.codemonkey.erp.domain.PurchaseInvoiceCurrencyTransaction;
import com.codemonkey.erp.domain.PurchaseInvoiceLine;
import com.codemonkey.erp.domain.Transaction;


public class PurchaseInvoiceCurrencyTransactionTest extends CurrencyTransactionTest {
	
	@Test
	public void test(){
		
		PurchaseInvoiceLine line = buildPurchaseInvoiceLine();
		
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
			
			PurchaseInvoiceCurrencyTransaction t1 = (PurchaseInvoiceCurrencyTransaction) t;
			
			assertNotNull(t1.getVendor());
			
			assertEquals(NEG_AMOUNT , t1.getAmountOnPurchaseInvoice());
			
			assertEquals(AMOUNT , t1.getAmountOnPurchaseOrder());
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(AMOUNT , stockCard.getAmountOnPurchaseOrder());
			assertEquals(NEG_AMOUNT , stockCard.getAmountOnPurchaseInvoice());
		}
		
	}
	
	protected void verifyPlanning(List<CurrencyPlanning> planningList) {
		
		for(CurrencyPlanning plan : planningList){
			verify(plan);
		}
		
		CurrencyOrderDemand demand0 = (CurrencyOrderDemand) planningList.get(0);
		assertEquals(NEG_AMOUNT , demand0.getAmount());
		
		CurrencyInvoiceDemand demand1 = (CurrencyInvoiceDemand) planningList.get(1);
		assertEquals(AMOUNT , demand1.getAmount());
	}
}
