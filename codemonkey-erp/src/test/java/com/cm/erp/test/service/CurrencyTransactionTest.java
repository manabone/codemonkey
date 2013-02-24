package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.CurrencyPlanning;
import com.cm.erp.domain.CurrencyStockCard;
import com.cm.erp.domain.CurrencyTransaction;
import com.cm.erp.domain.Transaction;
import com.cm.erp.service.CurrencyStockCardService;
import com.cm.erp.service.CurrencyTransactionFactory;

public class CurrencyTransactionTest extends AbsErpServiceTest {

	@Autowired private CurrencyTransactionFactory currencyTransactionFactory;
	@Autowired private CurrencyStockCardService currencyStockCardService;
	
	@Test
	public void testServices(){
		assertNotNull(currencyTransactionFactory);
		assertNotNull(currencyStockCardService);
	}
	
	protected List<Transaction> createTransactions(DocumentLine line) {
		List<Transaction> trans = currencyTransactionFactory.createTransactions(line);
		return trans;
	}
	
	protected void createCurrencyPlanning(List<Transaction> trans,
			List<CurrencyPlanning> planList) {
		
		for(Transaction t : trans){
			
			CurrencyTransaction tran = (CurrencyTransaction) t;
			
			//create planning
			if(CollectionUtils.isNotEmpty(tran.createPlanning())){
				planList.addAll(tran.createPlanning());
			}
		}
	}
	
	void updateCurrencyStockCard(List<Transaction> trans,
			List<CurrencyStockCard> stackCardList) {
		
		if(CollectionUtils.isEmpty(trans)) {
			return ;
		}
		
		for(Transaction t : trans){
			CurrencyTransaction tran = (CurrencyTransaction) t;
			CurrencyStockCard stockCard = currencyStockCardService.getStockCard(tran.getCurrency());
			tran.updateStockCard(stockCard);
			
			stackCardList.add(stockCard);
		}
		
	}
	
	public void verify(CurrencyPlanning plan) {
		assertNotNull(plan.getCurrency());
		assertNotNull(plan.getDate());
		assertEquals(AMOUNT , plan.getAmount());
		assertNotNull(plan.getCurrencyTransaction());
	}

	public void verify(CurrencyStockCard stockCard) {
		assertNotNull(stockCard.getCurrency());
	}


	protected void verify(Transaction tran) {
		CurrencyTransaction t = (CurrencyTransaction) tran;
		assertNotNull(t.getCurrency());
		assertNotNull(t.getAmount());
		assertNotNull(t.getDocLine());
	}
}
