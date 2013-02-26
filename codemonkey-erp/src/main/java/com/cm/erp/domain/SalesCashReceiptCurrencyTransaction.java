package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.utils.Calc;

@Entity
public class SalesCashReceiptCurrencyTransaction extends CurrencyTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private SalesCashReceiptLine scrLine;

	public SalesCashReceiptCurrencyTransaction(SalesCashReceiptLine scrLine) {
		super(scrLine);
		this.setScrLine(scrLine);
		this.customer = scrLine.getHeader().getCustomer();
		setDate(scrLine.getHeader().getPaymentDate());
	}

	@Override
	public void updateStockCard(CurrencyStockCard stockCard) {
		stockCard.setAmountOnSalesInvoice(Calc.add(stockCard.getAmountOnSalesInvoice() , getDocLine().getAmount()));
		stockCard.setAmountOnSalesOrder(Calc.sub(stockCard.getAmountOnSalesOrder() , getDocLine().getAmount()));
	}

	@Override
	public List<CurrencyPlanning> createPlanning() {
		List<CurrencyPlanning> plannings = new ArrayList<CurrencyPlanning>();
		plannings.add(createCurrencyOrderSupply());
		plannings.add(createCurrencyInvoiceSupply());
		return plannings;
	}

	private CurrencyPlanning createCurrencyInvoiceSupply() {
		CurrencyPlanning plan = create(new CurrencyInvoiceSupply());
		return plan;
	}

	private CurrencyPlanning createCurrencyOrderSupply() {
		CurrencyPlanning plan = create(new CurrencyOrderSupply());
		plan.setAmount(Calc.neg(getAmount()));
		return plan;
	}

	@Override
	public DocumentLine getDocLine() {
		return getScrLine();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SalesCashReceiptLine getScrLine() {
		return scrLine;
	}

	public void setScrLine(SalesCashReceiptLine scrLine) {
		this.scrLine = scrLine;
	}

}
