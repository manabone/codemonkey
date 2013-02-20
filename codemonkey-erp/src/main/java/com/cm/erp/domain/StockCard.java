package com.cm.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.domain.AbsEE;

@Entity
public class StockCard  extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Warehouse warehouse;
	
	private Double qtyOnHand;

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
}
