package com.codemonkey.erp.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.codemonkey.erp.domain.Customer;
import com.codemonkey.erp.domain.Item;
import com.codemonkey.erp.domain.PurchaseOrder;
import com.codemonkey.erp.domain.PurchaseOrderLine;
import com.codemonkey.erp.domain.PurchaseReceipt;
import com.codemonkey.erp.domain.PurchaseReceiptLine;
import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.erp.domain.SalesShipment;
import com.codemonkey.erp.domain.SalesShipmentLine;
import com.codemonkey.erp.domain.Vendor;
import com.codemonkey.erp.domain.Warehouse;
import com.codemonkey.erp.service.DocumentFactory;


public class DocumentFactoryTest extends AbsErpServiceTest {

	@Test
	public void testCreateSalesShipment(){
		
		SalesOrder so = new SalesOrder();
		
		so.setCustomer(new Customer());
		so.setWarehouse(new Warehouse());
		
		List<SalesOrderLine> soLines = new ArrayList<SalesOrderLine>();
		
		SalesOrderLine soLine1 = new SalesOrderLine();
		soLine1.setItem(new Item());
		soLine1.setQty(QTY);
		soLine1.setRequiredDate(new Date());
		
		soLines.add(soLine1);
		
		so.setLines(soLines);
		
		SalesShipment sp = DocumentFactory.createSalesShipment(so);
		
		assertNotNull(sp.getWarehouse());
		assertNotNull(sp.getCustomer());
		
		List<SalesShipmentLine> spLines = sp.getLines();
		
		assertEquals(1 , spLines.size());
		
		assertNotNull(spLines.get(0).getItem());
		assertEquals(QTY , spLines.get(0).getQty());
		assertNotNull(spLines.get(0).getRequiredDate());
		assertNotNull(spLines.get(0).getSalesOrderLine());
		
	}
	
	@Test
	public void testCreatePurchaseReceipt(){
		
		PurchaseOrder po = new PurchaseOrder();
		
		po.setVendor(new Vendor());
		po.setWarehouse(new Warehouse());
		
		List<PurchaseOrderLine> poLines = new ArrayList<PurchaseOrderLine>();
		
		PurchaseOrderLine poLine = new PurchaseOrderLine();
		poLine.setItem(new Item());
		poLine.setQty(QTY);
		poLine.setRequiredDate(new Date());
		
		poLines.add(poLine);
		
		po.setLines(poLines);
		
		PurchaseReceipt pr = DocumentFactory.createPurchaseReceipt(po);
		
		assertNotNull(pr.getWarehouse());
		
		List<PurchaseReceiptLine> rpLlines = pr.getLines();
		
		assertEquals(1 , rpLlines.size());
		
		assertNotNull(rpLlines.get(0).getItem());
		assertEquals(QTY , rpLlines.get(0).getQty());
		assertNotNull(rpLlines.get(0).getRequiredDate());
		assertNotNull(rpLlines.get(0).getPurchaseOrderLine());
		
	}
}
