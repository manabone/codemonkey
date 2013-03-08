package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ShipmentLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne 
	private Shipment header;
	
	@ManyToOne 
	private SalesOrderLine salesOrderLine;
	
	@Override
	public Shipment getHeader() {
		return header;
	}
	
	public void setHeader(Shipment header) {
		this.header = header;
	}

	@Override
	public List<Transaction> createItemTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new ShipmentItemTransaction(this));
		return trans;
	}

	public SalesOrderLine getSalesOrderLine() {
		return salesOrderLine;
	}

	public void setSalesOrderLine(SalesOrderLine salesOrderLine) {
		this.salesOrderLine = salesOrderLine;
	}

}
