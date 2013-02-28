package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.PurchaseOrder;
import com.codemonkey.erp.service.PurchaseOrderService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/purchaseOrderList/**")
public class PurchaseOrderListController extends AbsListExtController<PurchaseOrder>{

	@Autowired private PurchaseOrderService purchaseOrderService;
	
	@Override
	protected PurchaseOrderService service() {
		return purchaseOrderService;
	}

}
