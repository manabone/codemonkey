package com.codemonkey.erp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.SalesShipmentLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.RowFieldValidation;

@Service
public class SalesShipmentLineServiceImpl extends DocumentLineServiceImpl<SalesShipmentLine> implements SalesShipmentLineService{

	@Override
	public SalesShipmentLine createEntity() {
		return new SalesShipmentLine();
	}
	
	@Override
	protected Set<FieldValidation> validate(SalesShipmentLine line) {
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		
		if(line.getItem() == null){
			set.add(new RowFieldValidation("item" , FieldValidation.EMPTY  , "SalesOrderLine"));
		}
	
		if(line.getQty() == null){
			set.add(new RowFieldValidation("qty" , FieldValidation.EMPTY, "SalesOrderLine"));
		}else if(line.getQty() < 0){
			set.add(new RowFieldValidation("qty" , FieldValidation.LESS_THAN_ZERO, "SalesOrderLine"));
		}
		
		return set;
	}
}
