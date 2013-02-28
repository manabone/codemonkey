package com.codemonkey.erp.domain;

import javax.persistence.MappedSuperclass;

import com.codemonkey.domain.AbsEE;

@MappedSuperclass
public abstract class Transaction extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public abstract DocumentLine getDocLine();

}
