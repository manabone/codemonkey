package com.codemonkey.erp.domain;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.json.JSONObject;

import com.codemonkey.domain.AbsEE;
import com.codemonkey.utils.OgnlUtils;

@MappedSuperclass
public abstract class Document extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double totalAmount;
	
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Enumerated(EnumType.STRING)
	private DocumentStatus status;
	
	@ManyToOne
	private Warehouse warehouse;
	
	Document(){
		this.status = DocumentStatus.Draft;
	}

	public abstract List<ItemTransaction> createItemTransactions();

	public abstract List<CurrencyTransaction> createCurrencyTransactions();
	
	@Override
	public JSONObject listJson() {
		JSONObject json = super.listJson();
		
		json.put("warehouse", OgnlUtils.stringValue("warehouse.id", this));
		json.put("warehouse_text", OgnlUtils.stringValue("warehouse.code", this));
		json.put("currency", OgnlUtils.stringValue("currency", this));
		json.put("status", OgnlUtils.stringValue("status", this));
		json.put("totalAmount", OgnlUtils.stringValue("totalAmount", this));
		
		return json;
	}
	
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public DocumentStatus getStatus() {
		return status;
	}

	public void setStatus(DocumentStatus status) {
		this.status = status;
	}

}
