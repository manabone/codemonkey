package com.cm.erp.test.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cm.erp.domain.Customer;
import com.cm.erp.domain.Item;
import com.cm.erp.domain.SalesOrder;
import com.cm.erp.domain.SalesOrderLine;
import com.cm.erp.domain.Warehouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/app-erp-test.xml" })
@Transactional
public class AbsErpServiceTest {

	public static final double QTY = 7;
	
	public static final double PRICE = 11.5;
	
	Item buildItem(){
		Item item = new Item();
		return item;
	}
	
	Warehouse buildWarehouse(){
		Warehouse warehouse = new Warehouse();
		return warehouse;
	}
	
	SalesOrderLine buildSalesOrderLine(){
		SalesOrder so = buildSalesOrder();
		SalesOrderLine line = new SalesOrderLine();
		line.setItem(buildItem());
		line.setSalesOrder(so);
		line.setQty(QTY);
		line.setPrice(PRICE);
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
		return customer;
	}
	
}
