package com.codemonkey.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.Bar;
import com.codemonkey.domain.Foo;
import com.codemonkey.domain.Status;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.BarService;
import com.codemonkey.service.FooService;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.web.converter.CustomConversionService;

public class FooServiceTest extends AbsServiceTest {

	@Autowired private FooService fooService;
	@Autowired private AppRoleService appRoleService;
	@Autowired private CustomConversionService ccService;
	@Autowired private BarService barService;
	
	@Test
	public void testUnique(){
		Foo foo1 = new Foo();
		foo1.setCode("foo-1");
		foo1.setFstring("test");
		foo1.setFnumber(3d);
		assertEquals(true , fooService.isUnique(foo1, "code", foo1.getCode()));
		
		Foo foo2 = new Foo();
		foo2.setCode("foo-1");
		foo2.setFstring("test");
		assertEquals(true , fooService.isUnique(foo2, "code", foo2.getCode()));
		
		fooService.save(foo1);
		assertEquals(true , fooService.isUnique(foo1, "code", foo1.getCode()));
		
		assertEquals(false , fooService.isUnique(foo2, "code", foo2.getCode()));
		
	}
	
	@Test
	public void testLeftJoin(){
		Foo foo = new Foo();
		foo.setFstring("test");
		foo.setFnumber(3d);
		fooService.save(foo);
		
		Bar bar1 = new Bar();
		bar1.setName("bar1");
		bar1.setFoo(foo);
		
		Bar bar2 = new Bar();
		bar2.setName("bar2");
		bar2.setFoo(foo);
		
		barService.save(bar1);
		barService.save(bar2);
		
		List<Foo> list = fooService.findAllBy("bars.name" , "bar1");
		
		assertEquals(1 , list.size());
		
		long count = fooService.countBy("bars.name" , "bar1");
		
		assertEquals(1 , count);
		
		Foo foo2 = fooService.findBy("bars.name" , "bar1");
		
		assertNotNull(foo2);
		
		
		JSONObject queryInfo = new JSONObject();
		queryInfo.put("JOINS", "bars_LEFT");
		queryInfo.put("bars.name_Like", "bar");
		
		JSONObject queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY , queryInfo);
		
		list = fooService.findByQueryInfo(queryAndSort);
		
		assertEquals(1 , list.size());
		
		queryInfo.put("fstring", "test");
		list = fooService.findByQueryInfo(queryAndSort);
		
		assertEquals(1 , list.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("fnumber_LE", 3);
		queryAndSort.put(ExtConstant.QUERY , queryInfo);
		list = fooService.findByQueryInfo(queryAndSort);
		
		assertEquals(1 , list.size());
		
		queryInfo = new JSONObject();
		queryInfo.put("fnumber_LE", 2);
		queryAndSort.put(ExtConstant.QUERY , queryInfo);
		list = fooService.findByQueryInfo(queryAndSort);
		
		assertEquals(0 , list.size());
		
		//test or
		queryInfo = new JSONObject();
		queryInfo.put("JOINS", "bars_LEFT");
		queryInfo.put("bars.name_Like||bars.code_Like", "bar");
		queryAndSort.put(ExtConstant.QUERY , queryInfo);
		
		list = fooService.findByQueryInfo(queryAndSort);
		
		assertEquals(1 , list.size());
		
	}
	
	@Test
	public void testMultiLeftJoin(){
		Foo fooParent = new Foo();
		fooParent.setCode("foo-parent");
		fooParent.setFstring("foo-parent");
		fooParent.setFnumber(3d);
		fooService.save(fooParent);
		
		Foo foo = new Foo();
		foo.setCode("foo");
		foo.setFstring("foo");
		foo.setFnumber(3d);
		foo.setParent(fooParent);
		fooService.save(foo);
		
		Bar bar1 = new Bar();
		bar1.setName("bar1");
		bar1.setFoo(fooParent);
		
		Bar bar2 = new Bar();
		bar2.setName("bar2");
		bar2.setFoo(fooParent);
		
		barService.save(bar1);
		barService.save(bar2);
		
		fooService.findAll();
		barService.findAll();
		
		List<Foo> list = fooService.findAllBy("parent.bars.name_Like", "%bar1%");
		assertEquals(1 , list.size());
	}
	
