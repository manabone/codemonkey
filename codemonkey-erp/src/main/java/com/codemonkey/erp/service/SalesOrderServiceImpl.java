package com.codemonkey.erp.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;

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
	protected Set<FieldValidation> validate(SalesOrder so) {
		Set<FieldValidation> set = super.validate(so);
		
		if(so.getCustomer() == null){
			set.add(new FormFieldValidation("customer" , FieldValidation.EMPTY));
		}
		
		return set;
	}

}
