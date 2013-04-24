package com.codemonkey.erp.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.PurchaseOrder;
import com.codemonkey.erp.domain.PurchaseOrderLine;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;

@Service
public class PurchaseOrderServiceImpl extends DocumentServiceImpl<PurchaseOrder , PurchaseOrderLine> implements PurchaseOrderService{

	@Autowired private PurchaseOrderLineService purchaseOrderLineService;
	
	@Override
	public PurchaseOrder createEntity() {
		return new PurchaseOrder();
	}
	
	@Override
	PurchaseOrderLineService getDocumentLineService() {
		return purchaseOrderLineService;
	}
	
	@Override
	protected Set<FieldValidation> validate(PurchaseOrder po) {
		Set<FieldValidation> set = super.validate(po);
		
		if(po.getVendor() == null){
			set.add(new FormFieldValidation("vendor" , FieldValidation.EMPTY));
		}
		
		return set;
	}
	
}
