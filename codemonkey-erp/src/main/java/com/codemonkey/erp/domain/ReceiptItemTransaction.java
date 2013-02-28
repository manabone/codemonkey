package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class ReceiptItemTransaction extends ItemTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Vendor vendor;
	
	@ManyToOne
	private ReceiptLine rpLine;
	
	public ReceiptItemTransaction(ReceiptLine rpLine) {
		super(rpLine);
		this.vendor = rpLine.getPurchaseOrderLine().getHeader().getVendor();
		this.setRpLine(rpLine);
		this.setQtyOnHand(rpLine.getQty());
		this.setQtyOnPurchaseOrder(Calc.neg(rpLine.getQty()));
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		plannings.add(createItemOrderSupply());
		plannings.add(createItemOnhandSupply());
		return plannings;
	}
	
	private ItemPlanning createItemOnhandSupply() {
		ItemPlanning supply = create(new ItemOnhandSupply());
		supply.setQty(getQty());
		return supply;
	}

	private ItemPlanning createItemOrderSupply() {
		ItemPlanning demand = create(new ItemOrderSupply());
		demand.setQty(Calc.neg(getQty()));
		return demand;
	}


	@Override
	public DocumentLine getDocLine() {
		return getRpLine();
	}
	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public ReceiptLine getRpLine() {
		return rpLine;
	}

	public void setRpLine(ReceiptLine rpLine) {
		this.rpLine = rpLine;
	}

}
