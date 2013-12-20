package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.domain.Foo;
import com.codemonkey.utils.HqlHelper;

public class HqlHelperTest {

	@Test
	public void testBuildHql(){
		
		String hql = HqlHelper.findBy(Foo.class, "fstring");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fstring = ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fstring_EQ");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fstring = ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_LE");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fnumber <= ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_LT");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fnumber < ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_GE");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fnumber >= ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_GT");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fnumber > ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fstring_Like");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fstring like ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_isNull");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fnumber is null " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_isNotNull");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fnumber is not null " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_notEQ");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fnumber <> ? " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fnumber_notEQ&&Bars.name_Like");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  LEFT JOIN E.bars as bars where 1=1   And E.fnumber <> ?  And bars.name like ? " , hql);
	
		hql = HqlHelper.findBy(Foo.class, "fnumber_notEQ&&Bars.name_Like||Bars.code_Like");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  LEFT JOIN E.bars as bars where 1=1   And E.fnumber <> ?  And  ( bars.name like ?  OR bars.code like ?  ) " , hql);
	
		hql = HqlHelper.findBy(Foo.class, "fstring_EQOrderByfstring_DESC");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fstring = ?  Order By E.fstring DESC" , hql);
		
		hql = HqlHelper.findBy(Foo.class, "fstring_EQOrderByfstring_DESC&&fnumber_ASC");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And E.fstring = ?  Order By E.fstring DESC , E.fnumber ASC" , hql);
	
		hql = HqlHelper.findBy(Foo.class, "fstring||fnumber");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  where 1=1   And  ( E.fstring = ?  OR E.fnumber = ?  ) " , hql);
		
		hql = HqlHelper.findBy(Foo.class, "parent.bars.name_Like");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.Foo E  LEFT JOIN E.parent as parent LEFT JOIN parent.bars as bars where 1=1   And bars.name like ? " , hql);
		
	}
	
}
