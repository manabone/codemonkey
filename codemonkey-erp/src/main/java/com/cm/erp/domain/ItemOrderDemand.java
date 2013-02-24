package com.cm.erp.domain;

import javax.persistence.Entity;

import com.codemonkey.utils.Calc;

@Entity
public class ItemOrderDemand extends ItemPlanning {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemOrderDemand(ItemTransaction tran) {
		super(tran);
	}
	
	public ItemOrderDemand(ShipmentItemTransaction tran) {
		super(tran);
		setQty(-Calc.abs(tran.getQty()));
	}

}
