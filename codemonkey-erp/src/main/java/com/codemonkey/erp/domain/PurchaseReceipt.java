package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PurchaseReceipt extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private PurchaseOrder purchaseOrder;
	
	@OneToMany(mappedBy="header",cascade=CascadeType.ALL)
	private List<PurchaseReceiptLine> lines;

	public List<PurchaseReceiptLine> getLines() {
		return lines;
	}

	public void setLines(List<PurchaseReceiptLine> lines) {
		this.lines = lines;
	}

}
