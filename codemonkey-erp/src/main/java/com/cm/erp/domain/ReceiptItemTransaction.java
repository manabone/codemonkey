package com.cm.erp.domain;

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
	}

	@Override
	public void updateStockCard(ItemStockCard stockCard) {
		stockCard.setQtyOnPurchaseOrder(Calc.sub(stockCard.getQtyOnPurchaseOrder() , getQty()));
		stockCard.setQtyOnHand(Calc.add(stockCard.getQtyOnHand() , getQty()));
	}

	@Override
	public List<ItemPlanning> createPlanning() {
		List<ItemPlanning> plannings = new ArrayList<ItemPlanning>();
		ItemOrderSupply supply1 = new ItemOrderSupply(this);
		ItemOnhandSupply supply2 = new ItemOnhandSupply(this);
		plannings.add(supply1);
		plannings.add(supply2);
		return plannings;
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
