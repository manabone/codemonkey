package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.FunctionNode;
import com.codemonkey.service.FunctionNodeService;
import com.codemonkey.tree.ChildNode;
import com.codemonkey.tree.ParentNode;
import com.codemonkey.tree.TreeNode;

@Controller
@RequestMapping("/ext/functionNode/**")
public class FunctionNodeTreeController extends AbsTreeExtController<FunctionNode> {

	@Autowired private FunctionNodeService functionNodeService;
	
	@Override
	protected List<TreeNode> buildTreeNodes(Long id) {
		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		List<FunctionNode> list = null;
		if(id == null || id < 0 ){
			list = service().findAllBy("parent.id_isNull");
		}else{
			list = service().findAllBy("parent.id", id);
		}
		
		if(CollectionUtils.isNotEmpty(list)){
			for(FunctionNode p : list){
				TreeNode node = null;
				
				long count = service().countBy("parent.id", p.getId());
				if(count > 0){
					node = new ParentNode();
					buildTreeNode(p , node);
					
				}else{
					node = new ChildNode();
					buildTreeNode(p , node);
				}
				nodes.add(node);
			}
		}
		
		return nodes;
	}

	@Override
	protected FunctionNodeService service() {
		return functionNodeService;
	}

	@Override
	protected String getNodeText(JSONObject listJson) {
		return listJson.getString("name");
	}

}
