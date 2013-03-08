package com.codemonkey.erp.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.converter.CustomConversionService;

@Service
public class SalesOrderServiceImpl extends DocumentServiceImpl<SalesOrder , SalesOrderLine> implements SalesOrderService{

	@Autowired private SalesOrderLineService salesOrderLineService;
	
	@Override
	public SalesOrder createEntity() {
		return new SalesOrder();
	}
	
	@Override
	public JSONObject doSave(JSONObject params , CustomConversionService ccService){
		JSONObject result = super.doSave(params , ccService);
		
		JSONArray toModifyLines = params.getJSONArray(ExtConstant.TO_MODIFY_LINES);
		if(toModifyLines != null){
			for(int i = 0 ; i < toModifyLines.length() ; i++){
				salesOrderLineService.doSave(toModifyLines.getJSONObject(i) , ccService);
			}
		}
		
		JSONArray toDeleteLines = params.getJSONArray(ExtConstant.TO_DELETE_LINES);
		if(toDeleteLines != null){
			for(int i = 0 ; i < toDeleteLines.length() ; i++){
				salesOrderLineService.delete(toDeleteLines.getJSONObject(i).getLong(ExtConstant.ID));
			}
		}
		
		return result;
	}
	
	@Override
	SalesOrderLineService getDocumentLineService() {
		return salesOrderLineService;
	}

	@Override
	void validate4post(Document doc) {
		
	}
}
