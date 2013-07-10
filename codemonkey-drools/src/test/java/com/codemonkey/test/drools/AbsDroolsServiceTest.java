package com.codemonkey.test.drools;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.utils.SysUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/app-test-*.xml" })
@Transactional
public class AbsDroolsServiceTest {

	private Logger log = SysUtils.getLog(getClass());
	
	@Test
	public void test(){
		
	}

	public Logger getLog() {
		return log;
	}
	
}
