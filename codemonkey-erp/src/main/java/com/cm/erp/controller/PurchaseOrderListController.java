package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cm.erp.domain.PurchaseOrder;
import com.cm.erp.service.PurchaseOrderService;
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
