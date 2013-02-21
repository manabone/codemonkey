package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SalesOrderLine extends DocumentLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private SalesOrder salesOrder;
	
	private Double price;

	@Override
	public SalesOrder getHeader() {
		return salesOrder;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Transaction> createTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new SalesOrderTransaction(getItem() , getQty() , getWarehouse()));
		return trans;
	}

}
