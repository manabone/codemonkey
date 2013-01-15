package com.codemonkey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UrlPermission extends AbsEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String url;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
