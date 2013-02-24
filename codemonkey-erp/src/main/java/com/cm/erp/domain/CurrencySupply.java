package com.cm.erp.domain;

import javax.persistence.Entity;

@Entity
public class CurrencySupply extends CurrencyPlanning {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrencySupply(SalesOrderCurrencyTransaction tran) {
		super(tran);
	}
}
