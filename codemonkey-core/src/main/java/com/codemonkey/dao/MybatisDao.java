package com.codemonkey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisDao{
	
	@Autowired SqlSessionFactory sqlSessionFactory;

	public List<Map<String,Object>> query(String id , Map<String,Object> param){
		SqlSession session = sqlSessionFactory.openSession();
		return session.selectList(id, param);
	}
	
	public Long count(String id , Map<String,Object> param){
		SqlSession session = sqlSessionFactory.openSession();
		return (Long)session.selectOne(id, param);
	}
	
}
