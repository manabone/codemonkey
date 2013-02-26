package com.codemonkey.startup;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

public interface StartUpService {
	
	@PostConstruct
	public abstract void doInitDefaultData() throws SQLException;

	@PostConstruct
	public abstract void doInitAppPermissions() throws Exception;

}