package com.codemonkey.erp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
public abstract class DesignBom extends AbsBom {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String xversion;
	
	@OneToMany(mappedBy="bom")
	private Set<DesignBomLine> lines = new HashSet<DesignBomLine>();
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("xversion", OgnlUtils.stringValue("xversion", this));
		return jo;
	}

	public String getXversion() {
		return xversion;
	}

	public void setXversion(String xversion) {
		this.xversion = xversion;
	}

	public Set<DesignBomLine> getLines() {
		return lines;
	}

	public void setLines(Set<DesignBomLine> lines) {
		this.lines = lines;
	}

}
