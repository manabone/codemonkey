package com.codemonkey.erp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PurchaseOrder extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private Vendor vendor;
	
	private Date paymentDate;
	
	@OneToMany(mappedBy="header",cascade=CascadeType.ALL)
	private List<PurchaseOrderLine> lines;

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

	public List<PurchaseOrderLine> getLines() {
		return lines;
	}

	public void setLines(List<PurchaseOrderLine> lines) {
		this.lines = lines;
	}
	
}