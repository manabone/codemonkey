package com.cm.erp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SalesOrder extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public List<Transaction> createTransactions() {
		// TODO Auto-generated method stub
		return null;
	}
}
