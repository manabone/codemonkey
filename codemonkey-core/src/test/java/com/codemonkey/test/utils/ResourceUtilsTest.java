package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.test.service.AbsServiceTest;
import com.codemonkey.utils.ResourceUtils;

public class ResourceUtilsTest extends AbsServiceTest {

	@Autowired
	ResourceUtils resourceUtils;

	@Test
	public void testAppSetting() {
		String msg = resourceUtils.getAppSetting("setting.test");
		assertEquals("test-windows", msg);
	}

	@Test
	public void testMessage() {
		String msg = resourceUtils.msg("foo.fnumber");
		assertEquals("fnumber", msg);
	}

}
