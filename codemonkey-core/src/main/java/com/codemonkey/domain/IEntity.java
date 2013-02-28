package com.codemonkey.domain;

import java.util.Date;

import org.json.JSONObject;

public interface IEntity {

	Long getId();
	
	String getCode();
	
	void setModificationDate(Date modificationDate);
	
	void setCreationDate(Date creationDate);
	
	void setCreatedBy(String username);
	
	void setModifiedBy(String username);
	
	JSONObject listJson();
	
	JSONObject detailJson();
	
	boolean isOptimisticLockingFailure();
	
	void setOriginVersion(Integer originVersion);
	
	Integer getVersion();
	
}
