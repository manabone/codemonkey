package com.codemonkey.extcmp;


public class ExtColHidden extends ExtCol{

	public ExtColHidden(String id) {
		super(id);
		setDataIndex(id);
		setHeader(id);
		setHidden(true);
	}
	
}
