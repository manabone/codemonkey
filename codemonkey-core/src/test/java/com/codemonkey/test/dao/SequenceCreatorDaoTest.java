package com.codemonkey.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.dao.SequenceCreatorDao;
import com.codemonkey.dao.YearedSequenceCreatorDao;
import com.codemonkey.domain.seq.SequenceCreator;
import com.codemonkey.domain.seq.YearedSequenceCreator;

public class SequenceCreatorDaoTest extends AbsEntityDaoTest<SequenceCreator>{

	@Autowired private SequenceCreatorDao sequenceCreatorDao;
	
	@Autowired private YearedSequenceCreatorDao yearedSequenceCreatorDao;
	
	@Test
	public void test(){
		String seq = sequenceCreatorDao.fetch(new SequenceCreator("TEST"));
		assertEquals("TEST-1" , seq);
		
		seq = sequenceCreatorDao.fetch(new SequenceCreator("TEST"));
		assertEquals("TEST-2" , seq);
		
		seq = yearedSequenceCreatorDao.fetch(new YearedSequenceCreator("TEST" , 2011));
		assertEquals("TEST-2011-1" , seq);
		
		seq = yearedSequenceCreatorDao.fetch(new YearedSequenceCreator("TEST" , 2011));
		assertEquals("TEST-2011-2" , seq);
		
	}

	@Override
	GenericDao<SequenceCreator> getGenericDao() {
		return null;
	}

}
