package com.codemonkey.erp.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.domain.AbsEE;
import com.codemonkey.utils.OgnlUtils;

@Entity
public class ItemStockCard  extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Warehouse warehouse;
	
	private Double qtyOnHand = 0d;

	private Double qtyOnSalesOrder = 0d;
	
	private Double qtyOnPurchaseOrder = 0d;
	
	ItemStockCard(){}
	
	public ItemStockCard(Item item, Warehouse warehouse) {
		this.item = item;
		this.warehouse = warehouse;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Double getQtyOnHand() {
		return qtyOnHand;
	}

	public void setQtyOnHand(Double qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}

	public Double getQtyOnSalesOrder() {
		return qtyOnSalesOrder;
	}

	public void setQtyOnSalesOrder(Double qtyOnSalesOrder) {
		this.qtyOnSalesOrder = qtyOnSalesOrder;
	}

	public Double getQtyOnPurchaseOrder() {
		return qtyOnPurchaseOrder;
	}

	public void setQtyOnPurchaseOrder(Double qtyOnPurchaseOrder) {
		this.qtyOnPurchaseOrder = qtyOnPurchaseOrder;
	}
	
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("item", OgnlUtils.stringValue("item.id", this));
		jo.put("item_text", OgnlUtils.stringValue("item.code", this));
		
		jo.put("warehouse", OgnlUtils.stringValue("warehouse.id", this));
		jo.put("warehouse_text", OgnlUtils.stringValue("warehouse.code", this));
		
		jo.put("qtyOnHand", OgnlUtils.stringValue("qtyOnHand", this));
		jo.put("qtyOnSalesOrder", OgnlUtils.stringValue("qtyOnSalesOrder", this));
		jo.put("qtyOnPurchaseOrder", OgnlUtils.stringValue("qtyOnPurchaseOrder", this));
		
		return jo;
	}

}
