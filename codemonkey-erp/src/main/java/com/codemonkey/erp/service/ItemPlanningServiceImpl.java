package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.ItemPlanning;
import com.codemonkey.service.PhysicalServiceImpl;

@Service
public class ItemPlanningServiceImpl extends PhysicalServiceImpl<ItemPlanning> implements ItemPlanningService{

	@Override
	public ItemPlanning createEntity() {
		return new ItemPlanning();
	}

}
