package com.codemonkey.test.service;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.domain.AppUser;
import com.codemonkey.utils.SysUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContextTest.xml" })
@Transactional
public class AbsServiceTest {

	private Logger log = SysUtils.getLog(getClass());
	
	@Autowired DriverManagerDataSource datasource;
	
	
	@Before
	public void initSysUtils(){
		SysUtils.putAttribute(SysUtils.CURRENCT_USER, new AppUser());
	}
	
	@Test
	public void test(){
		
	}

	public Logger getLog() {
		return log;
	}
	
}
