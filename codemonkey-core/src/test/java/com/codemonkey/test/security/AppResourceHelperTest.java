package com.codemonkey.test.security;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.Foo;
import com.codemonkey.security.AppResourceHelper;
import com.codemonkey.security.RequestType;

public class AppResourceHelperTest {

	@Test
	public void test(){
		List<AppPermission> list = AppResourceHelper.formPermissions(Foo.class);
		
		assertEquals(4 , list.size());
		assertEquals("/app/ext/foo/create" , list.get(0).getUrl());
		assertEquals("foo:create" , list.get(0).getPermission());
		assertEquals(RequestType.JSON , list.get(0).getRequestType());
		
		assertEquals("/app/ext/foo/read" , list.get(1).getUrl());
		assertEquals("foo:read" , list.get(1).getPermission());
		assertEquals(RequestType.JSON , list.get(1).getRequestType());
		
		assertEquals("/app/ext/foo/update" , list.get(2).getUrl());
		assertEquals("foo:update" , list.get(2).getPermission());
		assertEquals(RequestType.JSON , list.get(2).getRequestType());
		
		assertEquals("/app/ext/foo/edit" , list.get(3).getUrl());
		assertEquals("foo:edit" , list.get(3).getPermission());
		assertEquals(RequestType.HTML , list.get(3).getRequestType());
		
		list = AppResourceHelper.listPermissions(Foo.class);
		
		assertEquals(3 , list.size());
	
		assertEquals("/app/ext/fooList/read" , list.get(0).getUrl());
		assertEquals("fooList:read" , list.get(0).getPermission());
		assertEquals(RequestType.JSON , list.get(0).getRequestType());
		
		assertEquals("/app/ext/fooList/destroy" , list.get(1).getUrl());
		assertEquals("fooList:destroy" , list.get(1).getPermission());
		assertEquals(RequestType.JSON , list.get(1).getRequestType());
		
		assertEquals("/app/ext/fooList/index" , list.get(2).getUrl());
		assertEquals("fooList:list" , list.get(2).getPermission());
		assertEquals(RequestType.HTML , list.get(2).getRequestType());
	}
}
