package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class ShipmentItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private ShipmentLine spLine;
	
	public ShipmentItemTransaction(ShipmentLine spLine) {
		super(spLine);
		this.customer = spLine.getSalesOrderLine().getHeader().getCustomer();
		this.setSpLine(spLine);
	}
	
	@Override
	public DocumentLine getDocLine() {
		return getSpLine();
	}

	@Override
	public void updateStockCard(ItemStockCard stockCard) {
		stockCard.setQtyOnHand(Calc.sub(stockCard.getQtyOnHand() , getQty()));
		stockCard.setQtyOnSalesOrder(Calc.sub(stockCard.getQtyOnSalesOrder() , getQty()));
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		
		ItemOrderDemand demand = new ItemOrderDemand(this);
		ItemOnhandSupply supply = new ItemOnhandSupply(this);
		
		plannings.add(demand);
		plannings.add(supply);
		
		return plannings;
	}
	
	public Customer getCustomer(){
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ShipmentLine getSpLine() {
		return spLine;
	}

	public void setSpLine(ShipmentLine spLine) {
		this.spLine = spLine;
	}

}
