package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@DiscriminatorValue("PURCHASE_ORDER")
public class PurchaseOrderItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Vendor vendor;
	
	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private PurchaseOrderLine poLine;
	
	PurchaseOrderItemTransaction(){}
	
	public PurchaseOrderItemTransaction(PurchaseOrderLine poLine) {
		super(poLine);
		this.vendor = poLine.getHeader().getVendor();
		this.poLine = poLine;
		this.setQtyOnPurchaseOrder(poLine.getQty());
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		plannings.add(createItemOrderSupply());
		return plannings;
	}
	
	private ItemPlanning createItemOrderSupply() {
		ItemPlanning supply = create(new ItemOrderSupply());
		supply.setQty(getQty());
		supply.setDate(getRequiredDate());
		return supply;
	}

	@Override
	public DocumentLine getDocLine() {
		return getPoLine();
	}
	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public PurchaseOrderLine getPoLine() {
		return poLine;
	}

	public void setPoLine(PurchaseOrderLine poLine) {
		this.poLine = poLine;
	}

}
