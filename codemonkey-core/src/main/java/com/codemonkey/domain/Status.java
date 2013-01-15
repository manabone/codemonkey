package com.codemonkey.domain;

import org.springframework.stereotype.Component;

@Component
public enum Status implements IEnum{

	ACTIVE , INACTIVE;

	public String getText() {
		return getName();
	}

	public String getName() {
		return name();
	}
}
