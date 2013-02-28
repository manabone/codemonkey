package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DocumentAdapter extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public List<ItemTransaction> createItemTransactions(){
		return null;
	}

	public List<CurrencyTransaction> createCurrencyTransactions(){
		return null;
	}
	
}
