package com.cm.erp.domain;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.codemonkey.domain.AbsEE;

@MappedSuperclass
public abstract class Document extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double totalAmount;
	
	@ManyToOne
	private Warehouse warehouse;

	public abstract List<ItemTransaction> createItemTransactions();

	public abstract List<CurrencyTransaction> createCurrencyTransactions();
	
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

}
