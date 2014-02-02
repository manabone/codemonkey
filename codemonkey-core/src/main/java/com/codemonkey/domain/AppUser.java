package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Entity
@DiscriminatorValue("AppUser")
@Audited
public class AppUser extends AbsAppUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
