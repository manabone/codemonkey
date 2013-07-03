
package com.codemonkey.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.AppUserService;

public class AppUserServiceTest extends GenericServiceTest<AppUser>{

	@Autowired AppUserService appUserService;
	@Autowired AppRoleService appRoleService;
	
	@Test
	public void testGetAndSave(){
		
		long count = appUserService.count();
		
		AppUser user1 = new AppUser();
		user1.setUsername("user1");
		user1.setPassword("user1");
		
		AppUser user2 = new AppUser();
		user2.setUsername("user2");
		user2.setPassword("user2");
		
		AppUser user3 = new AppUser();
		user3.setUsername("user3");
		user3.setPassword("user3");
		
		//test saving
		appUserService.save(user1);
		appUserService.save(user2);
		appUserService.save(user3);
		
		assertNotNull(user1.getId());
		assertEquals("user1" , user1.getUsername());
		assertNotNull(user1.getPassword());
		assertNotNull(user2.getId());
		assertNotNull(user3.getId());
		
		assertEquals(count + 3 , appUserService.findAll().size());
		assertEquals(count + 3 , appUserService.count());
		
		//test deleting
		appUserService.delete(user2.getId());
		
		assertEquals(count + 2 , appUserService.findAll().size());
		
		List<AppUser> users = new ArrayList<AppUser>();
		
		//test find by criteria
		users = appUserService.find();
		assertEquals(count + 2 , users.size());
		
		users = appUserService.find(Restrictions.eq("username", "user1"));
		assertEquals(1 , users.size());
		
		AppUser user = appUserService.findBy("username", "user1");
		
		assertNotNull(user);
		
	}
	
	@Test
	public void testAuth(){
//		String url = "/app/ext/fooList/index";
//		AppUser user = appUserService.findBy("username", "user");
//		appUserService.login(user.getUsername(), user.getPassword());
//		
//		AppRole appRole = appRoleService.findBy("name", "ROLE_USER");
//		AppPermission appPermission = new AppPermission("fooList:list" , url);
//		appRole.addAppPermission(appPermission);
//		appRoleService.save(appRole);
//		
//		user.addAppRole(appRole);
//		appUserService.save(user);
		
//		appUserService.isAuth(url);
		
	}
	
}
