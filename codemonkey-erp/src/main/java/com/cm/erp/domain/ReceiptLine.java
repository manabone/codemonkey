package com.cm.erp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReceiptLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private Receipt receipt;
	
	@ManyToOne 
	private PurchaseOrderLine purchaseOrderLine;

	@Override
	public Receipt getHeader() {
		return receipt;
	}

	@Override
	public List<Transaction> createItemTransactions() {
		return null;
	}
}
