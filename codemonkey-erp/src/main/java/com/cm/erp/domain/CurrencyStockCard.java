package com.cm.erp.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.codemonkey.domain.AbsEE;

@Entity
public class CurrencyStockCard  extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	private Double amountOnHand = 0d;

	private Double amountOnSalesOrder = 0d;
	
	private Double amountOnSalesInvoice = 0d;
	
	private Double amountOnPurchaseInvoice = 0d;
	
	private Double amountOnPurchaseOrder = 0d;
	
	public CurrencyStockCard(Currency currency) {
		this.currency = currency;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Double getAmountOnHand() {
		return amountOnHand;
	}

	public void setAmountOnHand(Double amountOnHand) {
		this.amountOnHand = amountOnHand;
	}

	public Double getAmountOnSalesInvoice() {
		return amountOnSalesInvoice;
	}

	public void setAmountOnSalesInvoice(Double amountOnSalesInvoice) {
		this.amountOnSalesInvoice = amountOnSalesInvoice;
	}

	public Double getAmountOnPurchaseInvoice() {
		return amountOnPurchaseInvoice;
	}

	public void setAmountOnPurchaseInvoice(Double amountOnPurchaseInvoice) {
		this.amountOnPurchaseInvoice = amountOnPurchaseInvoice;
	}

	public Double getAmountOnSalesOrder() {
		return amountOnSalesOrder;
	}

	public void setAmountOnSalesOrder(Double amountOnSalesOrder) {
		this.amountOnSalesOrder = amountOnSalesOrder;
	}

	public Double getAmountOnPurchaseOrder() {
		return amountOnPurchaseOrder;
	}

	public void setAmountOnPurchaseOrder(Double amountOnPurchaseOrder) {
		this.amountOnPurchaseOrder = amountOnPurchaseOrder;
	}
	
}
