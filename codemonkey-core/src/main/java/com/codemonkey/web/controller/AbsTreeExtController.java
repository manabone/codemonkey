package com.codemonkey.web.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.IEntity;
import com.codemonkey.tree.TreeNode;

@Controller
public abstract class AbsTreeExtController<T extends IEntity> extends AbsExtController<T> {

	abstract protected List<TreeNode> buildTreeNodes(Long id);
	
	abstract protected String getNodeText(JSONObject listJson);
	
	@RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam(required=false) Long id) {
		
		JSONArray data = new JSONArray();
		
		List<TreeNode> nodes = buildTreeNodes(id);
		
		if(CollectionUtils.isNotEmpty(nodes)){
			for(TreeNode treeNode : nodes){
				data.put(treeNode.json());
			}
		}
		
		JSONObject jo = new JSONObject();
		jo.put("data", data);
		jo.put("leaf", false);
		jo.put("expanded", true);
		
		return jo.toString();
	}
	
	@SuppressWarnings("unchecked")
	protected void buildTreeNode(T t , TreeNode node){
		JSONObject listJson = t.listJson();
		
		if(listJson == null) {
			return;
		}
		
		Iterator<String> it = listJson.keys();
		node.addAttr("internalId", t.getId());
		while(it.hasNext()){
			String key = it.next();
			node.addAttr(key, listJson.getString(key));
			node.addAttr("text", getNodeText(listJson));
		}
	}

}
