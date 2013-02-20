package com.cm.erp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Receipt extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private PurchaseOrder purchaseOrder;

	@Override
	public List<Transaction> createTransactions() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
