package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.PowerTree;
import com.codemonkey.service.PowerTreeService;
import com.codemonkey.tree.ChildNode;
import com.codemonkey.tree.ParentNode;
import com.codemonkey.tree.TreeNode;

@Controller
@RequestMapping("/ext/powerTree/**")
public class PowerTreeController extends AbsExtController<PowerTree>{

	public static final String ROOT = "root";
	
	@Autowired private PowerTreeService powerTreeService;
	
	@RequestMapping("read")
    @ResponseBody
    public String read(@RequestParam(required=false) String id ,
    		@RequestParam(required=false) AppRole appRole) {
		
		JSONObject jo = new JSONObject();
		JSONArray data = new JSONArray();
		
		List<TreeNode> treeNodes = buildTreeNodes(id, appRole);

		if(CollectionUtils.isNotEmpty(treeNodes)){
			for(TreeNode node : treeNodes){
				data.put(node.json());
			}
		}
		
		jo.put("data", data);
		
		return jo.toString();
	}
	
	public List<TreeNode> buildTreeNodes(String rootId, AppRole appRole) {
		Set<Long> powerTreeIdSet = new HashSet<Long>();
		if(appRole != null){
			Set<PowerTree> powerTrees = appRole.getPowerTrees();
			for(PowerTree p : powerTrees){
				powerTreeIdSet.add(p.getId());
			}
		}
		
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		
		List<PowerTree> rootNodes = null;
		
		if(StringUtils.isNotBlank(rootId) && ROOT.equals(rootId)){
			rootNodes = powerTreeService.findAllBy("parent_isNull");
		}else{
			rootNodes = powerTreeService.findAllBy("parent.id" , Long.valueOf(rootId));
		}
		
		if(CollectionUtils.isNotEmpty(rootNodes)){
			for(PowerTree root : rootNodes){
				
				List<PowerTree> nodes = powerTreeService.findAllBy("parent.id" , root.getId());
				
				if(CollectionUtils.isNotEmpty(nodes)){
					ParentNode node = new ParentNode();
					node.addAttr("id" , root.getId().toString());
					node.addAttr("name" , root.getName());
					node.addAttr("text" , root.getName());
					if(!powerTreeIdSet.add(root.getId())){
						node.addAttr("checked" , true);
					}

					List<TreeNode> childrenNodes = buildTreeNodes(node.getAttr("id").toString(), appRole);
					
					if(CollectionUtils.isNotEmpty(childrenNodes)){
						for(TreeNode childNode : childrenNodes){
							node.add(childNode);
						}
					}
					treeNodes.add(node);
				}else{
					ChildNode node = new ChildNode();
					node.addAttr("id" , root.getId().toString());
					node.addAttr("name" , root.getName());
					node.addAttr("text" , root.getName());
					if(!powerTreeIdSet.add(root.getId())){
						node.addAttr("checked" , true);
					}
					treeNodes.add(node);
				}
			}
		}
		
		return treeNodes;
	}

	@Override
	protected PowerTreeService service() {
		return powerTreeService;
	}

}
