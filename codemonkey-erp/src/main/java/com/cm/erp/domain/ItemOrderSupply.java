package com.cm.erp.domain;

import javax.persistence.Entity;

import com.codemonkey.utils.Calc;

@Entity
public class ItemOrderSupply extends ItemPlanning {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ItemOrderSupply(ItemTransaction tran) {
		super(tran);
	}
	
	ItemOrderSupply(ReceiptItemTransaction tran) {
		super(tran);
		setQty(-Calc.abs(tran.getQty()));
	}
	
}
