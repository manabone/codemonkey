package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
@DiscriminatorValue("PURCHASE_INVOICE")
public class PurchaseInvoiceCurrencyTransaction extends CurrencyTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Vendor vendor;
	
	@ManyToOne
	private PurchaseInvoiceLine piLine;

	public PurchaseInvoiceCurrencyTransaction(PurchaseInvoiceLine piLine) {
		super(piLine);
		setDate(piLine.getHeader().getPaymentDate());
		this.setPiLine(piLine);
		this.vendor = piLine.getHeader().getVendor();
		this.setAmountOnPurchaseOrder(piLine.getAmount());
		this.setAmountOnPurchaseInvoice(Calc.neg(piLine.getAmount()));
	}

	@Override
	public List<CurrencyPlanning> createPlanning() {
		List<CurrencyPlanning> plannings = new ArrayList<CurrencyPlanning>();
		plannings.add(createCurrencyOrderDemand());
		plannings.add(createCurrencyInvoiceDemand());
		return plannings;
	}

	private CurrencyPlanning createCurrencyOrderDemand() {
		CurrencyPlanning plan = create(new CurrencyOrderDemand());
		plan.setAmount(Calc.neg(getAmount()));
		return plan;
	}

	private CurrencyPlanning createCurrencyInvoiceDemand() {
		CurrencyPlanning plan = create(new CurrencyInvoiceDemand());
		plan.setAmount(getAmount());
		return plan;
	}

	@Override
	public DocumentLine getDocLine() {
		return getPiLine();
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public PurchaseInvoiceLine getPiLine() {
		return piLine;
	}

	public void setPiLine(PurchaseInvoiceLine piLine) {
		this.piLine = piLine;
	}

}
