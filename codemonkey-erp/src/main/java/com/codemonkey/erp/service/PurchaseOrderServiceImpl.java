package com.codemonkey.erp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.codemonkey.erp.domain.DocumentStatus;
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
	Set<FieldValidation> validate4post(PurchaseOrder doc) {
		Set<FieldValidation> set = validate(doc);
		
		if(!DocumentStatus.Draft.equals(doc.getStatus())){
			set.add(new FormFieldValidation("status" , FieldValidation.NOT_DRAFT));
		}
		
		List<PurchaseOrderLine> lines = purchaseOrderLineService.getLinesByHeader(doc);
		
		if(CollectionUtils.isEmpty(lines)){
			set.add(new FormFieldValidation("lines" , FieldValidation.EMPTY));
		}
		
		for(PurchaseOrderLine line : lines){
			if(line.getItem() == null){
				set.add(new FormFieldValidation("item" , FieldValidation.EMPTY));
			}
			
			if(line.getQty() == null){
				set.add(new FormFieldValidation("qty" , FieldValidation.EMPTY));
			}
			
			if(line.getPrice() == null){
				set.add(new FormFieldValidation("price" , FieldValidation.EMPTY));
			}
		}
		
		return set;
	}
	
	@Override
	protected Set<FieldValidation> validate(PurchaseOrder po) {
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		
		if(StringUtils.isBlank(po.getCode())){
			set.add(new FormFieldValidation("code" , FieldValidation.EMPTY));
		}
		
		if(po.getVendor() == null){
			set.add(new FormFieldValidation("vendor" , FieldValidation.EMPTY));
		}
		
		if(po.getWarehouse() == null){
			set.add(new FormFieldValidation("warehouse" , FieldValidation.EMPTY));
		}
		
		return set;
	}

	
}
