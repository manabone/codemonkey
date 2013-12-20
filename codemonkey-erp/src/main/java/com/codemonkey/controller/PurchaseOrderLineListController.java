package com.codemonkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.PurchaseOrderLine;
import com.codemonkey.erp.service.PurchaseOrderLineService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/purchaseOrderLineList/**")
public class PurchaseOrderLineListController extends AbsListExtController<PurchaseOrderLine>{

	@Autowired private PurchaseOrderLineService purchaseOrderLineService;
	
	@Override
	protected PurchaseOrderLineService service() {
		return purchaseOrderLineService;
	}

}
