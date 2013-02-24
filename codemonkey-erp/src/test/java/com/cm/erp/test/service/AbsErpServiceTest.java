package com.cm.erp.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cm.erp.domain.Currency;
import com.cm.erp.domain.Customer;
import com.cm.erp.domain.Document;
import com.cm.erp.domain.DocumentLine;
import com.cm.erp.domain.Item;
import com.cm.erp.domain.PurchaseOrder;
import com.cm.erp.domain.PurchaseOrderLine;
import com.cm.erp.domain.Receipt;
import com.cm.erp.domain.ReceiptLine;
import com.cm.erp.domain.SalesOrder;
import com.cm.erp.domain.SalesOrderLine;
import com.cm.erp.domain.Shipment;
import com.cm.erp.domain.ShipmentLine;
import com.cm.erp.domain.Vendor;
import com.cm.erp.domain.Warehouse;
import com.cm.erp.service.CustomerService;
import com.cm.erp.service.ItemService;
import com.cm.erp.service.VendorService;
import com.cm.erp.service.WarehouseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/erp-test.xml" })
@Transactional
public class AbsErpServiceTest {

	public static final Double QTY = 7d;
	
	public static final Double NEG_QTY = -QTY;
	
	public static final Double PRICE = 11.5;
	
	public static final Double AMOUNT = PRICE * QTY;
	
	@Autowired private ItemService itemService;
	
	@Autowired private WarehouseService warehouseService;
	
	@Autowired private CustomerService customerService;
	
	@Autowired private VendorService vendorService;
	
	@Test
	public void test(){
		
	}
	
	Item buildItem(){
		Item item = new Item();
		itemService.doSave(item);
		return item;
	}
	
	Warehouse buildWarehouse(){
		Warehouse warehouse = new Warehouse();
		warehouseService.doSave(warehouse);
		return warehouse;
	}
	
	Receipt buildReceipt(){
		
		Receipt rp = new Receipt();
		
		setDocumentAttr(rp);
		
		return rp;
	}
	
	
	ReceiptLine buildReceiptLine() {
		
		Receipt rp = buildReceipt();
		ReceiptLine line = new ReceiptLine();
		
		setDocumentLineAttr(line);
		
		line.setReceipt(rp);
		line.setPurchaseOrderLine(buildPurchaseOrderLine());
		
		return line;
	}
	
	Shipment buildShipment(){
		
		Shipment sp = new Shipment();
		
		setDocumentAttr(sp);
		
		return sp;
	}
	
	
	ShipmentLine buildShipmentLine() {
		
		Shipment sp = buildShipment();
		ShipmentLine line = new ShipmentLine();
		
		setDocumentLineAttr(line);
		
		line.setShipment(sp);
		line.setSalesOrderLine(buildSalesOrderLine());
		
		return line;
	}
	
	PurchaseOrderLine buildPurchaseOrderLine(){
		PurchaseOrder po = buildPurchaseOrder();
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		setDocumentLineAttr(line);
		
		line.setPurchaseOrder(po);
		line.setPrice(PRICE);
		return line;
	}
	
	PurchaseOrder buildPurchaseOrder() {
		PurchaseOrder po = new PurchaseOrder();
		
		setDocumentAttr(po);
		
		Vendor vendor = buildVendor();
		po.setVendor(vendor);
		po.setPaymentDate(new Date());
		
		return po;
	}
	
	private void setDocumentAttr(Document doc){
		doc.setWarehouse(buildWarehouse());
		doc.setCurrency(Currency.RMB);
	}

	Vendor buildVendor() {
		Vendor vendor = new Vendor();
		vendorService.doSave(vendor);
		return vendor;
	}

	SalesOrderLine buildSalesOrderLine(){
		SalesOrder so = buildSalesOrder();
		SalesOrderLine line = new SalesOrderLine();
		
		setDocumentLineAttr(line);
		
		line.setSalesOrder(so);
		line.setPrice(PRICE);
		
		return line;
	}
	
	private void setDocumentLineAttr(DocumentLine line){
		line.setQty(QTY);
		line.setItem(buildItem());
		line.setRequiredDate(new Date());
	}

	SalesOrder buildSalesOrder() {
		SalesOrder so = new SalesOrder();

		setDocumentAttr(so);
		
		Customer customer = buildCustomer();
		so.setCustomer(customer);
		so.setPaymentDate(new Date());
		
		return so;
	}

	Customer buildCustomer() {
		Customer customer = new Customer();
		customerService.doSave(customer);
		return customer;
	}
	
}
