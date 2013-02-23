package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cm.erp.domain.ItemTransaction;
import com.cm.erp.domain.SalesOrderItemTransaction;
import com.cm.erp.domain.SalesOrderLine;
import com.cm.erp.domain.Transaction;
import com.cm.erp.service.ItemTransactionFactory;

public class ItemTransactionTest extends AbsErpServiceTest {

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	
	@Test
	public void test4SalesOrderLine(){
		
		SalesOrderLine line = buildSalesOrderLine();
		
		assertNotNull(itemTransactionFactory);
		
		List<Transaction> trans = itemTransactionFactory.createTransactions(line);
		
		assertEquals(1 , trans.size());
		
		verify(trans.get(0));
		
		SalesOrderItemTransaction t = (SalesOrderItemTransaction) trans.get(0);
		
		assertNotNull(t.getCustomer());
		assertNull(t.getCost());
		assertNotNull(t.getAmount());
		
	}

	private void verify(Transaction tran) {
		ItemTransaction t = (ItemTransaction) tran;
		assertNotNull(t.getItem());
		assertNotNull(t.getQty());
		assertNotNull(t.getWarehouse());
		assertNotNull(t.getDocLine());
	}
}
