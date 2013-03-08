package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.ItemPlanning;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class ItemPlanningServiceImpl extends GenericServiceImpl<ItemPlanning> implements ItemPlanningService{

	@Override
	public ItemPlanning createEntity() {
		return new ItemPlanning();
	}

}