	@Test
	public void testVersion(){
		Foo foo = new Foo();
		foo.setFstring("test");
		foo.setFnumber(3d);
		fooService.save(foo);
		
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
		
		ClassHelper.build(params, foo , ccService);
	
		assertNull(foo.getSkipBuild());
	}
	
	@Test
	public void testQueryInfo(){
		
		AppRole role = new AppRole();
		role.setName("role1");
		appRoleService.save(role);
		
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
		fooService.save(foo1);
		fooService.save(foo2);
		fooService.save(foo3);
		
		//from Foo where 1 = 1
		JSONObject queryAndSort = new JSONObject();
		List<Foo> foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(3 , foos.size());
		
		foos = fooService.findAll();
		assertEquals(3 , foos.size());
		
		//from Foo E where 1 = 1 and E.fbool = ? [true]
		JSONObject queryInfo = new JSONObject().put("fbool", "true");
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(1 , foos.size());
		
		foos = fooService.findAllBy("fbool", true);
		assertEquals(1 , foos.size());
		
		//from Foo E where 1 = 1 and E.fnumber >= ? [2]
		queryInfo = new JSONObject();
		queryInfo.put("fnumber_GE", "2");
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(2 , foos.size());
		
		foos = fooService.findAllBy("fnumber_GE", 2d);
		assertEquals(2 , foos.size());
		
		//from Foo E where 1 = 1 and E.fstatus = ? ['ACTIVE']
		queryInfo = new JSONObject();
		queryInfo.put("fstatus", "ACTIVE");
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(2 , foos.size());
		
		foos = fooService.findAllBy("fstatus", Status.ACTIVE);
		assertEquals(2 , foos.size());
		
		//from Foo E where 1 = 1 and E.fdate >=  ? ['2011-12-07']
		queryInfo = new JSONObject();
		queryInfo.put("fdate_GE", "2011-12-07");
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(2 , foos.size());
		
		Date date = new DateTime("2011-12-07").toDate();
		foos = fooService.findAllBy("fdate_GE", date);
		assertEquals(2 , foos.size());
		
		//from Foo E where 1 = 1 and E.fstring like ? ['%3%']
		queryInfo = new JSONObject();
		queryInfo.put("fstring_Like", "3");
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(1 , foos.size());
		
		foos = fooService.findAllBy("fstring_Like" , "%3%");
		assertEquals(1 , foos.size());
		
		//from Foo E where 1 = 1 and E.appRole.id = ? [1]
		queryInfo = new JSONObject();
		queryInfo.put("appRole.id", role.getId());
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(1 , foos.size());
		
		foos = fooService.findAllBy("appRole.id" , role.getId());
		assertEquals(1 , foos.size());
		
		//from Foo E where 1 = 1 and E.appRole.name = ? ['role1']
		queryInfo = new JSONObject();
		queryInfo.put("appRole.name", "role1");
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(1 , foos.size());
		
		foos = fooService.findAllBy("appRole.name" , "role1");
		assertEquals(1 , foos.size());
		
		//from Foo E where 1 = 1 and E.fbool = ? and E.fnumber >= ? [false , 2]
		queryInfo = new JSONObject();
		queryInfo.put("fbool", "false");
		queryInfo.put("fnumber_LE", "2");
		queryAndSort = new JSONObject();
		queryAndSort.put(ExtConstant.QUERY, queryInfo);
		foos = fooService.findByQueryInfo(queryAndSort);
		assertEquals(1 , foos.size());
		
		foos = fooService.findAllBy("fbool&&fnumber_LE" , false , 2d);
		assertEquals(1 , foos.size());
		
	}
	
