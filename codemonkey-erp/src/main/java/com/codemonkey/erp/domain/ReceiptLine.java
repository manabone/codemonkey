package com.codemonkey.erp.domain;

import java.util.ArrayList;
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
	private Receipt header;
	
	@ManyToOne 
	private PurchaseOrderLine purchaseOrderLine;

	@Override
	public Receipt getHeader() {
		return header;
	}
	
	public void setHeader(Receipt header) {
		this.header = header;
	}

	@Override
	public List<Transaction> createItemTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new ReceiptItemTransaction(this));
		return trans;
	}

	public PurchaseOrderLine getPurchaseOrderLine() {
		return purchaseOrderLine;
	}

	public void setPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		this.purchaseOrderLine = purchaseOrderLine;
	}
	
}
