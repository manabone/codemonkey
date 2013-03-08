package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.PurchaseOrder;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class PurchaseOrderServiceImpl extends GenericServiceImpl<PurchaseOrder> implements PurchaseOrderService{

	@Override
	public PurchaseOrder createEntity() {
		return new PurchaseOrder();
	}
	
}
