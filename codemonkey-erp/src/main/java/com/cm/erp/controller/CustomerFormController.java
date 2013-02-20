package com.cm.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codemonkey.web.controller.AbsFormExtController;

import com.cm.erp.domain.Customer;
import com.cm.erp.service.CustomerService;

@Controller
@RequestMapping("/ext/customer/**")
public class CustomerFormController extends AbsFormExtController<Customer>{

	@Autowired private CustomerService customerService;
	
	@Override
	protected CustomerService service() {
		return customerService;
	}

	@Override
	protected Customer createEntity() {
		return new Customer();
	}
}
