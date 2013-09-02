package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.codemonkey.domain.Foo;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.HqlHelper;

public class HqlHelperTest {

	@Test
	public void orderByQueryInfo() throws ParseException{
		
		JSONArray sortArray = new JSONArray("[{property:\"fstring\",direction:\"ASC\"}]");
		JSONObject sort = new JSONObject().put(ExtConstant.SORT, sortArray);
		String orderBy = HqlHelper.orderByQueryInfo(sort);
		assertEquals(" ORDER BY fstring ASC" , orderBy);
		
		sortArray = new JSONArray("[{property:\"fstring\",direction:\"ASC\"},{property:\"fstring2\",direction:\"DESC\"}]");
		sort = new JSONObject().put(ExtConstant.SORT, sortArray);
		orderBy = HqlHelper.orderByQueryInfo(sort);
		assertEquals(" ORDER BY fstring ASC , fstring2 DESC" , orderBy);

	}
	
	@Test
	public void testBuildHql(){
		
		String hql = HqlHelper.findyBy(Foo.class, "fstring");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fstring = ? " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fstring_EQ");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fstring = ? " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fnumber_LE");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fnumber <= ? " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fnumber_LT");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fnumber < ? " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fnumber_GE");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fnumber >= ? " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fnumber_GT");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fnumber > ? " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fstring_Like");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fstring like ? " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fnumber_isNull");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fnumber is null " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fnumber_isNotNull");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fnumber is not null " , hql);
		
		hql = HqlHelper.findyBy(Foo.class, "fnumber_notEQ");
		
		assertEquals("SELECT E FROM com.codemonkey.domain.Foo E  where 1=1   And fnumber <> ? " , hql);
		
	}
	
}
