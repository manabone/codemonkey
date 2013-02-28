package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Item;
import com.codemonkey.erp.domain.ItemStockCard;
import com.codemonkey.erp.domain.Warehouse;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class ItemStockCardServiceImpl extends GenericServiceImpl<ItemStockCard> implements ItemStockCardService{

	public ItemStockCard getStockCard(Item item, Warehouse warehouse) {
		ItemStockCard stockCard = findBy("item.idAndwarehouse.id", item.getId() , warehouse.getId());
		if(stockCard == null){
			stockCard = new ItemStockCard(item , warehouse);
			doSave(stockCard);
		}
		return stockCard;
	}

}
