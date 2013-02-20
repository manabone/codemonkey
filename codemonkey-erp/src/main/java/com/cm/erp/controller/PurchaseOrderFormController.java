package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.PurchaseOrder;
import com.cm.erp.service.PurchaseOrderService;

@Controller
@RequestMapping("/ext/purchaseOrder/**")
public class PurchaseOrderFormController extends AbsFormExtController<PurchaseOrder>{

	@Autowired private PurchaseOrderService purchaseOrderService;
	
	@Override
	protected PurchaseOrderService service() {
		return purchaseOrderService;
	}

	@Override
	protected PurchaseOrder createEntity() {
		return new PurchaseOrder();
	}
}
