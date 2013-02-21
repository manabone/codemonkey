package com.cm.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Receipt extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private PurchaseOrder purchaseOrder;

}
