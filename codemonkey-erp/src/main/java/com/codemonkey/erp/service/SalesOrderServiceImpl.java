package com.codemonkey.erp.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public class SalesOrderServiceImpl extends DocumentServiceImpl<SalesOrder , SalesOrderLine> implements SalesOrderService{

	@Autowired private SalesOrderLineService salesOrderLineService;
	
	@Override
	public SalesOrder createEntity() {
		return new SalesOrder();
	}
	
	@Override
	SalesOrderLineService getDocumentLineService() {
		return salesOrderLineService;
	}

	@Override
	void validate4post(Document doc) {
		
	}

	@Override
	public SalesOrder doPost(JSONObject params, CustomConversionService ccService) {
		SalesOrder so = this.doSave(params, ccService);
		this.post(so);
		return so;
	}
}
