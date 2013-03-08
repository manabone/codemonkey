package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Customer;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class CustomerServiceImpl extends GenericServiceImpl<Customer> implements CustomerService{

	@Override
	public Customer createEntity() {
		return new Customer();
	}
}
