package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.CurrencyPlanning;
import com.codemonkey.service.PhysicalServiceImpl;

@Service
public class CurrencyPlanningServiceImpl extends PhysicalServiceImpl<CurrencyPlanning> implements CurrencyPlanningService{

	@Override
	public CurrencyPlanning createEntity() {
		return new CurrencyPlanning();
	}
}
