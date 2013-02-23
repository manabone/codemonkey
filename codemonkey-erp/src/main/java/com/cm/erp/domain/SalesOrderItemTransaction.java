package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SalesOrderItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Customer customer;
	
	public SalesOrderItemTransaction(SalesOrderLine soLine) {
		super(soLine);
		setItem(soLine.getItem());
		setQty(soLine.getQty());
		setAmount(soLine.getAmount());
		setWarehouse(soLine.getWarehouse());
		this.customer = soLine.getHeader().getCustomer();
	}

	@Override
	public void updateStockCard(ItemStockCard stockCard) {
		stockCard.setQtyRequired(stockCard.getQtyRequired() + getQty());
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		plannings.add(new ItemDemand(this));
		return plannings;
	}
	
	public Customer getCustomer(){
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
