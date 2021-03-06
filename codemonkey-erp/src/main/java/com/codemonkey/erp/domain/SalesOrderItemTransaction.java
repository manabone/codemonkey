package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@DiscriminatorValue("SALES_ORDER")
public class SalesOrderItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private SalesOrderLine soLine;
	
	SalesOrderItemTransaction(){
		super();
	}
	
	public SalesOrderItemTransaction(SalesOrderLine soLine) {
		super(soLine);
		this.customer = soLine.getHeader().getCustomer();
		this.soLine = soLine;
		this.setQtyOnSalesOrder(soLine.getQty());
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		plannings.add(createItemOrderDemand());
		return plannings;
	}
	
	private ItemPlanning createItemOrderDemand() {
		ItemPlanning demand = create(new ItemOrderDemand());
		demand.setQty(getQty());
		demand.setDate(getRequiredDate());
		return demand;
	}

	public Customer getCustomer(){
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public DocumentLine getDocLine() {
		return soLine;
	}

}
