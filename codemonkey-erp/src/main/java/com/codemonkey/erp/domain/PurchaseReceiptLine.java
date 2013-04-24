package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseReceiptLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private PurchaseReceipt header;
	
	@ManyToOne 
	private PurchaseOrderLine purchaseOrderLine;

	@Override
	public PurchaseReceipt getHeader() {
		return header;
	}
	
	public void setHeader(PurchaseReceipt header) {
		this.header = header;
	}

	@Override
	public List<Transaction> createItemTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new PurchaseReceiptItemTransaction(this));
		return trans;
	}

	public PurchaseOrderLine getPurchaseOrderLine() {
		return purchaseOrderLine;
	}

	public void setPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		this.purchaseOrderLine = purchaseOrderLine;
	}
	
}
