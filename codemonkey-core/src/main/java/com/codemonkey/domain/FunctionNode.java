package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

@Entity
public class FunctionNode extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private FunctionNode parent;
	
	private String viewClass;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("parent", OgnlUtils.stringValue("parent.id", this));
		jo.put("parent_name", OgnlUtils.stringValue("parent_name", this));
		jo.put("viewClass", OgnlUtils.stringValue("viewClass", this));
		return jo;
	}
	
	public FunctionNode getParent() {
		return parent;
	}

	public void setParent(FunctionNode parent) {
		this.parent = parent;
	}

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}

}
