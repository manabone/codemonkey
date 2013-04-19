
package com.codemonkey.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.Bar;
import com.codemonkey.domain.MM;

public class MMServiceTest extends GenericServiceTest<Bar>{

//	@Autowired MMServiceHolder mmServiceHolder;
//	
//	@Test
//	public void testGetAndSave(){
//		Bar bar1 = new Bar();
//		bar1.setName("group1");
//		bar1.setDescription("group1");
//		
//		mmServiceHolder.saveAndFlush(Bar.class , bar1);
//		
//		assertNotNull(bar1.getId());
//		
//		MM group1 = mmServiceHolder.get(Bar.class , bar1.getId());
//		assertNotNull(group1);
//		
//		//testing update
//		Bar userGrop2 = (Bar) mmServiceHolder.get(Bar.class , bar1.getId());
//		userGrop2.setName("group2");
//		userGrop2.setDescription("group2");
//		mmServiceHolder.saveAndFlush(Bar.class , userGrop2);
//		
//		Bar userGrop3 = (Bar) mmServiceHolder.get(Bar.class , bar1.getId());
//		assertEquals("group2", userGrop3.getName());
//		assertEquals("group2", userGrop3.getDescription());
//		
//		//test deleting
//		mmServiceHolder.deleteAndFlush(Bar.class , bar1.getId());
		
//		assertEquals(2 , mmService.findAll().size());
		
//	}
}
