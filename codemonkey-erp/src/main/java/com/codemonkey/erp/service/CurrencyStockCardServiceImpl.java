package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Currency;
import com.codemonkey.erp.domain.CurrencyStockCard;
import com.codemonkey.service.PhysicalServiceImpl;

@Service
public class CurrencyStockCardServiceImpl extends PhysicalServiceImpl<CurrencyStockCard> implements CurrencyStockCardService{

	public CurrencyStockCard getStockCard(Currency currency) {
		CurrencyStockCard stockCard = findBy("currency", currency);
		if(stockCard == null){
			stockCard = new CurrencyStockCard();
			stockCard.setCurrency(currency);
			save(stockCard);
		}
		return stockCard;
	}
	
	@Override
	public CurrencyStockCard createEntity() {
		return new CurrencyStockCard();
	}


}
