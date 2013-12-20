package com.codemonkey.domain;

import org.springframework.stereotype.Component;

@Component
public enum Bool implements IEnum{

	是 , 否;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
