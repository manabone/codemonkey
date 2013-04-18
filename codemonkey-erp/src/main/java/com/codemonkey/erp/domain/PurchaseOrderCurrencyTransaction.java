package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
@DiscriminatorValue("PURCHASE_ORDER")
public class PurchaseOrderCurrencyTransaction extends CurrencyTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Vendor vendor;
	
	@ManyToOne
	private PurchaseOrderLine poLine;

	public PurchaseOrderCurrencyTransaction(PurchaseOrderLine poLine) {
		super(poLine);
		setDate(poLine.getHeader().getPaymentDate());
		this.poLine = poLine;
		this.vendor = poLine.getHeader().getVendor();
		this.setAmountOnPurchaseOrder(Calc.neg(poLine.getAmount()));
	}

	@Override
	public List<CurrencyPlanning> createPlanning() {
		List<CurrencyPlanning> plannings = new ArrayList<CurrencyPlanning>();
		plannings.add(createCurrencyOrderDemand());
		return plannings;
	}

	private CurrencyPlanning createCurrencyOrderDemand() {
		CurrencyPlanning plan = create(new CurrencyOrderDemand());
		return plan;
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
