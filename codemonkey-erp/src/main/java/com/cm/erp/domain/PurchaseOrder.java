package com.cm.erp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseOrder extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private Vendor vendor;

	@Override
	public List<CurrencyTransaction> createCurrencyTransactions() {
		return null;
	}
	
}