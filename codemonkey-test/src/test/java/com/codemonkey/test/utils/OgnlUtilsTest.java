package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.Foo;
import com.codemonkey.domain.Status;
import com.codemonkey.utils.OgnlUtils;
import com.codemonkey.utils.SysUtils;

public class OgnlUtilsTest {

	@Test
	public void testGettingPropValue(){
		Foo foo = new Foo();
		
		foo.setCode("code1");
		
		assertEquals("code1" , OgnlUtils.stringValue("code", foo));
		
		
		assertEquals("" , OgnlUtils.stringValue("appRole.code", foo));
		
		AppRole appRole = new AppRole();
		appRole.setCode("role_user");
		foo.setAppRole(appRole);
		assertEquals("role_user" , OgnlUtils.stringValue("appRole.code", foo));
		
		Date fdate = new Date();
		foo.setFdate(fdate);
		assertEquals(SysUtils.formatDate(fdate) , OgnlUtils.stringValue("fdate", foo));
		
		foo.setFbool(true);
		assertEquals("true" , OgnlUtils.stringValue("fbool", foo));
		
		foo.setFnumber(12.3232131234);
		assertEquals("12.323" , OgnlUtils.stringValue("fnumber", foo));
		
		foo.setFstatus(Status.ACTIVE);
		assertEquals("ACTIVE" , OgnlUtils.stringValue("fstatus", foo));
		
	}
	
}
