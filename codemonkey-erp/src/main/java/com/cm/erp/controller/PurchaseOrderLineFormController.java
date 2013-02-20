package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.PurchaseOrderLine;
import com.cm.erp.service.PurchaseOrderLineService;

@Controller
@RequestMapping("/ext/purchaseOrderLine/**")
public class PurchaseOrderLineFormController extends AbsFormExtController<PurchaseOrderLine>{

	@Autowired private PurchaseOrderLineService purchaseOrderLineService;
	
	@Override
	protected PurchaseOrderLineService service() {
		return purchaseOrderLineService;
	}

	@Override
	protected PurchaseOrderLine createEntity() {
		return new PurchaseOrderLine();
	}
}
