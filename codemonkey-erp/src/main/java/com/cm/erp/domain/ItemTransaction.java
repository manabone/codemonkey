package com.cm.erp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class ItemTransaction  extends Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Warehouse warehouse;
	
	private Double qty;
	
	ItemTransaction(DocumentLine docLine) {
		this.qty = docLine.getQty();
		this.item = docLine.getItem();
		setWarehouse(docLine.getWarehouse());
	}
	
	public abstract void updateStockCard(ItemStockCard stockCard);
	
	public abstract List<ItemPlanning> createPlanning();
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

}
