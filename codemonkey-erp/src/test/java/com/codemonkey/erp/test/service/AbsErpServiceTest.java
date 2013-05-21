package com.codemonkey.erp.test.service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.AppUser;
import com.codemonkey.erp.domain.Currency;
import com.codemonkey.erp.domain.Customer;
import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.DocumentLine;
import com.codemonkey.erp.domain.Item;
import com.codemonkey.erp.domain.PurchaseInvoice;
import com.codemonkey.erp.domain.PurchaseInvoiceLine;
import com.codemonkey.erp.domain.PurchaseOrder;
import com.codemonkey.erp.domain.PurchaseOrderLine;
import com.codemonkey.erp.domain.PurchasePayment;
import com.codemonkey.erp.domain.PurchasePaymentLine;
import com.codemonkey.erp.domain.PurchaseReceipt;
import com.codemonkey.erp.domain.PurchaseReceiptLine;
import com.codemonkey.erp.domain.SalesCashReceipt;
import com.codemonkey.erp.domain.SalesCashReceiptLine;
import com.codemonkey.erp.domain.SalesInvoice;
import com.codemonkey.erp.domain.SalesInvoiceLine;
import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.erp.domain.SalesShipment;
import com.codemonkey.erp.domain.SalesShipmentLine;
import com.codemonkey.erp.domain.Vendor;
import com.codemonkey.erp.domain.Warehouse;
import com.codemonkey.erp.service.CustomerService;
import com.codemonkey.erp.service.ItemService;
import com.codemonkey.erp.service.VendorService;
import com.codemonkey.erp.service.WarehouseService;
import com.codemonkey.utils.SysUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/erp-test.xml" })
@Transactional
public class AbsErpServiceTest {

	public static final Double QTY = 7d;
	
	public static final Double NEG_QTY = -QTY;
	
	public static final Double PRICE = 11.5;
	
	public static final Double AMOUNT = PRICE * QTY;
	
	public static final Double NEG_AMOUNT = -AMOUNT;
	
	
	@Autowired private ItemService itemService;
	
	@Autowired private WarehouseService warehouseService;
	
	@Autowired private CustomerService customerService;
	
	@Autowired private VendorService vendorService;
	
	@Before
	public void initSysUtils(){
		SysUtils.putAttribute(SysUtils.CURRENCT_USER, new AppUser());
	}
	
	@Test
	public void test(){
		
	}
	
	Item buildItem(){
		Item item = new Item();
		itemService.save(item);
		return item;
	}
	
	Warehouse buildWarehouse(){
		Warehouse warehouse = new Warehouse();
		warehouseService.save(warehouse);
		return warehouse;
	}
	
	PurchaseReceipt buildPurchaseReceipt(){
		
		PurchaseReceipt rp = new PurchaseReceipt();
		
		setDocumentAttr(rp);
		
		return rp;
	}
	
	PurchaseReceiptLine buildPurchaseReceiptLine() {
		
		PurchaseReceipt rp = buildPurchaseReceipt();
		PurchaseReceiptLine line = new PurchaseReceiptLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(rp);
		line.setPurchaseOrderLine(buildPurchaseOrderLine());
		
		return line;
	}
	
	SalesShipment buildShipment(){
		
		SalesShipment sp = new SalesShipment();
		
		setDocumentAttr(sp);
		
		return sp;
	}
	
	
	SalesShipmentLine buildShipmentLine() {
		
		SalesShipment sp = buildShipment();
		sp.setCustomer(buildCustomer());
		SalesShipmentLine line = new SalesShipmentLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(sp);
		line.setSalesOrderLine(buildSalesOrderLine());
		
		return line;
	}
	
	PurchaseOrderLine buildPurchaseOrderLine(){
		PurchaseOrder po = buildPurchaseOrder();
		PurchaseOrderLine line = new PurchaseOrderLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(po);
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
	
	
	PurchasePaymentLine buildPurchasePaymentLine() {
		PurchasePayment pp = buildPurchasePayment();
		PurchasePaymentLine line = new PurchasePaymentLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(pp);
		line.setPrice(PRICE);
		return line;
	}
	
	PurchasePayment buildPurchasePayment() {
		PurchasePayment pp = new PurchasePayment();
		
		setDocumentAttr(pp);
		
		Vendor vendor = buildVendor();
		pp.setVendor(vendor);
		pp.setPaymentDate(new Date());
		
		return pp;
	}
	
	private void setDocumentAttr(Document doc){
		doc.setWarehouse(buildWarehouse());
		doc.setCurrency(Currency.RMB);
	}

	Vendor buildVendor() {
		Vendor vendor = new Vendor();
		vendorService.save(vendor);
		return vendor;
	}

	SalesOrderLine buildSalesOrderLine(){
		SalesOrder so = buildSalesOrder();
		SalesOrderLine line = new SalesOrderLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(so);
		line.setPrice(PRICE);
		
		return line;
	}
	
	SalesOrder buildSalesOrder() {
		SalesOrder so = new SalesOrder();

		setDocumentAttr(so);
		
		Customer customer = buildCustomer();
		so.setCustomer(customer);
		so.setPaymentDate(new Date());
		
		return so;
	}
	
	private void setDocumentLineAttr(DocumentLine line){
		line.setQty(QTY);
		line.setItem(buildItem());
		line.setRequiredDate(new Date());
	}

	SalesInvoiceLine buildSalesInvoiceLine(){
		
		SalesInvoice si = buildSalesInvoice();
		SalesInvoiceLine line = new SalesInvoiceLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(si);
		line.setPrice(PRICE);
		
		return line;
	}
	
	SalesInvoice buildSalesInvoice(){
		SalesInvoice si = new SalesInvoice();

		setDocumentAttr(si);
		
		Customer customer = buildCustomer();
		si.setCustomer(customer);
		si.setPaymentDate(new Date());
		
		return si;
	}
	
	SalesCashReceiptLine buildSalesCashReceiptLine(){
		
		SalesCashReceipt scr = buildSalesCashReceipt();
		SalesCashReceiptLine line = new SalesCashReceiptLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(scr);
		line.setPrice(PRICE);
		
		return line;
	}
	
	SalesCashReceipt buildSalesCashReceipt(){
		SalesCashReceipt scr = new SalesCashReceipt();

		setDocumentAttr(scr);
		
		Customer customer = buildCustomer();
		scr.setCustomer(customer);
		scr.setPaymentDate(new Date());
		
		return scr;
	}
	
	PurchaseInvoiceLine buildPurchaseInvoiceLine(){
		
		PurchaseInvoice pi = buildPurchaseInvoice();
		PurchaseInvoiceLine line = new PurchaseInvoiceLine();
		
		setDocumentLineAttr(line);
		
		line.setHeader(pi);
		line.setPrice(PRICE);
		
		return line;
	}
	
	PurchaseInvoice buildPurchaseInvoice(){
		PurchaseInvoice pi = new PurchaseInvoice();

		setDocumentAttr(pi);
		
		Vendor vendor = buildVendor();
		pi.setVendor(vendor);
		pi.setPaymentDate(new Date());
		
		return pi;
	}

	Customer buildCustomer() {
		Customer customer = new Customer();
		customerService.save(customer);
		return customer;
	}
	
}
