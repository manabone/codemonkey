package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class PurchaseInvoiceLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private PurchaseInvoice purchaseInvoice;
	
	private Double price;
	
	@Override
	public PurchaseInvoice getHeader() {
		return getPurchaseInvoice();
	}

	@Override
	public List<Transaction> createCurrencyTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new PurchaseInvoiceCurrencyTransaction(this));
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

	public PurchaseInvoice getPurchaseInvoice() {
		return purchaseInvoice;
	}

	public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		this.purchaseInvoice = purchaseInvoice;
	}

}
