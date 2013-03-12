package com.codemonkey.erp.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.web.controller.AbsFormExtController;
import com.codemonkey.web.converter.CustomConversionService;

@Controller
public abstract class DocumentFormController<T extends Document> extends AbsFormExtController<T>{


	abstract T post(JSONObject params, CustomConversionService ccService);
	
	//----------------------
    // post
    //----------------------
    @RequestMapping("post")
    @ResponseBody
    public String post(@RequestBody String body) {
    	
    	JSONObject params = parseJson(body);
    	
    	T t = post(params , getCcService());
    	
    	return result(t);
    }

}
