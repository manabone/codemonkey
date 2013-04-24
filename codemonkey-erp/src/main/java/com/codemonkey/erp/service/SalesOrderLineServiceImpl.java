package com.codemonkey.erp.service;

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
	public Set<FieldValidation> validate4post(SalesOrderLine line) {
		
		Set<FieldValidation> set = super.validate4post(line);
		
		if(line.getPrice() == null){
			set.add(new RowFieldValidation("price" , FieldValidation.EMPTY , getType().getSimpleName()));
		}else if(line.getPrice() < 0){
			set.add(new RowFieldValidation("price" , FieldValidation.LESS_THAN_ZERO , getType().getSimpleName()));
		}
		
		return set;
	}
}
