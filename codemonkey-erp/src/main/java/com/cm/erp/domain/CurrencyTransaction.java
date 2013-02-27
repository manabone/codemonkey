package com.cm.erp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.codemonkey.utils.Calc;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class CurrencyTransaction  extends Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	private Double amount;
	
	private Double amountOnHand = 0d;

	private Double amountOnSalesOrder = 0d;
	
	private Double amountOnSalesInvoice = 0d;
	
	private Double amountOnPurchaseInvoice = 0d;
	
	private Double amountOnPurchaseOrder = 0d;

	private Date date;
	
	CurrencyTransaction(DocumentLine docLine) {
		this.amount = docLine.getAmount();
		this.currency = docLine.getCurrency();
	}
	
	public void updateStockCard(CurrencyStockCard stockCard){
		stockCard.setAmountOnHand(Calc.add(stockCard.getAmountOnHand() , getAmountOnHand()));
		stockCard.setAmountOnPurchaseInvoice(Calc.add(stockCard.getAmountOnPurchaseInvoice() , getAmountOnPurchaseInvoice()));
		stockCard.setAmountOnPurchaseOrder(Calc.add(stockCard.getAmountOnPurchaseOrder() , getAmountOnPurchaseOrder()));
		stockCard.setAmountOnSalesInvoice(Calc.add(stockCard.getAmountOnSalesInvoice() , getAmountOnSalesInvoice()));
		stockCard.setAmountOnSalesOrder(Calc.add(stockCard.getAmountOnSalesOrder() , getAmountOnSalesOrder()));
	}
	
	public abstract List<CurrencyPlanning> createPlanning();
	
	CurrencyPlanning create(CurrencyPlanning plan){
		plan.setCurrency(getCurrency());
		plan.setAmount(getAmount());
		plan.setDate(getDate());
		plan.setCurrencyTransaction(this);
		return plan;
		
	}
	

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmountOnHand() {
		return amountOnHand;
	}

	public void setAmountOnHand(Double amountOnHand) {
		this.amountOnHand = amountOnHand;
	}

	public Double getAmountOnSalesOrder() {
		return amountOnSalesOrder;
	}

	public void setAmountOnSalesOrder(Double amountOnSalesOrder) {
		this.amountOnSalesOrder = amountOnSalesOrder;
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

	public Double getAmountOnPurchaseOrder() {
		return amountOnPurchaseOrder;
	}

	public void setAmountOnPurchaseOrder(Double amountOnPurchaseOrder) {
		this.amountOnPurchaseOrder = amountOnPurchaseOrder;
	}

}
