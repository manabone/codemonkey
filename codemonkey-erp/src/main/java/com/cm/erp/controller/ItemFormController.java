package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.Item;
import com.cm.erp.service.ItemService;

@Controller
@RequestMapping("/ext/item/**")
public class ItemFormController extends AbsFormExtController<Item>{

	@Autowired private ItemService itemService;
	
	@Override
	protected ItemService service() {
		return itemService;
	}

	@Override
	protected Item createEntity() {
		return new Item();
	}
}
