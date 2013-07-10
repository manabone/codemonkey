package com.codemonkey.test.mybatis;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.mybatis.service.MybatisService;
import com.codemonkey.utils.ExtConstant;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath*:spring/applicationContext-datasource.xml", 
		"classpath*:spring/applicationContext-dao.xml",
		"classpath*:spring/applicationContext-globle.xml",
		"classpath*:spring/applicationContext-mybatis.xml"
})
@Transactional
public class MybatisServiceTest{

	@Autowired private MybatisService mybatisService;

	@Autowired private JdbcTemplate jdbcTemplate;
	
	@Test
	public void testQuery() throws ParseException{
		
		prepareData();
		
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

	private void prepareData() {
		jdbcTemplate.execute("insert into app_user (id , username , password) values (-1 , 'admin' , 'admin')");
		jdbcTemplate.execute("insert into app_user (id , username , password) values (-2 , 'user' , 'user')");
	}
	
	@Test
	public void testCount(){

		prepareData();
		
		long count = mybatisService.count("selectAppUserList_count", null);
		assertEquals(2 , count);
		
	}
}
