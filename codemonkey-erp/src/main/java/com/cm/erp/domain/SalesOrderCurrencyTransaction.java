package com.cm.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class SalesOrderCurrencyTransaction extends CurrencyTransaction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired private SalesOrderLine salesOrderLine;
	
	
	public SalesOrderCurrencyTransaction(SalesOrderLine soLine) {
		salesOrderLine = soLine;
	}

	@Override
	public void updateStockCard(CurrencyStockCard stockCard) {
		stockCard.setAmountOnSalesOrder(stockCard.getAmountOnSalesOrder() + salesOrderLine.getAmount());
	}

	@Override
	public List<CurrencyPlanning> createPlanning() {
		List<CurrencyPlanning> plannings = new ArrayList<CurrencyPlanning>();
		plannings.add(new CurrencyDemand(this));
		return plannings;
	}

}
