package com.codemonkey.dao.searchingInfo;

import org.hibernate.Criteria;

public interface SearchingInfo {

	Criteria addCriterions(Criteria c);
	
	Criteria addOrders(Criteria c);
	
	int getMax();
	
	int getOffset();
}
