package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class SalesInvoiceLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private SalesInvoice header;
	
	@ManyToOne 
	private SalesOrderLine salesOrderLine;
	
	private Double price;

	private Double taxRate;
	
	@Override
	public SalesInvoice getHeader() {
		return header;
	}

	public void setHeader(SalesInvoice header) {
		this.header = header;
	}
	
	public List<Transaction> createCurrencyTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new SalesInvoiceCurrencyTransaction(this));
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

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public SalesOrderLine getSalesOrderLine() {
		return salesOrderLine;
	}

	public void setSalesOrderLine(SalesOrderLine salesOrderLine) {
		this.salesOrderLine = salesOrderLine;
	}

}
