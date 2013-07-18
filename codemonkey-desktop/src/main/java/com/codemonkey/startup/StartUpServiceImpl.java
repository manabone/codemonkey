package com.codemonkey.startup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.dbMigration.api.Configure;
import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.migration.DriverManagerMigrationManager;
import com.codemonkey.dbMigration.migration.MigrationManager;
import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.domain.Status;
import com.codemonkey.service.AppPermissionService;
import com.codemonkey.service.AppUserService;
import com.codemonkey.service.SecurityComponentService;
import com.codemonkey.utils.EnumHolder;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.SecurityController;

@Component
@Lazy(false)
@Transactional(propagation=Propagation.REQUIRED)
public class StartUpServiceImpl implements StartUpService {

	@Autowired private AppPermissionService appPermissionService;
	@Autowired private SecurityComponentService securityComponentService;
	@Autowired private List<SecurityController> securityControllers;
	@Autowired private DriverManagerDataSource dbUnitdatasource;
	@Autowired private AppUserService appUserService;
	@Autowired private EnumHolder enumHolder;
	
	private Logger logger = SysUtils.getLog(StartUpServiceImpl.class);
	
	
	@PostConstruct
	public void initEnumHolder(){
		enumHolder.addEnum(Status.class);
	}
	
	public void doInitUsers(){
		AppUser admin = appUserService.findBy("username", "admin");
		if(admin == null){
			admin = new AppUser();
			admin.setUsername("admin");
			admin.setPassword("admin");
			appUserService.save(admin);
		}
		
		AppUser user = appUserService.findBy("username", "user");
		if(user == null){
			user = new AppUser();
			user.setUsername("user");
			user.setPassword("user");
			appUserService.save(user);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.codemonkey.startup.StartUpService#initData()
	 */
	@PostConstruct
	public void doInitDefaultData() throws SQLException{
		logger.info("jdbc.url = " + dbUnitdatasource.getUrl());
		logger.info("jdbc.username = " + dbUnitdatasource.getUsername());
		logger.info("jdbc.password = " + dbUnitdatasource.getPassword());
		SysUtils.loadDataToDB(dbUnitdatasource, "default-data.xml" , DatabaseOperation.UPDATE);
	
		SysUtils.putAttribute(SysUtils.CURRENCT_USER, new AppUser());
		doInitUsers();
		doInitAppPermissions();
		doInitSecurityComponents();
	}
	
	/* (non-Javadoc)
	 * @see com.codemonkey.startup.StartUpService#initAppPermissions()
	 */
	public void doInitAppPermissions(){
		List<AppPermission> appPermissions = new ArrayList<AppPermission>();
		
		if(CollectionUtils.isNotEmpty(securityControllers)){
			for(SecurityController sc : securityControllers){
				if(CollectionUtils.isNotEmpty(sc.regAppPermissions())){
					appPermissions.addAll(sc.regAppPermissions());
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(appPermissions)){
			for(AppPermission p : appPermissions){
				AppPermission pp = appPermissionService.findBy("code", p.getPermission());
				if(pp == null){
					appPermissionService.save(p);
				}
			}
		}
	}
	
	public void doInitSecurityComponents(){
		List<SecurityComponent> cmps = new ArrayList<SecurityComponent>();
		
		if(CollectionUtils.isNotEmpty(securityControllers)){
			for(SecurityController sc : securityControllers){
				if(CollectionUtils.isNotEmpty(sc.regSecurityComponents())){
					cmps.addAll(sc.regSecurityComponents());
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(cmps)){
			for(SecurityComponent c : cmps){
				SecurityComponent cc = securityComponentService.findBy("code", c.getCode());
				if(cc == null){
					securityComponentService.save(c);
				}
			}
		}
	}
	
	@PostConstruct
	public void executeMigration() {
		Configure.configure();
		MigrationManager migrationManager = new DriverManagerMigrationManager(dbUnitdatasource , DBType.POSTGRESQL);
		migrationManager.migrate();
	}
	
}
