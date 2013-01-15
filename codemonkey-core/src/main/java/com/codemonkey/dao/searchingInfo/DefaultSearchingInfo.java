package com.codemonkey.dao.searchingInfo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;


public class DefaultSearchingInfo implements SearchingInfo{

	private int max = 20;
	private int offset = 0;
	
	public List<Criterion> buidCriterions() {
		return null;
	}

	public List<Order> buidOrders() {
		return null;
	}

	public int getMax() {
		return max;
	}

	public int getOffset() {
		return offset;
	}

	public Criteria addCriterions(Criteria c) {
		return c;
	}

	public Criteria addOrders(Criteria c) {
		return c;
	}

}
