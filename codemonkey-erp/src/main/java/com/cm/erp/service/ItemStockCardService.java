package com.cm.erp.service;

import com.cm.erp.domain.Item;
import com.cm.erp.domain.ItemStockCard;
import com.cm.erp.domain.Warehouse;
import com.codemonkey.service.GenericService;

public interface ItemStockCardService extends GenericService<ItemStockCard>{

	ItemStockCard getStockCard(Item item , Warehouse warehouse);
	
}
