package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.test.service.AbsServiceTest;
import com.codemonkey.utils.SysUtils;

public class SysUtilsTest extends AbsServiceTest{

	@Autowired SysUtils sysUtils;
	
	@Test
	public void testColumnToProp(){
		String prop = SysUtils.columnToProp("test_a");
		assertEquals("testA" , prop);
		
		prop = SysUtils.columnToProp("test_1");
		assertEquals("test1" , prop);
		
		prop = SysUtils.columnToProp("test_good_1");
		assertEquals("testGood1" , prop);
	}
	
	@Test
	public void testPropToColumn(){
		String prop = SysUtils.propToColumn("testA");
		assertEquals("test_a" , prop);
		
		prop = SysUtils.propToColumn("test1");
		assertEquals("test_1" , prop);
		
		prop = SysUtils.propToColumn("testGood1");
		assertEquals("test_good_1" , prop);
	}
	
	@Test
	public void testFormatString(){
		String s = "hello world";
		String result = SysUtils.formatString(s);
		
		assertEquals("hello world" , result);
		
		s = "hello world {0}";
		result = SysUtils.formatString(s , "java");
		
		assertEquals("hello world java" , result);
		
		s = "hello world {0} {1}";
		result = SysUtils.formatString(s , "java");
		
		assertEquals("hello world java {1}" , result);
		
		s = "hello world {0} {1}";
		result = SysUtils.formatString(s , "java" , "ruby" , "python");
		
		assertEquals("hello world java ruby" , result);
	}
	
}
