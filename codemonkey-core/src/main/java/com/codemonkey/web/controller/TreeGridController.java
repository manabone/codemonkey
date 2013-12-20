package com.codemonkey.web.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.Foo;
import com.codemonkey.service.GenericService;
import com.codemonkey.tree.ChildNode;
import com.codemonkey.tree.ParentNode;
import com.codemonkey.tree.TreeNode;

@Controller
@RequestMapping("/ext/treeGrid/**")
public class TreeGridController extends AbsExtController<Foo>{

	@RequestMapping("read")
    @ResponseBody 
    public String read(@RequestParam(required=false) String node) {
		JSONArray data = new JSONArray();
		
		if("root".equals(node)){
			
			ParentNode note1 = new ParentNode();
			note1.addAttr("id", "window");
			note1.addAttr("name", "window");
			note1.addAttr("text", "window");
			note1.addAttr("expanded" , true);
			
			TreeNode child1 = new ChildNode();
			child1.addAttr("id", "text");
			child1.addAttr("name", "text");
			child1.addAttr("text", "text");
			note1.add(child1);
			
			TreeNode child2 = new ChildNode();
			child2.addAttr("id", "select");
			child2.addAttr("name", "select");
			child2.addAttr("text", "select");
			note1.add(child2);
			
			ParentNode note2 = new ParentNode();
			note2.addAttr("id", "form");
			note2.addAttr("name", "form");
			note2.addAttr("text", "form");
			note2.addAttr("expanded" , true);
			
			TreeNode child3 = new ChildNode();
			child3.addAttr("id", "messageBox");
			child3.addAttr("name", "messageBox");
			child3.addAttr("text", "messageBox");
			note2.add(child3);
			
			TreeNode child4 = new ChildNode();
			child4.addAttr("id", "panel");
			child4.addAttr("name", "panel");
			child4.addAttr("text", "panel");
			note2.add(child4);
			
			data.put(note1.json());
			data.put(note2.json());
		
		}else if("form".equals(node)){
			
			TreeNode child1 = new ChildNode();
			child1.addAttr("id", "text");
			child1.addAttr("name", "text");
			child1.addAttr("text", "text");
			
			
			TreeNode child2 = new ChildNode();
			child2.addAttr("id", "select");
			child2.addAttr("name", "select");
			child2.addAttr("text", "select");
			
			data.put(child1.json());
			data.put(child2.json());
			
		}else if("window".equals(node)){
			
			TreeNode child1 = new ChildNode();
			child1.addAttr("id", "messageBox");
			child1.addAttr("name", "messageBox");
			child1.addAttr("text", "messageBox");
			
			TreeNode child2 = new ChildNode();
			child2.addAttr("id", "panel");
			child2.addAttr("name", "panel");
			child2.addAttr("text", "panel");
			
			data.put(child1.json());
			data.put(child2.json());
			
		}
		
		JSONObject jo = new JSONObject();
		jo.put("data", data);
		
		return jo.toString();
	}
	
	@Override
	protected GenericService<Foo> service() {
		return null;
	}

}
