package com.codemonkey.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.codemonkey.erp.domain.CurrencyOrderSupply;
import com.codemonkey.erp.domain.CurrencyPlanning;
import com.codemonkey.erp.domain.CurrencyStockCard;
import com.codemonkey.erp.domain.SalesOrderCurrencyTransaction;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.erp.domain.Transaction;


public class SalesOrderCurrencyTransactionTest extends CurrencyTransactionTest {
	
	@Test
	public void test4SalesOrderLine(){
		
		SalesOrderLine line = buildSalesOrderLine();
		
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
			
			SalesOrderCurrencyTransaction t1 = (SalesOrderCurrencyTransaction) t;
			
			assertNotNull(t1.getCustomer());
			
			assertEquals(AMOUNT , t1.getAmountOnSalesOrder());
			
		}
		
		return trans;
		
	}
	
	private void verifyStockCard(List<CurrencyStockCard> stackCardList) {
		
		for(CurrencyStockCard stockCard : stackCardList){
			verify(stockCard);
			assertEquals(AMOUNT , stockCard.getAmountOnSalesOrder());
		}
		
	}
	
	protected void verifyPlanning(List<CurrencyPlanning> planningList) {
		
		for(CurrencyPlanning plan : planningList){
			CurrencyOrderSupply supply = (CurrencyOrderSupply) plan;
			verify(supply);
			assertEquals(AMOUNT , supply.getAmount());
		}
	}
}
