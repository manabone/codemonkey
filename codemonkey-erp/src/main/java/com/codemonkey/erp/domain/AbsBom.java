package com.codemonkey.erp.domain;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.json.JSONObject;

import com.codemonkey.domain.AbsEE;
import com.codemonkey.utils.OgnlUtils;

@MappedSuperclass
public abstract class AbsBom extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Item item;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		
		jo.put("item", OgnlUtils.stringValue("item.id", this));
		jo.put("item_code", OgnlUtils.stringValue("item.code", this));
		
		return jo;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
