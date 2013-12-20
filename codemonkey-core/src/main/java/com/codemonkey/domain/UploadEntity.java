package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UPLOAD_ENTITY")
public class UploadEntity extends AbsUploadEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
