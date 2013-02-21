package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class SalesOrderItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SalesOrderItemTransaction(SalesOrderLine soLine) {
		setItem(soLine.getItem());
		setQty(soLine.getQty());
		setWarehouse(soLine.getWarehouse());
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

}
