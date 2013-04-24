package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;

import com.codemonkey.domain.AbsEE;

@Entity
public class Vendor extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy="vendor",cascade=CascadeType.ALL)
	private List<PartnerContact> contacts;
	
	@OneToMany(mappedBy="vendor",cascade=CascadeType.ALL)
	private List<PartnerAddress> addresses;

	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		
		PartnerContact defaultContact = getDefalutContact();
		if(defaultContact != null){
			jo.put("defaultContact", defaultContact.listJson());
		}
		
		PartnerAddress defaultAddress = getDefalutAddress();
		if(defaultAddress != null){
			jo.put("defaultAddress", defaultAddress.listJson());
		}
		
		return jo;
	}
	
	private PartnerAddress getDefalutAddress() {
		if(CollectionUtils.isNotEmpty(addresses)){
			for(PartnerAddress addr : addresses){
				if(addr.getIsDefault()){
					return addr;
				}
			}
		}
		return null;
	}

	private PartnerContact getDefalutContact() {
		if(CollectionUtils.isNotEmpty(contacts)){
			for(PartnerContact contact : contacts){
				if(contact.getIsDefault()){
					return contact;
				}
			}
		}
		return null;
	}

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
