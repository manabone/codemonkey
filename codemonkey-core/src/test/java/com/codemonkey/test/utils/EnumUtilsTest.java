package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.Test;

import com.codemonkey.utils.EnumUtils;

public class EnumUtilsTest {

	@Test
	public void testGettingEnumData(){
		JSONArray ja = EnumUtils.getEnmuDataByClazz("com.codemonkey.domain.Status");
		assertEquals(2 , ja.length());
		
	}
}
