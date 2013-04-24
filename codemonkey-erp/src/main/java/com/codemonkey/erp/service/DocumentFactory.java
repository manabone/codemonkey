package com.codemonkey.erp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.codemonkey.erp.domain.PurchaseOrder;
import com.codemonkey.erp.domain.PurchaseOrderLine;
import com.codemonkey.erp.domain.PurchaseReceipt;
import com.codemonkey.erp.domain.PurchaseReceiptLine;
import com.codemonkey.erp.domain.SalesOrder;
import com.codemonkey.erp.domain.SalesOrderLine;
import com.codemonkey.erp.domain.SalesShipment;
import com.codemonkey.erp.domain.SalesShipmentLine;

public class DocumentFactory{

	public static SalesShipment createSalesShipment(SalesOrder so){
		
		SalesShipment sp = new SalesShipment();
		sp.setWarehouse(so.getWarehouse());
		
		List<SalesShipmentLine> spLines = buildShipmentLines(so.getLines());
		
		sp.setLines(spLines);
		
		return sp;
	}

	private static List<SalesShipmentLine> buildShipmentLines(List<SalesOrderLine> lines) {
		List<SalesShipmentLine> spLines = new ArrayList<SalesShipmentLine>();
		
		if(CollectionUtils.isEmpty(lines)){
			return spLines;
		}
		
		for(SalesOrderLine soLine : lines){
			SalesShipmentLine spLine = new SalesShipmentLine();
			spLine.setItem(soLine.getItem());
			spLine.setQty(soLine.getQty());
			spLine.setRequiredDate(soLine.getRequiredDate());
			spLine.setSalesOrderLine(soLine);
			spLines.add(spLine);
		}
			
		return spLines;
	}
	
	public static PurchaseReceipt createPurchaseReceipt(PurchaseOrder po){
		
		PurchaseReceipt pr = new PurchaseReceipt();
		pr.setWarehouse(po.getWarehouse());
		
		List<PurchaseReceiptLine> prLines = buildReceiptLines(po.getLines());
		
		pr.setLines(prLines);
		
		return pr;
	}

	private static List<PurchaseReceiptLine> buildReceiptLines(List<PurchaseOrderLine> lines) {
		List<PurchaseReceiptLine> prLines = new ArrayList<PurchaseReceiptLine>();
		
		if(CollectionUtils.isEmpty(lines)){
			return prLines;
		}
		
		for(PurchaseOrderLine line : lines){
			PurchaseReceiptLine prLine = new PurchaseReceiptLine();
			prLine.setItem(line.getItem());
			prLine.setQty(line.getQty());
			prLine.setRequiredDate(line.getRequiredDate());
			prLine.setPurchaseOrderLine(line);
			prLines.add(prLine);
		}
			
		return prLines;
	}
}
