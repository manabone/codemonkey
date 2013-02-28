package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DocumentLineAdapter extends DocumentLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Transaction> createItemTransactions(){
		return null;
	}

	public List<Transaction> createCurrencyTransactions(){
		return null;
	}
}
