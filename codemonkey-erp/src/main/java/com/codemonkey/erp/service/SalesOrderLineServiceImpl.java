package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.SalesOrderLine;

@Service
public class SalesOrderLineServiceImpl extends DocumentLineServiceImpl<SalesOrderLine> implements SalesOrderLineService{

	@Override
	public SalesOrderLine createEntity() {
		return new SalesOrderLine();
	}
}
