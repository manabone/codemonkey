package com.codemonkey.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.dao.GenericDao;
import com.codemonkey.dao.SequenceCreatorDao;
import com.codemonkey.domain.seq.SequenceCreator;

public class SequenceCreatorDaoTest extends AbsEntityDaoTest<SequenceCreator>{

	@Autowired private SequenceCreatorDao sequenceCreatorDao;
	
	@Test
	public void test(){
		String seq = sequenceCreatorDao.fetch(new SequenceCreator("TEST"));
		assertEquals("TEST-1" , seq);
	}

	@Override
	GenericDao<SequenceCreator> getGenericDao() {
		return null;
	}

}
