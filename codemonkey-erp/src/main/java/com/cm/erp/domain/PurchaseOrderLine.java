package com.cm.erp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseOrderLine extends DocumentLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private PurchaseOrder purchaseOrder;
	
	private Double price;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public PurchaseOrder getHeader() {
		return purchaseOrder;
	}

	@Override
	public List<Transaction> createTransactions() {
		// TODO Auto-generated method stub
		return null;
	}
}
