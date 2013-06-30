package com.codemonkey.test.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.Foo;
import com.codemonkey.service.FooService;
import com.codemonkey.service.MybatisService;


@SuppressWarnings("rawtypes")
public class MybatisServiceTest extends GenericServiceTest {

	@Autowired private MybatisService mybatisService;

	@Test
	//TODO : insert test data and finish test
	public void testQuery(){
		
		List<Map<String , Object>> list = mybatisService.query("selectFooList", null);
//		assertEquals(2 , list.size());
	}
	
	@Test
	//TODO : insert test data and finish test
	public void testCount(){
		
		long count = mybatisService.count("selectFooList", null);
//		assertEquals(2 , count);
		
	}
}
