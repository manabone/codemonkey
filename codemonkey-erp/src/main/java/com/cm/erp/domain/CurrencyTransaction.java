package com.cm.erp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class CurrencyTransaction  extends Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	private Double amount;
	
	private Date date;
	
	CurrencyTransaction(DocumentLine docLine) {
		this.amount = docLine.getAmount();
		this.currency = docLine.getCurrency();
	}
	
	public abstract void updateStockCard(CurrencyStockCard stockCard);
	
	public abstract List<CurrencyPlanning> createPlanning();
	

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
