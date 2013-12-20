package com.codemonkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.erp.domain.Customer;
import com.codemonkey.erp.service.CustomerService;
import com.codemonkey.web.controller.AbsFormExtController;


@Controller
@RequestMapping("/ext/customer/**")
public class CustomerFormController extends AbsFormExtController<Customer>{

	@Autowired private CustomerService customerService;
	
	@Override
	protected CustomerService service() {
		return customerService;
	}
}
