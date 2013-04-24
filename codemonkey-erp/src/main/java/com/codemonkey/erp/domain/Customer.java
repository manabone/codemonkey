package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.codemonkey.domain.AbsEE;

@Entity
public class Customer  extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy="customer",cascade=CascadeType.ALL)
	private List<PartnerContact> contacts;
	
	@OneToMany(mappedBy="customer",cascade=CascadeType.ALL)
	private List<PartnerAddress> addresses;

	public List<PartnerContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<PartnerContact> contacts) {
		this.contacts = contacts;
	}

	public List<PartnerAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<PartnerAddress> addresses) {
		this.addresses = addresses;
	}
	
}
