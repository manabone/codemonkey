package com.codemonkey.erp.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.erp.service.SalesOrderLineService;
import com.codemonkey.erp.service.SalesOrderService;

public class SalesOrderServiceTest extends AbsErpServiceTest {

	@Autowired private SalesOrderService salesOrderService;
	@Autowired private SalesOrderLineService salesOrderLineService;
	
	@Test
	public void testSavingLines(){
		
//		SalesOrder so = new SalesOrder();
//		
//		SalesOrderLine soLine1 = new SalesOrderLine();
//		SalesOrderLine soLine2 = new SalesOrderLine();
//		
//		soLine1.setSalesOrder(so);
//		so.getLines().add(soLine1);
//		soLine2.setSalesOrder(so);
//		so.getLines().add(soLine2);
//		
//		salesOrderService.doSave(so);
//		
//		List<SalesOrderLine> lines2 = salesOrderLineService.findAllBy("salesOrder.id", so.getId());
//		
//		assertEquals(2 , lines2.size());
//		assertNotNull(lines2.get(0).getSalesOrder());
//		assertNotNull(lines2.get(1).getSalesOrder());
	}
}
