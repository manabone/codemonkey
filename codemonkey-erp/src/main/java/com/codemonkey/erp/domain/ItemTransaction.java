package com.codemonkey.erp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.utils.Calc;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class ItemTransaction  extends Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Warehouse warehouse;
	
	private Double qty;
	
	private Double qtyOnSalesOrder = 0d;
	
	private Double qtyOnPurchaseOrder = 0d;
	
	private Double qtyOnHand = 0d;
	
	private Date requiredDate;
	
	ItemTransaction(){}
	
	ItemTransaction(DocumentLine docLine) {
		this.qty = docLine.getQty();
		this.item = docLine.getItem();
		this.warehouse = docLine.getWarehouse();
		this.requiredDate = docLine.getRequiredDate();
	}
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		
		jo.put("item", OgnlUtils.stringValue("item.id", this));
		jo.put("item_text", OgnlUtils.stringValue("item.code", this));
		
		jo.put("warehouse", OgnlUtils.stringValue("warehouse.id", this));
		jo.put("warehouse_text", OgnlUtils.stringValue("warehouse.code", this));
		
		jo.put("qty", OgnlUtils.stringValue("qty", this));
		
		return jo;
	}
	
	public void updateStockCard(ItemStockCard stockCard){
		stockCard.setQtyOnSalesOrder(Calc.add(stockCard.getQtyOnSalesOrder() , getQtyOnSalesOrder()));
		stockCard.setQtyOnPurchaseOrder(Calc.add(stockCard.getQtyOnPurchaseOrder() , getQtyOnPurchaseOrder()));
		stockCard.setQtyOnHand(Calc.add(stockCard.getQtyOnHand() , getQtyOnHand()));
	}
	
	public abstract List<ItemPlanning> createPlanning();
	
	ItemPlanning create(ItemPlanning plan){
		plan.setItem(getItem());
		plan.setQty(getQty());
		plan.setWarehouse(getWarehouse());
		plan.setItemTransaction(this);
		plan.setDate(getRequiredDate());
		return plan;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
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

	public Double getQtyOnHand() {
		return qtyOnHand;
	}

	public void setQtyOnHand(Double qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}

}
