package com.codemonkey.extcmp;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public class ExtCmp {

	public static final String TEXT_FIELD = "textfield";
	public static final String HIDDEN = "hidden";
	public static final String CHECK_BOX = "checkbox";
	public static final String SELECT = "selectfield";
	public static final String DATE_FIELD = "datefield";
	public static final String POP_UP = "searchingselect";
	
	private String id;
	
	private String xtype;
	
	private boolean hidden = false;
	
	private int flex = 1;

	public ExtCmp(String id){
		this.id = id;
	} 
	
	public JSONObject json(){
		JSONObject jo = new JSONObject();
		
		if(StringUtils.isNotBlank(id)){
			jo.put("id", id);
		}
		
		if(StringUtils.isNotBlank(xtype)){
			jo.put("xtype", xtype);
		}
		
		if(hidden){
			jo.put("hidden", hidden);
		}
		
		jo.put("flex", flex);
		
		return jo;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public int getFlex() {
		return flex;
	}

	public void setFlex(int flex) {
		this.flex = flex;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
}
