package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
@DiscriminatorValue("SALES_SHIPMENT")
public class ShipmentItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private SalesShipmentLine spLine;
	
	ShipmentItemTransaction(){}
	
	public ShipmentItemTransaction(SalesShipmentLine spLine) {
		super(spLine);
		this.customer = spLine.getSalesOrderLine().getHeader().getCustomer();
		this.setSpLine(spLine);
		this.setQtyOnHand(Calc.neg(spLine.getQty()));
		this.setQtyOnSalesOrder(Calc.neg(spLine.getQty()));
	}
	
	@Override
	public DocumentLine getDocLine() {
		return getSpLine();
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		
		plannings.add(createItemOrderDemand());
		plannings.add(createItemOnhandSupply());
		
		return plannings;
	}
	
	private ItemPlanning createItemOnhandSupply() {
		ItemPlanning supply = create(new ItemOnhandSupply());
		supply.setQty(Calc.neg(getQty()));
		return supply;
	}

	private ItemPlanning createItemOrderDemand() {
		ItemPlanning demand = create(new ItemOrderDemand());
		demand.setQty(Calc.neg(getQty()));
		return demand;
	}

	public Customer getCustomer(){
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SalesShipmentLine getSpLine() {
		return spLine;
	}

	public void setSpLine(SalesShipmentLine spLine) {
		this.spLine = spLine;
	}

}
