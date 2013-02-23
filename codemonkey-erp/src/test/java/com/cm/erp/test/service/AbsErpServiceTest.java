package com.cm.erp.test.service;

import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cm.erp.domain.Customer;
import com.cm.erp.domain.Item;
import com.cm.erp.domain.SalesOrder;
import com.cm.erp.domain.SalesOrderLine;
import com.cm.erp.domain.Warehouse;
import com.cm.erp.service.CustomerService;
import com.cm.erp.service.ItemService;
import com.cm.erp.service.WarehouseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/erp-test.xml" })
@Transactional
public class AbsErpServiceTest {

	public static final Double QTY = 7d;
	
	public static final Double PRICE = 11.5;
	
	@Autowired private ItemService itemService;
	
	@Autowired private WarehouseService warehouseService;
	
	@Autowired private CustomerService customerService;
	
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
	
	SalesOrderLine buildSalesOrderLine(){
		SalesOrder so = buildSalesOrder();
		SalesOrderLine line = new SalesOrderLine();
		line.setItem(buildItem());
		line.setSalesOrder(so);
		line.setQty(QTY);
		line.setPrice(PRICE);
		line.setRequiredDate(new Date());
		return line;
	}

	public SalesOrder buildSalesOrder() {
		SalesOrder so = new SalesOrder();
		Customer customer = buildCustomer();
		so.setCustomer(customer);
		so.setWarehouse(buildWarehouse());
		return so;
	}

	private Customer buildCustomer() {
		Customer customer = new Customer();
		customerService.doSave(customer);
		return customer;
	}
	
}
