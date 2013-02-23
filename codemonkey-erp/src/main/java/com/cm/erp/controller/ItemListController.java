package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cm.erp.domain.Item;
import com.cm.erp.service.ItemService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/itemList/**")
public class ItemListController extends AbsListExtController<Item>{

	@Autowired private ItemService itemService;
	
	@Override
	protected ItemService service() {
		return itemService;
	}

}
