package com.codemonkey.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.Foo;
import com.codemonkey.domain.Status;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.FooService;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.web.converter.CustomConversionService;

public class FooServiceTest extends GenericServiceTest<Foo> {

	@Autowired private FooService fooService;
	@Autowired private AppRoleService appRoleService;
	@Autowired private CustomConversionService ccService;
	
	@Test
	public void testVersion(){
		Foo foo = new Foo();
		foo.setFstring("test");
		foo.setFnumber(3d);
		fooService.doSave(foo);
		
		assertEquals(new Integer(0) , foo.getVersion());
		assertEquals(new Integer(0) , foo.getOriginVersion());
		
//		foo.setFstring("test1");
//		fooService.doSave(foo);
//		
//		Foo foo2 = fooService.get(foo.getId());
//		assertEquals(new Integer(1) , foo2.getVersion());
//		assertEquals(new Integer(1) , foo2.getOriginVersion());
		
	}
	
	@Test
	public void testSkipBuild(){
		
		Foo foo = new Foo();
		
		JSONObject params = new JSONObject();
		params.put("skipBuild", "test");
		
		ClassHelper.bulid(params, foo , ccService);
	
		assertNull(foo.getSkipBuild());
	}
	
	@Test
	public void testQueryInfo(){
		
		AppRole role = new AppRole();
		role.setName("role1");
		appRoleService.doSave(role);
		
		Foo foo1 = new Foo();
		foo1.setFbool(true);
		foo1.setFdate(new DateTime("2011-12-06").toDate());
		foo1.setFnumber(1d);
		foo1.setFstatus(Status.ACTIVE);
		foo1.setFstring("111");
		foo1.setAppRole(role);
		
		Foo foo2 = new Foo();
		foo2.setFbool(false);
		foo2.setFdate(new DateTime("2011-12-07").toDate());
		foo2.setFnumber(2d);
		foo2.setFstatus(Status.ACTIVE);
		foo2.setFstring("222");
		
		Foo foo3 = new Foo();
		foo3.setFbool(false);
		foo3.setFdate(new DateTime("2011-12-08").toDate());
		foo3.setFnumber(3d);
		foo3.setFstatus(Status.INACTIVE);
		foo3.setFstring("333");
		
		//test saving
		fooService.doSave(foo1);
		fooService.doSave(foo2);
		fooService.doSave(foo3);
		
		JSONObject queryInfo = new JSONObject();
		List<Foo> foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(3 , foos.size());
		
		queryInfo.put("fbool", "true");
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(1 , foos.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("fnumber_GE", "2");
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(2 , foos.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("fstatus", "ACTIVE");
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(2 , foos.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("fdate_GE", "2011-12-07");
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(2 , foos.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("fstring_Like", "3");
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(1 , foos.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("appRole.id", role.getId());
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(1 , foos.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("appRole.name", "role1");
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(1 , foos.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("fbool", "false");
		queryInfo.put("fnumber_LE", "2");
		foos = fooService.findByQueryInfo(queryInfo);
		assertEquals(1 , foos.size());
		
	}
}
