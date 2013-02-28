package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.Item;
import com.codemonkey.erp.service.ItemService;
import com.codemonkey.web.controller.AbsFormExtController;


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
