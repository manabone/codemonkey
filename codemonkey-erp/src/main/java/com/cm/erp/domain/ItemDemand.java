package com.cm.erp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ItemDemand")
public class ItemDemand extends ItemPlanning {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemDemand(ItemTransaction trans) {
		setItem(trans.getItem());
		setQty(trans.getQty());
		setWarehouse(trans.getWarehouse());
		setTransaction(trans);
	}

}
