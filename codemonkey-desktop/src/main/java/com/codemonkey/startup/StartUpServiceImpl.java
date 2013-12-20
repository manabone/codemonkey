package com.codemonkey.startup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.codemonkey.dbMigration.api.Configure;
import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.migration.DriverManagerMigrationManager;
import com.codemonkey.dbMigration.migration.MigrationManager;
import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.PowerTree;
import com.codemonkey.domain.SecurityComponent;
import com.codemonkey.domain.Status;
import com.codemonkey.service.AppPermissionService;
import com.codemonkey.service.AppUserService;
import com.codemonkey.service.PowerTreeService;
import com.codemonkey.service.SecurityComponentService;
import com.codemonkey.utils.EnumHolder;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.SecurityController;

@Component
@Lazy(false)
// @Transactional(propagation = Propagation.REQUIRED)
public class StartUpServiceImpl implements StartUpService {

	@Autowired
	private AppPermissionService appPermissionService;
	@Autowired
	private SecurityComponentService securityComponentService;
	@Autowired
	private List<SecurityController> securityControllers;
	@Autowired
	private DriverManagerDataSource datasource;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private EnumHolder enumHolder;
	@Autowired
	private PowerTreeService powerTreeService;

	private Logger logger = SysUtils.getLog(StartUpServiceImpl.class);

	@PostConstruct
	public void initEnumHolder() {
		enumHolder.addEnum(Status.class);
	}

	/**
	 * 方法描述：创建初始化用户
	 * 
	 * @param:
	 * @return:
	 * @throws：
	 * @author: zb
	 * @version: 2013年10月23日 下午2:11:38
	 */
	public void doInitUsers() {
		AppUser admin = appUserService.findBy("username", "admin");
		if (admin == null) {
			admin = new AppUser();
			admin.setUsername("admin");
			admin.setName("admin");
			admin.changePassword("admin");
			appUserService.save(admin);
		}

		AppUser user = appUserService.findBy("username", "user");
		if (user == null) {
			user = new AppUser();
			user.setUsername("user");
			admin.setName("user");
			user.changePassword("user");
			appUserService.save(user);
		}

	}

	@PostConstruct
	public void doInitDefaultData() throws SQLException {
		logger.info("jdbc.url = " + datasource.getUrl());
		logger.info("jdbc.username = " + datasource.getUsername());
		logger.info("jdbc.password = " + datasource.getPassword());

		doInitUsers();
		doInitAppPermissions();
		doInitSecurityComponents();
		doInitPowerTree();
		doInitBsUnionCode();
		//doInitBsGroupInfo();
		//doInitBsRegionInfo();
	}
	
	private void doInitBsUnionCode() {
		SysUtils.loadDataToDB(datasource, "classpath*:default-data/default-data-*.xml");
	}

	private void doInitPowerTree() {
		List<PowerTree> powerTrees = new ArrayList<PowerTree>();

		if (CollectionUtils.isNotEmpty(securityControllers)) {
			for (SecurityController sc : securityControllers) {
				if (CollectionUtils.isNotEmpty(sc.regPowerTrees())) {
					powerTrees.addAll(sc.regPowerTrees());
				}
			}
		}

		if (CollectionUtils.isNotEmpty(powerTrees)) {
			for (PowerTree p : powerTrees) {

				PowerTree p1 = powerTreeService.findBy("code", p.getCode());

				if (p1 == null) {
					if (StringUtils.isNotBlank(p.getParentCode())) {
						PowerTree pp = powerTreeService.findBy("code",
								p.getParentCode());
						if (pp != null) {
							p.setParent(pp);
						}
					}
					powerTreeService.save(p);
				}
			}
		}

	}

	@Override
	public void doInitAppPermissions() {
		List<AppPermission> appPermissions = new ArrayList<AppPermission>();

		if (CollectionUtils.isNotEmpty(securityControllers)) {
			for (SecurityController sc : securityControllers) {
				if (CollectionUtils.isNotEmpty(sc.regAppPermissions())) {
					appPermissions.addAll(sc.regAppPermissions());
				}
			}
		}

		if (CollectionUtils.isNotEmpty(appPermissions)) {
			for (AppPermission p : appPermissions) {
				AppPermission pp = appPermissionService.findBy("code",
						p.getPermission());
				if (pp == null) {
					appPermissionService.save(p);
				}
			}
		}
	}

	/**
	 * 方法描述：
	 * 
	 * @param:
	 * @return:
	 * @throws：
	 * @author: zb
	 * @version: 2013年10月23日 下午2:13:37
	 */
	public void doInitSecurityComponents() {
		List<SecurityComponent> cmps = new ArrayList<SecurityComponent>();

		if (CollectionUtils.isNotEmpty(securityControllers)) {
			for (SecurityController sc : securityControllers) {
				if (CollectionUtils.isNotEmpty(sc.regSecurityComponents())) {
					cmps.addAll(sc.regSecurityComponents());
				}
			}
		}

		if (CollectionUtils.isNotEmpty(cmps)) {
			for (SecurityComponent c : cmps) {
				SecurityComponent cc = securityComponentService.findBy("code",
						c.getCode());
				if (cc == null) {
					securityComponentService.save(c);
				}
			}
		}
	}

	@PostConstruct
	public void executeMigration() {
		Configure.configure();
		MigrationManager migrationManager = new DriverManagerMigrationManager(
				datasource, DBType.POSTGRESQL);
		migrationManager.migrate();
	}

}
