package com.codemonkey.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProductionBomLine extends AbsBomLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private ProductionBom bom;

	public ProductionBom getBom() {
		return bom;
	}

	public void setBom(ProductionBom bom) {
		this.bom = bom;
	}
	
}
