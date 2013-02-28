package com.codemonkey.erp.service;

import org.springframework.stereotype.Service;

import com.codemonkey.erp.domain.Currency;
import com.codemonkey.erp.domain.CurrencyStockCard;
import com.codemonkey.service.GenericServiceImpl;

@Service
public class CurrencyStockCardServiceImpl extends GenericServiceImpl<CurrencyStockCard> implements CurrencyStockCardService{

	public CurrencyStockCard getStockCard(Currency currency) {
		CurrencyStockCard stockCard = findBy("currency", currency);
		if(stockCard == null){
			stockCard = new CurrencyStockCard(currency);
			doSave(stockCard);
		}
		return stockCard;
	}

}
