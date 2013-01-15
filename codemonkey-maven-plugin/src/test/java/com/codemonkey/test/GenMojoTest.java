package com.codemonkey.test;

import java.lang.reflect.Field;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.json.JSONObject;
import org.junit.Test;

import com.codemonkey.annotation.Label;
import com.codemonkey.mojo.GenMojo;

public class GenMojoTest extends AbstractMojoTestCase {

	GenMojo mojo;
	
	@Override
    protected void setUp() throws Exception {
		super.setUp();
		mojo = new GenMojo();
    }
	
	@Override
    protected void tearDown() throws Exception {
		
	}
	
	@Test
	public void testGenLabel(){
		String fieldName = "userName";
		String label = mojo.label(fieldName);
		
		assertEquals( "user name" , label);
		
		fieldName = "user123Name";
		label = mojo.label(fieldName);
		assertEquals( "user123 name" , label);
		
		fieldName = "userName123AndPassword";
		label = mojo.label(fieldName);
		assertEquals( "user name123 and password" , label);
	}
	
	@Label("Foo1")
	class Foo {
		
	}
	
	class TestDomain {
		@Label("my field name")
		public String field;
		
		@Label("中文")
		public String field2;
		
		public Foo foo;
		
		@Label("Foo2")
		public Foo foo2;
	}
	
	@Test
	public void testGenLabel2() throws Exception{
		
		Field f = TestDomain.class.getField("field");
		String label = mojo.label(f);
		assertEquals( "my field name" , label);
		
		f = TestDomain.class.getField("field2");
		label = mojo.label(f);
		assertEquals( "中文" , label);
		
		
		f = TestDomain.class.getField("foo");
		label = mojo.label(f);
		assertEquals( "Foo1" , label);
		
		f = TestDomain.class.getField("foo2");
		label = mojo.label(f);
		assertEquals( "Foo2" , label);
		
		JSONObject jo = new JSONObject();
		jo.put("name", "中文");
		
		assertEquals("{\"name\":\"中文\"}", mojo.unescapeUnicode(jo.toString()));
		
	}
	
}
