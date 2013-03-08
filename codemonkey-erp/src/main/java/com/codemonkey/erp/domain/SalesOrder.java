package com.codemonkey.erp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
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
	
	@OneToMany(mappedBy="header",cascade=CascadeType.ALL)
	private List<SalesOrderLine> lines;

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
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(lines)){
			for(SalesOrderLine line : lines){
				ja.put(line.listJson());
			}
		}
		jo.put("lines", ja);
		return jo;
	}
	
	public List<SalesOrderLine> getLines() {
		return lines;
	}

	public void setLines(List<SalesOrderLine> lines) {
		this.lines = lines;
	} 
}
