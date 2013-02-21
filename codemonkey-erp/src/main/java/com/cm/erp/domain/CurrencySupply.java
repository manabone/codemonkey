package com.cm.erp.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CurrencySupply")
public class CurrencySupply extends CurrencyPlanning {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
