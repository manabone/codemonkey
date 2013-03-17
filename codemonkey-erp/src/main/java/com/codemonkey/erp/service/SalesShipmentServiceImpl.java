package com.codemonkey.erp.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.DocumentStatus;
import com.codemonkey.erp.domain.SalesShipment;
import com.codemonkey.erp.domain.SalesShipmentLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;

@Service
public class SalesShipmentServiceImpl extends DocumentServiceImpl<SalesShipment , SalesShipmentLine> implements SalesShipmentService{

	@Autowired private SalesShipmentLineService salesShipmentLineService;
	
	@Override
	public SalesShipment createEntity() {
		return new SalesShipment();
	}
	
	@Override
	SalesShipmentLineService getDocumentLineService() {
		return salesShipmentLineService;
	}

	@Override
	Set<FieldValidation> validate4post(SalesShipment doc) {
		Set<FieldValidation> set = validate(doc);
		
		if(!DocumentStatus.Draft.equals(doc.getStatus())){
			set.add(new FormFieldValidation("status" , FieldValidation.NOT_DRAFT));
		}
		
		return set;
	}
	
	@Override
	protected Set<FieldValidation> validate(SalesShipment so) {
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		
		if(StringUtils.isBlank(so.getCode())){
			set.add(new FormFieldValidation("code" , FieldValidation.EMPTY));
		}
		
		if(so.getCustomer() == null){
			set.add(new FormFieldValidation("customer" , FieldValidation.EMPTY));
		}
		
		if(so.getWarehouse() == null){
			set.add(new FormFieldValidation("warehouse" , FieldValidation.EMPTY));
		}
		
		return set;
	}

}
