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
	
	public List<Map<String,Object>> query(String id , Object obj){
		try {
			return sqlMapClient.queryForList(id, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> query(String id){
		try {
			return sqlMapClient.queryForList(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String,Object> queryobj(String id , Object obj){
		try {
			return (Map<String, Object>) sqlMapClient.queryForObject(id, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String,Object> queryobj(String id){
		try {
			return (Map<String, Object>) sqlMapClient.queryForObject(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Long count(String id , Object obj){
		try {
			return (Long)sqlMapClient.queryForObject(id, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insert(String id , Object obj){
		try {
			sqlMapClient.insert(id , obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String id , Object obj){
		try {
			sqlMapClient.update(id , obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String id , Object obj){
		try {
			sqlMapClient.delete(id , obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
