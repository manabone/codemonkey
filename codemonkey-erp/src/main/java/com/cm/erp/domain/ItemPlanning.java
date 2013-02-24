package com.cm.erp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.codemonkey.domain.AbsEE;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ItemPlanning extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Item item;
	
	private Double qty;
	
	private Date date;
	
	@ManyToOne
	private Warehouse warehouse;
	
	@ManyToOne
	private ItemTransaction itemTransaction;
	
	ItemPlanning(ItemTransaction tran){
		setItem(tran.getItem());
		setQty(tran.getQty());
		setWarehouse(tran.getWarehouse());
		setItemTransaction(tran);
		setDate(tran.getRequiredDate());
	}


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

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public ItemTransaction getItemTransaction() {
		return itemTransaction;
	}

	public void setItemTransaction(ItemTransaction itemTransaction) {
		this.itemTransaction = itemTransaction;
	}

}
