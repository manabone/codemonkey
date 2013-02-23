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
	
	public ItemDemand(ItemTransaction tran) {
		super(tran);
	}

}
