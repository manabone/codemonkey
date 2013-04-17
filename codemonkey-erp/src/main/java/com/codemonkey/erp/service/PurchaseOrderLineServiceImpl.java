package com.codemonkey.erp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.PurchaseOrderLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.RowFieldValidation;

@Service
public class PurchaseOrderLineServiceImpl extends DocumentLineServiceImpl<PurchaseOrderLine> implements PurchaseOrderLineService{

	@Override
	public PurchaseOrderLine createEntity() {
		return new PurchaseOrderLine();
	}
	
	@Override
	protected Set<FieldValidation> validate(PurchaseOrderLine line) {
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		
		if(line.getItem() == null){
			set.add(new RowFieldValidation("item" , FieldValidation.EMPTY  , "PurchaseOrderLine"));
		}
	
		if(line.getPrice() == null){
			set.add(new RowFieldValidation("price" , FieldValidation.EMPTY , "PurchaseOrderLine"));
		}else if(line.getPrice() < 0){
			set.add(new RowFieldValidation("price" , FieldValidation.LESS_THAN_ZERO , "PurchaseOrderLine"));
		}
		
		if(line.getQty() == null){
			set.add(new RowFieldValidation("qty" , FieldValidation.EMPTY, "PurchaseOrderLine"));
		}else if(line.getQty() < 0){
			set.add(new RowFieldValidation("qty" , FieldValidation.LESS_THAN_ZERO, "PurchaseOrderLine"));
		}
		
		return set;
	}
	
}
