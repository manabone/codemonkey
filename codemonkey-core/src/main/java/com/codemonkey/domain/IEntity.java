package com.codemonkey.domain;

import java.util.Date;

import org.json.JSONObject;

import com.codemonkey.domain.seq.SequenceCreator;

public interface IEntity {

	Long getId();
	
	boolean isNew();
	
	String getCode();
	
	void setCode(String code);
	
	void setModificationDate(Date modificationDate);
	
	void setCreationDate(Date creationDate);
	
	void setCreatedBy(String username);
	
	void setModifiedBy(String username);
	
	JSONObject listJson();
	
	JSONObject detailJson();
	
	boolean isOptimisticLockingFailure();
	
	void setOriginVersion(Integer originVersion);
	
	Integer getVersion();
	
	SequenceCreator getSequenceCreator();
	
}
