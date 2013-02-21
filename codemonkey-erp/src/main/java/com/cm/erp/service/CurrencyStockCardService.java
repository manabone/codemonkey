package com.cm.erp.service;

import com.cm.erp.domain.Currency;
import com.cm.erp.domain.CurrencyStockCard;
import com.codemonkey.service.GenericService;

public interface CurrencyStockCardService extends GenericService<CurrencyStockCard>{

	CurrencyStockCard getStockCard(Currency currency);
	
}
