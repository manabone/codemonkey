package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.Date;
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
	
	@ManyToOne
	private SalesOrderLine soLine;
	
	private Date requiredDate; 
	
	public SalesOrderItemTransaction(SalesOrderLine soLine) {
		super(soLine);
		this.customer = soLine.getHeader().getCustomer();
		this.soLine = soLine;
		this.requiredDate = soLine.getRequiredDate();
	}

	@Override
	public void updateStockCard(ItemStockCard stockCard) {
		stockCard.setQtyRequired(stockCard.getQtyRequired() + getQty());
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		ItemDemand demand = new ItemDemand(this);
		demand.setDate(getRequiredDate());
		plannings.add(demand);
		return plannings;
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

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

}
