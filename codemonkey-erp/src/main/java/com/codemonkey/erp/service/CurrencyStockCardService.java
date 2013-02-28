package com.codemonkey.erp.service;

import com.codemonkey.erp.domain.Currency;
import com.codemonkey.erp.domain.CurrencyStockCard;
import com.codemonkey.service.GenericService;

public interface CurrencyStockCardService extends GenericService<CurrencyStockCard>{

	CurrencyStockCard getStockCard(Currency currency);
	
}
