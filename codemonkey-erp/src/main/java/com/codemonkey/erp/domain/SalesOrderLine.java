package com.codemonkey.erp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.utils.Calc;
import com.codemonkey.utils.OgnlUtils;

@Entity
public class SalesOrderLine extends DocumentLineAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private SalesOrder salesOrder;
	
	private Double price;

	private Double taxRate;
	
	@Override
	public SalesOrder getHeader() {
		return getSalesOrder();
	}
	
	@Override
	public JSONObject listJson(){
		JSONObject jo = super.listJson();
		jo.put("price", OgnlUtils.stringValue("price", this));
		jo.put("taxRate", OgnlUtils.stringValue("taxRate", this));
		jo.put("amount", OgnlUtils.stringValue("amount", this));
		jo.put("tax", OgnlUtils.stringValue("tax", this));
		jo.put("salesOrder", OgnlUtils.stringValue("salesOrder.id", this));
		return jo;
	}
	
	public List<Transaction> createItemTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new SalesOrderItemTransaction(this));
		return trans;
	}
	
	public List<Transaction> createCurrencyTransactions() {
		List<Transaction> trans = new ArrayList<Transaction>();
		trans.add(new SalesOrderCurrencyTransaction(this));
		return trans;
	}
	
	public Double getAmount() {
		return Calc.mul(getPrice() , getQty());
	}
	
	public Double getTax(){
		return Calc.mul(getAmount() , getTaxRate());
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

}
