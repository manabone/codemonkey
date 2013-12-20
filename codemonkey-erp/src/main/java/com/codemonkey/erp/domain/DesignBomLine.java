package com.codemonkey.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DesignBomLine extends AbsBomLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private DesignBom bom;

	public DesignBom getBom() {
		return bom;
	}

	public void setBom(DesignBom bom) {
		this.bom = bom;
	}
	
}
