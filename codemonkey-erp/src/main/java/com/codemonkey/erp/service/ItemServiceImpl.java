package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Item;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class ItemServiceImpl extends GenericServiceImpl<Item> implements ItemService{

	@Override
	public Item createEntity() {
		return new Item();
	}
}
