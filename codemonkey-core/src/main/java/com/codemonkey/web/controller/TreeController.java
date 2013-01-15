package com.codemonkey.web.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.service.GenericService;
import com.codemonkey.tree.ChildNode;
import com.codemonkey.tree.ParentNode;
import com.codemonkey.tree.TreeNode;

@Controller
@RequestMapping("/ext/tree/**")
public class TreeController extends AbsExtController{

	@RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam(required=false) String node) {
		JSONArray data = new JSONArray();
		
		if("root".equals(node)){
			
			TreeNode note1 = new ParentNode();
			note1.setId("window");
			note1.setName("window");
			note1.setText("window");
			
			TreeNode note2 = new ParentNode();
			note2.setId("form");
			note2.setName("form");
			note2.setText("form");
			
			data.put(note1.json());
			data.put(note2.json());
		
		}else if("form".equals(node)){
			
			TreeNode child1 = new ChildNode();
			child1.setId("text");
			child1.setName("text");
			child1.setText("text");
			
			TreeNode child2 = new ChildNode();
			child2.setId("select");
			child2.setName("select");
			child2.setText("select");
			
			data.put(child1.json());
			data.put(child2.json());
			
		}else if("window".equals(node)){
			
			TreeNode child1 = new ChildNode();
			child1.setId("messageBox");
			child1.setName("messageBox");
			child1.setText("messageBox");
			
			TreeNode child2 = new ChildNode();
			child2.setId("panel");
			child2.setName("panel");
			child2.setText("panel");
			
			data.put(child1.json());
			data.put(child2.json());
			
		}
		
		JSONObject jo = new JSONObject();
		jo.put("data", data);
		
		return jo.toString();
	}
	
	public List<AppPermission> regAppPermission() {
		return null;
	}

	@Override
	protected GenericService service() {
		return null;
	}

	@Override
	String getControllers() {
		return null;
	}

	@Override
	String getIndexView() {
		return null;
	}

}
