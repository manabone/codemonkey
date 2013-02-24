package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
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
	}

	@Override
	public void updateStockCard(CurrencyStockCard stockCard) {
		stockCard.setAmountOnPurchaseOrder(Calc.sub(stockCard.getAmountOnPurchaseOrder() , getDocLine().getAmount()));
		stockCard.setAmountOnPurchaseInvoice(Calc.add(stockCard.getAmountOnPurchaseInvoice() , getDocLine().getAmount()));
	}

	@Override
	public List<CurrencyPlanning> createPlanning() {
		List<CurrencyPlanning> plannings = new ArrayList<CurrencyPlanning>();
		plannings.add(createCurrencyOrderSupply());
		plannings.add(createCurrencyInvoiceSupply());
		return plannings;
	}

	private CurrencyPlanning createCurrencyOrderSupply() {
		CurrencyPlanning plan = create(new CurrencyOrderDemand());
		plan.setAmount(Calc.neg(getAmount()));
		return plan;
	}

	private CurrencyPlanning createCurrencyInvoiceSupply() {
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
