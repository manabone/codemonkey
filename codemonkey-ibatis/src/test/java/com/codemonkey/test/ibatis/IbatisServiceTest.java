package com.codemonkey.test.ibatis;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.ibatis.dao.IbatisDao;
import com.codemonkey.ibatis.service.IbatisService;
import com.codemonkey.utils.ExtConstant;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath*:spring/app-test-*.xml"
})
@Transactional
public class IbatisServiceTest{

	@Autowired private IbatisService ibatisService;

	@Autowired private IbatisDao ibatisDao;
	
	@Test
	public void testQuery() throws ParseException{
		
		prepareData();
		
		List<Map<String , Object>> list = ibatisService.query("test.selectAppUserList", null);
		assertEquals(2 , list.size());
		
		JSONObject queryAndSort = new JSONObject();
		JSONObject query = new JSONObject();
		
		query.put("username_Like", "adm");
		queryAndSort.put(ExtConstant.QUERY, query);
		
		list = ibatisService.query("test.selectAppUserList", queryAndSort);
		assertEquals(1 , list.size());
		
		queryAndSort = new JSONObject();
		JSONArray sort = new JSONArray("[{\"property\":\"username\",\"direction\":\"ASC\"}]");
		queryAndSort.put(ExtConstant.SORT, sort);
		
		list = ibatisService.query("test.selectAppUserList", queryAndSort);
		assertEquals(2 , list.size());
		
		assertEquals("admin" , list.get(0).get("username"));
		
	}

	private void prepareData() {
		Map<String , Object> map1 = new HashMap<String , Object>();
		map1.put("id", -1);
		map1.put("username", "user");
		map1.put("password", "user");
		ibatisDao.insert("test.insertTestAppUser",map1);
		
		Map<String , Object> map2 = new HashMap<String , Object>();
		map2.put("id", -2);
		map2.put("username", "admin");
		map2.put("password", "admin");
		ibatisDao.insert("test.insertTestAppUser",map2);
	}
	
	@Test
	public void testCount(){

		prepareData();
		
		long count = ibatisService.count("test.selectAppUserList_count", null);
		assertEquals(2 , count);
		
	}
}
