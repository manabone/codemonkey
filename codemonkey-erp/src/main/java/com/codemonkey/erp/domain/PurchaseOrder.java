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
public class PurchaseOrder extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne 
	private Vendor vendor;
	
	private Date paymentDate;
	
	@OneToMany(mappedBy="header",cascade=CascadeType.ALL)
	private List<PurchaseOrderLine> lines;

	@Override
	public JSONObject listJson() {
		JSONObject json = super.listJson();
		json.put("paymentDate", OgnlUtils.stringValue("paymentDate", this));
		json.put("vendor", OgnlUtils.stringValue("vendor.id", this));
		json.put("vendor_text", OgnlUtils.stringValue("vendor.code", this));
		
		return json;
	}
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		
		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(lines)){
			for(PurchaseOrderLine line : lines){
				ja.put(line.listJson());
			}
		}
		jo.put("lines", ja);
		return jo;
	}
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public List<PurchaseOrderLine> getLines() {
		return lines;
	}

	public void setLines(List<PurchaseOrderLine> lines) {
		this.lines = lines;
	}
	
}