package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
public class SalesShipment extends DocumentAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy="header",cascade=CascadeType.ALL)
	private List<SalesShipmentLine> lines;

	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		
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
}
