package com.codemonkey.erp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.DocumentStatus;
import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.RowFieldValidation;

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
	Set<FieldValidation> validate4post(SalesOrder doc) {
		Set<FieldValidation> set = validate(doc);
		
		if(!DocumentStatus.Draft.equals(doc.getStatus())){
			set.add(new FieldValidation("status" , FieldValidation.NOT_DRAFT));
		}
		
		return set;
	}
	
	@Override
	protected Set<FieldValidation> validate(SalesOrder so) {
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		
		if(StringUtils.isBlank(so.getCode())){
			set.add(new FieldValidation("code" , FieldValidation.EMPTY));
		}
		
		if(so.getCustomer() == null){
			set.add(new FieldValidation("customer" , FieldValidation.EMPTY));
		}
		
		if(so.getWarehouse() == null){
			set.add(new FieldValidation("warehouse" , FieldValidation.EMPTY));
		}
		
		List<SalesOrderLine> lines = getDocumentLineService().getLinesByHeader(so);
		
		if(CollectionUtils.isNotEmpty(lines)){
			int index = 0;
			for(SalesOrderLine line : lines){
				index++;
				if(line.getItem() == null){
					set.add(new RowFieldValidation("item" , FieldValidation.EMPTY , index));
				}
			
				if(line.getPrice() == null){
					set.add(new RowFieldValidation("price" , FieldValidation.EMPTY , index));
				}
				
				if(line.getPrice() < 0){
					set.add(new RowFieldValidation("price" , FieldValidation.LESS_THAN_ZERO , index));
				}
				
				if(line.getQty() == null){
					set.add(new RowFieldValidation("qty" , FieldValidation.EMPTY , index));
				}
				
				if(line.getQty() < 0){
					set.add(new RowFieldValidation("qty" , FieldValidation.LESS_THAN_ZERO , index));
				}
			}
		}
		
		return set;
	}

}
