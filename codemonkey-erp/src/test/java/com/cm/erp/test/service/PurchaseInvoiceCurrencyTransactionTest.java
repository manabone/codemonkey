package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.CurrencyInvoiceDemand;
import com.cm.erp.domain.CurrencyOrderDemand;
import com.cm.erp.domain.CurrencyPlanning;
import com.cm.erp.domain.CurrencyStockCard;
import com.cm.erp.domain.PurchaseInvoiceCurrencyTransaction;
import com.cm.erp.domain.PurchaseInvoiceLine;
import com.cm.erp.domain.Transaction;


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
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(NEG_AMOUNT , stockCard.getAmountOnPurchaseOrder());
			assertEquals(AMOUNT , stockCard.getAmountOnPurchaseInvoice());
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
