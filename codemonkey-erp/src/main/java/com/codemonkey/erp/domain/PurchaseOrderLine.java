package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class PurchaseOrderLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private PurchaseOrder header;
	
	private Double price;
	
	@Override
	public PurchaseOrder getHeader() {
		return header;
	}
	
	public void setHeader(PurchaseOrder header) {
		this.header = header;
	}

	@Override
	public List<Transaction> createItemTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new PurchaseOrderItemTransaction(this));
		return trans;
	}
	
	@Override
	public List<Transaction> createCurrencyTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new PurchaseOrderCurrencyTransaction(this));
		return trans;
	}
	
	public Double getAmount() {
		return Calc.mul(getPrice() , getQty());
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
