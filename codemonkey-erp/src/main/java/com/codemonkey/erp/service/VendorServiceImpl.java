package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Vendor;
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class VendorServiceImpl extends LogicalServiceImpl<Vendor> implements VendorService{

	@Override
	public Vendor createEntity() {
		return new Vendor();
	}
	
}
