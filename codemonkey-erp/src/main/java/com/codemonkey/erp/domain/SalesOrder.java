package com.codemonkey.erp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
public class SalesOrder extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Customer customer;
	
	private Date paymentDate;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@Override
	public JSONObject listJson() {
		JSONObject json = super.listJson();
		json.put("paymentDate", OgnlUtils.stringValue("paymentDate", this));
		json.put("customer", OgnlUtils.stringValue("customer.id", this));
		json.put("customer_text", OgnlUtils.stringValue("customer.code", this));
		return json;
	}
	
	
	public static void main(String[] args){
		SalesOrder so  = new SalesOrder();
		so.setPaymentDate(new Date());
		System.out.println(so.detailJson());
	} 
}
