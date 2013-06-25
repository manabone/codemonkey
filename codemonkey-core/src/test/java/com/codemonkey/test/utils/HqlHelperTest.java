package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.codemonkey.utils.HqlHelper;

public class HqlHelperTest {

	@Test
	public void orderByQueryInfo() throws ParseException{
		
		JSONArray sortArray = new JSONArray("[{property:\"fstring\",direction:\"ASC\"}]");
		JSONObject sort = new JSONObject().put("sort", sortArray);
		String orderBy = HqlHelper.orderByQueryInfo(sort);
		assertEquals(" ORDER BY fstring ASC" , orderBy);
		
		sortArray = new JSONArray("[{property:\"fstring\",direction:\"ASC\"},{property:\"fstring2\",direction:\"DESC\"}]");
		sort = new JSONObject().put("sort", sortArray);
		orderBy = HqlHelper.orderByQueryInfo(sort);
		assertEquals(" ORDER BY fstring ASC , fstring2 DESC" , orderBy);

	}
	
}
