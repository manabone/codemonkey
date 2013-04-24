package com.codemonkey.erp.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.SalesShipment;
import com.codemonkey.erp.domain.SalesShipmentLine;
import com.codemonkey.error.FieldValidation;

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
	public Set<FieldValidation> validate4post(SalesShipment doc) {
		Set<FieldValidation> set = super.validate4post(doc);
		return set;
	}

}
