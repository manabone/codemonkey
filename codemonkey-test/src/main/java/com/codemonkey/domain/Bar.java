package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.json.JSONObject;

@Entity
@DiscriminatorValue("bar")
@Audited
public class Bar extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Foo foo;
	
	@ManyToOne
	private Bar bar;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("foo",  getFoo() != null ? getFoo().getId() : "");
		jo.put("bar",  getBar() != null ? getBar().getId() : "");
		return jo;
	}

	public Foo getFoo() {
		return foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

	public Bar getBar() {
		return bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

}
