package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class SalesOrderTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SalesOrderTransaction(Item item, Double qty, Warehouse warehouse) {
		setItem(item);
		setQty(qty);
		setWarehouse(warehouse);
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
