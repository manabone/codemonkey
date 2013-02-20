package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cm.erp.domain.PurchaseOrderLine;
import com.cm.erp.service.PurchaseOrderLineService;
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
