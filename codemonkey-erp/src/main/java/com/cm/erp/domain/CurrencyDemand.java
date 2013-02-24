package com.cm.erp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CurrencyDemand")
public class CurrencyDemand extends CurrencyPlanning {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrencyDemand(PurchaseOrderCurrencyTransaction tran) {
		super(tran);
	}
	
}
