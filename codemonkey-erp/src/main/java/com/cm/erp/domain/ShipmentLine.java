package com.cm.erp.domain;

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
	private Shipment shipment;
	
	@ManyToOne 
	private SalesOrderLine salesOrderLine;
	
	@Override
	public Shipment getHeader() {
		return shipment;
	}

	@Override
	public List<Transaction> createItemTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

}
