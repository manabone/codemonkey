package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class PurchasePaymentLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private PurchasePayment header;
	
	@ManyToOne 
	private PurchaseInvoiceLine purchaseInvoiceLine;
	
	private Double price;
	
	@Override
	public PurchasePayment getHeader() {
		return header;
	}
	
	public void setHeader(PurchasePayment header) {
		this.header = header;
	}

	@Override
	public List<Transaction> createCurrencyTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new PurchasePaymentCurrencyTransaction(this));
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

	public PurchaseInvoiceLine getPurchaseInvoiceLine() {
		return purchaseInvoiceLine;
	}

	public void setPurchaseInvoiceLine(PurchaseInvoiceLine purchaseInvoiceLine) {
		this.purchaseInvoiceLine = purchaseInvoiceLine;
	}

}
