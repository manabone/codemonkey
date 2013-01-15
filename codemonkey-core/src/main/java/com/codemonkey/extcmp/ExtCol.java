package com.codemonkey.extcmp;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public class ExtCol extends ExtCmp{

	private String header;  
	
	private String dataIndex;
	

	public ExtCol(String id) {
		super(id);
		this.dataIndex = id;
		this.header = id;
	}
	
	public JSONObject json(){
		JSONObject jo = super.json();
		
		if(StringUtils.isNotBlank(header)){
			jo.put("header", header);
		}
		
		if(StringUtils.isNotBlank(dataIndex)){
			jo.put("dataIndex", dataIndex);
		}
		
		return jo;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	
	
}
