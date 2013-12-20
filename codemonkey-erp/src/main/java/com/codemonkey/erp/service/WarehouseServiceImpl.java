package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Warehouse;
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class WarehouseServiceImpl extends LogicalServiceImpl<Warehouse> implements WarehouseService{

	@Override
	public Warehouse createEntity() {
		return new Warehouse();
	}
	
}
