package com.codemonkey.test.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.service.MybatisService;
import com.codemonkey.utils.ExtConstant;


@SuppressWarnings("rawtypes")
public class MybatisServiceTest extends GenericServiceTest {

	@Autowired private MybatisService mybatisService;

	@Test
	public void testQuery() throws ParseException{
		
		List<Map<String , Object>> list = mybatisService.query("selectAppUserList", null);
		assertEquals(2 , list.size());
		
		JSONObject queryAndSort = new JSONObject();
		JSONObject query = new JSONObject();
		
		query.put("username", "adm");
		queryAndSort.put(ExtConstant.QUERY, query);
		
		list = mybatisService.query("selectAppUserList", queryAndSort);
		assertEquals(1 , list.size());
		
		queryAndSort = new JSONObject();
		JSONArray sort = new JSONArray("[{\"property\":\"username\",\"direction\":\"ASC\"}]");
		queryAndSort.put(ExtConstant.SORT, sort);
		
		list = mybatisService.query("selectAppUserList", queryAndSort);
		assertEquals(2 , list.size());
		
		assertEquals("admin" , list.get(0).get("username"));
		
	}
	
	@Test
	public void testCount(){
		
		long count = mybatisService.count("selectCountAppUser", null);
		assertEquals(2 , count);
		
	}
}
