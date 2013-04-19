package com.codemonkey.error;


public class SessionTimeoutError extends SysError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SessionTimeoutError(){
		super("session timeout please relogin");
	}

}
