package com.codemonkey.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.Customer;
import com.codemonkey.erp.service.CustomerService;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
@RequestMapping("/ext/customerList/**")
public class CustomerListController extends AbsListExtController<Customer>{

	@Autowired private CustomerService customerService;
	
	@Override
	protected CustomerService service() {
		return customerService;
	}

}
