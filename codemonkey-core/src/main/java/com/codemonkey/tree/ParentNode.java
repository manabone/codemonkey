package com.codemonkey.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParentNode extends TreeNode{

	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		jo.put("leaf", false);
		JSONArray childrenJa = new JSONArray();
		if(CollectionUtils.isNotEmpty(children)){
			for(TreeNode c : children){
				childrenJa.put(c.json());
			}
		}
		jo.put("children", childrenJa);
		return jo;
	}
	
	public void add(TreeNode node){
		children.add(node);
	}
	
	public void remove(TreeNode node){
		children.remove(node);
	}
	
	public void clearChildren(){
		children.clear();
	}
	
}
