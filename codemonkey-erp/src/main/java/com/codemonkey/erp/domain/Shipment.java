package com.codemonkey.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Shipment extends DocumentAdapter {

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
}
