package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class PurchaseOrderItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Vendor vendor;
	
	@ManyToOne
	private PurchaseOrderLine poLine;
	
	public PurchaseOrderItemTransaction(PurchaseOrderLine poLine) {
		super(poLine);
		this.vendor = poLine.getHeader().getVendor();
		this.poLine = poLine;
	}

	@Override
	public void updateStockCard(ItemStockCard stockCard) {
		stockCard.setQtyOnPurchaseOrder(Calc.add(stockCard.getQtyOnPurchaseOrder() , getQty()));
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		ItemDemand demand = new ItemDemand(this);
		demand.setDate(getRequiredDate());
		plannings.add(demand);
		return plannings;
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
