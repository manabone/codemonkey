package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.Foo;
import com.codemonkey.domain.Status;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.FooService;
import com.codemonkey.test.service.AbsServiceTest;
import com.codemonkey.utils.JsonArrayConverter;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.converter.CustomConversionService;

public class JsonArrayConverterTest extends AbsServiceTest{

	@Autowired private CustomConversionService ccService;
	@Autowired private AppRoleService appRoleService;
	@Autowired private FooService fooService;
	
	@Test
	public void test(){
		String fstring = "sss";
		boolean fbool = false;
		String fdate = "2013-03-07 00:00:00";
		String fdateString = "2013-03-07";
		Double fnumber = 4d;
		Status fstatus = Status.ACTIVE;
		
		AppRole appRole = new AppRole();
		appRoleService.doSave(appRole);
		
		JSONObject data = new JSONObject();
		JSONArray foos = new JSONArray();
		
		JSONObject fooJo1 = new JSONObject();
		
		fooJo1.put("id", "");
		fooJo1.put("fstring", fstring);
		fooJo1.put("fbool", fbool);
		fooJo1.put("fdate", fdate);
		fooJo1.put("fnumber", fnumber);
		fooJo1.put("fstatus", fstatus);
		fooJo1.put("appRole", appRole.getId());
		
		foos.put(fooJo1);
		
		Foo foo2 = new Foo();
		foo2.setFbool(true);
		foo2.setFdate(new Date());
		foo2.setFnumber(6d);
		foo2.setFstatus(Status.INACTIVE);
		foo2.setFstring("dump");
		foo2.setCode("foo2");
		
		fooService.doSave(foo2);
		
		JSONObject fooJo2 = new JSONObject();
		fooJo2.put("id", foo2.getId());
		fooJo2.put("fstring", fstring);
		fooJo2.put("fbool", fbool);
		fooJo2.put("fdate", fdate);
		fooJo2.put("fnumber", fnumber);
		fooJo2.put("fstatus", fstatus);
		fooJo2.put("appRole", appRole.getId());
		
		foos.put(fooJo2);
		
		data.put("foos", foos);
		
		JsonArrayConverter<Foo> converter = new JsonArrayConverter<Foo>();
		List<Foo> fooList = converter.convert(data , "foos" , Foo.class , ccService);
		
		assertEquals(2 , fooList.size());
		
		Foo foo1 = fooList.get(0);
		assertNotNull(foo1);
		
		assertEquals(fstring , foo1.getFstring());
		assertEquals(fbool , foo1.getFbool());
		assertEquals(fnumber , foo1.getFnumber());
		assertEquals(fstatus , foo1.getFstatus());
		assertEquals(appRole.getId() , foo1.getAppRole().getId());
		assertEquals(fdateString , SysUtils.formatDate(foo1.getFdate()));
		
		Foo foo2v = fooList.get(1);
		assertNotNull(foo2v);
		
		assertEquals(fstring , foo2v.getFstring());
		assertEquals(fbool , foo2v.getFbool());
		assertEquals(fnumber , foo2v.getFnumber());
		assertEquals(fstatus , foo2v.getFstatus());
		assertEquals(appRole.getId() , foo2v.getAppRole().getId());
		
		assertEquals(fdateString , SysUtils.formatDate(foo2v.getFdate()));
	}
	
}
