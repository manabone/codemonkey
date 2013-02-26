package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cm.erp.domain.CurrencyInvoiceSupply;
import com.cm.erp.domain.CurrencyOrderSupply;
import com.cm.erp.domain.CurrencyPlanning;
import com.cm.erp.domain.CurrencyStockCard;
import com.cm.erp.domain.SalesInvoiceCurrencyTransaction;
import com.cm.erp.domain.SalesInvoiceLine;
import com.cm.erp.domain.Transaction;


public class SalesCashReceiptCurrencyTransactionTest extends CurrencyTransactionTest {
	
	@Test
	public void test(){
		
		SalesInvoiceLine line = buildSalesInvoiceLine();
		
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
			
			SalesInvoiceCurrencyTransaction t1 = (SalesInvoiceCurrencyTransaction) t;
			
			assertNotNull(t1.getCustomer());
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(NEG_AMOUNT , stockCard.getAmountOnSalesOrder());
			assertEquals(AMOUNT , stockCard.getAmountOnSalesInvoice());
		}
		
	}
	
	protected void verifyPlanning(List<CurrencyPlanning> planningList) {
		
		for(CurrencyPlanning plan : planningList){
			verify(plan);
		}
		
		CurrencyOrderSupply supply0 = (CurrencyOrderSupply) planningList.get(0);
		assertEquals(NEG_AMOUNT , supply0.getAmount());
		
		CurrencyInvoiceSupply supply1 = (CurrencyInvoiceSupply) planningList.get(1);
		assertEquals(AMOUNT , supply1.getAmount());
	}
}
