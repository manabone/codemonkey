package com.codemonkey.tree;

import org.json.JSONObject;

public abstract class TreeNode {

	private String id;
	private String name;
	private String text;
	
	public JSONObject json(){
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("name", name);
		jo.put("text", text);
		jo.put("checked", false);
		return jo;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
