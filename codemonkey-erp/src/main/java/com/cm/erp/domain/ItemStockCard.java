package com.cm.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.domain.AbsEE;

@Entity
public class ItemStockCard  extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Warehouse warehouse;
	
	private Double qtyOnHand = 0d;

	private Double qtyRequired = 0d;
	
	public ItemStockCard(Item item, Warehouse warehouse) {
		this.item = item;
		this.warehouse = warehouse;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Double getQtyOnHand() {
		return qtyOnHand;
	}

	public void setQtyOnHand(Double qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}

	public Double getQtyRequired() {
		return qtyRequired;
	}

	public void setQtyRequired(Double qtyRequired) {
		this.qtyRequired = qtyRequired;
	}
}
