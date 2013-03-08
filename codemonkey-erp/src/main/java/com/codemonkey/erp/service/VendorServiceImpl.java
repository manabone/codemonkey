package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Vendor;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class VendorServiceImpl extends GenericServiceImpl<Vendor> implements VendorService{

	@Override
	public Vendor createEntity() {
		return new Vendor();
	}
	
}
