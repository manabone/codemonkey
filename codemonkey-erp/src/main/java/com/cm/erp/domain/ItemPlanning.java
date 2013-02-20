package com.cm.erp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.domain.AbsEE;

@Entity
public class ItemPlanning extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Item item;
	
	private Double qty;
	
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
