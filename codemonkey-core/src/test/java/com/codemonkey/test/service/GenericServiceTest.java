package com.codemonkey.test.service;

import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import com.codemonkey.utils.SysUtils;

public class GenericServiceTest<T> extends AbsServiceTest{

	@Before
	public void onSetUp() throws Exception {
		SysUtils.loadDataToDB(datasource, "test-data.xml", DatabaseOperation.UPDATE);
	}
	
	@Test
	public void test(){
		
	}
}
