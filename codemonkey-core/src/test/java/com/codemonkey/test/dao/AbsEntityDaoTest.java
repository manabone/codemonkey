package com.codemonkey.test.dao;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.SysUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContextTest.xml" })
@Transactional
public abstract class AbsEntityDaoTest<T extends IEntity>  {

	Logger logger;

	Class<?> type;
	
	AbsEntityDaoTest(){
		this.type = ClassHelper.getSuperClassGenricType(getClass());
		logger = SysUtils.getLog(getClass());
	}
    
    abstract GenericDao<T> getGenericDao();
    
    @Before
    public void onSetUp() throws Exception {
    	
    }
	
}
