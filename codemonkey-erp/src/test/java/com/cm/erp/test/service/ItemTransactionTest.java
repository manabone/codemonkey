package com.cm.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cm.erp.domain.ItemDemand;
import com.cm.erp.domain.ItemPlanning;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.ItemTransaction;
import com.cm.erp.domain.SalesOrderItemTransaction;
import com.cm.erp.domain.SalesOrderLine;
import com.cm.erp.domain.Transaction;
import com.cm.erp.service.ItemStockCardService;
import com.cm.erp.service.ItemTransactionFactory;

public class ItemTransactionTest extends AbsErpServiceTest {

	@Autowired private ItemTransactionFactory itemTransactionFactory;
	@Autowired private ItemStockCardService itemStockCardService;
	
	@Test
	public void test4SalesOrderLine(){
		
		SalesOrderLine line = buildSalesOrderLine();
		
		assertNotNull(itemTransactionFactory);
		
		List<Transaction> trans = verifySalesOrderTransactions(line);
		
		verifySalesOrderStockCard(trans);
		
		verifySalesOrderPlanning(trans);
		
	}

	private void verifySalesOrderPlanning(List<Transaction> trans) {
		
		List<ItemPlanning> planningList = new ArrayList<ItemPlanning>();
		
		for(Transaction t : trans){
			
			ItemTransaction tran = (ItemTransaction) t;
			
			//create planning
			if(CollectionUtils.isNotEmpty(tran.createPlanning())){
				planningList.addAll(tran.createPlanning());
			}
		}
		
		for(ItemPlanning plan : planningList){
			ItemDemand demand = (ItemDemand) plan;
			assertNotNull(demand.getItem());
			assertNotNull(demand.getDate());
			assertEquals(QTY , demand.getQty());
			assertNotNull(demand.getItemTransaction());
			assertNotNull(demand.getWarehouse());
		}
	}

	private void verifySalesOrderStockCard(List<Transaction> trans) {
		
		if(CollectionUtils.isEmpty(trans)) {
			return;
		}
		
		for(Transaction t : trans){
			ItemTransaction tran = (ItemTransaction) t;
			ItemStockCard stockCard = itemStockCardService.getStockCard(tran.getItem() , tran.getWarehouse());
			tran.updateStockCard(stockCard);
			
			assertEquals(QTY , stockCard.getQtyRequired());
			assertNotNull(stockCard.getWarehouse());
			assertNotNull(stockCard.getItem());
		}
		
	}

	private List<Transaction> verifySalesOrderTransactions(SalesOrderLine line) {
		
		List<Transaction> trans = itemTransactionFactory.createTransactions(line);
		
		assertEquals(1 , trans.size());
		
		verify(trans.get(0));
		
		SalesOrderItemTransaction t = (SalesOrderItemTransaction) trans.get(0);
		
		assertNotNull(t.getCustomer());
		
		return trans;
		
	}

	private void verify(Transaction tran) {
		ItemTransaction t = (ItemTransaction) tran;
		assertNotNull(t.getItem());
		assertNotNull(t.getQty());
		assertNotNull(t.getWarehouse());
		assertNotNull(t.getDocLine());
	}
}
