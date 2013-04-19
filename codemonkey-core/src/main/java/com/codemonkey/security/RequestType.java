package com.codemonkey.security;

import org.springframework.stereotype.Component;

import com.codemonkey.domain.IEnum;

@Component
public enum RequestType implements IEnum{

	HTML , JSON;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
