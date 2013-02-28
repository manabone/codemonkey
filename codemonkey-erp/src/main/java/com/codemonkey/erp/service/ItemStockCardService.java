package com.codemonkey.erp.service;

import com.codemonkey.erp.domain.Item;
import com.codemonkey.erp.domain.ItemStockCard;
import com.codemonkey.erp.domain.Warehouse;
import com.codemonkey.service.GenericService;

public interface ItemStockCardService extends GenericService<ItemStockCard>{

	ItemStockCard getStockCard(Item item , Warehouse warehouse);
	
}
