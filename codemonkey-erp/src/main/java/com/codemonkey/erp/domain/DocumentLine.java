package com.codemonkey.erp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.codemonkey.domain.AbsEE;

@MappedSuperclass
public abstract class DocumentLine extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//constants
	
	//properties
	@ManyToOne
	private Item item;
	
	private Double qty;
	
	private Date requiredDate;

	//constructors

	//abstract methods
	public abstract Document getHeader();
	
	public abstract List<Transaction> createItemTransactions();
	
	public abstract List<Transaction> createCurrencyTransactions();

	//customized methods
	public Warehouse getWarehouse(){
		return getHeader().getWarehouse();
	}
	
	public Currency getCurrency(){
		return getHeader().getCurrency();
	}
	
	//getter and setters
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getAmount() {
		return 0d;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

}
