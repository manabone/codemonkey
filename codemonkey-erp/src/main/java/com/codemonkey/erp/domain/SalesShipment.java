package com.codemonkey.erp.domain;

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
public class SalesShipment extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Customer customer;
	
	@OneToMany(mappedBy="header",cascade=CascadeType.ALL)
	private List<SalesShipmentLine> lines;

	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		
		jo.put("customer", OgnlUtils.stringValue("customer.id", this));
		jo.put("customer_text", OgnlUtils.stringValue("customer.code", this));
		
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(lines)){
			for(SalesShipmentLine line : lines){
				ja.put(line.listJson());
			}
		}
		jo.put("lines", ja);
		return jo;
	}
	
	public List<SalesShipmentLine> getLines() {
		return lines;
	}

	public void setLines(List<SalesShipmentLine> lines) {
		this.lines = lines;
	}

	public Customer getCustomer() {
		return this.customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
