package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class SalesShipment extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy="header",cascade=CascadeType.ALL)
	private List<SalesShipmentLine> lines;

	public List<SalesShipmentLine> getLines() {
		return lines;
	}

	public void setLines(List<SalesShipmentLine> lines) {
		this.lines = lines;
	}
}
