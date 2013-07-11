package com.codemonkey.ibatis.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
@SuppressWarnings("unchecked")
public class IbatisDao {
	
	@Autowired private SqlMapClient sqlMapClient;
	
	public List<Map<String,Object>> query(String id , Map<String,Object> param){
		try {
			return sqlMapClient.queryForList(id, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Long count(String id , Map<String,Object> param){
		try {
			return (Long)sqlMapClient.queryForObject(id, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insert(String id , Map<String,Object> param){
		try {
			sqlMapClient.insert(id , param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String id , Map<String,Object> param){
		try {
			sqlMapClient.update(id , param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String id , Map<String,Object> param){
		try {
			sqlMapClient.delete(id , param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
