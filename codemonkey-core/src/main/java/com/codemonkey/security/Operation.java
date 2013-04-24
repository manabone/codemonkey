package com.codemonkey.security;

import com.codemonkey.domain.IEnum;

public enum Operation implements IEnum{
	
	CREATE,
	DESTROY,
	UPDATE,
	READ,
	
	POST,
	
	EDIT{
		@Override
		public RequestType getRequestType(){
			return RequestType.HTML;
		}
	},
	LIST{
		@Override
		public RequestType getRequestType(){
			return RequestType.HTML;
		}
	},
	NEW{
		@Override
		public RequestType getRequestType(){
			return RequestType.HTML;
		}
	};

	public RequestType getRequestType(){
		return RequestType.JSON;
	}
	
	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}
	
}
