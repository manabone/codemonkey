package com.codemonkey.dbMigration.resovler;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.migration.MigrationException;
import com.codemonkey.dbMigration.script.Migration;
import com.codemonkey.dbMigration.script.MigrationFactory;
import com.codemonkey.dbMigration.version.VersionExtractor;

public abstract class AbstractMigrationResolver {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private MigrationFactory migrationFactory = new MigrationFactory();

	private String migrationsLocation;

	private VersionExtractor versionExtractor;

	protected abstract String getExtendsion();

	public Set<Migration> resolve(DBType dbType) {
		Set<Migration> migrations = new HashSet<Migration>();

		// Find all resources in the migrations location.
		List<String> convertedMigrationsLocationList = convertMigrationsLocation(
				getMigrationsLocation(), dbType);

		for (String convertedMigrationsLocation : convertedMigrationsLocationList) {
			PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
			List<Resource> resources;
			try {
				resources = new ArrayList<Resource>(
						Arrays.asList(patternResolver
								.getResources(convertedMigrationsLocation)));
			} catch (IOException e) {
				throw new MigrationException(e);
			}

			// Remove resources starting with a '.' (e.g. .svn, .cvs, etc)
			CollectionUtils.filter(resources, new Predicate() {
				public boolean evaluate(Object object) {
					try {
						String fileName = ((Resource) object).getFilename();
						return (((Resource) object).isReadable()
								&& !fileName.startsWith(".") && fileName
								.endsWith(getExtendsion()));

					} catch (Exception e) {
						if (logger.isDebugEnabled()) {
							logger.debug("Exception while filtering resource.",
									e);
						}
						return false;
					}
				}
			});

			if (resources.isEmpty()) {
				String message = "No migrations were found using resource pattern '"
						+ getMigrationsLocation() + "'.";
				logger.error(message);
				// throw new MigrationException(message);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Found " + resources.size() + " resources: "
						+ collectionToCommaDelimitedString(resources));
			}

			// Extract versions and create executable migrations for each
			// resource.
			for (Resource resource : resources) {
				String version = getVersionExtractor().extractVersion(resource);
				if (CollectionUtils.find(migrations,
						new Migration.MigrationVersionPredicate(version)) != null) {
					String message = "Non-unique migration version.";
					logger.error(message);
					throw new MigrationException(message);
				}
				migrations.add(migrationFactory.create(version, resource));
			}
		}
		return migrations;
	}

	public Set<Migration> resolve() {
		return resolve(DBType.UNKNOWN);
	}

	protected List<String> convertMigrationsLocation(
			String migrationsLocations, DBType dbType) {

		List<String> convertMigrationsLocation = new ArrayList<String>();

		String[] locations = migrationsLocations.split(";");

		for (int i = 0; i < locations.length; i++) {
			String converted = locations[i];

			if (!(isBlank(FilenameUtils.getName(converted)) || FilenameUtils
					.getName(converted).contains("*"))) {
				converted += "/";
			}

			if (!FilenameUtils.getName(converted).contains("*")) {
				converted += "*";
			}

			if (!converted.startsWith("file:")
					&& !converted.startsWith("classpath:")
					&& !converted.startsWith("classpath*:")) {
				converted = "file:" + converted;
			}
			convertMigrationsLocation.add(converted);
		}
		return convertMigrationsLocation;
	}

	public void setMigrationsLocation(String migrationsLocation) {
		this.migrationsLocation = migrationsLocation;
	}

	public String getMigrationsLocation() {
		return migrationsLocation;
	}

	public void setVersionExtractor(VersionExtractor versionExtractor) {
		this.versionExtractor = versionExtractor;
	}
	
	protected VersionExtractor getVersionExtractor(){
		return this.versionExtractor;
	}
}
