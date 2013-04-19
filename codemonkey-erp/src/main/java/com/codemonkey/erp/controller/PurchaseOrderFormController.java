package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.PurchaseOrder;
import com.codemonkey.erp.service.PurchaseOrderService;


@Controller
@RequestMapping("/ext/purchaseOrder/**")
public class PurchaseOrderFormController extends DocumentFormController<PurchaseOrder>{

	@Autowired private PurchaseOrderService purchaseOrderService;
	
	@Override
	protected PurchaseOrderService service() {
		return purchaseOrderService;
	}
	
	@Override
	void processPost(PurchaseOrder t) {
		purchaseOrderService.post(t);
	}
}
