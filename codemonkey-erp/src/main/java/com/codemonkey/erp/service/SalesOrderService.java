package com.codemonkey.erp.service;

import org.json.JSONObject;

import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.web.converter.CustomConversionService;

public interface SalesOrderService extends DocumentService<SalesOrder , SalesOrderLine>{

	SalesOrder doPost(JSONObject params, CustomConversionService ccService);

}
