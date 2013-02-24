package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SalesOrderCurrencyTransaction extends CurrencyTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private SalesOrderLine soLine;

	public SalesOrderCurrencyTransaction(SalesOrderLine soLine) {
		super(soLine);
		this.soLine = soLine;
		this.customer = soLine.getHeader().getCustomer();
		setDate(soLine.getHeader().getPaymentDate());
	}

	@Override
	public void updateStockCard(CurrencyStockCard stockCard) {
		stockCard.setAmountOnSalesOrder(stockCard.getAmountOnSalesOrder() + getDocLine().getAmount());
	}

	@Override
	public List<CurrencyPlanning> createPlanning() {
		List<CurrencyPlanning> plannings = new ArrayList<CurrencyPlanning>();
		plannings.add(new CurrencySupply(this));
		return plannings;
	}

	@Override
	public DocumentLine getDocLine() {
		return getSoLine();
	}

	public SalesOrderLine getSoLine() {
		return soLine;
	}

	public void setSoLine(SalesOrderLine soLine) {
		this.soLine = soLine;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
