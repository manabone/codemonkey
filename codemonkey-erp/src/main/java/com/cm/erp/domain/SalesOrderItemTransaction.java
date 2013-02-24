package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

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
	
	public SalesOrderItemTransaction(SalesOrderLine soLine) {
		super(soLine);
		this.customer = soLine.getHeader().getCustomer();
		this.soLine = soLine;
	}

	@Override
	public void updateStockCard(ItemStockCard stockCard) {
		stockCard.setQtyOnSalesOrder(Calc.add(stockCard.getQtyOnSalesOrder() , getQty()));
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		ItemOrderDemand demand = new ItemOrderDemand(this);
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

}
