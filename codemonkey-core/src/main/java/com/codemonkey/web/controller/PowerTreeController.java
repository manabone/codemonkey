package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String read(@RequestParam(required=false) String id) {
		JSONObject jo = new JSONObject();
		JSONArray data = new JSONArray();
		
		List<TreeNode> treeNodes = buildTreeNodes(id);

		if(CollectionUtils.isNotEmpty(treeNodes)){
			for(TreeNode node : treeNodes){
				data.put(node.json());
			}
		}
		
		jo.put("data", data);
		
		return jo.toString();
	}
	
	private List<TreeNode> buildTreeNodes(String rootId) {
		
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
					node.setId(root.getId().toString());
					node.setName(root.getName());
					node.setText(root.getName());
					
					List<TreeNode> childrenNodes = buildTreeNodes(node.getId().toString());
					
					if(CollectionUtils.isNotEmpty(childrenNodes)){
						for(TreeNode childNode : childrenNodes){
							node.add(childNode);
						}
					}
					treeNodes.add(node);
				}else{
					ChildNode node = new ChildNode();
					node.setId(root.getId().toString());
					node.setName(root.getName());
					node.setText(root.getName());
					treeNodes.add(node);
				}
				powerTreeService.findAllBy("parent_isNull");
			}
		}
		
		return treeNodes;
	}

	@Override
	protected PowerTreeService service() {
		return powerTreeService;
	}

}