	@Test 
	public void testFindByAndCountBy(){
		
		Foo foo = new Foo();
		foo.setFstring("aa");
		foo.setFnumber(1.0);
		fooService.save(foo);
				
		Foo fooa = new Foo();
		fooa.setFstring("aaa");
		fooa.setFnumber(1.0);
		fooService.save(fooa);
		
		Foo foob = new Foo();
		foob.setFstring("bbb");
		foob.setFnumber(2.0);
		fooService.save(foob);
		
		Foo fooc = new Foo();
		fooc.setFstring("ccc");
		fooc.setFnumber(3.0);
		fooService.save(fooc);
		
		Foo food = new Foo();
		food.setFstring("ddd");
		food.setFnumber(3.0);
		food.setFdate(new Date());
		fooService.save(food);
		
		
		long count = fooService.countBy("fstring", "aa");
		assertEquals(1 , count);
		
		List<Foo> list = fooService.findAllBy("fstring", "aa");
		assertEquals(1 , list.size());
		
		count = fooService.countBy("fstring_EQ", "aa");
		assertEquals(1 , count);
		
		list = fooService.findAllBy("fstring_EQ", "aa");
		assertEquals(1 , list.size());
		
		count = fooService.countBy("fnumber_notEQ", 1.0);
		assertEquals(3 , count);
		
		list = fooService.findAllBy("fnumber_notEQ", 1.0);
		assertEquals(3 , list.size());
		
		count = fooService.countBy("fstring_Like", "%aa%");
		assertEquals(2 , count);
		
		list = fooService.findAllBy("fstring_Like", "%aa%");
		assertEquals(2 , list.size());
		
		count = fooService.countBy("fstring_Like", "%aa%");
		assertEquals(2 , count);
		
		list = fooService.findAllBy("fstring_Like", "%aa%");
		assertEquals(2 , list.size());
		
		count = fooService.countBy("fnumber_GE", 1.0);
		assertEquals(5 , count);
		
		list = fooService.findAllBy("fnumber_GE", 1.0);
		assertEquals(5 , list.size());
		
		count = fooService.countBy("fnumber_GT", 1.0);
		assertEquals(3 , count);
		
		list = fooService.findAllBy("fnumber_GT", 1.0);
		assertEquals(3 , list.size());
		
		count = fooService.countBy("fnumber_LE", 3.0);
		assertEquals(5 , count);
		
		list = fooService.findAllBy("fnumber_LE", 3.0);
		assertEquals(5 , list.size());
		
		count = fooService.countBy("fnumber_LT", 3.0);
		assertEquals(3 , count);
		
		list = fooService.findAllBy("fnumber_LT", 3.0);
		assertEquals(3 , list.size());
		
		count = fooService.countBy("fdate_isNull");
		assertEquals(4 , count);
		
		list = fooService.findAllBy("fdate_isNull");
		assertEquals(4 , list.size());
		
		count = fooService.countBy("fdate_isNotNull");
		assertEquals(1 , count);
		
		list = fooService.findAllBy("fdate_isNotNull");
		assertEquals(1 , list.size());
		
		count = fooService.countBy("fnumber_GE&&fnumber_LE" , 2.0 , 3.0);
		assertEquals(3 , count);
		
		list = fooService.findAllBy("fnumber_GE&&fnumber_LE" , 2.0 , 3.0);
		assertEquals(3 , list.size());
		
		Foo fooTest = fooService.findBy("fstring", "bbb");
		assertEquals(foob.getId() , fooTest.getId());
		
		list = fooService.findAllBy("OrderByFnumber_DESC");
		assertEquals(new Double(3.0) , list.get(0).getFnumber());
		
		list = fooService.findAllBy("OrderByFnumber_DESC&&Fdate_ASC");
		assertNull(list.get(0).getFdate());
		
		list = fooService.findAllBy("fdate_isNotNullOrderByFnumber_DESC&&Fdate_ASC");
		assertNotNull(list.get(0).getFdate());
		assertEquals(new Double(3.0) , list.get(0).getFnumber());
	}
}
