package com.cm.erp.domain;

import javax.persistence.Entity;

import com.codemonkey.utils.Calc;

@Entity
public class ItemOnhandSupply extends ItemOrderSupply {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ItemOnhandSupply(ItemTransaction tran) {
		super(tran);
	}
	
	public ItemOnhandSupply(ShipmentItemTransaction tran) {
		super(tran);
		setQty(-Calc.abs(tran.getQty()));
	}
	
}
