package com.codemonkey.erp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PurchaseInvoice extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private Vendor vendor;
	
	private Date paymentDate;

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
}