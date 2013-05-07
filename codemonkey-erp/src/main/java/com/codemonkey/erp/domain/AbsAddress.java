package com.codemonkey.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.codemonkey.domain.AbsEE;

@Entity
public class AbsAddress extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Country country;
	
	@ManyToOne
	private Province province;
	
	@ManyToOne
	private City city;
	
	private String street;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
