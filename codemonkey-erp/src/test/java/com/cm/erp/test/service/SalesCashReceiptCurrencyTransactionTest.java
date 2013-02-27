package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.CurrencyInvoiceSupply;
import com.cm.erp.domain.CurrencyOnHandSupply;
import com.cm.erp.domain.CurrencyPlanning;
import com.cm.erp.domain.CurrencyStockCard;
import com.cm.erp.domain.SalesCashReceiptCurrencyTransaction;
import com.cm.erp.domain.SalesCashReceiptLine;
import com.cm.erp.domain.Transaction;


public class SalesCashReceiptCurrencyTransactionTest extends CurrencyTransactionTest {
	
	@Test
	public void test(){
		
		SalesCashReceiptLine line = buildSalesCashReceiptLine();
		
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
			
			SalesCashReceiptCurrencyTransaction t1 = (SalesCashReceiptCurrencyTransaction) t;
			
			assertNotNull(t1.getCustomer());
			
			assertEquals(AMOUNT , t1.getAmountOnHand());
			
			assertEquals(NEG_AMOUNT , t1.getAmountOnSalesInvoice());
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(AMOUNT , stockCard.getAmountOnHand());
			assertEquals(NEG_AMOUNT , stockCard.getAmountOnSalesInvoice());
		}
		
	}
	
	protected void verifyPlanning(List<CurrencyPlanning> planningList) {
		
		for(CurrencyPlanning plan : planningList){
			verify(plan);
		}
		
		CurrencyOnHandSupply supply0 = (CurrencyOnHandSupply) planningList.get(0);
		assertEquals(AMOUNT , supply0.getAmount());
		
		CurrencyInvoiceSupply supply1 = (CurrencyInvoiceSupply) planningList.get(1);
		assertEquals(NEG_AMOUNT , supply1.getAmount());
	}
}
