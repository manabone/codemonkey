package com.codemonkey.erp.domain;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.json.JSONObject;

import com.codemonkey.domain.AbsEE;
import com.codemonkey.utils.OgnlUtils;

@MappedSuperclass
public abstract class AbsBomLine extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Item item;
	
	private Double qty;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		
		jo.put("item", OgnlUtils.stringValue("item.id", this));
		jo.put("item_code", OgnlUtils.stringValue("item.code", this));
		
		jo.put("qty", OgnlUtils.stringValue("qty", this));
		
		return jo;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

}
