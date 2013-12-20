package com.codemonkey.erp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
public abstract class ProductionBom extends AbsBom {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TODO
	//productionOrder
	
	@OneToMany(mappedBy="bom")
	private Set<ProductionBomLine> lines = new HashSet<ProductionBomLine>();
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("xversion", OgnlUtils.stringValue("xversion", this));
		return jo;
	}

	public Set<ProductionBomLine> getLines() {
		return lines;
	}

	public void setLines(Set<ProductionBomLine> lines) {
		this.lines = lines;
	}

}
