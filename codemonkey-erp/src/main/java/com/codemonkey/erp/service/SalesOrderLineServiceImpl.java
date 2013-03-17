package com.codemonkey.erp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.RowFieldValidation;

@Service
public class SalesOrderLineServiceImpl extends DocumentLineServiceImpl<SalesOrderLine> implements SalesOrderLineService{

	@Override
	public SalesOrderLine createEntity() {
		return new SalesOrderLine();
	}
	
	@Override
	protected Set<FieldValidation> validate(SalesOrderLine line) {
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		
		if(line.getItem() == null){
			set.add(new RowFieldValidation("item" , FieldValidation.EMPTY  , "SalesOrderLine"));
		}
	
		if(line.getPrice() == null){
			set.add(new RowFieldValidation("price" , FieldValidation.EMPTY , "SalesOrderLine"));
		}else if(line.getPrice() < 0){
			set.add(new RowFieldValidation("price" , FieldValidation.LESS_THAN_ZERO , "SalesOrderLine"));
		}
		
		if(line.getQty() == null){
			set.add(new RowFieldValidation("qty" , FieldValidation.EMPTY, "SalesOrderLine"));
		}else if(line.getQty() < 0){
			set.add(new RowFieldValidation("qty" , FieldValidation.LESS_THAN_ZERO, "SalesOrderLine"));
		}
		
		return set;
	}
}
