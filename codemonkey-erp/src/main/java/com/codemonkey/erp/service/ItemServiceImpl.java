package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Item;
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class ItemServiceImpl extends LogicalServiceImpl<Item> implements ItemService{

	@Override
	public Item createEntity() {
		return new Item();
	}
}
