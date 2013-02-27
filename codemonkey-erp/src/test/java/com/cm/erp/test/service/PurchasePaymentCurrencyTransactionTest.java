package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.CurrencyInvoiceDemand;
import com.cm.erp.domain.CurrencyOnHandSupply;
import com.cm.erp.domain.CurrencyPlanning;
import com.cm.erp.domain.CurrencyStockCard;
import com.cm.erp.domain.PurchasePaymentCurrencyTransaction;
import com.cm.erp.domain.PurchasePaymentLine;
import com.cm.erp.domain.Transaction;


public class PurchasePaymentCurrencyTransactionTest extends CurrencyTransactionTest {
	
	@Test
	public void test(){
		
		PurchasePaymentLine line = buildPurchasePaymentLine();
		
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
			
			PurchasePaymentCurrencyTransaction t1 = (PurchasePaymentCurrencyTransaction) t;
			
			assertNotNull(t1.getVendor());
			
			assertEquals(NEG_AMOUNT , t1.getAmountOnHand());
			assertEquals(AMOUNT , t1.getAmountOnPurchaseInvoice());
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(NEG_AMOUNT , stockCard.getAmountOnHand());
			assertEquals(AMOUNT , stockCard.getAmountOnPurchaseInvoice());
		}
		
	}
	
	protected void verifyPlanning(List<CurrencyPlanning> planningList) {
		
		for(CurrencyPlanning plan : planningList){
			verify(plan);
		}
		
		CurrencyInvoiceDemand demand = (CurrencyInvoiceDemand) planningList.get(0);
		assertEquals(NEG_AMOUNT , demand.getAmount());
		
		CurrencyOnHandSupply supply = (CurrencyOnHandSupply) planningList.get(1);
		assertEquals(NEG_AMOUNT , supply.getAmount());
	}
}
